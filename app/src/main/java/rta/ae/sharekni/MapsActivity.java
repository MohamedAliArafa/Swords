package rta.ae.sharekni;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2;
    //    int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    MapsActivity context;
    int From_Em_Id;
    int From_Reg_Id;
    int To_Em_Id;
    int To_Reg_Id;
    String ID;

    int Driver_Route_ID;

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
    ImageView pass_icon_trigger_btn, driver_icon_trigger_btn;


    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;

    protected String mLatitudeLabel;
    protected String mLongitudeLabel;

    protected static final String TAG = "Maps Activity";

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
        driver_icon_trigger_btn = (ImageView) findViewById(R.id.driver_icon_trigger_btn);
        pass_icon_trigger_btn = (ImageView) findViewById(R.id.pass_icon_trigger_btn);
        context = this;

//
//        if (ContextCompat.checkSelfPermission(context,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context,
//                Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
//                    Manifest.permission.ACCESS_FINE_LOCATION) &&
//                    ActivityCompat.shouldShowRequestPermissionRationale(context,
//                            Manifest.permission.ACCESS_COARSE_LOCATION)) {
//
//                // Show an expanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {
//
//                // No explanation needed, we can request the permission.
//
//                ActivityCompat.requestPermissions(context,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//
//
//                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
//        }


        buildGoogleApiClient();


        myPrefs = this.getSharedPreferences("myPrefs", 0);
        ID = myPrefs.getString("account_id", "0");
        AccountType = myPrefs.getString("account_type", "");
        Log.d("Account Type Map", AccountType);
        assert AccountType != null;
        if (AccountType.equals("D")) {

            //  MapRelative.setVisibility(View.VISIBLE);
            pass_icon_trigger_btn.setVisibility(View.VISIBLE);
            driver_icon_trigger_btn.setVisibility(View.INVISIBLE);


            pass_icon_trigger_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (i == 'D') {
                        pass_icon_trigger_btn.setVisibility(View.INVISIBLE);
                        driver_icon_trigger_btn.setVisibility(View.VISIBLE);
//                        malefemale.setVisibility(View.INVISIBLE);
//                        femalemale.setVisibility(View.VISIBLE);
//                        malefemale_txt.setTextColor(Color.GRAY);
//                        femalemale_txt.setTextColor(Color.RED);
                        i = 'P';


                        mMap.clear();
                        new backTread().execute();

                    }


                }
            });


            driver_icon_trigger_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (i == 'P') {
                        pass_icon_trigger_btn.setVisibility(View.VISIBLE);
                        driver_icon_trigger_btn.setVisibility(View.INVISIBLE);
//                        malefemale.setVisibility(View.INVISIBLE);
//                        femalemale.setVisibility(View.VISIBLE);
//                        malefemale_txt.setTextColor(Color.GRAY);
//                        femalemale_txt.setTextColor(Color.RED);
                        i = 'D';


                        mMap.clear();
                        new backTread().execute();

                    }


                }
            });


//            femalemale.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (i == 'P') {
//                        femalemale.setVisibility(View.INVISIBLE);
//                        malefemale.setVisibility(View.VISIBLE);
//                        malefemale_txt.setTextColor(Color.RED);
//                        femalemale_txt.setTextColor(Color.GRAY);
//                        i = 'D';
//
//
//                        mMap.clear();
//                        new backTread().execute();
//
//                    }
//
//                }
//            });


