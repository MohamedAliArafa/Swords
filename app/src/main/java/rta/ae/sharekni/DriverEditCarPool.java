package rta.ae.sharekni;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;

import rta.ae.sharekni.Arafa.Classes.GetData;


public class DriverEditCarPool extends AppCompatActivity implements View.OnClickListener {


    Double Start_Latitude = 0.0, Start_Longitude = 0.0, End_Latitude = 0.0, End_Longitude = 0.0;

    int id = 1;
    int Single_Periodic_ID = 0;
    int From_Em_Id = -1;
    int From_Reg_Id = -1;
    int To_Em_Id = -1;
    int To_Reg_Id = -1;
    int year_x, month_x, day_x;
    int RouteId = -1;
    int Nationality_ID = 0;
    int Vehicle_Id = -1;
    int Age_ID = 0;
    int Language_ID = 0;
    int SAT_FLAG = 0;
    int SUN_FLAG = 0;
    int MON_FLAG = 0;
    int TUES_FLAG = 0;
    int WED_FLAG = 0;
    int THU_FLAG = 0;
    int FRI_FLAG = 0;

    String Start_From_Date_str = "";

    char gender = 'N';
    String To_EmirateEnName, From_EmirateEnName, To_RegionEnName, From_RegionEnName;

    List<TreeMap<String, String>> Create_CarPool_Emirates_List = new ArrayList<>();
    List<TreeMap<String, String>> Create_CarPool_Regions_List = new ArrayList<>();
    List<TreeMap<String, String>> Create_CarPool_Country_List = new ArrayList<>();
    List<TreeMap<String, String>> Create_CarPool_Lang_List = new ArrayList<>();
    List<TreeMap<String, String>> Create_CarPool_AgeRanges_List = new ArrayList<>();
    List<TreeMap<String, String>> Create_CarPool_Vehicles_List = new ArrayList<>();

    private Toolbar toolbar;
    static final int DILOG_ID = 0;
    static final int TIME_DIALOG_ID = 999;
    private int hour;
    private int minute;
    int MyId = -1;

    Dialog Create_CarPool_MainDialog;
    Dialog Languages_Dilaog;
    AutoCompleteTextView Create_CarPool_txt_regions;
    AutoCompleteTextView Create_CarPool_search_Nat;
    Spinner Create_CarPool_spinner;
    RelativeLayout Create_CarPool_pickup_relative;
    RelativeLayout Create_CarPool_dropOff_relative;
    RelativeLayout Create_CarPool_calendar_relative;
    RelativeLayout Create_CarPool_time_relative;
    RelativeLayout createCarPool_Sat_Day;
    RelativeLayout createCarPool_Sun_Day;
    RelativeLayout createCarPool_Mon_Day;
    RelativeLayout createCarPool_Tues_Day;
    RelativeLayout createCarPool_Wed_Day;
    RelativeLayout createCarPool_Thu_Day;
    RelativeLayout createCarPool_Fri_Day;
    SimpleAdapter Create_CarPool_EmAdapter;
    Button Create_CarPool_btn_submit_pickUp;
    TextView Create_CarPool_pickUp;
    TextView Create_CarPool_Dropoff;
    Button create;
    String Create_CarPool_txt_PickUp;
    String Create_CarPool_txt_Drop_Off;
    String Create_CarPool_full_date="";
    String Route_Name;
    EditText edit_route_name;
    TextView Create_CarPool_txt_Selecet_Start_Point;
    TextView Create_CarPool_txt_Select_Dest;
    TextView Create_CarPool_txt_year;
    TextView Create_CarPool_txt_beforeCal;
    TextView Create_CarPool_txt_time_selected;
    TextView Create_CarPool_before_Time;
    TextView maleFemaleTxt;
    TextView FemaleMaleTxt;
    TextView maleFemaleTxt2;
    TextView FemaleMaleTxt2;
    TextView Create_CarPool_Preferred_Lang_txt;
    TextView Create_CarPool_Age_Range_txt;
    TextView createCarPool_Vehicles;
    String Vehicle_Name_str;
    TextView txt_em_name;
    TextView txt_em_id;
    ListView lang_lv;
    Context mContext;
    ImageView seat1_off, seat2_off, seat3_off, seat4_off;
    ImageView Periodic_SingleRide, singleRide_Periodic, Create_CarPool_malefemale1, Create_CarPool_femalemale2;
    ImageView seat1_on, seat2_on, seat3_on, seat4_on;
    SharedPreferences myPrefs;
    String str_StartFromTime;
    String str_StartFromDate;
    String StartDate;

    getAgeRanges ageRanges;
    getLanguages languages;
    getNationalities nationalities;
    getVehicles vehicles;
    load load;
    int Number_Of_Seats = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        setContentView(R.layout.activity_driver_edit_car_pool);
        showDialogOnButtonClick();
        showTimeDialogOnButtonClick();
        mContext = this;

        initToolbar();

        myPrefs = this.getSharedPreferences("myPrefs", 0);
        String ID = myPrefs.getString("account_id", null);
        MyId = Integer.parseInt(ID);

        Intent in = getIntent();
        RouteId = in.getIntExtra("RouteID", -1);
        Route_Name = in.getStringExtra("RoutName");

        edit_route_name = (EditText) findViewById(R.id.createCarPool_EnName);

