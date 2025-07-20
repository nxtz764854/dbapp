package service;

import dao.ItemsDAO;
import model.Item;

import java.util.List;

public class ItemService {
    private ItemsDAO itemsDAO;

    public ItemService() {
        this.itemsDAO = new ItemsDAO();
    }

    /**
     * Retrieves an item by its ID.
     */
    public Item getItemByID(int itemID) {
        return itemsDAO.getItemByID(itemID);
    }

    /**
     * Retrieves an item by its name.
     */
    public Item getItemByName(String itemname) {
        return itemsDAO.getItemByName(itemname);
    }

    /**
     * Returns a list of all available items in the database.
     */
    public List<Item> getAllItems() {
        return itemsDAO.getAllItems();
    }

    /**
     * Adds a new item to the item database.
     */
    public boolean addItem(Item item) {
        return itemsDAO.addItem(item);
    }

    /**
     * Updates details of an existing item.
     */
    public boolean updateItem(Item item) {
        return itemsDAO.updateItem(item);
    }

    /**
     * Deletes an item by ID.
     */
    public boolean deleteItem(int itemID) {
        return itemsDAO.deleteItem(itemID);
    }

    /**
     * Returns a list of items by item type (e.g., Crop, Tool, Product).
     */
    public List<Item> getItemsByType(String itemtype) {
        return itemsDAO.getItemsByType(itemtype);
    }
}
