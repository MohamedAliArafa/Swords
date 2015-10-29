package com.example.nezarsaleh.shareknitest;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.nezarsaleh.shareknitest.Arafa.Classes.GetData;
import com.example.nezarsaleh.shareknitest.OnBoardDir.OnboardingActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

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
        new backThread().execute();
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

    private class backThread extends AsyncTask{
        JSONArray jsonArray;
        JSONArray Emirates;
        GetData j = new GetData();
        FileOutputStream fileOutputStream = null;
        @Override
        protected Object doInBackground(Object[] params) {
            try {
                InputStream emirates = openFileInput("Emirates.txt");
                if (emirates != null){
                    Emirates = j.GetEmitares();
                    fileOutputStream = openFileOutput("Emirates.txt", Sharekni.MODE_PRIVATE);
                    fileOutputStream.write(Emirates.toString().getBytes());
                }
                for (int i = 0;i <= 7;i++){
                    InputStream inputStream = openFileInput("Regions"+i+".txt");
                    if (inputStream != null) {
                        jsonArray = j.GetRegionsByEmiratesID(i);
                        fileOutputStream = openFileOutput("Regions"+i+".txt", Sharekni.MODE_PRIVATE);
                        fileOutputStream.write(jsonArray.toString().getBytes());
                    }
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    } //  class





