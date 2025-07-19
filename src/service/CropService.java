package service;

import dao.CropDAO;
import model.Crop;

import java.util.ArrayList;
import java.util.List;

public class CropService {
    private CropDAO cropDAO;

    public CropService() {
        this.cropDAO = new CropDAO();
    }

    /**
     * Get all crops owned by a player.
     */
    public List<Crop> getCropsByPlayerID(int playerID) {
        List<Crop> crops = cropDAO.getCropsByPlayer(playerID);
        return crops != null ? crops : new ArrayList<>();
    }

    /**
     * Add a new crop to the player's farm.
     */
    public boolean addCrop(Crop crop) {
        return cropDAO.addCrop(crop);
    }

    /**
     * Update a crop's details (e.g., age, readytoharvest).
     */
    public boolean updateCrop(Crop crop) {
        return cropDAO.updateCrop(crop);
    }

    public void markHarvested(int cropID) {
        cropDAO.deleteCrop(cropID); // Remove harvested crop from the farm
    }

    /**
     * Remove a crop from the farm.
     */
    public boolean deleteCrop(int cropID) {
        return cropDAO.deleteCrop(cropID);
    }

    /**
     * Get a specific crop by its ID.
     */
    public Crop getCropByID(int cropID) {
        return cropDAO.getCropByID(cropID);
    }

    public void advanceGrowthForPlayer(int playerID) {
        List<Crop> crops = cropDAO.getCropsByPlayer(playerID);
        for (Crop crop : crops) {
            int days = crop.getGrowth_time();
            if (days > 1) {
                crop.setGrowth_time(days - 1);
                cropDAO.updateCrop(crop);
            } else if (days == 1) {
                crop.setGrowth_time(0);
                crop.setReadytoharvest(true);
                cropDAO.updateCrop(crop);
            }
        }
    }

    /**
     * Get all crops that are ready to harvest for a player.
     */
    public List<Crop> getReadyToHarvestCrops(int playerID) {
        List<Crop> ready = cropDAO.getReadyToHarvest(playerID);
        return ready != null ? ready : new ArrayList<>();
    }

    /**
     * Get crops by produce item ID (e.g., what crop produces this item).
     */
    public List<Crop> getCropsByProduceID(int produceID) {
        List<Crop> byProduce = cropDAO.getCropsByProduceID(produceID);
        return byProduce != null ? byProduce : new ArrayList<>();
    }
}
