package com.example.nezarsaleh.shareknitest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nezarsaleh.shareknitest.Arafa.Activities.BestDriversBeforeLogin;
import com.example.nezarsaleh.shareknitest.Arafa.Activities.Profile;
import com.example.nezarsaleh.shareknitest.Arafa.Classes.GetData;
import com.example.nezarsaleh.shareknitest.Arafa.Classes.ImageDecoder;
import com.example.nezarsaleh.shareknitest.Arafa.DataModel.BestDriverDataModel;
import com.example.nezarsaleh.shareknitest.MainNavigationDrawerFragment.NavigationDrawerFragment;
import com.example.nezarsaleh.shareknitest.OnBoardDir.OnboardingActivity;
import com.pkmmte.view.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Locale;


public class HomePage extends ActionBarActivity implements View.OnClickListener {


    public static String ImagePhotoPath;
    public Thread t;
    static HomePage HomaPageActivity;
    String ID;
    String AccountType;
    CircularImageView circularImageView;
    TextView name, nat, Lnag_home;
    SharedPreferences myPrefs;
    RelativeLayout btn_create,btn_history;
    ImageView btn_edit;
    int Driver_ID;
    RelativeLayout Relative_quickSearch;
    TextView VehiclesCount, PassengerJoinedRidesCount, DriverMyRidesCount, DriverMyAlertsCount;
    RelativeLayout Home_Relative_Notify;
    String DriverMyRidesCount_str, PassengerJoinedRidesCount_str, VehiclesCount_str;
    Toolbar toolbar;
    RelativeLayout Home_Relative_Permit, Home_Realtive_Vehicles, driver_rides_Created;
    RelativeLayout Rides_joined_Relative;
    String name_str,nat_str;
    int DRIVER_ALERTS_COUNT=0;
    int y;
    int All_Alerts;
    String Firstname,LastName;
    Activity c;


