package com.example.ricoramars.gamebacklog;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Update;
import android.os.AsyncTask;

import java.util.List;

public class GameRepository {
    private GamesDao gamesDao;
    private LiveData<List<Games>> allGames;

    public GameRepository(Application application) {
        GamesDatabase database = GamesDatabase.getInstance(application);
        gamesDao = database.gamesDao();
        allGames = gamesDao.getAllGames();
    }

    public void insert (Games games){
        new InsertGameAsyncTask(gamesDao).execute(games);
    }

    public void update (Games games){
        new UpdateGameAsyncTask(gamesDao).execute(games);
    }

    public void delete (Games games) {
        new DeleteGameAsyncTask(gamesDao).execute(games);
    }

    public LiveData<List<Games>> getAllGames() {
        return allGames;
    }

    private static class InsertGameAsyncTask extends AsyncTask<Games, Void, Void> {
        private GamesDao gamesDao;

        private InsertGameAsyncTask(GamesDao gamesDao){
            this.gamesDao = gamesDao;
        }

        @Override
        protected Void doInBackground(Games... games) {
            gamesDao.insert(games[0]);
            return null;
        }
    }

    private static class UpdateGameAsyncTask extends AsyncTask<Games, Void, Void> {
        private GamesDao gamesDao;

        private UpdateGameAsyncTask(GamesDao gamesDao){
            this.gamesDao = gamesDao;
        }

        @Override
        protected Void doInBackground(Games... games) {
            gamesDao.update(games[0]);
            return null;
        }
    }

    private static class DeleteGameAsyncTask extends AsyncTask<Games, Void, Void> {
        private GamesDao gamesDao;

        private DeleteGameAsyncTask(GamesDao gamesDao){
            this.gamesDao = gamesDao;
        }

        @Override
        protected Void doInBackground(Games... games) {
            gamesDao.delete(games[0]);
            return null;
        }
    }
}
