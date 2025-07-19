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
        String sql = "SELECT * FROM inventory WHERE playerID = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

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
        String check = "SELECT quantity FROM inventory WHERE playerID = ? AND itemID = ?";
        String insert = "INSERT INTO inventory (playerID, itemID, quantity) VALUES (?, ?, ?)";
        String update = "UPDATE inventory SET quantity = quantity + ? WHERE playerID = ? AND itemID = ?";

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(check);
            stmt.setInt(1, playerID);
            stmt.setInt(2, itemID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                stmt = conn.prepareStatement(update);
                stmt.setInt(1, quantity);
                stmt.setInt(2, playerID);
                stmt.setInt(3, itemID);
            } else {
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

    /**
     * Removes a given item from a player's inventory.
     * @param playerID The ID of the player to remove the item from
     * @param itemID The ID of the item to remove
     * @return true if the removal was successful, false otherwise
     */
    public boolean removeItemFromInventory(int playerID, int itemID) {
        String sql = "DELETE FROM inventory WHERE playerID = ? AND itemID = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            stmt.setInt(2, itemID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
