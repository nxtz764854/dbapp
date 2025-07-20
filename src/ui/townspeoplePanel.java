package ui;

import javax.swing.*;
import java.awt.*;

public class townspeoplePanel extends JPanel {
    public townspeoplePanel(JFrame parent, java.sql.Connection conn) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Townspeople", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));

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
            new viewRelationshipsDialog(parentFrame, playerID);
        });

        buttonPanel.add(giftButton);
        buttonPanel.add(viewRelationshipsButton);

        add(title, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }
}
