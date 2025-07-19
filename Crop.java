public class Crop {
    private int cropID;
    private int playerID;
    private int itemID;
    private String cropname;
    private int growth_time;
    private boolean readytoharvest;

    public Crop() {}

    public Crop(int cropID, int playerID, int itemID, String cropname, int growth_time, boolean readytoharvest) {
        this.cropID = cropID;
        this.playerID = playerID;
        this.itemID = itemID;
        this.cropname = cropname;
        this.growth_time = growth_time;
        this.readytoharvest = readytoharvest;
    }

    public int getCropID() { 
        return cropID; 
    }

    public void setCropID(int cropID) { 
        this.cropID = cropID; 
    }

    public int getPlayerID() { 
        return playerID; 
    }

    public void setPlayerID(int playerID) { 
        this.playerID = playerID; 
    }

    public int getItemID() { 
        return itemID; 
    }

    public void setItemID(int itemID) { 
        this.itemID = itemID; 
    }

    public String getCropname() { 
        return cropname; 
    }

    public void setCropname(String cropname) { 
        this.cropname = cropname; 
    }

    public int getGrowth_time() { 
        return growth_time; 
    }

    public void setGrowth_time(int growth_time) { 
        this.growth_time = growth_time; 
    }

    public boolean isReadytoharvest() { 
        return readytoharvest; 
    }

    public void setReadytoharvest(boolean readytoharvest) { 
        this.readytoharvest = readytoharvest; 
    }
}
 