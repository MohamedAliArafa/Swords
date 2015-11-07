package rta.ae.sharekni;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcel;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import rta.ae.sharekni.Arafa.Classes.GetData;
import rta.ae.sharekni.Arafa.Classes.VolleySingleton;
import rta.ae.sharekni.Arafa.DataModel.BestRouteDataModel;

import rta.ae.sharekni.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class DriverPermits extends AppCompatActivity {


    String url = GetData.DOMAIN + "GetPermitByDriverId?id=";

    String days;

    Toolbar toolbar;
    int Driver_ID;

    ListView user_ride_created;

    SharedPreferences myPrefs;

    Activity c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_permits);
        user_ride_created = (ListView) findViewById(R.id.user_ride_created);
        initToolbar();
        myPrefs = this.getSharedPreferences("myPrefs", 0);
        String ID = myPrefs.getString("account_id",null);
//        Bundle in = getIntent().getExtras();
//        Log.d("Intent Id :", String.valueOf(in.getInt("DriverID")));
        Driver_ID = Integer.parseInt(ID);
        Log.d("Driver Id", String.valueOf(Driver_ID));

        new rideJson().execute();

        c=this;

    }






    private class rideJson extends AsyncTask {
        boolean exists = false;
        ProgressDialog pDialog;


        @Override
        protected void onPostExecute(Object o) {
            hidePDialog();
            super.onPostExecute(o);
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(DriverPermits.this);
            pDialog.setMessage("Loading" + "...");
            pDialog.show();
            super.onPreExecute();
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
                        new AlertDialog.Builder(DriverPermits.this)
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
                        Toast.makeText(DriverPermits.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url + Driver_ID,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                response = response.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                                response = response.replaceAll("<string xmlns=\"http://Sharekni-MobAndroid-Data.org/\">", "");
                                response = response.replaceAll("</string>", "");
                                // Display the first 500 characters of the response string.
                                String data = response.substring(40);
                                Log.d("url", url + Driver_ID);
                                try {
                                    JSONArray jArray = new JSONArray(data);
                                    final BestRouteDataModel[] driver = new BestRouteDataModel[jArray.length()];
                                    JSONObject json;
                                    if (jArray.length()==0){
                                        Log.d("Error 3 ","Error3");

                                        final Dialog dialog = new Dialog(c);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setContentView(R.layout.noroutesdialog);
                                        Button btn = (Button) dialog.findViewById(R.id.noroute_id);
                                        TextView Text_3 = (TextView) dialog.findViewById(R.id.Text_3);
                                        dialog.show();
                                        Text_3.setText("There is no permits yet");

                                        btn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                                c.finish();
                                            }
                                        });

                                    }


                                    for (int i = 0; i < jArray.length(); i++) {
                                        try {
                                            BestRouteDataModel item = new BestRouteDataModel(Parcel.obtain());
                                            days = "";
                                            json = jArray.getJSONObject(i);
                                            item.setFromEm(json.getString("CurrentPassengers"));
                                            item.setToEm(json.getString("IssueDate"));
                                            item.setToReg(json.getString("ExpireDate"));
                                            item.setRouteName(json.getString("RouteEnName"));
                                            item.setDriver_ID(json.getInt("ID"));
                                            driver[i] = item;

                                            Log.d("FromEmlv", json.getString("MaxPassengers"));
                                          //  Log.d("FromReglv", json.getString("FromRegionEnName"));
                                            Log.d("TomEmlv", json.getString("IssueDate"));
                                            Log.d("ToReglv", json.getString("ExpireDate"));

                                            driver_permits_Adapter arrayAdapter = new driver_permits_Adapter(DriverPermits.this, R.layout.driver_permits_list_item, driver);
                                            user_ride_created.setAdapter(arrayAdapter);

                                            user_ride_created.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    Intent intent =  new Intent(DriverPermits.this,ViewPermit.class);
                                                    intent.putExtra("ID",driver[position].Driver_ID);
                                                    startActivity(intent);
                                                }
                                            });



                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Log.d("Error 1 ",e.toString());
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d("Error 2 ", e.toString());
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                VolleySingleton.getInstance(DriverPermits.this).addToRequestQueue(stringRequest);
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
        textView.setText("Permits");
//        toolbar.setElevation(10);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }






}
