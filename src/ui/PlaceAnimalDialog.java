package ui;

import javax.swing.*;
import model.*;
import service.*;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

class PlaceAnimalDialog extends JDialog {
    public PlaceAnimalDialog(JFrame parent, int playerID, AnimalService animalService, InventoryService inventoryService, ItemService itemService) {
        super(parent, "Place Animal", true);
        setLayout(new GridLayout(0, 2, 5, 5));

        List<Inventory> inventory = inventoryService.getInventoryByPlayerID(playerID);
        Map<String, Integer> animalItems = new HashMap<>();
        Map<String, Integer> productItems = new HashMap<>();

        for (Inventory inv : inventory) {
            Item item = itemService.getItemByID(inv.getItemID());
            if (item != null && item.getItemtype().equals("animal")) {
                animalItems.put(item.getItemname(), item.getItemID());
            }
        }

        for (Item item : itemService.getAllItems()) {
            if (item.getItemtype().equals("product")) {
                productItems.put(item.getItemname(), item.getItemID());
            }
        }

        JComboBox<String> animalDropdown = new JComboBox<>(animalItems.keySet().toArray(new String[0]));
        JComboBox<String> produceDropdown = new JComboBox<>(productItems.keySet().toArray(new String[0]));

        add(new JLabel("Select Animal Item:"));
        add(animalDropdown);
        add(new JLabel("Select Produce Item:"));
        add(produceDropdown);

        JButton placeBtn = new JButton("Place");
        placeBtn.addActionListener(e -> {
            String animalName = (String) animalDropdown.getSelectedItem();
            String produceName = (String) produceDropdown.getSelectedItem();

            if (animalName == null || produceName == null) {
                JOptionPane.showMessageDialog(this, "Please select both animal and produce.");
                return;
            }

            int itemID = animalItems.get(animalName);
            int produceID = productItems.get(produceName);

            boolean removed = inventoryService.removeItemFromInventory(playerID, itemID, 1);
            if (!removed) {
                JOptionPane.showMessageDialog(this, "Failed to place animal: Item not available in inventory.");
                return;
            }

            Animal animal = new Animal(0, animalName, playerID, itemID, 0, 3, false);
            animal.setProduceID(produceID);
            animalService.addAnimal(animal);
            JOptionPane.showMessageDialog(this, "Animal placed successfully!");
            dispose();
        });

        add(new JLabel());
        add(placeBtn);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
