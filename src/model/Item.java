package model;

public class Item {
    private int itemID;
    private String itemname;
    private String itemtype;
    private String descript;

    public Item() {}

    public Item(int itemID, String itemname, String itemtype, String descript) {
        this.itemID = itemID;
        this.itemname = itemname;
        this.itemtype = itemtype;
        this.descript = descript;
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
}