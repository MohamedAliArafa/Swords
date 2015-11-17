package rta.ae.sharekni;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.pkmmte.view.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Locale;

import rta.ae.sharekni.Arafa.Classes.AppController;
import rta.ae.sharekni.Arafa.Classes.GetData;
import rta.ae.sharekni.Arafa.Classes.ImageDecoder;
import rta.ae.sharekni.MainNavigationDrawerFragment.NavigationDrawerFragment;
import rta.ae.sharekni.OnBoardDir.OnboardingActivity;


public class HomePage extends AppCompatActivity implements View.OnClickListener {


    public static String ImagePhotoPath;
    public Thread t;
    static HomePage HomaPageActivity;
    String ID;
    String AccountType;
    CircularImageView circularImageView;
    TextView name, nat, Lnag_home;
    SharedPreferences myPrefs;
    RelativeLayout btn_create, btn_history;
    int Vehicles_Count_FLAG = 0;

    int Driver_ID;
    RelativeLayout Relative_quickSearch;
    TextView VehiclesCount, PassengerJoinedRidesCount, DriverMyRidesCount, DriverMyAlertsCount;
    RelativeLayout Home_Relative_Notify;
    String DriverMyRidesCount_str, PassengerJoinedRidesCount_str, VehiclesCount_str;
    Toolbar toolbar;
    RelativeLayout Home_Relative_Permit, Home_Realtive_Vehicles, driver_rides_Created;
    RelativeLayout Rides_joined_Relative;
    String name_str, nat_str;
    int DRIVER_ALERTS_COUNT = 0;
    int y;
    int All_Alerts;
    String Firstname, LastName;
    Activity c;
    TextView Rides_joined_txt_1;
    ImageView Rides_joined_image_1;
    ImageView Edit_Profile_Im;
    TextView Saved_Search_txt_2;
    TextView rating;
    ImageView Saved_Search_image_2;
    String Locale_Str;

    Tracker mTracker;


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


        // Obtain the shared Tracker instance.
        AppController application = (AppController) getApplication();
        mTracker = application.getDefaultTracker();



        setContentView(R.layout.home_page_approved);
        initToolbar();
        c = this;

        name = (TextView) findViewById(R.id.tv_name_home);
        nat = (TextView) findViewById(R.id.nat_home);
        Lnag_home = (TextView) findViewById(R.id.lang_Home);
        rating = (TextView) findViewById(R.id.textView);
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
        Rides_joined_Relative = (RelativeLayout) findViewById(R.id.Rides_joined_Relative);
        btn_history = (RelativeLayout) findViewById(R.id.home_history);
        Rides_joined_txt_1 = (TextView) findViewById(R.id.txt_55);
        Rides_joined_image_1 = (ImageView) findViewById(R.id.im10);
        Saved_Search_image_2 = (ImageView) findViewById(R.id.im13);
        Saved_Search_txt_2 = (TextView) findViewById(R.id.txt_56);
        Edit_Profile_Im = (ImageView) findViewById(R.id.Edit_Profile_Im);

        circularImageView = (CircularImageView) findViewById(R.id.profilepic);
        circularImageView.setBorderWidth(5);
        circularImageView.setSelectorStrokeWidth(5);
        circularImageView.addShadow();


        NavigationDrawerFragment.navy_Change_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Locale locale = Locale.getDefault();
                Locale_Str = locale.toString();
                Log.d("test locale", Locale_Str);


