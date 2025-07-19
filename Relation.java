public class Relation {
    private int playerID;
    private String npcname;
    private int npchearts;

    public Relation() {}

    public Relation(int playerID, String npcname, int npchearts) {
        this.playerID = playerID;
        this.npcname = npcname;
        this.npchearts = npchearts;
    }

    public int getPlayerID() { 
        return playerID; 
    }
    
    public void setPlayerID(int playerID) { 
        this.playerID = playerID; 
    }

    public String getNpcname() { 
        return npcname; 
    }

    public void setNpcname(String npcname) {
        this.npcname = npcname; 
    }

    public int getNpchearts() { 
        return npchearts; 
    }

    public void setNpchearts(int npchearts) { 
        this.npchearts = npchearts; 
    }
}