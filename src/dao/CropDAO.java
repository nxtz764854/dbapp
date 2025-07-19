package dao;

import model.Crop;
import java.sql.*;
import java.util.*;

public class CropDAO {

    private Connection conn;

    public CropDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Retrieves all crops planted by a player from the database.
     */
    public List<Crop> getCropsByPlayer(int playerID) {
        List<Crop> crops = new ArrayList<>();
        String sql = "SELECT * FROM crop WHERE playerID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Crop crop = new Crop(
                    rs.getInt("cropID"),
                    rs.getInt("playerID"),
                    rs.getInt("itemID"),
                    rs.getString("cropname"),
                    rs.getInt("growth_time"),
                    rs.getInt("produceID"),
                    rs.getBoolean("readytoharvest")
                );
                crops.add(crop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return crops;
    }

    /**
     * Adds a new crop to the database.
     */
    public boolean addCrop(Crop crop) {
        String sql = "INSERT INTO crop (playerID, itemID, cropname, growth_time, produceID, readytoharvest) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, crop.getPlayerID());
            stmt.setInt(2, crop.getItemID());
            stmt.setString(3, crop.getCropname());
            stmt.setInt(4, crop.getGrowth_time());
            stmt.setInt(5, crop.getProduceID());
            stmt.setBoolean(6, crop.isReadytoharvest());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Retrieves crops that are ready to harvest for a player.
     */
    public List<Crop> getReadyToHarvest(int playerID) {
        List<Crop> crops = new ArrayList<>();
        String sql = "SELECT * FROM crop WHERE playerID = ? AND readytoharvest = TRUE";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Crop crop = new Crop(
                    rs.getInt("cropID"),
                    rs.getInt("playerID"),
                    rs.getInt("itemID"),
                    rs.getString("cropname"),
                    rs.getInt("growth_time"),
                    rs.getInt("produceID"),
                    rs.getBoolean("readytoharvest")
                );
                crops.add(crop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return crops;
    }

    /**
     * Updates the ready-to-harvest status of a crop.
     */
    public boolean updateHarvestStatus(int cropID, boolean ready) {
        String sql = "UPDATE crop SET readytoharvest = ? WHERE cropID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, ready);
            stmt.setInt(2, cropID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Deletes a crop by ID.
     */
    public boolean deleteCrop(int cropID) {
        String sql = "DELETE FROM crop WHERE cropID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cropID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
