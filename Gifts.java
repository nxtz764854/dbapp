import java.sql.*;
import java.util.Scanner;

public class Gifts {
     public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Get connection
            conn = DBConnection.getConnection();
            System.out.println("Connected to database.");

            // Get the playerID
            int playerID = -1;
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT playerID FROM player LIMIT 1");
            if (rs.next()) {
                playerID = rs.getInt("playerID");
            }
            rs.close();
            stmt.close();

            if (playerID == -1) {
                System.out.println("No player found.");
                return;
            }

            // Display list of townspeople
            stmt = conn.createStatement();
            System.out.println("Townspeople:");
            rs = stmt.executeQuery("SELECT * FROM npcs");

            while (rs.next()) {
                int id = rs.getInt("npcID");
                String npcName = rs.getString("npcname");
                boolean givingGift = rs.getBoolean("givinggifttoday");
                System.out.println(id + ". " + npcName + " - Gave gift today? " + givingGift);
            }
            rs.close();
            stmt.close();

            // Get id of receiver npc from user
            System.out.println("Who will you give a gift to? (Enter npcID):");
            int selectedNpc = scanner.nextInt();

            // Display player's inventory
            stmt = conn.createStatement();
            System.out.println("Your inventory:");
            rs = stmt.executeQuery("SELECT * FROM items WHERE playerID = " + playerID);

            while (rs.next()) {
                int id = rs.getInt("itemID");
                String itemName = rs.getString("itemname");
                String itemType = rs.getString("itemtype");
                String specialValue = rs.getString("specialvalue");
                int quantity = rs.getInt("quantity");
                System.out.println(id + ". " + itemName + ": " + itemType + ", " + specialValue + ", " + quantity);
            }
            rs.close();
            stmt.close();

            // Choose item to give
            System.out.println("Enter the itemID to give as a gift:");
            int itemID = scanner.nextInt();

            // Update NPC's gift status
            String updateSql = "UPDATE npcs SET givinggifttoday = TRUE WHERE npcID = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setInt(1, selectedNpc);
            updateStmt.executeUpdate();
            updateStmt.close();

            // Record the gift into log
            String insertGift = "INSERT INTO giftlog (npcID, playerID, itemID) VALUES (?, ?, ?)";
            PreparedStatement giftStmt = conn.prepareStatement(insertGift);
            giftStmt.setInt(1, selectedNpc);
            giftStmt.setInt(2, playerID);
            giftStmt.setInt(3, itemID);
            giftStmt.executeUpdate();
            giftStmt.close();

            // Confirm updated NPC info
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM npcs");

            System.out.println("\nUpdated gift status:");
            while (rs.next()) {
                int id = rs.getInt("npcID");
                String npcName = rs.getString("npcname");
                boolean givingGift = rs.getBoolean("givinggifttoday");
                System.out.println(id + ". " + npcName + " - Gave gift today? " + givingGift);
            }

        } catch (SQLException e) {
            System.out.println("Database error:");
            e.printStackTrace();
        } finally {
            // Cleanup
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                scanner.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
