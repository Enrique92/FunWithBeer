package com.example.kike.funwithbeers.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kike.funwithbeers.R;
import com.example.kike.funwithbeers.connectionSql.DataBaseAccess;
import com.example.kike.funwithbeers.models.Beer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BeerActivity extends AppCompatActivity {
    private static final int NOTIFICATION_ID = 0;
    private static final String CHANNEL_ID = "notification_channel";
    private ImageButton mImageBeer;
    private TextView mNameBeer;
    private TextView mTypeBeer;
    private TextView mCountryBeer;
    private TextView mABVBeer;
    private TextView mDescBeer;
    private RatingBar mRatingBeer;
    private String mCountryPress, url;
    private ArrayList<Beer> mDataBeer;
    private int cont = 0;
    private NotificationManager mManager;
    private DataBaseAccess sql;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);

        // Hide de action bar
        getSupportActionBar().hide();

        // Clean the arraylist
        mDataBeer = new ArrayList<>();

        mImageBeer = findViewById(R.id.imageBeer);
        mNameBeer = findViewById(R.id.beerName);
        mTypeBeer = findViewById(R.id.beerType);
        mCountryBeer = findViewById(R.id.beerCountry);
        mABVBeer = findViewById(R.id.beerABV);
        mDescBeer = findViewById(R.id.beerDescription);
        mRatingBeer = findViewById(R.id.ratingBeer);

        mImageBeer.setBackgroundColor(Color.TRANSPARENT);

        // Sound when we go inside beerActivity
        player = MediaPlayer.create(this, R.raw.mmm_beer);
        player.setLooping(false);
        player.start();

        // Open the connection
        openConnectionDB();

        // Load the database with the information
        getDataBaseInfo();

        // Send the rating of each beer to the Database
        sendRatingBeer();

        // Create the notification for the user
        createNotificationChannel();
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
     * Send the rate to the Database
     */
    public void sendRatingBeer() {
        mRatingBeer.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                // Only if the Score is different from the old one we update the data and show the notification
                if (mRatingBeer.getRating() != mDataBeer.get(cont).getScore()) {
                    switch ((int) ratingBar.getRating()) {
                        case 1:
                            deliverNotification(BeerActivity.this,
                                    "Beer rated: " + mNameBeer.getText() + Html.fromHtml("<br/>") + ". You hate it!");
                            soundRate(mRatingBeer.getRating());
                            break;
                        case 2:
                            deliverNotification(BeerActivity.this,
                                    "Beer rated: " + mNameBeer.getText() + ". \nNot bad!");
                            soundRate(mRatingBeer.getRating());
                            break;
                        case 3:
                            deliverNotification(BeerActivity.this,
                                    "Beer rated: " + mNameBeer.getText() + ". \nIt was good!");
                            soundRate(mRatingBeer.getRating());
                            break;
                        case 4:
                            deliverNotification(BeerActivity.this,
                                    "Beer rated: " + mNameBeer.getText() + ". \nIt was great!");
                            soundRate(mRatingBeer.getRating());
                            break;
                        case 5:
                            deliverNotification(BeerActivity.this,
                                    "Beer rated: " + mNameBeer.getText() + ". \nAwesome, you love it!");
                            soundRate(mRatingBeer.getRating());
                            break;
                        default:
                            makeToast("");
                    }
                }
                // Update the score
                sql.updateScore(mRatingBeer.getRating(), mDataBeer.get(cont).getId());

                // Change the score of the setScore to see the real score when you move between the beers
                mDataBeer.get(cont).setScore(mRatingBeer.getRating());
            }
        });
    }

    /**
     * Give sound to the screen
     * @param rate
     */
    public void soundRate(float rate) {
        if (rate == 5) {
            player = MediaPlayer.create(this, R.raw.five_starts);
        } else if (rate == 1) {
            player = MediaPlayer.create(this, R.raw.one_start);
        } else {
            player = MediaPlayer.create(this, R.raw.rest_starts);
        }
        player.start();
    }

    /**
     * Create the notification for the beers that the user rate
     */
    public void createNotificationChannel() {
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "You rate a new beer!", NotificationManager.IMPORTANCE_HIGH
            );
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.RED);
            channel.setDescription("You rate a new beer!");
            mManager.createNotificationChannel(channel);
        }
    }

    /**
     * Show the notification in the device
     *
     * @param context
     * @param message
     */
    public void deliverNotification(Context context, String message) {
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context, CHANNEL_ID
        ).setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("You rate a new beer!")
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        mManager.notify(NOTIFICATION_ID, builder.build());
    }

    /**
     * This method return the value of the select and add to an ArrayList
     * after that we get the data from the array and we print in each TextView
     * and the image in the ImageView
     */
    private void getDataBaseInfo() {
        // Get the name of the country and after that we get the beers of that country
        mCountryPress = getIntent().getStringExtra("FLAG");
        String params = mCountryPress;

        // Get the information of each beer, can be from country or by type of beer
        String mValueCou = getIntent().getStringExtra("VALUE_COUNTRY");
        String mValueTyp = getIntent().getStringExtra("VALUE_TYPE");
        String mType = getIntent().getStringExtra("TYPE");

        Log.i("TAG", "mValueCou ->" + mValueCou);
        Log.i("TAG", "mValueTyp ->" + mValueTyp);
        Log.i("TAG", "mType ->" + mType);

        if (mValueTyp == null || mValueCou == "Country") {
            Log.i("TAG", "HERE IF");
            mDataBeer = sql.getInfoBeer(params);
        } else {
            Log.i("TAG", "HERE ELSE");
            mDataBeer = sql.getBeersFromType(mType);
        }

        mNameBeer.setText(mDataBeer.get(cont).getName());
        mTypeBeer.setText(mDataBeer.get(cont).getType());
        mCountryBeer.setText(mDataBeer.get(cont).getCountry());
        mABVBeer.setText(mDataBeer.get(cont).getABV());
        mDescBeer.setText(mDataBeer.get(cont).getDescription());
        mRatingBeer.setRating(mDataBeer.get(cont).getScore());

        // Print the image of each beer
        Picasso.get()
                .load(mDataBeer.get(cont).getLinkimage())
                .fit()
                .centerCrop()
                .into(mImageBeer);
        mImageBeer.setClipToOutline(true);
    }

    /**
     * This method move the cursor to the next flag of the left
     *
     * @param view
     */
    public void moveLeft(View view) {
        // Move between the different beers
        if (cont != 0) {
            cont--;
        }

        // Get the information of each beer that the country have
        url = mDataBeer.get(cont).getLinkimage();
        mNameBeer.setText(mDataBeer.get(cont).getName());
        mTypeBeer.setText(mDataBeer.get(cont).getType());
        mCountryBeer.setText(mDataBeer.get(cont).getCountry());
        mABVBeer.setText(mDataBeer.get(cont).getABV());
        mDescBeer.setText(mDataBeer.get(cont).getDescription());

        // Update the score
        //mRatingBeer.setRating(mDataBeer.get(cont).getScore());

        // Update the score of the beer in the Database
        updateScoreBeer();

        // Print the image of each beer
        Picasso.get()
                .load(url)
                .fit()
                .centerCrop()
                .into(mImageBeer);
        mImageBeer.setClipToOutline(true);
    }

    /**
     * This method move the cursor to the next flag of the right
     *
     * @param view
     */
    public void moveRight(View view) {
        // Move between the different beers
        if (cont >= 0 && cont < mDataBeer.size() - 1) {
            cont++;
        }

        // Get the information of each beer that the country have
        url = mDataBeer.get(cont).getLinkimage();
        mNameBeer.setText(mDataBeer.get(cont).getName());
        mTypeBeer.setText(mDataBeer.get(cont).getType());
        mCountryBeer.setText(mDataBeer.get(cont).getCountry());
        mABVBeer.setText(mDataBeer.get(cont).getABV());
        mDescBeer.setText(mDataBeer.get(cont).getDescription());

        // Update the score of the beer in the Database
        updateScoreBeer();

        // Print the image of each beer
        Picasso.get()
                .load(url)
                .fit()
                .centerCrop()
                .into(mImageBeer);
        mImageBeer.setClipToOutline(true);
    }

    /**
     * Update the score of the beers
     */
    public void updateScoreBeer() {
        mRatingBeer.setRating(mDataBeer.get(cont).getScore());
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
     * Show the messages in the screen
     *
     * @param message
     */
    public void makeToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
