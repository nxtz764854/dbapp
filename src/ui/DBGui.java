package ui;

import ui.*;
import dao.*;
import model.*;
import java.service.*;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class DBGui extends JFrame{
    private Connection conn;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private GameService gameService = new GameService();
    private InventoryService inventoryService = new InventoryService();
    private ItemService itemService = new ItemService();


    public DBGui(Connection conn) {
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

            cardLayout = new CardLayout();
            cardPanel = new JPanel(cardLayout);

            // Title
            JLabel titleLabel = new JLabel("Welcome to Stardew Valley", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
            mainPanel.add(titleLabel, BorderLayout.NORTH);

            // Center panel
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
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
            JButton npcButton = new JButton("Townspeople"); // --> will lead to give a gift and view relationship feature
            JButton farmButton = new JButton("Farm"); // --> leads to harvest crops and collect animal products
            JButton shopButton = new JButton("Shop"); // --> will lead to buy/sell item and view of transactions
            JButton inventoryButton = new JButton("View Inventory"); // checks inventory
            JButton nextDayButton = new JButton("Advance to The Next Day"); // advances to next day
            JButton reportsButton = new JButton("Reports"); // --> leads to reports of gift logs, harvested crops, harvested animal products

            Dimension buttonSize = new Dimension(250, 50);
            JButton[] gameButtons = {npcButton, farmButton, shopButton, inventoryButton, nextDayButton, reportsButton};

            for (JButton btn : gameButtons) {
                btn.setMaximumSize(buttonSize);
                btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                btn.setBackground(new Color(221, 160, 89));
                btn.setForeground(Color.WHITE);
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

                    PlayerDAO playerDAO = new PlayerDAO();
                    Player player = new Player(playerName);
                    playerDAO.createPlayer(player);
                }
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
            
            npcButton.addActionListener(e -> {
                JPanel townspeoplePanel = new npcPanel(DBGui.this);
                cardPanel.add(townspeoplePanel, "Townspeople");
                cardLayout.show(cardPanel, "Townspeople");
            });

            farmButton.addActionListener(e -> {
                JPanel farmPanel = new FarmPanel(DBGui.this);
                cardPanel.add(farmPanel, "FARM");
                cardLayout.show(cardPanel, "FARM");
            });
            
            shopButton.addActionListener(e -> {
                JPanel shopPanel = new ShopPanel(DBGui.this);
                cardPanel.add(shopPanel, "SHOP");
                cardLayout.show(cardPanel, "SHOP");
            });
            
           inventoryButton.addActionListener(e -> {
                List<Inventory> inventoryList = inventoryService.getInventoryByPlayerID(playerID);
            
                if (inventoryList.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Your inventory is empty.");
                    return;
                }
            
                StringBuilder sb = new StringBuilder("Inventory:\n");
            
                for (Inventory inv : inventoryList) {
                    Item item = itemService.getItemByID(inv.getItemID());
                    String itemName = (item != null) ? item.getItemName() : "Unknown Item";
            
                    sb.append(itemName)
                      .append(" x")
                      .append(inv.getQuantity())
                      .append("\n");
                }
            
                JOptionPane.showMessageDialog(this, sb.toString());
            });
            
            nextDayButton.addActionListener(e -> {
                JPanel nextDayPanel = new NextDayPanel(DBGui.this);
                cardPanel.add(nextDayPanel, "NEXTDAY");
                cardLayout.show(cardPanel, "NEXTDAY");
                gameService.advanceDay(playerID);
                JOptionPane.showMessageDialog(this, "Sleeping...");
            });

            
            reportsButton.addActionListener(e -> {
                JPanel reportsPanel = new ReportsPanel(DBGui.this);
                cardPanel.add(reportsPanel, "REPORTS");
                cardLayout.show(cardPanel, "REPORTS");
            });

            mainPanel.add(centerPanel, BorderLayout.CENTER);
            cardPanel.add(mainPanel, "MainMenu");
            add(cardPanel);
            setLocationRelativeTo(null);
        });
    }

     public void showMainMenu() {
        cardLayout.show(cardPanel, "MainMenu");
    }
}
