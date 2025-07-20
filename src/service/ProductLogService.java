package service;

import dao.ProductDAO;
import model.ProductLog;

import java.util.List;

public class ProductLogService {
    private ProductDAO productDAO;

    public ProductLogService() {
        this.productDAO = new ProductDAO();
    }

    public boolean logProductCollection(ProductLog log) {
        return productDAO.insertProductLog(log);
    }

    public List<ProductLog> getAllProductLogs(int playerID) {
        return productDAO.getProductLogsByPlayer(playerID);
    }

    public List<ProductLog> getProductLogsBySeasonAndYear(int playerID, String season, int year) {
        return productDAO.getProductLogsBySeasonAndYear(playerID, season, year);
    }

    public int getTotalProducedQuantityByItem(int playerID, int itemID) {
        return productDAO.getTotalProducedQuantityByItem(playerID, itemID);
    }
}
