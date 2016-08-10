package rta.ae.sharekni;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import rta.ae.sharekni.Arafa.Classes.GetData;

public class Advanced_Search extends AppCompatActivity implements View.OnClickListener {

    char i = 'N';
    String IS_Smoking = "";
    static final int DILOG_ID = 0;
    static final int TIME_DIALOG_ID = 999;
    int Single_Periodic_ID;
    int From_Em_Id;
    int From_Reg_Id;
    int To_Em_Id;
    int To_Reg_Id;
    List<TreeMap<String, String>> Advanced_Emirates_List = new ArrayList<>();
    List<TreeMap<String, String>> Advanced_Regions_List = new ArrayList<>();
    List<TreeMap<String, String>> Advanced_Country_List = new ArrayList<>();
    List<TreeMap<String, String>> Advanced_Lang_List = new ArrayList<>();
    List<TreeMap<String, String>> Advanced_AgeRanges_List = new ArrayList<>();
    Dialog Advanced_MainDialog;
    Dialog Languages_Dilaog;
    Spinner Advanced_spinner;
    RelativeLayout Advanced_pickup_relative;
    RelativeLayout Advanced_dropOff_relative;
    RelativeLayout Advanced_calendar_relative;
    RelativeLayout Advanced_time_relative;
    SimpleAdapter Advanced_EmAdapter;
    JSONArray Emirates = null;
    JSONArray Regions = null;
    Button Advanced_btn_submit_pickUp;
    String Advanced_txt_PickUp = "Start Point";
    String Advanced_txt_Drop_Off = "End Point";
    String To_EmirateEnName, From_EmirateEnName, To_RegionEnName, From_RegionEnName;
    String Advanced_full_date;
    AutoCompleteTextView advanced_search_Nat;
    AutoCompleteTextView Advanced_txt_regions;
    int year_x, month_x, day_x;
    int MyId;
    int Nationality_ID;
    private int hour;
    private int minute;
    TextView Advanced_txt_time_selected;
    TextView Advanced_before_Time;
    TextView Advanced_txt_Select_Dest;
    TextView maleFemaleTxt;
    TextView FemaleMaleTxt;
    TextView maleFemaleTxt2;
    TextView Advanced_txt_Selecet_Start_Point;
    TextView FemaleMaleTxt2;
    TextView advanced_search_Preferred_Lang_txt;
    int Language_ID;
    TextView Advanced_txt_year;
    TextView advanced_search_Age_Range_txt;
    int Advanced_Search_Age_Range_ID;
    TextView Advanced_txt_beforeCal;
    ImageView Periodic_SingleRide, singleRide_Periodic, Advanced_malefemale1, Advanced_femalemale2;
    ListView lang_lv;
    Context mContext;
    Button quickSearch_pickUp;
    Button quickSearch_Dropoff;
    Button Advanced_btn_search_page;
    Button dvanced_Destination;
    boolean exists = false;

    networkCheck networkCheck;
    nat nat;
    lan lan;
    age age;

    int i2 = 0;
    String From_EmirateEnName_str, From_RegionEnName_str, To_EmirateEnName_str, To_RegionEnName_str;
    int From_Em_Id_2 = -1, From_Reg_Id_2 = -1, To_Em_Id_2 = -1, To_Reg_Id_2 = -1;

    ImageView save_off, save_on;
    TextView save_search_txt;
    int savefind = 0;

    CheckBox check_Both, Check_Male, Check_Female;
    CheckBox Check_Smoking, Check_NotSmoking;

    private Toolbar toolbar;
    private DatePickerDialog.OnDateSetListener dPickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;
            Advanced_txt_beforeCal.setVisibility(View.INVISIBLE);

            // Toast.makeText(RegisterNewTest.this, "hi"+year_x+" :"+month_x+" :"+day_x, Toast.LENGTH_SHORT).show();
            String year_string = String.valueOf(year_x);
            String month_string = String.valueOf(month_x);
            String day_string = String.valueOf(day_x);
            Advanced_full_date = day_string + "/" + month_string + "/" + year_string;
            Advanced_txt_year.setText(Advanced_full_date);
            Log.d("Calendar test", Advanced_full_date);
        }
    };
    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;
                    Advanced_before_Time.setVisibility(View.INVISIBLE);

                    // set current time into textview
                    Advanced_txt_time_selected.setText(new StringBuilder().append(pad(hour))
                            .append(":").append(pad(minute)));


                }
            };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_advanced__search);

        Advanced_txt_year = (TextView) findViewById(R.id.Advanced_search_txt_yaer);
        Advanced_txt_beforeCal = (TextView) findViewById(R.id.Advanced_textview50);
        Advanced_pickup_relative = (RelativeLayout) findViewById(R.id.advanced_search_pickup_relative);
        Advanced_txt_Selecet_Start_Point = (TextView) findViewById(R.id.Advanced_txt_Selecet_Start_Point);
        Advanced_dropOff_relative = (RelativeLayout) findViewById(R.id.advanced_search_dropOff_relative);
        Advanced_txt_Select_Dest = (TextView) findViewById(R.id.Advanced_txt_Select_Dest);
        Advanced_txt_time_selected = (TextView) findViewById(R.id.Advanced_txt_time_selected);
        Advanced_before_Time = (TextView) findViewById(R.id.Advanced_textview51);
        Advanced_btn_search_page = (Button) findViewById(R.id.btn_advanced_search_page);
        Periodic_SingleRide = (ImageView) findViewById(R.id.Periodic_SingleRide);
        singleRide_Periodic = (ImageView) findViewById(R.id.singleRide_Periodic);
