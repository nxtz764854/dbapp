package dao;

import model.Farm;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FarmDAO {

    public boolean insertFarm(Farm farm) {
        String sql = "INSERT INTO farm (playerID, cropID, animalID) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, farm.getPlayerID());
            stmt.setInt(2, farm.getCropID());
            stmt.setInt(3, farm.getAnimalID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Insert Farm Error: " + e.getMessage());
            return false;
        }
    }

    public boolean updateFarm(Farm farm) {
        String sql = "UPDATE farm SET cropID = ?, animalID = ? WHERE playerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, farm.getCropID());
            stmt.setInt(2, farm.getAnimalID());
            stmt.setInt(3, farm.getPlayerID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update Farm Error: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteFarm(int playerID) {
        String sql = "DELETE FROM farm WHERE playerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Delete Farm Error: " + e.getMessage());
            return false;
        }
    }

    public Farm getFarmByPlayerID(int playerID) {
        String sql = "SELECT * FROM farm WHERE playerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Farm farm = new Farm();
                farm.setPlayerID(rs.getInt("playerID"));
                farm.setCropID(rs.getInt("cropID"));
                farm.setAnimalID(rs.getInt("animalID"));
                return farm;
            }
        } catch (SQLException e) {
            System.err.println("Get Farm Error: " + e.getMessage());
        }
        return null;
    }

    public List<Farm> getAllFarms() {
        List<Farm> farms = new ArrayList<>();
        String sql = "SELECT * FROM farm";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Farm farm = new Farm();
                farm.setPlayerID(rs.getInt("playerID"));
                farm.setCropID(rs.getInt("cropID"));
                farm.setAnimalID(rs.getInt("animalID"));
                farms.add(farm);
            }
        } catch (SQLException e) {
            System.err.println("Get All Farms Error: " + e.getMessage());
        }
        return farms;
    }
}
