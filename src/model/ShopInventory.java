package model;

public class ShopInventory {
    private int shopID;
    private int itemID;
    private int stock;
    private int price;

    public int getShopID() { 
        return shopID; 
    }

    public void setShopID(int shopID) { 
        this.shopID = shopID; 
    }

    public int getItemID() { 
        return itemID; 
    }

    public void setItemID(int itemID) { 
        this.itemID = itemID; 
    }

    public int getStock() { 
        return stock; 
    }

    public void setStock(int stock) { 
        this.stock = stock; 
    }

    public int getPrice() { 
        return price; 
    }

    public void setPrice(int price) { 
        this.price = price; 
    }
}
