package ui;

import model.Item;
import service.ItemService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ItemCRUD extends JDialog {
    private final ItemService itemService = new ItemService();
    private JTable table;
    private DefaultTableModel tableModel;

    public ItemCRUD(JFrame parent) {
        super(parent, "Manage Items", true);
        setSize(800, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Table setup
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Type", "Description", "Price", "Buyable"}, 0);
        table = new JTable(tableModel);
        refreshTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Add Item");
        JButton updateBtn = new JButton("Update Selected");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton closeBtn = new JButton("Close");

        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(closeBtn);
        add(btnPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> showAddDialog());
        updateBtn.addActionListener(e -> showUpdateDialog());
        deleteBtn.addActionListener(e -> deleteSelectedItem());
        closeBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Item> items = itemService.getAllItems();
        for (Item i : items) {
            tableModel.addRow(new Object[]{
                    i.getItemID(),
                    i.getItemname(),
                    i.getItemtype(),
                    i.getDescript(),
                    i.getPrice(),
                    i.isBuyable()
            });
        }
    }

    private void showAddDialog() {
        JTextField nameField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField descField = new JTextField();
        JTextField priceField = new JTextField();
        JCheckBox buyableBox = new JCheckBox("Buyable");

        Object[] msg = {
                "Name:", nameField,
                "Type:", typeField,
                "Description:", descField,
                "Price:", priceField,
                buyableBox
        };

        int result = JOptionPane.showConfirmDialog(this, msg, "Add Item", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Item item = new Item(0,
                        nameField.getText(),
                        typeField.getText(),
                        descField.getText(),
                        Integer.parseInt(priceField.getText()),
                        buyableBox.isSelected());

                if (itemService.addItem(item)) {
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add item.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price input.");
            }
        }
    }

    private void showUpdateDialog() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select an item to update.");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        String name = (String) tableModel.getValueAt(row, 1);
        String type = (String) tableModel.getValueAt(row, 2);
        String desc = (String) tableModel.getValueAt(row, 3);
        int price = (int) tableModel.getValueAt(row, 4);
        boolean buyable = (boolean) tableModel.getValueAt(row, 5);

        JTextField nameField = new JTextField(name);
        JTextField typeField = new JTextField(type);
        JTextField descField = new JTextField(desc);
        JTextField priceField = new JTextField(String.valueOf(price));
        JCheckBox buyableBox = new JCheckBox("Buyable", buyable);

        Object[] msg = {
                "Name:", nameField,
                "Type:", typeField,
                "Description:", descField,
                "Price:", priceField,
                buyableBox
        };

        int result = JOptionPane.showConfirmDialog(this, msg, "Update Item", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Item updated = new Item(id,
                        nameField.getText(),
                        typeField.getText(),
                        descField.getText(),
                        Integer.parseInt(priceField.getText()),
                        buyableBox.isSelected());

                if (itemService.updateItem(updated)) {
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Update failed.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price input.");
            }
        }
    }

    private void deleteSelectedItem() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select an item to delete.");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete item ID " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (itemService.deleteItem(id)) {
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Delete failed.");
            }
        }
    }
}
