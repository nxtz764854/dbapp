package dao;

import model.ProductLog;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public boolean insertProductLog(ProductLog log) {
        String sql = "INSERT INTO product_logs (playerID, animalID, itemID, quantity, season, day, year) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, log.getPlayerID());
            stmt.setInt(2, log.getAnimalID());
            stmt.setInt(3, log.getItemID());
            stmt.setInt(4, log.getQuantity());
            stmt.setString(5, log.getSeason());
            stmt.setInt(6, log.getDay());
            stmt.setInt(7, log.getYear());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<ProductLog> getProductLogsByPlayer(int playerID) {
        List<ProductLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM product_logs WHERE playerID = ? ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                logs.add(mapResultSetToProductLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    public List<ProductLog> getProductLogsBySeasonAndYear(int playerID, String season, int year) {
        List<ProductLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM product_logs WHERE playerID = ? AND season = ? AND year = ? ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            stmt.setString(2, season);
            stmt.setInt(3, year);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                logs.add(mapResultSetToProductLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    public int getTotalProducedQuantityByItem(int playerID, int itemID) {
        String sql = "SELECT SUM(quantity) FROM product_logs WHERE playerID = ? AND itemID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            stmt.setInt(2, itemID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

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