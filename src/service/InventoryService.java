package service;

import dao.InventoryDAO;
import model.Inventory;

import java.util.List;

public class InventoryService {
    private InventoryDAO inventoryDAO;

    public InventoryService(InventoryDAO inventoryDAO) {
        this.inventoryDAO = inventoryDAO;
    }

    public List<Inventory> getInventoryByPlayerID(int playerID) {
        return inventoryDAO.getInventoryByPlayerID(playerID);
    }

    public boolean addItemToInventory(int playerID, int itemID, int quantity) {
        return inventoryDAO.addItemToInventory(playerID, itemID, quantity);
    }

    public boolean removeItemFromInventory(int playerID, int itemID) {
        return inventoryDAO.removeItemFromInventory(playerID, itemID);
    }
}
