package model;

public class Item {
    private int itemID;         // item ID
    private String itemname;    // item name
    private String itemtype;    // item type
    private String descript;    // item description
    private int price;          // item price
    private boolean buyable;    // whether the item is buyable

    public Item() {}

    public Item(int itemID, String itemname, String itemtype, String descript, int price, boolean buyable) {
        this.itemID = itemID;
        this.itemname = itemname;
        this.itemtype = itemtype;
        this.descript = descript;
        this.price = price;
        this.buyable = buyable;
    }

    public int getItemID() { 
        return itemID; 
    }

    public void setItemID(int itemID) { 
        this.itemID = itemID; 
    }

    public String getItemname() { 
        return itemname; 
    }

    public void setItemname(String itemname) { 
        this.itemname = itemname; 
    }

    public String getItemtype() { 
        return itemtype; 
    }

    public void setItemtype(String itemtype) { 
        this.itemtype = itemtype; 
    }

    public String getDescript() { 
        return descript; 
    }

    public void setDescript(String descript) { 
        this.descript = descript; 
    }

    public int getPrice() { 
        return price; 
    }

    public void setPrice(int price) { 
        this.price = price; 
    }

    public boolean isBuyable() { 
        return buyable; 
    }

    public void setBuyable(boolean buyable) { 
        this.buyable = buyable; 
    }

    @Override
    public String toString() {
        return itemname + ": " + price;
    }

}