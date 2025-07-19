package dao;

import model.Relation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelationsDAO {
    private Connection conn;

    public RelationsDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new relation into the database.
     * @param relation The relation to be inserted in the database
     * @return true if the insertion was successful, false otherwise
     * @throws SQLException If there is a problem with the database
     */
    public boolean insertRelation(Relation relation) throws SQLException {
        String sql = "INSERT INTO relations (playerID, npcname, npchearts) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, relation.getPlayerID());
            stmt.setInt(2, relation.getNpcID());
            stmt.setInt(3, relation.getNpchearts());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Updates the number of hearts a player has with a given NPC.
     * @param playerID The ID of the player whose hearts to update
     * @param npcname The name of the NPC whose hearts to update
     * @param newHearts The new number of hearts to set
     * @return true if the update was successful, false otherwise
     * @throws SQLException If there is a problem with the database
     */
    public boolean updateHearts(int playerID, String npcname, int newHearts) throws SQLException {
        String sql = "UPDATE relations SET npchearts = ? WHERE playerID = ? AND npcname = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newHearts);
            stmt.setInt(2, playerID);
            stmt.setString(3, npcname);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a relation from the database by player ID and NPC name.
     * @param playerID The ID of the player whose relation to delete
     * @param npcname The name of the NPC whose relation to delete
     * @return true if the deletion was successful, false otherwise
     * @throws SQLException If there is a problem with the database
     */
    public boolean deleteRelation(int playerID, String npcname) throws SQLException {
        String sql = "DELETE FROM relations WHERE playerID = ? AND npcname = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            stmt.setString(2, npcname);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Retrieves a relation from the database by player ID and NPC name.
     * @param playerID The ID of the player whose relation to retrieve
     * @param npcname The name of the NPC whose relation to retrieve
     * @return The relation if found, null otherwise
     * @throws SQLException If there is a problem with the database
     */
    public Relation getRelation(int playerID, String npcname) throws SQLException {
        String sql = "SELECT * FROM relations WHERE playerID = ? AND npcname = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            stmt.setString(2, npcname);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Relation(
                    rs.getInt("playerID"),
                    rs.getInt("npcID"),
                    rs.getInt("npchearts")
                );
            }
        }
        return null;
    }

    /**
     * Retrieves all relations for a given player from the database.
     * @param playerID The ID of the player to retrieve the relations for
     * @return A list of Relation objects containing all relations for the given player,
     *         possibly empty if no relations are found.
     * @throws SQLException If there is a problem with the database
     */
    public List<Relation> getAllRelationsForPlayer(int playerID) throws SQLException {
        List<Relation> list = new ArrayList<>();
        String sql = "SELECT * FROM relations WHERE playerID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Relation(
                    rs.getInt("playerID"),
                    rs.getInt("npcID"),
                    rs.getInt("npchearts")
                ));
            }
        }
        return list;
    }

    /**
     * Increments the number of hearts a player has with a given NPC by a given amount.
     * @param playerID The ID of the player whose hearts to increment
     * @param npcname The name of the NPC whose hearts to increment
     * @param amount The amount to increment the hearts by
     * @return true if the incrementation was successful, false otherwise
     * @throws SQLException If there is a problem with the database
     */
    public boolean incrementHearts(int playerID, String npcname, int amount) throws SQLException {
        String sql = "UPDATE relations SET npchearts = npchearts + ? WHERE playerID = ? AND npcname = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, amount);
            stmt.setInt(2, playerID);
            stmt.setString(3, npcname);
            return stmt.executeUpdate() > 0;
        }
    }
}
