package service;

import dao.HarvestDAO;
import model.HarvestLog;

import java.util.List;

public class HarvestLogService {
    private HarvestDAO harvestDAO;

    public HarvestLogService() {
        this.harvestDAO = new HarvestDAO();
    }

    public boolean recordHarvest(HarvestLog log) {
        return harvestDAO.insertHarvestLog(log);
    }

    public List<HarvestLog> getHarvestLogsByPlayer(int playerID) {
        return harvestDAO.getHarvestLogsByPlayer(playerID);
    }

    public List<HarvestLog> getHarvestLogsBySeasonAndYear(int playerID, String season, int year) {
        return harvestDAO.getHarvestLogsBySeasonAndYear(playerID, season, year);
    }

    public int getTotalHarvestedQuantityByItem(int playerID, int itemID) {
        return harvestDAO.getTotalHarvestedQuantityByItem(playerID, itemID);
    }
}