//            femalemale_txt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (i == 'D') {
//                        malefemale_txt.setTextColor(Color.GRAY);
//                        femalemale_txt.setTextColor(Color.RED);
//
//                        malefemale.setVisibility(View.INVISIBLE);
//                        femalemale.setVisibility(View.VISIBLE);
//                        i = 'P';
//
//
//                        mMap.clear();
//                        new backTread().execute();
//
//                    }


            //  }
            //    });


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


        mapFragment.getMapAsync(this);

    }


    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request missing location permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            );
        } else {
            // Location permission has been granted, continue as usual.


            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mMap.setMyLocationEnabled(true);

            if (mLastLocation != null) {
//            mLatitudeText.setText(String.format("%s: %f", mLatitudeLabel,
//                    mLastLocation.getLatitude()));
//            mLongitudeText.setText(String.format("%s: %f", mLongitudeLabel,
//                    mLastLocation.getLongitude()));
                Log.d("User Lat ", String.valueOf(mLastLocation.getLatitude()));
                Log.d("User Lat ", String.valueOf(mLastLocation.getLongitude()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom
                        (new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 8.25f));


            } else {
                Toast.makeText(this, R.string.No_Location_Detected, Toast.LENGTH_LONG).show();
            }


        }

        //  mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
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


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request missing location permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            // Location permission has been granted, continue as usual.

            mMap.setMyLocationEnabled(true);


        }

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
                    .setTitle(R.string.Location_Not_Enabled)
                    .setMessage(R.string.Please_turn_on_gps_first)
                    .setPositiveButton(R.string.Turn_On, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            enableGPS = true;
                            Intent gpsOptionsIntent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(gpsOptionsIntent);
                        }
                    })
                    .setNegativeButton(R.string.No_Map, new DialogInterface.OnClickListener() {
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


//        if (((LocationManager) context.getSystemService(Context.LOCATION_SERVICE))
//                .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//
//
//            LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
//            Criteria criteria = new Criteria();
//            String provider = service.getBestProvider(criteria, false);
//            Location location = service.getLastKnownLocation(provider);
//            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
//
//            Log.d("My Lat Lng", String.valueOf(userLocation.latitude));
//            Log.d("My Lat Lng", String.valueOf(userLocation.longitude));
//
//            MyLat = userLocation.latitude;
//            My_Lng = userLocation.longitude;
//
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom
//                    (new LatLng(userLocation.latitude, userLocation.longitude), 8.25f));
//
//
////            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom
////                    (new LatLng(MyLat, My_Lng), 8.25f));
//
//
//        }


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


        // else
        // }


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
                          //  item.setToEmirateId(jsonObject.getInt("ToEmirateId"));
                           // item.setToRegionId(jsonObject.getInt("ToRegionId"));
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
                            RelativeLayout ComingRideRelative = (RelativeLayout) v.findViewById(R.id.ComingRideRelative);
                            ComingRideRelative.setVisibility(View.GONE);

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
                          JSONArray j = new GetData().GetMatchedRoutesForPassengers(Integer.parseInt(ID));
                       // JSONArray j = new GetData().GetPassengersMapLookUp();
                        data = new MapDataModel[j.length()];

                        if (j.length()==0){
                            Toast.makeText(context, R.string.No_passengers_Found, Toast.LENGTH_LONG).show();
                           // context.finish();
                        }

                        for (int i = 0; i < j.length(); i++) {

                            MapDataModel item = new MapDataModel(Parcel.obtain());

                            JSONObject jsonObject = j.getJSONObject(i);

                            // Testing New Service
                            item.setFromRegionArName(jsonObject.getString("FromRegionArName"));
                            item.setFromRegionEnName(jsonObject.getString("FromRegionEnName"));
                            item.setFromEmirateEnName(jsonObject.getString("FromEmirateEnName"));
                            item.setFromEmirateArName(jsonObject.getString("FromEmirateArName"));

                            // Testing Old
                            //   item.setFromRegionArName(jsonObject.getString("FromRegionNameAr"));
                            //   item.setFromRegionEnName(jsonObject.getString("FromRegionNameEn"));
                            //   item.setFromEmirateEnName(jsonObject.getString("FromEmirateNameEn"));
                            //  item.setFromEmirateArName(jsonObject.getString("FromEmirateNameAr"));



                            item.setFromEmirateId(jsonObject.getInt("FromEmirateId"));
                            item.setFromRegionId(jsonObject.getInt("FromRegionId"));
                            item.setToEmirateId(jsonObject.getInt("ToEmirateId"));
                            item.setToRegionId(jsonObject.getInt("ToRegionId"));



                          //  item.setNoOfRoutes(jsonObject.getInt("NoOfRoutes"));
                            item.setNoOFPassengers(jsonObject.getInt("PassengersCount"));
                            item.setDriverRouteID(jsonObject.getInt("DriverRouteId"));

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

                            v = getLayoutInflater().inflate(R.layout.info_window_approved_2, null);
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
                            TextView NoOfPassengers = (TextView) v.findViewById(R.id.NoOfPassengers);



                            String lat = String.valueOf(latLng.latitude).substring(0, 7);
                            String lon = String.valueOf(latLng.longitude).substring(0, 7);
                            String NoOfRoutes_str = String.valueOf(NoOfRoutes);
                            String NoOfPassengers_str = String.valueOf(NoOfPassengers_Count);
                            emirateLat.setText(lat);
                            emiratelong.setText(lon);
                            emirateArName.setText(snippet);
                            emirateEnName.setText(title);
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
                            To_Em_Id=data[i].getToEmirateId();
                            To_Reg_Id=data[i].getToRegionId();
                            Driver_Route_ID =data[i].getDriverRouteID();


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
                            intent1.putExtra("RouteID", Driver_Route_ID);
                            intent1.putExtra("InviteType", "MapLookUp");
                            startActivity(intent1);

                            // TO DO Tip
                            //  intent1.putExtra("InviteType", "DriverRide");


                        }
                    });


                }


            }// if Driver


//            Log.d("GPs Enabled", String.valueOf(enableGPS));
//
//
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom
//                    (new LatLng(MyLat, My_Lng), 8.25f));
//
//
//            }
//
//
//            if (enableGPS) {
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom
//                        (new LatLng(MyLat, My_Lng), 8.25f));
//            }


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


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    mMap.setMyLocationEnabled(true);

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


}  //  classs
