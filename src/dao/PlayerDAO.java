package dao;

import model.Player;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {

    public boolean createPlayer(Player player) {
        String sql = "INSERT INTO players (playername) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, player.getPlayername());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Player getPlayerByID(int playerID) {
        String sql = "SELECT * FROM players WHERE playerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractPlayerFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Player getPlayerByName(String playername) {
        String sql = "SELECT * FROM players WHERE playername = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, playername);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractPlayerFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePlayer(Player player) {
        String sql = "UPDATE players SET playername = ?, wallet = ?, current_day = ?, current_season = ?, current_year = ? WHERE playerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, player.getPlayername());
            stmt.setInt(2, player.getWallet());
            stmt.setInt(3, player.getCurrent_day());
            stmt.setString(4, player.getCurrent_season());
            stmt.setInt(5, player.getCurrent_year());
            stmt.setInt(6, player.getPlayerID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDate(int playerID, int day, String season, int year) {
        String sql = "UPDATE players SET current_day = ?, current_season = ?, current_year = ? WHERE playerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, day);
            stmt.setString(2, season);
            stmt.setInt(3, year);
            stmt.setInt(4, playerID);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePlayer(int playerID) {
        String sql = "DELETE FROM players WHERE playerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM players";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                players.add(extractPlayerFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    private Player extractPlayerFromResultSet(ResultSet rs) throws SQLException {
        Player player = new Player();
        player.setPlayerID(rs.getInt("playerID"));
        player.setPlayername(rs.getString("playername"));
        player.setWallet(rs.getInt("wallet"));
        player.setCurrent_day(rs.getInt("current_day"));
        player.setCurrent_season(rs.getString("current_season"));
        player.setCurrent_year(rs.getInt("current_year"));
        return player;
    }
}
