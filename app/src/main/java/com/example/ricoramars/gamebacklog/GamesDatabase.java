package com.example.ricoramars.gamebacklog;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Games.class}, version = 1)
public abstract class GamesDatabase extends RoomDatabase {

    private static GamesDatabase instance;

    public abstract GamesDao gamesDao();

    public static synchronized GamesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    GamesDatabase.class, "games_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private GamesDao gamesDao;

        private PopulateDbAsyncTask(GamesDatabase db){
            gamesDao = db.gamesDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            gamesDao.insert(new Games("Call of Duty", "Playstation", "Hyped", "playing"));
            gamesDao.insert(new Games("World of Warcraft", "Pc", "Level 100", "Want to play"));
            gamesDao.insert(new Games("Flight simulator", "pc", "Raising money", "Dropped"));
            return null;
        }
    }
}
