package model;

public class Player {
    private int playerID;           // Unique player ID
    private String playername;      // Player name
    private int wallet;             // Player wallet
    private int current_day;        // Current day
    private String current_season;  // Current season
    private int current_year;       // Current year

    public Player() {}

    public Player(int playerID, String playername, int wallet, int current_day, String current_season, int current_year) {
        this.playerID = playerID;
        this.playername = playername;
        this.wallet = wallet;
        this.current_day = current_day;
        this.current_season = current_season;
        this.current_year = current_year;
    }

    public int getPlayerID() { 
        return playerID; 
    }
    
    public void setPlayerID(int playerID) { 
        this.playerID = playerID;
    }

    public String getPlayername() { 
        return playername; 
    }

    public void setPlayername(String playername) { 
        this.playername = playername; 
    }

    public int getWallet() { 
        return wallet; 
    }

    public void setWallet(int wallet) { 
        this.wallet = wallet; 
    }

    public int getCurrent_day() { 
        return current_day; 
    }

    public void setCurrent_day(int current_day) { 
        this.current_day = current_day; 
    }

    public String getCurrent_season() { 
        return current_season; 
    }

    public void setCurrent_season(String current_season) { 
        this.current_season = current_season; 
    }

    public int getCurrent_year() { 
        return current_year; 
    }

    public void setCurrent_year(int current_year) { 
        this.current_year = current_year; 
    }
}