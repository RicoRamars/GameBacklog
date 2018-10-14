package com.example.ricoramars.gamebacklog;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_GAME_REQUEST = 1234;
    public static final int EDIT_GAME_REQUEST = 5678;

    private GameViewholder gameViewholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.add_game_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditGameActivity.class);
                startActivityForResult(intent, ADD_GAME_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.gameList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final GameAdapter adapter = new GameAdapter();
        recyclerView.setAdapter(adapter);

        gameViewholder = ViewModelProviders.of(this).get(GameViewholder.class);
        gameViewholder.getAllGames().observe(this, new Observer<List<Games>>() {
            //Elke keer als er iets veranderd in de livedata update dit.
            @Override
            public void onChanged(@Nullable List<Games> games) {
                //Update RecyclerView
                adapter.setGames(games);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                gameViewholder.delete(adapter.getGamesAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Game deleted from list", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClikListener(new GameAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Games games) {
                Intent intent = new Intent(MainActivity.this, AddEditGameActivity.class);
                intent.putExtra(AddEditGameActivity.EXTRA_ID, games.getId());
                intent.putExtra(AddEditGameActivity.EXTRA_NAME, games.getGameName());
                intent.putExtra(AddEditGameActivity.EXTRA_PLATFORM, games.getPlatformType());
                intent.putExtra(AddEditGameActivity.EXTRA_NOTES, games.getNotes());
                intent.putExtra(AddEditGameActivity.EXTRA_STATUS, games.getGameStatus());
                startActivityForResult(intent, EDIT_GAME_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_GAME_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddEditGameActivity.EXTRA_NAME);
            String note = data.getStringExtra(AddEditGameActivity.EXTRA_NOTES);
            String platform = data.getStringExtra(AddEditGameActivity.EXTRA_PLATFORM);
            String status = data.getStringExtra(AddEditGameActivity.EXTRA_STATUS);

            Games games = new Games(name, note, platform, status);
            gameViewholder.insert(games);

            Toast.makeText(this, "Game saved", Toast.LENGTH_SHORT).show();
        } else {
            int id = data.getIntExtra(AddEditGameActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Game can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = data.getStringExtra(AddEditGameActivity.EXTRA_NAME);
            String note = data.getStringExtra(AddEditGameActivity.EXTRA_NOTES);
            String platform = data.getStringExtra(AddEditGameActivity.EXTRA_PLATFORM);
            String status = data.getStringExtra(AddEditGameActivity.EXTRA_STATUS);

            Games games = new Games(name, note, platform, status);
            games.setId(id);
            gameViewholder.update(games);
            Toast.makeText(this, "Game updated", Toast.LENGTH_SHORT).show();
        }
    }
}
