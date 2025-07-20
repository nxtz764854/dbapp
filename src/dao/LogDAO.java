package dao;

import model.Log;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogDAO {

    /**
     * Inserts a new log entry into the database.
     * @param log the Log to be inserted
     * @return true if the insertion was successful, false otherwise
     */
    public boolean insertLog(Log log) {
        String sql = "INSERT INTO logs (playerID, action, season, day, year) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, log.getPlayerID());
            stmt.setString(2, log.getAction());
            stmt.setString(3, log.getSeason());
            stmt.setInt(4, log.getDay());
            stmt.setInt(5, log.getYear());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves all log entries for a specific player.
     * @param playerID the player's ID
     * @return a list of Log entries for the player
     */
    public List<Log> getLogsByPlayer(int playerID) {
        List<Log> logs = new ArrayList<>();
        String sql = "SELECT * FROM logs WHERE playerID = ? ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                logs.add(mapResultSetToLog(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    /**
     * Retrieves all log entries from the database.
     * @return a list of Log entries
     */
    public List<Log> getAllLogs() {
        List<Log> logs = new ArrayList<>();
        String sql = "SELECT * FROM logs ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                logs.add(mapResultSetToLog(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    /**
     * Retrieves log entries for a player by season and year.
     * @param playerID the player's ID
     * @param season the season
     * @param year the year
     * @return a list of Log entries for the player, season, and year
     */
    public List<Log> getLogsBySeasonAndYear(int playerID, String season, int year) {
        List<Log> logs = new ArrayList<>();
        String sql = "SELECT * FROM logs WHERE playerID = ? AND season = ? AND year = ? ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            stmt.setString(2, season);
            stmt.setInt(3, year);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                logs.add(mapResultSetToLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    /**
     * Maps a result set row to a Log object.
     * @param rs the ResultSet to map
     * @return a Log object
     * @throws SQLException if a database access error occurs
     */
    private Log mapResultSetToLog(ResultSet rs) throws SQLException {
        return new Log(
            rs.getInt("logID"),
            rs.getInt("playerID"),
            rs.getString("action"),
            rs.getString("season"),
            rs.getInt("day"),
            rs.getInt("year"),
            rs.getTimestamp("timestamp")
        );
    }
}


