package service;

import dao.CropDAO;
import model.Crop;

import java.util.List;

public class CropService {
    private CropDAO cropDAO;

    public CropService() {
        this.cropDAO = new CropDAO();
    }

    public boolean addCrop(Crop crop) {
        return cropDAO.addCrop(crop);
    }

    public Crop getCropByID(int cropID) {
        return cropDAO.getCropByID(cropID);
    }

    public List<Crop> getAllCropsByPlayer(int playerID) {
        return cropDAO.getAllCropsByPlayer(playerID);
    }

    public List<Crop> getCropsByProduceID(int produceID) {
        return cropDAO.getCropsByProduceID(produceID);
    }

    public void advanceGrowthForPlayer(int playerID, int currentDay) {
        List<Crop> crops = cropDAO.getAllCropsByPlayer(playerID);
        for (Crop crop : crops) {
            int plantedDay = crop.getPlantedDay();
            int growthTime = crop.getGrowthTime();
            if (!crop.isReadyToHarvest() && currentDay - plantedDay >= growthTime) {
                cropDAO.updateHarvestStatus(crop.getCropID(), true);
            }
        }
    }

    public List<Crop> getReadyToHarvestCrops(int playerID) {
        return cropDAO.getReadyToHarvest(playerID);
    }

    public boolean resetReadyToHarvest(int playerID) {
        return cropDAO.resetReadyToHarvestCrops(playerID);
    }

    public boolean markHarvested(int cropID) {
        return cropDAO.updateHarvestStatus(cropID, false);
    }


    public boolean updateCrop(Crop crop) {
        return cropDAO.updateCrop(crop);
    }

    public boolean updateHarvestStatus(int cropID, boolean ready) {
        return cropDAO.updateHarvestStatus(cropID, ready);
    }

    public boolean deleteCrop(int cropID) {
        return cropDAO.deleteCrop(cropID);
    }
}