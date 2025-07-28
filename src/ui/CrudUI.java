package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class CrudUI extends JPanel {
    private DBGui parent;
    private Connection conn;

    public CrudUI(DBGui parent, Connection conn) {
        this.parent = parent;
        this.conn = conn;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("CRUD Management", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        JButton playerBtn = new JButton("Manage Players");
        JButton itemBtn = new JButton("Manage Items");
        JButton cropBtn = new JButton("Manage Crops");
        JButton animalBtn = new JButton("Manage Animals");
        JButton npcBtn = new JButton("Manage NPCs");
        JButton backBtn = new JButton("Back");

        buttonPanel.add(playerBtn);
        buttonPanel.add(itemBtn);
        buttonPanel.add(cropBtn);
        buttonPanel.add(animalBtn);
        buttonPanel.add(npcBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.CENTER);

        backBtn.addActionListener(e -> parent.showMainMenu());

        playerBtn.addActionListener(e -> new PlayerCRUD(parent));
        itemBtn.addActionListener(e -> new ItemCRUD(parent));
        cropBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Manage Crops - To Be Implemented"));
        animalBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Manage Animals - To Be Implemented"));
        npcBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Manage NPCs - To Be Implemented"));
    }
}