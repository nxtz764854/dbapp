package model;

import java.sql.Timestamp;

public class Transaction {
    private int transactionID;      // Unique transaction ID
    private int playerID;           // The player who made the transaction
    private String transactionType; // Type of transaction (buy or sell)
    private int itemID;             // The item involved in the transaction
    private int quantity;           // The quantity of the item transacted
    private int unitPrice;          // The price per unit of the item
    private int totalAmount;        // The total amount of the transaction
    private String season;          // The season during which the transaction occurred
    private int day;                // The day on which the transaction occurred
    private int year;               // The year in which the transaction occurred
    private Timestamp timestamp;    // Timestamp of when the transaction was made

    private String itemName;

    public Transaction() {}

    /**
     * Inserts an existing Transaction object.
     *
     * @param transactionID a unique identifier for the transaction
     * @param playerID the player who made the transaction
     * @param transactionType the type of transaction (buy or sell)
     * @param itemID the item involved in the transaction
     * @param quantity the quantity of the item transacted
     * @param unitPrice the price per unit of the item
     * @param totalAmount the total amount of the transaction
     * @param season the season during which the transaction occurred
     * @param day the day on which the transaction occurred
     * @param year the year in which the transaction occurred
     * @param timestamp the timestamp of when the transaction was made
     */
    public Transaction(int transactionID, int playerID, String transactionType, int itemID, int quantity, int unitPrice, int totalAmount, String season, int day, int year, Timestamp timestamp) {
        this.transactionID = transactionID;
        this.playerID = playerID;
        this.transactionType = transactionType;
        this.itemID = itemID;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalAmount = totalAmount;
        this.season = season;
        this.day = day;
        this.year = year;
        this.timestamp = timestamp;
    }

    /**
     * Creates a new Transaction object with the given parameters. Calculates the total amount.
     */
    public Transaction(int playerID, String transactionType, int itemID, int quantity, int unitPrice, String season, int day, int year) {
        this.playerID = playerID;
        this.transactionType = transactionType;
        this.itemID = itemID;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalAmount = unitPrice * quantity;
        this.season = season;
        this.day = day;
        this.year = year;
    }

    // Getters and setters

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public String getType() {
    return transactionType;
    }

    public int getAmount() {
        return totalAmount;
    }

    public String getDate() {
        return season + " " + day + ", Year " + year;
    }

}
