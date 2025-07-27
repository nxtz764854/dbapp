package service;

import dao.LogDAO;
import model.Log;

import java.util.List;

public class LogService {
    private LogDAO logDAO;

    public LogService() {
        this.logDAO = new LogDAO();
    }

    public boolean logAction(Log log) {
        return logDAO.insertLog(log);
    }

    public List<Log> getLogsByPlayer(int playerID) {
        return logDAO.getLogsByPlayer(playerID);
    }

    public List<Log> getLogsBySeasonAndYear(int playerID, String season, int year) {
        return logDAO.getLogsBySeasonAndYear(playerID, season, year);
    }
}