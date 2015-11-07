package rta.ae.sharekni;

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
import android.os.Parcel;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import rta.ae.sharekni.Arafa.Activities.Profile;
import rta.ae.sharekni.Arafa.Activities.Route;
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



public class HistoryNew extends AppCompatActivity {

    String url = GetData.DOMAIN + "GetDriverDetailsByAccountId?AccountId=";
    String url2 =  GetData.DOMAIN + "Passenger_MyApprovedRides?AccountId=";

    ListView Passenger_Approved_Rides_Lv;

    String days;

    Toolbar toolbar;
    int Driver_ID;

    ListView user_ride_created;

    SharedPreferences myPrefs;

    String AccountType;
    Activity c;


    RelativeLayout history_created_rides_realtive;
        TextView driver_profile_RouteEnName;



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
        setContentView(R.layout.activity_history_new);

        user_ride_created = (ListView) findViewById(R.id.user_ride_created);
        Passenger_Approved_Rides_Lv= (ListView) findViewById(R.id.Passenger_Approved_Rides_Lv);
        history_created_rides_realtive = (RelativeLayout) findViewById(R.id.history_created_rides_realtive);
        driver_profile_RouteEnName= (TextView) findViewById(R.id.driver_profile_RouteEnName);

        initToolbar();
        myPrefs = this.getSharedPreferences("myPrefs", 0);
        String ID = myPrefs.getString("account_id",null);
//        Bundle in = getIntent().getExtras();
//        Log.d("Intent Id :", String.valueOf(in.getInt("DriverID")));
        Driver_ID = Integer.parseInt(ID);
        AccountType = myPrefs.getString("account_type", null);
        Log.d("Driver Id", String.valueOf(Driver_ID));
        Log.d("Type",AccountType);





        c=this;

        if (AccountType.equals("D")) {


            new rideJson().execute();
            new rideJson2().execute();

        }else if (AccountType.equals("P")){
            user_ride_created.setVisibility(View.INVISIBLE);
            driver_profile_RouteEnName.setVisibility(View.INVISIBLE);
            history_created_rides_realtive.setVisibility(View.INVISIBLE);
            new rideJson2().execute();

            }



    }





    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText("History");
