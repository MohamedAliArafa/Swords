package rta.ae.sharekni;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.mobileapptracker.MobileAppTracker;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import rta.ae.sharekni.Arafa.Classes.GetData;
import rta.ae.sharekni.OnBoardDir.OnboardingActivity;

public class Sharekni extends Activity {
    static Sharekni TestVedio;

    public static Sharekni getInstance() {
        return TestVedio;
    }

    public static MobileAppTracker mobileAppTracker = null;

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


        mobileAppTracker = MobileAppTracker.init(getApplicationContext(),
                "189698",
                "172510cf81e7148e5a01851f65fb0c7e");
       // mobileAppTracker.setDebugMode(true);
       // mobileAppTracker.setAllowDuplicates(true);


    } //  on create

    private class backThread extends AsyncTask {
        JSONArray jsonArray;
        JSONArray Emirates;
        JSONArray Countries;
        JSONArray Nationalises;
        GetData j = new GetData();
        FileOutputStream fileOutputStream = null;
        File file = null;

        @Override
        protected Object doInBackground(Object[] params) {
            file = getFilesDir();
            try {
                InputStream emirates = openFileInput("Emirates.txt");
            } catch (FileNotFoundException e) {
                try {
                    Emirates = j.GetEmitares();
                    fileOutputStream = openFileOutput("Emirates.txt", Sharekni.MODE_PRIVATE);
                    fileOutputStream.write(Emirates.toString().getBytes());
                } catch (JSONException | IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
            try {
                InputStream Countries = openFileInput("Countries.txt");
            } catch (FileNotFoundException e) {
                try {
                    Countries = j.GetNationalities();
                    fileOutputStream = openFileOutput("Countries.txt", Sharekni.MODE_PRIVATE);
                    fileOutputStream.write(Countries.toString().getBytes());
                } catch (JSONException | IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }

            try {
                InputStream Nationalises = openFileInput("Nationalises.txt");
            } catch (FileNotFoundException e) {
                try {
                    Nationalises = j.GetNationalities();
                    fileOutputStream = openFileOutput("Nationalises.txt", Sharekni.MODE_PRIVATE);
                    fileOutputStream.write(Nationalises.toString().getBytes());
                } catch (JSONException | IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }


            for (int i = 0; i <= 7; i++) {
                try {
                    InputStream inputStream = openFileInput("Regions" + i + ".txt");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    try {
                        jsonArray = j.GetRegionsByEmiratesID(i);
                        fileOutputStream = openFileOutput("Regions" + i + ".txt", Sharekni.MODE_PRIVATE);
                        fileOutputStream.write(jsonArray.toString().getBytes());
                    } catch (JSONException | IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            return null;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Get source of open for app re-engagement
        mobileAppTracker.setReferralSources(this);
        // MAT will not function unless the measureSession call is included
        mobileAppTracker.measureSession();
    }
} //  class





