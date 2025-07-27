package dao;

import model.Transaction;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class TransactionDAO {

    /**
     * Inserts a transaction into the database.
     * @param transaction the transaction to be inserted
     * @return true if the transaction was inserted successfully, false otherwise
     */
    public boolean insertTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (playerID, transaction_type, itemID, quantity, unit_price, total_amount, season, day, year) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transaction.getPlayerID());
            stmt.setString(2, transaction.getTransactionType());
            stmt.setInt(3, transaction.getItemID());
            stmt.setInt(4, transaction.getQuantity());
            stmt.setInt(5, transaction.getUnitPrice());
            stmt.setInt(6, transaction.getTotalAmount());
            stmt.setString(7, transaction.getSeason());
            stmt.setInt(8, transaction.getDay());
            stmt.setInt(9, transaction.getYear());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves a list of transactions for a player.
     *
     * @param playerID the ID of the player
     * @return a list of transactions for the player
     */
    public List<Transaction> getTransactionsByPlayer(int playerID) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE playerID = ? ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToTransaction(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Retrieves a list of transactions for a player by season and year.
     *
     * @param playerID the ID of the player
     * @param season   the season
     * @param year     the year
     * @return a list of transactions for the player, season, and year
     */
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

    /**
     * Retrieves the total amount spent by a player.
     * @param playerID the ID of the player
     * @return the total amount spent by the player
     */
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

    /**
     * Retrieves the total amount earned by a player.
     * @param playerID the ID of the player
     * @return the total amount earned by the player
     */
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

    public List<Transaction> getTransactionsDetailedByPlayer(int playerID) {
        List<Transaction> list = new ArrayList<>();
        String sql = """
                        SELECT t.*, i.itemname
                        FROM transactions t
                        JOIN items i ON t.itemID = i.itemID
                        WHERE t.playerID = ?
                        ORDER BY t.timestamp DESC
                        """;

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = mapResultSetToTransaction(rs);
                transaction.setItemName(rs.getString("itemname"));
                list.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    /**
     * Maps a result set row to a Transaction object.
     * @param rs the result set to map
     * @return a Transaction object
     * @throws SQLException if an SQL exception occurs
     */
    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        return new Transaction(
            rs.getInt("transactionID"),
            rs.getInt("playerID"),
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

