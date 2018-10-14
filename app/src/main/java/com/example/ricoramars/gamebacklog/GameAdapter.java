package com.example.ricoramars.gamebacklog;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameHolder> {
    private List<Games> games = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_gamecards, parent, false);
        return new GameHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder holder, int position) {
        Games currentGames = games.get(position);
        holder.textViewGameTitle.setText(currentGames.getGameName());
        holder.textViewPlatform.setText(currentGames.getPlatformType());
        holder.textViewStatus.setText(currentGames.getGameStatus());
        holder.textViewNote.setText(currentGames.getNotes());
    }
    @Override
    public int getItemCount() {
        return games.size();
    }

    public void setGames(List<Games> games){
        this.games = games;
        notifyDataSetChanged();
    }

    public Games getGamesAt(int position) {
        return games.get(position);
    }

    class GameHolder extends RecyclerView.ViewHolder {
        private TextView textViewGameTitle;
        private TextView textViewPlatform;
        private TextView textViewStatus;
        private TextView textViewNote;

        public GameHolder(@NonNull View itemView) {
            super(itemView);
            textViewGameTitle = itemView.findViewById(R.id.game_Name);
            textViewPlatform = itemView.findViewById(R.id.platform_Type);
            textViewStatus = itemView.findViewById(R.id.game_Status);
            textViewNote = itemView.findViewById(R.id.game_Notes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(games.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(Games games);
    }

    public void setOnItemClikListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