//        Advanced_malefemale1 = (ImageView) findViewById(R.id.Advanced_malefemale1);
//        Advanced_femalemale2 = (ImageView) findViewById(R.id.Advanced_femalemale2);
        advanced_search_Nat = (AutoCompleteTextView) findViewById(R.id.advanced_search_Nat);
//        maleFemaleTxt = (TextView) findViewById(R.id.malefemale_txt);
//      FemaleMaleTxt = (TextView) findViewById(R.id.femalemale_txt);
        maleFemaleTxt2 = (TextView) findViewById(R.id.malefemale_txt2);
        FemaleMaleTxt2 = (TextView) findViewById(R.id.femalemale_txt2);
        advanced_search_Preferred_Lang_txt = (TextView) findViewById(R.id.advanced_search_Preferred_Lang_txt);
        advanced_search_Age_Range_txt = (TextView) findViewById(R.id.advanced_search_Age_Range_txt);
//        quickSearch_pickUp = (Button) findViewById(R.id.quickSearch_pickUp);
        //      quickSearch_Dropoff = (Button) findViewById(R.id.advanced_search__Dropoff);
        dvanced_Destination = (Button) findViewById(R.id.dvanced_Destination);

        // quickSearch_pickUp.setOnClickListener(this);
        //Advanced_pickup_relative.setOnClickListener(this);
        //  quickSearch_Dropoff.setOnClickListener(this);
        //  Advanced_dropOff_relative.setOnClickListener(this);


        save_off = (ImageView) findViewById(R.id.save_off);
        save_on = (ImageView) findViewById(R.id.save_on);
        save_search_txt = (TextView) findViewById(R.id.save_search_txt);


        check_Both = (CheckBox) findViewById(R.id.check_Both);
        Check_Male = (CheckBox) findViewById(R.id.Check_Male);
        Check_Female = (CheckBox) findViewById(R.id.Check_Female);

        Check_Smoking = (CheckBox) findViewById(R.id.Check_Smoking);
        Check_NotSmoking = (CheckBox) findViewById(R.id.Check_NotSmoking);

        i2 = 0;
        try {
            if (PickUpActivity.getInstance() != null) {

                Intent intent = getIntent();

                Bundle b = intent.getBundleExtra("options");
                Log.d("bundle", String.valueOf(b));
                From_Em_Id_2 = intent.getIntExtra("From_Em_Id", 0);
                From_Reg_Id_2 = intent.getIntExtra("From_Reg_Id", 0);
                To_Em_Id_2 = intent.getIntExtra("To_Em_Id", 0);
                To_Reg_Id_2 = intent.getIntExtra("To_Reg_Id", 0);

                Advanced_txt_PickUp = "";
                From_EmirateEnName_str = intent.getStringExtra("From_EmirateEnName");
                From_RegionEnName_str = intent.getStringExtra("From_RegionEnName");
                To_EmirateEnName_str = intent.getStringExtra("To_EmirateEnName");
                To_RegionEnName_str = intent.getStringExtra("To_RegionEnName");
                i2 = 1;
//                if (b != null) {
//                    savefind = b.getInt("savefind");
//                    if (savefind == 1) {
//                        save_off.setVisibility(View.INVISIBLE);
//                        save_on.setVisibility(View.VISIBLE);
//                        save_search_txt.setTextColor(Color.RED);
//                    }
//                    if (savefind == 0) {
//                        save_on.setVisibility(View.INVISIBLE);
//                        save_off.setVisibility(View.VISIBLE);
//                        save_search_txt.setTextColor(Color.GRAY);
//                    }
//                    IS_Smoking = b.getString("IS_Smoking");
//                    if (IS_Smoking != null && IS_Smoking.equals("1")) {
//                        Check_NotSmoking.setChecked(false);
//                        Check_Smoking.setChecked(true);
//                    } else if (IS_Smoking != null && IS_Smoking.equals("0")) {
//                        Check_NotSmoking.setChecked(true);
//                        Check_Smoking.setChecked(false);
//                    }
//                    if (b.getString("full_date") != null) {
//                        Advanced_txt_beforeCal.setVisibility(View.INVISIBLE);
//                        Advanced_txt_year.setText(b.getString("full_date"));
//                    }
//                    if (!b.getString("time").equals("")) {
//                        Advanced_before_Time.setVisibility(View.INVISIBLE);
//                        Advanced_txt_time_selected.setText(b.getString("time"));
//                    }
//                    Single_Periodic_ID = b.getInt("Single_Periodic_ID");
//                    if (Single_Periodic_ID == 1) {
//                        singleRide_Periodic.setVisibility(View.INVISIBLE);
//                        Periodic_SingleRide.setVisibility(View.VISIBLE);
//                        maleFemaleTxt2.setTextColor(Color.GRAY);
//                        FemaleMaleTxt2.setTextColor(Color.RED);
//                    } else if (Single_Periodic_ID == 0) {
//                        Periodic_SingleRide.setVisibility(View.INVISIBLE);
//                        singleRide_Periodic.setVisibility(View.VISIBLE);
//                        Single_Periodic_ID = 0;
//                        maleFemaleTxt2.setTextColor(Color.RED);
//                        FemaleMaleTxt2.setTextColor(Color.GRAY);
//                    }
//                    if (b.getString("advanced_search_Nat") != null) {
//                        advanced_search_Nat.setText(b.getString("advanced_search_Nat"));
//                        Nationality_ID = b.getInt("Nationality_ID");
//                    }
//                    if (b.getString("advanced_search_Preferred_Lang_txt") != null) {
//                        advanced_search_Preferred_Lang_txt.setText(b.getString("advanced_search_Preferred_Lang_txt"));
//                        Nationality_ID = b.getInt("Nationality_ID");
//                    }
//                    if (b.getString("advanced_search_Age_Range_txt") != null){
//                        advanced_search_Age_Range_txt.setText(b.getString("advanced_search_Age_Range_txt"));
//                    }
//                    i = b.getChar("Gender");
//                    if (i == 'N') {
//                        Check_Male.setChecked(false);
//                        Check_Female.setChecked(false);
//                    } else if (i == 'M') {
//                        Check_Male.setChecked(true);
//                        Check_Female.setChecked(false);
//                    } else if (i == 'F') {
//                        Check_Male.setChecked(false);
//                        Check_Female.setChecked(true);
//                    }
//                }
                try {

                    if (From_EmirateEnName_str.equals("null") || From_EmirateEnName_str.equals("")) {
                        From_EmirateEnName_str = "";
                        From_EmirateEnName_str = getString(R.string.not_set);
                        Advanced_txt_PickUp = "";
                        Advanced_txt_PickUp += From_EmirateEnName_str;
                    } else {
                        Advanced_txt_PickUp = "";
                        Advanced_txt_PickUp += From_EmirateEnName_str;
                    }

                    if (From_RegionEnName_str.equals("null") || From_RegionEnName_str.equals(getString(R.string.not_set))) {
                        From_RegionEnName_str = "";
                        From_RegionEnName_str = getString(R.string.not_set);
                        Advanced_txt_PickUp += ",";
                        Advanced_txt_PickUp += From_RegionEnName_str;

                    } else {
                        Advanced_txt_PickUp += ",";
                        Advanced_txt_PickUp += From_RegionEnName_str;
                    }


                } catch (NullPointerException e) {

                    Advanced_txt_PickUp = getString(R.string.start_point);
                }


                try {
                    if (To_EmirateEnName_str.equals("null") || To_EmirateEnName_str.equals("")) {
                        To_EmirateEnName_str = "";
                        To_EmirateEnName_str = getString(R.string.not_set);
                        Advanced_txt_Drop_Off = "";
                        Advanced_txt_Drop_Off += To_EmirateEnName_str;
                    } else {
                        Advanced_txt_Drop_Off = "";
                        Advanced_txt_Drop_Off += To_EmirateEnName_str;
                    }

                    if (To_RegionEnName_str.equals("null") || To_RegionEnName_str.equals(getString(R.string.not_set))) {
                        To_RegionEnName_str = "";
                        To_RegionEnName_str = getString(R.string.not_set);
                        Advanced_txt_Drop_Off += ",";
                        Advanced_txt_Drop_Off += To_RegionEnName_str;

                    } else {
                        Advanced_txt_Drop_Off += ",";
                        Advanced_txt_Drop_Off += To_RegionEnName_str;
                    }

                } catch (NullPointerException e) {

                    Advanced_txt_Drop_Off = getString(R.string.end_point);
                }

                Log.d("From_Em_Id_2", String.valueOf(intent.getIntExtra("From_Em_Id", 0)));
                Log.d("From_Reg_Id_2", String.valueOf(intent.getIntExtra("From_Reg_Id", 0)));
                Log.d("To_Em_Id_2", String.valueOf(intent.getIntExtra("To_Em_Id", 0)));
                Log.d("To_Reg_Id_2", String.valueOf(intent.getIntExtra("To_Reg_Id", 0)));


                PickUpActivity.getInstance().finish();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        showDialogOnButtonClick();
        showTimeDialogOnButtonClick();
        mContext = this;

        initToolbar();

        final Intent intent = getIntent();
        MyId = intent.getIntExtra("ID", 0);


        Check_Smoking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IS_Smoking = "1";
                    Check_NotSmoking.setChecked(false);
                }
            }
        });


        Check_NotSmoking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IS_Smoking = "0";
                    Check_Smoking.setChecked(false);
                }
            }
        });


        check_Both.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    i = 'N';
                    Check_Male.setChecked(false);
                    Check_Female.setChecked(false);
                }
            }
        });


        Check_Male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    i = 'M';
                    check_Both.setChecked(false);
                    Check_Female.setChecked(false);
                }
            }
        });


        Check_Female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    i = 'F';
                    check_Both.setChecked(false);
                    Check_Male.setChecked(false);
                }
            }
        });


        age = new age();
        lan = new lan();
        nat = new nat();
        networkCheck = new networkCheck();

        if (i2 == 0) {
            Advanced_txt_Selecet_Start_Point.setText(getString(R.string.start_point));
            Log.d("pick 1", Advanced_txt_PickUp);

        } else if (i2 == 1) {
            Advanced_txt_Selecet_Start_Point.setText(Advanced_txt_PickUp);

        }


        if (i2 == 0) {
            Advanced_txt_Select_Dest.setText(getString(R.string.end_point));
            Log.d("drop off 1 ", Advanced_txt_Drop_Off);
        } else if (i2 == 1) {
            Advanced_txt_Select_Dest.setText(Advanced_txt_Drop_Off);
        }


        dvanced_Destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle b = new Bundle();
                b.putInt("savefind", savefind);
                b.putString("IS_Smoking", IS_Smoking);
                b.putString("full_date", Advanced_full_date);
                if (!Advanced_txt_time_selected.getText().toString().equals(getString(R.string.start_time))) {
                    b.putString("time", Advanced_txt_time_selected.getText().toString());
                }
                b.putInt("Single_Periodic_ID", Single_Periodic_ID);
                if (!advanced_search_Nat.getText().toString().equals(getString(R.string.pref_nat))) {
                    b.putString("advanced_search_Nat", advanced_search_Nat.getText().toString());
                    b.putInt("Nationality_ID", Nationality_ID);
                }
                if (!advanced_search_Preferred_Lang_txt.getText().toString().equals(getString(R.string.choose_lang))) {
                    b.putString("advanced_search_Preferred_Lang_txt", advanced_search_Preferred_Lang_txt.getText().toString());
                    b.putInt("Nationality_ID", Nationality_ID);
                }
                if (!advanced_search_Age_Range_txt.getText().toString().equals(getString(R.string.choose_age))) {
                    b.putString("advanced_search_Age_Range_txt", advanced_search_Age_Range_txt.getText().toString());
                    b.putInt("Advanced_Search_Age_Range_ID", Advanced_Search_Age_Range_ID);
                }
                b.putChar("Gender", i);

                Intent intent = new Intent(getBaseContext(), PickUpActivity.class);
                intent.putExtra("FALG_SEARCH", 2);
                intent.putExtra("options", b);
                networkCheck.cancel(true);
                nat.cancel(true);
                lan.cancel(true);
                age.cancel(true);

                startActivity(intent);
                finish();
            }
        });

        //done4
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


