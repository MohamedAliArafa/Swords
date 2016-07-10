package rta.ae.sharekni.RideDetails;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import rta.ae.sharekni.Arafa.Classes.GetData;
import rta.ae.sharekni.DriverGetReviewAdapter;
import rta.ae.sharekni.DriverGetReviewDataModel;
import rta.ae.sharekni.LoginApproved;
import rta.ae.sharekni.R;
import rta.ae.sharekni.Ride_Details_Passengers_DataModel;


public class RideDetailsAsPassenger extends AppCompatActivity {


    final List<Ride_Details_Passengers_DataModel> Passengers_arr = new ArrayList<>();
    TextView
            FromRegionEnName, ToRegionEnName, FromEmirateEnName, ToEmirateEnName, StartFromTime, EndToTime_, AgeRange, PreferredGender, IsSmoking, ride_details_day_of_week, NationalityEnName, PrefLanguageEnName;


    String Gender_ste, Nat_txt, Smokers_str;
    int No_Seats;

    int FLAG_HIDE_JOIN = 1;

    String str_StartFromTime, str_EndToTime_, str_PrefLanguageEnName;


    String days;
    String Str_AgeRange;

    final JSONArray[] myJsonArray = new JSONArray[1];
    private Toolbar toolbar;

    int Route_ID;
    int Passenger_ID;
    int Driver_ID;
    Button Join_Ride_btn;
    ListView ride_details_passengers;
    int FLAG_1;

    private GoogleMap mMap;

    double StartLat, StartLng, EndLat, EndLng;

    Activity con;
    RatingBar ratingBar;

    private List<DriverGetReviewDataModel> driverGetReviewDataModels_arr = new ArrayList<>();

    ListView Driver_get_Review_lv;
    int Pass_id;
    boolean exists;
    int FLAG_REVIEW = 0;
    Button Passenger_Review_Driver_Btn;

    SharedPreferences myPrefs;

    String Review_str;
    EditText Edit_Review_txt;
    String str_Remarks = "";
    RelativeLayout Relative_REviews;
    TextView Relative_REviews_Address_2;
    int NoOfStars;

    String FromRegionEnName_Str, ToRegionEnName_Str, FromEmirateEnName_Str, ToEmirateEnName_Str;


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
        // EndToTime_ = (TextView) findViewById(R.id.EndToTime_);

        FromEmirateEnName = (TextView) findViewById(R.id.FromEmirateEnName);
        ToEmirateEnName = (TextView) findViewById(R.id.ToEmirateEnName);
        //Pass_rate_Driver_btn= (Button) findViewById(R.id.Pass_rate_Driver_btn);

        NationalityEnName = (TextView) findViewById(R.id.NationalityEnName);
        PrefLanguageEnName = (TextView) findViewById(R.id.PrefLanguageEnName);

        AgeRange = (TextView) findViewById(R.id.AgeRange);
        PreferredGender = (TextView) findViewById(R.id.PreferredGender);
        IsSmoking = (TextView) findViewById(R.id.IsSmoking);
        Join_Ride_btn = (Button) findViewById(R.id.Join_Ride_btn);
        ride_details_day_of_week = (TextView) findViewById(R.id.ride_details_day_of_week);
        Driver_get_Review_lv = (ListView) findViewById(R.id.Driver_get_Review_lv);
        Passenger_Review_Driver_Btn = (Button) findViewById(R.id.Passenger_Review_Driver_Btn);
        Relative_REviews = (RelativeLayout) findViewById(R.id.Relative_REviews);
        Relative_REviews_Address_2 = (TextView) findViewById(R.id.Relative_REviews_Address_2);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar2);
        ratingBar.setStepSize(1);

        Join_Ride_btn.setVisibility(View.INVISIBLE);
        //Pass_rate_Driver_btn.setVisibility(View.INVISIBLE);
        ratingBar.setVisibility(View.GONE);
        Passenger_Review_Driver_Btn.setVisibility(View.GONE);

        // setListViewHeightBasedOnChildren(Driver_get_Review_lv);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        myPrefs = this.getSharedPreferences("myPrefs", 0);
        Passenger_ID = Integer.parseInt(myPrefs.getString("account_id", "0"));

        Bundle in = getIntent().getExtras();
        Driver_ID = in.getInt("DriverID");
        Route_ID = in.getInt("RouteID");

