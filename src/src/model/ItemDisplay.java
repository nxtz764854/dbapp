package model;

public class ItemDisplay {
    private int itemId;
    private String itemName;

    public ItemDisplay(int itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    @Override
    public String toString() {
        return itemName; 
    }
}
