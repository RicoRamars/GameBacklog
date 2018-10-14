package com.example.ricoramars.gamebacklog;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class GameViewholder extends AndroidViewModel{
    private GameRepository repository;
    private LiveData<List<Games>> allGames;

    public GameViewholder(@NonNull Application application) {
        super(application);
        repository = new GameRepository(application);
        allGames = repository.getAllGames();
    }

    public void insert(Games games) {
        repository.insert(games);
    }

    public void update(Games games) {
        repository.update(games);
    }

    public void delete(Games games) {
        repository.delete(games);
    }

    public LiveData<List<Games>> getAllGames() {
        return allGames;
    }
}
