package com.example.nezarsaleh.shareknitest.Arafa.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcel;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nezarsaleh.shareknitest.Arafa.Classes.GetData;
import com.example.nezarsaleh.shareknitest.Arafa.DataModel.BestDriverDataModel;
import com.example.nezarsaleh.shareknitest.Arafa.DataModel.BestRouteDataModel;
import com.example.nezarsaleh.shareknitest.Arafa.DataModelAdapter.BestRouteDataModelAdapter;
import com.example.nezarsaleh.shareknitest.DriverEditCarPool;
import com.example.nezarsaleh.shareknitest.DriverGetReviewAdapter;
import com.example.nezarsaleh.shareknitest.DriverGetReviewDataModel;
import com.example.nezarsaleh.shareknitest.HomePage;
import com.example.nezarsaleh.shareknitest.OnBoardDir.OnboardingActivity;
import com.example.nezarsaleh.shareknitest.R;
import com.example.nezarsaleh.shareknitest.Ride_Details_Passengers_Adapter;
import com.example.nezarsaleh.shareknitest.Ride_Details_Passengers_DataModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Route extends AppCompatActivity implements OnMapReadyCallback {

    TextView FromRegionEnName, ToRegionEnName, FromEmirateEnName, ToEmirateEnName, StartFromTime, EndToTime_, AgeRange, PreferredGender, IsSmoking, ride_details_day_of_week, NationalityEnName, PrefLanguageEnName;
    String str_StartFromTime, str_EndToTime_, Smokers_str;
    String days;
    SharedPreferences myPrefs;
    int Route_ID;
    int Passenger_ID;
    int Driver_ID;
    ListView ride_details_passengers;
    double StartLat, StartLng, EndLat, EndLng;
    Activity con;
    ListView Driver_get_Review_lv;
    private Toolbar toolbar;
    private GoogleMap mMap;
    private List<DriverGetReviewDataModel> driverGetReviewDataModels_arr = new ArrayList<>();
    final List<Ride_Details_Passengers_DataModel> Passengers_arr = new ArrayList<>();
    Bundle in;
    String Route_name;
    Button Route_Delete_Btn, Route_Edit_Btn;
    GetData j = new GetData();

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, RadioGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_details_new);

        initToolbar();
        con = this;
        ride_details_passengers = (ListView) findViewById(R.id.ride_details_passengers);

        FromRegionEnName = (TextView) findViewById(R.id.FromRegionEnName);
        ToRegionEnName = (TextView) findViewById(R.id.ToRegionEnName);

        StartFromTime = (TextView) findViewById(R.id.StartFromTime);
        EndToTime_ = (TextView) findViewById(R.id.EndToTime_);

        FromEmirateEnName = (TextView) findViewById(R.id.FromEmirateEnName);
        ToEmirateEnName = (TextView) findViewById(R.id.ToEmirateEnName);

        NationalityEnName = (TextView) findViewById(R.id.NationalityEnName);
        PrefLanguageEnName = (TextView) findViewById(R.id.PrefLanguageEnName);

        AgeRange = (TextView) findViewById(R.id.AgeRange);
        PreferredGender = (TextView) findViewById(R.id.PreferredGender);
        IsSmoking = (TextView) findViewById(R.id.IsSmoking);

        ride_details_day_of_week = (TextView) findViewById(R.id.ride_details_day_of_week);
        Driver_get_Review_lv = (ListView) findViewById(R.id.Driver_get_Review_lv);

        Route_Delete_Btn = (Button) findViewById(R.id.Route_Delete_Btn);
        Route_Edit_Btn = (Button) findViewById(R.id.Route_Edit_Btn);

        myPrefs = this.getSharedPreferences("myPrefs", 0);
        in = getIntent().getExtras();
        Driver_ID = in.getInt("DriverID");
        Route_ID = in.getInt("RouteID");
        Route_name = in.getString("RouteName");

        Log.d("Test Driver id", String.valueOf(Driver_ID));
        Log.d("test Passenger id", String.valueOf(Passenger_ID));
        Log.d("test Route id", String.valueOf(Route_ID));

        new loadingBasicInfo().execute();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_ride_details);
        mapFragment.getMapAsync(this);

