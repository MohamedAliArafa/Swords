package com.example.nezarsaleh.shareknitest.Arafa.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import com.example.nezarsaleh.shareknitest.Arafa.Classes.AppController;
import com.example.nezarsaleh.shareknitest.Arafa.Classes.CircularNetworkImageView;
import com.example.nezarsaleh.shareknitest.Arafa.Classes.GetData;
import com.example.nezarsaleh.shareknitest.Arafa.Classes.VolleySingleton;
import com.example.nezarsaleh.shareknitest.Arafa.DataModel.BestDriverDataModel;
import com.example.nezarsaleh.shareknitest.Arafa.DataModel.BestRouteDataModel;
import com.example.nezarsaleh.shareknitest.Arafa.DataModelAdapter.BestDriverDataModelAdapter;
import com.example.nezarsaleh.shareknitest.Arafa.DataModelAdapter.ProfileRideAdapter;
import com.example.nezarsaleh.shareknitest.LoginApproved;
import com.example.nezarsaleh.shareknitest.OnBoardDir.OnboardingActivity;
import com.example.nezarsaleh.shareknitest.R;
import com.example.nezarsaleh.shareknitest.RideDetailsPassenger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {

    TextView TopName, NationalityEnName;
    ImageView profile_msg,profile_call;
    ListView lv_driver;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    String URL_Photo = GetData.PhotoURL;
    String days;
    SharedPreferences myPrefs;
    int Passenger_ID;
    int Driver_ID;
    String AccountType;
    String ID;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (LoginApproved.getInstance() != null) {
                LoginApproved.getInstance().finish();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.driver_details);
        initToolbar();

        if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();
        CircularNetworkImageView Photo = (CircularNetworkImageView) findViewById(R.id.profilephoto);

        lv_driver = (ListView) findViewById(R.id.user_rides);
        TopName = (TextView) findViewById(R.id.TopName);
        NationalityEnName = (TextView) findViewById(R.id.NationalityEnName);
        profile_msg = (ImageView) findViewById(R.id.profile_msg);
        profile_call = (ImageView) findViewById(R.id.profile_call);

        myPrefs = this.getSharedPreferences("myPrefs", 0);
        Passenger_ID = Integer.parseInt(myPrefs.getString("account_id", "0"));
        AccountType = myPrefs.getString("account_type", null);

        GetData j = new GetData();
        Bundle in = getIntent().getExtras();
        Driver_ID = in.getInt("DriverID");
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading" + "...");
        pDialog.show();

        new jsoning(lv_driver,pDialog,this).execute();

        try {
            final JSONObject json = j.GetDriverById(Driver_ID);
            TopName.setText(json.getString("FirstName") + " " + json.getString("MiddleName"));
            NationalityEnName.setText(json.getString("NationalityEnName"));
            Photo.setImageUrl(URL_Photo + json.getString("PhotoPath"), imageLoader);

            profile_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    try {
                        intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + json.getString("Mobile")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);
                }
            });

            profile_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    try {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + json.getString("Mobile")));
                        intent.putExtra("sms_body", "Hello " + json.getString("FirstName"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);
                }
            });
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText("Driver Details");


//        toolbar.setElevation(10);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public class jsoning extends AsyncTask {

        ListView lv;
        Activity con;
        BestRouteDataModel[] driver;
        private ProgressDialog pDialog;
        private List<BestDriverDataModel> arr = new ArrayList<>();
        boolean exists = false;

        public jsoning(final ListView lv, ProgressDialog pDialog, final Activity con) {

            this.lv = lv;
            this.con = con;
            this.pDialog = pDialog;

        }

        @Override
        protected void onPostExecute(Object o) {
            if (exists){
                ProfileRideAdapter arrayAdapter = new ProfileRideAdapter(Profile.this, R.layout.driver_profile_rides, driver);
                lv.setAdapter(arrayAdapter);
                lv.requestLayout();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        assert AccountType != null;
                        if (Passenger_ID != 0) {
                            if (Driver_ID != Passenger_ID) {
                                Intent in = new Intent(Profile.this, RideDetailsPassenger.class);
                                in.putExtra("RouteID", driver[i].getID());
                                in.putExtra("PassengerID", Passenger_ID);
                                Log.d("Last 4", String.valueOf(Passenger_ID));
                                in.putExtra("DriverID", Driver_ID);
                                Log.d("inside intent", String.valueOf(Passenger_ID));
                                Profile.this.startActivity(in);
                            } else {
                                Intent in = new Intent(Profile.this, Route.class);
                                in.putExtra("RouteID", driver[i].getID());
                                in.putExtra("RouteName",driver[i].getRouteName());
                                in.putExtra("PassengerID", Passenger_ID);
                                Log.d("Last 3", String.valueOf(Passenger_ID));
                                in.putExtra("DriverID", Driver_ID);
                                Profile.this.startActivity(in);
                            }
                        } else {
                            Intent in = new Intent(Profile.this, RideDetailsPassenger.class);
                            in.putExtra("RouteID", driver[i].getID());
                            in.putExtra("PassengerID", Passenger_ID);
                            Log.d("Last 2", String.valueOf(Passenger_ID));
                            in.putExtra("DriverID", Driver_ID);
                            Profile.this.startActivity(in);
                        }
                    }
                });
            }
            hidePDialog();
        }

        private void hidePDialog() {
            if (pDialog != null) {
                pDialog.dismiss();
                pDialog = null;
            }
        }

        @Override
        protected Object doInBackground(Object[] params) {
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
                        new AlertDialog.Builder(Profile.this)
                                .setTitle("Connection Problem!")
                                .setMessage("Make sure you have internet connection")
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentToBeNewRoot = new Intent(Profile.this, Profile.class);
                                        ComponentName cn = intentToBeNewRoot.getComponent();
                                        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                                        startActivity(mainIntent);
                                    }
                                })
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();
                        Toast.makeText(Profile.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                JSONArray response = null;
                try {
                    response = new GetData().GetDriverRides(Driver_ID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                driver = new BestRouteDataModel[response.length()];
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject json = response.getJSONObject(i);
                        BestRouteDataModel item = new BestRouteDataModel(Parcel.obtain());
                        days = "";
                        item.setID(json.getInt("ID"));
                        item.setFromEm(json.getString("FromEmirateEnName"));
                        item.setFromReg(json.getString("FromRegionEnName"));
                        item.setToEm(json.getString("ToEmirateEnName"));
                        item.setToReg(json.getString("ToRegionEnName"));
                        item.setRouteName(json.getString("RouteEnName"));
                        item.setStartFromTime(json.getString("StartFromTime"));
                        item.setEndToTime_(json.getString("EndToTime_"));
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

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }

}