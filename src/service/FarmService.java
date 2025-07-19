package service;

import dao.FarmDAO;
import model.Farm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FarmService {
    private FarmDAO farmDAO;

    public FarmService(Connection conn) {
        this.farmDAO = new FarmDAO(conn);
    }

    public boolean addFarm(Farm farm) {
        try {
            return farmDAO.insertFarm(farm);
        } catch (SQLException e) {
            System.out.println("Error adding farm: " + e.getMessage());
            return false;
        }
    }

    public boolean updateFarm(Farm farm) {
        try {
            return farmDAO.updateFarm(farm);
        } catch (SQLException e) {
            System.out.println("Error updating farm: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteFarm(int playerID) {
        try {
            return farmDAO.deleteFarm(playerID);
        } catch (SQLException e) {
            System.out.println("Error deleting farm: " + e.getMessage());
            return false;
        }
    }

    public Farm getFarmByPlayerID(int playerID) {
        try {
            return farmDAO.getFarmByPlayerID(playerID);
        } catch (SQLException e) {
            System.out.println("Error retrieving farm: " + e.getMessage());
            return null;
        }
    }

    public List<Farm> getAllFarms() {
        try {
            return farmDAO.getAllFarms();
        } catch (SQLException e) {
            System.out.println("Error retrieving all farms: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
