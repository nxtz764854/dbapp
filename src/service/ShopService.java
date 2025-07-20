package service;

import dao.ShopDAO;
import model.Shop;

import java.util.List;

public class ShopService {
    private ShopDAO shopDAO;

    public ShopService() {
        this.shopDAO = new ShopDAO();
    }

    //Add a new shop to the database
    public boolean addShop(Shop shop) {
        return shopDAO.addShop(shop);
    }

    //Update the details of an existing shop in the database
    public boolean updateShop(Shop shop) {
        return shopDAO.updateShop(shop);
    }

    //Delete the shop with the given ID from the database
    public boolean deleteShop(int shopID) {
        return shopDAO.deleteShop(shopID);
    }

    //Get the shop with the given ID
    public Shop getShopByID(int shopID) {
        return shopDAO.getShopByID(shopID);
    }

    //Get the shop with the given name
    public Shop getShopByName(String shopname) {
        return shopDAO.getShopByName(shopname);
    }

    //Get a list of all shops in the database
    public List<Shop> getAllShops() {
        return shopDAO.getAllShops();
    }
}
