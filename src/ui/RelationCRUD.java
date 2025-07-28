package ui;

import dao.RelationsDAO;
import model.Relation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RelationCRUD extends JFrame {

    private JTextField playerIDField, npcIDField, heartsField;
    private JTable relationTable;
    private DefaultTableModel tableModel;
    private RelationsDAO relationsDAO = new RelationsDAO();

    public RelationCRUD(JFrame parent) {
        setTitle("Relation Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(parent);

        initComponents();
        loadRelations();
        setVisible(true);
    }

    private void initComponents() {
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        playerIDField = new JTextField();
        npcIDField = new JTextField();
        heartsField = new JTextField();

        inputPanel.add(new JLabel("Player ID:"));
        inputPanel.add(playerIDField);
        inputPanel.add(new JLabel("NPC ID:"));
        inputPanel.add(npcIDField);
        inputPanel.add(new JLabel("Hearts:"));
        inputPanel.add(heartsField);

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

        tableModel = new DefaultTableModel(new Object[]{"Player ID", "NPC ID", "Hearts"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        relationTable = new JTable(tableModel);
        relationTable.getSelectionModel().addListSelectionListener(e -> populateFields());
        JScrollPane scrollPane = new JScrollPane(relationTable);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
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
                    r.getNpchearts()
            });
        }
    }

    private void populateFields() {
        int selected = relationTable.getSelectedRow();
        if (selected >= 0) {
            playerIDField.setText(String.valueOf(relationTable.getValueAt(selected, 0)));
            npcIDField.setText(String.valueOf(relationTable.getValueAt(selected, 1)));
            heartsField.setText(String.valueOf(relationTable.getValueAt(selected, 2)));
        }
    }

    private void addRelation() {
        try {
            Relation r = new Relation(
                    Integer.parseInt(playerIDField.getText()),
                    Integer.parseInt(npcIDField.getText()),
                    Integer.parseInt(heartsField.getText())
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
                    Integer.parseInt(heartsField.getText())
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
        relationTable.clearSelection();
    }
}
