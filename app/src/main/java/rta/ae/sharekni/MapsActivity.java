package rta.ae.sharekni;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationServices;
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
import java.util.Locale;

import rta.ae.sharekni.Arafa.Classes.GetData;
import rta.ae.sharekni.Map.MapDataModel;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    MapsActivity context;
    int From_Em_Id;
    int From_Reg_Id;
    int To_Em_Id;
    int To_Reg_Id;

    TextView N0_Of_Drivers;

    char i = 'D';
    SharedPreferences myPrefs;
    Boolean enableGPS = false;


    String To_EmirateEnName, From_EmirateEnName, To_RegionEnName, From_RegionEnName;
    TextView malefemale_txt, femalemale_txt;
    ImageView malefemale, femalemale;
    RelativeLayout MapRelative;
    Double MyLat = 0.0;
    Double My_Lng = 0.0;
    String AccountType;
    private FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        malefemale_txt = (TextView) findViewById(R.id.malefemale_txt);
        femalemale_txt = (TextView) findViewById(R.id.femalemale_txt);

        malefemale = (ImageView) findViewById(R.id.malefemale);
        femalemale = (ImageView) findViewById(R.id.femalemale);

        MapRelative = (RelativeLayout) findViewById(R.id.MapRelative);


        myPrefs = this.getSharedPreferences("myPrefs", 0);
        AccountType = myPrefs.getString("account_type", "");
        Log.d("Account Type Map", AccountType);
        assert AccountType != null;
        if (AccountType.equals("D")) {

            MapRelative.setVisibility(View.VISIBLE);


            malefemale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (i == 'D') {
                        malefemale.setVisibility(View.INVISIBLE);
                        femalemale.setVisibility(View.VISIBLE);
                        malefemale_txt.setTextColor(Color.GRAY);
                        femalemale_txt.setTextColor(Color.RED);
                        i = 'P';


                        mMap.clear();
                        new backTread().execute();

                    }


                }
            });


            femalemale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (i == 'P') {
                        femalemale.setVisibility(View.INVISIBLE);
                        malefemale.setVisibility(View.VISIBLE);
                        malefemale_txt.setTextColor(Color.RED);
                        femalemale_txt.setTextColor(Color.GRAY);
                        i = 'D';


                        mMap.clear();
                        new backTread().execute();

                    }

                }
            });


            femalemale_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (i == 'D') {
                        malefemale_txt.setTextColor(Color.GRAY);
                        femalemale_txt.setTextColor(Color.RED);

                        malefemale.setVisibility(View.INVISIBLE);
                        femalemale.setVisibility(View.VISIBLE);
                        i = 'P';


                        mMap.clear();
                        new backTread().execute();

                    }


                }
            });


            malefemale_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (i == 'P') {
                        i = 'D';
                        malefemale_txt.setTextColor(Color.RED);
                        femalemale_txt.setTextColor(Color.GRAY);

                        malefemale.setVisibility(View.VISIBLE);
                        femalemale.setVisibility(View.INVISIBLE);


                        mMap.clear();
                        new backTread().execute();
                    }


                }
            });


        }


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


