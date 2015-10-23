package com.example.nezarsaleh.shareknitest;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nezarsaleh.shareknitest.Arafa.Classes.GetData;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;


public class QuickSearchResults extends AppCompatActivity {

    int From_Em_Id;
    int From_Reg_Id;
    int To_Em_Id;
    int To_Reg_Id;
    String To_EmirateEnName, From_EmirateEnName, To_RegionEnName, From_RegionEnName;

    TextView To_EmirateEnName_txt;
    TextView From_EmirateEnName_txt;
    TextView To_RegionEnName_txt;
    TextView From_RegionEnName_txt;
    SharedPreferences myPrefs;
    ListView lvResult;
    private Toolbar toolbar;
    String ID;

    String Str_To_EmirateEnName_txt,Str_To_RegionEnName_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_search_results);
        lvResult = (ListView) findViewById(R.id.lv_searchResult);

        myPrefs = this.getSharedPreferences("myPrefs", 0);
        ID = myPrefs.getString("account_id", null);
//        Bundle in = getIntent().getExtras();
//        Log.d("Intent Id :", String.valueOf(in.getInt("DriverID")));

        initToolbar();
        Intent intent = getIntent();
        From_Em_Id = intent.getIntExtra("From_Em_Id", 0);
        From_Reg_Id = intent.getIntExtra("From_Reg_Id", 0);
        To_Em_Id = intent.getIntExtra("To_Em_Id", 0);
        To_Reg_Id = intent.getIntExtra("To_Reg_Id", 0);

        To_EmirateEnName = intent.getStringExtra("To_EmirateEnName");
        From_EmirateEnName = intent.getStringExtra("From_EmirateEnName");
        To_RegionEnName = intent.getStringExtra("To_RegionEnName");
        From_RegionEnName = intent.getStringExtra("From_RegionEnName");


        From_EmirateEnName_txt = (TextView) findViewById(R.id.quick_search_em_from);
        From_RegionEnName_txt = (TextView) findViewById(R.id.quick_search_reg_from);
        To_EmirateEnName_txt = (TextView) findViewById(R.id.quick_search_em_to);
        To_RegionEnName_txt = (TextView) findViewById(R.id.quick_search_reg_to);

        From_EmirateEnName_txt.setText(From_EmirateEnName);
        From_RegionEnName_txt.setText(From_RegionEnName);



        if (To_EmirateEnName.equals("null")){
            To_EmirateEnName="Not Specified";
            To_EmirateEnName_txt.setText(To_EmirateEnName);

        }else {

            To_EmirateEnName_txt.setText(To_EmirateEnName);

        }

        if (To_RegionEnName.equals("null")){

            To_RegionEnName="Not Specified";
            To_RegionEnName_txt.setText(To_RegionEnName);
        }else {

            To_RegionEnName_txt.setText(To_RegionEnName);
        }




        new backTread().execute();

    }


    private class backTread extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            char gender = 'N';
            String Time = "";
//            int FromEmId = 2;
//            int FromRegId = 4;
//            int ToEmId = 4;
//            int ToRegId = 20;
            int pref_lnag = 0;
            int pref_nat = 0;
            int Age_Ranged_id = 0;
            String StartDate = "";
            int saveFind = 1;

            boolean exists = false;
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
                        new AlertDialog.Builder(QuickSearchResults.this)
                                .setTitle("Connection Problem!")
                                .setMessage("Make sure you have internet connection")
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentToBeNewRoot = new Intent(QuickSearchResults.this, QuickSearchResults.class);
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
                        Toast.makeText(QuickSearchResults.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                if (ID == null) {
                    GetData j = new GetData();
                    j.QuickSearchForm(0, gender, Time, From_Em_Id
                            , From_Reg_Id, To_Em_Id, To_Reg_Id, pref_lnag, pref_nat
                            , Age_Ranged_id, StartDate, saveFind
                            , lvResult, QuickSearchResults.this);
                } else {
                    GetData j = new GetData();
                    j.QuickSearchForm(Integer.parseInt(ID), gender, Time, From_Em_Id
                            , From_Reg_Id, To_Em_Id, To_Reg_Id, pref_lnag, pref_nat
                            , Age_Ranged_id, StartDate, saveFind
                            , lvResult, QuickSearchResults.this);
                }
            }
            return null;
        }
    }


//done

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_quick_search_results, menu);
//        return true;
//    }


//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText("Search Results");
//        toolbar.setElevation(10);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}