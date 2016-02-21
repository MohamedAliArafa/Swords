package rta.ae.sharekni;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import rta.ae.sharekni.Arafa.Classes.GetData;
import rta.ae.sharekni.Arafa.Classes.VolleySingleton;
import rta.ae.sharekni.Arafa.DataModel.BestRouteDataModel;

public class SavedSearch extends AppCompatActivity {


    Activity c;
    Toolbar toolbar;
    int Driver_ID;
    String days;
    String url = GetData.DOMAIN +  "Passenger_GetSavedSearch?AccountId=";

    ListView user_ride_created;
    int ID;
    SharedPreferences myPrefs;
    String AccountType;

    String FromEmirateEnName_str,ToEmirateEnName_str,ToRegionEnName_str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        user_ride_created = (ListView) findViewById(R.id.user_ride_created);
        initToolbar();
        c=this;


        myPrefs = this.getSharedPreferences("myPrefs", 0);
        ID = Integer.parseInt(myPrefs.getString("account_id", "0"));
        AccountType = myPrefs.getString("account_type", null);
        Log.d("Account type his",AccountType);
        Log.d("id history", String.valueOf(ID));




        new rideJson().execute();
    }


    private class rideJson extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            boolean exists = false;
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
                        new AlertDialog.Builder(SavedSearch.this)
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
                        Toast.makeText(SavedSearch.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url + ID,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                response = response.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                                response = response.replaceAll("<string xmlns=\"http://Sharekni-MobAndroid-Data.org/\">", "");
                                response = response.replaceAll("</string>", "");
                                // Display the first 500 characters of the response string.
                                String data = response.substring(40);
                                Log.d("url", url + ID);
                                try {
                                    JSONArray jArray = new JSONArray(data);

                                    if (jArray.length()==0){
                                        Log.d("Error 3 ","Error3");

                                        final Dialog dialog = new Dialog(c);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setContentView(R.layout.noroutesdialog);
                                        Button btn = (Button) dialog.findViewById(R.id.noroute_id);
                                        TextView Text_3 = (TextView) dialog.findViewById(R.id.Text_3);
                                        dialog.show();
                                        Text_3.setText(R.string.No_Saved_Search);

                                        btn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                                c.finish();
                                            }
                                        });

                                    }


                                    final BestRouteDataModel[] driver = new BestRouteDataModel[jArray.length()];
                                    JSONObject json;
                                    for (int i = 0; i < jArray.length(); i++) {
                                        try {
                                            BestRouteDataModel item = new BestRouteDataModel(Parcel.obtain());
                                            days = "";
                                            json = jArray.getJSONObject(i);
                                            item.setID(json.getInt("RouteId"));


                                            FromEmirateEnName_str=json.getString(getString(R.string.from_reg_en_name));
                                            if (FromEmirateEnName_str.equals("null")){
                                                item.setFromEm(getString(R.string.not_set));
                                                item.setFromReg(getString(R.string.not_set));
                                                item.setRouteName(getString(R.string.not_set));
                                            }else {
                                                item.setFromEm(json.getString(getString(R.string.from_em_en_name)));
                                                item.setFromReg(json.getString(getString(R.string.from_reg_en_name)));
                                            }


                                            ToEmirateEnName_str=json.getString(getString(R.string.to_reg_en_name));
                                            if (ToEmirateEnName_str.equals("null")){
                                                item.setToEm(getString(R.string.not_set));
                                                item.setToReg(getString(R.string.not_set));
//                                                item.setRouteName(json.getString(getString(R.string.from_em_en_name)));
                                            }else{
                                                item.setRouteName(json.getString(getString(R.string.from_em_en_name)) + " : " + json.getString(getString(R.string.to_em_en_name)));
                                                item.setToEm(json.getString(getString(R.string.to_em_en_name)));
                                                item.setToReg(json.getString(getString(R.string.to_reg_en_name)));


                                            }






                                            item.setFromEmId(json.getInt("FromEmirateId"));
                                            item.setToEmId(json.getInt("ToEmirateId"));
                                            item.setFromRegid(json.getInt("FromRegionId"));
                                            item.setToRegId(json.getInt("ToRegionId"));


                                            if (json.getString("Saturday").equals("true")) {
                                                days += getString(R.string.sat);
                                            }
                                            if (json.getString("Sunday").equals("true")) {
                                                days += getString(R.string.sun);

                                            }
                                            if (json.getString("Monday").equals("true")) {
                                                days += getString(R.string.mon);

                                            }
                                            if (json.getString("Tuesday").equals("true")) {
                                                days += getString(R.string.tue);
                                            }
                                            if (json.getString("Wednesday").equals("true")) {
                                                days += getString(R.string.wed);
                                            }
                                            if (json.getString("Thursday").equals("true")) {
                                                days += getString(R.string.thu);

                                            }
                                            if (json.getString("Friday").equals("true")) {
                                                days += getString(R.string.fri);
                                            }


                                            item.setDriver_profile_dayWeek(days);
                                            days = "";

                                            driver[i] = item;
                                            Log.d("ID", String.valueOf(json.getInt("ID")));
                                            Log.d("FromEmlv", json.getString("FromEmirateEnName"));
                                            Log.d("FromReglv", json.getString("FromRegionEnName"));
                                            Log.d("TomEmlv", json.getString("ToEmirateEnName"));
                                            Log.d("ToReglv", json.getString("ToRegionEnName"));

                                            SavedSearchAdapter arrayAdapter = new SavedSearchAdapter(SavedSearch.this, R.layout.saved_search_list_item, driver);
                                            user_ride_created.setAdapter(arrayAdapter);
                                            user_ride_created.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                                    Intent intent1 = new Intent(getBaseContext(), QuickSearchResults.class);

                                                    intent1.putExtra("From_Em_Id", driver[i].FromEmId);
                                                    intent1.putExtra("To_Em_Id", driver[i].ToEmId);
                                                    intent1.putExtra("From_Reg_Id", driver[i].FromRegid);
                                                    intent1.putExtra("To_Reg_Id", driver[i].ToRegId);
                                                    intent1.putExtra("From_EmirateEnName", driver[i].FromEm);
                                                    intent1.putExtra("From_RegionEnName", driver[i].FromReg);
                                                    intent1.putExtra("To_EmirateEnName", driver[i].ToEm);
                                                    intent1.putExtra("To_RegionEnName", driver[i].ToReg);
                                                    intent1.putExtra("MapKey", "Driver");
                                                    startActivity(intent1);


                                                }
                                            });
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
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
                VolleySingleton.getInstance(SavedSearch.this).addToRequestQueue(stringRequest);
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
        textView.setText(R.string.saved_search);
//        toolbar.setElevation(10);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
