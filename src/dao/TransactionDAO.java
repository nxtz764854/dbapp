package dao;

import model.Transaction;
import util.DBConnection;

import java.sql.*;
import java.util.*;

public class TransactionDAO {

    public boolean insertTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (playerID, shopID, transaction_type, itemID, quantity, unit_price, total_amount, season, day, year) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transaction.getPlayerID());
            stmt.setInt(2, transaction.getShopID());
            stmt.setString(3, transaction.getTransactionType());
            stmt.setInt(4, transaction.getItemID());
            stmt.setInt(5, transaction.getQuantity());
            stmt.setInt(6, transaction.getUnitPrice());
            stmt.setInt(7, transaction.getTotalAmount());
            stmt.setString(8, transaction.getSeason());
            stmt.setInt(9, transaction.getDay());
            stmt.setInt(10, transaction.getYear());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Transaction> getTransactionsByPlayer(int playerID) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE playerID = ? ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Transaction(
                    rs.getInt("transactionID"),
                    rs.getInt("playerID"),
                    rs.getInt("shopID"),
                    rs.getString("transaction_type"),
                    rs.getInt("itemID"),
                    rs.getInt("quantity"),
                    rs.getInt("unit_price"),
                    rs.getInt("total_amount"),
                    rs.getString("season"),
                    rs.getInt("day"),
                    rs.getInt("year"),
                    rs.getTimestamp("timestamp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Transaction> getTransactionsBySeasonAndYear(int playerID, String season, int year) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE playerID = ? AND season = ? AND year = ? ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            stmt.setString(2, season);
            stmt.setInt(3, year);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public int getTotalSpentByPlayer(int playerID) {
        String sql = "SELECT SUM(total_amount) FROM transactions WHERE playerID = ? AND transaction_type = 'buy'";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalEarnedByPlayer(int playerID) {
        String sql = "SELECT SUM(total_amount) FROM transactions WHERE playerID = ? AND transaction_type = 'sell'";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        return new Transaction(
            rs.getInt("transactionID"),
            rs.getInt("playerID"),
            rs.getInt("shopID"),
            rs.getString("transaction_type"),
            rs.getInt("itemID"),
            rs.getInt("quantity"),
            rs.getInt("unit_price"),
            rs.getInt("total_amount"),
            rs.getString("season"),
            rs.getInt("day"),
            rs.getInt("year"),
            rs.getTimestamp("timestamp")
        );
    }
}
