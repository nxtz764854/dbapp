package model;

public class Crop {
    private int cropID;             // Unique crop ID
    private String cropname;        // Name of the crop
    private int playerID;           // The player who owns the crop
    private int itemID;             // The item ID associated with the crop
    private int planted_day;        // The day the crop was planted
    private int growth_time;        // The number of days required for the crop to grow
    private int produceID;          // The item ID of the produce after harvesting
    private boolean readytoharvest; // Indicates if the crop is ready to be harvested

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

    public int getPlantedDay() { 
        return planted_day; 
    }

    public void setPlantedDay(int planted_day) {
        this.planted_day = planted_day;
    } 

    public int getGrowthTime() { 
        return growth_time; 
    }

    public void setGrowthTime(int growth_time) { 
        this.growth_time = growth_time; 
    }

    public int getProduceID() { 
        return produceID; 
    }

    public void setProduceID(int produceID) { 
        this.produceID = produceID; 
    }

    public boolean isReadyToHarvest() { 
        return readytoharvest; 
    }

    public void setReadytToHarvest(boolean readytoharvest) { 
        this.readytoharvest = readytoharvest; 
    }
}
 