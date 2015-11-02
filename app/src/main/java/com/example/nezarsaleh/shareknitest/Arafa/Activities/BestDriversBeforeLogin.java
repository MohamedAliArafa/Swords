package com.example.nezarsaleh.shareknitest.Arafa.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nezarsaleh.shareknitest.Arafa.Classes.GetData;
import com.example.nezarsaleh.shareknitest.Arafa.DataModel.BestDriverDataModel;
import com.example.nezarsaleh.shareknitest.Arafa.DataModelAdapter.BestDriverDataModelAdapter;
import com.example.nezarsaleh.shareknitest.OnBoardDir.OnboardingActivity;
import com.example.nezarsaleh.shareknitest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class BestDriversBeforeLogin extends AppCompatActivity {

    TextView tv;
    ListView lv;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arafa);

        tv = (TextView) findViewById(R.id.info);
        lv = (ListView) findViewById(R.id.lvMain);
       initToolbar();

        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);

        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading" + "...");
        pDialog.show();

        new jsoning(lv, pDialog, this).execute();
    }

    public class jsoning extends AsyncTask {

        ListView lv;
        Activity con;
        private ProgressDialog pDialog;
        private List<BestDriverDataModel> arr = new ArrayList<>();
        boolean exists = false;


        public jsoning(final ListView lv, ProgressDialog pDialog, final Activity con) {

            this.lv = lv;
            this.con = con;
            this.pDialog = pDialog;

            BestDriverDataModelAdapter adapter = new BestDriverDataModelAdapter(con, arr);
            lv.setAdapter(adapter);
        }

        @Override
        protected void onPostExecute(Object o) {
            if (exists){
                lv.requestLayout();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent in = new Intent(con, Profile.class);
                        in.putExtra("DriverID", arr.get(i).getID());
                        Log.d("Array Id :", String.valueOf(arr.get(i).getID()));
                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        con.startActivity(in);
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
                int timeoutMs = 20000;   // 2 seconds
                sock.connect(sockaddr, timeoutMs);
                exists = true;
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(BestDriversBeforeLogin.this)
                                .setTitle("Connection Problem!")
                                .setMessage("Make sure you have internet connection")
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentToBeNewRoot = new Intent(BestDriversBeforeLogin.this, BestDriversBeforeLogin.class);
                                        ComponentName cn = intentToBeNewRoot.getComponent();
                                        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                                        startActivity(mainIntent);
                                    }
                                })
                                .setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentToBeNewRoot = new Intent(BestDriversBeforeLogin.this, OnboardingActivity.class);
                                        ComponentName cn = intentToBeNewRoot.getComponent();
                                        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                                        startActivity(mainIntent);
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();
                        Toast.makeText(BestDriversBeforeLogin.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                JSONArray response = null;
                try {
                    response = new GetData().GetBestDrivers();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        final BestDriverDataModel driver = new BestDriverDataModel(Parcel.obtain());
                        driver.setID(obj.getInt("AccountId"));
                        driver.setName(obj.getString("AccountName"));
                        driver.setPhotoURL(obj.getString("AccountPhoto"));
                        driver.setNationality(obj.getString("NationalityEnName"));
                        driver.setRating(obj.getInt("Rating"));
                        driver.setPhoneNumber(obj.getString("AccountMobile"));
                        arr.add(driver);
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
        getMenuInflater().inflate(R.menu.menu_most_rides_details, menu);
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





    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText("Best Drivers");
//        toolbar.setElevation(10);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

}
