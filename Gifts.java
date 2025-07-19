import java.sql.*;
import java.util.Scanner;

public class Gifts {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/farming_game";
        String user = "root";
        String password = "root1sql";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Scanner scanner = new Scanner(System.in);


        try {
            // Connect to database
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database.");

            // Display list of townspeople
            stmt = conn.createStatement();
            System.out.println("Townspeople:");
            rs = stmt.executeQuery("SELECT * FROM npcs");

            while (rs.next()) {
                int id = rs.getInt("npcID");
                String npcName = rs.getString("npcname");
                String givinggifttoday = rs.getString("givinggifttoday");
                System.out.println(id + ". " + npcName + " - " + givinggifttoday);
            }

            rs.close();
            stmt.close();

            // Get id of receiver npc from user
            System.out.println("Who will you give a gift to?");
            int selectedNpc = scanner.nextInt();

            // Shows the player's list of items in inventory
            stmt = conn.createStatement();
            System.out.println("Your inventory:");
            rs = stmt.executeQuery("SELECT * FROM items");

            while (rs.next()) {
                int id = rs.getInt("itemID");
                String itemName = rs.getString("itemname");
                String itemType = rs.getString("itemtype");
                String specialvalue = rs.getString("specialvalue");
                String quantity = rs.getString("quantity");
                System.out.println(id + ". " + itemName + ": " + itemType + ", " + specialvalue + ", " + quantity);
            }

            rs.close();
            stmt.close();

            // Updates the npc's givinggifttoday to true
            String updateSql = "UPDATE npcs SET givinggifttoday = TRUE WHERE id = selectedNpc";

            // test lang
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM npcs");

            System.out.println("\n Updated gift:");
            while (rs.next()) {
                int id = rs.getInt("npcID");
                String npcName = rs.getString("npcname");
                String givinggifttoday = rs.getString("givinggifttoday");
                System.out.println(id + ". " + npcName + " - " + givinggifttoday);
            }

        } catch (SQLException e) {
            System.out.println("❌ Database error:");
            e.printStackTrace();
        } finally {
            // Close everything
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