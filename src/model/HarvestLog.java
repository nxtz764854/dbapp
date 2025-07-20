package model;

import java.sql.Timestamp;
public class HarvestLog {
    private int harvestID;
    private int playerID;
    private int cropID;
    private int itemID;
    private int quantity;
    private String season;
    private int day;
    private int year;
    private Timestamp timestamp;

    public int getHarvestID() { return harvestID; }
    public void setHarvestID(int harvestID) { this.harvestID = harvestID; }

    public int getPlayerID() { return playerID; }
    public void setPlayerID(int playerID) { this.playerID = playerID; }

    public int getCropID() { return cropID; }
    public void setCropID(int cropID) { this.cropID = cropID; }

    public int getItemID() { return itemID; }
    public void setItemID(int itemID) { this.itemID = itemID; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getSeason() { return season; }
    public void setSeason(String season) { this.season = season; }

    public int getDay() { return day; }
    public void setDay(int day) { this.day = day; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
}
