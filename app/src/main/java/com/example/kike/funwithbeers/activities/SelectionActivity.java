package com.example.kike.funwithbeers.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kike.funwithbeers.R;
import com.example.kike.funwithbeers.connectionSql.DataBaseAccess;
import com.example.kike.funwithbeers.models.Continent;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SelectionActivity extends AppCompatActivity {
    private static final int REQ = 1;
    private ImageButton mImageSelection;
    private TextView mSelectionText;
    private AnimationDrawable animationDrawable;
    private int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        // Hide de action bar
        getSupportActionBar().hide();

        mImageSelection = findViewById(R.id.imgShowBy);
        mSelectionText = findViewById(R.id.selectionText);


        // Load the two possibilities to show the beers
        loadSelection();
    }

    /**
     * Method that load all the continents to show in the screen
     */
    private void loadSelection() {
        mSelectionText.setText("Continent");
        mImageSelection.setBackgroundResource(R.drawable.animation_list_type_continents);
        animationDrawable = (AnimationDrawable) mImageSelection.getBackground();
        animationDrawable.start();
    }

    /**
     * Method to move to the next value of the Left
     *
     * @param view
     */
    public void moveLeft(View view) {
        if (cont > 0) {
            cont--;
        }

        mSelectionText.setText("Continent");

        // Print the image of each continent
        Picasso.get()
                .load(R.drawable.animation_list_type_continents)
                .fit()
                .centerCrop()
                .into(mImageSelection);
        mImageSelection.setClipToOutline(true);

        mImageSelection.setBackgroundResource(R.drawable.animation_list_type_continents);
        animationDrawable = (AnimationDrawable) mImageSelection.getBackground();
        animationDrawable.start();
    }

    /**
     * Method to move to the next value of the Right
     *
     * @param view
     */
    public void moveRight(View view) {
        if (cont < 1) {
            cont++;
        } else {
            cont = 1;
        }

        mSelectionText.setText("Type of Beer");
        mImageSelection.setBackgroundResource(R.color.colorBackground);

        // Print the image of each continent
        Picasso.get()
                .load(R.drawable.types_of_beers)
                .fit()
                .centerCrop()
                .into(mImageSelection);
        mImageSelection.setClipToOutline(true);
    }

    /**
     * Go to one Activity or the other depending of how we want to show the beers
     * @param view
     */
    public void showBeerBy(View view) {
        if (cont == 0) {
            Intent mContinents = new Intent(getApplicationContext(), ContinentActivity.class);
            startActivityForResult(mContinents, REQ);
        } else {
            Intent mType = new Intent(getApplicationContext(), TypeActivity.class);
            startActivityForResult(mType, REQ);
        }
    }

    /**
     * This method return to the Continent Activity
     *
     * @param view
     */
    public void returnMenu(View view) {
        finish();
    }
}
