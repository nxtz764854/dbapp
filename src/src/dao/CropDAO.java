package dao;

import model.Crop;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CropDAO {

    /**
     * Adds a new crop to the database.
     * @param crop the crop to add
     * @return true if the operation is successful
     */
    public boolean addCrop(Crop crop) {
        String sql = "INSERT INTO crops (cropname, playerID, itemID, planted_day, growth_time, produceID, readytoharvest) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameters for the insertion
            stmt.setString(1, crop.getCropname());
            stmt.setInt(2, crop.getPlayerID());
            stmt.setInt(3, crop.getItemID());
            stmt.setInt(4, crop.getPlantedDay());
            stmt.setInt(5, crop.getGrowthTime());
            stmt.setInt(6, crop.getProduceID());
            stmt.setBoolean(7, crop.isReadyToHarvest());

            // Execute the insertion statement
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Log any SQL exceptions
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates an existing crop's information in the database.
     * @param crop the crop with updated information
     * @return true if the operation is successful
     */
    public boolean updateCrop(Crop crop) {
        String sql = "UPDATE crops SET cropname = ?, playerID = ?, itemID = ?, planted_day = ?, growth_time = ?, produceID = ?, readytoharvest = ? WHERE cropID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameters for the update
            stmt.setString(1, crop.getCropname());
            stmt.setInt(2, crop.getPlayerID());
            stmt.setInt(3, crop.getItemID());
            stmt.setInt(4, crop.getPlantedDay());
            stmt.setInt(5, crop.getGrowthTime());
            stmt.setInt(6, crop.getProduceID());
            stmt.setBoolean(7, crop.isReadyToHarvest());
            stmt.setInt(8, crop.getCropID());

            // Execute the update statement
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Log any SQL exceptions
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates the harvest status of a specific crop.
     * @param cropID the ID of the crop
     * @param ready the harvest status to set
     * @return true if the operation is successful
     */
    public boolean updateHarvestStatus(int cropID, boolean ready) {
        String sql = "UPDATE crops SET readytoharvest = ? WHERE cropID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameters for the update
            stmt.setBoolean(1, ready);
            stmt.setInt(2, cropID);

            // Execute the update statement
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Log any SQL exceptions
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a crop from the database by its ID.
     * @param cropID the ID of the crop to delete
     * @return true if the operation is successful
     */
    public boolean deleteCrop(int cropID) {
        String sql = "DELETE FROM crops WHERE cropID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameter for the deletion
            stmt.setInt(1, cropID);

            // Execute the deletion statement
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Log any SQL exceptions
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves a crop from the database by its ID.
     * @param cropID the ID of the crop to retrieve
     * @return the Crop object if found, otherwise null
     */
    public Crop getCropByID(int cropID) {
        String sql = "SELECT * FROM crops WHERE cropID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameter for the query
            stmt.setInt(1, cropID);
            ResultSet rs = stmt.executeQuery();

            // Extract and return the crop if it exists
            if (rs.next()) {
                return extractCrop(rs);
            }
        } catch (SQLException e) {
            // Log any SQL exceptions
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all crops for a given player from the database.
     * @param playerID the player's ID
     * @return a list of Crop objects
     */
    public List<Crop> getAllCropsByPlayer(int playerID) {
        List<Crop> crops = new ArrayList<>();
        String sql = "SELECT * FROM crops WHERE playerID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameter for the query
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            // Loop through the result set and add crops to the list
            while (rs.next()) {
                crops.add(extractCrop(rs));
            }
        } catch (SQLException e) {
            // Log any SQL exceptions
            e.printStackTrace();
        }
        return crops;
    }

    /**
     * Retrieves crops that are ready to be harvested for a given player.
     * @param playerID the player's ID
     * @return a list of Crop objects that are ready to harvest
     */
    public List<Crop> getReadyToHarvest(int playerID) {
        List<Crop> crops = new ArrayList<>();
        String sql = "SELECT * FROM crops WHERE playerID = ? AND readytoharvest = TRUE";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameter for the query
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            // Loop through the result set and add crops to the list
            while (rs.next()) {
                crops.add(extractCrop(rs));
            }
        } catch (SQLException e) {
            // Log any SQL exceptions
            e.printStackTrace();
        }
        return crops;
    }

    /**
     * Resets the harvest status of crops for a given player.
     * @param playerID the player's ID
     * @return true if the operation is successful
     */
    public boolean resetReadyToHarvestCrops(int playerID) {
        String sql = "UPDATE crops SET readytoharvest = FALSE WHERE playerID = ? AND readytoharvest = TRUE";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameter for the update
            stmt.setInt(1, playerID);

            // Execute the update statement
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Log any SQL exceptions
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves crops by their produce ID.
     * @param produceID the produce ID to filter crops
     * @return a list of Crop objects with the specified produce ID
     */
    public List<Crop> getCropsByProduceID(int produceID) {
        List<Crop> crops = new ArrayList<>();
        String sql = "SELECT * FROM crops WHERE produceID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameter for the query
            stmt.setInt(1, produceID);
            ResultSet rs = stmt.executeQuery();

            // Loop through the result set and add crops to the list
            while (rs.next()) {
                crops.add(extractCrop(rs));
            }
        } catch (SQLException e) {
            // Log any SQL exceptions
            e.printStackTrace();
        }
        return crops;
    }

    /**
     * Extracts a Crop object from a ResultSet.
     * @param rs the ResultSet containing crop data
     * @return a Crop object
     * @throws SQLException if an SQL error occurs
     */
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