                if (Locale_Str.contains("en")) {

                    Locale locale2 = new Locale("ar");
                    Locale.setDefault(locale2);
                    Configuration config2 = new Configuration();
                    config2.locale = locale2;
                    getApplicationContext().getResources().updateConfiguration(config2, null);
                    finish();
                    startActivity(getIntent());


                } else {


                    Locale locale3 = new Locale("en");
                    Locale.setDefault(locale3);
                    Configuration config3 = new Configuration();
                    config3.locale = locale3;
                    getApplicationContext().getResources().updateConfiguration(config3, null);
                    finish();
                    startActivity(getIntent());
                }


//                new AlertDialog.Builder(HomePage.this)
//                        .setTitle(R.string.choose_language)
//                        .setMessage(R.string.choose_language_mss)
//                        .setPositiveButton("English", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                Locale locale = new Locale("en_US");
//                                Locale.setDefault(locale);
//                                Configuration config = new Configuration();
//                                config.locale = locale;
//                                getBaseContext().getApplicationContext().getResources().updateConfiguration(config, null);
//                                finish();
//                                startActivity(getIntent());
//                            }
//                        })
//                        .setNegativeButton("العربية", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                Locale locale = new Locale("ar");
//                                Locale.setDefault(locale);
//                                Configuration config = new Configuration();
//                                config.locale = locale;
//                                getBaseContext().getApplicationContext().getResources().updateConfiguration(config, null);
//                                finish();
//                                startActivity(getIntent());
//                            }
//                        }).setIcon(android.R.drawable.ic_dialog_alert).show();


            }
        });
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


    private class refresh extends AsyncTask {

        JSONObject jsonArray;

        @Override
        protected void onPostExecute(Object o) {
            VehiclesCount_str = "";
            VehiclesCount_str += "(";
            try {
                All_Alerts = jsonArray.getInt("DriverMyAlertsCount") + jsonArray.getInt("PassengerMyAlertsCount");

                Vehicles_Count_FLAG = jsonArray.getInt("VehiclesCount");
                Log.d("vehicle count flag", String.valueOf(Vehicles_Count_FLAG));

                VehiclesCount_str += jsonArray.getString("VehiclesCount");
                VehiclesCount_str += ")";
                VehiclesCount.setText(VehiclesCount_str);
                PassengerJoinedRidesCount_str = "";
                PassengerJoinedRidesCount_str += "(";
                PassengerJoinedRidesCount_str += jsonArray.getString("PassengerJoinedRidesCount");
                PassengerJoinedRidesCount_str += ")";
                PassengerJoinedRidesCount.setText(PassengerJoinedRidesCount_str);
                rating.setText(jsonArray.getString("AccountRating"));
                if (DRIVER_ALERTS_COUNT < All_Alerts) {
                    DRIVER_ALERTS_COUNT = All_Alerts;
                    CreateNotification(y++);
                }
                DriverMyRidesCount_str = "";
                DriverMyRidesCount_str += "(";
                DriverMyRidesCount_str += jsonArray.getString("DriverMyRidesCount");
                DriverMyRidesCount_str += ")";
                DriverMyRidesCount.setText(DriverMyRidesCount_str);
                DriverMyAlertsCount.setText(String.valueOf(All_Alerts));

//                if (DRIVER_ALERTS_COUNT>0){
//
//
//
//                }


                DriverMyAlertsCount.setText(String.valueOf(All_Alerts));

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
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject j = jsonArray.getJSONObject(i);
                Intent intent = new Intent(this, DriverAlertsForRequest.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                Notification.Builder builder = new Notification.Builder(this);
                builder.setContentTitle(getString(R.string.route) + j.getString("RouteName"));
                builder.setContentText(j.getString("PassengerName") + getString(R.string.passenger_send_you_request));
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

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject j = jsonArray.getJSONObject(i);
                if (j.getString("DriverAccept").equals("false")) {

                    Intent intent = new Intent(this, DriverAlertsForRequest.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                    Notification.Builder builder = new Notification.Builder(this);
                    builder.setContentTitle(getString(R.string.route) + j.getString("RouteName"));
                    builder.setContentText(j.getString("DriverName") + getString(R.string.reject_your_request));
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
                    builder.setContentTitle(getString(R.string.route) + j.getString("RouteName"));
                    builder.setContentText(j.getString("DriverName") + getString(R.string.accept_your_request));
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


//
//        if (id==R.id.Stop_Service){
//
//            Toast.makeText(HomePage.this, "Hello", Toast.LENGTH_SHORT).show();
//
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
    protected void onResume() {
        super.onResume();

        Log.i("Home Page", "Setting screen name: " + name);
        mTracker.setScreenName("Image~" + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
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
            } else {

                Intent intent = new Intent(getBaseContext(), PassengerMyApprovedRides.class);
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
        textView.setText(R.string.home_page);
//        toolbar.setElevation(10);

        setSupportActionBar(toolbar);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawwer);
        drawerFragment.setUp(R.id.fragment_navigation_drawwer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

    }

    private class loading extends AsyncTask {

        ProgressDialog pDialog;
        JSONObject jsonArray;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(HomePage.this);
            pDialog.setMessage(getString(R.string.loading) + "...");
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            try {
                All_Alerts = jsonArray.getInt("DriverMyAlertsCount") + jsonArray.getInt("PassengerMyAlertsCount");
                name_str = "";
                nat_str = "";
                Firstname = "";
                LastName = "";
                Firstname = (jsonArray.getString("FirstName"));
                Firstname = Firstname.substring(0, 1).toUpperCase() + Firstname.substring(1);
                LastName = (jsonArray.getString("LastName"));
                LastName = LastName.substring(0, 1).toUpperCase() + LastName.substring(1);
                name_str = Firstname + " " + LastName;
                //  name_str = (jsonArray.getString("FirstName") + " " + jsonArray.getString("LastName"));
                nat_str = (jsonArray.getString(getString(R.string.nat_name2)));
                //   name_str=  name_str.substring(0, 1).toUpperCase() + name_str.substring(1);
                nat_str = nat_str.substring(0, 1).toUpperCase() + nat_str.substring(1);
                NavigationDrawerFragment.tv_name_home.setText(name_str);
                name.setText(name_str);
                nat.setText(nat_str);
                NavigationDrawerFragment.nat_home.setText(nat_str);
                //   str.substring(0, 1).toUpperCase() + str.substring(1);
                Vehicles_Count_FLAG = jsonArray.getInt("VehiclesCount");
                Log.d("vehicle count flag", String.valueOf(Vehicles_Count_FLAG));
                VehiclesCount_str = "";
                VehiclesCount_str += "(";
                VehiclesCount_str += (jsonArray.getString("VehiclesCount"));
                VehiclesCount_str += ")";
                VehiclesCount.setText(VehiclesCount_str);
                PassengerJoinedRidesCount_str = "";
                PassengerJoinedRidesCount_str += "(";
                PassengerJoinedRidesCount_str += (jsonArray.getString("PassengerJoinedRidesCount"));
                PassengerJoinedRidesCount_str += ")";
                PassengerJoinedRidesCount.setText(PassengerJoinedRidesCount_str);
                DriverMyRidesCount_str = "";
                DriverMyRidesCount_str += "(";
                DriverMyRidesCount_str += (jsonArray.getString("DriverMyRidesCount"));
                DriverMyRidesCount_str += ")";
                DriverMyRidesCount.setText(DriverMyRidesCount_str);
                DriverMyAlertsCount.setText(String.valueOf(All_Alerts));
                rating.setText(jsonArray.getString("AccountRating"));
                assert AccountType != null;
                if (!AccountType.equals("D")) {
                    // btn_create.setBackgroundColor(Color.LTGRAY);
//                    Home_Relative_Permit.setBackgroundColor(Color.LTGRAY);
                    Rides_joined_txt_1.setText(R.string.rides_joined);
                    Rides_joined_image_1.setImageResource(R.drawable.joinedusertype);

                    Saved_Search_txt_2.setText(R.string.saved_search);
                    Saved_Search_image_2.setImageResource(R.drawable.homesavedsearchbtn);

                    Rides_joined_Relative.setVisibility(View.INVISIBLE);

                    NavigationDrawerFragment.Convert_txt_id.setText(getString(R.string.saved_search));
                    NavigationDrawerFragment.menuicon12.setImageResource(R.drawable.menusavesearchicon);


                    btn_create.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(getBaseContext(), PassengerMyApprovedRides.class);
                            startActivity(intent);

                        }
                    });

                    Home_Relative_Permit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), History.class);
                            startActivity(intent);

                        }
                    });


                    NavigationDrawerFragment.navy_My_vehicles.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), History.class);
                            startActivity(intent);
                        }
                    });


                    Home_Realtive_Vehicles.setVisibility(View.INVISIBLE);
                    driver_rides_Created.setVisibility(View.INVISIBLE);
                } else {


                    NavigationDrawerFragment.navy_My_vehicles.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Vehicles_Count_FLAG == 0) {

                                Intent intent = new Intent(getBaseContext(), RegisterVehicle.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getBaseContext(), Display_My_Vehicles.class);
                                startActivity(intent);

                            }
                        }
                    });


                    Home_Relative_Permit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), DriverPermits.class);
                            startActivity(intent);

                        }
                    });


                    Home_Realtive_Vehicles.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("vehicle flag count", String.valueOf(Vehicles_Count_FLAG));

                            if (Vehicles_Count_FLAG == 0) {

                                Intent intent = new Intent(getBaseContext(), RegisterVehicle.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getBaseContext(), Display_My_Vehicles.class);
                                startActivity(intent);

                            }


