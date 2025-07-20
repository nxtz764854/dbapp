package service;

import dao.FarmDAO;
import model.Farm;

import java.util.ArrayList;
import java.util.List;

public class FarmService {
    private FarmDAO farmDAO; // Data access object for Farm operations

    public FarmService() {
        this.farmDAO = new FarmDAO(); // Initialize FarmDAO
    }

    // Adds a farm and returns true if successful
    public boolean addFarm(Farm farm) {
        return farmDAO.insertFarm(farm);
    }

    // Updates a farm and returns true if successful
    public boolean updateFarm(Farm farm) {
        return farmDAO.updateFarm(farm);
    }

    // Deletes a farm by player ID and returns true if successful
    public boolean deleteFarm(int playerID) {
        return farmDAO.deleteFarm(playerID);
    }

    // Retrieves a farm by player ID
    public Farm getFarmByPlayerID(int playerID) {
        return farmDAO.getFarmByPlayerID(playerID);
    }

    // Retrieves all farms, returns an empty list if none found
    public List<Farm> getAllFarms() {
        List<Farm> farms = farmDAO.getAllFarms();
        return farms != null ? farms : new ArrayList<>();
    }
}
