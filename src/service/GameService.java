package service;

import model.*;

public class GameService {
    private PlayerService playerService;
    private RelationService relationService;
    private AnimalService animalService;
    private CropService cropService;
    private InventoryService inventoryService;
    private NPCService npcService;


    public GameService() {
        this.playerService = new PlayerService();
        this.relationService = new RelationService();
        this.animalService = new AnimalService();
        this.cropService = new CropService();
        this.inventoryService = new InventoryService();
        this.npcService = new NPCService();
    }

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

        animalService.resetReadyToHarvest(playerID);
        cropService.advanceGrowthForPlayer(playerID);
        npcService.resetAllGiftFlags();
    }

    private String getNextSeason(String currentSeason) {
        return switch (currentSeason) {
            case "Spring" -> "Summer";
            case "Summer" -> "Fall";
            case "Fall" -> "Winter";
            case "Winter" -> "Spring";
            default -> "Spring"; // fallback for unrecognized values
        };
    }

    // Gift giving logic
    public boolean giveGift(int playerID, int npcID, int itemID) {
        Relation relation = relationService.getRelation(playerID, npcID);
        if (relation == null) {
            relationService.createRelation(playerID, npcID);
            relation = relationService.getRelation(playerID, npcID);
        }

        int currentHearts = relation.getNpchearts();
        currentHearts += 1; // Simple gift logic, can expand
        return relationService.updateHearts(playerID, npcID, currentHearts);
    }

    public void harvestAnimals(int playerID) {
        animalService.getReadyToHarvestAnimals(playerID).forEach(animal -> {
            int produceID = animal.getProduceID();
            inventoryService.addItemToInventory(playerID, produceID, 1);
            animalService.markHarvested(animal.getAnimalID());
        });
    }

    public void harvestCrops(int playerID) {
        cropService.getReadyToHarvestCrops(playerID).forEach(crop -> {
            int produceID = crop.getProduceID();
            inventoryService.addItemToInventory(playerID, produceID, 1); 
            cropService.markHarvested(crop.getCropID());
        });
    }
}

