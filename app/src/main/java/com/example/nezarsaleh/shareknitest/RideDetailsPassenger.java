package com.example.nezarsaleh.shareknitest;

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
import android.os.Parcel;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nezarsaleh.shareknitest.Arafa.Classes.GetData;
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

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class RideDetailsPassenger extends AppCompatActivity implements OnMapReadyCallback {


    TextView
            FromRegionEnName, ToRegionEnName, FromEmirateEnName, ToEmirateEnName, StartFromTime, EndToTime_, AgeRange, PreferredGender, IsSmoking, ride_details_day_of_week, NationalityEnName, PrefLanguageEnName;


    String Gender_ste, Nat_txt, Smokers_str;


    String str_StartFromTime, str_EndToTime_,str_PrefLanguageEnName;


    String days;
    String Str_AgeRange;

    final JSONArray[] myJsonArray = new JSONArray[1];
    private Toolbar toolbar;


    int Route_ID;
    int Passenger_ID;
    int Driver_ID;
    Button Join_Ride_btn;
    ListView ride_details_passengers;

    private GoogleMap mMap;

    double StartLat, StartLng, EndLat, EndLng;

    Activity con;

    private List<DriverGetReviewDataModel> driverGetReviewDataModels_arr = new ArrayList<>();

    ListView Driver_get_Review_lv;
    int Pass_id;
    boolean exists;

    Button Passenger_Review_Driver_Btn;

    SharedPreferences myPrefs;

    String Review_str;
    EditText Edit_Review_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_details_passenger);

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
        Join_Ride_btn = (Button) findViewById(R.id.Join_Ride_btn);
        ride_details_day_of_week = (TextView) findViewById(R.id.ride_details_day_of_week);
        Driver_get_Review_lv = (ListView) findViewById(R.id.Driver_get_Review_lv);
        Passenger_Review_Driver_Btn = (Button) findViewById(R.id.Passenger_Review_Driver_Btn);


        // setListViewHeightBasedOnChildren(Driver_get_Review_lv);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        myPrefs = this.getSharedPreferences("myPrefs", 0);
        Passenger_ID = Integer.parseInt(myPrefs.getString("account_id", "0"));

        Bundle in = getIntent().getExtras();
        Driver_ID = in.getInt("DriverID");
        Route_ID = in.getInt("RouteID");

        Log.d("Test Driver id", String.valueOf(Driver_ID));
        Log.d("test Route id", String.valueOf(Route_ID));
        Log.d("test Passenger id 2", String.valueOf(Passenger_ID));

        exists = false;
        new back().execute();



    }  //  on create


    private class back extends AsyncTask{

        JSONObject json;
        private ProgressDialog pDialog;

        private void hidePDialog() {
            if (pDialog != null) {
                pDialog.dismiss();
                pDialog = null;
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(RideDetailsPassenger.this);
            pDialog.setMessage("Loading" + "...");
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            if (exists) {
                GetData GD = new GetData();
                try {
                    json = GD.GetRouteById(Route_ID);
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
                    Nat_txt = (json.getString("NationalityEnName"));
                    if (Nat_txt.equals("null")) {
                        Nat_txt = "Not Specified";
                        NationalityEnName.setText(Nat_txt);
                    } else {
                        NationalityEnName.setText(Nat_txt);
                    }
                    PrefLanguageEnName.setText(json.getString("PrefLanguageEnName"));

                    Str_AgeRange=json.getString("AgeRange");
                    if (Str_AgeRange.equals("null")){
                        Str_AgeRange="Not Specified";
                        AgeRange.setText(Str_AgeRange);


                    }else {

                        AgeRange.setText(Str_AgeRange);

                    }



                    Gender_ste = "";
                    Gender_ste = json.getString("PreferredGender");
                    switch (Gender_ste) {
                        case "M":
                            Gender_ste = "Male";
                            PreferredGender.setText(Gender_ste);
                            break;
                        case "F":
                            Gender_ste = "Female";
                            PreferredGender.setText(Gender_ste);
                            break;
                        case "null":
                            Gender_ste = "Not Specified";
                            PreferredGender.setText(Gender_ste);
                        default:
                            Gender_ste = "Not Specified";
                            PreferredGender.setText(Gender_ste);
                            break;
                    }

                    Smokers_str = "";
                    Smokers_str = json.getString("IsSmoking");
                    if (Smokers_str.equals("true")) {
                        Smokers_str = "Yes";
                        IsSmoking.setText(Smokers_str);
                    } else if (Smokers_str.equals("false")) {
                        Smokers_str = "No";
                        IsSmoking.setText(Smokers_str);
                    }

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


                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map_ride_details);
                mapFragment.getMapAsync(RideDetailsPassenger.this);

//                Join_Ride_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        GetData j = new GetData();
//                        if (Passenger_ID == 0) {
//                            final Dialog dialog = new Dialog(RideDetailsPassenger.this);
//                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                            dialog.setContentView(R.layout.noroutesdialog);
//                            Button btn = (Button) dialog.findViewById(R.id.noroute_id);
//                            TextView Text_3 = (TextView) dialog.findViewById(R.id.Text_3);
//                            Text_3.setText("PLease Login First");
//                            btn.setText("Log In");
//                            dialog.show();
//
//                            btn.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.dismiss();
//                                    Intent intent = new Intent(RideDetailsPassenger.this, LoginApproved.class);
//                                    RideDetailsPassenger.this.startActivity(intent);
//
//                                }
//                            });
//                        } else {
//                            try {
//                                String response = j.Passenger_SendAlert(Driver_ID, Passenger_ID, Route_ID, "TestCase2");
//                                if (response.equals("\"-1\"") || response.equals("\"-2\'")) {
//                                    Toast.makeText(RideDetailsPassenger.this, "You Already Joined", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(RideDetailsPassenger.this, "successfully  Joined", Toast.LENGTH_SHORT).show();
//                                }
//                                Log.d("join ride res", String.valueOf(response));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });

                JSONArray response1 = null;
                try {
                    response1 = new GetData().Driver_GetReview(Driver_ID, Route_ID);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                assert response1 != null;
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

                DriverGetReviewAdapter arrayAdapter = new DriverGetReviewAdapter(con, driverGetReviewDataModels_arr);
                Driver_get_Review_lv.setAdapter(arrayAdapter);
                setListViewHeightBasedOnChildren(Driver_get_Review_lv);


                Passenger_Review_Driver_Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final GetData j = new GetData();

                        Review_str = "";
                        final Dialog dialog = new Dialog(con);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.review_dialog);
                        Button btn = (Button) dialog.findViewById(R.id.Review_Btn);
                        Edit_Review_txt = (EditText) dialog.findViewById(R.id.Edit_Review_txt);
                        dialog.show();
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Review_str = Edit_Review_txt.getText().toString();
                                try {
                                    String response = j.Passenger_Review_Driver(Driver_ID, Passenger_ID, Route_ID, URLEncoder.encode(Review_str));
                                    if (response.equals("\"-1\"") || response.equals("\"-2\'")) {
                                        Toast.makeText(RideDetailsPassenger.this, "Cannot Review", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RideDetailsPassenger.this, "Done", Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            }if (exists) {
                GetData GD = new GetData();
                try {
                    days = "";
                    JSONObject json = GD.GetRouteById(Route_ID);
                    FromRegionEnName.setText(json.getString("FromRegionEnName"));
                    ToRegionEnName.setText(json.getString("ToRegionEnName"));

                    FromEmirateEnName.setText(json.getString("FromEmirateEnName"));
                    ToEmirateEnName.setText(json.getString("ToEmirateEnName"));


                    str_StartFromTime = json.getString("StartFromTime");
                    if (str_StartFromTime.equals("null")){
                        str_StartFromTime="Not Specified";
                        StartFromTime.setText(str_StartFromTime);

                    }else{

                        str_StartFromTime = str_StartFromTime.substring(Math.max(0, str_StartFromTime.length() - 7));
                        StartFromTime.setText(str_StartFromTime);
                    }




                    str_EndToTime_ = json.getString("EndToTime_");
                    if (str_EndToTime_.equals("null")){
                        str_EndToTime_="Not Specified";
                        EndToTime_.setText(str_EndToTime_);

                    }else {
                        str_EndToTime_ = str_EndToTime_.substring(Math.max(0, str_EndToTime_.length() - 7));
                        Log.d("time to", str_EndToTime_);
                        EndToTime_.setText(str_EndToTime_);

                    }



                    Nat_txt = (json.getString("NationalityEnName"));
                    if (Nat_txt.equals("null")) {
                        Nat_txt = "Not Specified";
                        NationalityEnName.setText(Nat_txt);
                    } else {
                        NationalityEnName.setText(Nat_txt);
                    }


                    str_PrefLanguageEnName=json.getString("PrefLanguageEnName");

                    if (str_PrefLanguageEnName.equals("null")){
                        str_PrefLanguageEnName="Not Specified";
                        PrefLanguageEnName.setText(str_PrefLanguageEnName);


                    }else {

                        PrefLanguageEnName.setText(str_PrefLanguageEnName);
                    }




                    Str_AgeRange=json.getString("AgeRange");
                    if (Str_AgeRange.equals("null")){
                        Str_AgeRange="Not Specified";
                        AgeRange.setText(Str_AgeRange);


                    }else {

                        AgeRange.setText(Str_AgeRange);

                    }


                    Gender_ste = "";
                    Gender_ste = json.getString("PreferredGender");
                    switch (Gender_ste) {
                        case "M":
                            Gender_ste = "Male";
                            break;
                        case "F":
                            Gender_ste = "Female";
                            break;
                        default:
                            Gender_ste = "Not Specified";
                            break;
                    }
                    PreferredGender.setText(Gender_ste);
                    Smokers_str = "";
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


                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map_ride_details);
                mapFragment.getMapAsync(RideDetailsPassenger.this);

                Join_Ride_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetData j = new GetData();
                        if (Passenger_ID == 0) {

                            final Dialog dialog = new Dialog(RideDetailsPassenger.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.please_log_in_dialog);
                            Button btn = (Button) dialog.findViewById(R.id.noroute_id);
                            TextView Text_3 = (TextView) dialog.findViewById(R.id.Text_3);
                            Button No_Btn = (Button) dialog.findViewById(R.id.No_Btn);
                            Text_3.setText("In Order to Proceed you have to login first");
                            dialog.show();

                            No_Btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(RideDetailsPassenger.this, LoginApproved.class);
                                    RideDetailsPassenger.this.startActivity(intent);

                                }
                            });


                        } else {
                            try {
                                String response = j.Passenger_SendAlert(Driver_ID, Passenger_ID, Route_ID, "TestCase2");
                                if (response.equals("\"-1\"") || response.equals("\"-2\'")) {
                                    Toast.makeText(RideDetailsPassenger.this, "Cannot Join This Route", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RideDetailsPassenger.this, "successfully  Joined", Toast.LENGTH_SHORT).show();
                                }
                                Log.d("join ride res", String.valueOf(response));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });



                Passenger_Review_Driver_Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (Passenger_ID == 0) {
                            final Dialog dialog = new Dialog(RideDetailsPassenger.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.please_log_in_dialog);
                            Button btn = (Button) dialog.findViewById(R.id.noroute_id);
                            TextView Text_3 = (TextView) dialog.findViewById(R.id.Text_3);
                            Button No_Btn = (Button) dialog.findViewById(R.id.No_Btn);
                            Text_3.setText("In Order to Proceed you have to login first");
                            dialog.show();

                            No_Btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(RideDetailsPassenger.this, LoginApproved.class);
                                    RideDetailsPassenger.this.startActivity(intent);

                                }
                            });
                        } else {


                            final GetData j = new GetData();
                            Review_str = "";
                            final Dialog dialog = new Dialog(con);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.review_dialog);
                            Button btn = (Button) dialog.findViewById(R.id.Review_Btn);
                            Edit_Review_txt = (EditText) dialog.findViewById(R.id.Edit_Review_txt);
                            dialog.show();
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Review_str = Edit_Review_txt.getText().toString();
                                    try {
                                        String response = j.Passenger_Review_Driver(Driver_ID, Passenger_ID, Route_ID, URLEncoder.encode(Review_str));
                                        if (response.equals("\"-1\"") || response.equals("\"-2\'")) {
                                            Toast.makeText(RideDetailsPassenger.this, "Cannot Review", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(RideDetailsPassenger.this, "Done", Toast.LENGTH_SHORT).show();
                                        }
                                        dialog.dismiss();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });


                        } //else
                    } // on click view
                });
                hidePDialog();
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
                        new AlertDialog.Builder(RideDetailsPassenger.this)
                                .setTitle("Connection Problem!")
                                .setMessage("Make sure you have internet connection")
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentToBeNewRoot = new Intent(RideDetailsPassenger.this, RideDetailsPassenger.class);
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
                        Toast.makeText(RideDetailsPassenger.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom
                (new LatLng(StartLat, EndLng), 8.1f));

        // Instantiates a new Polyline object and adds points to define a rectangle
        PolylineOptions rectOptions = new PolylineOptions()
                .add(new LatLng(StartLat, StartLng))
                .add(new LatLng(EndLat, EndLng))
                .color(R.color.primaryColor)
                .width(6);  // North of the previous point, but at the same longitude
        // Closes the polyline.

        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.setMyLocationEnabled(true);

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


}
