package rta.ae.sharekni;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import rta.ae.sharekni.Arafa.Classes.GetData;
import rta.ae.sharekni.Arafa.Classes.VolleySingleton;
import rta.ae.sharekni.Arafa.DataModel.BestRouteDataModel;
import rta.ae.sharekni.Arafa.DataModelAdapter.ProfileRideAdapter;

import com.example.nezarsaleh.shareknitest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class History extends AppCompatActivity {


    Toolbar toolbar;
    int Driver_ID;
    String days;
    String url = GetData.DOMAIN +  "Passenger_GetSavedSearch?AccountId=";

    ListView user_ride_created;
    int ID;
    SharedPreferences myPrefs;
    String AccountType;

    String FromEmirateEnName_str,ToEmirateEnName_str,ToRegionEnName_str;


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
                        new AlertDialog.Builder(History.this)
                                .setTitle("Connection Problem!")
                                .setMessage("Make sure you have internet connection")
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentToBeNewRoot = new Intent(History.this, History.class);
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
                        Toast.makeText(History.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url + ID,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                response = response.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                                response = response.replaceAll("<string xmlns=\"http://Sharekni-MobAndroid-Data.org/\">", "");
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

                                            ToEmirateEnName_str=json.getString("ToEmirateEnName");
                                            if (ToEmirateEnName_str.equals("null")){
                                                item.setToEm("Not set");
                                                item.setToReg("Not set");
                                                item.setRouteName(json.getString("FromEmirateEnName"));


                                            }else{
                                                item.setRouteName(json.getString("FromEmirateEnName") + " : " + json.getString("ToEmirateEnName"));
                                                item.setToEm(json.getString("ToEmirateEnName"));
                                                item.setToReg(json.getString("ToRegionEnName"));


                                            }






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

                                            ProfileRideAdapter arrayAdapter = new ProfileRideAdapter(History.this, R.layout.saved_search_list_item, driver);
                                            user_ride_created.setAdapter(arrayAdapter);
                                            user_ride_created.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                                    Intent intent1 = new Intent(getBaseContext(), QuickSearchResults.class);

                                                    intent1.putExtra("From_Em_Id", driver[i].FromEmId);
                                                    intent1.putExtra("To_Em_Id", driver[i].ToEmId);
                                                    intent1.putExtra("From_Reg_Id", driver[i].FromRegid);
                                                    intent1.putExtra("To_Reg_Id", driver[i].ToRegId);
                                                    intent1.putExtra("From_EmirateEnName", driver[i].FromEm);
                                                    intent1.putExtra("From_RegionEnName", driver[i].FromReg);
                                                    intent1.putExtra("To_EmirateEnName", driver[i].ToEm);
                                                    intent1.putExtra("To_RegionEnName", driver[i].ToReg);
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
                VolleySingleton.getInstance(History.this).addToRequestQueue(stringRequest);
            }
            return null;
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText("Saved Search");
//        toolbar.setElevation(10);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
