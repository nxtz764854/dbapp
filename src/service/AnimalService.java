package service;

import dao.AnimalDAO;
import model.Animal;

import java.util.List;

public class AnimalService {
    private AnimalDAO animalDAO;

    public AnimalService() {
        this.animalDAO = new AnimalDAO();
    }

    public List<Animal> getAnimalsByPlayerID(int playerID) {
        return animalDAO.getAnimalsByPlayerID(playerID);
    }

    public Animal getAnimalByID(int animalID) {
        return animalDAO.getAnimalByID(animalID);
    }

    public void addAnimal(Animal animal) {
        animalDAO.addAnimal(animal);
    }

    public void updateAnimal(Animal animal) {
        animalDAO.updateAnimal(animal);
    }

    public void deleteAnimal(int animalID) {
        animalDAO.deleteAnimal(animalID);
    }

    public List<Animal> getReadyToHarvestAnimals(int playerID) {
        return animalDAO.getReadyToHarvestAnimals(playerID);
    }

    public List<Animal> getAnimalsByProduceID(int produceID) {
        return animalDAO.getAnimalsByProduceID(produceID);
    }
}