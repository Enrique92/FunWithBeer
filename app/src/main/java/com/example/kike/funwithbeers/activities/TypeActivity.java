package com.example.kike.funwithbeers.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kike.funwithbeers.R;
import com.example.kike.funwithbeers.connectionSql.DataBaseAccess;

import java.util.ArrayList;

public class TypeActivity extends AppCompatActivity {
    private static final String VALUE_TYPE = "VALUE_TYPE";
    private static final String TYPE = "TYPE";
    private DataBaseAccess sql;
    private TextView mSelectShowBeers;
    private ImageView mGifDrunken;
    private AnimationDrawable animationDrawable;
    private ArrayList<String> mDataTypes;
    private int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        // Hide de action bar
        getSupportActionBar().hide();

        mSelectShowBeers = findViewById(R.id.selectShowBeers);
        mGifDrunken = findViewById(R.id.gifDrunken);

        // Start the gif
        mGifDrunken.setBackgroundResource(R.drawable.animation_type_beers);
        animationDrawable = (AnimationDrawable) mGifDrunken.getBackground();
        animationDrawable.start();

        // Open the connection
        openConnectionDB();

        // Get each type of beer from the DataBase
        getDataBaseInfo();
    }

    /**
     * Open the connection of the DB
     */
    public void openConnectionDB() {
        // Start the connection with the Database
        sql = DataBaseAccess.getInstance(getApplicationContext());
        // Open the connection
        sql.openDataBaseConnection();
    }

    /**
     * Get the information from the Db
     */
    private void getDataBaseInfo() {
        // Get the information of the type of beers
        mDataTypes = sql.getTypeOfBeers();
        mSelectShowBeers.setText(mDataTypes.get(cont));
    }

    /**
     * Method to move to the next value of the Right
     *
     * @param view
     */
    public void moveRight(View view) {
        // Move between the different types
        if (cont >= 0 && cont < mDataTypes.size() - 1) {
            cont++;
        }

        // Get the information of the type of beer
        mDataTypes = sql.getTypeOfBeers();
        mSelectShowBeers.setText(mDataTypes.get(cont));
    }

    /**
     * Method to move to the next value of the Left
     *
     * @param view
     */
    public void moveLeft(View view) {
        // Move between the different types
        if (cont != 0) {
            cont--;
        }

        // Get the information of the type of beer
        mDataTypes = sql.getTypeOfBeers();
        mSelectShowBeers.setText(mDataTypes.get(cont));
    }

    /**
     * This method return to the Continent Activity
     *
     * @param view
     */
    public void returnMenu(View view) {
        finish();
    }

    public void goTypeBeers(View view) {
        Intent mTypesBeers = new Intent(getApplicationContext(), BeerActivity.class);
        mTypesBeers.putExtra(VALUE_TYPE, "Type");
        mTypesBeers.putExtra(TYPE, mDataTypes.get(cont));
        startActivity(mTypesBeers);
    }
}
