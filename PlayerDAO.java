import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {

    /**
     * Creates a new player in the database, and returns true if the
     * operation was successful, false otherwise.
     * @param player The player to be created in the database
     * @return true if the player was created, false otherwise
     */
    public boolean createPlayer(Player player) {
        String sql = "INSERT INTO players (playername, wallet, current_day, current_season, current_year) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, player.getPlayername());
            stmt.setInt(2, player.getWallet());
            stmt.setInt(3, player.getCurrent_day());
            stmt.setString(4, player.getCurrent_season());
            stmt.setInt(5, player.getCurrent_year());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a player from the database by its ID.
     * @param playerID The ID of the player to retrieve
     * @return The player if found, null otherwise
     */
    public Player getPlayerByID(int playerID) {
        String sql = "SELECT * FROM players WHERE playerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractPlayerFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a player from the database by its name.
     * @param playername The name of the player to retrieve
     * @return The player if found, null otherwise
     */
    public Player getPlayerByName(String playername) {
        String sql = "SELECT * FROM players WHERE playername = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, playername);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractPlayerFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Checks if a player with the given name exists in the database.
     * @param playername The name of the player to check
     * @return true if the player exists, false otherwise
     */
    public boolean playerExists(String playername) {
        String sql = "SELECT playerID FROM players WHERE playername = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, playername);
            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates the wallet of a player in the database.
     * @param playerID The ID of the player to update
     * @param newWallet The new wallet value to set
     * @return true if the update was successful, false otherwise
     */
    public boolean updateWallet(int playerID, int newWallet) {
        String sql = "UPDATE players SET wallet = ? WHERE playerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newWallet);
            stmt.setInt(2, playerID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a player from the database.
     * @param playerID The ID of the player to delete
     * @return true if the deletion was successful, false otherwise
     */
    public boolean deletePlayer(int playerID) {
        String sql = "DELETE FROM players WHERE playerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all players from the database.
     * @return A list of Player objects, possibly empty
     */
    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM players";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                players.add(extractPlayerFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    /**
     * Extracts a Player object from a ResultSet.
     * @param rs The ResultSet from which to extract the player
     * @return A Player object with the extracted data
     * @throws SQLException If the extraction fails
     */
    private Player extractPlayerFromResultSet(ResultSet rs) throws SQLException {
        Player player = new Player();
        player.setPlayerID(rs.getInt("playerID"));
        player.setPlayername(rs.getString("playername"));
        player.setWallet(rs.getInt("wallet"));
        player.setCurrent_day(rs.getInt("current_day"));
        player.setCurrent_season(rs.getString("current_season"));
        player.setCurrent_year(rs.getInt("current_year"));
        return player;
    }
}