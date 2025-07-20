package model;

public class Relation {
    private int playerID;
    private int npcID;
    private int npchearts;
    private int lastGiftDay;
    private int giftCountThisWeek;

    public Relation() {}

    public Relation(int playerID, int npcID, int npchearts, int lastGiftDay, int giftCountThisWeek) {
        this.playerID = playerID;
        this.npcID = npcID;
        this.npchearts = npchearts;
        this.lastGiftDay = lastGiftDay;
        this.giftCountThisWeek = giftCountThisWeek;
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

    public int getLastGiftDay() {
        return lastGiftDay;
    }

    public void setLastGiftDay(int lastGiftDay) {
        this.lastGiftDay = lastGiftDay;
    }

    public int getGiftCountThisWeek() {
        return giftCountThisWeek;
    }

    public void setGiftCountThisWeek(int giftCountThisWeek) {
        this.giftCountThisWeek = giftCountThisWeek;
    }
}