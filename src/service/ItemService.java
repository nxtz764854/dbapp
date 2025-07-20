package service;

import dao.ItemDAO;
import model.Item;

import java.util.List;

public class ItemService {
    private final ItemDAO itemDAO;

    public ItemService() {
        this.itemDAO = new ItemDAO();
    }

    public boolean addItem(Item item) {
        return itemDAO.addItem(item);
    }

    public Item getItemByID(int itemID) {
        return itemDAO.getItemByID(itemID);
    }

    public Item getItemByName(String name) {
        return itemDAO.getItemByName(name);
    }

    public List<Item> getAllItems() {
        return itemDAO.getAllItems();
    }

    public List<Item> getItemsByType(String itemType) {
        return itemDAO.getItemsByType(itemType);
    }

    public boolean updateItem(Item item) {
        return itemDAO.updateItem(item);
    }

    public boolean deleteItem(int itemID) {
        return itemDAO.deleteItem(itemID);
    }
}
