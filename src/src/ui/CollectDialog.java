package ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import service.*;
import model.*;

public class CollectDialog extends JDialog {
    public CollectDialog(JFrame parent, int playerID) {
        super(parent, "Collect Animal Products", true);
        setSize(400, 300);
        setLayout(new BorderLayout());

        AnimalService animalService = new AnimalService();
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
                GameService gameService = new GameService();
                gameService.harvestAnimals(playerID);
                JOptionPane.showMessageDialog(this, "Animal products collected and added to inventory!");
                dispose();
            });

            add(collectButton, BorderLayout.SOUTH);
        }

        setVisible(true);
    }
}
