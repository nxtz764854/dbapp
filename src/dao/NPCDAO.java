package dao;

import model.NPC;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NPCDAO {

    /**
     * Inserts an NPC into the database
     * @param npc the NPC to insert
     * @return true if the insertion was successful, false otherwise
     */
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

    /**
     * Updates an NPC in the database
     * @param npc the NPC to update
     * @return true if the update was successful, false otherwise
     */
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

    /**
     * Deletes an NPC from the database
     * @param npcID the ID of the NPC to delete
     * @return true if the deletion was successful, false otherwise
     */
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

    /**
     * Retrieves an NPC by ID from the database
     * @param npcID the ID of the NPC to retrieve
     * @return the NPC if found, null otherwise
     */
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

    /**
     * Retrieves an NPC by name from the database
     * @param npcname the name of the NPC to retrieve
     * @return the NPC if found, null otherwise
     */
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

    /**
     * Retrieves all NPCs from the database
     * @return a list of all NPCs in the database
     */
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

    /**
     * Sets the gift flag for an NPC to true or false
     * @param npcID the ID of the NPC to set the flag for
     * @param flag the value to set the flag to
     * @return true if the update was successful, false otherwise
     */
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

    /**
     * Resets all gift flags to false
     * @return true if the update was successful, false otherwise
     */
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

    /**
     * Extracts an NPC from a ResultSet
     * @param rs the ResultSet to extract from
     * @return the extracted NPC
     * @throws SQLException if there is an error extracting the NPC
     */
    private NPC extractNPC(ResultSet rs) throws SQLException {
        return new NPC(
            rs.getInt("npcID"),
            rs.getString("npcname"),
            rs.getBoolean("givinggifttoday")
        );
    }
}

