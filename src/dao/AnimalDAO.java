package dao;

import model.Animal;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {

    public void addAnimal(Animal animal) {
        String sql = "INSERT INTO animals (playerID, itemID, animalname, age, producedays, produceID, readytoharvest) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, animal.getPlayerID());
            stmt.setInt(2, animal.getItemID());
            stmt.setString(3, animal.getAnimalname());
            stmt.setInt(4, animal.getAge());
            stmt.setInt(5, animal.getProducedays());
            stmt.setInt(6, animal.getProduceID());
            stmt.setBoolean(7, animal.isReadytoharvest());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public List<Animal> getAnimalsByPlayerID(int playerID) {
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

    public void updateAnimal(Animal animal) {
        String sql = "UPDATE animals SET itemID = ?, animalname = ?, age = ?, producedays = ?, produceID = ?, readytoharvest = ? WHERE animalID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, animal.getItemID());
            stmt.setString(2, animal.getAnimalname());
            stmt.setInt(3, animal.getAge());
            stmt.setInt(4, animal.getProducedays());
            stmt.setInt(5, animal.getProduceID());
            stmt.setBoolean(6, animal.isReadytoharvest());
            stmt.setInt(7, animal.getAnimalID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public List<Animal> getAnimalsByProduceID(int produceID) {
        String sql = "SELECT * FROM animals WHERE produceID = ?";
        List<Animal> animals = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, produceID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                animals.add(mapResultSetToAnimal(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return animals;
    }

    public List<Animal> getReadyToHarvestAnimals(int playerID) {
        String sql = "SELECT * FROM animals WHERE playerID = ? AND readytoharvest = TRUE";
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

    private Animal mapResultSetToAnimal(ResultSet rs) throws SQLException {
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
