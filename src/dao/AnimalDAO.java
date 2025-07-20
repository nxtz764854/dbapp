package dao;

import model.Animal;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {

    public boolean addAnimal(Animal animal) {
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

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateAnimal(Animal animal) {
        String sql = "UPDATE animals SET playerID = ?, itemID = ?, animalname = ?, age = ?, producedays = ?, produceID = ?, readytoharvest = ? WHERE animalID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, animal.getPlayerID());
            stmt.setInt(2, animal.getItemID());
            stmt.setString(3, animal.getAnimalname());
            stmt.setInt(4, animal.getAge());
            stmt.setInt(5, animal.getProducedays());
            stmt.setInt(6, animal.getProduceID());
            stmt.setBoolean(7, animal.isReadytoharvest());
            stmt.setInt(8, animal.getAnimalID());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateHarvestStatus(int animalID, boolean ready) {
        String sql = "UPDATE animals SET readytoharvest = ? WHERE animalID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, ready);
            stmt.setInt(2, animalID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAnimal(int animalID) {
        String sql = "DELETE FROM animals WHERE animalID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, animalID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Animal getAnimalByID(int animalID) {
        String sql = "SELECT * FROM animals WHERE animalID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, animalID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractAnimal(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Animal> getAnimalsByPlayerID(int playerID) {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animals WHERE playerID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                animals.add(extractAnimal(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animals;
    }

    public List<Animal> getAnimalsByProduceID(int produceID) {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animals WHERE produceID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, produceID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                animals.add(extractAnimal(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animals;
    }

    public List<Animal> getReadyToHarvestAnimals(int playerID) {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animals WHERE playerID = ? AND readytoharvest = TRUE";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                animals.add(extractAnimal(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animals;
    }

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