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
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));

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

        addButton.addActionListener(e -> addRelation());
        updateButton.addActionListener(e -> updateRelation());
        deleteButton.addActionListener(e -> deleteRelation());
        refreshButton.addActionListener(e -> loadRelations());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

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

    private void loadRelations() {
        tableModel.setRowCount(0);
        String playerIDStr = playerIDField.getText().trim();
        int playerID = playerIDStr.isEmpty() ? 1 : Integer.parseInt(playerIDStr);

        List<Relation> relations = relationsDAO.getRelationsByPlayer(playerID);
        for (Relation r : relations) {
            tableModel.addRow(new Object[]{
                    r.getPlayerID(),
                    r.getNpcID(),
                    r.getNpchearts(),
                    r.getLastGiftDay(),
                    r.getGiftCountThisWeek()
            });
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
                    Integer.parseInt(playerIDField.getText()),
                    Integer.parseInt(npcIDField.getText()),
                    Integer.parseInt(heartsField.getText()),
                    Integer.parseInt(giftDayField.getText()),
                    Integer.parseInt(giftCountField.getText())
            );
            if (relationsDAO.insertRelation(r)) {
                loadRelations();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add relation.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void updateRelation() {
        try {
            Relation r = new Relation(
                    Integer.parseInt(playerIDField.getText()),
                    Integer.parseInt(npcIDField.getText()),
                    Integer.parseInt(heartsField.getText()),
                    Integer.parseInt(giftDayField.getText()),
                    Integer.parseInt(giftCountField.getText())
            );
            if (relationsDAO.updateRelation(r)) {
                loadRelations();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update relation.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void deleteRelation() {
        try {
            int playerID = Integer.parseInt(playerIDField.getText());
            int npcID = Integer.parseInt(npcIDField.getText());
            if (relationsDAO.deleteRelation(playerID, npcID)) {
                loadRelations();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete relation.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
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

