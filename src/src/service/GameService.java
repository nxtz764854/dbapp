package service;

import model.*;
import java.util.List;

/**
 * The GameService class handles various game operations including advancing the day,
 * giving gifts, harvesting crops and animals, and managing transactions for items.
 */
public class GameService {

    private PlayerService playerService;
    private RelationService relationService;
    private AnimalService animalService;
    private CropService cropService;
    private InventoryService inventoryService;
    private NPCService npcService;
    private GiftLogService giftLogService;
    private HarvestLogService harvestLogService;
    private ProductLogService productLogService;
    private TransactionService transactionService;
    private ItemService itemService;

    /**
     * Initializes the GameService with all necessary services.
     */
    public GameService() {
        this.playerService = new PlayerService();
        this.relationService = new RelationService();
        this.animalService = new AnimalService();
        this.cropService = new CropService();
        this.inventoryService = new InventoryService();
        this.npcService = new NPCService();
        this.giftLogService = new GiftLogService();
        this.harvestLogService = new HarvestLogService();
        this.productLogService = new ProductLogService();
        this.transactionService = new TransactionService();
        this.itemService = new ItemService();
    }

    /**
     * Advances the game day for a player, updating the player's day, season, and year.
     * Also increments animal ages, advances crop growth, and resets gift flags.
     * 
     * @param playerID The ID of the player whose day is to be advanced.
     */
    public void advanceDay(int playerID) {
        Player player = playerService.getPlayerByID(playerID);
        if (player == null) return;

        int day = player.getCurrent_day();
        String season = player.getCurrent_season();
        int year = player.getCurrent_year();

        day++;
        if (day > 28) {
            day = 1;
            season = getNextSeason(season);
            if (season.equals("Spring")) {
                year++;
            }
        }

        player.setCurrent_day(day);
        player.setCurrent_season(season);
        player.setCurrent_year(year);
        playerService.updatePlayer(player);

        animalService.incrementAgesAndSetReady(playerID);
        cropService.advanceGrowthForPlayer(playerID, day);
        npcService.resetAllGiftFlags();
    }

    /**
     * Determines the next season in the game cycle.
     * 
     * @param currentSeason The current season.
     * @return The next season.
     */
    private String getNextSeason(String currentSeason) {
        return switch (currentSeason) {
            case "Spring" -> "Summer";
            case "Summer" -> "Fall";
            case "Fall" -> "Winter";
            case "Winter" -> "Spring";
            default -> "Spring";
        };
    }

    /**
     * Allows a player to give a gift to an NPC, updating the player's inventory
     * and the NPC's relationship stats.
     * 
     * @param playerID The ID of the player giving the gift.
     * @param npcID The ID of the NPC receiving the gift.
     * @param itemID The ID of the item being gifted.
     * @return true if the gift was successfully given, false otherwise.
     */
    public boolean giveGift(int playerID, int npcID, int itemID) {
        Player player = playerService.getPlayerByID(playerID);
        if (player == null) return false;

        String season = player.getCurrent_season();
        int day = player.getCurrent_day();
        int year = player.getCurrent_year();

        boolean removed = inventoryService.removeItemFromInventory(playerID, itemID, 1);
        if (!removed) return false;

        Relation relation = relationService.getRelation(playerID, npcID);
        if (relation == null) {
            relationService.createRelation(playerID, npcID);
            relation = relationService.getRelation(playerID, npcID);
        }

        relationService.incrementHearts(playerID, npcID, 1);
        relationService.updateGiftStats(playerID, npcID, day, relation.getGiftCountThisWeek() + 1);

        GiftLog giftLog = new GiftLog(playerID, npcID, itemID, season, day, year);
        return giftLogService.logGift(giftLog);
    }

    /**
     * Harvests all animals ready for harvest for a player, updating the inventory
     * and logging the harvest.
     * 
     * @param playerID The ID of the player harvesting animals.
     */
    public void harvestAnimals(int playerID) {
        Player player = playerService.getPlayerByID(playerID);
        if (player == null) return;

        String season = player.getCurrent_season();
        int day = player.getCurrent_day();
        int year = player.getCurrent_year();

        List<Animal> animals = animalService.getReadyToHarvestAnimals(playerID);
        for (Animal animal : animals) {
            int animalID = animal.getAnimalID();
            int itemID = animal.getProduceID();

            inventoryService.addItemToInventory(playerID, itemID, 1);
            animalService.markHarvested(animalID);

            ProductLog log = new ProductLog(playerID, animalID, itemID, 1, season, day, year);
            productLogService.logProductCollection(log);
        }
    }

    /**
     * Harvests all crops ready for harvest for a player, updating the inventory
     * and logging the harvest.
     * 
     * @param playerID The ID of the player harvesting crops.
     */
    public void harvestCrops(int playerID) {
        Player player = playerService.getPlayerByID(playerID);
        if (player == null) return;

        String season = player.getCurrent_season();
        int day = player.getCurrent_day();
        int year = player.getCurrent_year();

        List<Crop> crops = cropService.getReadyToHarvestCrops(playerID);
        for (Crop crop : crops) {
            int cropID = crop.getCropID();
            int itemID = crop.getProduceID();

            inventoryService.addItemToInventory(playerID, itemID, 1);
            cropService.markHarvested(cropID);

            HarvestLog log = new HarvestLog(playerID, cropID, itemID, 1, season, day, year);
            harvestLogService.recordHarvest(log);
        }
    }

    /**
     * Allows a player to buy an item, updating the player's inventory and wallet.
     * 
     * @param playerID The ID of the player buying the item.
     * @param itemID The ID of the item being bought.
     * @return true if the item was successfully bought, false otherwise.
     */
    public boolean buyItem(int playerID, int itemID) {
        Item item = itemService.getItemByID(itemID);
        if (item == null || !item.isBuyable()) return false;

        int price = item.getPrice();
        Player player = playerService.getPlayerByID(playerID);
        if (player.getWallet() < price) return false;

        player.setWallet(player.getWallet() - price);
        playerService.updatePlayer(player);

        inventoryService.addItemToInventory(playerID, itemID, 1);

        Transaction transaction = new Transaction(playerID, "buy", itemID, 1, price,
                player.getCurrent_season(), player.getCurrent_day(), player.getCurrent_year());
        transactionService.recordTransaction(transaction);

        return true;
    }

    /**
     * Allows a player to sell an item, updating the player's inventory and wallet.
     * 
     * @param playerID The ID of the player selling the item.
     * @param itemID The ID of the item being sold.
     * @param price The price at which the item is sold.
     * @return true if the item was successfully sold, false otherwise.
     */
    public boolean sellItem(int playerID, int itemID, int price) {
        boolean removed = inventoryService.removeItemFromInventory(playerID, itemID, 1);
        if (!removed) return false;

        Player player = playerService.getPlayerByID(playerID);
        player.setWallet(player.getWallet() + price);
        playerService.updatePlayer(player);

        Transaction transaction = new Transaction(playerID, "sell", itemID, 1, price,
                player.getCurrent_season(), player.getCurrent_day(), player.getCurrent_year());
        transactionService.recordTransaction(transaction);

        return true;
    }
}

