package rta.ae.sharekni;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import rta.ae.sharekni.Arafa.Classes.GetData;
import rta.ae.sharekni.Arafa.Classes.VolleySingleton;

public class Display_My_Vehicles extends AppCompatActivity {

    Toolbar toolbar;
    ListView user_vehicles;
    Activity c;

    rideJson rideJson;
    Boolean CREATED=false;
    Boolean JOINED=false;
    String GetVehiclesUrl = GetData.DOMAIN + "GetByDriverId?id=";
    SharedPreferences myPrefs;
    int Driver_ID;
    back back;
    Button Refresh_vehicles_Btn;
    public static  String  TRAFFIC_FILE_NUMBER="a";
    public static  String  TRAFFIC_BIRTH_DATE="a";

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, RadioGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__my__vehicles);
        initToolbar();
        c=this;
        back = new back();

        user_vehicles = (ListView) findViewById(R.id.user_vehicles);
        Refresh_vehicles_Btn= (Button) findViewById(R.id.Refresh_vehicles_Btn);

        myPrefs = this.getSharedPreferences("myPrefs", 0);
        String ID = myPrefs.getString("account_id",null);



//        Bundle in = getIntent().getExtras();
//        Log.d("Intent Id :", String.valueOf(in.getInt("DriverID")));
        Driver_ID = Integer.parseInt(ID);
        Log.d("Driver Id", String.valueOf(Driver_ID));


        Intent in =  getIntent();
