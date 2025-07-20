package dao;

import model.Relation;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelationsDAO {

    public boolean insertRelation(Relation relation) {
        String sql = "INSERT INTO relations (playerID, npcID, npchearts, last_gift_day, gift_count_this_week) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, relation.getPlayerID());
            stmt.setInt(2, relation.getNpcID());
            stmt.setInt(3, relation.getNpchearts());
            stmt.setInt(4, relation.getLastGiftDay());
            stmt.setInt(5, relation.getGiftCountThisWeek());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateRelation(Relation relation) {
        String sql = "UPDATE relations SET npchearts = ?, last_gift_day = ?, gift_count_this_week = ? WHERE playerID = ? AND npcID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, relation.getNpchearts());
            stmt.setInt(2, relation.getLastGiftDay());
            stmt.setInt(3, relation.getGiftCountThisWeek());
            stmt.setInt(4, relation.getPlayerID());
            stmt.setInt(5, relation.getNpcID());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteRelation(int playerID, int npcID) {
        String sql = "DELETE FROM relations WHERE playerID = ? AND npcID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            stmt.setInt(2, npcID);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Relation getRelation(int playerID, int npcID) {
        String sql = "SELECT * FROM relations WHERE playerID = ? AND npcID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            stmt.setInt(2, npcID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToRelation(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Relation> getRelationsByPlayer(int playerID) {
        List<Relation> relations = new ArrayList<>();
        String sql = "SELECT * FROM relations WHERE playerID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                relations.add(mapResultSetToRelation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relations;
    }

    private Relation mapResultSetToRelation(ResultSet rs) throws SQLException {
        return new Relation(
            rs.getInt("playerID"),
            rs.getInt("npcID"),
            rs.getInt("npchearts"),
            rs.getInt("last_gift_day"),
            rs.getInt("gift_count_this_week")
        );
    }

    public boolean updateGiftStats(int playerID, int npcID, int day, int count) {
        String sql = "UPDATE relations SET last_gift_day = ?, gift_count_this_week = ? WHERE playerID = ? AND npcID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, day);
            stmt.setInt(2, count);
            stmt.setInt(3, playerID);
            stmt.setInt(4, npcID);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean incrementHearts(int playerID, int npcID, int amount) {
        String sql = "UPDATE relations SET npchearts = npchearts + ? WHERE playerID = ? AND npcID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, amount);
            stmt.setInt(2, playerID);
            stmt.setInt(3, npcID);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
