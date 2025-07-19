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

    public void markHarvested(int animalID) {
        Animal animal = animalDAO.getAnimalByID(animalID);
        if (animal != null) {
            animal.setReadytoharvest(false);
            animalDAO.updateAnimal(animal);
        }
    }

    public void resetReadyToHarvest(int playerID) {
        List<Animal> animals = animalDAO.getAnimalsByPlayerID(playerID);
        for (Animal animal : animals) {
            int days = animal.getProducedays();
            if (days <= 1) {
                animal.setReadytoharvest(true);
                animal.setProducedays(0);
            } else {
                animal.setProducedays(days - 1);
            }
            animalDAO.updateAnimal(animal);
        }
    }


    public List<Animal> getAnimalsByProduceID(int produceID) {
        return animalDAO.getAnimalsByProduceID(produceID);
    }
}