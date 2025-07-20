package service;

import dao.HarvestDAO;
import model.HarvestLog;

import java.util.List;

public class HarvestLogService {
    private HarvestDAO harvestDAO;

    public HarvestLogService() {
        this.harvestDAO = new HarvestDAO();
    }

    // Records a harvest log entry in the database. Returns true if the operation is successful.
    public boolean recordHarvest(HarvestLog log) {
        return harvestDAO.insertHarvestLog(log);
    }

    // Fetches all harvest logs associated with a player. Returns an empty list if no logs are found.
    public List<HarvestLog> getHarvestLogsByPlayer(int playerID) {
        return harvestDAO.getHarvestLogsByPlayer(playerID);
    }

    // Fetches all harvest logs associated with a player for a given season and year.
    // Returns an empty list if no logs are found.
    public List<HarvestLog> getHarvestLogsBySeasonAndYear(int playerID, String season, int year) {
        return harvestDAO.getHarvestLogsBySeasonAndYear(playerID, season, year);
    }

    // Fetches the total quantity of a given item harvested by a player. Returns 0 if the item has not been harvested.
    public int getTotalHarvestedQuantityByItem(int playerID, int itemID) {
        return harvestDAO.getTotalHarvestedQuantityByItem(playerID, itemID);
    }

    public List<HarvestLog> getHarvestLogsDetailedByPlayer(int playerID) {
    return harvestDAO.getHarvestLogsDetailedByPlayer(playerID);
}
}

