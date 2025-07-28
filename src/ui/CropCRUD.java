package ui;

import dao.CropDAO;
import model.Crop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CropCRUD extends JPanel {
    private CropDAO cropDAO = new CropDAO();
    private JTable table;
    private DefaultTableModel model;
    private JPanel formPanel;
    private JTextField cropnameField, playerIDField, itemIDField, plantedDayField, growthTimeField, produceIDField;
    private JCheckBox harvestCheck;
    private JButton addButton, updateButton, deleteButton, backButton;

    private CardLayout cardLayout;
    private JPanel cardPanel;

    public CropCRUD(CardLayout cardLayout, JPanel cardPanel) {
    this.cardLayout = cardLayout;
    this.cardPanel = cardPanel;


        setLayout(new BorderLayout());

        JLabel title = new JLabel("Crop Management", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Table setup
        model = new DefaultTableModel(new String[]{"ID", "Name", "PlayerID", "ItemID", "PlantedDay", "GrowthTime", "ProduceID", "Harvest"}, 0);
        table = new JTable(model);
        loadCrops();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Form
        formPanel = new JPanel(new GridLayout(8, 2));
        cropnameField = new JTextField();
        playerIDField = new JTextField();
        itemIDField = new JTextField();
        plantedDayField = new JTextField();
        growthTimeField = new JTextField();
        produceIDField = new JTextField();
        harvestCheck = new JCheckBox();

        formPanel.add(new JLabel("Crop Name:"));
        formPanel.add(cropnameField);
        formPanel.add(new JLabel("Player ID:"));
        formPanel.add(playerIDField);
        formPanel.add(new JLabel("Item ID:"));
        formPanel.add(itemIDField);
        formPanel.add(new JLabel("Planted Day:"));
        formPanel.add(plantedDayField);
        formPanel.add(new JLabel("Growth Time:"));
        formPanel.add(growthTimeField);
        formPanel.add(new JLabel("Produce ID:"));
        formPanel.add(produceIDField);
        formPanel.add(new JLabel("Ready to Harvest:"));
        formPanel.add(harvestCheck);

        add(formPanel, BorderLayout.EAST);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        backButton = new JButton("Back to Menu");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Listeners
        addButton.addActionListener(e -> addCrop());
        updateButton.addActionListener(e -> updateCrop());
        deleteButton.addActionListener(e -> deleteCrop());
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "MainMenu"));

        table.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());
    }

    private void loadCrops() {
        model.setRowCount(0);
        List<Crop> crops = cropDAO.getAllCropsByPlayer(1); // default playerID for now
        for (Crop crop : crops) {
            model.addRow(new Object[]{
                    crop.getCropID(),
                    crop.getCropname(),
                    crop.getPlayerID(),
                    crop.getItemID(),
                    crop.getPlantedDay(),
                    crop.getGrowthTime(),
                    crop.getProduceID(),
                    crop.isReadyToHarvest()
            });
        }
    }

    private void fillFormFromTable() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            cropnameField.setText(model.getValueAt(selectedRow, 1).toString());
            playerIDField.setText(model.getValueAt(selectedRow, 2).toString());
            itemIDField.setText(model.getValueAt(selectedRow, 3).toString());
            plantedDayField.setText(model.getValueAt(selectedRow, 4).toString());
            growthTimeField.setText(model.getValueAt(selectedRow, 5).toString());
            produceIDField.setText(model.getValueAt(selectedRow, 6).toString());
            harvestCheck.setSelected((boolean) model.getValueAt(selectedRow, 7));
        }
    }

    private void addCrop() {
        Crop crop = readForm();
        if (cropDAO.addCrop(crop)) {
            JOptionPane.showMessageDialog(this, "Crop added!");
            loadCrops();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add crop.");
        }
    }

    private void updateCrop() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int cropID = (int) model.getValueAt(selectedRow, 0);
            Crop crop = readForm();
            crop.setCropID(cropID);
            if (cropDAO.updateCrop(crop)) {
                JOptionPane.showMessageDialog(this, "Crop updated!");
                loadCrops();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update crop.");
            }
        }
    }

    private void deleteCrop() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int cropID = (int) model.getValueAt(selectedRow, 0);
            if (cropDAO.deleteCrop(cropID)) {
                JOptionPane.showMessageDialog(this, "Crop deleted!");
                loadCrops();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete crop.");
            }
        }
    }

    private Crop readForm() {
        Crop crop = new Crop();
        crop.setCropname(cropnameField.getText());
        crop.setPlayerID(Integer.parseInt(playerIDField.getText()));
        crop.setItemID(Integer.parseInt(itemIDField.getText()));
        crop.setPlantedDay(Integer.parseInt(plantedDayField.getText()));
        crop.setGrowthTime(Integer.parseInt(growthTimeField.getText()));
        crop.setProduceID(Integer.parseInt(produceIDField.getText()));
        crop.setReadytToHarvest(harvestCheck.isSelected());
        return crop;
    }
}