    public static HomePage getInstance() {
        return HomaPageActivity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        HomaPageActivity = this;

        super.onCreate(savedInstanceState);
        try {
            if (LoginApproved.getInstance() != null) {
                LoginApproved.getInstance().finish();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (LoginApproved.pDialog != null) {
            LoginApproved.pDialog.dismiss();
            LoginApproved.pDialog = null;
        }
        try {
            if (RegisterNewTest.getInstance() != null) {
                RegisterNewTest.getInstance().finish();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();


        }
        try {
            if (OnboardingActivity.getInstance() != null) {
                OnboardingActivity.getInstance().finish();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();


        }



        setContentView(R.layout.home_page_approved);
        initToolbar();
        c=this;
        name = (TextView) findViewById(R.id.tv_name_home);
        nat = (TextView) findViewById(R.id.nat_home);
        Lnag_home = (TextView) findViewById(R.id.lang_Home);
        btn_create = (RelativeLayout) findViewById(R.id.btn_createCarPool);
        Home_Relative_Permit = (RelativeLayout) findViewById(R.id.Home_Relative_Permit);
        Home_Realtive_Vehicles = (RelativeLayout) findViewById(R.id.Home_Realtive_Vehicles);
        driver_rides_Created = (RelativeLayout) findViewById(R.id.driver_rides_Created);
        btn_create.setOnClickListener(this);
        VehiclesCount = (TextView) findViewById(R.id.VehiclesCount);
        PassengerJoinedRidesCount = (TextView) findViewById(R.id.PassengerJoinedRidesCount);
        DriverMyRidesCount = (TextView) findViewById(R.id.DriverMyRidesCount);
        DriverMyAlertsCount = (TextView) findViewById(R.id.DriverMyAlertsCount);
        Relative_quickSearch = (RelativeLayout) findViewById(R.id.Relative_quickSearch);
        Home_Relative_Notify = (RelativeLayout) findViewById(R.id.Home_Relative_Notify);
        driver_rides_Created = (RelativeLayout) findViewById(R.id.driver_rides_Created);
        btn_edit = (ImageView) findViewById(R.id.Edit_Profile_Im);
        Rides_joined_Relative= (RelativeLayout) findViewById(R.id.Rides_joined_Relative);
        btn_history = (RelativeLayout) findViewById(R.id.home_history);

            circularImageView = (CircularImageView) findViewById(R.id.profilepic);
            circularImageView.setBorderWidth(5);
            circularImageView.setSelectorStrokeWidth(5);
            circularImageView.addShadow();

        myPrefs = this.getSharedPreferences("myPrefs", 0);
        ID = myPrefs.getString("account_id", null);
        AccountType = myPrefs.getString("account_type", null);

        Driver_ID = Integer.parseInt(ID);

        t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new refresh().execute();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();

        new loading().execute();



    }  // on create




    private class refresh extends AsyncTask{

        JSONObject jsonArray;

        @Override
        protected void onPostExecute(Object o) {
            VehiclesCount_str="";
            VehiclesCount_str += "(";
            try {
                All_Alerts = jsonArray.getInt("DriverMyAlertsCount")+jsonArray.getInt("PassengerMyAlertsCount");
                VehiclesCount_str += jsonArray.getString("VehiclesCount");
                VehiclesCount_str += ")";
                VehiclesCount.setText(VehiclesCount_str);
                PassengerJoinedRidesCount_str="";
                PassengerJoinedRidesCount_str += "(";
                PassengerJoinedRidesCount_str += jsonArray.getString("PassengerJoinedRidesCount");
                PassengerJoinedRidesCount_str += ")";
                PassengerJoinedRidesCount.setText(PassengerJoinedRidesCount_str);

                if (DRIVER_ALERTS_COUNT < All_Alerts){
                    DRIVER_ALERTS_COUNT = All_Alerts;
                    CreateNotification(y++);
                }
                DriverMyRidesCount_str="";
                DriverMyRidesCount_str += "(";
                DriverMyRidesCount_str += jsonArray.getString("DriverMyRidesCount");
                DriverMyRidesCount_str += ")";
                DriverMyRidesCount.setText(DriverMyRidesCount_str);
                DriverMyRidesCount.setText(DriverMyRidesCount_str);
                DriverMyAlertsCount.setText( String.valueOf(All_Alerts));

//                if (DRIVER_ALERTS_COUNT>0){
//
//
//
//                }


                DriverMyAlertsCount.setText( String.valueOf(All_Alerts));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Object doInBackground(Object[] params) {
            final GetData j = new GetData();
            try {
                jsonArray = j.GetDriverById(Integer.parseInt(ID));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void CreateNotification(int y) {
        GetData gd = new GetData();
        try {
            JSONArray jsonArray = gd.GetDriverAlertsForRequest(Integer.parseInt(ID));
            for (int i = 0;i< jsonArray.length();i++){
                JSONObject j = jsonArray.getJSONObject(i);
                Intent intent = new Intent(this, DriverAlertsForRequest.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                Notification.Builder builder = new Notification.Builder(this);
                builder.setContentTitle("Route :" + j.getString("RouteName"));
                builder.setContentText(j.getString("PassengerName") + " Send You A Join Request ");
                builder.setSmallIcon(R.drawable.sharekni_logo);
                builder.setContentIntent(pendingIntent);
                Notification notification = builder.build();
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(y, notification);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            JSONArray jsonArray = gd.Get_Passenger_GetAcceptedRequestsFromDriver(Integer.parseInt(ID));

            for (int i = 0;i< jsonArray.length();i++){
                JSONObject j = jsonArray.getJSONObject(i);
                if (j.getString("DriverAccept").equals("false")){

                    Intent intent = new Intent(this, DriverAlertsForRequest.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                    Notification.Builder builder = new Notification.Builder(this);
                    builder.setContentTitle("Route :" + j.getString("RouteName"));
                    builder.setContentText(j.getString("DriverName") + " Rejected Your Request ");
                    builder.setSmallIcon(R.drawable.sharekni_logo);
                    builder.setContentIntent(pendingIntent);
                    Notification notification = builder.build();
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.notify(y, notification);

                } //  if
                 else {

                    Intent intent = new Intent(this, DriverAlertsForRequest.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                    Notification.Builder builder = new Notification.Builder(this);
                    builder.setContentTitle("Route :" + j.getString("RouteName"));
                    builder.setContentText(j.getString("DriverName") + " Accepted Your Request ");
                    builder.setSmallIcon(R.drawable.sharekni_logo);
                    builder.setContentIntent(pendingIntent);
                    Notification notification = builder.build();
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.notify(y, notification);
                } // else

                }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub_navigate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();




//        if (id==R.id.Start_Service){
//
//            Intent intent = new Intent(this,MyNotifications.class);
//            intent.putExtra("Flag",2);
//            startService(intent);
//        }
//
//        if (id==R.id.Stop_Service){
//
//            Intent intent = new Intent(this,MyNotifications.class);
//            stopService(intent);
//
//        }

        //noinspection SimplifiableIfStatement

//        if (id == R.id.change_Pass_menu) {
//            Intent intent = new Intent(this, ChangePasswordTest.class);
//            startActivity(intent);
//        }
//
//        if (id == R.id.EditProfile_Pass_menu) {
//
//            Intent intent = new Intent(this, EditProfileTest.class);
//            startActivity(intent);
//
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        t.interrupt();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        if (AccountType.equals("D")) {
            if (v == btn_create) {
                Intent intent = new Intent(getBaseContext(), DriverCreateCarPool.class);
                intent.putExtra("ID", Driver_ID);
                startActivity(intent);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText("Home Page");
//        toolbar.setElevation(10);

        setSupportActionBar(toolbar);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawwer);
        drawerFragment.setUp(R.id.fragment_navigation_drawwer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

    }

    private class loading extends AsyncTask{

        ProgressDialog pDialog;
        JSONObject jsonArray;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(HomePage.this);
            pDialog.setMessage("Loading" + "...");
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            try {
                All_Alerts = jsonArray.getInt("DriverMyAlertsCount")+jsonArray.getInt("PassengerMyAlertsCount");
                name_str="";
                nat_str="";
                Firstname="";
                LastName="";
                Firstname=(jsonArray.getString("FirstName"));
                Firstname=  Firstname.substring(0, 1).toUpperCase() + Firstname.substring(1);
                LastName= (jsonArray.getString("LastName"));
                LastName=  LastName.substring(0, 1).toUpperCase() + LastName.substring(1);
                name_str=Firstname+" "+LastName;
                //  name_str = (jsonArray.getString("FirstName") + " " + jsonArray.getString("LastName"));
                nat_str = (jsonArray.getString("NationalityEnName"));
                //   name_str=  name_str.substring(0, 1).toUpperCase() + name_str.substring(1);
                nat_str= nat_str.substring(0, 1).toUpperCase() + nat_str.substring(1);
                NavigationDrawerFragment.tv_name_home.setText(name_str);
                name.setText(name_str);
                nat.setText(nat_str);
                NavigationDrawerFragment.nat_home.setText(nat_str);
                //   str.substring(0, 1).toUpperCase() + str.substring(1);
                VehiclesCount_str="";
                VehiclesCount_str += "(";
                VehiclesCount_str += (jsonArray.getString("VehiclesCount"));
                VehiclesCount_str += ")";
                VehiclesCount.setText(VehiclesCount_str);
                PassengerJoinedRidesCount_str="";
                PassengerJoinedRidesCount_str += "(";
                PassengerJoinedRidesCount_str += (jsonArray.getString("PassengerJoinedRidesCount"));
                PassengerJoinedRidesCount_str += ")";
                PassengerJoinedRidesCount.setText(PassengerJoinedRidesCount_str);
                DriverMyRidesCount_str="";
                DriverMyRidesCount_str += "(";
                DriverMyRidesCount_str += (jsonArray.getString("DriverMyRidesCount"));
                DriverMyRidesCount_str += ")";
                DriverMyRidesCount.setText(DriverMyRidesCount_str);
                DriverMyAlertsCount.setText( String.valueOf(All_Alerts));
                assert AccountType != null;
                if (!AccountType.equals("D")) {
                    btn_create.setBackgroundColor(Color.LTGRAY);
                    Home_Relative_Permit.setBackgroundColor(Color.LTGRAY);
                    Home_Realtive_Vehicles.setVisibility(View.INVISIBLE);
                    driver_rides_Created.setVisibility(View.INVISIBLE);
                }else {

                    Home_Realtive_Vehicles.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Dialog dialog = new Dialog(c);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.noroutesdialog);
                            Button btn = (Button) dialog.findViewById(R.id.noroute_id);
                            TextView Text_3 = (TextView) dialog.findViewById(R.id.Text_3);
                            dialog.show();
                            Text_3.setText("This service is temporary unavailable. Please check again later");

                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();

                                }
                            });
                        }
                    });

                }
                if (jsonArray.getString("GenderEn").equals("Female")) {
                    circularImageView.setImageResource(R.drawable.defaultdriverfemale);
                }

//                circularImageView.setImageURI(Uri.parse(GetData.PhotoURL));
                ImageDecoder im = new ImageDecoder();
                im.stringRequest(jsonArray.getString("PhotoPath"), circularImageView, HomePage.this);
                im.stringRequest(jsonArray.getString("PhotoPath"), NavigationDrawerFragment.circularImageView, HomePage.this);
                ImagePhotoPath = jsonArray.getString("PhotoPath");
            } catch (JSONException e) {
                hidePDialog();
                e.printStackTrace();
            }

            btn_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), History.class);
                    startActivity(intent);
                }
            });

            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), EditProfileTest.class);
                    startActivity(intent);
                }
            });

