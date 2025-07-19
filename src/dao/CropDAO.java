package dao;

import model.Crop;
import util.DBConnection;

import java.sql.*;
import java.util.*;

public class CropDAO {

    public List<Crop> getCropsByPlayer(int playerID) {
        List<Crop> crops = new ArrayList<>();
        String sql = "SELECT * FROM crop WHERE playerID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Crop crop = mapResultSetToCrop(rs);
                crops.add(crop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return crops;
    }

    public boolean addCrop(Crop crop) {
        String sql = "INSERT INTO crop (playerID, itemID, cropname, growth_time, produceID, readytoharvest) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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

    public boolean updateCrop(Crop crop) {
        String sql = "UPDATE crop SET playerID = ?, itemID = ?, cropname = ?, growth_time = ?, produceID = ?, readytoharvest = ? WHERE cropID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, crop.getPlayerID());
            stmt.setInt(2, crop.getItemID());
            stmt.setString(3, crop.getCropname());
            stmt.setInt(4, crop.getGrowth_time());
            stmt.setInt(5, crop.getProduceID());
            stmt.setBoolean(6, crop.isReadytoharvest());
            stmt.setInt(7, crop.getCropID());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Crop> getReadyToHarvest(int playerID) {
        List<Crop> crops = new ArrayList<>();
        String sql = "SELECT * FROM crop WHERE playerID = ? AND readytoharvest = TRUE";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Crop crop = mapResultSetToCrop(rs);
                crops.add(crop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return crops;
    }

    public boolean updateHarvestStatus(int cropID, boolean ready) {
        String sql = "UPDATE crop SET readytoharvest = ? WHERE cropID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, ready);
            stmt.setInt(2, cropID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteCrop(int cropID) {
        String sql = "DELETE FROM crop WHERE cropID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cropID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Crop getCropByID(int cropID) {
        String sql = "SELECT * FROM crop WHERE cropID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cropID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToCrop(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Crop> getCropsByProduceID(int produceID) {
        List<Crop> crops = new ArrayList<>();
        String sql = "SELECT * FROM crop WHERE produceID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, produceID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Crop crop = mapResultSetToCrop(rs);
                crops.add(crop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return crops;
    }

    private Crop mapResultSetToCrop(ResultSet rs) throws SQLException {
        return new Crop(
            rs.getInt("cropID"),
            rs.getInt("playerID"),
            rs.getInt("itemID"),
            rs.getString("cropname"),
            rs.getInt("growth_time"),
            rs.getInt("produceID"),
            rs.getBoolean("readytoharvest")
        );
    }
}
