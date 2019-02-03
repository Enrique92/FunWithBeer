package com.example.kike.funwithbeers.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.kike.funwithbeers.R;

public class AboutItActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_it);

        // Hide de action bar
        getSupportActionBar().hide();
    }

    /**
     * Method that return to the Main Menu
     *
     * @param view
     */
    public void returnMenu(View view) {
        finish();
    }
}
