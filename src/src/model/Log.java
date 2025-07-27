package model;

import java.sql.Timestamp;

public class Log {
    private int logID;              // Unique log ID
    private int playerID;           // ID of the player who performed the action
    private String action;          // Description of the action performed
    private String season;          // Season when the action was performed
    private int day;                // Day of the season when the action was performed
    private int year;               // Year when the action was performed
    private Timestamp timestamp;    // Timestamp of when the action was logged

    public Log() {}

    public Log(int logID, int playerID, String action, String season, int day, int year, Timestamp timestamp) {
        this.logID = logID;
        this.playerID = playerID;
        this.action = action;
        this.season = season;
        this.day = day;
        this.year = year;
        this.timestamp = timestamp;
    }

    public int getLogID() { 
        return logID; 
    
    }
    public void setLogID(int logID) { 
        this.logID = logID; 
    }

    public int getPlayerID() { 
        return playerID; 
    }

    public void setPlayerID(int playerID) { 
        this.playerID = playerID; 
    }

    public String getAction() { 
        return action; 
    }

    public void setAction(String action) { 
        this.action = action; 
    
    }

    public String getSeason() { 
        return season; 
    }

    public void setSeason(String season) { 
        this.season = season; 
    }

    public int getDay() { 
        return day; 
    }

    public void setDay(int day) { 
        this.day = day; 
    }

    public int getYear() { 
        return year; 
    }

    public void setYear(int year) { 
        this.year = year; 
    }

    public Timestamp getTimestamp() { 
        return timestamp; 
    }

    public void setTimestamp(Timestamp timestamp) { 
        this.timestamp = timestamp; 
    }
}
