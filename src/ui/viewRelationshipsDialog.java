package ui;

import dao.RelationsDAO;
import service.*;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class viewRelationshipsDialog extends JDialog {
    private RelationService relationService;
    private NPCService npcService;

    public viewRelationshipsDialog(JFrame parent, int playerID) {
        super(parent, "View Relationships", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        relationService = new RelationService();
        npcService = new NPCService();

        // UI component
        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);

        // Get all relations
        List<Relation> relations = relationService.getRelationsByPlayer(playerID);
        StringBuilder sb = new StringBuilder();

        for (Relation rel : relations) {
            NPC npc = npcService.getNPCByID(rel.getNpcID());
            if (npc != null) {
                sb.append("Name: ").append(npc.getNpcname())
                        .append("\nHearts: ").append(rel.getNpchearts())
                        .append("\n\n");
            }
        }

        infoArea.setText(sb.length() > 0 ? sb.toString() : "No relationships found.");
        add(new JScrollPane(infoArea), BorderLayout.CENTER);

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}
