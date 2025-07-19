package model;

public class Farm {
    private int playerID;
    private int cropID;
    private int animalID;

    public Farm() {}

    public Farm(int playerID, int cropID, int animalID) {
        this.playerID = playerID;
        this.cropID = cropID;
        this.animalID = animalID;
    }

    public int getPlayerID() { 
        return playerID; 
    }
    public void setPlayerID(int playerID) { 
        this.playerID = playerID; 
    }

    public int getCropID() { 
        return cropID; 
    }

    public void setCropID(int cropID) {
         this.cropID = cropID; 
        }

    public int getAnimalID() { 
        return animalID; 
    }
    public void setAnimalID(int animalID) { 
        this.animalID = animalID;
     }
}