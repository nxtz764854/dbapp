package service;

import dao.PlayerDAO;
import model.Player;

import java.util.List;

public class PlayerService {
    private final PlayerDAO playerDAO;

    public PlayerService() {
        this.playerDAO = new PlayerDAO();
    }

    public boolean createPlayer(Player player) {
        return playerDAO.createPlayer(player);
    }

    public Player getPlayerByID(int playerID) {
        return playerDAO.getPlayerByID(playerID);
    }

    public List<Player> getAllPlayers() {
        return playerDAO.getAllPlayers();
    }

    public boolean updatePlayer(Player player) {
        return playerDAO.updatePlayer(player);
    }

    public boolean deletePlayer(int playerID) {
        return playerDAO.deletePlayer(playerID);
    }
}
