package service;

import dao.CropDAO;
import model.Crop;
import util.DBConnection;

import java.sql.Connection;
import java.util.List;

public class CropService {
    private CropDAO cropDAO;

    public CropService() {
        try {
            Connection conn = DBConnection.getConnection();
            this.cropDAO = new CropDAO(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all crops owned by a player.
     */
    public List<Crop> getCropsByPlayerID(int playerID) {
        return cropDAO.getCropsByPlayer(playerID);
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
        return cropDAO.getReadyToHarvest(playerID);
    }

    /**
     * Get crops by produce item ID (e.g., what crop produces this item).
     */
    public List<Crop> getCropsByProduceID(int produceID) {
        return cropDAO.getCropsByProduceID(produceID);
    }
}
