package com.example.ricoramars.gamebacklog;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.widget.Spinner;

@Entity(tableName = "games_table")
public class Games {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String GameName;

    private String PlatformType;

    private String Notes;

    public String GameStatus;

    public Games(String GameName, String PlatformType, String Notes, String GameStatus) {
        this.GameName = GameName;
        this.PlatformType = PlatformType;
        this.Notes = Notes;
        this.GameStatus = GameStatus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }

    public String getGameName() {

        return GameName;
    }

    public String getPlatformType() {
        return PlatformType;
    }

    public String getNotes() {
        return Notes;
    }

    public String getGameStatus() {
        return GameStatus;
    }
}
