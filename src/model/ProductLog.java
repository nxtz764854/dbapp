package model;

import java.sql.Timestamp;

public class ProductLog {
    private int productlogID;
    private int playerID;
    private int animalID;
    private int itemID;
    private int quantity;
    private String season;
    private int day;
    private int year;
    private Timestamp timestamp;

    public ProductLog() {}

    public ProductLog(int productlogID, int playerID, int animalID, int itemID, int quantity, String season, int day, int year, Timestamp timestamp) {
        this.productlogID = productlogID;
        this.playerID = playerID;
        this.animalID = animalID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.season = season;
        this.day = day;
        this.year = year;
        this.timestamp = timestamp;
    }

    public ProductLog(int playerID, int animalID, int itemID, int quantity, String season, int day, int year) {
        this.playerID = playerID;
        this.animalID = animalID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.season = season;
        this.day = day;
        this.year = year;
    }

    public int getProductlogID() { 
        return productlogID; 
    }
    
    public void setProductlogID(int productlogID) { 
        this.productlogID = productlogID; 
    }

    public int getPlayerID() { 
        return playerID; 
    }
    
    public void setPlayerID(int playerID) { 
        this.playerID = playerID; 
    }

    public int getAnimalID() { 
        return animalID; 
    }
    
    public void setAnimalID(int animalID) { 
        this.animalID = animalID; 
    }

    public int getItemID() { 
        return itemID; 
    }
    
    public void setItemID(int itemID) { 
        this.itemID = itemID; 
    }

    public int getQuantity() { 
        return quantity; 
    }
    
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
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