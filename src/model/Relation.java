package model;

public class Relation {
    private int playerID;           // The player who has the relation with the NPC
    private int npcID;              // The NPC with whom the player has a relation
    private int npchearts;          // The number of hearts the player has with the NPC
    private int lastGiftDay;        // Track when last gift was given
    private int giftCountThisWeek;  // Track number of gifts given this week

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