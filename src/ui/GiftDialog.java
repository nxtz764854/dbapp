package ui;

import dao.ItemDAO;
import model.Item;
import model.NPC;
import model.Player;
import model.GiftLog;
import model.Inventory;
import model.Relation;
import service.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GiftDialog extends JDialog {
    private JComboBox<String> itemDropdown;
    private JComboBox<String> npcDropdown;
    private JButton giveButton;
    private InventoryService inventoryService;
    private ItemService itemService;
    private PlayerService playerService;
    private GiftLogService giftLogService;
    private RelationService relationService;
    private NPCService npcService;
    private int playerId;

    public GiftDialog(JFrame parent, java.sql.Connection conn, int playerId) {
        super(parent, "Give a Gift", true);
        this.playerId = playerId;
        setSize(400, 250);
        setLocationRelativeTo(parent);

        inventoryService = new InventoryService();
        itemService = new ItemService();
        playerService = new PlayerService();
        giftLogService = new GiftLogService();
        relationService = new RelationService();
        npcService = new NPCService();

        setLayout(new GridLayout(4, 2, 10, 10));

        // Dropdowns
        add(new JLabel("Select Item:"));
        itemDropdown = new JComboBox<>();
        add(itemDropdown);

        add(new JLabel("Select NPC:"));
        npcDropdown = new JComboBox<>();
        add(npcDropdown);

        giveButton = new JButton("Give Gift");
        add(new JLabel());  // Spacer
        add(giveButton);

        populateItemDropdown();
        populateNPCDropdown();

        giveButton.addActionListener(e -> giveGift());
    }

    private void populateItemDropdown() {
        itemDropdown.removeAllItems();
        List<Inventory> inventoryList = inventoryService.getInventoryByPlayerID(playerId);
        ItemService itemService = new ItemService();

        for (Inventory inv : inventoryList) {
            Item item = itemService.getItemByID(inv.getItemID());
            if (item != null) {
                itemDropdown.addItem(item.getItemname());
            }
        }
    }

    private void populateNPCDropdown() {
        npcDropdown.removeAllItems();
        List<NPC> npcs = npcService.getAllNPCs();
        for (NPC npc : npcs) {
            npcDropdown.addItem(npc.getNpcname());
        }
    }

    private void giveGift() {
        String itemName = (String) itemDropdown.getSelectedItem();
        String npcName = (String) npcDropdown.getSelectedItem();

        if (itemName == null || npcName == null) {
            JOptionPane.showMessageDialog(this, "Please select both an item and an NPC.");
            return;
        }

        Item item = itemService.getItemByName(itemName);
        NPC npc = npcService.getNPCByName(npcName);

        if (item == null || npc == null) {
            JOptionPane.showMessageDialog(this, "Error: Could not find item or NPC.");
            return;
        }

        int npcID = npc.getNpcID();
        int itemID = item.getItemID();

        // Get date context from player
        Player player = playerService.getPlayerByID(playerId);
        if (player == null) {
            JOptionPane.showMessageDialog(this, "Error: Player not found.");
            return;
        }

        String season = player.getCurrent_season();
        int day = player.getCurrent_day();
        int year = player.getCurrent_year();

        // Log the gift
        GiftLog log = new GiftLog(playerId, npcID, itemID, season, day, year);
        boolean logged = giftLogService.logGift(log);

        // Remove gift from inventory
        boolean removed = inventoryService.removeItemFromInventory(playerId, itemID, 1);

        // Update or create relation
        Relation relation = relationService.getRelation(playerId, npcID);
        if (relation == null) {
            relationService.createRelation(playerId, npcID);
            relationService.incrementHearts(playerId, npcID, 1);
        } else {
            relationService.incrementHearts(playerId, npcID, 1);
            relationService.updateGiftStats(playerId, npcID, day, relation.getGiftCountThisWeek() + 1);
        }

        if (logged && removed) {
            JOptionPane.showMessageDialog(this, "Gift given successfully to " + npcName + "!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error giving gift. Check inventory or logs.");
        }
    }
}
