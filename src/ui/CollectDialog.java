package ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import service.*;
import model.*;

public class CollectDialog extends JDialog {
    private AnimalService animalService = new AnimalService();
    private InventoryService inventoryService = new InventoryService();

    public CollectDialog(JFrame parent, int playerID) {
        super(parent, "Collect Animal Products", true);
        setSize(400, 300);
        setLayout(new BorderLayout());

        List<Animal> readyAnimals = animalService.getReadyToHarvestAnimals(playerID);

        if (readyAnimals.isEmpty()) {
            add(new JLabel("No animals ready to collect from."), BorderLayout.CENTER);
        } else {
            DefaultListModel<String> model = new DefaultListModel<>();
            for (Animal animal : readyAnimals) {
                model.addElement(animal.getAnimalname() + " (ID: " + animal.getAnimalID() + ")");
            }

            JList<String> animalList = new JList<>(model);
            JScrollPane scrollPane = new JScrollPane(animalList);
            add(scrollPane, BorderLayout.CENTER);

            JButton collectButton = new JButton("Collect Products");
            collectButton.addActionListener(e -> {
                for (Animal animal : readyAnimals) {
                    animalService.markHarvested(animal.getAnimalID());
                    inventoryService.addItemToInventory(playerID, animal.getProduceID(), 1);
                }
                JOptionPane.showMessageDialog(this, "Animal products collected and added to inventory!");
                dispose();
            });

            add(collectButton, BorderLayout.SOUTH);
        }
    }
}
