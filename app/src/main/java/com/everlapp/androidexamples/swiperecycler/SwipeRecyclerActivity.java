package com.everlapp.androidexamples.swiperecycler;

import android.graphics.Canvas;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.everlapp.androidexamples.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/FanFataL/swipe-controller-demo/blob/master/app/src/main/java/pl/fanfatal/swipecontrollerdemo/MainActivity.java
 *
 * https://medium.com/@maydin/multi-and-single-selection-in-recyclerview-d29587a7dee2
 */
public class SwipeRecyclerActivity extends AppCompatActivity {

    private PlayersDataAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_recycler);

        setPlayersDataAdapter();
        setupRecyclerView();

    }


    private void setPlayersDataAdapter() {
        List<Player> players = new ArrayList<>();
        try {
            InputStreamReader is = new InputStreamReader(getAssets().open("datasets/players.csv"));
            BufferedReader br = new BufferedReader(is);
            br.readLine();
            String line;
            String[] st;
            while ((line = br.readLine()) != null) {
                st = line.split(",");
                Player player = new Player();
                player.setName(st[6]);
                player.setNationality(st[2]);
                player.setClub(st[3]);
                player.setRating(5);
                player.setAge(5);

                player.setSelected(false);
                players.add(player);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        mAdapter = new PlayersDataAdapter(players);
    }


    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        // Add Item swipe
        final SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                mAdapter.getPlayers().remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // Draw buttons
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }


    // Toast.makeText(this, "Selected player: " + player.getName(), Toast.LENGTH_LONG).show();
}
