package service;

import dao.GiftDAO;
import model.GiftLog;

import java.util.List;

public class GiftLogService {
    private GiftDAO giftDAO = new GiftDAO();

    public boolean logGift(GiftLog log) {
        return giftDAO.insertGiftLog(log);
    }

    public List<GiftLog> getGiftLogsByPlayer(int playerID) {
        return giftDAO.getGiftLogsByPlayer(playerID);
    }

    public List<GiftLog> getGiftLogsByPlayerAndNPC(int playerID, int npcID) {
        return giftDAO.getGiftLogsByPlayerAndNPC(playerID, npcID);
    }

    public GiftLog getLatestGiftLog(int playerID, int npcID) {
        return giftDAO.getLatestGiftLog(playerID, npcID);
    }

    public int countGiftsGivenThisWeek(int playerID, int npcID, int year, int weekNumber) {
        return giftDAO.countGiftsGivenThisWeek(playerID, npcID, year, weekNumber);
    }

    public List<GiftLog> getGiftLogsForWeek(int playerID, int npcID, int year, int weekNumber) {
        return giftDAO.getGiftLogsForWeek(playerID, npcID, year, weekNumber);
    }
}