//        TRAFFIC_FILE_NUMBER =  in.getStringExtra("TRAFFIC_FILE_NUMBER");
//        TRAFFIC_BIRTH_DATE =  in.getStringExtra("TRAFFIC_BIRTH_DATE");


        rideJson = new rideJson();
        rideJson.execute();




        Refresh_vehicles_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( !RegisterVehicle.TRAFFIC_BIRTH_DATE.equals("a")  ||  !RegisterVehicle.TRAFFIC_FILE_NUMBER.equals("a") ){

                    Toast.makeText(Display_My_Vehicles.this, "Updting Vehicles", Toast.LENGTH_SHORT).show();
                    Log.d("traffic birthdate", RegisterVehicle.TRAFFIC_BIRTH_DATE);
                    Log.d("traffic file num",RegisterVehicle.TRAFFIC_FILE_NUMBER);
                    back.execute();


                }else {
                    Toast.makeText(Display_My_Vehicles.this, "Please check file number first", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }



    private class rideJson extends AsyncTask {
        boolean exists = false;
        ProgressDialog pDialog;


        @Override
        protected void onPostExecute(Object o) {
            CREATED=true;
            hidePDialog();
            super.onPostExecute(o);
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(Display_My_Vehicles.this);
            pDialog.setMessage(getString(R.string.loading) + "...");
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
                int timeoutMs = 20000;   // 2 seconds
                sock.connect(sockaddr, timeoutMs);
                exists = true;
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(Display_My_Vehicles.this)
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
                        Toast.makeText(Display_My_Vehicles.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {

                StringRequest stringRequest = new StringRequest(Request.Method.GET, GetVehiclesUrl + Driver_ID,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                response = response.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                                response = response.replaceAll("<string xmlns=\"http://Sharekni-MobAndroid-Data.org/\">", "");
                                response = response.replaceAll("</string>", "");
                                // Display the first 500 characters of the response string.
                                String data = response.substring(40);
                                Log.d("url", GetVehiclesUrl + Driver_ID);

                                try {
                                    JSONArray jArray = new JSONArray(data);
                                    final User_Vehicles_Data_Model[] driver = new User_Vehicles_Data_Model[jArray.length()];
                                    JSONObject json;
                                    if (jArray.length()==0){
                                        Log.d("Error 3 ", "Error3");



                                        final Dialog dialog = new Dialog(c);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setContentView(R.layout.noroutesdialog);
                                        Button btn = (Button) dialog.findViewById(R.id.noroute_id);
                                        TextView Text_3 = (TextView) dialog.findViewById(R.id.Text_3);
                                        dialog.show();
                                        Text_3.setText(R.string.No_Vehicles);

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
                                            User_Vehicles_Data_Model item = new User_Vehicles_Data_Model(Parcel.obtain());

                                            json = jArray.getJSONObject(i);

                                            if (json.getString("PlateCode").equals("null")){
                                                item.setPlateCode(" ");
                                            }else {

                                                item.setPlateCode(json.getString("PlateCode"));

                                            }

                                            if (json.getString("PlateNumber").equals("null")){
                                                item.setPlateNumber(" ");
                                            }else {
                                                item.setPlateNumber(json.getString("PlateNumber"));
                                            }



                                            driver[i] = item;

                                            Log.d("PlateCode", json.getString("PlateCode"));
                                            Log.d("PlateNumber", json.getString("PlateNumber"));




                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Log.d("Error 1 ",e.toString());
                                        }
                                    }

                                    User_vehicles_Adapter arrayAdapter = new User_vehicles_Adapter(Display_My_Vehicles.this, R.layout.user_vehicles_item, driver);
                                    user_vehicles.setAdapter(arrayAdapter);
                                  // setListViewHeightBasedOnChildren(user_vehicles);


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
                VolleySingleton.getInstance(Display_My_Vehicles.this).addToRequestQueue(stringRequest);
            }
            return null;
        }
    }






    private class back extends AsyncTask {

        ProgressDialog pDialog;
        boolean exists = false;
        String data;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(Display_My_Vehicles.this);
            pDialog.setMessage(getString(R.string.loading) + "...");
            pDialog.show();
        }


        @Override
        protected void onPostExecute(Object o) {


            if (data.equals("\"1\"")) {
                Toast.makeText(getBaseContext(), getString(R.string.verified), Toast.LENGTH_LONG).show();

            } else if (data.equals("\"-3\"")) {
                Toast.makeText(getBaseContext(), getString(R.string.invalid_dob), Toast.LENGTH_LONG).show();
                Log.d("inside -3", data);

            } else if (data.equals("\"-4\"")) {
                Toast.makeText(getBaseContext(), getString(R.string.lic_ver_but_no_cars), Toast.LENGTH_LONG).show();

            } else if (data.equals("\"-5\"") || data.equals("\"-6\"")) {
                Toast.makeText(getBaseContext(), getString(R.string.invalid_data), Toast.LENGTH_LONG).show();

            } else if (data.equals("\"0\"")) {
                //  Toast.makeText(context, "license verified, but no cars found ", Toast.LENGTH_LONG).show();
                Log.d("license no json", data + " Error in Connection with the DataBase Server");

            } else if (data.equals("\"-2\"")) {
                Toast.makeText(getBaseContext(),getString( R.string.cant_user_file_number) , Toast.LENGTH_LONG).show();

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
                        new AlertDialog.Builder(Display_My_Vehicles.this)
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
                        Toast.makeText(Display_My_Vehicles.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {

                GetData j = new GetData();

                try {

                    data = j.RegisterVehicle(Driver_ID, RegisterVehicle.TRAFFIC_FILE_NUMBER, RegisterVehicle.TRAFFIC_BIRTH_DATE);

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
        if (rideJson.getStatus()== AsyncTask.Status.RUNNING) {
            rideJson.cancel(true);
        }
        if (back.getStatus() == AsyncTask.Status.RUNNING){
            back.cancel(true);
        }


        finish();

    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display_my_vehicles_menu, menu);
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



//        if (id == R.id.test_vehicles){
//            Intent intent = new Intent(getBaseContext(),RegisterVehicle.class);
//            startActivity(intent);
//        }





        return super.onOptionsItemSelected(item);
    }
    



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText(getString(R.string.vehicles));
//        toolbar.setElevation(10);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
