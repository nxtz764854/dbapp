package model;

import java.sql.Timestamp;

public class GiftLog {
    private int giftID;
    private int playerID;
    private int npcID;
    private int itemID;
    private String season;
    private int day;
    private int year;
    private Timestamp timestamp;

    public GiftLog() {}

    public GiftLog(int giftID, int playerID, int npcID, int itemID, String season, int day, int year, Timestamp timestamp) {
        this.giftID = giftID;
        this.playerID = playerID;
        this.npcID = npcID;
        this.itemID = itemID;
        this.season = season;
        this.day = day;
        this.year = year;
        this.timestamp = timestamp;
    }

    public GiftLog(int playerID, int npcID, int itemID, String season, int day, int year) {
        this.playerID = playerID;
        this.npcID = npcID;
        this.itemID = itemID;
        this.season = season;
        this.day = day;
        this.year = year;
    }


    public int getGiftID() { 
        return giftID; 
    }

    public void setGiftID(int giftID) { 
        this.giftID = giftID; 
    }

    public int getPlayerID() { 
        return playerID; 
    }

    public void setPlayerID(int playerID) { 
        this.playerID = playerID; 
    }

    public int getNpcID() { 
        return npcID; 
    }

    public void setNpcID(int npcID) { 
        this.npcID = npcID; 
    }

    public int getItemID() { 
        return itemID; 
    }
    
    public void setItemID(int itemID) { 
        this.itemID = itemID; 
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
}
