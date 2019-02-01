package com.example.kike.funwithbeers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kike.funwithbeers.R;
import com.example.kike.funwithbeers.connectionSql.DataBaseAccess;
import com.example.kike.funwithbeers.models.Continent;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ContinentActivity extends AppCompatActivity {
    private static final String CONT = "CONT";
    private ImageButton mImgViewContinents;
    private ArrayList<Continent> mContinentsList;
    private TextView nameContinent, numberCountries;
    private int cont = 0;
    private DataBaseAccess sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continent);
        // Hide de action bar
        getSupportActionBar().hide();

        mImgViewContinents = findViewById(R.id.imgContinent);
        nameContinent = findViewById(R.id.nameContinent);
        numberCountries = findViewById(R.id.numberCountries);

        // Insert the values in the ArrayList of continents
        loadContinents();

        // Set the text of the TextView to show the name of the
        // continent and the number of countries that it have.
        nameContinent.setText(mContinentsList.get(cont).getName());

        // Print the image of each continent
        Picasso.get()
                .load(mContinentsList.get(cont).getImage())
                .fit()
                .centerCrop()
                .into(mImgViewContinents);
        mImgViewContinents.setClipToOutline(true);

        // Open the connection of the Database
        openConnectionDB();

        // Get the number of countries that the continent have
        getNumberCountries();
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
     * Method that load all the continents to show in the screen
     */
    private void loadContinents() {
        mContinentsList = new ArrayList<>();
        mContinentsList.add(new Continent(0, "North America", "NA", R.drawable.noth_america));
        mContinentsList.add(new Continent(1, "Central America", "CA", R.drawable.central_america));
        mContinentsList.add(new Continent(2, "South America", "SA", R.drawable.south_america));
        mContinentsList.add(new Continent(3, "Europe", "EU", R.drawable.europe));
        mContinentsList.add(new Continent(4, "Africa", "AF", R.drawable.africa));
        mContinentsList.add(new Continent(5, "Asia", "AS", R.drawable.asia));
        mContinentsList.add(new Continent(6, "Oceania", "OC", R.drawable.oceania));
    }

    /**
     * Method to return to the Main Menu
     *
     * @param view
     */
    public void returnMenu(View view) {
        finish();
    }

    /**
     * Method to move to the next continent of the Left
     *
     * @param view
     */
    public void moveLeftCont(View view) {
        if (cont > 0) {
            cont--;
        }

        // Get the number of countries that the continent have
        getNumberCountries();

        // Print the name of the continent
        nameContinent.setText(mContinentsList.get(cont).getName());

        // Print the image of each continent
        Picasso.get()
                .load(mContinentsList.get(cont).getImage())
                .fit()
                .centerCrop()
                .into(mImgViewContinents);
        mImgViewContinents.setClipToOutline(true);
    }

    /**
     * Method to move to the next continent of the Right
     *
     * @param view
     */
    public void moveRightCont(View view) {
        if (cont < 6) {
            cont++;
        } else {
            cont = 6;
        }

        // Get the number of countries that the continent have
        getNumberCountries();

        // Print the name of the continent
        nameContinent.setText(mContinentsList.get(cont).getName());

        // Print the image of each continent
        Picasso.get()
                .load(mContinentsList.get(cont).getImage())
                .fit()
                .centerCrop()
                .into(mImgViewContinents);
        mImgViewContinents.setClipToOutline(true);
    }

    /**
     * Get the number of countries from the Database
     */
    public void getNumberCountries() {
        numberCountries.setText(String.valueOf(sql.getNumCou(mContinentsList.get(cont).getShortName())));
    }

    /**
     * Method to move to the FlagActivity and show the flags of that continent
     *
     * @param view
     */
    public void viewCountriesContinent(View view) {
        Intent mCountries = new Intent(getApplicationContext(), FlagActivity.class);
        mCountries.putExtra(CONT, mContinentsList.get(cont).getShortName());
        startActivityForResult(mCountries, 1);
    }
}
