package model;

public class Relation {
    private int playerID;
    private int npcID;
    private int npchearts;
    private int last_gift_day;
    private int gift_count_this_week;

    public Relation() {}

    public Relation(int playerID, int npcID, int npchearts, int last_gift_day, int gift_count_this_week) {
        this.playerID = playerID;
        this.npcID = npcID;
        this.npchearts = npchearts;
        this.last_gift_day = last_gift_day;
        this.gift_count_this_week = gift_count_this_week;
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

    public int getNpchearts() { 
        return npchearts; 
    }

    public void setNpchearts(int npchearts) { 
        this.npchearts = npchearts; 
    }

    public int getLast_gift_day() {
        return last_gift_day;
    }

    public void setLast_gift_day(int last_gift_day) {
        this.last_gift_day = last_gift_day;
    }

    public int getGift_count_this_week() {
        return gift_count_this_week;
    }

    public void setGift_count_this_week(int gift_count_this_week) {
        this.gift_count_this_week = gift_count_this_week;
    }
}