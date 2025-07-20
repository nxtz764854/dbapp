package dao;

import model.NPC;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NPCDAO {

    public boolean insertNPC(NPC npc) {
        String sql = "INSERT INTO npcs (npcname, givinggifttoday) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, npc.getNpcname());
            stmt.setBoolean(2, npc.isGivinggifttoday());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error inserting NPC: " + e.getMessage());
            return false;
        }
    }

    public boolean updateNPC(NPC npc) {
        String sql = "UPDATE npcs SET npcname = ?, givinggifttoday = ? WHERE npcID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, npc.getNpcname());
            stmt.setBoolean(2, npc.isGivinggifttoday());
            stmt.setInt(3, npc.getNpcID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating NPC: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteNPC(int npcID) {
        String sql = "DELETE FROM npcs WHERE npcID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, npcID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting NPC: " + e.getMessage());
            return false;
        }
    }

    public NPC getNPCByID(int npcID) {
        String sql = "SELECT * FROM npcs WHERE npcID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, npcID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new NPC(
                    rs.getInt("npcID"),
                    rs.getString("npcname"),
                    rs.getBoolean("givinggifttoday")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving NPC by ID: " + e.getMessage());
        }
        return null;
    }
    
    public NPC getNPCByName(String npcname) {
        String sql = "SELECT * FROM npcs WHERE npcname = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, npcname);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new NPC(
                    rs.getInt("npcID"),
                    rs.getString("npcname"),
                    rs.getBoolean("givinggifttoday")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving NPC by name: " + e.getMessage());
        }
        return null;
    }

    public List<NPC> getAllNPCs() {
        List<NPC> npcs = new ArrayList<>();
        String sql = "SELECT * FROM npcs";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                NPC npc = new NPC(
                    rs.getInt("npcID"),
                    rs.getString("npcname"),
                    rs.getBoolean("givinggifttoday")
                );
                npcs.add(npc);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving all NPCs: " + e.getMessage());
        }
        return npcs;
    }

    public void updateGivingGiftToday(int npcID, boolean flag) {
        String query = "UPDATE npcs SET givinggifttoday = ? WHERE npc_id = ?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, flag);
            stmt.setInt(2, npcID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean resetAllGiftFlags() {
        String sql = "UPDATE npc SET givinggifttoday = false";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean setGiftFlag(int npcID, boolean flag) {
        String sql = "UPDATE npcs SET givinggifttoday = ? WHERE npcID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, flag);
            stmt.setInt(2, npcID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating gift flag: " + e.getMessage());
            return false;
        }
    }
}
