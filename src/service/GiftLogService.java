package service;

import dao.GiftDAO;
import model.GiftLog;

import java.util.List;

public class GiftLogService {
    private GiftDAO giftDAO = new GiftDAO();

    // Log a new gift
    public boolean logGift(GiftLog log) {
        return giftDAO.insertGiftLog(log);
    }

    // Get all gift logs for a player
    public List<GiftLog> getGiftLogsByPlayer(int playerID) {
        return giftDAO.getGiftLogsByPlayer(playerID);
    }

    // Get all gift logs for a player and specific NPC
    public List<GiftLog> getGiftLogsByPlayerAndNPC(int playerID, int npcID) {
        return giftDAO.getGiftLogsByPlayerAndNPC(playerID, npcID);
    }

    // Get the latest gift log for a player and specific NPC
    public GiftLog getLatestGiftLog(int playerID, int npcID) {
        return giftDAO.getLatestGiftLog(playerID, npcID);
    }

    public List<GiftLog> getDetailedGiftLogs(int playerID) {
        return giftDAO.getGiftLogsDetailedByPlayer(playerID);
    }
}