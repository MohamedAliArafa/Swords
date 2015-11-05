package rta.ae.sharekni;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import rta.ae.sharekni.Arafa.Activities.Profile;
import rta.ae.sharekni.Arafa.Classes.GetData;

import com.example.nezarsaleh.shareknitest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;


public class QuickSearchResults extends AppCompatActivity {

    int From_Em_Id;
    int From_Reg_Id;
    int To_Em_Id;
    int To_Reg_Id;
    String To_EmirateEnName, From_EmirateEnName, To_RegionEnName, From_RegionEnName;
    String check;
    TextView To_EmirateEnName_txt;
    TextView From_EmirateEnName_txt;
    TextView To_RegionEnName_txt;
    TextView From_RegionEnName_txt;
    SharedPreferences myPrefs;
    char Gender;
    ListView lvResult;
    private Toolbar toolbar;
    String ID;

    TextView to_txt_id, comma5;
    String Str_To_EmirateEnName_txt, Str_To_RegionEnName_txt;
    Activity acivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_search_results);
        lvResult = (ListView) findViewById(R.id.lv_searchResult);

        myPrefs = this.getSharedPreferences("myPrefs", 0);
        ID = myPrefs.getString("account_id", "0");
//        Bundle in = getIntent().getExtras();
//        Log.d("Intent Id :", String.valueOf(in.getInt("DriverID")));

        initToolbar();
        Intent intent = getIntent();

        acivity=this;

        From_Em_Id = intent.getIntExtra("From_Em_Id", 0);
        From_Reg_Id = intent.getIntExtra("From_Reg_Id", 0);
        To_Em_Id = intent.getIntExtra("To_Em_Id", 0);
        To_Reg_Id = intent.getIntExtra("To_Reg_Id", 0);

        To_EmirateEnName = intent.getStringExtra("To_EmirateEnName");
        From_EmirateEnName = intent.getStringExtra("From_EmirateEnName");
        To_RegionEnName = intent.getStringExtra("To_RegionEnName");
        From_RegionEnName = intent.getStringExtra("From_RegionEnName");
        Gender = intent.getCharExtra("Gender", ' ');

        From_EmirateEnName_txt = (TextView) findViewById(R.id.quick_search_em_from);
        From_RegionEnName_txt = (TextView) findViewById(R.id.quick_search_reg_from);
        To_EmirateEnName_txt = (TextView) findViewById(R.id.quick_search_em_to);
        To_RegionEnName_txt = (TextView) findViewById(R.id.quick_search_reg_to);
        to_txt_id = (TextView) findViewById(R.id.to_txt_id);
        comma5 = (TextView) findViewById(R.id.comma5);
        From_EmirateEnName_txt.setText(From_EmirateEnName);
        From_RegionEnName_txt.setText(From_RegionEnName);

        To_EmirateEnName_txt.setText("");
        To_RegionEnName_txt.setText("");
        To_EmirateEnName_txt.setText(To_EmirateEnName);
        To_RegionEnName_txt.setText(To_RegionEnName);

        if (To_EmirateEnName_txt.getText().toString().equals("")) {
            comma5.setVisibility(View.INVISIBLE);
            to_txt_id.setVisibility(View.INVISIBLE);
        }

