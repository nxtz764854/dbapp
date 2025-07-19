package service;

import dao.FarmDAO;
import model.Farm;

import java.util.ArrayList;
import java.util.List;

public class FarmService {
    private FarmDAO farmDAO;

    public FarmService() {
        this.farmDAO = new FarmDAO();
    }

    public boolean addFarm(Farm farm) {
        return farmDAO.insertFarm(farm);
    }

    public boolean updateFarm(Farm farm) {
        return farmDAO.updateFarm(farm);
    }

    public boolean deleteFarm(int playerID) {
        return farmDAO.deleteFarm(playerID);
    }

    public Farm getFarmByPlayerID(int playerID) {
        return farmDAO.getFarmByPlayerID(playerID);
    }

    public List<Farm> getAllFarms() {
        List<Farm> farms = farmDAO.getAllFarms();
        return farms != null ? farms : new ArrayList<>();
    }
}
