package ui;

import dao.NPCDAO;
import model.NPC;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class NPCCRUD extends JFrame {

    private JFrame parent;
    private JTextField nameField;
    private JCheckBox giftCheckBox;
    private JTable npcTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> tableSorter;
    private NPCDAO npcDAO = new NPCDAO();

    private JComboBox<String> sortComboBox;
    private JComboBox<String> giftFilterComboBox;
    private JTextField searchField;

    public NPCCRUD(JFrame parent) {
        super("Manage Townspeople");
        this.parent = parent;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(parent);

        initComponents();
        loadNPCs();
        setVisible(true);
    }

    private void initComponents() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        nameField = new JTextField();
        giftCheckBox = new JCheckBox("Giving Gift Today");

        inputPanel.add(new JLabel("NPC Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Gift Status:"));
        inputPanel.add(giftCheckBox);

        JPanel filterPanel = new JPanel(new FlowLayout());

        filterPanel.add(new JLabel("Search:"));
        searchField = new JTextField(15);
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                applyFilters();
            }
        });
        filterPanel.add(searchField);

        filterPanel.add(new JLabel("Sort:"));
        sortComboBox = new JComboBox<>(new String[]{"Default", "Name A-Z", "Name Z-A"});
        sortComboBox.addActionListener(e -> applySorting());
        filterPanel.add(sortComboBox);

        filterPanel.add(new JLabel("Gift Filter:"));
        giftFilterComboBox = new JComboBox<>(new String[]{"All", "Giving Gift", "Not Giving Gift"});
        giftFilterComboBox.addActionListener(e -> applyFilters());
        filterPanel.add(giftFilterComboBox);

        JButton clearFiltersButton = new JButton("Clear Filters");
        clearFiltersButton.addActionListener(e -> clearFilters());
        filterPanel.add(clearFiltersButton);

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
        tableSorter = new TableRowSorter<>(tableModel);
        npcTable.setRowSorter(tableSorter);
        npcTable.getSelectionModel().addListSelectionListener(e -> populateFields());

        JScrollPane tableScroll = new JScrollPane(npcTable);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(filterPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(tableScroll, BorderLayout.EAST);
    }

    private void loadNPCs() {
        tableModel.setRowCount(0);
        List<NPC> npcs = npcDAO.getAllNPCs();
        for (NPC npc : npcs) {
            tableModel.addRow(new Object[]{npc.getNpcID(), npc.getNpcname(), npc.isGivinggifttoday()});
        }
        applySorting();
    }

    private void applySorting() {
        String sortOption = (String) sortComboBox.getSelectedItem();

        switch (sortOption) {
            case "Name A-Z":
                tableSorter.setSortKeys(List.of(new RowSorter.SortKey(1, SortOrder.ASCENDING)));
                break;
            case "Name Z-A":
                tableSorter.setSortKeys(List.of(new RowSorter.SortKey(1, SortOrder.DESCENDING)));
                break;
            default:
                tableSorter.setSortKeys(null);
                break;
        }
    }

    private void applyFilters() {
        String searchText = searchField.getText().toLowerCase().trim();
        String giftFilter = (String) giftFilterComboBox.getSelectedItem();

        RowFilter<DefaultTableModel, Object> filter = new RowFilter<>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                String name = entry.getStringValue(1).toLowerCase();
                boolean matchesSearch = searchText.isEmpty() || name.contains(searchText);

                boolean giftStatus = (Boolean) entry.getValue(2);
                boolean matchesGift = switch (giftFilter) {
                    case "Giving Gift" -> giftStatus;
                    case "Not Giving Gift" -> !giftStatus;
                    default -> true;
                };

                return matchesSearch && matchesGift;
            }
        };

        tableSorter.setRowFilter(filter);
    }

    private void clearFilters() {
        searchField.setText("");
        sortComboBox.setSelectedIndex(0);
        giftFilterComboBox.setSelectedIndex(0);
        tableSorter.setRowFilter(null);
        applySorting();
    }

    private void populateFields() {
        int selected = npcTable.getSelectedRow();
        if (selected >= 0) {
            int modelRow = npcTable.convertRowIndexToModel(selected);
            nameField.setText((String) tableModel.getValueAt(modelRow, 1));
            giftCheckBox.setSelected((Boolean) tableModel.getValueAt(modelRow, 2));
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
            applyFilters();
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

        int modelRow = npcTable.convertRowIndexToModel(selected);
        int id = (Integer) tableModel.getValueAt(modelRow, 0);
        String name = nameField.getText().trim();
        boolean giftToday = giftCheckBox.isSelected();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "NPC name cannot be empty.");
            return;
        }

        NPC npc = new NPC();
        npc.setNpcID(id);
        npc.setNpcname(name);
        npc.setGivinggifttoday(giftToday);

        if (npcDAO.updateNPC(npc)) {
            loadNPCs();
            clearFields();
            applyFilters();
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

        int modelRow = npcTable.convertRowIndexToModel(selected);
        int id = (Integer) tableModel.getValueAt(modelRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this NPC?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION && npcDAO.deleteNPC(id)) {
            loadNPCs();
            clearFields();
            applyFilters();
        } else if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Failed to delete NPC.");
        }
    }

    private void clearFields() {
        nameField.setText("");
        giftCheckBox.setSelected(false);
        npcTable.clearSelection();
    }
}
