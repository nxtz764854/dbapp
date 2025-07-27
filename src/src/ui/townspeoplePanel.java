package ui;

import javax.swing.*;
import java.awt.*;

public class townspeoplePanel extends JPanel {
    public townspeoplePanel(JFrame parent, java.sql.Connection conn, int playerID) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Townspeople", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton giftButton = new JButton("Give a Gift");
        giftButton.addActionListener(e -> {
            try {
                new GiftDialog(parent, conn).setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        JButton viewRelationshipsButton = new JButton("View Relationships");
        viewRelationshipsButton.addActionListener(e -> {
            new viewRelationshipsDialog(parent, playerID);
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            if (parent instanceof ui.DBGui gui) {
                gui.showMainMenu();
            }
        });

        buttonPanel.add(giftButton);
        buttonPanel.add(viewRelationshipsButton);
        buttonPanel.add(backButton);

        add(title, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }
}
