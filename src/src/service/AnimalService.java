package service;

import dao.AnimalDAO;
import model.Animal;

import java.util.List;

public class AnimalService {
    private AnimalDAO animalDAO = new AnimalDAO();

    // Add an animal to the database. Returns true if the operation is successful.
    public boolean addAnimal(Animal animal) {
        animalDAO.addAnimal(animal);
        return true;
    }

    // Get an animal by its ID. Returns null if no animal is found.
    public Animal getAnimalByID(int animalID) {
        return animalDAO.getAnimalByID(animalID);
    }

    // Get all animals associated with a player. Returns an empty list if no animals are found.
    public List<Animal> getAnimalsByPlayerID(int playerID) {
        return animalDAO.getAnimalsByPlayerID(playerID);
    }

    // Get all animals that produce a certain item. Returns an empty list if no animals are found.
    public List<Animal> getAnimalsByProduceID(int produceID) {
        return animalDAO.getAnimalsByProduceID(produceID);
    }

    // Increment the age of all animals associated with a player and set those that are ready to harvest to ready.
    // Returns true if the operation is successful.
    public boolean incrementAgesAndSetReady(int playerID) {
        return animalDAO.incrementAnimalAgesAndSetReady(playerID);
    }

    // Reset all animals associated with a player to not ready to harvest.
    // Returns true if the operation is successful.
    public boolean resetReadyToHarvest(int playerID) {
        return animalDAO.resetReadyToHarvestAnimals(playerID);
    }

    // Mark an animal as harvested. Returns true if the operation is successful.
    public boolean markHarvested(int animalID) {
        return animalDAO.updateHarvestStatus(animalID, false);
    }


    // Get all animals associated with a player that are ready to harvest. Returns an empty list if no animals are found.
    public List<Animal> getReadyToHarvestAnimals(int playerID) {
        return animalDAO.getReadyToHarvestAnimals(playerID);

    }

    // Update an animal in the database. Returns true if the operation is successful.
    public boolean updateAnimal(Animal animal) {
        animalDAO.updateAnimal(animal);
        return true;
    }

    // Delete an animal from the database. Returns true if the operation is successful.
    public boolean deleteAnimal(int animalID) {
        animalDAO.deleteAnimal(animalID);
        return true;
    }
}