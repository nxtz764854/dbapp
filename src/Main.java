import util.DBConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();

        if (conn != null) {
            GameMenu menu = new GameMenu(conn);
            menu.start(); // Start the game loop
        } else {
            System.out.println("Failed to connect to the database.");
        }
    }
}