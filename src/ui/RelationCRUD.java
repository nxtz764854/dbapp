package ui;

import dao.RelationsDAO;
import model.Relation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RelationCRUD extends JDialog {

    private JTextField playerIDField, npcIDField, heartsField, giftDayField, giftCountField;
    private JTable relationTable;
    private DefaultTableModel tableModel;
    private RelationsDAO relationsDAO = new RelationsDAO();

    public RelationCRUD(JFrame parent) {
        super(parent, "Relation Management", true);
        setSize(800, 500);
        setLocationRelativeTo(parent);

        initComponents();
        loadRelations();
        setVisible(true); // Show immediately like NPCCRUD
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Relation Details"));

        playerIDField = new JTextField();
        npcIDField = new JTextField();
        heartsField = new JTextField();
        giftDayField = new JTextField();
        giftCountField = new JTextField();

        formPanel.add(new JLabel("Player ID:"));
        formPanel.add(playerIDField);
        formPanel.add(new JLabel("NPC ID:"));
        formPanel.add(npcIDField);
        formPanel.add(new JLabel("Hearts:"));
        formPanel.add(heartsField);
        formPanel.add(new JLabel("Last Gift Day:"));
        formPanel.add(giftDayField);
        formPanel.add(new JLabel("Gift Count This Week:"));
        formPanel.add(giftCountField);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton refreshButton = new JButton("Refresh");
        JButton testButton = new JButton("Test Connection");

        addButton.addActionListener(e -> addRelation());
        updateButton.addActionListener(e -> updateRelation());
        deleteButton.addActionListener(e -> deleteRelation());
        refreshButton.addActionListener(e -> loadRelations());
        testButton.addActionListener(e -> testConnection());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(testButton);

        tableModel = new DefaultTableModel(new Object[]{"Player ID", "NPC ID", "Hearts", "Last Gift Day", "Gift Count"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        relationTable = new JTable(tableModel);
        relationTable.getSelectionModel().addListSelectionListener(e -> populateFields());

        JScrollPane scrollPane = new JScrollPane(relationTable);

        setLayout(new BorderLayout(10, 10));
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void testConnection() {
        try {
            List<Relation> relations = relationsDAO.getRelationsByPlayer(1);
            if (relations == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed - null result");
            } else if (relations.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Database connected but no relations found for Player 1.");
            } else {
                JOptionPane.showMessageDialog(this, "Database connected. Found " + relations.size() + " relations for Player 1.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadRelations() {
        try {
            tableModel.setRowCount(0);
            String playerIDStr = playerIDField.getText().trim();
            int playerID = playerIDStr.isEmpty() ? 1 : Integer.parseInt(playerIDStr);

            List<Relation> relations = relationsDAO.getRelationsByPlayer(playerID);

            if (relations == null) {
                JOptionPane.showMessageDialog(this, "Failed to load relations - null result from database");
                return;
            }

            if (relations.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No relations found for Player ID: " + playerID);
                return;
            }

            for (Relation r : relations) {
                tableModel.addRow(new Object[]{
                        r.getPlayerID(),
                        r.getNpcID(),
                        r.getNpchearts(),
                        r.getLastGiftDay(),
                        r.getGiftCountThisWeek()
                });
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Player ID format");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading relations: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void populateFields() {
        int selectedRow = relationTable.getSelectedRow();
        if (selectedRow >= 0) {
            playerIDField.setText(String.valueOf(relationTable.getValueAt(selectedRow, 0)));
            npcIDField.setText(String.valueOf(relationTable.getValueAt(selectedRow, 1)));
            heartsField.setText(String.valueOf(relationTable.getValueAt(selectedRow, 2)));
            giftDayField.setText(String.valueOf(relationTable.getValueAt(selectedRow, 3)));
            giftCountField.setText(String.valueOf(relationTable.getValueAt(selectedRow, 4)));
        }
    }

    private void addRelation() {
        try {
            Relation r = new Relation(
                    Integer.parseInt(playerIDField.getText().trim()),
                    Integer.parseInt(npcIDField.getText().trim()),
                    Integer.parseInt(heartsField.getText().trim()),
                    Integer.parseInt(giftDayField.getText().trim()),
                    Integer.parseInt(giftCountField.getText().trim())
            );

            if (relationsDAO.insertRelation(r)) {
                loadRelations();
                clearFields();
                JOptionPane.showMessageDialog(this, "Relation added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add relation.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter numeric values.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding relation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateRelation() {
        try {
            Relation r = new Relation(
                    Integer.parseInt(playerIDField.getText().trim()),
                    Integer.parseInt(npcIDField.getText().trim()),
                    Integer.parseInt(heartsField.getText().trim()),
                    Integer.parseInt(giftDayField.getText().trim()),
                    Integer.parseInt(giftCountField.getText().trim())
            );

            if (relationsDAO.updateRelation(r)) {
                loadRelations();
                clearFields();
                JOptionPane.showMessageDialog(this, "Relation updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update relation.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter numeric values.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating relation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void deleteRelation() {
        try {
            int playerID = Integer.parseInt(playerIDField.getText().trim());
            int npcID = Integer.parseInt(npcIDField.getText().trim());

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this relation?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) return;

            if (relationsDAO.deleteRelation(playerID, npcID)) {
                loadRelations();
                clearFields();
                JOptionPane.showMessageDialog(this, "Relation deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete relation.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter numeric values.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting relation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearFields() {
        playerIDField.setText("");
        npcIDField.setText("");
        heartsField.setText("");
        giftDayField.setText("");
        giftCountField.setText("");
        relationTable.clearSelection();
    }
}
