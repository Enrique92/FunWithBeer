package com.example.kike.funwithbeers.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.kike.funwithbeers.R;

public class MainActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private AnimationDrawable animationDrawable;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Hide de action bar
        getSupportActionBar().hide();

        // Initialized the params of the view
        linearLayout = findViewById(R.id.mainMenuLayout);
        linearLayout.setBackgroundResource(R.drawable.animation_list_background);
        animationDrawable = (AnimationDrawable) linearLayout.getBackground();
    }

    /**
     * This method is load when the cursor return to this Activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Add an animation and sound to the GIF that we add
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            player = MediaPlayer.create(this, R.raw.beer_sound);
            player.setLooping(true);
            player.start();
            animationDrawable.start();
        }
    }

    /**
     * This method is call when the activity was in Pause
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            player.stop();
            animationDrawable.stop();
        }
    }

    /**
     * Method that change to the other Activity and select the country
     *
     * @param view
     */
    public void showBeersBy(View view) {
        Intent mContinents = new Intent(getApplicationContext(), SelectionActivity.class);
        startActivity(mContinents);
    }

    /**
     * Method that exit from the app
     *
     * @param view
     */
    public void exitApp(View view) {
        // Exit from the App
        player = MediaPlayer.create(this, R.raw.exit_sound);
        player.setLooping(false);
        player.start();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this, R.style.CustomDialogTheme);
        alertDialog.setMessage("Are you sure you want to quit?");
        alertDialog.setTitle("Exit");
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setCancelable(true);
        alertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                player.stop();
            }
        });
        alertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onResume();
            }
        });
        alertDialog.show();
    }

    /**
     * Method that execute when you press the back button
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        player = MediaPlayer.create(this, R.raw.exit_sound);
        player.setLooping(false);
        player.start();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this, R.style.CustomDialogTheme)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exit")
                    .setMessage("Are you sure you want to quit?")
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                            player.stop();
                        }
                    }).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Method that show more about the app
     *
     * @param view
     */
    public void aboutIt(View view) {
        Intent mAboutIt = new Intent(getApplicationContext(), AboutItActivity.class);
        startActivity(mAboutIt);
    }

    /**
     * Show the brands of the beers of the world
     *
     * @param view
     */
    public void showBrands(View view) {
        Intent mShowBrands = new Intent(getApplicationContext(), BrandActivity.class);
        startActivity(mShowBrands);
    }
}