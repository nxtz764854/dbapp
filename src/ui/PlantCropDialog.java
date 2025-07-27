package ui;

import dao.*;
import model.*;
import service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

class PlantCropDialog extends JDialog {
    public PlantCropDialog(JFrame parent, int playerID, CropService cropService, InventoryService inventoryService, ItemService itemService) {
        super(parent, "Plant Crop", true);
        setSize(400, 200);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Select crop to plant:");
        panel.add(label, BorderLayout.NORTH);

        List<Inventory> inventoryList = inventoryService.getInventoryByPlayerID(playerID);
        DefaultComboBoxModel<String> cropModel = new DefaultComboBoxModel<>();
        for (Inventory inv : inventoryList) {
            Item item = itemService.getItemByID(inv.getItemID());
            if (item != null && item.getItemtype().equals("crop")) {
                cropModel.addElement(item.getItemname());
            }
        }
        JComboBox<String> cropComboBox = new JComboBox<>(cropModel);
        panel.add(cropComboBox, BorderLayout.CENTER);

        JButton plantButton = new JButton("Plant");
        plantButton.addActionListener(e -> {
            String selectedCrop = (String) cropComboBox.getSelectedItem();
            if (selectedCrop == null) {
                JOptionPane.showMessageDialog(this, "No crop selected or available.");
                return;
            }

            Item seedItem = itemService.getItemByName(selectedCrop);
            if (seedItem == null) {
                JOptionPane.showMessageDialog(this, "Seed item not found.");
                return;
            }

            boolean removed = inventoryService.removeItemFromInventory(playerID, seedItem.getItemID(), 1);
            if (!removed) {
                JOptionPane.showMessageDialog(this, "Not enough seeds in inventory.");
                return;
            }

            Crop crop = new Crop(0, selectedCrop, playerID, seedItem.getItemID(),
                    new PlayerService().getPlayerByID(playerID).getCurrent_day(),
                    3, seedItem.getItemID(), false);

            if (cropService.addCrop(crop)) {
                JOptionPane.showMessageDialog(this, "Crop planted successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to plant crop.");
            }
        });
        panel.add(plantButton, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
}