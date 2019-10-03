package com.everlapp.androidexamples.swiperecycler;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
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
        extends RecyclerView.Adapter<PlayersDataAdapter.PlayerViewHolder>
        implements OnItemSelected {

    private final String TAG = this.getClass().getSimpleName();

    private List<Player> players;

    private boolean isMultiSelectionEnabled = false;
    //private List<Player> selectedPlayers;


    private OnItemSelected itemSelectedListener;


    // constr
    public PlayersDataAdapter(List<Player> players) {
        this.players = players;
        isMultiSelectionEnabled = true;     // todo set from constructor

        itemSelectedListener = this;

        /*selectedPlayers = new ArrayList<>();
        for (Player player : players) {

        }*/
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

        holder.player = player;
        holder.setChecked(holder.player.isSelected());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isMultiSelectionEnabled) {
            return PlayerViewHolder.MULTI_SELECTION;
        } else {
            return PlayerViewHolder.SINGLE_SELECTION;
        }
    }


    @Override
    public void OnItemSelected(Player player) {
        Log.d(TAG, "New On item sell: " + player.getName());
    }


    /**
     * View Holder
     */
    class PlayerViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener, View.OnLongClickListener {

        public static final int MULTI_SELECTION = 2;
        public static final int SINGLE_SELECTION = 1;
        Player player;

        private TextView name;
        private TextView nationality;
        private TextView club;
        private TextView rating;
        private TextView age;


        public PlayerViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

            name = view.findViewById(R.id.name);
            nationality = view.findViewById(R.id.nationality);
            club = view.findViewById(R.id.club);
            rating = view.findViewById(R.id.rating);
            age = view.findViewById(R.id.age);
        }

        @Override
        public void onClick(View v) {
            // TODO Temp onClick -> need OnLongClick !!!
            if (player.isSelected() && getItemViewType() == MULTI_SELECTION) {
                setChecked(false);
            } else {
                setChecked(true);
            }

            itemSelectedListener.OnItemSelected(player);
            Log.d(TAG, "ON ITEM CLick!!!!");
        }

        @Override
        public boolean onLongClick(View v) {
            return true;
        }

        public void setChecked(boolean value) {
            if (value) {
                name.setBackgroundColor(Color.BLUE);
            } else {
                name.setBackgroundColor(Color.RED);
            }
            player.setSelected(value);
        }


    }


    public List<Player> getPlayers() {
        return players;
    }

}
