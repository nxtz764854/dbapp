package dao;

import model.Player;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {

    /**
     * Inserts a new player into the database.
     * @param player the Player object to be created.
     * @return true if the player was created successfully, false otherwise.
     */
    public boolean createPlayer(Player player) {
        String sql = "INSERT INTO players (playername) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set player name for insertion
            stmt.setString(1, player.getPlayername());

            // Execute insert statement
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a player by their ID.
     * @param playerID the ID of the player to retrieve.
     * @return the Player object if found, null otherwise.
     */
    public Player getPlayerByID(int playerID) {
        String sql = "SELECT * FROM players WHERE playerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the player ID for the query
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            // Extract player if found
            if (rs.next()) {
                return extractPlayerFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a player by their name.
     * @param playername the name of the player to retrieve.
     * @return the Player object if found, null otherwise.
     */
    public Player getPlayerByName(String playername) {
        String sql = "SELECT * FROM players WHERE playername = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the player name for the query
            stmt.setString(1, playername);
            ResultSet rs = stmt.executeQuery();

            // Extract player if found
            if (rs.next()) {
                return extractPlayerFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates an existing player's information in the database.
     * @param player the Player object containing updated information.
     * @return true if the player was updated successfully, false otherwise.
     */
    public boolean updatePlayer(Player player) {
        String sql = "UPDATE players SET playername = ?, wallet = ?, current_day = ?, current_season = ?, current_year = ? WHERE playerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the updated player information
            stmt.setString(1, player.getPlayername());
            stmt.setInt(2, player.getWallet());
            stmt.setInt(3, player.getCurrent_day());
            stmt.setString(4, player.getCurrent_season());
            stmt.setInt(5, player.getCurrent_year());
            stmt.setInt(6, player.getPlayerID());

            // Execute update statement
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates the date-related fields for a player.
     * @param playerID the ID of the player to update.
     * @param day the new day value.
     * @param season the new season value.
     * @param year the new year value.
     * @return true if the date was updated successfully, false otherwise.
     */
    public boolean updateDate(int playerID, int day, String season, int year) {
        String sql = "UPDATE players SET current_day = ?, current_season = ?, current_year = ? WHERE playerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the new date information
            stmt.setInt(1, day);
            stmt.setString(2, season);
            stmt.setInt(3, year);
            stmt.setInt(4, playerID);

            // Execute update statement
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a player from the database.
     * @param playerID the ID of the player to delete.
     * @return true if the player was deleted successfully, false otherwise.
     */
    public boolean deletePlayer(int playerID) {
        String sql = "DELETE FROM players WHERE playerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the player ID for deletion
            stmt.setInt(1, playerID);

            // Execute delete statement
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all players from the database.
     * @return a list of Player objects.
     */
    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM players";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Extract and add each player to the list
            while (rs.next()) {
                players.add(extractPlayerFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    /**
     * Extracts a Player object from a ResultSet.
     * @param rs the ResultSet containing player data.
     * @return a Player object.
     * @throws SQLException if an SQL exception occurs.
     */
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

