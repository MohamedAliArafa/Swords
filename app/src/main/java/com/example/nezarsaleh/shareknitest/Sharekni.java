package com.example.nezarsaleh.shareknitest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.nezarsaleh.shareknitest.OnBoardDir.OnboardingActivity;

public class Sharekni extends Activity {
    static Sharekni TestVedio;

    public static Sharekni getInstance(){
        return  TestVedio ;
    }

    ImageView Splash_background;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vediotest);
        TestVedio = this;
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
                Intent in = new Intent(Sharekni.this, OnboardingActivity.class);
                startActivity(in);
//                finish();
                // Do something after 5s = 5000ms

            }
        }, 8000);


    } //  on create


    } //  class





