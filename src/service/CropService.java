package service;

import dao.CropDAO;
import model.Crop;

import java.util.List;

public class CropService {
    private CropDAO cropDAO;  // Data Access Object for Crop operations

    public CropService() {
        this.cropDAO = new CropDAO();  // Initialize CropDAO
    }

    // Adds a new crop to the database
    public boolean addCrop(Crop crop) {
        return cropDAO.addCrop(crop);
    }

    // Retrieves a crop by its ID
    public Crop getCropByID(int cropID) {
        return cropDAO.getCropByID(cropID);
    }

    // Retrieves all crops for a given player
    public List<Crop> getAllCropsByPlayer(int playerID) {
        return cropDAO.getAllCropsByPlayer(playerID);
    }

    // Retrieves crops by their produce ID
    public List<Crop> getCropsByProduceID(int produceID) {
        return cropDAO.getCropsByProduceID(produceID);
    }

    // Advances the growth of crops for a player based on the current day
    public void advanceGrowthForPlayer(int playerID, int currentDay) {
        List<Crop> crops = cropDAO.getAllCropsByPlayer(playerID);
        for (Crop crop : crops) {
            int plantedDay = crop.getPlantedDay();
            int growthTime = crop.getGrowthTime();
            // Update harvest status if crop is ready
            if (!crop.isReadyToHarvest() && currentDay - plantedDay >= growthTime) {
                cropDAO.updateHarvestStatus(crop.getCropID(), true);
            }
        }
    }

    // Retrieves crops that are ready to be harvested for a player
    public List<Crop> getReadyToHarvestCrops(int playerID) {
        return cropDAO.getReadyToHarvest(playerID);
    }

    // Resets the harvest status of crops for a player
    public boolean resetReadyToHarvest(int playerID) {
        return cropDAO.resetReadyToHarvestCrops(playerID);
    }

    // Marks a crop as harvested
    public boolean markHarvested(int cropID) {
        return cropDAO.updateHarvestStatus(cropID, false);
    }

    // Updates a crop's information
    public boolean updateCrop(Crop crop) {
        return cropDAO.updateCrop(crop);
    }

    // Updates the harvest status of a specific crop
    public boolean updateHarvestStatus(int cropID, boolean ready) {
        return cropDAO.updateHarvestStatus(cropID, ready);
    }

    // Deletes a crop by its ID
    public boolean deleteCrop(int cropID) {
        return cropDAO.deleteCrop(cropID);
    }
}
