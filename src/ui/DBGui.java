package ui;

import dao.*;
import model.*;
import service.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.sql.Connection;

public class DBGui extends JFrame {
    private Connection conn;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    private GameService gameService = new GameService();
    private InventoryService inventoryService = new InventoryService();
    private ItemService itemService = new ItemService();
    private GiftLogService giftLogService = new GiftLogService();
    private HarvestLogService harvestLogService = new HarvestLogService();
    private ProductLogService productLogService = new ProductLogService();
    private TransactionService transactionService = new TransactionService();
    private PlayerService playerService = new PlayerService();
    private RelationService relationService = new RelationService();
    private AnimalService animalService = new AnimalService();
    private CropService cropService = new CropService();
    private NPCService npcService = new NPCService();

    private int playerID;
    private boolean reportPanelAdded = false;

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
            setLocationRelativeTo(null);

            cardLayout = new CardLayout();
            cardPanel = new JPanel(cardLayout);
            add(cardPanel);

            JPanel mainPanel = new JPanel() {
                Image bg = new ImageIcon(getClass().getResource("bg.png")).getImage();

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                }
            };
            mainPanel.setLayout(new BorderLayout());

            JLabel titleLabel = new JLabel("Welcome to Stardew Valley", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
            mainPanel.add(titleLabel, BorderLayout.NORTH);

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setOpaque(false);

            JLabel nameLabel = new JLabel("Enter Player Name:");
            nameLabel.setForeground(new Color(221, 160, 89));
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

            JTextField nameField = new JTextField();
            nameField.setMaximumSize(new Dimension(250, 30));
            nameField.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton submitButton = new JButton("Submit");
            submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton npcButton = new JButton("Townspeople");
            JButton farmButton = new JButton("Farm");
            JButton shopButton = new JButton("Shop");
            JButton inventoryButton = new JButton("View Inventory");
            JButton nextDayButton = new JButton("Advance to The Next Day");
            JButton reportsButton = new JButton("Reports");

            Dimension buttonSize = new Dimension(250, 50);
            JButton[] gameButtons = {npcButton, farmButton, shopButton, inventoryButton, nextDayButton, reportsButton};

            for (JButton btn : gameButtons) {
                btn.setMaximumSize(buttonSize);
                btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                btn.setBackground(new Color(221, 160, 89));
                btn.setForeground(Color.WHITE);
                btn.setFocusPainted(false);
                btn.setFont(new Font("SansSerif", Font.BOLD, 14));
                btn.setVisible(false);
            }

            submitButton.addActionListener(e -> {
                String playerName = nameField.getText().trim();

                if (playerName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a player name.");
                } else {
                    nameLabel.setVisible(false);
                    nameField.setVisible(false);
                    submitButton.setVisible(false);

                    for (JButton btn : gameButtons) {
                        btn.setVisible(true);
                    }

                    PlayerDAO playerDAO = new PlayerDAO();
                    Player existingPlayer = playerDAO.getPlayerByName(playerName);

                    if (existingPlayer == null) {
                        Player newPlayer = new Player(playerName);
                        playerDAO.createPlayer(newPlayer);
                        existingPlayer = playerDAO.getPlayerByName(playerName);
                    }

                    if (existingPlayer != null) {
                        this.playerID = existingPlayer.getPlayerID();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to retrieve or create player.");
                    }

                }
            });

            centerPanel.add(nameLabel);
            centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            centerPanel.add(nameField);
            centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            centerPanel.add(submitButton);
            centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

            for (JButton btn : gameButtons) {
                centerPanel.add(btn);
                centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            }

            mainPanel.add(centerPanel, BorderLayout.CENTER);
            cardPanel.add(mainPanel, "MainMenu");

            npcButton.addActionListener(e -> {
                JPanel townspeoplePanel = new townspeoplePanel(DBGui.this, conn, playerID);
                cardPanel.add(townspeoplePanel, "Townspeople");
                cardLayout.show(cardPanel, "Townspeople");
            });

            farmButton.addActionListener(e -> {
                String[] options = {"Harvest Crops", "Collect Animal Products", "Plant Crop", "Place Animal"};
                int choice = JOptionPane.showOptionDialog(
                        null,
                        "Choose a farm activity:",
                        "Farm Actions",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[0]
                );
                switch (choice) {
                    case 0 -> new HarvestDialog(DBGui.this, playerID);
                    case 1 -> new CollectDialog(DBGui.this, playerID);
                    case 2 -> new PlantCropDialog(DBGui.this, playerID, cropService, inventoryService, itemService);
                    case 3 -> new PlaceAnimalDialog(DBGui.this, playerID, animalService, inventoryService, itemService);
                }
            });

            shopButton.addActionListener(e -> {
                String[] options = {"Buy/Sell Item", "View Transactions"};
                int choice = JOptionPane.showOptionDialog(
                        null,
                        "Choose an action:",
                        "Shop Options",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[0]
                );
                if (choice == 0) {
                    new ShopDialog(DBGui.this, playerID);
                } else if (choice == 1) {
                    new TransactionHistoryDialog(DBGui.this, playerID);
                }
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
                    String itemName = (item != null) ? item.getItemname() : "Unknown Item";
                    sb.append(itemName).append(" x").append(inv.getQuantity()).append("\n");
                }

                JOptionPane.showMessageDialog(this, sb.toString());
            });

            nextDayButton.addActionListener(e -> {
                gameService.advanceDay(playerID);
                JOptionPane.showMessageDialog(this, "Sleeping...");
            });

            reportsButton.addActionListener(e -> {
                if (!reportPanelAdded) {
                    JPanel reportPanel = new JPanel();
                    reportPanel.setLayout(new GridLayout(5, 1, 10, 10));

                    JButton giftReportButton = new JButton("Gifting Report");
                    JButton harvestReportButton = new JButton("Harvest Report");
                    JButton productReportButton = new JButton("Product Report");
                    JButton transactionReportButton = new JButton("Transaction Report");
                    JButton backButton = new JButton("Back");

                    reportPanel.add(giftReportButton);
                    reportPanel.add(harvestReportButton);
                    reportPanel.add(productReportButton);
                    reportPanel.add(transactionReportButton);
                    reportPanel.add(backButton);

                    cardPanel.add(reportPanel, "Reports");
                    reportPanelAdded = true;

                    backButton.addActionListener(evt -> cardLayout.show(cardPanel, "MainMenu"));

                    giftReportButton.addActionListener(evt -> {
                        JTextField npcIDField = new JTextField();
                        JTextField yearField = new JTextField();
                        JTextField weekField = new JTextField();

                        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
                        inputPanel.add(new JLabel("NPC ID:"));
                        inputPanel.add(npcIDField);
                        inputPanel.add(new JLabel("Year:"));
                        inputPanel.add(yearField);
                        inputPanel.add(new JLabel("Week Number:"));
                        inputPanel.add(weekField);

                        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Gift Report Details", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            int npcID = Integer.parseInt(npcIDField.getText());
                            int year = Integer.parseInt(yearField.getText());
                            int week = Integer.parseInt(weekField.getText());
                            List<GiftLog> logs = giftLogService.getGiftLogsForWeek(playerID, npcID, year, week);

                            StringBuilder sb = new StringBuilder("Gift Logs:\n");
                            for (GiftLog log : logs) {
                                sb.append("NPC ID: ").append(log.getNpcID())
                                  .append(", Item: ").append(log.getItemName())
                                  .append(", Date: ").append(log.getDateGiven()).append("\n");
                            }
                            showReport(sb.toString(), "Gift Report");
                        }
                    });

                    harvestReportButton.addActionListener(evt -> {
                        JComboBox<String> seasonBox = new JComboBox<>(new String[]{"Spring", "Summer", "Fall", "Winter"});
                        JTextField yearField = new JTextField();

                        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
                        inputPanel.add(new JLabel("Season:"));
                        inputPanel.add(seasonBox);
                        inputPanel.add(new JLabel("Year:"));
                        inputPanel.add(yearField);

                        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Harvest Report Details", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            String season = (String) seasonBox.getSelectedItem();
                            int year = Integer.parseInt(yearField.getText());
                            List<HarvestLog> logs = harvestLogService.getHarvestLogsBySeasonAndYear(playerID, season, year);

                            StringBuilder sb = new StringBuilder("Harvest Logs:\n");
                            for (HarvestLog log : logs) {
                                sb.append("Crop: ").append(log.getCropName())
                                  .append(", Quantity: ").append(log.getQuantity())
                                  .append(", Date: ").append(log.getDateHarvested()).append("\n");
                            }
                            showReport(sb.toString(), "Harvest Report");
                        }
                    });

                    productReportButton.addActionListener(evt -> {
                        JComboBox<String> seasonBox = new JComboBox<>(new String[]{"Spring", "Summer", "Fall", "Winter"});
                        JTextField yearField = new JTextField();

                        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
                        inputPanel.add(new JLabel("Season:"));
                        inputPanel.add(seasonBox);
                        inputPanel.add(new JLabel("Year:"));
                        inputPanel.add(yearField);

                        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Product Report Details", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            String season = (String) seasonBox.getSelectedItem();
                            int year = Integer.parseInt(yearField.getText());
                            List<ProductLog> logs = productLogService.getProductLogsBySeasonAndYear(playerID, season, year);

                            StringBuilder sb = new StringBuilder("Product Logs:\n");
                            for (ProductLog log : logs) {
                                sb.append("Product: ").append(log.getAnimalName())
                                  .append(", Quantity: ").append(log.getQuantityProduced())
                                  .append(", Date: ").append(log.getDateProduced()).append("\n");
                            }
                            showReport(sb.toString(), "Product Report");
                        }
                    });

                    transactionReportButton.addActionListener(evt -> {
                        JComboBox<String> seasonBox = new JComboBox<>(new String[]{"Spring", "Summer", "Fall", "Winter"});
                        JTextField yearField = new JTextField();

                        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
                        inputPanel.add(new JLabel("Season:"));
                        inputPanel.add(seasonBox);
                        inputPanel.add(new JLabel("Year:"));
                        inputPanel.add(yearField);

                        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Transaction Report Details", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            String season = (String) seasonBox.getSelectedItem();
                            int year = Integer.parseInt(yearField.getText());
                            List<Transaction> logs = transactionService.getTransactionsBySeasonAndYear(playerID, season, year);

                            StringBuilder sb = new StringBuilder("Transaction Logs:\n");
                            for (Transaction tx : logs) {
                                sb.append("Type: ").append(tx.getType())
                                  .append(", Item: ").append(tx.getItemName())
                                  .append(", Amount: ").append(tx.getAmount())
                                  .append(", Date: ").append(tx.getDate()).append("\n");
                            }
                            showReport(sb.toString(), "Transaction Report");
                        }
                    });
                }

                cardLayout.show(cardPanel, "Reports");
            });
        });
    }

    private void showReport(String content, String title) {
        JTextArea textArea = new JTextArea(content, 10, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(null, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void showMainMenu() {
        cardLayout.show(cardPanel, "MainMenu");
    }
}