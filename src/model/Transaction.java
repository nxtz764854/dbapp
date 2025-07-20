package model;

import java.sql.Timestamp;

public class Transaction {
    private int transactionID;
    private int playerID;
    private int shopID;
    private String transactionType;
    private int itemID;
    private int quantity;
    private int unitPrice;
    private int totalAmount;
    private String season;
    private int day;
    private int year;
    private Timestamp timestamp;

    public int getTransactionID() { 
        return transactionID; 
    }
    public void setTransactionID(int transactionID) { 
        this.transactionID = transactionID; 
    }

    public int getPlayerID() { 
        return playerID; 
    }
    public void setPlayerID(int playerID) { 
        this.playerID = playerID; 
    }

    public int getShopID() { 
        return shopID; 
    }
    public void setShopID(int shopID) { 
        this.shopID = shopID; 
    }

    public String getTransactionType() { 
        return transactionType; 
    }
    public void setTransactionType(String transactionType) { 
        this.transactionType = transactionType; 
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

    public int getUnitPrice() { 
        return unitPrice; 
    }
    public void setUnitPrice(int unitPrice) { 
        this.unitPrice = unitPrice; 
    }

    public int getTotalAmount() { 
        return totalAmount; 
    }
    public void setTotalAmount(int totalAmount) { 
        this.totalAmount = totalAmount; 
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
