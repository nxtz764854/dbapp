package ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Crop;
import service.CropService;
import service.GameService;

public class HarvestDialog extends JDialog {
    private JTextArea textArea;
    private CropService cropService;

    public HarvestDialog(JFrame parent, int playerID) {
        super(parent, "Harvest Crops", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        cropService = new CropService();
        textArea = new JTextArea();
        textArea.setEditable(false);

        List<Crop> readyCrops = cropService.getReadyToHarvestCrops(playerID);
        StringBuilder sb = new StringBuilder();

        for (Crop crop : readyCrops) {
            sb.append("Crop: ").append(crop.getCropname())
              .append(" (Planted on Day ").append(crop.getPlantedDay())
              .append(")\n");
        }

        if (readyCrops.isEmpty()) {
            sb.append("No crops ready for harvest.");
        }

        textArea.setText(sb.toString());
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JButton close = new JButton("Close");
        close.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();

        if (!readyCrops.isEmpty()) {
            JButton harvest = new JButton("Harvest");
            harvest.addActionListener(e -> {
                GameService gameService = new GameService();
                gameService.harvestCrops(playerID);
                JOptionPane.showMessageDialog(this, "Crops harvested!");
                dispose();
            });
            buttonPanel.add(harvest);
        }

        buttonPanel.add(close);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
