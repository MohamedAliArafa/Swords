package com.example.nezarsaleh.shareknitest;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nezarsaleh.shareknitest.Arafa.Classes.GetData;
import com.example.nezarsaleh.shareknitest.Map.MapJsonParse;
import com.example.nezarsaleh.shareknitest.OnBoardDir.OnboardingActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    MapsActivity context;
    private static final String DOMAIN = "http://sharekni.sdgstaff.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        new backTread().execute();
        context = this;
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom
                (new LatLng(25.0014511, 55.3588621), 8.25f));


        MapJsonParse mapJsonParse = new MapJsonParse();
        String urlmap = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/GetAllMostDesiredRides";
        mapJsonParse.stringRequest(urlmap, mMap, context);


        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.setMyLocationEnabled(true);

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            View v;

            @Override
            public View getInfoWindow(Marker marker) {

                v = getLayoutInflater().inflate(R.layout.info_window_approved, null);
                LatLng latLng = marker.getPosition();
                String title = marker.getTitle();
                String snippet = marker.getSnippet();

                Log.d("Reg Ar2", marker.getTitle());
                Log.d("Reg En2", marker.getSnippet());

                TextView emirateArName = (TextView) v.findViewById(R.id.emirateAr_name_id);
                TextView emirateEnName = (TextView) v.findViewById(R.id.emirateEn_name_id);
                TextView emirateLat = (TextView) v.findViewById(R.id.txt_map_lat);
                TextView emiratelong = (TextView) v.findViewById(R.id.txt_map_long);

                String lat = String.valueOf(latLng.latitude).substring(0, 7);
                String lon = String.valueOf(latLng.longitude).substring(0, 7);
                emirateLat.setText(lat);
                emiratelong.setText(lon);
                emirateArName.setText(title);
                emirateEnName.setText(snippet);
                return v;

            }


            @Override
            public View getInfoContents(Marker marker) {


                return v;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.Satelltie) {

            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        }


        if (id == R.id.Terrain) {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        }

        if (id == R.id.HyBird) {

            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        }


        if (id == R.id.Normal) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        }


        return super.onOptionsItemSelected(item);

    }


    private class backTread extends AsyncTask {

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
                        new AlertDialog.Builder(MapsActivity.this)
                                .setTitle("Connection Problem!")
                                .setMessage("Make sure you have internet connection")
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentToBeNewRoot = new Intent(MapsActivity.this, QuickSearchResults.class);
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
                        Toast.makeText(MapsActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                return null;


            }
            return null;
        }


    }    // back thread classs


}  //  classs
