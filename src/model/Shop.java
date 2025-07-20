package model;

public class Shop {
    private int shopID;
    private String shopname;
    private int ownerNpcID;

    public int getShopID() { 
        return shopID; 
    }

    public void setShopID(int shopID) { 
        this.shopID = shopID; 
    }

    public String getShopname() { 
        return shopname; 
    }

    public void setShopname(String shopname) { 
        this.shopname = shopname; 
    }

    public int getOwnerNpcID() { 
        return ownerNpcID; 
    }

    public void setOwnerNpcID(int ownerNpcID) { 
        this.ownerNpcID = ownerNpcID; 
    }
}
