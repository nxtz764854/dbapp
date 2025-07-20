package dao;

import model.Log;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogDAO {

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
