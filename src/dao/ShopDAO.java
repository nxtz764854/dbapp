package dao;

import model.Shop;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShopDAO {

    public boolean addShop(Shop shop) {
        String sql = "INSERT INTO shops (shopname, owner_npcID) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, shop.getShopname());
            stmt.setInt(2, shop.getOwnerNpcID());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateShop(Shop shop) {
        String sql = "UPDATE shops SET shopname = ?, owner_npcID = ? WHERE shopID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, shop.getShopname());
            stmt.setInt(2, shop.getOwnerNpcID());
            stmt.setInt(3, shop.getShopID());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteShop(int shopID) {
        String sql = "DELETE FROM shops WHERE shopID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shopID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Shop getShopByID(int shopID) {
        String sql = "SELECT * FROM shops WHERE shopID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shopID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractShop(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Shop getShopByName(String shopname) {
        String sql = "SELECT * FROM shops WHERE shopname = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, shopname);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractShop(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Shop> getAllShops() {
        List<Shop> shops = new ArrayList<>();
        String sql = "SELECT * FROM shops";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                shops.add(extractShop(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shops;
    }

    private Shop extractShop(ResultSet rs) throws SQLException {
        Shop shop = new Shop();
        shop.setShopID(rs.getInt("shopID"));
        shop.setShopname(rs.getString("shopname"));
        shop.setOwnerNpcID(rs.getInt("owner_npcID"));
        return shop;
    }
}
