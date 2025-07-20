package service;

import model.*;
import java.util.List;

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
    private ShopService shopService;
    private ShopInventoryService shopInventoryService;
    private TransactionService transactionService;

    public GameService() {
        // Initialize all service instances
        this.playerService = new PlayerService();
        this.relationService = new RelationService();
        this.animalService = new AnimalService();
        this.cropService = new CropService();
        this.inventoryService = new InventoryService();
        this.npcService = new NPCService();
        this.giftLogService = new GiftLogService();
        this.harvestLogService = new HarvestLogService();
        this.productLogService = new ProductLogService();
    }

    public void advanceDay(int playerID) {
        // Fetch player information
        Player player = playerService.getPlayerByID(playerID);
        if (player == null) return;

        int day = player.getCurrent_day();
        String season = player.getCurrent_season();
        int year = player.getCurrent_year();

        // Advance date logic
        day++;
        if (day > 28) {
            day = 1;
            season = getNextSeason(season);
            if (season.equals("Spring")) {
                year++;
            }
        }

        // Update player's date attributes
        player.setCurrent_day(day);
        player.setCurrent_season(season);
        player.setCurrent_year(year);
        playerService.updatePlayer(player);

        // Perform daily resets
        animalService.incrementAgesAndSetReady(playerID);
        animalService.resetReadyToHarvest(playerID);
        cropService.advanceGrowthForPlayer(playerID, day);
        npcService.resetAllGiftFlags();
    }

    private String getNextSeason(String currentSeason) {
        // Determine next season
        return switch (currentSeason) {
            case "Spring" -> "Summer";
            case "Summer" -> "Fall";
            case "Fall" -> "Winter";
            case "Winter" -> "Spring";
            default -> "Spring";
        };
    }

    public boolean giveGift(int playerID, int npcID, int itemID) {
        // Fetch player information
        Player player = playerService.getPlayerByID(playerID);
        if (player == null) return false;

        String season = player.getCurrent_season();
        int day = player.getCurrent_day();
        int year = player.getCurrent_year();

        // Check and remove item from inventory
        boolean removed = inventoryService.removeItemFromInventory(playerID, itemID, 1);
        if (!removed) return false;

        // Create or update NPC relation
        Relation relation = relationService.getRelation(playerID, npcID);
        if (relation == null) {
            relationService.createRelation(playerID, npcID);
            relation = relationService.getRelation(playerID, npcID);
        }

        // Increase relationship heart count and update gift stats
        relationService.incrementHearts(playerID, npcID, 1);
        relationService.updateGiftStats(playerID, npcID, day, relation.getGiftCountThisWeek() + 1);

        // Log the gift action
        GiftLog giftLog = new GiftLog(playerID, npcID, itemID, season, day, year);
        return giftLogService.logGift(giftLog);
    }

    public void harvestAnimals(int playerID) {
        // Fetch player information
        Player player = playerService.getPlayerByID(playerID);
        if (player == null) return;

        String season = player.getCurrent_season();
        int day = player.getCurrent_day();
        int year = player.getCurrent_year();

        // Get and process ready-to-harvest animals
        List<Animal> animals = animalService.getReadyToHarvestAnimals(playerID);
        for (Animal animal : animals) {
            int animalID = animal.getAnimalID();
            int itemID = animal.getProduceID();

            // Add produce to inventory and mark animal as harvested
            inventoryService.addItemToInventory(playerID, itemID, 1);
            animalService.markHarvested(animalID);

            // Log product collection
            ProductLog log = new ProductLog(playerID, animalID, itemID, 1, season, day, year);
            productLogService.logProductCollection(log);
        }
    }

    public void harvestCrops(int playerID) {
        // Fetch player information
        Player player = playerService.getPlayerByID(playerID);
        if (player == null) return;

        String season = player.getCurrent_season();
        int day = player.getCurrent_day();
        int year = player.getCurrent_year();

        // Get and process ready-to-harvest crops
        List<Crop> crops = cropService.getReadyToHarvestCrops(playerID);
        for (Crop crop : crops) {
            int cropID = crop.getCropID();
            int itemID = crop.getProduceID();

            // Add produce to inventory and mark crop as harvested
            inventoryService.addItemToInventory(playerID, itemID, 1);
            cropService.markHarvested(cropID);

            // Log harvest
            HarvestLog log = new HarvestLog(playerID, cropID, itemID, 1, season, day, year);
            harvestLogService.recordHarvest(log);
        }
    }
    
    public boolean buyItem(int playerID, int shopID, int itemID) {
        ShopInventory item = shopInventoryService.getItemFromShop(shopID, itemID);
        if (item == null) return false;

        int price = item.getPrice();
        Player player = playerService.getPlayerByID(playerID);
        if (player.getWallet() < price) return false;

        // Deduct wallet and update
        player.setWallet(player.getWallet() - price);
        playerService.updatePlayer(player);

        inventoryService.addItemToInventory(playerID, itemID, 1);

        Transaction transaction = new Transaction(playerID, "buy", itemID, 1, price,
            player.getCurrent_season(), player.getCurrent_day(), player.getCurrent_year());
        transactionService.recordTransaction(transaction);

        return true;
    }

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
