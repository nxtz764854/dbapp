package service;

import dao.InventoryDAO;
import model.Inventory;

import java.util.List;

public class InventoryService {
    private InventoryDAO inventoryDAO;

    public InventoryService() {
        this.inventoryDAO = new InventoryDAO();
    }

    // Retrieves the inventory of a player by player ID.
    public List<Inventory> getInventoryByPlayerID(int playerID) {
        return inventoryDAO.getInventoryByPlayerID(playerID);
    }

    //Adds a given quantity of an item to a player's inventory.
    public boolean addItemToInventory(int playerID, int itemID, int quantity) {
        return inventoryDAO.addItemToInventory(playerID, itemID, quantity);
    }

    //Removes a given item from a player's inventory.
    public boolean removeItemFromInventory(int playerID, int itemID, int quantity) {
        return inventoryDAO.removeItemFromInventory(playerID, itemID, quantity);
    }
}
