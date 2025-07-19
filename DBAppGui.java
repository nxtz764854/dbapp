package com.stardew;

import com.stardew.GameMenu;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class DBAppGui extends JFrame{
    private Connection conn;

    public DBAppGui(Connection conn) {
        this.conn = conn;
        UI();

        setVisible(true);
    }

    public void UI() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Stardew Valley");
            frame.setSize(700, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel() {
                Image bg = new ImageIcon(getClass().getResource("bg.png")).getImage();

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                }
            };

            mainPanel.setLayout(new BorderLayout());

            // Title
            JLabel titleLabel = new JLabel("Welcome to Stardew Valley", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
            mainPanel.add(titleLabel, BorderLayout.NORTH);

            // Center panel with buttons
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setBounds(200, 100, 300, 300);
            centerPanel.setOpaque(false);

            JButton giftButton = new JButton("Give Gift to Townsperson");
            JButton harvestButton = new JButton("Harvest Crops");
            JButton collectButton = new JButton("Collect Animal Products");
            JButton transactionButton = new JButton("Buy/Sell Item");

            Dimension buttonSize = new Dimension(250, 50);
            for (JButton btn : new JButton[]{giftButton, harvestButton, collectButton, transactionButton}) {
                btn.setMaximumSize(buttonSize);
                btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                btn.setBackground(new Color(160, 82, 45));
                btn.setForeground(new Color(160, 82, 45));
                btn.setFocusPainted(false);
                btn.setFont(new Font("SansSerif", Font.BOLD, 14));
                centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                centerPanel.add(btn);
            }

            mainPanel.add(centerPanel, BorderLayout.CENTER);
            frame.add(mainPanel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Action Listeners
            giftButton.addActionListener(e -> new GiftDialog(frame, conn));
            harvestButton.addActionListener(e -> new HarvestDialog(frame));
            collectButton.addActionListener(e -> new CollectDialog(frame));
            transactionButton.addActionListener(e -> new TransactionDialog(frame));
        });
    }
}
