package dao;

import model.Item;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemsDAO {

    public boolean addItem(Item item) {
        String sql = "INSERT INTO items (itemname, itemtype, descript) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getItemname());
            stmt.setString(2, item.getItemtype());
            stmt.setString(3, item.getDescript());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateItem(Item item) {
        String sql = "UPDATE items SET itemname = ?, itemtype = ?, descript = ? WHERE itemID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getItemname());
            stmt.setString(2, item.getItemtype());
            stmt.setString(3, item.getDescript());
            stmt.setInt(4, item.getItemID());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteItem(int itemID) {
        String sql = "DELETE FROM items WHERE itemID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, itemID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Item getItemByID(int itemID) {
        String sql = "SELECT * FROM items WHERE itemID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, itemID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractItem(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Item getItemByName(String itemname) {
        String sql = "SELECT * FROM items WHERE itemname = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, itemname);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractItem(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Item> getItemsByType(String itemType) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE itemtype = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, itemType);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                items.add(extractItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                items.add(extractItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    private Item extractItem(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setItemID(rs.getInt("itemID"));
        item.setItemname(rs.getString("itemname"));
        item.setItemtype(rs.getString("itemtype"));
        item.setDescript(rs.getString("descript"));
        return item;
    }
}
