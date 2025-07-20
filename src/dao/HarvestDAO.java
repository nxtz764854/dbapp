package dao;

import model.HarvestLog;
import util.DBConnection;

import java.sql.*;
import java.util.*;

public class HarvestDAO {

    /**
     * Inserts a new HarvestLog entry into the database.
     * @param log the HarvestLog to be inserted
     * @return true if the insertion was successful, false otherwise
     */
    public boolean insertHarvestLog(HarvestLog log) {
        // SQL query to insert a new harvest log
        String sql = "INSERT INTO harvest_logs (playerID, cropID, itemID, quantity, season, day, year) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            stmt.setInt(1, log.getPlayerID());
            stmt.setInt(2, log.getCropID());
            stmt.setInt(3, log.getItemID());
            stmt.setInt(4, log.getQuantity());
            stmt.setString(5, log.getSeason());
            stmt.setInt(6, log.getDay());
            stmt.setInt(7, log.getYear());

            // Execute the statement and return true if successful
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves all harvest logs for a specific player.
     * @param playerID the player's ID
     * @return a list of HarvestLogs for the player
     */
    public List<HarvestLog> getHarvestLogsByPlayer(int playerID) {
        List<HarvestLog> logs = new ArrayList<>();
        // SQL query to fetch harvest logs for a specific player
        String sql = "SELECT * FROM harvest_logs WHERE playerID = ? ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            // Map each result set row to a HarvestLog object
            while (rs.next()) {
                logs.add(new HarvestLog(
                    rs.getInt("harvestID"),
                    rs.getInt("playerID"),
                    rs.getInt("cropID"),
                    rs.getInt("itemID"),
                    rs.getInt("quantity"),
                    rs.getString("season"),
                    rs.getInt("day"),
                    rs.getInt("year"),
                    rs.getTimestamp("timestamp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    /**
     * Retrieves harvest logs for a player by season and year.
     * @param playerID the player's ID
     * @param season the season
     * @param year the year
     * @return a list of HarvestLogs for the player, season, and year
     */
    public List<HarvestLog> getHarvestLogsBySeasonAndYear(int playerID, String season, int year) {
        List<HarvestLog> logs = new ArrayList<>();
        // SQL query to fetch harvest logs for a player by season and year
        String sql = "SELECT * FROM harvest_logs WHERE playerID = ? AND season = ? AND year = ? ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            stmt.setString(2, season);
            stmt.setInt(3, year);

            ResultSet rs = stmt.executeQuery();

            // Map each result set row to a HarvestLog object
            while (rs.next()) {
                logs.add(mapResultSetToHarvestLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    /**
     * Retrieves the total quantity of a specific item harvested by a player.
     * @param playerID the player's ID
     * @param itemID the item's ID
     * @return the total harvested quantity of the item
     */
    public int getTotalHarvestedQuantityByItem(int playerID, int itemID) {
        // SQL query to calculate the total harvested quantity of an item
        String sql = "SELECT SUM(quantity) FROM harvest_logs WHERE playerID = ? AND itemID = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            stmt.setInt(2, itemID);
            ResultSet rs = stmt.executeQuery();

            // Return the sum of the quantities
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Maps a result set row to a HarvestLog object.
     * @param rs the ResultSet to map
     * @return a HarvestLog object
     * @throws SQLException if a database access error occurs
     */
    private HarvestLog mapResultSetToHarvestLog(ResultSet rs) throws SQLException {
        return new HarvestLog(
            rs.getInt("harvestID"),
            rs.getInt("playerID"),
            rs.getInt("cropID"),
            rs.getInt("itemID"),
            rs.getInt("quantity"),
            rs.getString("season"),
            rs.getInt("day"),
            rs.getInt("year"),
            rs.getTimestamp("timestamp")
        );
    }
}

