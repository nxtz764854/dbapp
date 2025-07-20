package model;

public class NPC {
    private int npcID;                  // Unique NPC ID
    private String npcname;             // Name of the NPC
    private boolean givinggifttoday;    // Indicates if the NPC is giving a gift today

    public NPC() {}

    public NPC(int npcID, String npcname, boolean givinggifttoday) {
        this.npcID = npcID;
        this.npcname = npcname;
        this.givinggifttoday = givinggifttoday;
    }

    public int getNpcID() { 
        return npcID; 
    }

    public void setNpcID(int npcID) { 
        this.npcID = npcID; 
    }

    public String getNpcname() { 
        return npcname; 
    }
    public void setNpcname(String npcname) { 
        this.npcname = npcname; 
    }

    public boolean isGivinggifttoday() { 
        return givinggifttoday;
    }
    public void setGivinggifttoday(boolean givinggifttoday) { 
        this.givinggifttoday = givinggifttoday; 
    }
}