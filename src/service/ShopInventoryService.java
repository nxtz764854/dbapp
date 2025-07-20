package service;

import dao.ShopInventoryDAO;
import model.ShopInventory;

import java.util.List;

public class ShopInventoryService {
    private ShopInventoryDAO shopInventoryDAO;

    public ShopInventoryService() {
        this.shopInventoryDAO = new ShopInventoryDAO();
    }

    public boolean addItemToShop(ShopInventory shopInventory) {
        return shopInventoryDAO.addItemToShop(shopInventory);
    }

    public boolean updateItemPrice(int shopID, int itemID, int newPrice) {
        return shopInventoryDAO.updateItemPrice(shopID, itemID, newPrice);
    }

    public boolean removeItemFromShop(int shopID, int itemID) {
        return shopInventoryDAO.removeItemFromShop(shopID, itemID);
    }

    public ShopInventory getItemFromShop(int shopID, int itemID) {
        return shopInventoryDAO.getItemFromShop(shopID, itemID);
    }

    public List<ShopInventory> getItemsByShop(int shopID) {
        return shopInventoryDAO.getItemsByShop(shopID);
    }
}