        Create_CarPool_txt_year = (TextView) findViewById(R.id.createCarPool_search_txt_yaer);
        Create_CarPool_txt_beforeCal = (TextView) findViewById(R.id.createCarPool_textview50);
        Create_CarPool_pickup_relative = (RelativeLayout) findViewById(R.id.createCarPool_pickup_relative);
        Create_CarPool_txt_Selecet_Start_Point = (TextView) findViewById(R.id.createCarPool_txt_Selecet_Start_Point);
        Create_CarPool_dropOff_relative = (RelativeLayout) findViewById(R.id.createCarPool_dropOff_relative);
        Create_CarPool_txt_Select_Dest = (TextView) findViewById(R.id.createCarPool_txt_Select_Dest);
        Create_CarPool_txt_time_selected = (TextView) findViewById(R.id.createCarPool_txt_time_selected);
        Create_CarPool_before_Time = (TextView) findViewById(R.id.createCarPool_textview51);
        create = (Button) findViewById(R.id.createCarPool);
        create.setText(getString(R.string.Save_Changes_Edit_Carpool));

        Periodic_SingleRide = (ImageView) findViewById(R.id.createCarPool_Periodic_SingleRide);
        singleRide_Periodic = (ImageView) findViewById(R.id.createCarPool_singleRide_Periodic);
        Create_CarPool_malefemale1 = (ImageView) findViewById(R.id.createCarPool_malefemale1);
        Create_CarPool_femalemale2 = (ImageView) findViewById(R.id.createCarPool_femalemale2);
        Create_CarPool_search_Nat = (AutoCompleteTextView) findViewById(R.id.createCarPool_search_Nat);

        maleFemaleTxt = (TextView) findViewById(R.id.createCarPool_malefemale_txt);
        FemaleMaleTxt = (TextView) findViewById(R.id.createCarPool_femalemale_txt);
        maleFemaleTxt2 = (TextView) findViewById(R.id.createCarPool_malefemale_txt2);
        FemaleMaleTxt2 = (TextView) findViewById(R.id.createCarPool_femalemale_txt2);

        Create_CarPool_Preferred_Lang_txt = (TextView) findViewById(R.id.createCarPool_Preferred_Lang_txt);
        Create_CarPool_Age_Range_txt = (TextView) findViewById(R.id.createCarPool_Age_Range_txt);
        Create_CarPool_pickUp = (TextView) findViewById(R.id.createCarPool_pickUp);
        Create_CarPool_Dropoff = (TextView) findViewById(R.id.createCarPool_search__Dropoff);

        createCarPool_Sat_Day = (RelativeLayout) findViewById(R.id.createCarPool_Sat_Day);
        createCarPool_Sun_Day = (RelativeLayout) findViewById(R.id.createCarPool_Sun_Day);
        createCarPool_Mon_Day = (RelativeLayout) findViewById(R.id.createCarPool_Mon_Day);
        createCarPool_Tues_Day = (RelativeLayout) findViewById(R.id.createCarPool_Tues_Day);
        createCarPool_Wed_Day = (RelativeLayout) findViewById(R.id.createCarPool_Wed_Day);
        createCarPool_Thu_Day = (RelativeLayout) findViewById(R.id.createCarPool_Thu_Day);
        createCarPool_Fri_Day = (RelativeLayout) findViewById(R.id.createCarPool_Fri_Day);

        createCarPool_Sat_Day.setOnClickListener(this);
        createCarPool_Sun_Day.setOnClickListener(this);
        createCarPool_Mon_Day.setOnClickListener(this);
        createCarPool_Tues_Day.setOnClickListener(this);
        createCarPool_Wed_Day.setOnClickListener(this);
        createCarPool_Thu_Day.setOnClickListener(this);
        createCarPool_Fri_Day.setOnClickListener(this);

        seat1_off = (ImageView) findViewById(R.id.seat1_off);
        seat2_off = (ImageView) findViewById(R.id.seat2_off);
        seat3_off = (ImageView) findViewById(R.id.seat3_off);
        seat4_off = (ImageView) findViewById(R.id.seat4_off);

        seat1_on = (ImageView) findViewById(R.id.seat1_on);
        seat2_on = (ImageView) findViewById(R.id.seat2_on);
        seat3_on = (ImageView) findViewById(R.id.seat3_on);
        seat4_on = (ImageView) findViewById(R.id.seat4_on);


        createCarPool_Vehicles = (TextView) findViewById(R.id.createCarPool_Vehicles);

        create.setOnClickListener(this);


