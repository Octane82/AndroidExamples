package com.everlapp.androidexamples.swiperecycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.everlapp.androidexamples.R;

import java.util.List;

/**
 * RecyclerView players adapter
 */
public class PlayersDataAdapter
        extends RecyclerView.Adapter<PlayersDataAdapter.PlayerViewHolder> {

    private List<Player> players;

    public PlayersDataAdapter(List<Player> players) {
        this.players = players;
    }


    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_row, parent, false);
        return new PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = players.get(position);
        holder.name.setText(player.getName());
        holder.nationality.setText(player.getNationality());
        holder.club.setText(player.getClub());
        holder.rating.setText(player.getRating().toString());
        holder.age.setText(player.getAge().toString());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }



    class PlayerViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView nationality;
        private TextView club;
        private TextView rating;
        private TextView age;


        public PlayerViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            nationality = view.findViewById(R.id.nationality);
            club = view.findViewById(R.id.club);
            rating = view.findViewById(R.id.rating);
            age = view.findViewById(R.id.age);
        }

    }

    public List<Player> getPlayers() {
        return players;
    }

}