//        MapJsonParse mapJsonParse = new MapJsonParse();
//        String urlmap = DOMAIN + "/_mobfiles/CLS_MobRoute.asmx/GetAllMostDesiredRides";
//        mapJsonParse.stringRequest(urlmap, mMap, context);

        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.setMyLocationEnabled(true);

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {


                if (!((LocationManager) context.getSystemService(Context.LOCATION_SERVICE))
                        .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Intent gpsOptionsIntent = new Intent(
                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(gpsOptionsIntent);

                    //prompt user to enable gps
                } else {
                    //gps is enabled
                }


//               Location myLocation =  mMap.getMyLocation();
//               if (myLocation!=null) {
//                   LatLng loc = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
//                   Marker mMarker = mMap.addMarker(new MarkerOptions().position(loc));
//                   if (mMap != null) {
//                       mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
//                   }
//               }

                return false;
            }

        });


        if (!((LocationManager) context.getSystemService(Context.LOCATION_SERVICE))
                .isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            new AlertDialog.Builder(MapsActivity.this)
                    .setTitle("Location is not Enabled")
                    .setMessage("PLease Turn on Gps First")
                    .setPositiveButton("Turn On", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            enableGPS=true;
                            Intent gpsOptionsIntent = new Intent(
                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(gpsOptionsIntent);
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();


        }


//
//         GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
//            @Override
//            public void onMyLocationChange(Location location) {
//                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
//              Marker  mMarker = mMap.addMarker(new MarkerOptions().position(loc));
//                if(mMap != null){
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
//                }
//            }
//        };

//
//
//
//        mMap.setOnMyLocationChangeListener(myLocationChangeListener);


//
//




        if (((LocationManager) context.getSystemService(Context.LOCATION_SERVICE))
                .isProviderEnabled(LocationManager.GPS_PROVIDER)) {


            LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = service.getBestProvider(criteria, false);
            Location location = service.getLastKnownLocation(provider);
            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

            Log.d("My Lat Lng", String.valueOf(userLocation.latitude));
            Log.d("My Lat Lng", String.valueOf(userLocation.longitude));

            MyLat = userLocation.latitude;
            My_Lng = userLocation.longitude;

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom
                    (new LatLng(userLocation.latitude, userLocation.longitude), 8.25f));


//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom
//                    (new LatLng(MyLat, My_Lng), 8.25f));


        }



//
//


//
//
//
//        MyLat =   mMap.getMyLocation().getLatitude();
//        My_Lng = mMap.getMyLocation().getLongitude();
//
//        if (MyLat!=0.0 && My_Lng!=0.0 ) {
//
//            Log.d("My Lat", String.valueOf(MyLat));
//            Log.d("My Lng", String.valueOf(My_Lng));
//
//        }


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


    private class backTread extends AsyncTask implements GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener {
        boolean exists = false;
        MapDataModel[] data;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (exists) {

                if (i == 'D') {


                    try {
                        JSONArray j = new GetData().GetMapLookUp();
                        data = new MapDataModel[j.length()];

                        for (int i = 0; i < j.length(); i++) {

                            MapDataModel item = new MapDataModel(Parcel.obtain());

                            JSONObject jsonObject = j.getJSONObject(i);

                            item.setFromRegionArName(jsonObject.getString("FromRegionNameAr"));
                            item.setFromRegionEnName(jsonObject.getString("FromRegionNameEn"));
                            item.setFromEmirateEnName(jsonObject.getString("FromEmirateNameEn"));
                            item.setFromEmirateId(jsonObject.getInt("FromEmirateId"));
                            item.setFromRegionId(jsonObject.getInt("FromRegionId"));
                            item.setFromEmirateArName(jsonObject.getString("FromEmirateNameAr"));

                            item.setNoOfRoutes(jsonObject.getInt("NoOfRoutes"));
                            item.setNoOFPassengers(jsonObject.getInt("NoOfPassengers"));

                            if (jsonObject.getString("FromLng").equals("null") && jsonObject.getString("FromLat").equals("null")) {
                                item.longitude = 0.0;
                                item.latitude = 0.0;
                            } else {
                                item.setLongitude(jsonObject.getDouble("FromLng"));
                                item.setLatitude(jsonObject.getDouble("FromLat"));
                            }

                            if (item.latitude != 0.0 && item.longitude != 0.0) {
                                data[i] = item;
                                final Marker markerZero = mMap.addMarker(new MarkerOptions().
                                        title(String.valueOf(i)).
                                        position(new LatLng(item.latitude, item.longitude))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pindriver))

                                );

//                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom
//                                        (new LatLng(data[i].latitude, data[i].longitude), 12.0f));


                            }


                        } // for

//                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom
//                                (new LatLng(25.197197, 55.2743764), 8.25f));


                    } // try
                    catch (JSONException e) {
                        e.printStackTrace();
                    }  // cathch


                    mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                        View v;

                        @Override
                        public View getInfoWindow(Marker marker) {

                            v = getLayoutInflater().inflate(R.layout.info_window_approved, null);
                            LatLng latLng = marker.getPosition();
                            int i = Integer.parseInt(marker.getTitle());

                            String snippet = data[i].getFromRegionArName();
                            String title = data[i].getFromRegionEnName();
                            int NoOfRoutes = data[i].getNoOfRoutes();
                            int NoOfPassengers_Count = data[i].getNoOFPassengers();
                            TextView emirateArName = (TextView) v.findViewById(R.id.emirateAr_name_id);
                            TextView emirateEnName = (TextView) v.findViewById(R.id.emirateEn_name_id);
                            TextView emirateLat = (TextView) v.findViewById(R.id.txt_map_lat);
                            TextView emiratelong = (TextView) v.findViewById(R.id.txt_map_long);
                            TextView NoOfRoutes_txt = (TextView) v.findViewById(R.id.NoOfRoutes);
                            TextView NoOfPassengers = (TextView) v.findViewById(R.id.NoOfPassengers);
                            TextView N0_Of_Drivers = (TextView) v.findViewById(R.id.N0_Of_Drivers);

                            String lat = String.valueOf(latLng.latitude).substring(0, 7);
                            String lon = String.valueOf(latLng.longitude).substring(0, 7);
                            String NoOfRoutes_str = String.valueOf(NoOfRoutes);
                            String NoOfPassengers_str = String.valueOf(NoOfPassengers_Count);
                            emirateLat.setText(lat);
                            emiratelong.setText(lon);
                            emirateArName.setText(snippet);
                            emirateEnName.setText(title);
                            NoOfRoutes_txt.setText(NoOfRoutes_str);
                            N0_Of_Drivers.setText(NoOfRoutes_str);
                            NoOfPassengers.setText(NoOfPassengers_str);
                            return v;

                        }


                        @Override
                        public View getInfoContents(Marker marker) {


                            return v;
                        }
                    });


                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            int i = Integer.parseInt(marker.getTitle());


                            From_Em_Id = data[i].getFromEmirateId();
                            From_Reg_Id = data[i].getFromRegionId();


                            Locale locale = Locale.getDefault();
                            String loca = locale.toString();
                            Log.d("locale", loca);
                            if (loca.contains("en")) {
                                From_EmirateEnName = data[i].getFromEmirateEnName();
                                From_RegionEnName = data[i].getFromRegionEnName();
                                Log.d("Maps em en", From_EmirateEnName);
                                Log.d("Maps em en", From_RegionEnName);
                            } else if (loca.equals("ar")) {
                                From_EmirateEnName = data[i].getFromEmirateArName();
                                From_RegionEnName = data[i].getFromRegionArName();
                                Log.d("Maps em ar", From_EmirateEnName);
                                Log.d("Maps em ar", From_RegionEnName);
                            }


                            Intent intent1 = new Intent(getBaseContext(), QuickSearchResults.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent1.putExtra("From_Em_Id", From_Em_Id);
                            intent1.putExtra("To_Em_Id", To_Em_Id);
                            intent1.putExtra("From_Reg_Id", From_Reg_Id);
                            intent1.putExtra("To_Reg_Id", To_Reg_Id);
                            intent1.putExtra("From_EmirateEnName", From_EmirateEnName);
                            intent1.putExtra("From_RegionEnName", From_RegionEnName);
                            intent1.putExtra("To_EmirateEnName", To_EmirateEnName);
                            intent1.putExtra("To_RegionEnName", To_RegionEnName);
                            intent1.putExtra("MapKey", "Driver");
                            intent1.putExtra("InviteType", "MapLookUp");
                            startActivity(intent1);


                        }
                    });


                } //  if

                else {
                    mMap.clear();


                    try {
                        JSONArray j = new GetData().GetPassengersMapLookUp();
                        data = new MapDataModel[j.length()];

                        for (int i = 0; i < j.length(); i++) {

                            MapDataModel item = new MapDataModel(Parcel.obtain());

                            JSONObject jsonObject = j.getJSONObject(i);

                            item.setFromRegionArName(jsonObject.getString("FromRegionNameAr"));
                            item.setFromRegionEnName(jsonObject.getString("FromRegionNameEn"));
                            item.setFromEmirateEnName(jsonObject.getString("FromEmirateNameEn"));
                            item.setFromEmirateId(jsonObject.getInt("FromEmirateId"));
                            item.setFromRegionId(jsonObject.getInt("FromRegionId"));
                            item.setFromEmirateArName(jsonObject.getString("FromEmirateNameAr"));

                            item.setNoOfRoutes(jsonObject.getInt("NoOfRoutes"));
                            item.setNoOFPassengers(jsonObject.getInt("NoOfPassengers"));

                            if (jsonObject.getString("FromLng").equals("null") && jsonObject.getString("FromLat").equals("null")) {
                                item.longitude = 0.0;
                                item.latitude = 0.0;
                            } else {
                                item.setLongitude(jsonObject.getDouble("FromLng"));
                                item.setLatitude(jsonObject.getDouble("FromLat"));
                            }

                            if (item.latitude != 0.0 && item.longitude != 0.0) {
                                data[i] = item;
                                final Marker markerZero = mMap.addMarker(new MarkerOptions().
                                        title(String.valueOf(i)).
                                        position(new LatLng(item.latitude, item.longitude))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pinpassenger))

                                );

//                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom
//                                        (new LatLng(data[i].latitude, data[i].longitude), 12.0f));


                            }


                        } // for

