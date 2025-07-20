package model;

import java.sql.Timestamp;
public class HarvestLog {
    private int harvestID;          // unique harvest log ID
    private int playerID;           // player who harvested the crop
    private int cropID;             // the crop that was harvested
    private int itemID;             // the item that was produced by harvesting the crop
    private int quantity;           // quantity of the item that was produced
    private String season;          // the season when the crop was harvested
    private int day;                // the day when the crop was harvested
    private int year;               // the year when the crop was harvested
    private Timestamp timestamp;    // timestamp when the crop was harvested

    private String itemName;
    private String cropName;

    public HarvestLog() {}

    /**
     * Inserts an existing HarvestLog with all parameters.
     *
     * @param harvestID  unique harvest log ID
     * @param playerID   player who harvested the crop
     * @param cropID     the crop that was harvested
     * @param itemID     the item that was produced by harvesting the crop
     * @param quantity   quantity of the item that was produced
     * @param season     the season when the crop was harvested
     * @param day        the day when the crop was harvested
     * @param year       the year when the crop was harvested
     * @param timestamp  timestamp when the crop was harvested
     */
    public HarvestLog(int harvestID, int playerID, int cropID, int itemID, int quantity, String season, int day, int year, Timestamp timestamp) {
        this.harvestID = harvestID;
        this.playerID = playerID;
        this.cropID = cropID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.season = season;
        this.day = day;
        this.year = year;
        this.timestamp = timestamp;
    }

    /**
     * Constructs a HarvestLog object without harvestID. This is used for inserting a new harvest log into the database.
     */
    public HarvestLog(int playerID, int cropID, int itemID, int quantity, String season, int day, int year) {
        this.playerID = playerID;
        this.cropID = cropID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.season = season;
        this.day = day;
        this.year = year;
    }

    public int getHarvestID() {
        return harvestID;
    }

    public void setHarvestID(int harvestID) {
        this.harvestID = harvestID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getCropID() {
        return cropID;
    }

    public void setCropID(int cropID) {
        this.cropID = cropID;
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

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCropName() {
        return cropName;
    }

    public String getDateHarvested() {
    return season + " " + day + ", Year " + year;
}
}
