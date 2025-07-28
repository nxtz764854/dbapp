package ui;

import dao.NPCDAO;
import model.NPC;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class NPCCRUD extends JFrame {

    private JTextField nameField;
    private JCheckBox giftCheckBox;
    private JTable npcTable;
    private DefaultTableModel tableModel;
    private NPCDAO npcDAO = new NPCDAO();

    public NPCCRUD(JFrame parent) {
        setTitle("Manage Townspeople");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(parent);

        initComponents();
        loadNPCs();
        setVisible(true); // <-- Show immediately
    }

    private void initComponents() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        nameField = new JTextField();
        giftCheckBox = new JCheckBox("Giving Gift Today");

        inputPanel.add(new JLabel("NPC Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Gift Status:"));
        inputPanel.add(giftCheckBox);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton refreshButton = new JButton("Refresh");

        addButton.addActionListener(e -> addNPC());
        updateButton.addActionListener(e -> updateNPC());
        deleteButton.addActionListener(e -> deleteNPC());
        refreshButton.addActionListener(e -> loadNPCs());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Giving Gift"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        npcTable = new JTable(tableModel);
        npcTable.getSelectionModel().addListSelectionListener(e -> populateFields());

        JScrollPane tableScroll = new JScrollPane(npcTable);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(tableScroll, BorderLayout.SOUTH);
    }

    private void loadNPCs() {
        tableModel.setRowCount(0);
        List<NPC> npcs = npcDAO.getAllNPCs();
        for (NPC npc : npcs) {
            tableModel.addRow(new Object[]{npc.getNpcID(), npc.getNpcname(), npc.isGivinggifttoday()});
        }
    }

    private void populateFields() {
        int selected = npcTable.getSelectedRow();
        if (selected >= 0) {
            nameField.setText((String) npcTable.getValueAt(selected, 1));
            giftCheckBox.setSelected((boolean) npcTable.getValueAt(selected, 2));
        }
    }

    private void addNPC() {
        String name = nameField.getText().trim();
        boolean giftToday = giftCheckBox.isSelected();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "NPC name cannot be empty.");
            return;
        }

        NPC npc = new NPC();
        npc.setNpcname(name);
        npc.setGivinggifttoday(giftToday);

        if (npcDAO.insertNPC(npc)) {
            loadNPCs();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add NPC.");
        }
    }

    private void updateNPC() {
        int selected = npcTable.getSelectedRow();
        if (selected < 0) {
            JOptionPane.showMessageDialog(this, "Select an NPC to update.");
            return;
        }

        int id = (int) npcTable.getValueAt(selected, 0);
        String name = nameField.getText().trim();
        boolean giftToday = giftCheckBox.isSelected();

        NPC npc = new NPC();
        npc.setNpcID(id);
        npc.setNpcname(name);
        npc.setGivinggifttoday(giftToday);

        if (npcDAO.updateNPC(npc)) {
            loadNPCs();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update NPC.");
        }
    }

    private void deleteNPC() {
        int selected = npcTable.getSelectedRow();
        if (selected < 0) {
            JOptionPane.showMessageDialog(this, "Select an NPC to delete.");
            return;
        }

        int id = (int) npcTable.getValueAt(selected, 0);
        if (npcDAO.deleteNPC(id)) {
            loadNPCs();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete NPC.");
        }
    }

    private void clearFields() {
        nameField.setText("");
        giftCheckBox.setSelected(false);
        npcTable.clearSelection();
    }
}
