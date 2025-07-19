import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FarmDAO {
    private Connection conn;

    public FarmDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new farm into the database.
     * @param farm The farm to insert, which must have valid playerID, cropID, and animalID fields.
     * @return true if the insertion was successful, false otherwise
     * @throws SQLException If there is a problem with the database
     */
    public boolean insertFarm(Farm farm) throws SQLException {
        String sql = "INSERT INTO farm (playerID, cropID, animalID) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, farm.getPlayerID());
            stmt.setInt(2, farm.getCropID());
            stmt.setInt(3, farm.getAnimalID());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Updates an existing farm in the database.
     * @param farm The farm to update, which must have valid playerID, cropID, and animalID fields.
     * @return true if the update was successful, false otherwise
     * @throws SQLException If there is a problem with the database
     */
    public boolean updateFarm(Farm farm) throws SQLException {
        String sql = "UPDATE farm SET cropID = ?, animalID = ? WHERE playerID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, farm.getCropID());
            stmt.setInt(2, farm.getAnimalID());
            stmt.setInt(3, farm.getPlayerID());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a farm from the database by player ID.
     * @param playerID The ID of the player whose farm to delete
     * @return true if the deletion was successful, false otherwise
     * @throws SQLException If there is a problem with the database
     */
    public boolean deleteFarm(int playerID) throws SQLException {
        String sql = "DELETE FROM farm WHERE playerID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Retrieves a farm from the database by player ID.
     * @param playerID The ID of the player whose farm to retrieve
     * @return The Farm object if found, null otherwise
     * @throws SQLException If there is a problem with the database
     */
    public Farm getFarmByPlayerID(int playerID) throws SQLException {
        String sql = "SELECT * FROM farm WHERE playerID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Farm farm = new Farm();
                farm.setPlayerID(rs.getInt("playerID"));
                farm.setCropID(rs.getInt("cropID"));
                farm.setAnimalID(rs.getInt("animalID"));
                return farm;
            }
        }
        return null;
    }

    /**
     * Retrieves all farms from the database.
     * @return A list of Farm objects, possibly empty if no farms are found.
     * @throws SQLException If there is a problem with the database.
     */
    public List<Farm> getAllFarms() throws SQLException {
        List<Farm> farms = new ArrayList<>();
        String sql = "SELECT * FROM farm";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Farm farm = new Farm();
                farm.setPlayerID(rs.getInt("playerID"));
                farm.setCropID(rs.getInt("cropID"));
                farm.setAnimalID(rs.getInt("animalID"));
                farms.add(farm);
            }
        }
        return farms;
    }
}
