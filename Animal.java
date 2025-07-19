public class Animal {
    private int animalID;
    private int playerID;
    private int itemID;
    private String animalname;
    private int age;
    private int producedays;
    private boolean readytoharvest;

    public Animal() {}

    public Animal(int animalID, int playerID, int itemID, String animalname, int age, int producedays, boolean readytoharvest) {
        this.animalID = animalID;
        this.playerID = playerID;
        this.itemID = itemID;
        this.animalname = animalname;
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

    public String getAnimalname() { 
        return animalname; 
    }
    public void setAnimalname(String animalname) { 
        this.animalname = animalname; 
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

    public boolean isReadytoharvest() { 
        return readytoharvest; 
    }

    public void setReadytoharvest(boolean readytoharvest) { 
        this.readytoharvest = readytoharvest; 
    }
}