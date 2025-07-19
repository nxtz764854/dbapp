package com.stardew;

import javax.swing.*;
import java.sql.Connection;

public class GameMenu {
    private Connection conn;

    public GameMenu(Connection conn) {
        this.conn = conn;
    }

    public void start() {
        SwingUtilities.invokeLater(() -> {
            new DBAppGui(conn);
        });
    }
}
