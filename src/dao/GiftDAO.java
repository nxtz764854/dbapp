package dao;

import model.GiftLog;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GiftDAO {

    public boolean insertGiftLog(GiftLog log) {
        String sql = "INSERT INTO gift_logs (playerID, npcID, itemID, season, day, year) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, log.getPlayerID());
            stmt.setInt(2, log.getNpcID());
            stmt.setInt(3, log.getItemID());
            stmt.setString(4, log.getSeason());
            stmt.setInt(5, log.getDay());
            stmt.setInt(6, log.getYear());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<GiftLog> getGiftLogsByPlayer(int playerID) {
        List<GiftLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM gift_logs WHERE playerID = ? ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                logs.add(new GiftLog(
                    rs.getInt("giftID"),
                    rs.getInt("playerID"),
                    rs.getInt("npcID"),
                    rs.getInt("itemID"),
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