package model;

public class Animal {
    private int animalID;               // Unique ID of the animal
    private String animalname;          // Name of the animal
    private int playerID;               // ID of the player who owns the animal
    private int itemID;                 // ID of the item associated with the animal
    private int age;                    // Age of the animal
    private int producedays;            // Days required to produce
    private int produceID;              // ID of the produce item
    private boolean readytoharvest;     // Status if the animal is ready to harvest

    public Animal() {}

    public Animal(int animalID, String animalname, int playerID, int itemID, int age, int producedays, boolean readytoharvest) {
        this.animalID = animalID;
        this.animalname = animalname;
        this.playerID = playerID;
        this.itemID = itemID;
        this.age = age;
        this.producedays = producedays;
        this.readytoharvest = readytoharvest;
    }

    public int getAnimalID() { 
        return animalID; 
    }

    public void setAnimalID(int animalID) { 
        this.animalID = animalID; 
    }

    public String getAnimalname() { 
        return animalname; 
    }
    public void setAnimalname(String animalname) { 
        this.animalname = animalname; 
    }

    public int getPlayerID() { 
        return playerID; 
    }

    public void setPlayerID(int playerID) { 
        this.playerID = playerID; 
    }

    public int getItemID() { 
        return itemID; 
    }

    public void setItemID(int itemID) { 
        this.itemID = itemID; 
    }

    public int getAge() { 
        return age; 
    }

    public void setAge(int age) { 
        this.age = age; 
    }

    public int getProducedays() { 
        return producedays;
    }

    public void setProducedays(int producedays) { 
        this.producedays = producedays; 
    }

    public int getProduceID() { 
        return produceID; 
    }

    public void setProduceID(int produceID) { 
        this.produceID = produceID; 
    }

    public boolean isReadytoharvest() { 
        return readytoharvest; 
    }

    public void setReadytoharvest(boolean readytoharvest) { 
        this.readytoharvest = readytoharvest; 
    }
}