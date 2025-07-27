package model;

import java.sql.Timestamp;

public class ProductLog {
    private int productlogID;       // Unique log ID
    private int playerID;           // The player who produced the product
    private int animalID;           // The animal that produced the product
    private int itemID;             // The item that was produced
    private int quantity;           // The quantity of the item that was produced
    private String season;          // The current season when the product was produced
    private int day;                // The current day when the product was produced
    private int year;               // The current year when the product was produced
    private Timestamp timestamp;    // Timestamp when the product was produced

    private String itemName;
    private String animalName;

    public ProductLog() {}

    /**
     * Inserts an existing ProductLog object with all parameters.
     * 
     * @param productlogID the unique log ID
     * @param playerID the player who produced the product
     * @param animalID the animal that produced the product
     * @param itemID the item that was produced
     * @param quantity the quantity of the item that was produced
     * @param season the current season when the product was produced
     * @param day the current day when the product was produced
     * @param year the current year when the product was produced
     * @param timestamp the timestamp when the product was produced
     */
    public ProductLog(int productlogID, int playerID, int animalID, int itemID, int quantity, String season, int day, int year, Timestamp timestamp) {
        this.productlogID = productlogID;
        this.playerID = playerID;
        this.animalID = animalID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.season = season;
        this.day = day;
        this.year = year;
        this.timestamp = timestamp;
    }

    /**
     * Creates a new ProductLog object without log ID.
     */
    public ProductLog(int playerID, int animalID, int itemID, int quantity, String season, int day, int year) {
        this.playerID = playerID;
        this.animalID = animalID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.season = season;
        this.day = day;
        this.year = year;
    }

    public int getProductlogID() { 
        return productlogID; 
    }
    
    public void setProductlogID(int productlogID) { 
        this.productlogID = productlogID; 
    }

    public int getPlayerID() { 
        return playerID; 
    }
    
    public void setPlayerID(int playerID) { 
        this.playerID = playerID; 
    }

    public int getAnimalID() { 
        return animalID; 
    }
    
    public void setAnimalID(int animalID) { 
        this.animalID = animalID; 
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

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getAnimalName() {
        return animalName;
    }

    public int getQuantityProduced() {
        return quantity;
    }

    public String getDateProduced() {
        return season + " " + day + ", Year " + year;
    }

}