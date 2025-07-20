package service;

import dao.TransactionDAO;
import model.Transaction;

import java.util.List;

public class TransactionService {
    private TransactionDAO transactionDAO;

    public TransactionService() {
        this.transactionDAO = new TransactionDAO();
    }

    public boolean recordTransaction(Transaction transaction) {
        return transactionDAO.insertTransaction(transaction);
    }

    public List<Transaction> getTransactionsByPlayer(int playerID) {
        return transactionDAO.getTransactionsByPlayer(playerID);
    }

    public List<Transaction> getTransactionsBySeasonAndYear(int playerID, String season, int year) {
        return transactionDAO.getTransactionsBySeasonAndYear(playerID, season, year);
    }

    public int getTotalSpentByPlayer(int playerID) {
        return transactionDAO.getTotalSpentByPlayer(playerID);
    }

    public int getTotalEarnedByPlayer(int playerID) {
        return transactionDAO.getTotalEarnedByPlayer(playerID);
    }

    public List<Transaction> getTransactionsDetailedByPlayer(int playerID) {
        return transactionDAO.getTransactionsDetailedByPlayer(playerID);
    }
}