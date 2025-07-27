package model;

public class Inventory {
    private int playerID;   // ID of the player
    private int itemID;     // ID of the item
    private int quantity;   // Quantity of the item

    public Inventory() {}

    public Inventory(int playerID, int itemID, int quantity) {
        this.playerID = playerID;
        this.itemID = itemID;
        this.quantity = quantity;
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

    public int getQuantity() { 
        return quantity; 
    
    }
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
    }
}