//        if (To_EmirateEnName.equals("null")){
//            To_EmirateEnName="Not Specified";
//            To_EmirateEnName_txt.setText(To_EmirateEnName);
//        }else {
//            To_EmirateEnName_txt.setText(To_EmirateEnName);
//        }
//        if (To_RegionEnName.equals("null")){
//            To_RegionEnName="Not Specified";
//            To_RegionEnName_txt.setText(To_RegionEnName);
//        }else {
//            To_RegionEnName_txt.setText(To_RegionEnName);
//        }

        new backTread().execute();
    }


    private class backTread extends AsyncTask {

        JSONArray jArray;
        Boolean error = false;

        @Override
        protected void onPostExecute(Object o) {



            try {

                if (jArray.length()==0) {
                    Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();
                    finish();
                }

            }catch (NullPointerException e){

                Toast.makeText(acivity, "No Routes Available ", Toast.LENGTH_SHORT).show();

                final Dialog dialog = new Dialog(acivity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.noroutesdialog);
                Button btn = (Button) dialog.findViewById(R.id.noroute_id);
                dialog.show();

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        acivity.finish();
                    }
                });
            }


            if (error) {
                Toast.makeText(QuickSearchResults.this, "No Available Results", Toast.LENGTH_SHORT).show();

            } else {
                String days = "";
                final List<QuickSearchDataModel> searchArray = new ArrayList<>();
                QuickSearchResultAdapter adapter;
                adapter = new QuickSearchResultAdapter(QuickSearchResults.this, searchArray);
                lvResult.setAdapter(adapter);
                try {
                    JSONObject json;
                    for (int i = 0; i < jArray.length(); i++) {
                        try {
                            final QuickSearchDataModel item = new QuickSearchDataModel(Parcel.obtain());
                            json = jArray.getJSONObject(i);
                            Log.d("test account email", json.getString("AccountName"));
                            item.setAccountName(json.getString("AccountName"));
                            item.setDriverId(json.getInt("DriverId"));
                            item.setAccountPhoto(json.getString("AccountPhoto"));
                            item.setDriverEnName(json.getString("DriverEnName"));
//                    item.setFrom_EmirateName_en(json.getString("From_EmirateName_en"));
//                    item.setFrom_RegionName_en(json.getString("From_RegionName_en"));
//                    item.setTo_EmirateName_en(json.getString("To_EmirateName_en"));
//                    item.setTo_RegionName_en(json.getString("To_RegionName_en"));
                            item.setAccountMobile(json.getString("AccountMobile"));
                            item.setSDG_Route_Start_FromTime(json.getString("SDG_Route_Start_FromTime"));
                            item.setNationality_en(json.getString("Nationality_en"));
                            item.setRating(json.getString("Rating"));

                            days = "";

                            if (json.getString("Saturday").equals("true")) {
                                days += ", Sat";
                            }
                            if (json.getString("SDG_RouteDays_Sunday").equals("true")) {
                                days += ", Sun";
                            }
                            if (json.getString("SDG_RouteDays_Monday").equals("true")) {
                                days += ", Mon";
                            }
                            if (json.getString("SDG_RouteDays_Tuesday").equals("true")) {
                                days += ", Tue";
                            }
                            if (json.getString("SDG_RouteDays_Wednesday").equals("true")) {
                                days += ", Wed";
                            }
                            if (json.getString("SDG_RouteDays_Thursday").equals("true")) {
                                days += ", Thu";
                            }
                            if (json.getString("SDG_RouteDays_Friday").equals("true")) {
                                days += ", Fri";
                            }
                            if (!days.equals("")) {
                                item.setSDG_RouteDays(days.substring(1));
                            }
                            days = "";
                            searchArray.add(item);
                            lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent in = new Intent(QuickSearchResults.this, Profile.class);
                                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    in.putExtra("DriverID", searchArray.get(position).getDriverId());
                                    in.putExtra("PassengerID", ID);
                                    in.putExtra("RouteID", searchArray.get(position).getSDG_Route_ID());
                                    Log.d("Array Id :", String.valueOf(searchArray.get(position).getDriverId()));
                                    QuickSearchResults.this.startActivity(in);
                                    Log.d("Array id : ", searchArray.get(position).getAccountName());
                                    Log.d("on click id : ", String.valueOf(searchArray.get(position).getDriverId()));
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }

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
                                        mainIntent.putExtra("From_Em_Id", From_Em_Id);
                                        mainIntent.putExtra("From_Reg_Id", From_Reg_Id);
                                        mainIntent.putExtra("To_Em_Id", To_Em_Id);
                                        mainIntent.putExtra("To_Reg_Id", To_Reg_Id);

                                        mainIntent.putExtra("To_EmirateEnName", To_EmirateEnName);
                                        mainIntent.putExtra("From_EmirateEnName", From_EmirateEnName);
                                        mainIntent.putExtra("To_RegionEnName", To_RegionEnName);
                                        mainIntent.putExtra("From_RegionEnName", From_RegionEnName);
                                        mainIntent.putExtra("Gender", Gender);
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
                if (ID.equals("0")) {
                    GetData j = new GetData();
                    if (Gender != ' ') {
                        try {
                            jArray = j.Search(0, Gender, Time, From_Em_Id
                                    , From_Reg_Id, To_Em_Id, To_Reg_Id, pref_lnag, pref_nat
                                    , Age_Ranged_id, StartDate, saveFind,acivity);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {

                        try {
                            jArray = j.Search(0, gender, Time, From_Em_Id
                                    , From_Reg_Id, To_Em_Id, To_Reg_Id, pref_lnag, pref_nat
                                    , Age_Ranged_id, StartDate, saveFind,acivity);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    GetData j = new GetData();
                    try {
                        jArray = j.Search(Integer.parseInt(ID), gender, Time, From_Em_Id
                                , From_Reg_Id, To_Em_Id, To_Reg_Id, pref_lnag, pref_nat
                                , Age_Ranged_id, StartDate, saveFind,acivity);
                    } catch (JSONException e) {
                        error = true;
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }


//done

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quick_search_results, menu);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }


}