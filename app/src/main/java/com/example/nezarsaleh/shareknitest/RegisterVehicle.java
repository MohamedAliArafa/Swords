package com.example.nezarsaleh.shareknitest;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    String full_date="";


    Button btn_register_vehicle_1;

    RelativeLayout btn_datepicker_id;

    EditText File_num_edit;
    String File_NO_Str="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vehicle);

        initToolbar();

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

        File_NO_Str = File_num_edit.getText().toString();







        btn_register_vehicle_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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



//                    Intent intent = new Intent(getBaseContext(), Register_Vehicle_Verify.class);
//                    startActivity(intent);

            }
        });






    }  //  on create


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText("Vehicle");

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
