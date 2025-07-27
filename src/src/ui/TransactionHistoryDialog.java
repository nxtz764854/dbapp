package ui;

import model.Transaction;
import service.TransactionService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TransactionHistoryDialog extends JDialog {
    private final TransactionService transactionService = new TransactionService();

    public TransactionHistoryDialog(JFrame parent, int playerID) {
        super(parent, "Transaction History", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        List<Transaction> transactions = transactionService.getTransactionsDetailedByPlayer(playerID);
        StringBuilder sb = new StringBuilder();
        for (Transaction t : transactions) {
            sb.append("[").append(t.getSeason()).append(" ")
            .append(t.getDay()).append(", Year ").append(t.getYear()).append("] ")
            .append(t.getTransactionType().toUpperCase()).append(" - ")
            .append("Item: ").append(t.getItemName() != null ? t.getItemName() : "Unknown")
            .append(" x").append(t.getQuantity())
            .append(" for ").append(t.getUnitPrice()).append(" gold\n");
        }


        textArea.setText(sb.toString());
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        setVisible(true);
    }
}
