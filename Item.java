public class Item {
    private int itemID;
    private String itemname;
    private String itemtype;
    private int specialvalue;
    private String desc;
    private int quantity;

    public Item() {}

    public Item(int itemID, String itemname, String itemtype, int specialvalue, String desc, int quantity) {
        this.itemID = itemID;
        this.itemname = itemname;
        this.itemtype = itemtype;
        this.specialvalue = specialvalue;
        this.desc = desc;
        this.quantity = quantity;
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

    public int getSpecialvalue() { 
        return specialvalue; 
    }

    public void setSpecialvalue(int specialvalue) { 
        this.specialvalue = specialvalue; 
    }

    public String getDesc() { 
        return desc; 
    }

    public void setDesc(String desc) { 
        this.desc = desc; 
    }

    public int getQuantity() { 
        return quantity; 
    }

    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
    }
}