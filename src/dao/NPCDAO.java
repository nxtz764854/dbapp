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
            e.printStackTrace();
        }
        return false;
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
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteNPC(int npcID) {
        String sql = "DELETE FROM npcs WHERE npcID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, npcID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public NPC getNPCByID(int npcID) {
        String sql = "SELECT * FROM npcs WHERE npcID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, npcID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractNPC(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
                return extractNPC(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
                npcs.add(extractNPC(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return npcs;
    }

    public boolean setGiftFlag(int npcID, boolean flag) {
        String sql = "UPDATE npcs SET givinggifttoday = ? WHERE npcID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, flag);
            stmt.setInt(2, npcID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean resetAllGiftFlags() {
        String sql = "UPDATE npcs SET givinggifttoday = FALSE";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private NPC extractNPC(ResultSet rs) throws SQLException {
        return new NPC(
            rs.getInt("npcID"),
            rs.getString("npcname"),
            rs.getBoolean("givinggifttoday")
        );
    }
}
