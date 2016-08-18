package rta.ae.sharekni;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import rta.ae.sharekni.Arafa.Classes.GetData;

public class RegisterVehicle extends AppCompatActivity {

    Toolbar toolbar;

    Context mContext;

    static final int DILOG_ID = 0;

    final Calendar cal = Calendar.getInstance();
    int year_x, month_x, day_x;
    TextView txt_year;
    TextView txt_dayOfWeek;
    TextView txt_comma;
    TextView txt_beforeCal;
    String full_date = "";
    Activity c;


    Button btn_register_vehicle_1;

    RelativeLayout btn_datepicker_id;

    public static String TRAFFIC_FILE_NUMBER = "a";
    public static String TRAFFIC_BIRTH_DATE = "a";


    EditText File_num_edit;
    String File_NO_Str = "";
    Bundle in;
    SharedPreferences myPrefs;

    int FileNo;
    int Driver_ID;

    back back;
    Driver_RegisterVehicleWithETService_JsonParse license_check = new Driver_RegisterVehicleWithETService_JsonParse();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vehicle);

        initToolbar();
        c = this;

        back = new back();

        myPrefs = this.getSharedPreferences("myPrefs", 0);
        String ID = myPrefs.getString("account_id", null);
//        Bundle in = getIntent().getExtras();
//        Log.d("Intent Id :", String.valueOf(in.getInt("DriverID")));
        Driver_ID = Integer.parseInt(ID);
        Log.d("Driver Id", String.valueOf(Driver_ID));


        mContext = this;
        cal.add(Calendar.YEAR, -18);

        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        txt_comma = (TextView) findViewById(R.id.Register_comma_cal);
        txt_year = (TextView) findViewById(R.id.txt_year);
        txt_beforeCal = (TextView) findViewById(R.id.txt_beforeCal);
        txt_dayOfWeek = (TextView) findViewById(R.id.txt_dayOfWeek);
        showDialogOnButtonClick();

        btn_register_vehicle_1 = (Button) findViewById(R.id.btn_register_vehicle_1);
        File_num_edit = (EditText) findViewById(R.id.File_num_edit);


        btn_register_vehicle_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                GetData j = new GetData();
                if (!full_date.equals("") && !File_num_edit.getText().toString().equals(getString(R.string.enter_licence)) && (File_num_edit.length() > 0)) {
                    File_NO_Str = File_num_edit.getText().toString();
                    back.execute();
                } else if (File_num_edit.length() <= 0) {
                    Toast.makeText(RegisterVehicle.this, R.string.enter_file_no, Toast.LENGTH_SHORT).show();
                } else if (full_date.equals("")) {
                    Toast.makeText(RegisterVehicle.this, R.string.enter_DOB, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterVehicle.this, R.string.enter_file_DOB, Toast.LENGTH_SHORT).show();
                }

                //    license_check.stringRequest(GetData.DOMAIN+"Driver_RegisterVehicleWithETService?AccountId="+Driver_ID+"&TrafficFileNo="+FileNo+"&BirthDate="+full_date,RegisterVehicle.this);
                //   Log.d("reg vehicle", GetData.DOMAIN + "Driver_RegisterVehicleWithETService?AccountId=" + Driver_ID + "&TrafficFileNo=" + FileNo + "&BirthDate=" + full_date);



                    /*
                    try {
                      String data =  j.RegisterVehicle(Driver_ID, FileNo, full_date);
                        if (data.equals("\"1\"")){
                            Toast.makeText(getBaseContext(), "Verified", Toast.LENGTH_LONG).show();

                        }else if(data.equals("\"-3\"")){
                            Toast.makeText(getBaseContext(), "Date birth invalid", Toast.LENGTH_LONG).show();
                            Log.d("inside -3",data);
                        }else if (data.equals("\"-4\"")){
                            Toast.makeText(getBaseContext(), "license verified, but no cars found . or invalid file number ", Toast.LENGTH_LONG).show();

                        }else if (data.equals("\"-5\"") || data.equals("\"-6\"") ){
                            Toast.makeText(getBaseContext(), "Invalid data, please check agaian", Toast.LENGTH_LONG).show();

                        }else if (data.equals("\"0\"")){
                            //  Toast.makeText(context, "license verified, but no cars found ", Toast.LENGTH_LONG).show();
                            Log.d("license no json",data+" Error in Connection with the DataBase Server");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                */


                /*
                if (full_date.equals("")){
                    Toast.makeText(RegisterVehicle.this, "Enter your date of birth please", Toast.LENGTH_SHORT).show();

                }else if (File_NO_Str.equals("")){

                    Toast.makeText(RegisterVehicle.this, "please enter Traffic File Number", Toast.LENGTH_SHORT).show();
                }else if (File_NO_Str.equals("") && full_date.equals("")){

                    Toast.makeText(RegisterVehicle.this, "check file number and birth date please", Toast.LENGTH_SHORT).show();
                }else if(!File_NO_Str.equals("") && !full_date.equals("")) {

                    Toast.makeText(RegisterVehicle.this, "File Number" + File_NO_Str, Toast.LENGTH_LONG).show();
                    Toast.makeText(RegisterVehicle.this, "Date"+full_date, Toast.LENGTH_SHORT).show();

                }

                */


