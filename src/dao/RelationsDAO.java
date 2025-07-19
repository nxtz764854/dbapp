package dao;

import model.Relation;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelationsDAO {

    public boolean insertRelation(Relation relation) {
        String sql = "INSERT INTO relations (playerID, npcID, npchearts) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, relation.getPlayerID());
            stmt.setInt(2, relation.getNpcID());
            stmt.setInt(3, relation.getNpchearts());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateHearts(int playerID, int npcID, int newHearts) {
        String sql = "UPDATE relations SET npchearts = ? WHERE playerID = ? AND npcID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newHearts);
            stmt.setInt(2, playerID);
            stmt.setInt(3, npcID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
            return false;
        }
    }

    public Relation getRelation(int playerID, int npcID) {
        String sql = "SELECT * FROM relations WHERE playerID = ? AND npcID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            stmt.setInt(2, npcID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Relation(
                    rs.getInt("playerID"),
                    rs.getInt("npcID"),
                    rs.getInt("npchearts")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Relation> getAllRelationsForPlayer(int playerID) {
        List<Relation> list = new ArrayList<>();
        String sql = "SELECT * FROM relations WHERE playerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Relation(
                    rs.getInt("playerID"),
                    rs.getInt("npcID"),
                    rs.getInt("npchearts")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
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
            return false;
        }
    }

    public List<Relation> getRelationsByPlayer(int playerID) {
        List<Relation> relations = new ArrayList<>();
        String sql = "SELECT * FROM relations WHERE playerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Relation relation = new Relation(
                    rs.getInt("playerID"),
                    rs.getInt("npcID"),
                    rs.getInt("npchearts")
                );
                relations.add(relation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relations;
    }
}