//        Pass_rate_Driver_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Dialog dialog = new Dialog(RideDetailsPassenger.this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.rate_layout);
//                Button btn = (Button) dialog.findViewById(R.id.noroute_id);
//                final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.ratingBar);
//                ratingBar.setStepSize(1);
//                dialog.show();
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        NoOfStars = (int) ratingBar.getRating();
//                        Log.d("NoOfStars", String.valueOf(NoOfStars));
//                        new rateDriver().execute();
//                        dialog.dismiss();
//                    }
//                });
//            }
//        });

        try {

            if (in.getInt("FLAG_1") == 1) {
                Join_Ride_btn.setVisibility(View.INVISIBLE);
                ratingBar.setVisibility(View.VISIBLE);
                Passenger_Review_Driver_Btn.setVisibility(View.VISIBLE);

            } else {
                Join_Ride_btn.setVisibility(View.VISIBLE);
                Passenger_Review_Driver_Btn.setVisibility(View.GONE);
            }
        } catch (NullPointerException e) {

        }


        Log.d("Test Driver id", String.valueOf(Driver_ID));
        Log.d("test Route id", String.valueOf(Route_ID));
        Log.d("test Passenger id 2", String.valueOf(Passenger_ID));

        exists = false;
        new back().execute();


    }  //  on create


    private class rateDriver extends AsyncTask {

        String res;

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Log.d("res", res);
            if (res.equals("\"1\"")) {
                Toast.makeText(RideDetailsAsPassenger.this, R.string.rate_submitted, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RideDetailsAsPassenger.this, R.string.rate_submit_failed, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Object doInBackground(Object[] params) {
            GetData gd = new GetData();
            try {
                res = gd.Passenger_RateDriver(Driver_ID, Passenger_ID, Route_ID, NoOfStars);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class back extends AsyncTask implements OnMapReadyCallback {

        JSONArray response2 = null;
        JSONObject json;
        private ProgressDialog pDialog;
        JSONArray response1 = null;
        GetData j = new GetData();

        private void hidePDialog() {
            if (pDialog != null) {
                pDialog.dismiss();
                pDialog = null;
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(RideDetailsAsPassenger.this);
            pDialog.setMessage(getString(R.string.loading) + "...");
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Object o) {

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map_ride_details);
            mapFragment.getMapAsync(this);

//            if (FLAG_HIDE_JOIN==1) {
//                Join_Ride_btn.setVisibility(View.VISIBLE);

//            }

            if (FLAG_HIDE_JOIN == 2) {

                ratingBar.setVisibility(View.VISIBLE);
                Join_Ride_btn.setVisibility(View.INVISIBLE);
                Passenger_Review_Driver_Btn.setVisibility(View.VISIBLE);

            }
            if (FLAG_HIDE_JOIN == 5) {


                Join_Ride_btn.setVisibility(View.INVISIBLE);

            }

            if (FLAG_REVIEW == 0) {
                Relative_REviews.setVisibility(View.INVISIBLE);
                Relative_REviews_Address_2.setVisibility(View.INVISIBLE);
            }


            DriverGetReviewAdapter arrayAdapter = new DriverGetReviewAdapter(con, driverGetReviewDataModels_arr);
            Driver_get_Review_lv.setAdapter(arrayAdapter);
            setListViewHeightBasedOnChildren(Driver_get_Review_lv);

            Passenger_Review_Driver_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Passenger_ID == 0) {
                        final Dialog dialog = new Dialog(RideDetailsAsPassenger.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.please_log_in_dialog);
                        Button btn = (Button) dialog.findViewById(R.id.noroute_id);
                        TextView Text_3 = (TextView) dialog.findViewById(R.id.Text_3);
                        Button No_Btn = (Button) dialog.findViewById(R.id.No_Btn);
                        Text_3.setText(getString(R.string.login_first));
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
                                Intent intent = new Intent(RideDetailsAsPassenger.this, LoginApproved.class);
                                RideDetailsAsPassenger.this.startActivity(intent);
                            }
                        });
                    } else {

                        Review_str = "";
                        final Dialog dialog = new Dialog(con);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.review_dialog);
                        Button btn = (Button) dialog.findViewById(R.id.Review_Btn);
                        final TextView Review_Empty_Error = (TextView) dialog.findViewById(R.id.Review_Empty_Error);
                        Edit_Review_txt = (EditText) dialog.findViewById(R.id.Edit_Review_txt);
                        dialog.show();
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Review_str = Edit_Review_txt.getText().toString();

                                if (!Review_str.equals("")) {
                                    Review_Empty_Error.setVisibility(View.INVISIBLE);
                                    try {
                                        String response = j.Passenger_Review_Driver(Driver_ID, Passenger_ID, Route_ID, URLEncoder.encode(Review_str));
                                        if (response.equals("\"-1\"") || response.equals("\"-2\'")) {
                                            Toast.makeText(RideDetailsAsPassenger.this, getString(R.string.cannot_review), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(RideDetailsAsPassenger.this, getString(R.string.done), Toast.LENGTH_SHORT).show();
                                            con.recreate();
                                        }
                                        dialog.dismiss();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }//  if review is null
                                else {
                                    Review_Empty_Error.setVisibility(View.VISIBLE);

                                }


                            }
                        });

                    }
                }
            });


            if (exists) {
                try {
                    ratingBar.setRating(Float.parseFloat(j.Passenger_GetDriverRate(Driver_ID, Passenger_ID, Route_ID)));
                    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            NoOfStars = (int) rating;
                            new rateDriver().execute();
                        }
                    });
                    days = "";
                    FromRegionEnName.setText(json.getString(getString(R.string.from_reg_en_name)));
                    ToRegionEnName.setText(json.getString(getString(R.string.fto_reg_en_name)));
                    FromEmirateEnName.setText(json.getString(getString(R.string.from_em_en_name)));
                    ToEmirateEnName.setText(json.getString(getString(R.string.to_em_en_name)));

                    FromRegionEnName_Str = (json.getString(getString(R.string.from_reg_en_name)));
                    ToRegionEnName_Str = (json.getString(getString(R.string.fto_reg_en_name)));
                    FromEmirateEnName_Str = (json.getString(getString(R.string.from_em_en_name)));
                    ToEmirateEnName_Str = (json.getString(getString(R.string.to_em_en_name)));


                    str_StartFromTime = json.getString("StartFromTime");
                    No_Seats = json.getInt("NoOfSeats");
                    if (No_Seats == 0) {
                        Join_Ride_btn.setVisibility(View.INVISIBLE);
                    }
                    if (str_StartFromTime.equals("null")) {
                        str_StartFromTime = getString(R.string.not_set);
                        StartFromTime.setText(str_StartFromTime);
                    } else {
                        str_StartFromTime = str_StartFromTime.substring(Math.max(0, str_StartFromTime.length() - 7));
                        StartFromTime.setText(str_StartFromTime);
                    }
                    str_EndToTime_ = json.getString("EndToTime_");
                    if (str_EndToTime_.equals("null")) {
                        str_EndToTime_ = getString(R.string.not_set);
                        //EndToTime_.setText(str_EndToTime_);
                    } else {
                        str_EndToTime_ = str_EndToTime_.substring(Math.max(0, str_EndToTime_.length() - 7));
                        Log.d("time to", str_EndToTime_);
                        // EndToTime_.setText(str_EndToTime_);
                    }
                    if (json.getString(getString(R.string.nat_name2)).equals("null")) {
                        NationalityEnName.setText(getString(R.string.not_set));
                    } else {
                        NationalityEnName.setText(json.getString(getString(R.string.nat_name2)));
                    }
                    if (json.getString("PrefLanguageId").equals("0") || json.getString("PrefLanguageId").equals("null")) {
                        PrefLanguageEnName.setText(getString(R.string.not_set));
                    } else {
                        PrefLanguageEnName.setText(json.getString(getString(R.string.pref_lang)));
                    }
                    if (json.getString("AgeRangeID").equals("0") || json.getString("AgeRangeID").equals("null")) {
                        AgeRange.setText(getString(R.string.not_set));
                    } else {
                        AgeRange.setText(json.getString("AgeRange"));
                    }
                    Gender_ste = "";
                    Gender_ste = json.getString("PreferredGender");
                    switch (Gender_ste) {
                        case "M":
                            Gender_ste = getString(R.string.reg_gender_male);
                            break;
                        case "F":
                            Gender_ste = getString(R.string.reg_gender_female);
                            break;
                        default:
                            Gender_ste = getString(R.string.not_set);
                            break;
                    }
                    PreferredGender.setText(Gender_ste);
                    Smokers_str = "";
                    Smokers_str = json.getString("IsSmoking");
                    if (Smokers_str.equals("true")) {
                        Smokers_str = getString(R.string.Accept_Smokers_txt);
                    } else if (Smokers_str.equals("false")) {
                        Smokers_str = getString(R.string.not_set);
                    } else {
                        Smokers_str = getString(R.string.not_set);
                    }

                    IsSmoking.setText(Smokers_str);
                    StartLat = json.getDouble("StartLat");
                    StartLng = json.getDouble("StartLng");
                    EndLat = json.getDouble("EndLat");
                    EndLng = json.getDouble("EndLng");
                    Log.d("S Lat", String.valueOf(StartLat));

                    if (json.getString("Saturday").equals("true")) {
                        days += getString(R.string.sat);
                    }
                    if (json.getString("Sunday").equals("true")) {
                        days += getString(R.string.sun);
                    }
                    if (json.getString("Monday").equals("true")) {
                        days += getString(R.string.mon);
                    }
                    if (json.getString("Tuesday").equals("true")) {
                        days += getString(R.string.tue);
                    }
                    if (json.getString("Wednesday").equals("true")) {
                        days += getString(R.string.wed);
                    }
                    if (json.getString("Thursday").equals("true")) {
                        days += getString(R.string.thu);
                    }
                    if (json.getString("Friday").equals("true")) {
                        days += getString(R.string.fri);
                    }
                    if (!days.equals("")) {
                        ride_details_day_of_week.setText(days.substring(1));
                    } else {
                        ride_details_day_of_week.setText(days);
                    }
                    days = "";

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Join_Ride_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Passenger_ID == 0) {
                            final Dialog dialog = new Dialog(RideDetailsAsPassenger.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.please_log_in_dialog);
                            Button btn = (Button) dialog.findViewById(R.id.noroute_id);
                            TextView Text_3 = (TextView) dialog.findViewById(R.id.Text_3);
                            Button No_Btn = (Button) dialog.findViewById(R.id.No_Btn);
                            Text_3.setText(getString(R.string.login_first));
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
                                    Intent intent = new Intent(RideDetailsAsPassenger.this, LoginApproved.class);
                                    RideDetailsAsPassenger.this.startActivity(intent);
                                }
                            });
                        } else {
                            final Dialog dialog = new Dialog(con);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.review_dialog);
                            Button btn = (Button) dialog.findViewById(R.id.Review_Btn);
                            TextView Lang_Dialog_txt_id = (TextView) dialog.findViewById(R.id.Lang_Dialog_txt_id);
                            TextView Review_text_address = (TextView) dialog.findViewById(R.id.Review_text_address);
                            Edit_Review_txt = (EditText) dialog.findViewById(R.id.Edit_Review_txt);
                            Lang_Dialog_txt_id.setText(R.string.write_remark);
                            Review_text_address.setText(R.string.your_remarks);
                            Edit_Review_txt.setText(R.string.i_like_join_ride);
                            dialog.show();
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    str_Remarks = Edit_Review_txt.getText().toString();
                                    String response = null;
                                    try {
                                        response = j.Passenger_SendAlert(Driver_ID, Passenger_ID, Route_ID, URLEncoder.encode(str_Remarks));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    assert response != null;
                                    switch (response) {
                                        case "\"-1\"":
                                            Toast.makeText(RideDetailsAsPassenger.this, getString(R.string.already_sent_request), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            break;
                                        case "\"0\"":
                                            Toast.makeText(RideDetailsAsPassenger.this, getString(R.string.login_network_error), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            break;
                                        default:
                                            Toast.makeText(RideDetailsAsPassenger.this, R.string.req_sent_succ, Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                            Join_Ride_btn.setVisibility(View.INVISIBLE);
                                            finish();
                                            break;
                                    }
                                }
                            });


                        }
                    }
                });


