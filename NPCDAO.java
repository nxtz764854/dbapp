import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NPCDAO {

    /**
     * Inserts a new NPC into the database.
     * @param npc The NPC to insert, which must have valid npcname and givinggifttoday fields.
     * @return true if the insertion was successful, false otherwise
     * @throws SQLException If there is a problem with the database
     */
    public boolean insertNPC(NPC npc) throws SQLException {
        String sql = "INSERT INTO npcs (npcname, givinggifttoday) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, npc.getNpcname());
            stmt.setBoolean(2, npc.isGivinggifttoday());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Updates an existing NPC in the database.
     * @param npc The NPC to update, which must have valid npcname, givinggifttoday, and npcID fields.
     * @return true if the update was successful, false otherwise
     * @throws SQLException If there is a problem with the database
     */
    public boolean updateNPC(NPC npc) throws SQLException {
        String sql = "UPDATE npcs SET npcname = ?, givinggifttoday = ? WHERE npcID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, npc.getNpcname());
            stmt.setBoolean(2, npc.isGivinggifttoday());
            stmt.setInt(3, npc.getNpcID());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Deletes an NPC from the database by its ID.
     * @param npcID The ID of the NPC to delete
     * @return true if the deletion was successful, false otherwise
     * @throws SQLException If there is a problem with the database
     */
    public boolean deleteNPC(int npcID) throws SQLException {
        String sql = "DELETE FROM npcs WHERE npcID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, npcID);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Retrieves an NPC from the database by its ID.
     * @param npcID The ID of the NPC to retrieve
     * @return The NPC if found, null otherwise
     * @throws SQLException If there is a problem with the database
     */
    public NPC getNPCByID(int npcID) throws SQLException {
        String sql = "SELECT * FROM npcs WHERE npcID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, npcID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new NPC(
                    rs.getInt("npcID"),
                    rs.getString("npcname"),
                    rs.getBoolean("givinggifttoday")
                );
            }
        }
        return null;
    }

    /**
     * Retrieves all NPCs from the database.
     * @return A list of NPC objects, possibly empty
     * @throws SQLException If there is a problem with the database
     */
    public List<NPC> getAllNPCs() throws SQLException {
        List<NPC> npcs = new ArrayList<>();
        String sql = "SELECT * FROM npcs";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                NPC npc = new NPC(
                    rs.getInt("npcID"),
                    rs.getString("npcname"),
                    rs.getBoolean("givinggifttoday")
                );
                npcs.add(npc);
            }
        }
        return npcs;
    }

    /**
     * Updates the gift flag for a specific NPC in the database.
     * 
     * @param npcID The ID of the NPC whose gift flag is to be updated
     * @param flag The new value for the giving gift today flag
     * @return true if the update was successful, false otherwise
     * @throws SQLException If there is a problem with the database
     */
    public boolean setGiftFlag(int npcID, boolean flag) throws SQLException {
        String sql = "UPDATE npcs SET givinggifttoday = ? WHERE npcID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, flag);
            stmt.setInt(2, npcID);
            return stmt.executeUpdate() > 0;
        }
    }
}
