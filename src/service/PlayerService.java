package service;

import dao.PlayerDAO;
import model.Player;

import java.util.List;

public class PlayerService {
    private PlayerDAO playerDAO;

    public PlayerService(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    public boolean createPlayer(Player player) {
        return playerDAO.createPlayer(player);
    }

    public boolean updatePlayer(Player player) {
        return playerDAO.updatePlayer(player);
    }

    public Player getPlayerByID(int playerID) {
        return playerDAO.getPlayerByID(playerID);
    }

    public Player getPlayerByName(String name) {
        return playerDAO.getPlayerByName(name);
    }

    public boolean updateWallet(int playerID, int newWallet) {
        return playerDAO.updateWallet(playerID, newWallet);
    }

    public boolean updateDate(int playerID, int day, String season, int year) {
        return playerDAO.updateDate(playerID, day, season, year);
    }

    public boolean playerExists(String playername) {
        return playerDAO.playerExists(playername);
    }

    public List<Player> getAllPlayers() {
        return playerDAO.getAllPlayers();
    }

    public boolean deletePlayer(int playerID) {
        return playerDAO.deletePlayer(playerID);
    }
}
