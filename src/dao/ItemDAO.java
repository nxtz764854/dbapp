package dao;

import model.Item;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    /**
     * Adds an item to the database.
     * @param item the item to add
     * @return true if the operation is successful
     */
    public boolean addItem(Item item) {
        // SQL statement to insert an item into the database
        String sql = "INSERT INTO items (itemname, itemtype, descript, price, buyable) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the values of the statement
            stmt.setString(1, item.getItemname());
            stmt.setString(2, item.getItemtype());
            stmt.setString(3, item.getDescript());
            stmt.setInt(4, item.getPrice());
            stmt.setBoolean(5, item.isBuyable());

            // Execute the statement and return the result
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates an item in the database.
     * @param item the item to update
     * @return true if the operation is successful
     */
    public boolean updateItem(Item item) {
        // SQL statement to update an item in the database
        String sql = "UPDATE items SET itemname = ?, itemtype = ?, descript = ?, price = ?, buyable = ? WHERE itemID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the values of the statement
            stmt.setString(1, item.getItemname());
            stmt.setString(2, item.getItemtype());
            stmt.setString(3, item.getDescript());
            stmt.setInt(4, item.getPrice());
            stmt.setBoolean(5, item.isBuyable());
            stmt.setInt(6, item.getItemID());

            // Execute the statement and return the result
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes an item from the database.
     * @param itemID the ID of the item to delete
     * @return true if the operation is successful
     */
    public boolean deleteItem(int itemID) {
        // SQL statement to delete an item from the database
        String sql = "DELETE FROM items WHERE itemID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the value of the statement
            stmt.setInt(1, itemID);

            // Execute the statement and return the result
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves an item from the database by ID.
     * @param itemID the ID of the item to retrieve
     * @return the item if found, null otherwise
     */
    public Item getItemByID(int itemID) {
        // SQL statement to retrieve an item from the database by ID
        String sql = "SELECT * FROM items WHERE itemID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the value of the statement
            stmt.setInt(1, itemID);

            // Execute the statement and return the result
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractItem(rs);
            }
        } catch (SQLException e) {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves an item from the database by name.
     * @param itemname the name of the item to retrieve
     * @return the item if found, null otherwise
     */
    public Item getItemByName(String itemname) {
        // SQL statement to retrieve an item from the database by name
        String sql = "SELECT * FROM items WHERE itemname = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the value of the statement
            stmt.setString(1, itemname);

            // Execute the statement and return the result
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractItem(rs);
            }
        } catch (SQLException e) {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all items from the database by type.
     * @param itemType the type of items to retrieve
     * @return a list of items if found, an empty list otherwise
     */
    public List<Item> getItemsByType(String itemType) {
        List<Item> items = new ArrayList<>();
        // SQL statement to retrieve all items from the database by type
        String sql = "SELECT * FROM items WHERE itemtype = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the value of the statement
            stmt.setString(1, itemType);

            // Execute the statement and return the result
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                items.add(extractItem(rs));
            }
        } catch (SQLException e) {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        return items;
    }

    /**
     * Retrieves all items from the database.
     * @return a list of items if found, an empty list otherwise
     */
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        // SQL statement to retrieve all items from the database
        String sql = "SELECT * FROM items";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Execute the statement and return the result
            while (rs.next()) {
                items.add(extractItem(rs));
            }
        } catch (SQLException e) {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        return items;
    }

    /**
     * Retrieves all buyable items from the database.
     * @return a list of items if found, an empty list otherwise
     */
    public List<Item> getBuyableItems() {
        List<Item> items = new ArrayList<>();
        // SQL statement to retrieve all buyable items from the database
        String sql = "SELECT * FROM items WHERE buyable = TRUE";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            // Execute the statement and return the result
            while (rs.next()) {
                items.add(extractItem(rs));
            }
        } catch (SQLException e) {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
        return items;
    }


    /**
     * Extracts an item from the result set.
     * @param rs the result set to extract from
     * @return the extracted item
     * @throws SQLException if a database access error occurs
     */
    private Item extractItem(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setItemID(rs.getInt("itemID"));
        item.setItemname(rs.getString("itemname"));
        item.setItemtype(rs.getString("itemtype"));
        item.setDescript(rs.getString("descript"));
        item.setPrice(rs.getInt("price"));
        item.setBuyable(rs.getBoolean("buyable"));
        return item;
    }
}

