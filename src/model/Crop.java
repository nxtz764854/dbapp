package model;

public class Crop {
    private int cropID;
    private String cropname;
    private int playerID;
    private int itemID;
    private int planted_day;
    private int growth_time;
    private int produceID;
    private boolean readytoharvest;

    public Crop() {}

    public Crop(int cropID, String cropname, int playerID, int itemID, int planted_day, int growth_time, int produceID, boolean readytoharvest) { 
        this.cropID = cropID;
        this.cropname = cropname;
        this.playerID = playerID; 
        this.itemID = itemID; 
        this.planted_day = planted_day;
        this.growth_time = growth_time; 
        this.produceID = produceID; 
        this.readytoharvest = readytoharvest; 
    }

    public int getCropID() { 
        return cropID; 
    }

    public void setCropID(int cropID) { 
        this.cropID = cropID; 
    }

    public String getCropname() { 
        return cropname; 
    }

    public void setCropname(String cropname) { 
        this.cropname = cropname; 
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

    public int GetPlanted_day() { 
        return planted_day; 
    }

    public void setPlanted_day(int planted_day) {
        this.planted_day = planted_day;
    } 

    public int getGrowth_time() { 
        return growth_time; 
    }

    public void setGrowth_time(int growth_time) { 
        this.growth_time = growth_time; 
    }

    public int getProduceID() { 
        return produceID; 
    }

    public void setProduceID(int produceID) { 
        this.produceID = produceID; 
    }

    public boolean isReadytoharvest() { 
        return readytoharvest; 
    }

    public void setReadytoharvest(boolean readytoharvest) { 
        this.readytoharvest = readytoharvest; 
    }
}
 