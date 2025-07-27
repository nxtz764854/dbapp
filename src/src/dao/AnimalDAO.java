package dao;

import model.Animal;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {

    /**
     * Adds an animal to the database.
     * @param animal the animal to add
     * @return true if the operation is successful
     */
    public boolean addAnimal(Animal animal) {
        String sql = "INSERT INTO animals (playerID, itemID, animalname, age, producedays, produceID, readytoharvest) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameters for the insertion
            stmt.setInt(1, animal.getPlayerID());
            stmt.setInt(2, animal.getItemID());
            stmt.setString(3, animal.getAnimalname());
            stmt.setInt(4, animal.getAge());
            stmt.setInt(5, animal.getProducedays());
            stmt.setInt(6, animal.getProduceID());
            stmt.setBoolean(7, animal.isReadytoharvest());

            // Execute the insertion statement
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Log any SQL exceptions
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates an animal's information in the database.
     * @param animal the animal with updated information
     * @return true if the operation is successful
     */
    public boolean updateAnimal(Animal animal) {
        String sql = "UPDATE animals SET playerID = ?, itemID = ?, animalname = ?, age = ?, producedays = ?, produceID = ?, readytoharvest = ? WHERE animalID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameters for the update
            stmt.setInt(1, animal.getPlayerID());
            stmt.setInt(2, animal.getItemID());
            stmt.setString(3, animal.getAnimalname());
            stmt.setInt(4, animal.getAge());
            stmt.setInt(5, animal.getProducedays());
            stmt.setInt(6, animal.getProduceID());
            stmt.setBoolean(7, animal.isReadytoharvest());
            stmt.setInt(8, animal.getAnimalID());

            // Execute the update statement
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Log any SQL exceptions
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates the harvest status of an animal.
     * @param animalID the ID of the animal
     * @param ready the harvest status to set
     * @return true if the operation is successful
     */
    public boolean updateHarvestStatus(int animalID, boolean ready) {
        String sql = "UPDATE animals SET readytoharvest = ? WHERE animalID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameters for the update
            stmt.setBoolean(1, ready);
            stmt.setInt(2, animalID);

            // Execute the update statement
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Log any SQL exceptions
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes an animal from the database.
     * @param animalID the ID of the animal to delete
     * @return true if the operation is successful
     */
    public boolean deleteAnimal(int animalID) {
        String sql = "DELETE FROM animals WHERE animalID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameter for the deletion
            stmt.setInt(1, animalID);

            // Execute the deletion statement
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Log any SQL exceptions
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves an animal by its ID.
     * @param animalID the ID of the animal
     * @return the animal, or null if not found
     */
    public Animal getAnimalByID(int animalID) {
        String sql = "SELECT * FROM animals WHERE animalID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameter for the query
            stmt.setInt(1, animalID);
            ResultSet rs = stmt.executeQuery();

            // Extract and return the animal if found
            if (rs.next()) {
                return extractAnimal(rs);
            }
        } catch (SQLException e) {
            // Log any SQL exceptions
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all animals associated with a player.
     * @param playerID the player's ID
     * @return a list of animals
     */
    public List<Animal> getAnimalsByPlayerID(int playerID) {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animals WHERE playerID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameter for the query
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            // Extract and add each animal to the list
            while (rs.next()) {
                animals.add(extractAnimal(rs));
            }
        } catch (SQLException e) {
            // Log any SQL exceptions
            e.printStackTrace();
        }
        return animals;
    }

    /**
     * Retrieves all animals that produce a certain item.
     * @param produceID the ID of the produce
     * @return a list of animals
     */
    public List<Animal> getAnimalsByProduceID(int produceID) {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animals WHERE produceID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameter for the query
            stmt.setInt(1, produceID);
            ResultSet rs = stmt.executeQuery();

            // Extract and add each animal to the list
            while (rs.next()) {
                animals.add(extractAnimal(rs));
            }
        } catch (SQLException e) {
            // Log any SQL exceptions
            e.printStackTrace();
        }
        return animals;
    }

    /**
     * Retrieves all animals associated with a player that are ready to harvest.
     * @param playerID the player's ID
     * @return a list of animals
     */
    public List<Animal> getReadyToHarvestAnimals(int playerID) {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animals WHERE playerID = ? AND readytoharvest = TRUE";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameter for the query
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            // Extract and add each animal to the list
            while (rs.next()) {
                animals.add(extractAnimal(rs));
            }
        } catch (SQLException e) {
            // Log any SQL exceptions
            e.printStackTrace();
        }
        return animals;
    }

    /**
     * Increments the age of all animals associated with a player and sets those that are ready to harvest.
     * @param playerID the player's ID
     * @return true if the operation is successful
     */
    public boolean incrementAnimalAgesAndSetReady(int playerID) {
        try (Connection conn = DBConnection.getConnection()) {

            String ageSQL = "UPDATE animals SET age = age + 1 WHERE playerID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(ageSQL)) {
                stmt.setInt(1, playerID);
                stmt.executeUpdate();
            }

            String readySQL = "UPDATE animals SET readytoharvest = TRUE WHERE playerID = ? AND age >= producedays";
            try (PreparedStatement stmt = conn.prepareStatement(readySQL)) {
                stmt.setInt(1, playerID);
                stmt.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    /**
     * Resets all animals associated with a player to not ready to harvest.
     * @param playerID the player's ID
     * @return true if the operation is successful
     */
    public boolean resetReadyToHarvestAnimals(int playerID) {
        String sql = "UPDATE animals SET readytoharvest = FALSE WHERE playerID = ? AND readytoharvest = TRUE";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameter for the update
            stmt.setInt(1, playerID);

            // Execute the update statement
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Log any SQL exceptions
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Extracts an animal object from the result set.
     * @param rs the result set containing animal data
     * @return the extracted animal
     * @throws SQLException if a database access error occurs
     */
    private Animal extractAnimal(ResultSet rs) throws SQLException {
        Animal animal = new Animal();
        animal.setAnimalID(rs.getInt("animalID"));
        animal.setPlayerID(rs.getInt("playerID"));
        animal.setItemID(rs.getInt("itemID"));
        animal.setAnimalname(rs.getString("animalname"));
        animal.setAge(rs.getInt("age"));
        animal.setProducedays(rs.getInt("producedays"));
        animal.setProduceID(rs.getInt("produceID"));
        animal.setReadytoharvest(rs.getBoolean("readytoharvest"));
        return animal;
    }
}
