package model;

import java.sql.Timestamp;

public class Log {
    private int logID;
    private int playerID;
    private String action;
    private String season;
    private int day;
    private int year;
    private Timestamp timestamp;

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