//        Advanced_malefemale1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Advanced_malefemale1.setVisibility(View.INVISIBLE);
//                Advanced_femalemale2.setVisibility(View.VISIBLE);
//                maleFemaleTxt.setTextColor(Color.GRAY);
//                FemaleMaleTxt.setTextColor(Color.RED);
//                i = 'F';
//
//            }
//        });
//
//
//        Advanced_femalemale2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Advanced_femalemale2.setVisibility(View.INVISIBLE);
//                Advanced_malefemale1.setVisibility(View.VISIBLE);
//                maleFemaleTxt.setTextColor(Color.RED);
//                FemaleMaleTxt.setTextColor(Color.GRAY);
//                i = 'M';
//
//            }
//        });
//
//        FemaleMaleTxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                maleFemaleTxt.setTextColor(Color.GRAY);
//                FemaleMaleTxt.setTextColor(Color.RED);
//                Advanced_malefemale1.setVisibility(View.INVISIBLE);
//                Advanced_femalemale2.setVisibility(View.VISIBLE);
//                i = 'F';
//
//            }
//        });
//
//        maleFemaleTxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                maleFemaleTxt.setTextColor(Color.RED);
//                FemaleMaleTxt.setTextColor(Color.GRAY);
//                Advanced_malefemale1.setVisibility(View.VISIBLE);
//                Advanced_femalemale2.setVisibility(View.INVISIBLE);
//                i = 'M';
//            }
//        });


        save_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((From_Em_Id_2 != -1) && (From_Reg_Id_2 != -1) && (To_Em_Id_2 != -1) && (To_Reg_Id_2 != -1) && (From_Em_Id_2 != 0) && (From_Reg_Id_2 != 0) && (To_Em_Id_2 != 0) && (To_Reg_Id_2 != 0)) {
                    save_off.setVisibility(View.INVISIBLE);
                    save_on.setVisibility(View.VISIBLE);
                    save_search_txt.setTextColor(Color.RED);
                    savefind = 1;
                } else {
                    Toast.makeText(Advanced_Search.this, R.string.saveSearch_Error, Toast.LENGTH_SHORT).show();
                }
            }
        });

        save_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_on.setVisibility(View.INVISIBLE);
                save_off.setVisibility(View.VISIBLE);
                save_search_txt.setTextColor(Color.GRAY);
                savefind = 0;

            }
        });


        String ret;
        try {
            InputStream inputStream = openFileInput("Emirates.txt");
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
                Emirates = new JSONArray(ret);
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //  code to get nationals and set the adapter to the autotext complete
        nat.execute();

        // code to get Languages and set it to the SPinner
        lan.execute();

        // get age ranges and set it to the spineer
        age.execute();

        Advanced_btn_search_page.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                if (From_Em_Id_2 != -1 && From_Reg_Id_2 != -1 && From_Em_Id_2 != 0 && From_Reg_Id_2 != 0) {

                    Intent intent1 = new Intent(getBaseContext(), QuickSearchResults.class);
                    intent1.putExtra("From_Em_Id", From_Em_Id_2);
                    intent1.putExtra("To_Em_Id", To_Em_Id_2);
                    intent1.putExtra("From_Reg_Id", From_Reg_Id_2);
                    intent1.putExtra("To_Reg_Id", To_Reg_Id_2);
                    intent1.putExtra("From_EmirateEnName", From_EmirateEnName_str);
                    intent1.putExtra("From_RegionEnName", From_RegionEnName_str);
                    intent1.putExtra("To_EmirateEnName", To_EmirateEnName_str);
                    intent1.putExtra("To_RegionEnName", To_RegionEnName_str);
                    intent1.putExtra("Gender", i);
                    intent1.putExtra("SaveFind", savefind);
                    intent1.putExtra("Smokers", IS_Smoking);
                    intent1.putExtra("IsRounded", Single_Periodic_ID);
                    intent1.putExtra("MapKey", "Advanced_Search");
                    intent1.putExtra("AgeRange", Advanced_Search_Age_Range_ID);
                    intent1.putExtra("Nationality_ID", Nationality_ID);
                    intent1.putExtra("Language_ID", Language_ID);
                    intent1.putExtra("InviteType", "");

                    networkCheck.cancel(true);
                    nat.cancel(true);
                    lan.cancel(true);
                    age.cancel(true);

                    startActivity(intent1);

                    i2 = 1;


                } else {
                    Toast.makeText(Advanced_Search.this, R.string.select_start_point, Toast.LENGTH_SHORT).show();
                }

            }
        });

        networkCheck.execute();
    }   //  on create


    private class networkCheck extends AsyncTask {

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
                        new AlertDialog.Builder(Advanced_Search.this)
                                .setTitle(getString(R.string.connection_problem))
                                .setMessage(getString(R.string.con_problem_message))
                                .setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        networkCheck.cancel(true);
                                        nat.cancel(true);
                                        lan.cancel(true);
                                        age.cancel(true);

                                        finish();
                                        startActivity(getIntent());
                                    }
                                })
                                .setNegativeButton(getString(R.string.goBack), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        networkCheck.cancel(true);
                                        nat.cancel(true);
                                        lan.cancel(true);
                                        age.cancel(true);

                                        finish();
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();
                        Toast.makeText(Advanced_Search.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }
    }

    private class nat extends AsyncTask {
        boolean exists = false;

        @Override
        protected void onPostExecute(Object o) {
            if (exists) {

                Log.d("Nat", Advanced_Country_List.toString());
                SimpleAdapter adapterCountry = new SimpleAdapter(Advanced_Search.this, Advanced_Country_List
                        , R.layout.autocomplete_row
                        , new String[]{"ID", "NationalityEnName"}
                        , new int[]{R.id.row_id, R.id.row_name});

                advanced_search_Nat.setAdapter(adapterCountry);
                advanced_search_Nat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView txt_lang_name = (TextView) view.findViewById(R.id.row_name);
                        TextView txt_lang_id = (TextView) view.findViewById(R.id.row_id);
                        Nationality_ID = Integer.parseInt(txt_lang_id.getText().toString());
                        advanced_search_Nat.setText(txt_lang_name.getText().toString());
                        Log.d("id of lang", "" + Nationality_ID);
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
                        new AlertDialog.Builder(Advanced_Search.this)
                                .setTitle(getString(R.string.connection_problem))
                                .setMessage(getString(R.string.con_problem_message))
                                .setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        networkCheck.cancel(true);
                                        nat.cancel(true);
                                        lan.cancel(true);
                                        age.cancel(true);

                                        finish();
                                        startActivity(getIntent());
                                    }
                                })
                                .setNegativeButton(getString(R.string.goBack), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        networkCheck.cancel(true);
                                        nat.cancel(true);
                                        lan.cancel(true);
                                        age.cancel(true);

                                        finish();
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();
                        Toast.makeText(Advanced_Search.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                try {
                    JSONArray j = new GetData().GetNationalities();
                    for (int i = 0; i < j.length(); i++) {
                        TreeMap<String, String> valuePairs = new TreeMap<>();
                        JSONObject jsonObject = j.getJSONObject(i);
                        valuePairs.put("ID", jsonObject.getString("ID"));
                        valuePairs.put("NationalityEnName", jsonObject.getString(getString(R.string.nat_name2)));
                        Advanced_Country_List.add(valuePairs);
                    }
                    //Toast.makeText(RegisterNewTest.this, "test pref lang" + Lang_List.toString(), Toast.LENGTH_LONG).show();
                    Log.d("test pref lang", Advanced_Country_List.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    private class lan extends AsyncTask {
        boolean exists = false;

        @Override
        protected void onPostExecute(Object o) {
            if (exists) {
                Log.d("test pref lang  2 :", Advanced_Lang_List.toString());
                final SimpleAdapter adapter2 = new SimpleAdapter(Advanced_Search.this, Advanced_Lang_List
                        , R.layout.autocomplete_row
                        , new String[]{"LanguageId", "LanguageEnName"}
                        , new int[]{R.id.row_id, R.id.row_name});
                advanced_search_Preferred_Lang_txt.setOnClickListener(new View.OnClickListener() {
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
                                advanced_search_Preferred_Lang_txt.setText(txt_lang_name.getText().toString());
                                Log.d("id of lang", "" + Language_ID);
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
                        new AlertDialog.Builder(Advanced_Search.this)
                                .setTitle(getString(R.string.connection_problem))
                                .setMessage(getString(R.string.con_problem_message))
                                .setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        networkCheck.cancel(true);
                                        nat.cancel(true);
                                        lan.cancel(true);
                                        age.cancel(true);

                                        finish();
                                        startActivity(getIntent());
                                    }
                                })
                                .setNegativeButton(getString(R.string.goBack), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        networkCheck.cancel(true);
                                        nat.cancel(true);
                                        lan.cancel(true);
                                        age.cancel(true);

                                        finish();
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();
                        Toast.makeText(Advanced_Search.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                try {
                    JSONArray j = new GetData().GetPrefLanguage();
                    for (int i = 0; i < j.length(); i++) {

                        TreeMap<String, String> valuePairs = new TreeMap<>();
                        JSONObject jsonObject = j.getJSONObject(i);
                        valuePairs.put("LanguageId", jsonObject.getString("LanguageId"));
                        valuePairs.put("LanguageEnName", jsonObject.getString(getString(R.string.lang_name)));
                        Advanced_Lang_List.add(valuePairs);


                    }
                    //Toast.makeText(RegisterNewTest.this, "test pref lang" + Lang_List.toString(), Toast.LENGTH_LONG).show();
                    Log.d("test pref lang", Advanced_Lang_List.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    private class age extends AsyncTask {
        boolean exists = false;

        @Override
        protected void onPostExecute(Object o) {
            if (exists) {
                Log.d("test pref lang  2 :", Advanced_AgeRanges_List.toString());

                final SimpleAdapter AgeRangesAdapter = new SimpleAdapter(Advanced_Search.this, Advanced_AgeRanges_List
                        , R.layout.autocomplete_row
                        , new String[]{"RangeId", "Range"}
                        , new int[]{R.id.row_id, R.id.row_name});


                advanced_search_Age_Range_txt.setOnClickListener(new View.OnClickListener() {
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
                                Advanced_Search_Age_Range_ID = Integer.parseInt(txt_lang_id.getText().toString());
                                advanced_search_Age_Range_txt.setText(txt_lang_name.getText().toString());
                                Log.d("id of lang", "" + Advanced_Search_Age_Range_ID);
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
                        new AlertDialog.Builder(Advanced_Search.this)
                                .setTitle(getString(R.string.connection_problem))
                                .setMessage(getString(R.string.con_problem_message))
                                .setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        networkCheck.cancel(true);
                                        nat.cancel(true);
                                        lan.cancel(true);
                                        age.cancel(true);

                                        finish();
                                        startActivity(getIntent());
                                    }
                                })
                                .setNegativeButton(getString(R.string.goBack), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        networkCheck.cancel(true);
                                        nat.cancel(true);
                                        lan.cancel(true);
                                        age.cancel(true);

                                        finish();
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();
                        Toast.makeText(Advanced_Search.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
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
                        Advanced_AgeRanges_List.add(valuePairs);


                    }
                    //Toast.makeText(RegisterNewTest.this, "test pref lang" + Lang_List.toString(), Toast.LENGTH_LONG).show();
                    Log.d("test pref lang", Advanced_AgeRanges_List.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
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
//            d.setMaxDate(cal.getTimeInMillis());
            return dp;
        }
        if (id == TIME_DIALOG_ID) {
            return new TimePickerDialog(this,
                    timePickerListener, hour, minute, false);
        }
        return null;
    }

    public void showDialogOnButtonClick() {
        Advanced_calendar_relative = (RelativeLayout) findViewById(R.id.advanced_search_calendar_relative);
        Advanced_calendar_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DILOG_ID);
            }
        });
    }

    public void showTimeDialogOnButtonClick() {
        Advanced_time_relative = (RelativeLayout) findViewById(R.id.advanced_search_time_relative);
        Advanced_time_relative.setOnClickListener(new View.OnClickListener() {
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
        textView.setText(getString(R.string.advanced_search));
//        toolbar.setElevation(10);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            Locale locale = Locale.getDefault();
            String Locale_Str2 = locale.toString();
            if (!Locale_Str2.contains("ar")) {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_arrow_back);
            } else {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_arrow_forward);
            }
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }


    @Override
    public void onClick(View v) {

        /*
        if (v == Advanced_pickup_relative || v == quickSearch_pickUp) {
            Advanced_Emirates_List.clear();
            try {
                JSONArray j;
                if (Emirates == null) {
                    j = new GetData().GetEmitares();
                } else {
                    j = Emirates;
                }
                for (int i = 0; i < j.length(); i++) {
                    TreeMap<String, String> valuePairs = new TreeMap<>();
                    JSONObject jsonObject = j.getJSONObject(i);
                    valuePairs.put("EmirateId", jsonObject.getString("EmirateId"));
                    valuePairs.put("EmirateEnName", jsonObject.getString("EmirateEnName"));
                    Advanced_Emirates_List.add(valuePairs);
                }
                Log.d("test Emirates ", Advanced_Emirates_List.toString());
            } catch (JSONException e) {
                e.printStackTrace();

            }

            Advanced_EmAdapter = new SimpleAdapter(Advanced_Search.this, Advanced_Emirates_List
                    , R.layout.dialog_pick_emirate_lv_row
                    , new String[]{"EmirateId", "EmirateEnName"}
                    , new int[]{R.id.row_id_search, R.id.row_name_search});

            Advanced_MainDialog = new Dialog(Advanced_Search.this);
            Advanced_MainDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            Advanced_MainDialog.setContentView(R.layout.main_search_dialog);
            TextView Address = (TextView) Advanced_MainDialog.findViewById(R.id.Lang_Dialog_txt_id);
            Address.setText("Pick Up");
            Advanced_btn_submit_pickUp = (Button) Advanced_MainDialog.findViewById(R.id.btn_submit_puckup);
//                MainDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            Advanced_txt_regions = (AutoCompleteTextView) Advanced_MainDialog.findViewById(R.id.mainDialog_Regions_auto);
            Advanced_spinner = (Spinner) Advanced_MainDialog.findViewById(R.id.Emirates_spinner);
            Advanced_spinner.setAdapter(Advanced_EmAdapter);

            Advanced_MainDialog.show();


            Advanced_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Advanced_txt_PickUp = "";
                    TextView txt_em_name = (TextView) view.findViewById(R.id.row_name_search);
                    TextView txt_em_id = (TextView) view.findViewById(R.id.row_id_search);
                    From_Em_Id = Integer.parseInt(txt_em_id.getText().toString());
                    From_EmirateEnName = txt_em_name.getText().toString();

                    Advanced_txt_PickUp += txt_em_name.getText().toString();
                    Advanced_txt_PickUp += ", ";

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
                            Regions = new JSONArray(ret);
                        }
                    } catch (FileNotFoundException e) {
                        Log.e("login activity", "File not found: " + e.toString());
                    } catch (IOException e) {
                        Log.e("login activity", "Can not read file: " + e.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.d("id of lang", "" + From_Em_Id);

                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            Advanced_txt_regions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GetData getData = new GetData();
                    JSONArray jsonArray;
                    try {
                        if (Regions == null) {
                            jsonArray = getData.GetRegionsByEmiratesID(From_Em_Id);
                        } else {
                            jsonArray = Regions;
                        }
                        Advanced_Regions_List.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            TreeMap<String, String> valuePairs = new TreeMap<>();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            valuePairs.put("ID", jsonObject.getString("ID"));
                            valuePairs.put("RegionEnName", jsonObject.getString("RegionEnName"));
                            Advanced_Regions_List.add(valuePairs);
                        }
                        Log.d("test Regions search ", Advanced_Regions_List.toString());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    final SimpleAdapter RegAdapter = new SimpleAdapter(Advanced_Search.this, Advanced_Regions_List
                            , R.layout.dialog_pick_regions_lv_row
                            , new String[]{"ID", "RegionEnName"}
                            , new int[]{R.id.row_id_search, R.id.row_name_search});

                    Advanced_txt_regions.setAdapter(RegAdapter);


                    Advanced_txt_regions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TextView txt_reg_name = (TextView) view.findViewById(R.id.row_name_search);
                            TextView txt_reg_id = (TextView) view.findViewById(R.id.row_id_search);
                            From_Reg_Id = Integer.parseInt(txt_reg_id.getText().toString());
                            From_RegionEnName = txt_reg_name.getText().toString();
                            Advanced_txt_regions.setText(txt_reg_name.getText().toString());
                            Advanced_txt_PickUp += txt_reg_name.getText().toString();
                        }
                    });


                }
            });


            Advanced_btn_submit_pickUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Advanced_txt_Selecet_Start_Point.setText(Advanced_txt_PickUp);
                    Advanced_MainDialog.dismiss();
                }
            });


        }



        if (v == Advanced_dropOff_relative || v == quickSearch_Dropoff) {


            Advanced_Emirates_List.clear();
            try {
                JSONArray j = new GetData().GetEmitares();
                for (int i = 0; i < j.length(); i++) {

                    TreeMap<String, String> valuePairs = new TreeMap<>();
                    JSONObject jsonObject = j.getJSONObject(i);
                    valuePairs.put("EmirateId", jsonObject.getString("EmirateId"));
                    valuePairs.put("EmirateEnName", jsonObject.getString("EmirateEnName"));
                    Advanced_Emirates_List.add(valuePairs);
                }
                Log.d("test Emirates ", Advanced_Emirates_List.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Advanced_EmAdapter = new SimpleAdapter(Advanced_Search.this, Advanced_Emirates_List
                    , R.layout.dialog_pick_emirate_lv_row
                    , new String[]{"EmirateId", "EmirateEnName"}
                    , new int[]{R.id.row_id_search, R.id.row_name_search});


            Advanced_MainDialog = new Dialog(Advanced_Search.this);
            Advanced_MainDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            Advanced_MainDialog.setContentView(R.layout.main_search_dialog);

            TextView Address = (TextView) Advanced_MainDialog.findViewById(R.id.Lang_Dialog_txt_id);
            Address.setText("Drop Off");


            Advanced_btn_submit_pickUp = (Button) Advanced_MainDialog.findViewById(R.id.btn_submit_puckup);
//                MainDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            Advanced_txt_regions = (AutoCompleteTextView) Advanced_MainDialog.findViewById(R.id.mainDialog_Regions_auto);
            Advanced_spinner = (Spinner) Advanced_MainDialog.findViewById(R.id.Emirates_spinner);
            Advanced_spinner.setAdapter(Advanced_EmAdapter);

            Advanced_MainDialog.show();


            Advanced_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Advanced_txt_Drop_Off = "";
                    TextView txt_em_name = (TextView) view.findViewById(R.id.row_name_search);
                    TextView txt_em_id = (TextView) view.findViewById(R.id.row_id_search);
                    To_Em_Id = Integer.parseInt(txt_em_id.getText().toString());
                    To_EmirateEnName = txt_em_name.getText().toString();

                    Advanced_txt_Drop_Off += txt_em_name.getText().toString();
                    Advanced_txt_Drop_Off += ", ";

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
                            Regions = new JSONArray(ret);
                        }
                    } catch (FileNotFoundException e) {
                        Log.e("login activity", "File not found: " + e.toString());
                    } catch (IOException e) {
                        Log.e("login activity", "Can not read file: " + e.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.d("id of lang", "" + To_Em_Id);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            Advanced_txt_regions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    GetData getData = new GetData();
                    JSONArray jsonArray;
                    try {
                        if (Regions == null) {
                            jsonArray = getData.GetRegionsByEmiratesID(To_Em_Id);
                        } else {
                            jsonArray = Regions;
                        }
                        Advanced_Regions_List.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            TreeMap<String, String> valuePairs = new TreeMap<>();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            valuePairs.put("ID", jsonObject.getString("ID"));
                            valuePairs.put("RegionEnName", jsonObject.getString("RegionEnName"));
                            Advanced_Regions_List.add(valuePairs);
                        }
                        Log.d("test Regions search ", Advanced_Regions_List.toString());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    final SimpleAdapter RegAdapter = new SimpleAdapter(Advanced_Search.this, Advanced_Regions_List
                            , R.layout.dialog_pick_regions_lv_row
                            , new String[]{"ID", "RegionEnName"}
                            , new int[]{R.id.row_id_search, R.id.row_name_search});

                    Advanced_txt_regions.setAdapter(RegAdapter);


                    Advanced_txt_regions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TextView txt_reg_name = (TextView) view.findViewById(R.id.row_name_search);
                            TextView txt_reg_id = (TextView) view.findViewById(R.id.row_id_search);
                            To_Reg_Id = Integer.parseInt(txt_reg_id.getText().toString());
                            To_RegionEnName = txt_reg_name.getText().toString();
                            Advanced_txt_regions.setText(txt_reg_name.getText().toString());
                            Advanced_txt_Drop_Off += txt_reg_name.getText().toString();
                        }
                    });


                }
            });


            Advanced_btn_submit_pickUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Advanced_txt_Select_Dest.setText(Advanced_txt_Drop_Off);
                    Advanced_MainDialog.dismiss();
                }
            });


        }


        */

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

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (networkCheck.getStatus() == AsyncTask.Status.RUNNING) {
            networkCheck.cancel(true);
        }
        if (nat.getStatus() == AsyncTask.Status.RUNNING) {
            nat.cancel(true);
        }
        if (lan.getStatus() == AsyncTask.Status.RUNNING) {
            lan.cancel(true);
        }
        if (age.getStatus() == AsyncTask.Status.RUNNING) {
            age.cancel(true);
        }
        finish();
    }

}    //class