//        GetData j = new GetData();
//        j.GetPassengersByRouteIdForm(Route_ID, ride_details_passengers, this);

        new loadingReviews().execute();


        Route_Delete_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String response = j.Driver_DeleteRoute(Route_ID);
                    if (response.equals("\"-1\"") || response.equals("\"-2\'")) {
                        Toast.makeText(Route.this, "Cannot Delete This Route", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent in = new Intent(Route.this, HomePage.class);
                        in.putExtra("RouteID", Route_ID);
                        in.putExtra("DriverID",Driver_ID);
                        in.putExtra("RouteName",Route_name);
                        startActivity(in);
                        Toast.makeText(Route.this, "Route Deleted", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("join ride res", String.valueOf(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


    }  // on create

    private class loadingBasicInfo extends AsyncTask {
        JSONObject json;
        Exception exception;
        boolean exists = false;

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(Route.this);
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
        protected void onPostExecute(Object o) {
            if (exists) {
                if (exception == null) {
                    try {
                        FromRegionEnName.setText(json.getString("FromRegionEnName"));
                        ToRegionEnName.setText(json.getString("ToRegionEnName"));
                        FromEmirateEnName.setText(json.getString("FromEmirateEnName"));
                        ToEmirateEnName.setText(json.getString("ToEmirateEnName"));
                        str_StartFromTime = json.getString("StartFromTime");
                        str_EndToTime_ = json.getString("EndToTime_");
                        str_StartFromTime = str_StartFromTime.substring(Math.max(0, str_StartFromTime.length() - 7));
                        Log.d("string", str_StartFromTime);
                        str_EndToTime_ = str_EndToTime_.substring(Math.max(0, str_EndToTime_.length() - 7));
                        Log.d("time to", str_EndToTime_);
                        StartFromTime.setText(str_StartFromTime);
                        EndToTime_.setText(str_EndToTime_);
                        if (json.getString("NationalityEnName").equals("null")) {
                            NationalityEnName.setText("Not Specified");
                        } else {
                            NationalityEnName.setText(json.getString("NationalityEnName"));
                        }
                        PrefLanguageEnName.setText(json.getString("PrefLanguageEnName"));
                        AgeRange.setText(json.getString("AgeRange"));
                        PreferredGender.setText(json.getString("PreferredGender"));
                        Smokers_str = "";
                        //Done
                        Smokers_str = json.getString("IsSmoking");
                        if (Smokers_str.equals("true")) {
                            Smokers_str = "Yes";
                        } else if (Smokers_str.equals("false")) {
                            Smokers_str = "No";
                        }
                        IsSmoking.setText(Smokers_str);
                        StartLat = json.getDouble("StartLat");
                        StartLng = json.getDouble("StartLng");
                        EndLat = json.getDouble("EndLat");
                        EndLng = json.getDouble("EndLng");
                        Log.d("S Lat", String.valueOf(StartLat));
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
                        ride_details_day_of_week.setText(days);
                        days = "";
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Dialog dialog = new Dialog(Route.this);
                    dialog.setTitle("Some Thing Is Wrong !?");
                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            Intent in = new Intent(Route.this, HomePage.class);
                            startActivity(in);
                        }
                    });
                }

                Route_Edit_Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(Route.this, DriverEditCarPool.class);
                        Bundle b = new Bundle();
                        in.putExtra("Data", b);
                        in.putExtra("RouteID", Route_ID);
                        try {
                            in.putExtra("RouteName", json.getString("RouteEnName"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(in);
                    }
                });

            }
            hidePDialog();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            GetData GD = new GetData();
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
                        new AlertDialog.Builder(Route.this)
                                .setTitle("Connection Problem!")
                                .setMessage("Make sure you have internet connection")
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentToBeNewRoot = new Intent(Route.this, Route.class);
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
                        Toast.makeText(Route.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                try {
                    days = "";
                    json = GD.GetRouteById(Route_ID);
                } catch (Exception e) {
                    exception = e;
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    private class loadingReviews extends AsyncTask {
        JSONArray response1 = null;
        JSONArray response2 = null;

        @Override
        protected void onPostExecute(Object o) {
            for (int i = 0; i < response1.length(); i++) {
                try {
                    JSONObject obj = response1.getJSONObject(i);
                    final DriverGetReviewDataModel review = new DriverGetReviewDataModel(Parcel.obtain());
                    review.setAccountName(obj.getString("AccountName"));
                    review.setAccountNationalityEn(obj.getString("AccountNationalityEn"));
                    review.setReview(obj.getString("Review"));
                    driverGetReviewDataModels_arr.add(review);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


                for (int y = 0; y < response2.length(); y++) {

                    try {
                        JSONObject obj = response2.getJSONObject(y);
                        final Ride_Details_Passengers_DataModel item = new Ride_Details_Passengers_DataModel(Parcel.obtain());
                        Log.d("test account email", obj.getString("AccountName"));
                        item.setAccountName(obj.getString("AccountName"));
                        item.setAccountNationalityEn(obj.getString("AccountNationalityEn"));
                        Passengers_arr.add(item);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            DriverGetReviewAdapter arrayAdapter = new DriverGetReviewAdapter(con, driverGetReviewDataModels_arr);
            Driver_get_Review_lv.setAdapter(arrayAdapter);
            setListViewHeightBasedOnChildren(Driver_get_Review_lv);

            Ride_Details_Passengers_Adapter adapter = new Ride_Details_Passengers_Adapter(con, Passengers_arr);
            ride_details_passengers.setAdapter(adapter);
            setListViewHeightBasedOnChildren(ride_details_passengers);

        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                response1 = new GetData().Driver_GetReview(Driver_ID, Route_ID);
                response2 = new GetData().GetPassengers_ByRouteID(Route_ID);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_route, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.Join_Ride) {
//                GetData j = new GetData();
//                try {
//                    String response  = j.Passenger_SendAlert(Driver_ID, Passenger_ID, Route_ID, "TestCase2");
//                    if (response.equals("-1")&&response.equals("-2")){
//                        Toast.makeText(Route.this, "Cannot Join This Route", Toast.LENGTH_SHORT).show();
//                    }else {
//                        Toast.makeText(Route.this, "successfully  Joined", Toast.LENGTH_SHORT).show();
//                    }
//                    Log.d("join ride res",response.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom
                (new LatLng(StartLat, EndLng), 8.1f));
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.setMyLocationEnabled(true);

        // Instantiates a new Polyline object and adds points to define a rectangle
        PolylineOptions rectOptions = new PolylineOptions()
                .add(new LatLng(StartLat, StartLng))
                .add(new LatLng(EndLat, EndLng))
                .color(R.color.primaryColor)
                .width(6);  // North of the previous point, but at the same longitude
        // Closes the polyline.


// Get back the mutable Polyline
        Polyline polyline = mMap.addPolyline(rectOptions);

        final Marker markerZero = mMap.addMarker(new MarkerOptions().
                position(new LatLng(StartLat, StartLng))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.anchor)));

        final Marker markerZero2 = mMap.addMarker(new MarkerOptions().
                position(new LatLng(EndLat, EndLng))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.anchor)));


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText("Ride Details");
//        toolbar.setElevation(10);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
