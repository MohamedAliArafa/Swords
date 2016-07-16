package rta.ae.sharekni;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Locale;

import rta.ae.sharekni.Arafa.Classes.GetData;
import rta.ae.sharekni.Arafa.Classes.VolleySingleton;
import rta.ae.sharekni.Arafa.DataModel.BestRouteDataModel;

public class PassengerMyApprovedRides extends AppCompatActivity {

    String url = GetData.DOMAIN + "Passenger_MyApprovedRides?AccountId=";
    ListView Passenger_Approved_Rides_Lv;
    SharedPreferences myPrefs;
    int Passenger_ID;

    GetData j = new GetData();

    String days;
    Toolbar toolbar;
    Activity c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_my_approved_rides);
        initToolbar();
        Passenger_Approved_Rides_Lv = (ListView) findViewById(R.id.Passenger_Approved_Rides_Lv);

        myPrefs = this.getSharedPreferences("myPrefs", 0);
        String ID = myPrefs.getString("account_id", null);

        Passenger_ID = Integer.parseInt(ID);
        Log.d("Driverid1", String.valueOf(Passenger_ID));

        c = this;

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
            pDialog.setMessage(getString(R.string.loading) + "...");
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
                                .setTitle(getString(R.string.connection_problem))
                                .setMessage(getString(R.string.con_problem_message))
                                .setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        startActivity(getIntent());
                                    }
                                })
                                .setNegativeButton(getString(R.string.goBack), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();
                        Toast.makeText(PassengerMyApprovedRides.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
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
                                response = response.replaceAll("<string xmlns=\"http://Sharekni-MobAndroid-Data.org/\">", "");
                                response = response.replaceAll("</string>", "");
                                // Display the first 500 characters of the response string.
                                String data = response.substring(40);
                                Log.d("url", url + Passenger_ID);
                                try {
                                    JSONArray jArray = new JSONArray(data);
                                    final BestRouteDataModel[] passenger = new BestRouteDataModel[jArray.length()];
                                    JSONObject json;


                                    if (jArray.length() == 0) {
                                        Log.d("Error 3 ", "Error3");

                                        final Dialog dialog = new Dialog(c);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setContentView(R.layout.noroutesdialog);
                                        Button btn = (Button) dialog.findViewById(R.id.noroute_id);
                                        TextView Text_3 = (TextView) dialog.findViewById(R.id.Text_3);
                                        dialog.show();
                                        Text_3.setText(R.string.no_rides_joined);

                                        btn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                                c.finish();
                                            }
                                        });

                                    }


                                    for (int i = 0; i < jArray.length(); i++) {
                                        try {
                                            BestRouteDataModel item = new BestRouteDataModel(Parcel.obtain());
                                            days = "";
                                            json = jArray.getJSONObject(i);
                                            int Route_ID = (json.getInt("RouteID"));
                                            int Driver_Account = (json.getInt("Account"));

                                            String Route_Name = (json.getString("Name_en"));
                                            item.setRoutePassengerId(json.getInt("RoutePassengerId"));
                                            item.setRoute_id(Route_ID);
                                            item.setPassenger_ID(Passenger_ID);
                                            Log.d("Route id", String.valueOf(Route_ID));
                                            Log.d("Driver_account", String.valueOf(Driver_Account));
                                            Log.d("Route Name", Route_Name);

                                            JSONObject jsonObject = GD.GetRouteById(Route_ID);
                                            String Routename2 = jsonObject.getString("RouteEnName");
                                            Log.d("Route name 2 ", Routename2);

                                            item.setFromEm(jsonObject.getString(getString(R.string.from_em_en_name)));
                                            item.setFromReg(jsonObject.getString(getString(R.string.from_reg_en_name)));
                                            item.setToEm(jsonObject.getString(getString(R.string.to_em_en_name)));
                                            item.setToReg(jsonObject.getString(getString(R.string.to_reg_en_name)));
                                            item.setRouteName(jsonObject.getString(getString(R.string.route_name)));
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
        textView.setText(getString(R.string.ride_joined));
//        toolbar.setElevation(10);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            Locale locale = Locale.getDefault();
            String Locale_Str2 = locale.toString();
            if (Locale_Str2.contains("en")) {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_arrow_back);
            } else {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_arrow_forward);
            }
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


}
