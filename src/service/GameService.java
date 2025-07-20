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
    }

    public void advanceDay(int playerID) {
        Player player = playerService.getPlayerByID(playerID);
        if (player == null) return;

        int day = player.getCurrent_day();
        String season = player.getCurrent_season();
        int year = player.getCurrent_year();

        // Advance date
        day++;
        if (day > 28) {
            day = 1;
            season = getNextSeason(season);
            if (season.equals("Spring")) {
                year++;
            }
        }

        // Update player date
        player.setCurrent_day(day);
        player.setCurrent_season(season);
        player.setCurrent_year(year);
        playerService.updatePlayer(player);

        // Daily resets
        animalService.incrementAgesAndSetReady(playerID);
        animalService.resetReadyToHarvest(playerID);
        cropService.advanceGrowthForPlayer(playerID, day);
        npcService.resetAllGiftFlags();
    }

    private String getNextSeason(String currentSeason) {
        return switch (currentSeason) {
            case "Spring" -> "Summer";
            case "Summer" -> "Fall";
            case "Fall" -> "Winter";
            case "Winter" -> "Spring";
            default -> "Spring";
        };
    }

    public boolean giveGift(int playerID, int npcID, int itemID) {
        Player player = playerService.getPlayerByID(playerID);
        if (player == null) return false;

        String season = player.getCurrent_season();
        int day = player.getCurrent_day();
        int year = player.getCurrent_year();

        // Check inventory
        boolean removed = inventoryService.removeItemFromInventory(playerID, itemID, 1);
        if (!removed) return false;

        // Create or update relation
        Relation relation = relationService.getRelation(playerID, npcID);
        if (relation == null) {
            relationService.createRelation(playerID, npcID);
            relation = relationService.getRelation(playerID, npcID);
        }

        // Add heart and update gift stats
        relationService.incrementHearts(playerID, npcID, 1);
        relationService.updateGiftStats(playerID, npcID, day, relation.getGiftCountThisWeek() + 1);

        // Log the gift
        GiftLog giftLog = new GiftLog(playerID, npcID, itemID, season, day, year);
        return giftLogService.logGift(giftLog);
    }

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
}
