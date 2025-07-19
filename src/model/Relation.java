package model;

public class Relation {
    private int playerID;
    private int npcID;
    private int npchearts;

    public Relation() {}

    public Relation(int playerID, int npcID, int npchearts) {
        this.playerID = playerID;
        this.npcID = npcID;
        this.npchearts = npchearts;
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
}