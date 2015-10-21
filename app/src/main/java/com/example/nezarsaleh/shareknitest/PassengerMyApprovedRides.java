package com.example.nezarsaleh.shareknitest;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nezarsaleh.shareknitest.Arafa.Classes.GetData;
import com.example.nezarsaleh.shareknitest.Arafa.Classes.VolleySingleton;
import com.example.nezarsaleh.shareknitest.Arafa.DataModel.BestRouteDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class PassengerMyApprovedRides extends AppCompatActivity {


    private static final String DOMAIN = "http://sharekni.sdgstaff.com";

    String url =  DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/Passenger_MyApprovedRides?AccountId=";


    ListView Passenger_Approved_Rides_Lv;
    SharedPreferences myPrefs;
    int Passenger_ID;

    GetData j= new GetData();

    String days;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_my_approved_rides);
        initToolbar();
        Passenger_Approved_Rides_Lv= (ListView) findViewById(R.id.Passenger_Approved_Rides_Lv);

        myPrefs = this.getSharedPreferences("myPrefs", 0);
        String ID = myPrefs.getString("account_id",null);

        Passenger_ID = Integer.parseInt(ID);
        Log.d("Driverid1", String.valueOf(Passenger_ID));



        new rideJson().execute();



    }





    private class rideJson extends AsyncTask {
        ProgressDialog pDialog;

        @Override
        protected void onPostExecute(Object o) {
            hidePDialog();
            super.onPostExecute(o);
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(PassengerMyApprovedRides.this);
            pDialog.setMessage("Loading" + "...");
            pDialog.show();
            super.onPreExecute();
        }

        private void hidePDialog() {
            if (pDialog != null) {
                pDialog.dismiss();
                pDialog = null;
            }
        }

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
                        new AlertDialog.Builder(PassengerMyApprovedRides.this)
                                .setTitle("Connection Problem!")
                                .setMessage("Make sure you have internet connection")
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentToBeNewRoot = new Intent(PassengerMyApprovedRides.this, PassengerMyApprovedRides.class);
                                        ComponentName cn = intentToBeNewRoot.getComponent();
                                        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                                        startActivity(mainIntent);
                                    }
                                })
                                .setNegativeButton("Exit!", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();
                        Toast.makeText(PassengerMyApprovedRides.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                final GetData GD = new GetData();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url + Passenger_ID,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                response = response.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                                response = response.replaceAll("<string xmlns=\"http://tempuri.org/\">", "");
                                response = response.replaceAll("</string>", "");
                                // Display the first 500 characters of the response string.
                                String data = response.substring(40);
                                Log.d("url", url + Passenger_ID);
                                try {
                                    JSONArray jArray = new JSONArray(data);
                                    final BestRouteDataModel[] passenger = new BestRouteDataModel[jArray.length()];
                                    JSONObject json;
                                    for (int i = 0; i < jArray.length(); i++) {
                                        try {
                                            BestRouteDataModel item = new BestRouteDataModel(Parcel.obtain());
                                            days = "";
                                            json = jArray.getJSONObject(i);
                                            int Route_ID = (json.getInt("RouteID"));
                                            int Driver_Account = (json.getInt("Account"));
                                            String Route_Name = (json.getString("Name_en"));

                                            Log.d("Route id", String.valueOf(Route_ID));
                                            Log.d("Driver_account", String.valueOf(Driver_Account));
                                            Log.d("Route Name", Route_Name);

                                            JSONObject jsonObject = GD.GetRouteById(Route_ID);
                                            String Routename2 = jsonObject.getString("RouteEnName");
                                            Log.d("Route name 2 ", Routename2);

                                            item.setFromEm(jsonObject.getString("FromEmirateEnName"));
                                            item.setFromReg(jsonObject.getString("FromRegionEnName"));
                                            item.setToEm(jsonObject.getString("ToEmirateEnName"));
                                            item.setToReg(jsonObject.getString("ToRegionEnName"));
                                            item.setRouteName(jsonObject.getString("RouteEnName"));
                                            item.setStartFromTime(jsonObject.getString("StartFromTime"));
                                            item.setEndToTime_(jsonObject.getString("EndToTime_"));
                                            item.setDriver_ID(Driver_Account);

                                            passenger[i] = item;

                                            PassngerApprovedRidesAdapter arrayAdapter = new PassngerApprovedRidesAdapter(PassengerMyApprovedRides.this, R.layout.passenger_approved_rides, passenger);
                                            Passenger_Approved_Rides_Lv.setAdapter(arrayAdapter);


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error : ", error.toString());
                        //Ride.setText("That didn't work! : " + error.toString());
                    }
                });
                VolleySingleton.getInstance(PassengerMyApprovedRides.this).addToRequestQueue(stringRequest);
            }
            return null;
        }  //  do in background


    } //  Ride Json






    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText("My Approved Rides");
//        toolbar.setElevation(10);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
