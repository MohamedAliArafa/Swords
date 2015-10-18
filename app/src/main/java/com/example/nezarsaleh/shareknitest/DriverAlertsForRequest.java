package com.example.nezarsaleh.shareknitest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nezarsaleh.shareknitest.Arafa.Activities.Profile;
import com.example.nezarsaleh.shareknitest.Arafa.Activities.Route;
import com.example.nezarsaleh.shareknitest.Arafa.Classes.GetData;
import com.example.nezarsaleh.shareknitest.Arafa.DataModel.BestDriverDataModel;
import com.example.nezarsaleh.shareknitest.Arafa.DataModelAdapter.BestDriverDataModelAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class DriverAlertsForRequest extends AppCompatActivity {


    ListView Alerts_For_Request;
    int Driver_Id;
    SharedPreferences myPrefs;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_driver_alerts_for_request);
            Alerts_For_Request= (ListView) findViewById(R.id.Alerts_For_Request);
            toolbar= (Toolbar) findViewById(R.id.app_bar);
            initToolbar();


        myPrefs = this.getSharedPreferences("myPrefs", 0);
        String ID = myPrefs.getString("account_id",null);
        Driver_Id = Integer.parseInt(ID);

        Log.d("final",ID);



        ProgressDialog pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading" + "...");
            pDialog.show();

            new jsoning(Alerts_For_Request, pDialog, this).execute();


    }






    public class jsoning extends AsyncTask {

        ListView lv;
        Activity con;
        private ProgressDialog pDialog;
        JSONObject obj;
        private List<DriverAlertsForRequestDataModel> arr = new ArrayList<>();


        public jsoning(final ListView lv, ProgressDialog pDialog, final Activity con) {

            this.lv = lv;
            this.con = con;
            this.pDialog = pDialog;

            DriverAlertsForRequestAdapter adapter = new DriverAlertsForRequestAdapter(con, arr);
            lv.setAdapter(adapter);
        }

        @Override
        protected void onPostExecute(Object o) {
            lv.requestLayout();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Intent in = new Intent(con, Profile.class);
//                    in.putExtra("ID", arr.get(i).getID());
//                    Log.d("Array Id :", String.valueOf(arr.get(i).getID()));
//                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    con.startActivity(in);
//
//

                    Intent in = new Intent(con, DriverRequestDetails.class);
                    in.putExtra("PassengerName",arr.get(i).getPassengerName());
                    in.putExtra("RouteName",arr.get(i).getRouteName());
                    in.putExtra("NationalityEnName",arr.get(i).getNationalityEnName());

                    in.putExtra("AccountPhoto",arr.get(i).getAccountPhoto());
                    in.putExtra("PassengerMobile",arr.get(i).getPassengerMobile());
                    in.putExtra("Remarks",arr.get(i).getRemarks());

                    in.putExtra("RequestId",arr.get(i).getRequestId());
                    in.putExtra("RequestDate",arr.get(i).getRequestDate());

                    con.startActivity(in);



                }
            });
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
                        new AlertDialog.Builder(DriverAlertsForRequest.this)
                                .setTitle("Connection Problem!")
                                .setMessage("Make sure you have internet connection")
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentToBeNewRoot = new Intent(DriverAlertsForRequest.this, DriverAlertsForRequest.class);
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
                        Toast.makeText(DriverAlertsForRequest.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                JSONArray response = null;
                try {
                    response = new GetData().GetDriverAlertsForRequest(Driver_Id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < response.length(); i++) {
                    try {
                        obj = response.getJSONObject(i);
                        final DriverAlertsForRequestDataModel Alert = new DriverAlertsForRequestDataModel(Parcel.obtain());
                        Alert.setPassengerName(obj.getString("PassengerName"));
                        Alert.setNationalityEnName(obj.getString("NationalityEnName"));
                        Alert.setAccountPhoto(obj.getString("AccountPhoto"));
                        Alert.setRouteName(obj.getString("RouteName"));
                        Alert.setPassengerMobile(obj.getString("PassengerMobile"));
                        Alert.setRemarks(obj.getString("Remarks"));
                        Alert.setRequestId(obj.getInt("RequestId"));
                        Alert.setRequestDate(obj.getString("RequestDate"));

                        //driver.setRating(obj.getInt("Rating"));
                        arr.add(Alert);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //hidePDialog();
                }
            }
            return null;
        }
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText("Notifications");
//        toolbar.setElevation(10);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



}
