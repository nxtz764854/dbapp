package dao;

import model.Relation;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelationsDAO {

    /**
     * Inserts a new relation into the database.
     * @param relation the Relation object to be inserted
     * @return true if the insertion was successful, false otherwise
     */
    public boolean insertRelation(Relation relation) {
        String sql = "INSERT INTO relations (playerID, npcID, npchearts, last_gift_day, gift_count_this_week) VALUES (?, ?, ?, ?, ?)";

        // Establish a connection and prepare the SQL statement
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            stmt.setInt(1, relation.getPlayerID());
            stmt.setInt(2, relation.getNpcID());
            stmt.setInt(3, relation.getNpchearts());
            stmt.setInt(4, relation.getLastGiftDay());
            stmt.setInt(5, relation.getGiftCountThisWeek());

            // Execute the statement and return true if successful
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates an existing relation in the database.
     * @param relation the Relation object with updated values
     * @return true if the update was successful, false otherwise
     */
    public boolean updateRelation(Relation relation) {
        String sql = "UPDATE relations SET npchearts = ?, last_gift_day = ?, gift_count_this_week = ? WHERE playerID = ? AND npcID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            stmt.setInt(1, relation.getNpchearts());
            stmt.setInt(2, relation.getLastGiftDay());
            stmt.setInt(3, relation.getGiftCountThisWeek());
            stmt.setInt(4, relation.getPlayerID());
            stmt.setInt(5, relation.getNpcID());

            // Execute the statement and return true if successful
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a relation from the database.
     * @param playerID the player ID
     * @param npcID the NPC ID
     * @return true if the deletion was successful, false otherwise
     */
    public boolean deleteRelation(int playerID, int npcID) {
        String sql = "DELETE FROM relations WHERE playerID = ? AND npcID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            stmt.setInt(1, playerID);
            stmt.setInt(2, npcID);

            // Execute the statement and return true if successful
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves a specific relation from the database.
     * @param playerID the player ID
     * @param npcID the NPC ID
     * @return the Relation object if found, null otherwise
     */
    public Relation getRelation(int playerID, int npcID) {
        String sql = "SELECT * FROM relations WHERE playerID = ? AND npcID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            stmt.setInt(1, playerID);
            stmt.setInt(2, npcID);
            ResultSet rs = stmt.executeQuery();

            // Map the result set to a Relation object
            if (rs.next()) {
                return mapResultSetToRelation(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all relations for a specific player.
     * @param playerID the player ID
     * @return a list of Relation objects for the player
     */
    public List<Relation> getRelationsByPlayer(int playerID) {
        List<Relation> relations = new ArrayList<>();
        String sql = "SELECT * FROM relations WHERE playerID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            // Map each result to a Relation object
            while (rs.next()) {
                relations.add(mapResultSetToRelation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relations;
    }

    /**
     * Maps a ResultSet to a Relation object.
     * @param rs the ResultSet to map from
     * @return a Relation object
     * @throws SQLException if there is an error during mapping
     */
    private Relation mapResultSetToRelation(ResultSet rs) throws SQLException {
        return new Relation(
            rs.getInt("playerID"),
            rs.getInt("npcID"),
            rs.getInt("npchearts"),
            rs.getInt("last_gift_day"),
            rs.getInt("gift_count_this_week")
        );
    }

    /**
     * Updates the gift statistics for a relation.
     * @param playerID the player ID
     * @param npcID the NPC ID
     * @param day the day of the last gift
     * @param count the count of gifts this week
     * @return true if the update was successful, false otherwise
     */
    public boolean updateGiftStats(int playerID, int npcID, int day, int count) {
        String sql = "UPDATE relations SET last_gift_day = ?, gift_count_this_week = ? WHERE playerID = ? AND npcID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            stmt.setInt(1, day);
            stmt.setInt(2, count);
            stmt.setInt(3, playerID);
            stmt.setInt(4, npcID);

            // Execute the statement and return true if successful
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates the heart count for a relation.
     * @param playerID the player ID
     * @param npcID the NPC ID
     * @param newHearts the new heart count
     * @return true if the update was successful, false otherwise
     */
    public boolean updateHearts(int playerID, int npcID, int newHearts) {
        String sql = "UPDATE relations SET npchearts = ? WHERE playerID = ? AND npcID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            stmt.setInt(1, newHearts);
            stmt.setInt(2, playerID);
            stmt.setInt(3, npcID);

            // Execute the statement and return true if successful
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Increments the heart count for a relation.
     * @param playerID the player ID
     * @param npcID the NPC ID
     * @param amount the amount to increment
     * @return true if the update was successful, false otherwise
     */
    public boolean incrementHearts(int playerID, int npcID, int amount) {
        String sql = "UPDATE relations SET npchearts = npchearts + ? WHERE playerID = ? AND npcID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            stmt.setInt(1, amount);
            stmt.setInt(2, playerID);
            stmt.setInt(3, npcID);

            // Execute the statement and return true if successful
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

