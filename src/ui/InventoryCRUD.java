package ui;

import model.Inventory;
import model.Item;
import model.Player;
import service.InventoryService;
import service.ItemService;
import service.PlayerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InventoryCRUD extends JDialog {
    private final PlayerService playerService = new PlayerService();
    private final ItemService itemService = new ItemService();
    private final InventoryService inventoryService = new InventoryService();

    private JComboBox<String> playerDropdown;
    private DefaultTableModel tableModel;
    private JTable table;

    public InventoryCRUD(JFrame parent) {
        super(parent, "Manage Inventory", true);
        setSize(800, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Dropdown to select player
        JPanel topPanel = new JPanel(new FlowLayout());
        playerDropdown = new JComboBox<>();
        List<Player> players = playerService.getAllPlayers();
        for (Player p : players) {
            playerDropdown.addItem(p.getPlayerID() + ": " + p.getPlayername());
        }
        topPanel.add(new JLabel("Select Player: "));
        topPanel.add(playerDropdown);
        add(topPanel, BorderLayout.NORTH);

        // Inventory table
        tableModel = new DefaultTableModel(new Object[]{"Item Name", "Type", "Description", "Quantity"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Add Item");
        JButton removeBtn = new JButton("Remove Item");
        JButton refreshBtn = new JButton("Refresh");
        JButton closeBtn = new JButton("Close");

        btnPanel.add(addBtn);
        btnPanel.add(removeBtn);
        btnPanel.add(refreshBtn);
        btnPanel.add(closeBtn);
        add(btnPanel, BorderLayout.SOUTH);

        playerDropdown.addActionListener(e -> refreshInventory());
        refreshBtn.addActionListener(e -> refreshInventory());
        closeBtn.addActionListener(e -> dispose());
        addBtn.addActionListener(e -> showAddDialog());
        removeBtn.addActionListener(e -> showRemoveDialog());

        if (!players.isEmpty()) refreshInventory();

        setVisible(true);
    }

    private void refreshInventory() {
        tableModel.setRowCount(0);
        if (playerDropdown.getSelectedItem() == null) return;

        int playerID = Integer.parseInt(playerDropdown.getSelectedItem().toString().split(":")[0]);
        List<Inventory> inventory = inventoryService.getInventoryByPlayerID(playerID);

        for (Inventory inv : inventory) {
            Item item = itemService.getItemByID(inv.getItemID());
            if (item != null) {
                tableModel.addRow(new Object[]{
                        item.getItemname(),
                        item.getItemtype(),
                        item.getDescript(),
                        inv.getQuantity()
                });
            }
        }
    }

    private void showAddDialog() {
        if (playerDropdown.getSelectedItem() == null) return;
        int playerID = Integer.parseInt(playerDropdown.getSelectedItem().toString().split(":")[0]);

        List<Item> items = itemService.getAllItems();
        JComboBox<String> itemBox = new JComboBox<>();
        for (Item item : items) {
            itemBox.addItem(item.getItemID() + ": " + item.getItemname());
        }

        JTextField qtyField = new JTextField();

        Object[] msg = {"Select Item:", itemBox, "Quantity:", qtyField};
        int result = JOptionPane.showConfirmDialog(this, msg, "Add Item to Inventory", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int itemID = Integer.parseInt(itemBox.getSelectedItem().toString().split(":")[0]);
                int quantity = Integer.parseInt(qtyField.getText());
                if (quantity <= 0) throw new NumberFormatException();

                if (inventoryService.addItemToInventory(playerID, itemID, quantity)) {
                    refreshInventory();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add item.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity.");
            }
        }
    }

    private void showRemoveDialog() {
        if (playerDropdown.getSelectedItem() == null || table.getSelectedRow() == -1) return;
        int playerID = Integer.parseInt(playerDropdown.getSelectedItem().toString().split(":")[0]);

        String itemName = tableModel.getValueAt(table.getSelectedRow(), 0).toString();
        Item item = itemService.getItemByName(itemName);
        if (item == null) return;

        JTextField qtyField = new JTextField();
        Object[] msg = {"Quantity to remove:", qtyField};
        int result = JOptionPane.showConfirmDialog(this, msg, "Remove Item", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int quantity = Integer.parseInt(qtyField.getText());
                if (quantity <= 0) throw new NumberFormatException();

                if (inventoryService.removeItemFromInventory(playerID, item.getItemID(), quantity)) {
                    refreshInventory();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to remove item.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity.");
            }
        }
    }
}
