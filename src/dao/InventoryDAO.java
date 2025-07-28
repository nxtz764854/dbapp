package dao;

import model.Inventory;
import util.DBConnection;

import java.sql.*;
import java.util.*;

public class InventoryDAO {

    /**
     * Retrieves the inventory of a player by player ID.
     * @param playerID The ID of the player to retrieve the inventory from
     * @return A list of Inventory objects containing all items in the player's inventory
     */
    public List<Inventory> getInventoryByPlayerID(int playerID) {
        List<Inventory> list = new ArrayList<>();
        String sql = "SELECT * FROM inventories WHERE playerID = ?";

        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            // Iterate over result set and create Inventory objects
            while (rs.next()) {
                list.add(new Inventory(playerID, rs.getInt("itemID"), rs.getInt("quantity")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Adds a given quantity of an item to a player's inventory.
     * @param playerID The ID of the player to add the item to
     * @param itemID The ID of the item to add
     * @param quantity The quantity of the item to add
     * @return true if the addition was successful, false otherwise
     */
    public boolean addItemToInventory(int playerID, int itemID, int quantity) {
        String check = "SELECT quantity FROM inventories WHERE playerID = ? AND itemID = ?";
        String insert = "INSERT INTO inventories (playerID, itemID, quantity) VALUES (?, ?, ?)";
        String update = "UPDATE inventories SET quantity = quantity + ? WHERE playerID = ? AND itemID = ?";

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(check);
            stmt.setInt(1, playerID);
            stmt.setInt(2, itemID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Update existing item quantity
                stmt = conn.prepareStatement(update);
                stmt.setInt(1, quantity);
                stmt.setInt(2, playerID);
                stmt.setInt(3, itemID);
            } else {
                // Insert new item into inventory
                stmt = conn.prepareStatement(insert);
                stmt.setInt(1, playerID);
                stmt.setInt(2, itemID);
                stmt.setInt(3, quantity);
            }

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<String[]> getDetailedInventory() {
        List<String[]> data = new ArrayList<>();
        String sql = """
            SELECT p.playerID, p.playername, i.itemID, i.itemname, inv.quantity
            FROM inventories inv
            JOIN players p ON inv.playerID = p.playerID
            JOIN items i ON inv.itemID = i.itemID
            ORDER BY p.playerID, i.itemname
        """;

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                data.add(new String[]{
                    String.valueOf(rs.getInt("playerID")),
                    rs.getString("playername"),
                    String.valueOf(rs.getInt("itemID")),
                    rs.getString("itemname"),
                    String.valueOf(rs.getInt("quantity"))
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }


    /**
     * Removes a given item from a player's inventory.
     * @param playerID The ID of the player to remove the item from
     * @param itemID The ID of the item to remove
     * @param quantity The quantity of the item to remove
     * @return true if the removal was successful, false otherwise
     */
    public boolean removeItemFromInventory(int playerID, int itemID, int quantity) {
        String check = "SELECT quantity FROM inventories WHERE playerID = ? AND itemID = ?";
        String delete = "DELETE FROM inventories WHERE playerID = ? AND itemID = ?";
        String update = "UPDATE inventories SET quantity = quantity - ? WHERE playerID = ? AND itemID = ?";

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(check);
            stmt.setInt(1, playerID);
            stmt.setInt(2, itemID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int currentQuantity = rs.getInt("quantity");
                if (currentQuantity <= quantity) {
                    // Delete the item if the current quantity is less than or equal to the removal quantity
                    stmt = conn.prepareStatement(delete);
                    stmt.setInt(1, playerID);
                    stmt.setInt(2, itemID);
                } else {
                    // Update the item quantity
                    stmt = conn.prepareStatement(update);
                    stmt.setInt(1, quantity);
                    stmt.setInt(2, playerID);
                    stmt.setInt(3, itemID);
                }
            }

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}

