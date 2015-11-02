package com.example.nezarsaleh.shareknitest;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
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
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nezarsaleh.shareknitest.Arafa.Classes.GetData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class PickUpActivity extends AppCompatActivity {

    int From_Em_Id = -1;
    int From_Reg_Id = -1;

    TextView Emirates_txt;
    String To_EmirateEnName, From_EmirateEnName, To_RegionEnName, From_RegionEnName;

    List<TreeMap<String, String>> Create_CarPool_Emirates_List = new ArrayList<>();
    List<TreeMap<String, String>> Create_CarPool_Regions_List = new ArrayList<>();

    private ArrayList<RegionsDataModel> arr = new ArrayList<>();
    Toolbar toolbar;
    Button btn_pickup_Submit;
    Button btn_choose_lat;
    SimpleAdapter Create_CarPool_EmAdapter;
    Context mContext;
    Dialog Emirates_Dialog;
    ListView Emirates_lv;

    AutoCompleteTextView Create_CarPool_txt_regions;

//    private GoogleMap mMap;
//    SupportMapFragment mapFragment;
//
//    Double RegionLatitude, RegionLongitude;
//
//    Marker markerZero;
//    LatLng position2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up);
//        mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map_pick_up);
//
//        mapFragment.getMapAsync(this);
        initToolbar();

        mContext = this;


        Emirates_txt = (TextView) findViewById(R.id.Emirates_spinner);
        Create_CarPool_txt_regions = (AutoCompleteTextView) findViewById(R.id.mainDialog_Regions_auto);



        Create_CarPool_Emirates_List.clear();
        try {
            JSONArray j = new GetData().GetEmitares();
            for (int i = 0; i < j.length(); i++) {


                TreeMap<String, String> valuePairs = new TreeMap<>();
                JSONObject jsonObject = j.getJSONObject(i);
                valuePairs.put("EmirateId", jsonObject.getString("EmirateId"));
                valuePairs.put("EmirateEnName", jsonObject.getString("EmirateEnName"));
                Create_CarPool_Emirates_List.add(valuePairs);
                Log.d("test Emirates ", Create_CarPool_Emirates_List.toString());


            }
            Log.d("test Emirates ", Create_CarPool_Emirates_List.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Create_CarPool_EmAdapter = new SimpleAdapter(PickUpActivity.this, Create_CarPool_Emirates_List
                , R.layout.dialog_pick_emirate_lv_row
                , new String[]{"EmirateId", "EmirateEnName"}
                , new int[]{R.id.row_id_search, R.id.row_name_search});


        Emirates_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Emirates_Dialog = new Dialog(mContext);
                Emirates_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Emirates_Dialog.setContentView(R.layout.languages_dialog);
                TextView Lang_Dialog_txt_id = (TextView) Emirates_Dialog.findViewById(R.id.Lang_Dialog_txt_id);
                Lang_Dialog_txt_id.setText("Emirates");
                Emirates_lv = (ListView) Emirates_Dialog.findViewById(R.id.Langs_list);
                Emirates_lv.setAdapter(Create_CarPool_EmAdapter);
                Emirates_Dialog.show();
                Emirates_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        TextView txt_em_name = (TextView) view.findViewById(R.id.row_name_search);
                        TextView txt_em_id = (TextView) view.findViewById(R.id.row_id_search);

                        From_Em_Id = Integer.parseInt(txt_em_id.getText().toString());
                        From_EmirateEnName = txt_em_name.getText().toString();
                        Emirates_txt.setText(txt_em_name.getText().toString());
                        Emirates_Dialog.dismiss();
                    }
                });


            }
        });


        Create_CarPool_txt_regions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new backTread().execute();


            }
        });







    } // oncreate



    private class backTread extends AsyncTask  {
        boolean exists = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            if (exists) {

                RegionsAdapter regionsAdapter = new RegionsAdapter(getBaseContext(), R.layout.regions_layout_list, arr);


                Create_CarPool_txt_regions.setAdapter(regionsAdapter);
                Create_CarPool_txt_regions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Create_CarPool_txt_regions.setText(arr.get(position).getRegionEnName());
                        From_Reg_Id = arr.get(position).getID();
                        From_RegionEnName = arr.get(position).getRegionEnName();



                        Log.d("Em Name : ", From_EmirateEnName);
                        Log.d("Reg Name",From_RegionEnName);
                    }
                });


            } //  if


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
                        new AlertDialog.Builder(PickUpActivity.this)
                                .setTitle("Connection Problem!")
                                .setMessage("Make sure you have internet connection")
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentToBeNewRoot = new Intent(PickUpActivity.this, QuickSearchResults.class);
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
                        Toast.makeText(PickUpActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {

                GetData getData = new GetData();
                try {
                    JSONArray jsonArray = getData.GetRegionsByEmiratesID(From_Em_Id);


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        final RegionsDataModel regions = new RegionsDataModel(Parcel.obtain());
                        regions.setID(jsonObject.getInt("ID"));
                        regions.setRegionEnName(jsonObject.getString("RegionEnName"));
                        arr.add(regions);

                    }

                    Log.d("test Regions search ", arr.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            return null;
        }






    }    // back thread classs





    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText("Destination");
//        toolbar.setElevation(10);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}  //  Class