            Home_Relative_Notify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getBaseContext(), DriverAlertsForRequest.class);
                    startActivity(intent);

                }
            });


            Relative_quickSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getBaseContext(), SearchOptions.class);
                    intent.putExtra("PassengerId",ID);
                    startActivity(intent);

                }
            });


            driver_rides_Created.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), DriverCreatedRides.class);
                    intent.putExtra("DriverID", Driver_ID);
                    startActivity(intent);
                }
            });

            Rides_joined_Relative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), PassengerMyApprovedRides.class);
                    startActivity(intent);
                }
            });

            hidePDialog();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            final GetData j = new GetData();
            boolean exists = false;
            try {
                SocketAddress sockaddr = new InetSocketAddress("www.google.com", 80);
                Socket sock = new Socket();
                int timeoutMs = 20000;   // 2 seconds
                sock.connect(sockaddr, timeoutMs);
                exists = true;
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(HomePage.this)
                                .setTitle("Connection Problem!")
                                .setMessage("Make sure you have internet connection")
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentToBeNewRoot = new Intent(HomePage.this, HomePage.class);
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
                        Toast.makeText(HomePage.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                try {
                    Log.d("Driver Id", String.valueOf(Driver_ID));
                    jsonArray = j.GetDriverById(Integer.parseInt(String.valueOf(Driver_ID)));
                } catch (JSONException e) {
                    hidePDialog();
                    e.printStackTrace();
                }
            }
            return null;
        }

        private void hidePDialog() {
            if (pDialog != null) {
                pDialog.dismiss();
                pDialog = null;
            }
        }
    }

}
