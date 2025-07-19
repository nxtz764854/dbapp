import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {

    /**
     * Adds a new animal to the database.
     * @param animal The animal to be added. The animal object must have valid
     *               playerID, itemID, animalname, age, and produces fields.
     */
    public void addAnimal(Animal animal) {
        String sql = "INSERT INTO animals (playerID, itemID, animalname, age, produces, isAlive) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, animal.getPlayerID());
            stmt.setInt(2, animal.getItemID());
            stmt.setString(3, animal.getAnimalname());
            stmt.setInt(4, animal.getAge());
            stmt.setString(5, animal.getProduces());
            stmt.setBoolean(6, animal.isAlive());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves an animal from the database by its ID.
     * @param animalID The ID of the animal to retrieve
     * @return The Animal object if found, null otherwise
     */
    public Animal getAnimalByID(int animalID) {
        String sql = "SELECT * FROM animals WHERE animalID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, animalID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToAnimal(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retrieves all animals for a given player from the database.
     * @param playerID The ID of the player to retrieve the animals for
     * @return A list of Animal objects containing all animals for the given player,
     *         possibly empty if no animals are found.
     */
    public List<Animal> getAllAnimalsForPlayer(int playerID) {
        String sql = "SELECT * FROM animals WHERE playerID = ?";
        List<Animal> animals = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                animals.add(mapResultSetToAnimal(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return animals;
    }

    /**
     * Updates the details of an existing animal in the database.
     * 
     * @param animal The Animal object containing updated details. The object must have
     *               a valid animalID and may include updated values for itemID, animalname,
     *               age, produces, and isAlive status.
     */
    public void updateAnimal(Animal animal) {
        String sql = "UPDATE animals SET itemID = ?, animalname = ?, age = ?, produces = ?, isAlive = ? WHERE animalID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, animal.getItemID());
            stmt.setString(2, animal.getAnimalname());
            stmt.setInt(3, animal.getAge());
            stmt.setString(4, animal.getProduces());
            stmt.setBoolean(5, animal.isAlive());
            stmt.setInt(6, animal.getAnimalID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes an animal from the database by its ID.
     * @param animalID The ID of the animal to delete
     */
    public void deleteAnimal(int animalID) {
        String sql = "DELETE FROM animals WHERE animalID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, animalID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all animals from the database with the given produce type.
     * @param produces The produce type to filter by
     * @return A list of Animal objects containing all animals of the given produce type,
     *         possibly empty if no animals are found.
     */
    public List<Animal> getAnimalsByType(String produces) {
        String sql = "SELECT * FROM animals WHERE produces = ?";
        List<Animal> animals = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produces);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                animals.add(mapResultSetToAnimal(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return animals;
    }

    /**
     * Retrieves all animals for a given player that are alive.
     * @param playerID The ID of the player to retrieve the animals for
     * @return A list of Animal objects containing all alive animals for the given player,
     *         possibly empty if no animals are found.
     */
    public List<Animal> getAliveAnimals(int playerID) {
        String sql = "SELECT * FROM animals WHERE playerID = ? AND isAlive = TRUE";
        List<Animal> animals = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                animals.add(mapResultSetToAnimal(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return animals;
    }

    /**
     * Marks an animal as dead in the database by setting its isAlive status to FALSE.
     * @param animalID The ID of the animal to be marked as dead.
     */
    public void markAnimalAsDead(int animalID) {
        String sql = "UPDATE animals SET isAlive = FALSE WHERE animalID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, animalID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maps a ResultSet to an Animal object.
     * @param rs The ResultSet to map from
     * @return An Animal object with the data from the ResultSet
     * @throws SQLException If the mapping fails
     */
    private Animal mapResultSetToAnimal(ResultSet rs) throws SQLException {
        Animal animal = new Animal();
        animal.setAnimalID(rs.getInt("animalID"));
        animal.setPlayerID(rs.getInt("playerID"));
        animal.setItemID(rs.getInt("itemID"));
        animal.setAnimalname(rs.getString("animalname"));
        animal.setAge(rs.getInt("age"));
        animal.setProduces(rs.getString("produces"));
        animal.setAlive(rs.getBoolean("isAlive"));
        return animal;
    }
}
