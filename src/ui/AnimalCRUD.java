package ui;

import dao.AnimalDAO;
import model.Animal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AnimalCRUD extends JPanel {
    private AnimalDAO animalDAO = new AnimalDAO();
    private JTable table;
    private DefaultTableModel tableModel;

    public AnimalCRUD(DBGui parent) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Manage Animals", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Table model setup
        tableModel = new DefaultTableModel(new Object[]{"ID", "Player ID", "Item ID", "Name", "Age", "Produce Days", "Produce ID", "Ready"}, 0);
        table = new JTable(tableModel);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton backBtn = new JButton("Back");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        addBtn.addActionListener(e -> showAnimalDialog(null));
        updateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                Animal animal = extractAnimalFromRow(row);
                showAnimalDialog(animal);
            } else {
                JOptionPane.showMessageDialog(this, "Select an animal to update.");
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) tableModel.getValueAt(row, 0);
                if (animalDAO.deleteAnimal(id)) {
                    refreshTable();
                    JOptionPane.showMessageDialog(this, "Animal deleted.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select an animal to delete.");
            }
        });

        backBtn.addActionListener(e -> parent.showMainMenu());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Animal> animals = animalDAO.getAnimalsByPlayerID(1); // Default playerID
        for (Animal a : animals) {
            tableModel.addRow(new Object[]{
                    a.getAnimalID(), a.getPlayerID(), a.getItemID(), a.getAnimalname(),
                    a.getAge(), a.getProducedays(), a.getProduceID(), a.isReadytoharvest()
            });
        }
    }

    private void showAnimalDialog(Animal existing) {
        JTextField playerField = new JTextField();
        JTextField itemField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField produceDaysField = new JTextField();
        JTextField produceIDField = new JTextField();
        JCheckBox readyBox = new JCheckBox("Ready to Harvest");

        if (existing != null) {
            playerField.setText(String.valueOf(existing.getPlayerID()));
            itemField.setText(String.valueOf(existing.getItemID()));
            nameField.setText(existing.getAnimalname());
            ageField.setText(String.valueOf(existing.getAge()));
            produceDaysField.setText(String.valueOf(existing.getProducedays()));
            produceIDField.setText(String.valueOf(existing.getProduceID()));
            readyBox.setSelected(existing.isReadytoharvest());
        }

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Player ID:"));
        panel.add(playerField);
        panel.add(new JLabel("Item ID:"));
        panel.add(itemField);
        panel.add(new JLabel("Animal Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Produce Days:"));
        panel.add(produceDaysField);
        panel.add(new JLabel("Produce ID:"));
        panel.add(produceIDField);
        panel.add(readyBox);

        int result = JOptionPane.showConfirmDialog(this, panel, existing == null ? "Add Animal" : "Update Animal", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Animal animal = new Animal();
            animal.setPlayerID(Integer.parseInt(playerField.getText()));
            animal.setItemID(Integer.parseInt(itemField.getText()));
            animal.setAnimalname(nameField.getText());
            animal.setAge(Integer.parseInt(ageField.getText()));
            animal.setProducedays(Integer.parseInt(produceDaysField.getText()));
            animal.setProduceID(Integer.parseInt(produceIDField.getText()));
            animal.setReadytoharvest(readyBox.isSelected());

            boolean success;
            if (existing == null) {
                success = animalDAO.addAnimal(animal);
            } else {
                animal.setAnimalID(existing.getAnimalID());
                success = animalDAO.updateAnimal(animal);
            }

            if (success) {
                refreshTable();
                JOptionPane.showMessageDialog(this, "Animal saved successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Error saving animal.");
            }
        }
    }

    private Animal extractAnimalFromRow(int row) {
        Animal a = new Animal();
        a.setAnimalID((int) tableModel.getValueAt(row, 0));
        a.setPlayerID((int) tableModel.getValueAt(row, 1));
        a.setItemID((int) tableModel.getValueAt(row, 2));
        a.setAnimalname((String) tableModel.getValueAt(row, 3));
        a.setAge((int) tableModel.getValueAt(row, 4));
        a.setProducedays((int) tableModel.getValueAt(row, 5));
        a.setProduceID((int) tableModel.getValueAt(row, 6));
        a.setReadytoharvest((boolean) tableModel.getValueAt(row, 7));
        return a;
    }
}