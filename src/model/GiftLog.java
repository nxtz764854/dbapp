package model;

import java.sql.Timestamp;

public class GiftLog {
    private int giftID;             // Unique gift ID
    private int playerID;           // The player who gave the gift
    private int npcID;              // The NPC that received the gift
    private int itemID;             // The item that was given as a gift
    private String season;          // The season when the gift was given
    private int day;                // The day of the year when the gift was given
    private int year;               // The year when the gift was given
    private Timestamp timestamp;    // Timestamp when the gift was given

    public GiftLog() {}

    /**
     * Insert an existing GiftLog with all the information.
     *
     * @param giftID Unique gift ID
     * @param playerID The player who gave the gift
     * @param npcID The NPC that received the gift
     * @param itemID The item that was given as a gift
     * @param season The season when the gift was given
     * @param day The day of the year when the gift was given
     * @param year The year when the gift was given
     * @param timestamp Timestamp when the gift was given
     */
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

    /**
     * Create a new GiftLog with all the information except the unique gift ID.
     */
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