//        toolbar.setElevation(10);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            pDialog = new ProgressDialog(HistoryNew.this);
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
                        new AlertDialog.Builder(HistoryNew.this)
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
                        Toast.makeText(HistoryNew.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
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

//                                        final Dialog dialog = new Dialog(c);
//                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                                        dialog.setContentView(R.layout.noroutesdialog);
//                                        Button btn = (Button) dialog.findViewById(R.id.noroute_id);
//                                        TextView Text_3 = (TextView) dialog.findViewById(R.id.Text_3);
//                                        dialog.show();
//                                        Text_3.setText("There is no Rides Created yet");
//
//                                        btn.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                dialog.dismiss();
//                                                c.finish();
//                                            }
//                                        });

                                    }


                                    for (int i = 0; i < jArray.length(); i++) {
                                        try {
                                            BestRouteDataModel item = new BestRouteDataModel(Parcel.obtain());
                                            days = "";
                                            json = jArray.getJSONObject(i);
                                            item.setID(json.getInt("ID"));
                                            item.setFromEm(json.getString("FromEmirateEnName"));
                                            item.setFromReg(json.getString("FromRegionEnName"));
                                            item.setToEm(json.getString("ToEmirateEnName"));
                                            item.setToReg(json.getString("ToRegionEnName"));
                                            item.setRouteName(json.getString("RouteEnName"));
//                                            item.setStartFromTime(json.getString("StartFromTime"));
//                                            item.setEndToTime_(json.getString("EndToTime_"));

                                            if (json.getString("Saturday").equals("true")) {
                                                days += "Sat , ";
                                            }
                                            if (json.getString("Sunday").equals("true")) {
                                                days += "Sun , ";

                                            }
                                            if (json.getString("Monday").equals("true")) {
                                                days += "Mon , ";

                                            }
                                            if (json.getString("Tuesday").equals("true")) {
                                                days += "Tue , ";
                                            }
                                            if (json.getString("Wednesday").equals("true")) {
                                                days += "Wed , ";
                                            }
                                            if (json.getString("Thursday").equals("true")) {
                                                days += "Thu , ";

                                            }
                                            if (json.getString("Friday").equals("true")) {
                                                days += "Fri ";
                                            }


                                            item.setDriver_profile_dayWeek(days);
                                            days = "";

                                            driver[i] = item;
                                            Log.d("ID", String.valueOf(json.getInt("ID")));
                                            Log.d("FromEmlv", json.getString("FromEmirateEnName"));
                                            Log.d("FromReglv", json.getString("FromRegionEnName"));
                                            Log.d("TomEmlv", json.getString("ToEmirateEnName"));
                                            Log.d("ToReglv", json.getString("ToRegionEnName"));



                                            user_ride_created.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                    Intent in = new Intent(HistoryNew.this, Route.class);
                                                    in.putExtra("RouteID", driver[i].getID());
                                                    in.putExtra("DriverID", Driver_ID);
                                                    HistoryNew.this.startActivity(in);


                                                }

                                            });
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Log.d("Error 1 ",e.toString());
                                        }
                                    }

                                    HistoryNewAdapter arrayAdapter = new HistoryNewAdapter(HistoryNew.this, R.layout.history_created_joined_rides_list_item, driver);
                                    user_ride_created.setAdapter(arrayAdapter);
                                    setListViewHeightBasedOnChildren(user_ride_created);




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
                VolleySingleton.getInstance(HistoryNew.this).addToRequestQueue(stringRequest);
            }
            return null;
        }
    }








    private class rideJson2 extends AsyncTask {
        ProgressDialog pDialog;

        @Override
        protected void onPostExecute(Object o) {
            hidePDialog();
            super.onPostExecute(o);
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(HistoryNew.this);
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
                        new AlertDialog.Builder(HistoryNew.this)
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
                        Toast.makeText(HistoryNew.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                final GetData GD = new GetData();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url2 + Driver_ID,
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
                                    final BestRouteDataModel[] passenger = new BestRouteDataModel[jArray.length()];
                                    JSONObject json;


//                                    if (jArray.length()==0){
//                                        Log.d("Error 3 ","Error3");
//
//                                        final Dialog dialog = new Dialog(c);
//                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                                        dialog.setContentView(R.layout.noroutesdialog);
//                                        Button btn = (Button) dialog.findViewById(R.id.noroute_id);
//                                        TextView Text_3 = (TextView) dialog.findViewById(R.id.Text_3);
//                                        dialog.show();
//                                        Text_3.setText("There is no Rides joined");
//
//                                        btn.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                dialog.dismiss();
//                                                c.finish();
//                                            }
//                                        });
//
//                                    }



                                    for (int i = 0; i < jArray.length(); i++) {
                                        try {
                                            BestRouteDataModel item = new BestRouteDataModel(Parcel.obtain());
                                            days = "";
                                            json = jArray.getJSONObject(i);
                                            int Route_ID = (json.getInt("RouteID"));
                                            int Driver_Account = (json.getInt("Account"));
                                            String Route_Name = (json.getString("Name_en"));
                                            item.setRoutePassengerId(json.getInt("RoutePassengerId"));
                                            Log.d("Route id", String.valueOf(Route_ID));
                                            Log.d("Driver_account", String.valueOf(Driver_Account));
                                            Log.d("Route Name", Route_Name);

                                            JSONObject jsonObject = GD.GetRouteById(Route_ID);
                                            String Routename2 = jsonObject.getString("RouteEnName");
                                            Log.d("Route name 2 ", Routename2);

                                            item.setFromEm(jsonObject.getString("FromEmirateEnName"));
                                            item.setFromReg(jsonObject.getString("FromRegionEnName"));
                                            item.setToEm(jsonObject.getString("ToEmirateEnName"));
                                            item.setToReg(jsonObject.getString("ToRegionEnName"));
                                            item.setRouteName(jsonObject.getString("RouteEnName"));
                                  //          item.setStartFromTime(jsonObject.getString("StartFromTime"));
                                    //        item.setEndToTime_(jsonObject.getString("EndToTime_"));

                                            item.setDriver_ID(Driver_Account);

                                            passenger[i] = item;

                                            Passenger_Approved_Rides_Lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    Intent intent = new Intent(HistoryNew.this, Profile.class);
                                                    intent.putExtra("DriverID",passenger[position].getDriver_ID() );
                                                   HistoryNew.this.startActivity(intent);
                                                }
                                            });


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }


                                    HistoryNewAdapter arrayAdapter = new HistoryNewAdapter(HistoryNew.this, R.layout.history_created_joined_rides_list_item, passenger);
                                    Passenger_Approved_Rides_Lv.setAdapter(arrayAdapter);
                                    setListViewHeightBasedOnChildren(Passenger_Approved_Rides_Lv);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error : ", error.toString());
                        //Ride.setText("That didn't work! : " + error.toString());
                    }
                });
                VolleySingleton.getInstance(HistoryNew.this).addToRequestQueue(stringRequest);
            }
            return null;
        }  //  do in background


    } //  Ride Json







}
