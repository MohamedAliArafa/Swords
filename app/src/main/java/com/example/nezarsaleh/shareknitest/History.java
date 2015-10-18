package com.example.nezarsaleh.shareknitest;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nezarsaleh.shareknitest.Arafa.Activities.Route;
import com.example.nezarsaleh.shareknitest.Arafa.Classes.VolleySingleton;
import com.example.nezarsaleh.shareknitest.Arafa.DataModel.BestRouteDataModel;
import com.example.nezarsaleh.shareknitest.Arafa.DataModelAdapter.ProfileRideAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class History extends AppCompatActivity {

    Toolbar toolbar;
    int Driver_ID;
    String days;
    String url = "http://sharekni-web.sdg.ae/_mobfiles/CLS_MobRoute.asmx/Passenger_GetSavedSearch?AccountId=";

    ListView user_ride_created;
    int ID;
    SharedPreferences myPrefs;
    String AccountType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        user_ride_created = (ListView) findViewById(R.id.user_ride_created);
        initToolbar();

        myPrefs = this.getSharedPreferences("myPrefs", 0);
        ID = Integer.parseInt(myPrefs.getString("account_id", "0"));
        AccountType = myPrefs.getString("account_type", null);
        Log.d("Account type his",AccountType);
        Log.d("id history", String.valueOf(ID));




        new rideJson().execute();
    }


    private class rideJson extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            RequestQueue queue = VolleySingleton.getInstance(getBaseContext().getApplicationContext()).getRequestQueue();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url + ID,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            response = response.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                            response = response.replaceAll("<string xmlns=\"http://tempuri.org/\">", "");
                            response = response.replaceAll("</string>", "");
                            // Display the first 500 characters of the response string.
                            String data = response.substring(40);
                            Log.d("url", url + ID);
                            try {
                                JSONArray jArray = new JSONArray(data);
                                final BestRouteDataModel[] driver = new BestRouteDataModel[jArray.length()];
                                JSONObject json;
                                for (int i = 0; i < jArray.length(); i++) {
                                    try {
                                        BestRouteDataModel item = new BestRouteDataModel(Parcel.obtain());
                                        days = "";
                                        json = jArray.getJSONObject(i);
                                        item.setID(json.getInt("RouteId"));
                                        item.setFromEm(json.getString("FromEmirateEnName"));
                                        item.setFromReg(json.getString("FromRegionEnName"));
                                        item.setToEm(json.getString("ToEmirateEnName"));
                                        item.setToReg(json.getString("ToRegionEnName"));
                                        item.setRouteName(json.getString("FromEmirateEnName") + ":" + json.getString("ToEmirateEnName"));
                                        item.setStartFromTime("00:00");
                                        item.setEndToTime_("00:00");
                                        item.setFromEmId(json.getInt("FromEmirateId"));
                                        item.setToEmId(json.getInt("ToEmirateId"));
                                        item.setFromRegid(json.getInt("FromRegionId"));
                                        item.setToRegId(json.getInt("ToRegionId"));





                                        if (json.getString("Saturday").equals("true")) {
                                            days += "Sat , ";
                                        }
                                        if (json.getString("Sunday").equals("true")) {
                                            days += "Sun , ";

                                        }
                                        if (json.getString("Monday").equals("true")) {
                                            days += "Mon , ";

                                        }
                                        if (json.getString("Tuesday").equals("true")) {
                                            days += "Tue , ";
                                        }
                                        if (json.getString("Wednesday").equals("true")) {
                                            days += "Wed , ";
                                        }
                                        if (json.getString("Thursday").equals("true")) {
                                            days += "Thu , ";

                                        }
                                        if (json.getString("Friday").equals("true")) {
                                            days += "Fri ";
                                        }


                                        item.setDriver_profile_dayWeek(days);
                                        days = "";

                                        driver[i] = item;
                                        Log.d("ID", String.valueOf(json.getInt("ID")));
                                        Log.d("FromEmlv", json.getString("FromEmirateEnName"));
                                        Log.d("FromReglv", json.getString("FromRegionEnName"));
                                        Log.d("TomEmlv", json.getString("ToEmirateEnName"));
                                        Log.d("ToReglv", json.getString("ToRegionEnName"));

                                        ProfileRideAdapter arrayAdapter = new ProfileRideAdapter(History.this, R.layout.driver_created_rides_lits_item, driver);
                                        user_ride_created.setAdapter(arrayAdapter);
                                        user_ride_created.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                                Intent intent1 = new Intent(getBaseContext(), QuickSearchResults.class);

                                                intent1.putExtra("From_Em_Id", driver[i].FromEmId);
                                                intent1.putExtra("To_Em_Id", driver[i].ToEmId);
                                                intent1.putExtra("From_Reg_Id", driver[i].FromRegid);
                                                intent1.putExtra("To_Reg_Id", driver[i].ToRegId);
                                                intent1.putExtra("From_EmirateEnName",driver[i].FromEm);
                                                intent1.putExtra("From_RegionEnName",driver[i].FromReg);
                                                intent1.putExtra("To_EmirateEnName",driver[i].ToEm);
                                                intent1.putExtra("To_RegionEnName",driver[i].ToReg);
                                                startActivity(intent1);


                                            }
                                        });
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
            queue.add(stringRequest);
            VolleySingleton.getInstance(History.this).addToRequestQueue(stringRequest);
            return null;
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText("History");
//        toolbar.setElevation(10);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
