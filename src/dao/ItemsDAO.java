package dao;

import model.Item;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemsDAO {
    /**
     * Adds a given item to the database.
     * @param item The item to add
     * @return true if the addition was successful, false otherwise
     */
    public boolean addItem(Item item) {
        String query = "INSERT INTO items (itemname, itemtype, specialvalue, desc, quantity) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, item.getItemname());
            stmt.setString(2, item.getItemtype());
            stmt.setInt(3, item.getSpecialvalue());
            stmt.setString(4, item.getDesc());
            stmt.setInt(5, item.getQuantity());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates the details of an existing item in the database.
     * 
     * @param item The item object containing updated information. The item must have a valid itemID.
     * @return true if the update was successful, false otherwise
     */
    public boolean updateItem(Item item) {
        String query = "UPDATE items SET itemname = ?, itemtype = ?, specialvalue = ?, desc = ?, quantity = ? WHERE itemID = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, item.getItemname());
            stmt.setString(2, item.getItemtype());
            stmt.setInt(3, item.getSpecialvalue());
            stmt.setString(4, item.getDesc());
            stmt.setInt(5, item.getQuantity());
            stmt.setInt(6, item.getItemID());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes an item from the database.
     * @param itemID The ID of the item to delete
     * @return true if the deletion was successful, false otherwise
     */
    public boolean deleteItem(int itemID) {
        String query = "DELETE FROM items WHERE itemID = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, itemID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    /**
     * Retrieves an item from the database by its ID.
     * @param itemID The ID of the item to retrieve
     * @return The item if found, null otherwise
     */
    public Item getItemByID(int itemID) {
        String query = "SELECT * FROM items WHERE itemID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, itemID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Item(
                    rs.getInt("itemID"),
                    rs.getString("itemname"),
                    rs.getString("itemtype"),
                    rs.getInt("specialvalue"),
                    rs.getString("desc"),
                    rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all items from the database.
     * 
     * @return A list of Items objects containing all items from the database,
     *         possibly empty if no items are found.
     */
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                items.add(new Item(
                    rs.getInt("itemID"),
                    rs.getString("itemname"),
                    rs.getString("itemtype"),
                    rs.getInt("specialvalue"),
                    rs.getString("desc"),
                    rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    /**
     * Updates the quantity of an item in the database.
     * @param itemID The ID of the item to update
     * @param newQuantity The new quantity value to set
     * @return true if the update was successful, false otherwise
     */
    public boolean updateQuantity(int itemID, int newQuantity) {
        String query = "UPDATE items SET quantity = ? WHERE itemID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, newQuantity);
            stmt.setInt(2, itemID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves all items from the database with the given item type.
     * @param itemType The item type to filter by
     * @return A list of Items objects containing all items of the given type,
     *         possibly empty if no items are found.
     */
    public List<Item> getItemsByType(String itemType) {
        List<Item> filtered = new ArrayList<>();
        String query = "SELECT * FROM items WHERE itemtype = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, itemType);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                filtered.add(new Item(
                    rs.getInt("itemID"),
                    rs.getString("itemname"),
                    rs.getString("itemtype"),
                    rs.getInt("specialvalue"),
                    rs.getString("desc"),
                    rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filtered;
    }

    /**
     * Retrieves an item from the database by its name.
     * @param itemname The name of the item to retrieve
     * @return The item if found, null otherwise
     */
    public Item getItemByName(String itemname) {
    String query = "SELECT * FROM items WHERE itemname = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, itemname);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Item(
                rs.getInt("itemID"),
                rs.getString("itemname"),
                rs.getString("itemtype"),
                rs.getInt("specialvalue"),
                rs.getString("desc"),
                rs.getInt("quantity")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

}