        singleRide_Periodic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleRide_Periodic.setVisibility(View.INVISIBLE);
                Periodic_SingleRide.setVisibility(View.VISIBLE);
                Single_Periodic_ID = 1;
                maleFemaleTxt2.setTextColor(Color.GRAY);
                FemaleMaleTxt2.setTextColor(Color.RED);
            }
        });

        Periodic_SingleRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Periodic_SingleRide.setVisibility(View.INVISIBLE);
                singleRide_Periodic.setVisibility(View.VISIBLE);
                Single_Periodic_ID = 0;
                maleFemaleTxt2.setTextColor(Color.RED);
                FemaleMaleTxt2.setTextColor(Color.GRAY);
            }
        });

        FemaleMaleTxt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleFemaleTxt2.setTextColor(Color.GRAY);
                FemaleMaleTxt2.setTextColor(Color.RED);
                Periodic_SingleRide.setVisibility(View.VISIBLE);
                singleRide_Periodic.setVisibility(View.INVISIBLE);
            }
        });

        maleFemaleTxt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleFemaleTxt2.setTextColor(Color.RED);
                FemaleMaleTxt2.setTextColor(Color.GRAY);
                Periodic_SingleRide.setVisibility(View.INVISIBLE);
                singleRide_Periodic.setVisibility(View.VISIBLE);
            }
        });

        //Create_CarPool
        Create_CarPool_malefemale1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = 'F';
                Create_CarPool_malefemale1.setVisibility(View.INVISIBLE);
                Create_CarPool_femalemale2.setVisibility(View.VISIBLE);
                maleFemaleTxt.setTextColor(Color.GRAY);
                FemaleMaleTxt.setTextColor(Color.RED);
                Log.d("gender", String.valueOf(gender));
            }
        });

        Create_CarPool_femalemale2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = 'M';
                Create_CarPool_femalemale2.setVisibility(View.INVISIBLE);
                Create_CarPool_malefemale1.setVisibility(View.VISIBLE);
                maleFemaleTxt.setTextColor(Color.RED);
                FemaleMaleTxt.setTextColor(Color.GRAY);
                Log.d("gender", String.valueOf(gender));

            }
        });

        FemaleMaleTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = 'F';
                maleFemaleTxt.setTextColor(Color.GRAY);
                FemaleMaleTxt.setTextColor(Color.RED);
                Create_CarPool_malefemale1.setVisibility(View.INVISIBLE);
                Create_CarPool_femalemale2.setVisibility(View.VISIBLE);
                Log.d("gender", String.valueOf(gender));
            }
        });

        maleFemaleTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = 'M';
                maleFemaleTxt.setTextColor(Color.RED);
                FemaleMaleTxt.setTextColor(Color.GRAY);
                Create_CarPool_malefemale1.setVisibility(View.VISIBLE);
                Create_CarPool_femalemale2.setVisibility(View.INVISIBLE);
                Log.d("gender", String.valueOf(gender));

            }
        });


        seat1_on.setOnClickListener(this);
        seat2_on.setOnClickListener(this);
        seat3_on.setOnClickListener(this);
        seat4_on.setOnClickListener(this);
        seat1_off.setOnClickListener(this);
        seat2_off.setOnClickListener(this);
        seat3_off.setOnClickListener(this);
        seat4_off.setOnClickListener(this);

        load = new load();

        load.execute();

        // Create_CarPool
        //  code to get nationals and set the adapter to the autotext complete

        nationalities = new getNationalities();

        nationalities.execute();

        //Toast.makeText(RegisterNewTest.this, "test pref lang" +Lang_List.toString(), Toast.LENGTH_LONG).show();
        Log.d("test pref lang  2 :", Create_CarPool_Country_List.toString());

        // code to get Vehicles and set it to txt view

        vehicles = new getVehicles();

        vehicles.execute();

        //Toast.makeText(RegisterNewTest.this, "test pref lang" +Lang_List.toString(), Toast.LENGTH_LONG).show();
        Log.d("test pref lang  2 :", Create_CarPool_Vehicles_List.toString());


        //Create_CarPool
        // code to get Languages and set it to the SPinner

        languages = new getLanguages();

        languages.execute();

        //Toast.makeText(RegisterNewTest.this, "test pref lang" +Lang_List.toString(), Toast.LENGTH_LONG).show();
        Log.d("test pref lang  2 :", Create_CarPool_Lang_List.toString());

        //Create_CarPool
        // get age ranges and set it to the spineer

        ageRanges = new getAgeRanges();

        ageRanges.execute();

        //Toast.makeText(RegisterNewTest.this, "test pref lang" +Lang_List.toString(), Toast.LENGTH_LONG).show();
        Log.d("test pref lang  2 :", Create_CarPool_AgeRanges_List.toString());
    }

    private class load extends AsyncTask {
        JSONObject j;

        @Override
        protected void onPostExecute(Object o) {
            try {
                edit_route_name.setText(j.getString(getString(R.string.route_name)));
                Create_CarPool_txt_Selecet_Start_Point.setText(j.getString(getString(R.string.from_em_en_name)) + " , " + j.getString(getString(R.string.from_reg_en_name)));
                Create_CarPool_txt_Select_Dest.setText(j.getString(getString(R.string.to_em_en_name)) + " , " + j.getString(getString(R.string.to_reg_en_name)));
                From_Em_Id = j.getInt("FromEmirateId");
                From_Reg_Id = j.getInt("FromRegionId");
                To_Em_Id = j.getInt("ToEmirateId");
                To_Reg_Id = j.getInt("ToRegionId");

                Start_Latitude = j.getDouble("StartLat");
                Start_Longitude = j.getDouble("StartLng");
                End_Latitude = j.getDouble("EndLat");
                End_Longitude = j.getDouble("EndLng");

//                Create_CarPool_txt_year.setText("Not Changed");
                Vehicle_Id = j.getInt("VehicelId");

                Vehicle_Name_str = j.getString(getString(R.string.VehicleName_Edit_CarPool));

                if (!Vehicle_Name_str.equals("null") && !Vehicle_Name_str.equals("")) {
                    createCarPool_Vehicles.setText(Vehicle_Name_str);
                } else {
                    createCarPool_Vehicles.setText(getString(R.string.select_vehicle));
                }

                if( !j.getString("NationalityId").equals("null")  &&  !j.getString("NationalityId").equals("")  ){
                    Nationality_ID = j.getInt("NationalityId");
                }else {
                    Nationality_ID = 0;
                }

                if (j.getString("StartLat").equals("") && j.getString("StartLng").equals("") || j.getString("StartLat").equals("0.0") && j.getString("StartLng").equals("0.0")){
                    new FromLongLat().execute();
                }

                if (j.getString("EndLat").equals("")  &&  !j.getString("EndLng").equals("") || j.getString("EndLat").equals("0.0")  &&  !j.getString("EndLng").equals("0.0")){
                    new ToLongLat().execute();
                }

                Language_ID = j.getInt("PrefLanguageId");

                Age_ID = j.getInt("AgeRangeID");

                if (j.getString(getString(R.string.nat_name2)).equals("null")) {
                    Create_CarPool_search_Nat.setText(getString(R.string.select_pref_nat));
                } else {
                    Create_CarPool_search_Nat.setText(j.getString(getString(R.string.nat_name2)));

                }

                if (j.getString(getString(R.string.pref_lang)).equals("null")) {

                    Create_CarPool_Preferred_Lang_txt.setText(getString(R.string.select_pref_lang));
                } else {
                    Create_CarPool_Preferred_Lang_txt.setText(j.getString(getString(R.string.pref_lang)));
                }

                if (j.getString("AgeRange").equals("null")) {
                    Create_CarPool_Age_Range_txt.setText(getString(R.string.choose_age));
                } else {
                    Create_CarPool_Age_Range_txt.setText(j.getString("AgeRange"));

                }


                if (j.getInt("NoOfSeats") == 1) {
                    seat1_on.setVisibility(View.VISIBLE);
                    seat1_off.setVisibility(View.INVISIBLE);
                    Number_Of_Seats = 1;
                    id = 2;
                }
                if (j.getInt("NoOfSeats") == 2) {
                    seat1_on.setVisibility(View.VISIBLE);
                    seat1_off.setVisibility(View.INVISIBLE);
                    seat2_off.setVisibility(View.INVISIBLE);
                    seat2_on.setVisibility(View.VISIBLE);
                    Number_Of_Seats = 2;
                    id = 3;
                }
                if (j.getInt("NoOfSeats") == 3) {
                    seat1_on.setVisibility(View.VISIBLE);
                    seat1_off.setVisibility(View.INVISIBLE);
                    seat2_off.setVisibility(View.INVISIBLE);
                    seat2_on.setVisibility(View.VISIBLE);
                    seat3_off.setVisibility(View.INVISIBLE);
                    seat3_on.setVisibility(View.VISIBLE);
                    Number_Of_Seats = 3;
                    id = 4;
                }
                if (j.getInt("NoOfSeats") == 4) {
                    seat1_on.setVisibility(View.VISIBLE);
                    seat1_off.setVisibility(View.INVISIBLE);
                    seat2_off.setVisibility(View.INVISIBLE);
                    seat2_on.setVisibility(View.VISIBLE);
                    seat3_off.setVisibility(View.INVISIBLE);
                    seat3_on.setVisibility(View.VISIBLE);
                    seat4_off.setVisibility(View.INVISIBLE);
                    seat4_on.setVisibility(View.VISIBLE);
                    Number_Of_Seats = 4;
                    id = 5;
                }


                if (j.getString("PreferredGender").equals("M")) {
                    gender = 'M';
                    maleFemaleTxt.setTextColor(Color.RED);
                    FemaleMaleTxt.setTextColor(Color.GRAY);
                    Create_CarPool_malefemale1.setVisibility(View.INVISIBLE);
                    Create_CarPool_femalemale2.setVisibility(View.VISIBLE);
                } else if (j.getString("PreferredGender").equals("F")) {
                    gender = 'F';
                    maleFemaleTxt.setTextColor(Color.GRAY);
                    FemaleMaleTxt.setTextColor(Color.RED);
                    Create_CarPool_malefemale1.setVisibility(View.INVISIBLE);
                    Create_CarPool_femalemale2.setVisibility(View.VISIBLE);
                }

                if (j.getBoolean("Saturday")) {
                    createCarPool_Sat_Day.setBackgroundResource(R.drawable.days_of_week_circular_on);
                    SAT_FLAG = 1;
                } else {
                    createCarPool_Sat_Day.setBackgroundResource(R.drawable.days_of_week_circular_off);
                    SAT_FLAG = 0;
                }
                if (j.getBoolean("Sunday")) {
                    createCarPool_Sun_Day.setBackgroundResource(R.drawable.days_of_week_circular_on);
                    SUN_FLAG = 1;
                } else {
                    createCarPool_Sun_Day.setBackgroundResource(R.drawable.days_of_week_circular_off);
                    SUN_FLAG = 0;
                }
                if (j.getBoolean("Monday")) {
                    createCarPool_Mon_Day.setBackgroundResource(R.drawable.days_of_week_circular_on);
                    MON_FLAG = 1;
                } else {
                    createCarPool_Mon_Day.setBackgroundResource(R.drawable.days_of_week_circular_off);
                    MON_FLAG = 0;
                }
                if (j.getBoolean("Tuesday")) {
                    createCarPool_Tues_Day.setBackgroundResource(R.drawable.days_of_week_circular_on);
                    TUES_FLAG = 1;
                } else {
                    createCarPool_Tues_Day.setBackgroundResource(R.drawable.days_of_week_circular_off);
                    TUES_FLAG = 0;
                }
                if (j.getBoolean("Wednesday")) {
                    createCarPool_Wed_Day.setBackgroundResource(R.drawable.days_of_week_circular_on);
                    WED_FLAG = 1;
                } else {
                    createCarPool_Wed_Day.setBackgroundResource(R.drawable.days_of_week_circular_off);
                    WED_FLAG = 0;
                }
                if (j.getBoolean("Thursday")) {
                    createCarPool_Thu_Day.setBackgroundResource(R.drawable.days_of_week_circular_on);
                    THU_FLAG = 1;
                } else {
                    createCarPool_Thu_Day.setBackgroundResource(R.drawable.days_of_week_circular_off);
                    THU_FLAG = 0;
                }
                if (j.getBoolean("Friday")) {
                    createCarPool_Fri_Day.setBackgroundResource(R.drawable.days_of_week_circular_on);
                    FRI_FLAG = 1;
                } else {
                    createCarPool_Fri_Day.setBackgroundResource(R.drawable.days_of_week_circular_off);
                    FRI_FLAG = 0;
                }

                str_StartFromTime = j.getString("StartFromTime");
                str_StartFromDate = j.getString("StartFromTime");

                str_StartFromDate = str_StartFromDate.substring(0, str_StartFromDate.length() - 8);
                Log.d("Date 1 ", str_StartFromDate);

                str_StartFromTime = str_StartFromTime.substring(Math.max(0, str_StartFromTime.length() - 7));
                Log.d("string", str_StartFromTime);

                if (!str_StartFromTime.equals("") && !str_StartFromTime.equals("null")) {

                    Create_CarPool_before_Time.setVisibility(View.INVISIBLE);
                    Create_CarPool_txt_time_selected.setText(str_StartFromTime);

                }


                Start_From_Date_str = j.getString("StartDate");
                Start_From_Date_str = Start_From_Date_str.replace('-', '/');
                Log.d("date 1 ", Start_From_Date_str);

                if (!Start_From_Date_str.equals("") && !Start_From_Date_str.equals("null")) {

                    Create_CarPool_txt_year.setText(Start_From_Date_str);
                    Create_CarPool_txt_beforeCal.setVisibility(View.INVISIBLE);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                j = new GetData().GetRouteById(RouteId);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    private class getNationalities extends AsyncTask {

        @Override
        protected void onPostExecute(Object o) {
            SimpleAdapter adapterCountry = new SimpleAdapter(DriverEditCarPool.this, Create_CarPool_Country_List
                    , R.layout.autocomplete_row
                    , new String[]{"ID", "NationalityEnName"}
                    , new int[]{R.id.row_id, R.id.row_name});

            Create_CarPool_search_Nat.setAdapter(adapterCountry);
            Create_CarPool_search_Nat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView txt_lang_name = (TextView) view.findViewById(R.id.row_name);
                    TextView txt_lang_id = (TextView) view.findViewById(R.id.row_id);
                    Nationality_ID = Integer.parseInt(txt_lang_id.getText().toString());
                    Create_CarPool_search_Nat.setText(txt_lang_name.getText().toString());
                    Log.d("id of lang", "" + Nationality_ID);
                }
            });
        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                JSONArray j = new GetData().GetNationalities();
                for (int i = 0; i < j.length(); i++) {
                    TreeMap<String, String> valuePairs = new TreeMap<>();
                    JSONObject jsonObject = j.getJSONObject(i);
                    valuePairs.put("ID", jsonObject.getString("ID"));
                    valuePairs.put("NationalityEnName", jsonObject.getString(getString(R.string.nat_name2)));
                    Create_CarPool_Country_List.add(valuePairs);
                }
                //Toast.makeText(RegisterNewTest.this, "test pref lang" + Lang_List.toString(), Toast.LENGTH_LONG).show();
                Log.d("test pref lang", Create_CarPool_Country_List.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class getVehicles extends AsyncTask {

        @Override
        protected void onPostExecute(Object o) {
            final SimpleAdapter adapter3 = new SimpleAdapter(DriverEditCarPool.this, Create_CarPool_Vehicles_List
                    , R.layout.autocomplete_row
                    , new String[]{"ID", "ManufacturingEnName"}
                    , new int[]{R.id.row_id, R.id.row_name});

            createCarPool_Vehicles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Languages_Dilaog = new Dialog(mContext);
                    Languages_Dilaog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    Languages_Dilaog.setContentView(R.layout.languages_dialog);
                    TextView Lang_Dialog_txt_id = (TextView) Languages_Dilaog.findViewById(R.id.Lang_Dialog_txt_id);
                    Lang_Dialog_txt_id.setText("Vehicles");
                    lang_lv = (ListView) Languages_Dilaog.findViewById(R.id.Langs_list);
                    lang_lv.setAdapter(adapter3);
                    Languages_Dilaog.show();
                    lang_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TextView txt_lang_name = (TextView) view.findViewById(R.id.row_name);
                            TextView txt_lang_id = (TextView) view.findViewById(R.id.row_id);
                            Vehicle_Id = Integer.parseInt(txt_lang_id.getText().toString());
                            createCarPool_Vehicles.setText(txt_lang_name.getText().toString());
                            // Log.d("id of lang", "" + Language_ID);
                            Languages_Dilaog.dismiss();
                        }
                    });
                }
            });
        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                JSONArray j = new GetData().GetVehiclesForCreateCarPool(MyId);
                for (int i = 0; i < j.length(); i++) {
                    TreeMap<String, String> valuePairs = new TreeMap<>();
                    JSONObject jsonObject = j.getJSONObject(i);
                    valuePairs.put("ID", jsonObject.getString("ID"));
                    valuePairs.put("ManufacturingEnName", jsonObject.getString(getString(R.string.man_en_name)));
                    Create_CarPool_Vehicles_List.add(valuePairs);
                }
                //Toast.makeText(RegisterNewTest.this, "test pref lang" + Lang_List.toString(), Toast.LENGTH_LONG).show();
                Log.d("test pref lang", Create_CarPool_Vehicles_List.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class getLanguages extends AsyncTask {

        @Override
        protected void onPostExecute(Object o) {
            final SimpleAdapter adapter2 = new SimpleAdapter(DriverEditCarPool.this, Create_CarPool_Lang_List
                    , R.layout.autocomplete_row
                    , new String[]{"LanguageId", "LanguageEnName"}
                    , new int[]{R.id.row_id, R.id.row_name});

            Create_CarPool_Preferred_Lang_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Languages_Dilaog = new Dialog(mContext);
                    Languages_Dilaog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    Languages_Dilaog.setContentView(R.layout.languages_dialog);
                    lang_lv = (ListView) Languages_Dilaog.findViewById(R.id.Langs_list);
                    lang_lv.setAdapter(adapter2);
                    Languages_Dilaog.show();
                    lang_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TextView txt_lang_name = (TextView) view.findViewById(R.id.row_name);
                            TextView txt_lang_id = (TextView) view.findViewById(R.id.row_id);
                            Language_ID = Integer.parseInt(txt_lang_id.getText().toString());
                            Create_CarPool_Preferred_Lang_txt.setText(txt_lang_name.getText().toString());
                            // Log.d("id of lang", "" + Language_ID);
                            Languages_Dilaog.dismiss();
                        }
                    });
                }
            });
        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                JSONArray j = new GetData().GetPrefLanguage();
                for (int i = 0; i < j.length(); i++) {

                    TreeMap<String, String> valuePairs = new TreeMap<>();
                    JSONObject jsonObject = j.getJSONObject(i);
                    valuePairs.put("LanguageId", jsonObject.getString("LanguageId"));
                    valuePairs.put("LanguageEnName", jsonObject.getString(getString(R.string.lang_name)));
                    Create_CarPool_Lang_List.add(valuePairs);

                }
                //Toast.makeText(RegisterNewTest.this, "test pref lang" + Lang_List.toString(), Toast.LENGTH_LONG).show();
                Log.d("test pref lang", Create_CarPool_Lang_List.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class getAgeRanges extends AsyncTask {
        boolean exists = false;

        @Override
        protected void onPostExecute(Object o) {
            if (exists) {

                final SimpleAdapter AgeRangesAdapter = new SimpleAdapter(DriverEditCarPool.this, Create_CarPool_AgeRanges_List
                        , R.layout.autocomplete_row
                        , new String[]{"RangeId", "Range"}
                        , new int[]{R.id.row_id, R.id.row_name});

                Create_CarPool_Age_Range_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Languages_Dilaog = new Dialog(mContext);
                        Languages_Dilaog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        Languages_Dilaog.setContentView(R.layout.languages_dialog);
                        TextView Lang_Dialog_txt_id = (TextView) Languages_Dilaog.findViewById(R.id.Lang_Dialog_txt_id);
                        Lang_Dialog_txt_id.setText(R.string.Age_Ranges_str);
                        lang_lv = (ListView) Languages_Dilaog.findViewById(R.id.Langs_list);
                        lang_lv.setAdapter(AgeRangesAdapter);
                        Languages_Dilaog.show();
                        lang_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                TextView txt_lang_name = (TextView) view.findViewById(R.id.row_name);
                                TextView txt_lang_id = (TextView) view.findViewById(R.id.row_id);
                                Age_ID = Integer.parseInt(txt_lang_id.getText().toString());
                                Create_CarPool_Age_Range_txt.setText(txt_lang_name.getText().toString());
                                // Log.d("id of lang", "" + Language_ID);
                                Languages_Dilaog.dismiss();
                            }
                        });
                    }
                });
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
                        new AlertDialog.Builder(DriverEditCarPool.this)
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
                        Toast.makeText(DriverEditCarPool.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            if (exists) {
                try {
                    JSONArray j = new GetData().GetAgeRanges();
                    for (int i = 0; i < j.length(); i++) {
                        TreeMap<String, String> valuePairs = new TreeMap<>();
                        JSONObject jsonObject = j.getJSONObject(i);
                        valuePairs.put("RangeId", jsonObject.getString("RangeId"));
                        valuePairs.put("Range", jsonObject.getString("Range"));
                        Create_CarPool_AgeRanges_List.add(valuePairs);
                    }
                    //Toast.makeText(RegisterNewTest.this, "test pref lang" + Lang_List.toString(), Toast.LENGTH_LONG).show();
                    Log.d("test pref lang", Create_CarPool_AgeRanges_List.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_driver_create_car_pool, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }


    Calendar cal = Calendar.getInstance();
    DatePicker d;

    protected void onPrepareDialog(int id, Dialog dialog) {
        if (id == DILOG_ID) {
            DatePickerDialog datePickerDialog = (DatePickerDialog) dialog;
            // Get the current date
            datePickerDialog.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        } else if (id == TIME_DIALOG_ID) {

            TimePickerDialog timePickerDialog = (TimePickerDialog) dialog;
            timePickerDialog.updateTime(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));

        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DILOG_ID) {
            DatePickerDialog dp = new DatePickerDialog(this, dPickerListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            d = dp.getDatePicker();
            d.updateDate(year_x, month_x, day_x);
          //  d.setMaxDate(cal.getTimeInMillis());
            d.setMinDate(cal.getTimeInMillis());
            return dp;
        }
        if (id == TIME_DIALOG_ID) {
            return new TimePickerDialog(this,
                    timePickerListener, hour, minute, false);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dPickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;
            Create_CarPool_txt_beforeCal.setVisibility(View.INVISIBLE);
            // Toast.makeText(RegisterNewTest.this, "hi"+year_x+" :"+month_x+" :"+day_x, Toast.LENGTH_SHORT).show();
            String year_string = String.valueOf(year_x);
            String month_string = String.valueOf(month_x);
            String day_string = String.valueOf(day_x);
            Create_CarPool_full_date = day_string + "/" + month_string + "/" + year_string;
            Create_CarPool_txt_year.setText(Create_CarPool_full_date);
            Log.d("Calendar test", Create_CarPool_full_date);
        }
    };

    public void showDialogOnButtonClick() {
        Create_CarPool_calendar_relative = (RelativeLayout) findViewById(R.id.createCarPool_calendar_relative);
        Create_CarPool_calendar_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DILOG_ID);
            }
        });

    }


    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;
                    Create_CarPool_before_Time.setVisibility(View.INVISIBLE);
                    // set current time into textview
                    Create_CarPool_txt_time_selected.setText(new StringBuilder().append(pad(hour)).append(":").append(pad(minute)));

                }
            };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public void showTimeDialogOnButtonClick() {

        Create_CarPool_time_relative = (RelativeLayout) findViewById(R.id.createCarPool_time_relative);
        Create_CarPool_time_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText(R.string.edit_ride);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        if (v == create) {
            if (edit_route_name.getText() != null && edit_route_name.getText().toString() != getString(R.string.ride_name) && From_Em_Id != -1 && To_Em_Id != -1 && From_Reg_Id != -1 && To_Reg_Id != -1 && Single_Periodic_ID != -1 && Vehicle_Id != -1 && id != -1 && Create_CarPool_txt_beforeCal.getText().toString() != getString(R.string.click_to_select) && Create_CarPool_txt_time_selected.getText() != null && Create_CarPool_txt_time_selected.getText().toString() != getString(R.string.click_to_select)) {
                    String is_Rounded;
                    String EnName = edit_route_name.getText().toString();
                    int FromEmId = From_Em_Id;   // dubai
                    int ToEmId = To_Em_Id;   // 3agman
                    int FromRegId = From_Reg_Id;//
                    int ToRegId = To_Reg_Id;   // 3agman mueseum
                    if (Single_Periodic_ID == 1) {
                        is_Rounded = "true";
                    } else {
                        is_Rounded = "false";
                    }
                    String Time = Create_CarPool_txt_time_selected.getText().toString();
                    String Saturday = String.valueOf(SAT_FLAG);
                    String Sunday = String.valueOf(SUN_FLAG);
                    String Monday = String.valueOf(MON_FLAG);
                    String Tuesday = String.valueOf(TUES_FLAG);
                    String Wednesday = String.valueOf(WED_FLAG);
                    String Thursday = String.valueOf(THU_FLAG);
                    String Friday = String.valueOf(FRI_FLAG);
                    int Vehicle_ID = Vehicle_Id;
                    int No_OF_Seats = Number_Of_Seats;
                    double Start_Lat = Start_Latitude;
                    double Start_Lng = Start_Longitude;
                    double End_Lat = End_Latitude;
                    double End_Lng = End_Longitude;
                    int pref_lnag = Language_ID;
                    int pref_nat = Nationality_ID;
                    int Age_Ranged_id = Age_ID;
                    // String StartDate = Create_CarPool_txt_year.getText().toString();
                    StartDate = Create_CarPool_txt_year.getText().toString();

//                if ( !Create_CarPool_full_date.equals("") ) {
//                     StartDate = Create_CarPool_full_date;
//                }else {
//
//                    StartDate = "";
//
//                }


                    GetData j = new GetData();
                    j.DriverEditCarPoolFrom(RouteId, EnName, FromEmId, ToEmId, FromRegId, ToRegId
                            , is_Rounded, URLEncoder.encode(Time), Saturday, Sunday, Monday, Tuesday, Wednesday, Thursday, Friday
                            , gender, Vehicle_ID, No_OF_Seats, Start_Lat, Start_Lng, End_Lat, End_Lng
                            , pref_lnag, pref_nat, Age_Ranged_id, StartDate, this);
            } else {
                Toast.makeText(DriverEditCarPool.this, getString(R.string.fill_all_error), Toast.LENGTH_SHORT).show();
            }
        }


        if (v == seat1_off && id == 1) {
            seat1_on.setVisibility(View.VISIBLE);
            seat1_off.setVisibility(View.INVISIBLE);
            id = 2;
            Number_Of_Seats = 1;
        }

        if (v == seat1_on && id == 2) {
            seat1_on.setVisibility(View.INVISIBLE);
            seat1_off.setVisibility(View.VISIBLE);
            id = 1;
            Number_Of_Seats = 0;
        }
        if (v == seat2_off && id == 2) {
            seat2_off.setVisibility(View.INVISIBLE);
            seat2_on.setVisibility(View.VISIBLE);
            id = 3;
            Number_Of_Seats = 2;
        }
        if (v == seat2_on && id == 3) {
            seat2_off.setVisibility(View.VISIBLE);
            seat2_on.setVisibility(View.INVISIBLE);
            id = 2;
            Number_Of_Seats = 1;
        }
        if (v == seat3_off && id == 3) {
            seat3_off.setVisibility(View.INVISIBLE);
            seat3_on.setVisibility(View.VISIBLE);
            id = 4;
            Number_Of_Seats = 3;
        }
        if (v == seat3_on && id == 4) {
            seat3_on.setVisibility(View.INVISIBLE);
            seat3_off.setVisibility(View.VISIBLE);
            id = 3;
            Number_Of_Seats = 2;
        }
        if (v == seat4_off && id == 4) {
            seat4_off.setVisibility(View.INVISIBLE);
            seat4_on.setVisibility(View.VISIBLE);
            id = 5;
            Number_Of_Seats = 4;
        }

        if (v == seat4_on && id == 5) {
            seat4_on.setVisibility(View.INVISIBLE);
            seat4_off.setVisibility(View.VISIBLE);
            id = 4;
            Number_Of_Seats = 3;
        }

        if (v == createCarPool_Sat_Day && SAT_FLAG == 0) {

            createCarPool_Sat_Day.setBackgroundResource(R.drawable.days_of_week_circular_on);
            SAT_FLAG = 1;
        } else if (v == createCarPool_Sat_Day && SAT_FLAG == 1) {

            createCarPool_Sat_Day.setBackgroundResource(R.drawable.days_of_week_circular_off);
            SAT_FLAG = 0;
        }


        if (v == createCarPool_Sun_Day && SUN_FLAG == 0) {

            createCarPool_Sun_Day.setBackgroundResource(R.drawable.days_of_week_circular_on);
            SUN_FLAG = 1;
        } else if (v == createCarPool_Sun_Day && SUN_FLAG == 1) {

            createCarPool_Sun_Day.setBackgroundResource(R.drawable.days_of_week_circular_off);
            SUN_FLAG = 0;
        }


        if (v == createCarPool_Mon_Day && MON_FLAG == 0) {

            createCarPool_Mon_Day.setBackgroundResource(R.drawable.days_of_week_circular_on);
            MON_FLAG = 1;
        } else if (v == createCarPool_Mon_Day && MON_FLAG == 1) {

            createCarPool_Mon_Day.setBackgroundResource(R.drawable.days_of_week_circular_off);
            MON_FLAG = 0;
        }


        if (v == createCarPool_Tues_Day && TUES_FLAG == 0) {

            createCarPool_Tues_Day.setBackgroundResource(R.drawable.days_of_week_circular_on);
            TUES_FLAG = 1;
        } else if (v == createCarPool_Tues_Day && TUES_FLAG == 1) {

            createCarPool_Tues_Day.setBackgroundResource(R.drawable.days_of_week_circular_off);
            TUES_FLAG = 0;
        }

        if (v == createCarPool_Wed_Day && WED_FLAG == 0) {

            createCarPool_Wed_Day.setBackgroundResource(R.drawable.days_of_week_circular_on);
            WED_FLAG = 1;
        } else if (v == createCarPool_Wed_Day && WED_FLAG == 1) {

            createCarPool_Wed_Day.setBackgroundResource(R.drawable.days_of_week_circular_off);
            WED_FLAG = 0;
        }


        if (v == createCarPool_Thu_Day && THU_FLAG == 0) {

            createCarPool_Thu_Day.setBackgroundResource(R.drawable.days_of_week_circular_on);
            THU_FLAG = 1;
        } else if (v == createCarPool_Thu_Day && THU_FLAG == 1) {

            createCarPool_Thu_Day.setBackgroundResource(R.drawable.days_of_week_circular_off);
            THU_FLAG = 0;
        }


        if (v == createCarPool_Fri_Day && FRI_FLAG == 0) {

            createCarPool_Fri_Day.setBackgroundResource(R.drawable.days_of_week_circular_on);
            FRI_FLAG = 1;
        } else if (v == createCarPool_Fri_Day && FRI_FLAG == 1) {

            createCarPool_Fri_Day.setBackgroundResource(R.drawable.days_of_week_circular_off);
            FRI_FLAG = 0;
        }

    }

    private class ToLongLat extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {

            String ret;
            try {
                InputStream inputStream = openFileInput("Regions" + To_Em_Id + ".txt");
                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((receiveString = bufferedReader.readLine()) != null) {
                        stringBuilder.append(receiveString);
                    }
                    inputStream.close();
                    ret = stringBuilder.toString();

                    JSONArray jsonArray = new JSONArray(ret);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject.getInt("ID") == From_Reg_Id){
                            End_Latitude = jsonObject.getDouble("RegionLatitude");
                            End_Longitude = jsonObject.getDouble("RegionLongitude");
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                Log.e("login activity", "File not found: " + e.toString());
            } catch (IOException e) {
                Log.e("login activity", "Can not read file: " + e.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }    // back thread classs


    private class FromLongLat extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {

            String ret;
            try {
                InputStream inputStream = openFileInput("Regions" + From_Em_Id + ".txt");
                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((receiveString = bufferedReader.readLine()) != null) {
                        stringBuilder.append(receiveString);
                    }
                    inputStream.close();
                    ret = stringBuilder.toString();

                    JSONArray jsonArray = new JSONArray(ret);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject.getInt("ID") == From_Reg_Id){
                            Start_Latitude = jsonObject.getDouble("RegionLatitude");
                            Start_Longitude = jsonObject.getDouble("RegionLongitude");
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                Log.e("login activity", "File not found: " + e.toString());
            } catch (IOException e) {
                Log.e("login activity", "Can not read file: " + e.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }    // back thread classs


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (nationalities.getStatus() == AsyncTask.Status.RUNNING) {
            nationalities.cancel(true);
        }
        if (languages.getStatus() == AsyncTask.Status.RUNNING) {
            languages.cancel(true);
        }
        if (ageRanges.getStatus() == AsyncTask.Status.RUNNING) {
            ageRanges.cancel(true);
        }
        if (vehicles.getStatus() == AsyncTask.Status.RUNNING) {
            vehicles.cancel(true);
        }
        if (load.getStatus() == AsyncTask.Status.RUNNING) {
            load.cancel(true);
        }
        finish();

    }


}
