package ui;

import service.*;
import model.*;
import dao.*;

import javax.swing.*;
import java.awt.*;

import java.util.List;

import java.sql.*;

public class GiftDialog extends JDialog {
    private JComboBox<String> townspersonBox;
    private JComboBox<ItemDisplay> itemBox;
    private InventoryService inventoryService = new InventoryService();
    private int playerId = 1; //temporary muna



    public GiftDialog(JFrame parent, Connection conn) {
        super(parent, "Give a Gift", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        townspersonBox = new JComboBox<>();
        itemBox = new JComboBox<>();

        populateTownspplBox(conn); 
        populateItemBox();



        setLayout(new BorderLayout());

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Components
        JLabel townspersonLabel = new JLabel("Select Townsperson:");

        JLabel itemLabel = new JLabel("Select Item from Inventory:");

        JButton giveButton = new JButton("Give Gift");

        JTextArea resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Add to form
        formPanel.add(townspersonLabel);
        formPanel.add(townspersonBox);
        formPanel.add(itemLabel);
        formPanel.add(itemBox);
        formPanel.add(new JLabel());
        formPanel.add(giveButton);

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Give Gift
        giveButton.addActionListener(e -> {
            String townsperson = (String) townspersonBox.getSelectedItem();
            ItemDisplay item = (ItemDisplay) itemBox.getSelectedItem();
            String itemName = item != null ? item.getItemName() : "Unknown";


            // Simulate favorite check
            boolean isFavorite = isFavoriteGift(townsperson, itemName);

            StringBuilder log = new StringBuilder("Gave " + item + " to " + townsperson + ".\n");

            if (isFavorite) {
                log.append("It's their favorite! Relationship increased by +2.");
            } else {
                log.append("They liked it. Relationship increased by +1.");
            }

            // Deduct item from inventory
            boolean removed = inventoryService.removeItemFromInventory(playerId, item.getItemId(), 1);

            if (removed) {
                log.append("\nItem deducted from inventory.\nGift activity logged.");
                itemBox.removeItem(item);
            } else {
                log.append("\nFailed to deduct item from inventory.");
            }


            resultArea.setText(log.toString());
        });

        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void populateItemBox() {
        itemBox.removeAllItems(); // clear existing
        List<Inventory> itemList = inventoryService.getInventoryByPlayerID(playerId);
        ItemService itemService = new ItemService();

        for (Inventory inv : itemList) {
            Item item = itemService.getItemByID(inv.getItemID());
            if (item != null) {
                itemBox.addItem(new ItemDisplay(item.getItemID(), item.getItemname()));
            }
        }
    }




    private void populateTownspplBox(Connection conn) {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT npcname FROM npcs")) {
            while (rs.next()) {
                townspersonBox.addItem(rs.getString("npcname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private boolean isFavoriteGift(String townsperson, String item) {
        return switch (townsperson) {
            case "Abigail" -> item.equals("Amethyst");
            case "Sebastian" -> item.equals("Coconut");
            case "Leah" -> item.equals("Salad");
            case "Elliott" -> item.equals("Wine");
            default -> false;
        };
    }
}
