package com.stardew;

import com.stardew.dao.PlayerDAO;
import com.stardew.model.Player;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class DBAppGui extends JFrame{
    private Connection conn;

    public DBAppGui(Connection conn) {
        this.conn = conn;
        UI();

        setVisible(true);
    }
    
    public void UI() {
        SwingUtilities.invokeLater(() -> {
            setTitle("Stardew Valley");
            setSize(700, 500);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

            // Center panel
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setBounds(200, 100, 300, 300);
            centerPanel.setOpaque(false);

            // Login UI
            JLabel nameLabel = new JLabel("Enter Player Name:");
            nameLabel.setForeground(new Color(221, 160, 89));
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

            JTextField nameField = new JTextField();
            nameField.setMaximumSize(new Dimension(250, 30));
            nameField.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton submitButton = new JButton("Submit");
            submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Gameplay Buttons (initially hidden)
            JButton giftButton = new JButton("Townspeople"); // --> will lead to give a gift and view relationship feature
            JButton harvestButton = new JButton("Harvest Crops"); // --> leads to view harvestable crops
            JButton collectButton = new JButton("Collect Animal Products"); // --> leads to harvestable products
            JButton transactionButton = new JButton("Shop"); // --> will lead to buy/sell item and view of transactions
            JButton inventoryButton = new JButton("View Inventory"); // checks inventory
            JButton nextDayButton = new JButton("Advance to The Next Day"); // advances to next day
            JButton reportsButton = new JButton("Reports"); // --> leads to reports of gift logs, harvested crops, harvested animal products

            Dimension buttonSize = new Dimension(250, 50);
            JButton[] gameButtons = {giftButton, harvestButton, collectButton, transactionButton};

            for (JButton btn : gameButtons) {
                btn.setMaximumSize(buttonSize);
                btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                btn.setBackground(new Color(221, 160, 89));
                btn.setForeground(new Color(221, 160, 89));
                btn.setFocusPainted(false);
                btn.setFont(new Font("SansSerif", Font.BOLD, 14));
                btn.setVisible(false); // Hide buttons initially
            }

            // Submit action
            submitButton.addActionListener(e -> {
                String playerName = nameField.getText().trim();

                if (playerName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a player name.");
                } else {
                    System.out.println("Player Name: " + playerName);
                    nameLabel.setVisible(false);
                    nameField.setVisible(false);
                    submitButton.setVisible(false);

                    for (JButton btn : gameButtons) {
                        btn.setVisible(true);
                    }
                }
                PlayerDAO playerDAO = new PlayerDAO();
                Player player = new Player(nameField.getText());
                playerDAO.createPlayer(player);
            });

            // Add login components
            centerPanel.add(nameLabel);
            centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            centerPanel.add(nameField);
            centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            centerPanel.add(submitButton);
            centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

            // Add game buttons (hidden initially)
            for (JButton btn : gameButtons) {
                centerPanel.add(btn);
                centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            }

            // Add action listeners
            giftButton.addActionListener(e -> {
                try {
                    new GiftDialog(this, conn);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            harvestButton.addActionListener(e -> new HarvestDialog(this));
            collectButton.addActionListener(e -> new CollectDialog(this));
            transactionButton.addActionListener(e -> new TransactionDialog(this));

            mainPanel.add(centerPanel, BorderLayout.CENTER);
            add(mainPanel);
            setLocationRelativeTo(null);
            setVisible(true);
        });
    }
}