//                    Intent intent = new Intent(getBaseContext(), Register_Vehicle_Verify.class);
//                    startActivity(intent);


            }
        });


    }  //  on create


    private class back extends AsyncTask {

        ProgressDialog pDialog;
        boolean exists = false;
        String data;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(RegisterVehicle.this);
            pDialog.setMessage(getString(R.string.loading) + "...");
            pDialog.show();
        }


        @Override
        protected void onPostExecute(Object o) {

            if (data.equals("\"1\"")) {
                Toast.makeText(getBaseContext(), getString(R.string.verified), Toast.LENGTH_LONG).show();
                TRAFFIC_FILE_NUMBER = File_NO_Str;
                TRAFFIC_BIRTH_DATE = full_date;
                c.finish();
            } else if (data.equals("\"-3\"")) {
                Toast.makeText(getBaseContext(), getString(R.string.invalid_dob), Toast.LENGTH_LONG).show();
                Log.d("inside -3", data);
                c.finish();
            } else if (data.equals("\"-4\"")) {
                Toast.makeText(getBaseContext(), getString(R.string.lic_ver_but_no_cars), Toast.LENGTH_LONG).show();
                c.finish();
            } else if (data.equals("\"-5\"") || data.equals("\"-6\"")) {
                Toast.makeText(getBaseContext(), getString(R.string.invalid_data), Toast.LENGTH_LONG).show();
                c.finish();
            } else if (data.equals("\"0\"")) {
                //  Toast.makeText(context, "license verified, but no cars found ", Toast.LENGTH_LONG).show();
                Log.d("license no json", data + " Error in Connection with the DataBase Server");
                c.finish();
            } else if (data.equals("\"-2\"")) {
                Toast.makeText(getBaseContext(), getString(R.string.cant_user_file_number), Toast.LENGTH_LONG).show();
                c.finish();
            }
            hidePDialog();
        }

        @Override
        protected Object doInBackground(Object[] params) {
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
                        new AlertDialog.Builder(RegisterVehicle.this)
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
                        Toast.makeText(RegisterVehicle.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {

                GetData j = new GetData();

                try {
                    data = j.RegisterVehicle(Driver_ID, File_NO_Str, full_date);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (back.getStatus() == AsyncTask.Status.RUNNING) {
            back.cancel(true);
        }
        finish();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText(getString(R.string.vehicles));

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            Locale locale = Locale.getDefault();
            String Locale_Str2 = locale.toString();
            if (!Locale_Str2.contains("ar")&&!Locale_Str2.contains("ur")) {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_arrow_back);
            } else {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_arrow_forward);
            }

            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    public void showDialogOnButtonClick() {
        btn_datepicker_id = (RelativeLayout) findViewById(R.id.datepicker_id);
        btn_datepicker_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DILOG_ID);
            }
        });
    }

    protected void onPrepareDialog(int id, Dialog dialog) {
        DatePickerDialog datePickerDialog = (DatePickerDialog) dialog;
        // Get the current date
        datePickerDialog.updateDate(year_x, month_x, day_x);
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DILOG_ID) {
            DatePickerDialog dp = new DatePickerDialog(this, dPickerListener, year_x, month_x, day_x);
            DatePicker d = dp.getDatePicker();
            d.setMaxDate(cal.getTimeInMillis());
            return dp;
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener dPickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;
            txt_beforeCal.setVisibility(View.INVISIBLE);
            SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year_x);
            cal.set(Calendar.MONTH, month_x);
            cal.set(Calendar.DAY_OF_MONTH, day_x + 4);
            Date date = cal.getTime();
            String dayOfWeek = simpledateformat.format(date);
            String year_string = String.valueOf(year_x);
            String month_string = String.valueOf(month_x);
            String day_string = String.valueOf(day_x);
            full_date = day_string + "/" + month_string + "/" + year_string;
            txt_year.setText(full_date);
            txt_comma.setVisibility(View.VISIBLE);
            txt_dayOfWeek.setText(dayOfWeek);
            Log.d("Calendar test", full_date);
        }
    };


}
