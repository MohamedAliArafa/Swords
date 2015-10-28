package com.example.nezarsaleh.shareknitest;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class TestVedio extends Activity {



    ImageView Splash_background;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vediotest);







        Splash_background = (ImageView) findViewById(R.id.Splash_background);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Splash_background.setImageResource(R.drawable.splashtwo);

                // Do something after 5s = 5000ms

            }



        }, 5000);





        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

               finish();

                // Do something after 5s = 5000ms

            }
        }, 8000);


    } //  on create


    } //  class