//                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom
//                                (new LatLng(25.197197, 55.2743764), 8.25f));


                    } // try
                    catch (JSONException e) {
                        e.printStackTrace();
                    }  // cathch


                    mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                        View v;

                        @Override
                        public View getInfoWindow(Marker marker) {

                            v = getLayoutInflater().inflate(R.layout.info_window_approved, null);
                            LatLng latLng = marker.getPosition();
                            int i = Integer.parseInt(marker.getTitle());

                            String snippet = data[i].getFromRegionArName();
                            String title = data[i].getFromRegionEnName();
                            int NoOfRoutes = data[i].getNoOfRoutes();
                            int NoOfPassengers_Count = data[i].getNoOFPassengers();
                            TextView emirateArName = (TextView) v.findViewById(R.id.emirateAr_name_id);
                            TextView emirateEnName = (TextView) v.findViewById(R.id.emirateEn_name_id);
                            TextView emirateLat = (TextView) v.findViewById(R.id.txt_map_lat);
                            TextView emiratelong = (TextView) v.findViewById(R.id.txt_map_long);
                            TextView NoOfRoutes_txt = (TextView) v.findViewById(R.id.NoOfRoutes);
                            TextView NoOfPassengers = (TextView) v.findViewById(R.id.NoOfPassengers);
                            TextView N0_Of_Drivers = (TextView) v.findViewById(R.id.N0_Of_Drivers);
                            RelativeLayout ComingRideRelative = (RelativeLayout) v.findViewById(R.id.ComingRideRelative);
                            ComingRideRelative.setVisibility(View.INVISIBLE);

                            String lat = String.valueOf(latLng.latitude).substring(0, 7);
                            String lon = String.valueOf(latLng.longitude).substring(0, 7);
                            String NoOfRoutes_str = String.valueOf(NoOfRoutes);
                            String NoOfPassengers_str = String.valueOf(NoOfPassengers_Count);
                            emirateLat.setText(lat);
                            emiratelong.setText(lon);
                            emirateArName.setText(snippet);
                            emirateEnName.setText(title);
                            NoOfRoutes_txt.setText(NoOfRoutes_str);
                            N0_Of_Drivers.setText(NoOfRoutes_str);
                            NoOfPassengers.setText(NoOfPassengers_str);
                            return v;

                        }


                        @Override
                        public View getInfoContents(Marker marker) {


                            return v;
                        }
                    });


                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            int i = Integer.parseInt(marker.getTitle());


                            From_Em_Id = data[i].getFromEmirateId();
                            From_Reg_Id = data[i].getFromRegionId();


                            Locale locale = Locale.getDefault();
                            String loca = locale.toString();
                            Log.d("locale", loca);
                            if (loca.contains("en")) {
                                From_EmirateEnName = data[i].getFromEmirateEnName();
                                From_RegionEnName = data[i].getFromRegionEnName();
                                Log.d("Maps em en", From_EmirateEnName);
                                Log.d("Maps em en", From_RegionEnName);
                            } else if (loca.equals("ar")) {
                                From_EmirateEnName = data[i].getFromEmirateArName();
                                From_RegionEnName = data[i].getFromRegionArName();
                                Log.d("Maps em ar", From_EmirateEnName);
                                Log.d("Maps em ar", From_RegionEnName);
                            }


                            Intent intent1 = new Intent(getBaseContext(), QuickSearchResults.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent1.putExtra("From_Em_Id", From_Em_Id);
                            intent1.putExtra("To_Em_Id", To_Em_Id);
                            intent1.putExtra("From_Reg_Id", From_Reg_Id);
                            intent1.putExtra("To_Reg_Id", To_Reg_Id);
                            intent1.putExtra("From_EmirateEnName", From_EmirateEnName);
                            intent1.putExtra("From_RegionEnName", From_RegionEnName);
                            intent1.putExtra("To_EmirateEnName", To_EmirateEnName);
                            intent1.putExtra("To_RegionEnName", To_RegionEnName);
                            intent1.putExtra("MapKey", "Passenger");
                            intent1.putExtra("RouteID", 0);
                            intent1.putExtra("InviteType", "MapLookUp");
                            startActivity(intent1);


                        }
                    });


                }


            }// if Driver


            Log.d("GPs Enabled", String.valueOf(enableGPS));
            if (enableGPS) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom
                        (new LatLng(MyLat, My_Lng), 8.25f));
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
                        new AlertDialog.Builder(MapsActivity.this)
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
                        Toast.makeText(MapsActivity.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {

                return null;


            }
            return null;
        }


        // info window adapter

        @Override
        public View getInfoWindow(Marker marker) {
            return null;


        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }


        // info window click listenre
        @Override
        public void onInfoWindowClick(Marker marker) {

        }
    }    // back thread classs


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("On Resume", "Resume");

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("on Restart", "Restart");
    }
}  //  classs
