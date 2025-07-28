package ui;

import model.Player;
import service.PlayerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PlayerCRUD extends JDialog {
    private PlayerService playerService = new PlayerService();
    private JTable table;
    private DefaultTableModel tableModel;

    public PlayerCRUD(JFrame parent) {
        super(parent, "Manage Players", true);
        setSize(700, 400);
        setLocationRelativeTo(parent);

        setLayout(new BorderLayout());

        // Table setup
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Wallet", "Day", "Season", "Year"}, 0);
        table = new JTable(tableModel);
        refreshTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel();

        JButton addBtn = new JButton("Add Player");
        JButton updateBtn = new JButton("Update Selected");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton closeBtn = new JButton("Close");

        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(closeBtn);
        add(btnPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> showAddDialog());
        updateBtn.addActionListener(e -> showUpdateDialog());
        deleteBtn.addActionListener(e -> deleteSelectedPlayer());
        closeBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Player> players = playerService.getAllPlayers();
        for (Player p : players) {
            tableModel.addRow(new Object[]{
                    p.getPlayerID(),
                    p.getPlayername(),
                    p.getWallet(),
                    p.getCurrent_day(),
                    p.getCurrent_season(),
                    p.getCurrent_year()
            });
        }
    }

    private void showAddDialog() {
        JTextField nameField = new JTextField();
        Object[] msg = {"Player Name:", nameField};
        int result = JOptionPane.showConfirmDialog(this, msg, "Add Player", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            if (!name.isEmpty()) {
                Player newPlayer = new Player(name);
                if (playerService.createPlayer(newPlayer)) {
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add player.");
                }
            }
        }
    }

    private void showUpdateDialog() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a player to update.");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        String name = (String) tableModel.getValueAt(row, 1);
        int wallet = (int) tableModel.getValueAt(row, 2);
        int day = (int) tableModel.getValueAt(row, 3);
        String season = (String) tableModel.getValueAt(row, 4);
        int year = (int) tableModel.getValueAt(row, 5);

        JTextField nameField = new JTextField(name);
        JTextField walletField = new JTextField(String.valueOf(wallet));
        JTextField dayField = new JTextField(String.valueOf(day));
        JTextField seasonField = new JTextField(season);
        JTextField yearField = new JTextField(String.valueOf(year));

        Object[] msg = {
                "Name:", nameField,
                "Wallet:", walletField,
                "Day:", dayField,
                "Season:", seasonField,
                "Year:", yearField
        };

        int result = JOptionPane.showConfirmDialog(this, msg, "Update Player", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Player updated = new Player(id, nameField.getText(),
                        Integer.parseInt(walletField.getText()),
                        Integer.parseInt(dayField.getText()),
                        seasonField.getText(),
                        Integer.parseInt(yearField.getText()));

                if (playerService.updatePlayer(updated)) {
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Update failed.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input.");
            }
        }
    }

    private void deleteSelectedPlayer() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a player to delete.");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete player ID " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (playerService.deletePlayer(id)) {
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Delete failed.");
            }
        }
    }
}