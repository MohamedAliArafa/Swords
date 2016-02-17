package rta.ae.sharekni;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mobileapptracker.MobileAppTracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLEncoder;

import rta.ae.sharekni.Arafa.Classes.GetData;
import rta.ae.sharekni.Arafa.Classes.VolleySingleton;
import rta.ae.sharekni.Arafa.DataModel.BestDriverDataModel;
import rta.ae.sharekni.OnBoardDir.OnboardingActivity;

public class Sharekni extends Activity {
    static Sharekni TestVedio;
    protected static ProgressDialog pDialog;
    String url = GetData.DOMAIN +"CheckLogin?";
    String user,pass;

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
        SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String ID = myPrefs.getString("account_id", null);
        user = myPrefs.getString("account_user", null);
        pass = myPrefs.getString("account_pass", null);

        if (ID != null && user != null && pass != null){
            Log.w("user",user);
            Log.w("Pass",pass);
            new loginProcces().execute();
        }
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

    private class loginProcces extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            boolean exists = false;
            try {
                SocketAddress sockaddr = new InetSocketAddress("www.google.com", 80);
                Socket sock = new Socket();
                int timeoutMs = 2000;   // 2 seconds
                sock.connect(sockaddr, timeoutMs);
                exists = true;
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(Sharekni.this)
                                .setTitle(R.string.connection_problem)
                                .setMessage(R.string.con_problem_message)
                                .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        startActivity(getIntent());
//                                        Intent intentToBeNewRoot = new Intent(LoginApproved.this, LoginApproved.class);
//                                        ComponentName cn = intentToBeNewRoot.getComponent();
//                                        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
//                                        startActivity(mainIntent);
                                    }
                                })
                                .setNegativeButton(R.string.goBack, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
//                                        Intent intentToBeNewRoot = new Intent(LoginApproved.this, OnboardingActivity.class);
//                                        ComponentName cn = intentToBeNewRoot.getComponent();
//                                        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
//                                        startActivity(mainIntent);
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();
                        Toast.makeText(Sharekni.this, R.string.connection_problem, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "username=" + URLEncoder.encode(user) + "&password=" + URLEncoder.encode(pass),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Login","Last Seen Updated");
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w("Login","Last Seen Failed");
                    }
                });
                VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
            }
            return null;
        }
    }

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





