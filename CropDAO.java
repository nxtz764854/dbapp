import java.sql.*;
import java.util.*;
import model.Crop;

public class CropDAO {

    /**
     * Retrieves all crops planted by a player from the database.
     * @param playerID The ID of the player to retrieve the crops for
     * @return A list of Crop objects, possibly empty
     */
    public List<Crop> getCropsByPlayer(int playerID) {
        List<Crop> crops = new ArrayList<>();
        String sql = "SELECT * FROM crop WHERE playerID = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                crops.add(new Crop(
                    rs.getInt("cropID"),
                    playerID,
                    rs.getString("cropname"),
                    rs.getString("status"),
                    rs.getDate("plantdate"),
                    rs.getDate("harvestdate")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return crops;
    }

    /**
     * Adds a crop to the database.
     * @param crop The Crop object containing the crop's details to be added
     * @return true if the crop was successfully added, false otherwise
     */

    public boolean addCrop(Crop crop) {
        String sql = "INSERT INTO crop (playerID, cropname, status, plantdate, harvestdate) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, crop.getPlayerID());
            stmt.setString(2, crop.getCropname());
            stmt.setString(3, crop.getStatus());
            stmt.setDate(4, crop.getPlantdate());
            stmt.setDate(5, crop.getHarvestdate());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Retrieves all crops that are ready to harvest for a player.
     * @param playerID The ID of the player to retrieve the crops for
     * @return A list of Crop objects, possibly empty
     */
    public List<Crop> getReadyToHarvest(int playerID) {
        List<Crop> ready = new ArrayList<>();
        String sql = "SELECT * FROM crop WHERE playerID = ? AND status = 'Ready to Harvest'";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ready.add(new Crop(
                    rs.getInt("cropID"),
                    playerID,
                    rs.getString("cropname"),
                    rs.getString("status"),
                    rs.getDate("plantdate"),
                    rs.getDate("harvestdate")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ready;
    }

    /**
     * Updates the status of a crop in the database.
     * @param cropID The ID of the crop to update
     * @param newStatus The new status to set for the crop
     * @return true if the update was successful, false otherwise
     */
    public boolean updateStatus(int cropID, String newStatus) {
        String sql = "UPDATE crop SET status = ? WHERE cropID = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, cropID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
