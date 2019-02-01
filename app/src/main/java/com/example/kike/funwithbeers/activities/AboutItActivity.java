package com.example.kike.funwithbeers.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.kike.funwithbeers.R;

public class AboutItActivity extends AppCompatActivity {

    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_it);

        // Hide de action bar
        getSupportActionBar().hide();
    }

    /**
     * Method that is called when it start the Activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        // When the activity start, we start the song at the background
        player = MediaPlayer.create(this, R.raw.all_go_to_the_bar);
        player.setLooping(true);
        player.start();
    }

    /**
     * Method that is called when you return to the Main Menu and destroy the old Activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
    }

    /**
     * Method that return to the Main Menu
     *
     * @param view
     */
    public void returnMenu(View view) {
        Intent mMainMenu = new Intent(getApplicationContext(), MainActivity.class);
        finish();
    }
}
