package service;

import dao.AnimalDAO;
import model.Animal;

import java.util.List;

public class AnimalService {
    private AnimalDAO animalDAO = new AnimalDAO();

    public boolean addAnimal(Animal animal) {
        animalDAO.addAnimal(animal);
        return true;
    }

    public Animal getAnimalByID(int animalID) {
        return animalDAO.getAnimalByID(animalID);
    }

    public List<Animal> getAnimalsByPlayerID(int playerID) {
        return animalDAO.getAnimalsByPlayerID(playerID);
    }

    public List<Animal> getAnimalsByProduceID(int produceID) {
        return animalDAO.getAnimalsByProduceID(produceID);
    }

    public boolean incrementAgesAndSetReady(int playerID) {
        return animalDAO.incrementAnimalAgesAndSetReady(playerID);
    }

    public boolean resetReadyToHarvest(int playerID) {
        return animalDAO.resetReadyToHarvestAnimals(playerID);
    }

    public boolean markHarvested(int animalID) {
        return animalDAO.updateHarvestStatus(animalID, false);
    }


    public List<Animal> getReadyToHarvestAnimals(int playerID) {
        return animalDAO.getReadyToHarvestAnimals(playerID);
    }

    public boolean updateAnimal(Animal animal) {
        animalDAO.updateAnimal(animal);
        return true;
    }

    public boolean deleteAnimal(int animalID) {
        animalDAO.deleteAnimal(animalID);
        return true;
    }
}