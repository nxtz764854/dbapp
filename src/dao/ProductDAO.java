package dao;

import model.ProductLog;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    /**
     * Inserts a new product log into the database.
     * @param log the ProductLog to be inserted
     * @return true if the insertion was successful, false otherwise
     */
    public boolean insertProductLog(ProductLog log) {
        String sql = "INSERT INTO product_logs (playerID, animalID, itemID, quantity, season, day, year) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Establish a connection and prepare the SQL statement
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            stmt.setInt(1, log.getPlayerID());
            stmt.setInt(2, log.getAnimalID());
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
     * Retrieves all product logs for a specific player.
     * @param playerID the player's ID
     * @return a list of ProductLogs for the player
     */
    public List<ProductLog> getProductLogsByPlayer(int playerID) {
        List<ProductLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM product_logs WHERE playerID = ? ORDER BY timestamp DESC";

        // Establish a connection and prepare the SQL statement
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            // Map each result set row to a ProductLog object
            while (rs.next()) {
                logs.add(mapResultSetToProductLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    /**
     * Retrieves product logs for a player by season and year.
     * @param playerID the player's ID
     * @param season   the season
     * @param year     the year
     * @return a list of ProductLogs for the player, season, and year
     */
    public List<ProductLog> getProductLogsBySeasonAndYear(int playerID, String season, int year) {
        List<ProductLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM product_logs WHERE playerID = ? AND season = ? AND year = ? ORDER BY timestamp DESC";

        // Establish a connection and prepare the SQL statement
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            stmt.setString(2, season);
            stmt.setInt(3, year);
            ResultSet rs = stmt.executeQuery();

            // Map each result set row to a ProductLog object
            while (rs.next()) {
                logs.add(mapResultSetToProductLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    /**
     * Retrieves the total quantity of a specific item produced by a player.
     * @param playerID the player's ID
     * @param itemID   the item's ID
     * @return the total produced quantity of the item
     */
    public int getTotalProducedQuantityByItem(int playerID, int itemID) {
        String sql = "SELECT SUM(quantity) FROM product_logs WHERE playerID = ? AND itemID = ?";

        // Establish a connection and prepare the SQL statement
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            stmt.setInt(2, itemID);
            ResultSet rs = stmt.executeQuery();

            // Return the total quantity if the result set is not empty
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Maps a result set row to a ProductLog object.
     * @param rs the ResultSet to map
     * @return a ProductLog object
     * @throws SQLException if a database access error occurs
     */
    private ProductLog mapResultSetToProductLog(ResultSet rs) throws SQLException {
        return new ProductLog(
            rs.getInt("productlogID"),
            rs.getInt("playerID"),
            rs.getInt("animalID"),
            rs.getInt("itemID"),
            rs.getInt("quantity"),
            rs.getString("season"),
            rs.getInt("day"),
            rs.getInt("year"),
            rs.getTimestamp("timestamp")
        );
    }
}