//                            final Dialog dialog = new Dialog(c);
//                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                            dialog.setContentView(R.layout.noroutesdialog);
//                            Button btn = (Button) dialog.findViewById(R.id.noroute_id);
//                            TextView Text_3 = (TextView) dialog.findViewById(R.id.Text_3);
//                            dialog.show();
//                            Text_3.setText("This service is temporary unavailable. Please check again later");
//
//                            btn.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.dismiss();
//
//                                }
//                            });
//

                        }
                    });

                }
                if (jsonArray.getString("GenderEn").equals("Female")) {
                    circularImageView.setImageResource(R.drawable.defaultdriverfemale);
                }
//                GetData gd = new GetData();
//                gd.SetImage(circularImageView,jsonArray.getString("PhotoPath"));
//                circularImageView.setImageBitmap(gd.GetImage(jsonArray.getString("PhotoPath")));
//                circularImageView.setImageURI(Uri.parse(GetData.PhotoURL));
                ImageDecoder im = new ImageDecoder();
                im.stringRequest(jsonArray.getString("PhotoPath"), circularImageView, HomePage.this);
                im.stringRequest(jsonArray.getString("PhotoPath"), NavigationDrawerFragment.circularImageView, HomePage.this);
                ImagePhotoPath = jsonArray.getString("PhotoPath");
            } catch (JSONException e) {
                hidePDialog();
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            btn_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), HistoryNew.class);
                    startActivity(intent);

                }
            });

            Edit_Profile_Im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), EditProfileTest.class);
                    startActivity(intent);
                }
            });

            Home_Relative_Notify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Action")
                            .setAction("Share")
                            .build());

                    Intent intent = new Intent(getBaseContext(), DriverAlertsForRequest.class);
                    startActivity(intent);

                }
            });

            Relative_quickSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getBaseContext(), QSearch.class);
                    intent.putExtra("PassengerId", ID);
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
                        Toast.makeText(HomePage.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
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
