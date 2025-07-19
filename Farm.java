public class Farm {
    private int playerID;
    private String cropname;
    private String animalname;

    public Farm() {}

    public Farm(int playerID, String cropname, String animalname) {
        this.playerID = playerID;
        this.cropname = cropname;
        this.animalname = animalname;
    }

    public int getPlayerID() { 
        return playerID; 
    }
    public void setPlayerID(int playerID) { 
        this.playerID = playerID; 
    }

    public String getCropname() { 
        return cropname; 
    }

    public void setCropname(String cropname) {
         this.cropname = cropname; 
        }

    public String getAnimalname() { 
        return animalname; 
    }
    public void setAnimalname(String animalname) { 
        this.animalname = animalname;
     }
}