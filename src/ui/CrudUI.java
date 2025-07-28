package ui;

import ui.CropCRUD;
import ui.ItemCRUD;
import ui.PlayerCRUD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class CrudUI extends JPanel {
    private DBGui parent;
    private Connection conn;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public CrudUI(DBGui parent, Connection conn) {
        this.parent = parent;
        this.conn = conn;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("CRUD Management", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Card panel to switch between views
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Main menu panel
        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        JButton playerBtn = new JButton("Manage Players");
        JButton itemBtn = new JButton("Manage Items");
        JButton invBtn = new JButton("Manage Inventories");
        JButton cropBtn = new JButton("Manage Crops");
        JButton animalBtn = new JButton("Manage Animals");
        JButton npcBtn = new JButton("Manage NPCs");
        JButton backBtn = new JButton("Back");

        buttonPanel.add(playerBtn);
        buttonPanel.add(itemBtn);
        buttonPanel.add(invBtn);
        buttonPanel.add(cropBtn);
        buttonPanel.add(animalBtn);
        buttonPanel.add(npcBtn);
        buttonPanel.add(backBtn);

        JPanel menuWrapper = new JPanel(new BorderLayout());
        menuWrapper.add(buttonPanel, BorderLayout.CENTER);

        cardPanel.add(menuWrapper, "MainMenu");
        cardPanel.add(new CropCRUD(cardLayout, cardPanel), "Crop");
        cardPanel.add(new AnimalCRUD(parent), "Animal");

        add(cardPanel, BorderLayout.CENTER);

        // Button actions
        backBtn.addActionListener(e -> parent.showMainMenu());

        playerBtn.addActionListener(e -> new PlayerCRUD(parent));
        itemBtn.addActionListener(e -> new ItemCRUD(parent));
        invBtn.addActionListener(e -> new InventoryCRUD(parent));
        cropBtn.addActionListener(e -> cardLayout.show(cardPanel, "Crop"));
        animalBtn.addActionListener(e -> cardLayout.show(cardPanel, "Animal"));
        npcBtn.addActionListener(e -> new NPCCRUD(parent));
    }

    public void showMainMenu() {
        cardLayout.show(cardPanel, "MainMenu");
    }
}
