import java.sql.Connection;

import ui.DBGui;
import util.DBConnection;

public class Main {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        new DBGui(conn);  // CLI menu
    }
}
