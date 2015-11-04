package com.example.nezarsaleh.shareknitest.Arafa.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

import com.example.nezarsaleh.shareknitest.Arafa.Classes.AppController;
import com.example.nezarsaleh.shareknitest.Arafa.Classes.CircularNetworkImageView;
import com.example.nezarsaleh.shareknitest.Arafa.Classes.GetData;
import com.example.nezarsaleh.shareknitest.Arafa.DataModel.BestDriverDataModel;
import com.example.nezarsaleh.shareknitest.Arafa.DataModel.BestRouteDataModel;
import com.example.nezarsaleh.shareknitest.Arafa.DataModelAdapter.ProfileRideAdapter;
import com.example.nezarsaleh.shareknitest.LoginApproved;
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
    String FirstName,SecondName,ThirdName,Full_Name;

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
        pDialog.setMessage(getString(R.string.loading) + "...");
        pDialog.show();

        new jsoning(lv_driver,pDialog,this).execute();

        try {
            final JSONObject json = j.GetDriverById(Driver_ID);

           // TopName.setText(json.getString("FirstName") + " " + json.getString("MiddleName"));

            Full_Name="";
            FirstName= json.getString("FirstName");
            if (FirstName.equals("")){
                Full_Name+="";
            }else{
                FirstName = FirstName.substring(0, 1).toUpperCase() + FirstName.substring(1);
                Log.d("First Name",FirstName);
                Full_Name+=FirstName;

            }


            SecondName = json.getString("MiddleName");
            if (SecondName.equals("")){
                Full_Name+=" ";
            }else{

                Log.d("Second name 1",SecondName);
                SecondName =  SecondName.substring(0, 1).toUpperCase() + SecondName.substring(1);
                Log.d("Second name 2",SecondName);
                Full_Name+=" ";
                Full_Name+=SecondName;
            }

            ThirdName = json.getString("LastName");
            if (ThirdName.equals("")){

                Full_Name+=" ";
            }else {

                Log.d("Second name 1",ThirdName);
                ThirdName =  ThirdName.substring(0, 1).toUpperCase() + ThirdName.substring(1);
                Log.d("Second name 2",ThirdName);
                Full_Name+=" ";
                Full_Name+=ThirdName;
            }








            TopName.setText(Full_Name);

            NationalityEnName.setText(json.getString(getString(R.string.nat_name2)));
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
                        intent.putExtra("sms_body", getString(R.string.hello_world) + json.getString("FirstName"));
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
        textView.setText(R.string.driver_details);


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
                                .setTitle(getString(R.string.connection_problem))
                                .setMessage(getString(R.string.con_problem_message))
                                .setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentToBeNewRoot = new Intent(Profile.this, Profile.class);
                                        ComponentName cn = intentToBeNewRoot.getComponent();
                                        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                                        mainIntent.putExtra("DriverID",Driver_ID);
                                        startActivity(mainIntent);
                                    }
                                })
                                .setNegativeButton(getString(R.string.exit), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();
                        Toast.makeText(Profile.this,getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
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
                        item.setFromEm(json.getString(getString(R.string.from_em_en_name)));
                        item.setFromReg(json.getString(getString(R.string.from_reg_en_name)));
                        item.setToEm(json.getString(getString(R.string.to_em_en_name)));
                        item.setToReg(json.getString(getString(R.string.to_reg_en_name)));
                        item.setRouteName(json.getString(getString(R.string.route_name)));
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


                        item.setDriver_profile_dayWeek(days);
                        days = "";
                        driver[i] = item;
                        Log.d("ID", String.valueOf(json.getInt("ID")));
//                        Log.d("FromEmlv", json.getString(getString(R.string.from_em_name)));
//                        Log.d("FromReglv", json.getString(getString(R.string.from_reg_name)));
//                        Log.d("TomEmlv", json.getString(getString(R.string.to_em_name)));
//                        Log.d("ToReglv", json.getString(getString(R.string.to_reg_name)));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id==android.R.id.home){
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