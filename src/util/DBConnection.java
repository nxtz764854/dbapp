package util;

import java.sql.Connection; // java.sql.Connection interface
import java.sql.DriverManager; // java.sql.DriverManager class
import java.sql.SQLException; // java.sql.SQLException class

public class DBConnection {
    // MySQL database URL
    private static final String URL = "jdbc:mysql://localhost:3306/farming_game?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    // MySQL database username
    private static final String USER = "root";
    // MySQL database password
    private static final String PASSWORD = "********";

    // The connection object
    private static Connection connection = null;

    // Static initializer to load the JDBC driver and initialize the connection
    static {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Initialize the connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            // Print a success message
            System.out.println("Database connected successfully.");
        } catch (ClassNotFoundException e) {
            // Print an error message if the JDBC driver is not found
            System.err.println("JDBC Driver not found.");
            // Print the stack trace
            e.printStackTrace();
        } catch (SQLException e) {
            // Print an error message if the connection fails
            System.err.println("Database connection failed.");
            // Print the stack trace
            e.printStackTrace();
        }
    }

    // Public method to retrieve the active connection
    public static Connection getConnection() {
        // Return the active connection
        return connection;
    }
}
