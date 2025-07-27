package ui;

import model.Item;
import service.GameService;
import service.ItemService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ShopDialog extends JDialog {
    private final GameService gameService = new GameService();
    private final ItemService itemService = new ItemService();

    public ShopDialog(JFrame parent, int playerID) {
        super(parent, "Buy/Sell Items", true);
        setSize(450, 300);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JComboBox<Item> itemComboBox = new JComboBox<>();
        JTextField quantityField = new JTextField("1");

        // Load only buyable items
        List<Item> buyableItems = itemService.getBuyableItems();
        for (Item item : buyableItems) {
            itemComboBox.addItem(item);
        }

        JButton buyButton = new JButton("Buy");
        JButton sellButton = new JButton("Sell");

        centerPanel.add(new JLabel("Select Item:"));
        centerPanel.add(itemComboBox);
        centerPanel.add(new JLabel("Quantity:"));
        centerPanel.add(quantityField);
        centerPanel.add(buyButton);
        centerPanel.add(sellButton);
        add(centerPanel, BorderLayout.CENTER);

        buyButton.addActionListener(e -> {
            Item item = (Item) itemComboBox.getSelectedItem();
            int quantity;
            try {
                quantity = Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity.");
                return;
            }

            boolean success = true;
            for (int i = 0; i < quantity; i++) {
                if (!gameService.buyItem(playerID, item.getItemID())) {
                    success = false;
                    break;
                }
            }

            JOptionPane.showMessageDialog(this, success
                    ? "Item(s) purchased successfully!"
                    : "Purchase failed. Check your balance.");
        });

        sellButton.addActionListener(e -> {
            Item item = (Item) itemComboBox.getSelectedItem();
            int quantity;
            try {
                quantity = Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity.");
                return;
            }

            boolean success = true;
            for (int i = 0; i < quantity; i++) {
                if (!gameService.sellItem(playerID, item.getItemID(), item.getPrice())) {
                    success = false;
                    break;
                }
            }

            JOptionPane.showMessageDialog(this, success
                    ? "Item(s) sold successfully!"
                    : "Sell failed. Do you have enough items?");
        });

        setVisible(true);
    }
}
