package com.example.kike.funwithbeers.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kike.funwithbeers.R;
import com.example.kike.funwithbeers.connectionSql.DataBaseAccess;
import com.example.kike.funwithbeers.models.Flag;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FlagActivity extends AppCompatActivity {
    private ImageButton mImgViewFlag;
    private TextView mNameCountry, mNameCont;
    private int cont = 0;
    private Integer counter = 1;
    private String mRegionContPress, url;
    private ProgressBar mProgressBar;
    private TextView mTextProgressBar;
    private ArrayList<Flag> mUrlFlags;
    private static final String FLAG = "FLAG";
    private static final String VALUE_COUNTRY = "VALUE_COUNTRY";
    private DataBaseAccess sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag);

        // Hide de action bar
        getSupportActionBar().hide();

        // Initialized the params of the view
        mImgViewFlag = findViewById(R.id.imgFlag);
        mNameCountry = findViewById(R.id.nameCountry);
        mNameCont = findViewById(R.id.nameCont);
        mProgressBar = findViewById(R.id.progressBar);
        mTextProgressBar = findViewById(R.id.textProgressBar);

        // Open the connection to the Database
        openConnectionDB();

        // Load the database with the information
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
     * Method that start the progressbar when you are waiting to see the Image
     */
    public void launchProgressBar() {
        // Configure the progressBar
        mProgressBar.setMax(10);
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setProgress(0);
        new MyAsyncTask().execute(1);
    }

    class MyAsyncTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            for (; counter <= params[0]; counter++) {
                try {
                    Thread.sleep(1000);
                    publishProgress(counter);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            mProgressBar.setVisibility(View.GONE);
            mTextProgressBar.setText(result);
        }

        @Override
        protected void onPreExecute() {
            mTextProgressBar.setText("Loading...");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mTextProgressBar.setText("Loading...");
            mProgressBar.setProgress(values[0]);
        }
    }

    /**
     * Mehod that print the image
     */
    public void printImage() {
        // Print the flag of each country
        Picasso.get()
                .load(mUrlFlags.get(cont).getFlag())
                .fit()
                .centerCrop()
                .into(mImgViewFlag);
        mImgViewFlag.setClipToOutline(true);
    }


    /**
     * This method get the data from the DB and then print in the screen
     */
    public void getDataBaseInfo() {
        // Get the name of the continent and after that we get the countries of that continent
        mRegionContPress = getIntent().getStringExtra("CONT");
        String params = mRegionContPress;

        // Get the name, continent and flags of the continent from the select
        mUrlFlags = sql.getInfoFlag(params);
        mNameCountry.setText(mUrlFlags.get(cont).getName());
        mNameCont.setText(mUrlFlags.get(cont).getRegion());
        url = mUrlFlags.get(cont).getFlag();

        launchProgressBar();

        // Print the flag of each country
        printImage();
    }

    /**
     * This method return to the Continent Activity
     *
     * @param view
     */
    public void returnMenu(View view) {
        finish();
    }

    /**
     * This method move the cursor to the next flag of the left
     *
     * @param view
     */
    public void moveLeft(View view) {
        // Move between the different flags
        if (cont != 0) {
            cont--;
        }

        // Get the name, continent and flags of the continent from the select
        url = mUrlFlags.get(cont).getFlag();
        mNameCountry.setText(mUrlFlags.get(cont).getName());
        mNameCont.setText(mUrlFlags.get(cont).getRegion());

        launchProgressBar();

        // Print the flag of each country
        printImage();
    }

    /**
     * This method move the cursor to the next flag of the right
     *
     * @param view
     */
    public void moveRight(View view) {
        // Move between the different flags
        if (cont >= 0 && cont < mUrlFlags.size() - 1) {
            cont++;
        }

        // Get the name, continent and flags of the continent from the select
        url = mUrlFlags.get(cont).getFlag();
        mNameCountry.setText(mUrlFlags.get(cont).getName());
        mNameCont.setText(mUrlFlags.get(cont).getRegion());

        launchProgressBar();

        // Print the flag of each country
        printImage();
    }

    /**
     * Method to move to the next activity
     * @param view
     */
    public void showBeers(View view) {
        Intent mFlags = new Intent(getApplicationContext(), BeerActivity.class);
        mFlags.putExtra(VALUE_COUNTRY, "Country");
        mFlags.putExtra(FLAG, mUrlFlags.get(cont).getName());
        startActivity(mFlags);
    }
}
