package dao;

import model.HarvestLog;
import util.DBConnection;

import java.sql.*;
import java.util.*;

public class HarvestDAO {

    public boolean insertHarvestLog(HarvestLog log) {
        String sql = "INSERT INTO harvest_logs (playerID, cropID, itemID, quantity, season, day, year) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, log.getPlayerID());
            stmt.setInt(2, log.getCropID());
            stmt.setInt(3, log.getItemID());
            stmt.setInt(4, log.getQuantity());
            stmt.setString(5, log.getSeason());
            stmt.setInt(6, log.getDay());
            stmt.setInt(7, log.getYear());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<HarvestLog> getHarvestLogsByPlayer(int playerID) {
        List<HarvestLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM harvest_logs WHERE playerID = ? ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                logs.add(new HarvestLog(
                    rs.getInt("harvestID"),
                    rs.getInt("playerID"),
                    rs.getInt("cropID"),
                    rs.getInt("itemID"),
                    rs.getInt("quantity"),
                    rs.getString("season"),
                    rs.getInt("day"),
                    rs.getInt("year"),
                    rs.getTimestamp("timestamp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }
}