import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemsDAO {
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
}
