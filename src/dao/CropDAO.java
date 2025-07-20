package dao;

import model.Crop;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CropDAO {

    public boolean addCrop(Crop crop) {
        String sql = "INSERT INTO crops (cropname, playerID, itemID, planted_day, growth_time, produceID, readytoharvest) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, crop.getCropname());
            stmt.setInt(2, crop.getPlayerID());
            stmt.setInt(3, crop.getItemID());
            stmt.setInt(4, crop.getPlantedDay());
            stmt.setInt(5, crop.getGrowthTime());
            stmt.setInt(6, crop.getProduceID());
            stmt.setBoolean(7, crop.isReadyToHarvest());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCrop(Crop crop) {
        String sql = "UPDATE crops SET cropname = ?, playerID = ?, itemID = ?, planted_day = ?, growth_time = ?, produceID = ?, readytoharvest = ? WHERE cropID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, crop.getCropname());
            stmt.setInt(2, crop.getPlayerID());
            stmt.setInt(3, crop.getItemID());
            stmt.setInt(4, crop.getPlantedDay());
            stmt.setInt(5, crop.getGrowthTime());
            stmt.setInt(6, crop.getProduceID());
            stmt.setBoolean(7, crop.isReadyToHarvest());
            stmt.setInt(8, crop.getCropID());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateHarvestStatus(int cropID, boolean ready) {
        String sql = "UPDATE crops SET readytoharvest = ? WHERE cropID = ?";
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
        String sql = "DELETE FROM crops WHERE cropID = ?";
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
        String sql = "SELECT * FROM crops WHERE cropID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cropID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractCrop(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Crop> getAllCropsByPlayer(int playerID) {
        List<Crop> crops = new ArrayList<>();
        String sql = "SELECT * FROM crops WHERE playerID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                crops.add(extractCrop(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return crops;
    }

    public List<Crop> getReadyToHarvest(int playerID) {
        List<Crop> crops = new ArrayList<>();
        String sql = "SELECT * FROM crops WHERE playerID = ? AND readytoharvest = TRUE";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                crops.add(extractCrop(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return crops;
    }

    public List<Crop> getCropsByProduceID(int produceID) {
        List<Crop> crops = new ArrayList<>();
        String sql = "SELECT * FROM crops WHERE produceID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, produceID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                crops.add(extractCrop(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return crops;
    }

    private Crop extractCrop(ResultSet rs) throws SQLException {
        Crop crop = new Crop();
        crop.setCropID(rs.getInt("cropID"));
        crop.setCropname(rs.getString("cropname"));
        crop.setPlayerID(rs.getInt("playerID"));
        crop.setItemID(rs.getInt("itemID"));
        crop.setPlantedDay(rs.getInt("planted_day"));
        crop.setGrowthTime(rs.getInt("growth_time"));
        crop.setProduceID(rs.getInt("produceID"));
        crop.setReadytToHarvest(rs.getBoolean("readytoharvest"));
        return crop;
    }
}
