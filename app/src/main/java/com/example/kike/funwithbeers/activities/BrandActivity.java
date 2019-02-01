package com.example.kike.funwithbeers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kike.funwithbeers.R;
import com.example.kike.funwithbeers.connectionSql.DataBaseAccess;
import com.example.kike.funwithbeers.models.Brand;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BrandActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ImageView mImageBrand;
    private TextView mNameBrand;
    private TextView mFoundedBrand;
    private TextView mCountryBrand;
    private TextView mDescBeer;
    private String url;
    private ArrayList<Brand> mDataBrand;
    private int cont = 0;
    private DataBaseAccess sql;
    private float lat, log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands);

        // Hide de action bar
        getSupportActionBar().hide();

        // Clean the arraylist
        mDataBrand = new ArrayList<>();

        mImageBrand = findViewById(R.id.imageBrand);
        mNameBrand = findViewById(R.id.nameBrand);
        mFoundedBrand = findViewById(R.id.yearBrand);
        mCountryBrand = findViewById(R.id.countryBrand);
        mDescBeer = findViewById(R.id.descBrand);

        // Open the connection
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
     * This method return the value of the select and add to an ArrayList
     * after that we get the data from the array and we print in each TextView
     * and the image in the ImageView
     */
    private void getDataBaseInfo() {
        // Get the information of each beer that the country have
        mDataBrand = sql.getInfoBrand();
        mNameBrand.setText(mDataBrand.get(cont).getName());
        mFoundedBrand.setText(mDataBrand.get(cont).getFounded());
        mCountryBrand.setText(mDataBrand.get(cont).getCountry());
        mDescBeer.setText(mDataBrand.get(cont).getDescription());

        // Get the location of each main factory
        getLatLng();

        // Print the image of each beer
        Picasso.get()
                .load(mDataBrand.get(cont).getLinkImageBrand())
                //.resize(120, 100)
                .fit()
                .centerCrop()
                .into(mImageBrand);
        mImageBrand.setClipToOutline(true);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * This method move the cursor to the next flag of the left
     *
     * @param view
     */
    public void moveLeft(View view) {
        // Move between the different brands
        if (cont != 0) {
            cont--;
        }

        // Get the information of each brand
        url = mDataBrand.get(cont).getLinkImageBrand();
        mNameBrand.setText(mDataBrand.get(cont).getName());
        mFoundedBrand.setText(mDataBrand.get(cont).getFounded());
        mCountryBrand.setText(mDataBrand.get(cont).getCountry());
        mDescBeer.setText(mDataBrand.get(cont).getDescription());

        // Get the location of each main factory
        getLatLng();

        // Print the image of each brand
        Picasso.get()
                .load(url)
                //.resize(120, 100)
                .fit()
                .centerCrop()
                .into(mImageBrand);
        mImageBrand.setClipToOutline(true);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * This method move the cursor to the next flag of the right
     *
     * @param view
     */
    public void moveRight(View view) {
        // Move between the different brands
        if (cont >= 0 && cont < mDataBrand.size() - 1) {
            cont++;
        }

        // Get the information of each brand
        url = mDataBrand.get(cont).getLinkImageBrand();
        mNameBrand.setText(mDataBrand.get(cont).getName());
        mFoundedBrand.setText(mDataBrand.get(cont).getFounded());
        mCountryBrand.setText(mDataBrand.get(cont).getCountry());
        mDescBeer.setText(mDataBrand.get(cont).getDescription());

        // Get the location of each main factory
        getLatLng();

        // Print the image of each brand
        Picasso.get()
                .load(url)
                //.resize(120, 100)
                .fit()
                .centerCrop()
                .into(mImageBrand);
        mImageBrand.setClipToOutline(true);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * This method return to the Continent Activity
     *
     * @param view
     */
    public void returnMenu(View view) {
        finish();
    }

    public void getLatLng() {
        lat = mDataBrand.get(cont).getLat();
        log = mDataBrand.get(cont).getLog();
    }

    /**
     * This method print the location of the main factory of the Brand
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        Log.i("TAG", "LAT: " + lat);
        Log.i("TAG", "LOG: " + log);
        LatLng location = new LatLng(lat, log);
        googleMap.addMarker(new MarkerOptions().position(location)
                .title(mNameBrand.getText().toString()).icon(BitmapDescriptorFactory.
                        fromResource(R.drawable.ic_location)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}
