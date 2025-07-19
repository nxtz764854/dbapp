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
