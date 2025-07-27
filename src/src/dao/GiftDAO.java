package dao;

import model.GiftLog;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GiftDAO {

    /**
     * Inserts a new gift log into the database.
     * @param log the GiftLog to be inserted
     * @return true if the insertion was successful, false otherwise
     */
    public boolean insertGiftLog(GiftLog log) {
        // Insert a new gift log into the database
        String sql = "INSERT INTO gift_logs (playerID, npcID, itemID, season, day, year) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            stmt.setInt(1, log.getPlayerID());
            stmt.setInt(2, log.getNpcID());
            stmt.setInt(3, log.getItemID());
            stmt.setString(4, log.getSeason());
            stmt.setInt(5, log.getDay());
            stmt.setInt(6, log.getYear());

            // Execute the statement and return true if successful
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves all gift logs for a player from the database.
     * @param playerID the player ID
     * @return a list of GiftLogs for the player
     */
    public List<GiftLog> getGiftLogsByPlayer(int playerID) {
        // Retrieve all gift logs for a player from the database
        List<GiftLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM gift_logs WHERE playerID = ? ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                logs.add(mapResultSetToGiftLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    /**
     * Retrieves all gift logs for a player and specific NPC from the database.
     * @param playerID the player ID
     * @param npcID the NPC ID
     * @return a list of GiftLogs for the player and NPC
     */
    public List<GiftLog> getGiftLogsByPlayerAndNPC(int playerID, int npcID) {
        // Retrieve all gift logs for a player and specific NPC from the database
        List<GiftLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM gift_logs WHERE playerID = ? AND npcID = ? ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            stmt.setInt(2, npcID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                logs.add(mapResultSetToGiftLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    /**
     * Retrieves the latest gift log for a player and specific NPC from the database.
     * @param playerID the player ID
     * @param npcID the NPC ID
     * @return the latest GiftLog for the player and NPC
     */
    public GiftLog getLatestGiftLog(int playerID, int npcID) {
        // Retrieve the latest gift log for a player and specific NPC from the database
        String sql = "SELECT * FROM gift_logs WHERE playerID = ? AND npcID = ? ORDER BY timestamp DESC LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            stmt.setInt(2, npcID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToGiftLog(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Counts the number of gifts given to an NPC by a player in a given week.
     * @param playerID the player ID
     * @param npcID the NPC ID
     * @param year the year
     * @param weekNumber the week number
     * @return the number of gifts given
     */
    public int countGiftsGivenThisWeek(int playerID, int npcID, int year, int weekNumber) {
        // Count the number of gifts given to an NPC by a player in a given week
        String sql = "SELECT COUNT(*) FROM gift_logs WHERE playerID = ? AND npcID = ? AND YEAR(timestamp) = ? AND WEEK(timestamp, 1) = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            stmt.setInt(2, npcID);
            stmt.setInt(3, year);
            stmt.setInt(4, weekNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Retrieves all gift logs for a player and specific NPC in a given week.
     * @param playerID the player ID
     * @param npcID the NPC ID
     * @param year the year
     * @param weekNumber the week number
     * @return a list of GiftLogs for the player and NPC
     */
    public List<GiftLog> getGiftLogsForWeek(int playerID, int npcID, int year, int weekNumber) {
        // Retrieve all gift logs for a player and specific NPC in a given week
        List<GiftLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM gift_logs WHERE playerID = ? AND npcID = ? AND YEAR(timestamp) = ? AND WEEK(timestamp, 1) = ? ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            stmt.setInt(2, npcID);
            stmt.setInt(3, year);
            stmt.setInt(4, weekNumber);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                logs.add(mapResultSetToGiftLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    public List<GiftLog> getGiftLogsDetailedByPlayer(int playerID) {
        List<GiftLog> logs = new ArrayList<>();
        String sql = """
            SELECT gl.*, i.itemname, n.npcname
            FROM gift_logs gl
            JOIN items i ON gl.itemID = i.itemID
            JOIN npcs n ON gl.npcID = n.npcID
            WHERE gl.playerID = ?
            ORDER BY gl.timestamp DESC
            """;

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                GiftLog log = mapResultSetToGiftLog(rs);
                log.setItemName(rs.getString("itemname"));
                log.setNpcName(rs.getString("npcname"));
                logs.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    /**
     * Maps a ResultSet to a GiftLog object.
     * @param rs the ResultSet
     * @return a GiftLog object
     * @throws SQLException if there is an error
     */
    private GiftLog mapResultSetToGiftLog(ResultSet rs) throws SQLException {
        return new GiftLog(
            rs.getInt("giftID"),
            rs.getInt("playerID"),
            rs.getInt("npcID"),
            rs.getInt("itemID"),
            rs.getString("season"),
            rs.getInt("day"),
            rs.getInt("year"),
            rs.getTimestamp("timestamp")
        );
    }
}