//
//                Passenger_Review_Driver_Btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (Passenger_ID == 0) {
//                            final Dialog dialog = new Dialog(RideDetailsPassenger.this);
//                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                            dialog.setContentView(R.layout.please_log_in_dialog);
//                            Button btn = (Button) dialog.findViewById(R.id.noroute_id);
//                            TextView Text_3 = (TextView) dialog.findViewById(R.id.Text_3);
//                            Button No_Btn = (Button) dialog.findViewById(R.id.No_Btn);
//                            Text_3.setText("In order to proceed you have to login first");
//                            dialog.show();
//
//                            No_Btn.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.dismiss();
//                                }
//                            });
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
//
//
//                            final GetData j = new GetData();
//                            Review_str = "";
//                            final Dialog dialog = new Dialog(con);
//                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                            dialog.setContentView(R.layout.review_dialog);
//                            Button btn = (Button) dialog.findViewById(R.id.Review_Btn);
//                            Edit_Review_txt = (EditText) dialog.findViewById(R.id.Edit_Review_txt);
//                            dialog.show();
//
//                            btn.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Review_str = Edit_Review_txt.getText().toString();
//                                    try {
//                                        String response = j.Passenger_Review_Driver(Driver_ID, Passenger_ID, Route_ID, URLEncoder.encode(Review_str));
//                                        if (response.equals("\"-1\"") || response.equals("\"-2\'")) {
//                                            Toast.makeText(RideDetailsPassenger.this, "Cannot Review", Toast.LENGTH_SHORT).show();
//                                        } else {
//                                            Toast.makeText(RideDetailsPassenger.this, "Done", Toast.LENGTH_SHORT).show();
//                                        }
//                                        dialog.dismiss();
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });
//
//
//                        } //else
//                    } // on click view
//                });
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
                json = new GetData().GetRouteById(Route_ID);
                response2 = new GetData().GetPassengers_ByRouteID(Route_ID);
                response1 = new GetData().Driver_GetReview(Driver_ID, Route_ID);
                assert response1 != null;
                for (int i = 0; i < response1.length(); i++) {
                    try {
                        JSONObject obj = response1.getJSONObject(i);
                        final DriverGetReviewDataModel review = new DriverGetReviewDataModel(Parcel.obtain());
                        review.setDriverID(Driver_ID);
                        review.setAccountID(obj.getInt("AccountId"));
                        review.setAccountName(obj.getString("AccountName"));
                        review.setAccountNationalityEn(obj.getString(getString(R.string.acc_nat_name)));
                        review.setReview(obj.getString("Review"));
                        review.setReviewID(obj.getInt("ReviewId"));
                        review.setRoute_ID(Route_ID);
                        review.setDriverID(Driver_ID);
                        if (!review.getReview().equals("") && !review.getReview().equals("null")) {
                            driverGetReviewDataModels_arr.add(review);
                            FLAG_REVIEW++;

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                assert response2 != null;
                Log.d("Passengers resp", response2.toString());

                for (int y = 0; y < response2.length(); y++) {

                    try {
                        JSONObject obj = response2.getJSONObject(y);
                        final Ride_Details_Passengers_DataModel item = new Ride_Details_Passengers_DataModel(Parcel.obtain());
                        Log.d("Passenger Name", obj.getString("AccountName"));
//                        item.setAccountPhoto(obj.getString("AccountPhoto"));
                        Log.d("Passenger id", String.valueOf(Passenger_ID));
                        Log.d("Pass list id", String.valueOf(obj.getInt("AccountId")));
                        if (Passenger_ID == obj.getInt("AccountId") && obj.getString("RequestStatus").equals("true")) {
                            FLAG_HIDE_JOIN = 2;
                        }

                        if (Passenger_ID == obj.getInt("AccountId")) {
                            if (obj.getString("RequestStatus").equals("null") || obj.getString("RequestStatus").equals("false")) {
                                FLAG_HIDE_JOIN = 5;
                            }
                        }

                        item.setPassengerId(obj.getInt("ID"));
                        item.setAccountName(obj.getString("AccountName"));
                        item.setRate(obj.getInt("PassenegerRateByDriver"));

                        if (obj.getString("AccountMobile").equals("null")) {
                            item.setAccountMobile("");
                        } else {
                            item.setAccountMobile(obj.getString("AccountMobile"));
                        }

                        item.setAccountNationalityEn(obj.getString(getString(R.string.acc_nat_name)));
                        Passengers_arr.add(item);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                }


                exists = true;
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(RideDetailsAsPassenger.this)
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
                        Toast.makeText(RideDetailsAsPassenger.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }


        @Override
        public void onMapReady(GoogleMap googleMap) {


            mMap = googleMap;

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom
                    (new LatLng(StartLat, EndLng), 8.1f));

            // Instantiates a new Polyline object and adds points to define a rectangle

            Log.d("Start lat 1 ", String.valueOf(StartLat));
            Log.d("End lat 1 ", String.valueOf(EndLat));
            Log.d("Start lng 1 ", String.valueOf(StartLng));
            Log.d("End lng 1 ", String.valueOf(EndLng));

            PolylineOptions rectOptions = new PolylineOptions()
                    .add(new LatLng(StartLat, StartLng))
                    .add(new LatLng(EndLat, EndLng))
                    .color(R.color.primaryColor)
                    .width(6);  // North of the previous point, but at the same longitude
            // Closes the polyline.

            mMap.getUiSettings().setMapToolbarEnabled(false);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setZoomGesturesEnabled(true);


// Get back the mutable Polyline
            Polyline polyline = mMap.addPolyline(rectOptions);


            final Marker markerZero = mMap.addMarker(new MarkerOptions().
                    position(new LatLng(StartLat, StartLng))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pindriver)).snippet(FromRegionEnName_Str).title(FromEmirateEnName_Str));

            mMap.addMarker(new MarkerOptions().
                    position(new LatLng(EndLat, EndLng))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pindriver)).snippet(ToRegionEnName_Str).title(ToEmirateEnName_Str));
            markerZero.showInfoWindow();


        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText(getString(R.string.ride_details));
//        toolbar.setElevation(10);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_most_rides_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }


}
