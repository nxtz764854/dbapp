package model;

public class Item {
    private int itemID;
    private String itemname;
    private String itemtype;
    private String desc;

    public Item() {}

    public Item(int itemID, String itemname, String itemtype, String desc) {
        this.itemID = itemID;
        this.itemname = itemname;
        this.itemtype = itemtype;
        this.desc = desc;
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

    public String getDesc() { 
        return desc; 
    }

    public void setDesc(String desc) { 
        this.desc = desc; 
    }
}