package dao;

import model.ShopInventory;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShopInventoryDAO {

    public boolean addItemToShop(ShopInventory shopInventory) {
        String sql = "INSERT INTO shop_inventory (shopID, itemID, price) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shopInventory.getShopID());
            stmt.setInt(2, shopInventory.getItemID());
            stmt.setInt(3, shopInventory.getPrice());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateItemPrice(int shopID, int itemID, int newPrice) {
        String sql = "UPDATE shop_inventory SET price = ? WHERE shopID = ? AND itemID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newPrice);
            stmt.setInt(2, shopID);
            stmt.setInt(3, itemID);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeItemFromShop(int shopID, int itemID) {
        String sql = "DELETE FROM shop_inventory WHERE shopID = ? AND itemID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shopID);
            stmt.setInt(2, itemID);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ShopInventory getItemFromShop(int shopID, int itemID) {
        String sql = "SELECT * FROM shop_inventory WHERE shopID = ? AND itemID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shopID);
            stmt.setInt(2, itemID);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractShopInventory(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ShopInventory> getItemsByShop(int shopID) {
        String sql = "SELECT * FROM shop_inventory WHERE shopID = ?";
        List<ShopInventory> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shopID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(extractShopInventory(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private ShopInventory extractShopInventory(ResultSet rs) throws SQLException {
        ShopInventory si = new ShopInventory();
        si.setShopID(rs.getInt("shopID"));
        si.setItemID(rs.getInt("itemID"));
        si.setPrice(rs.getInt("price"));
        return si;
    }
}
