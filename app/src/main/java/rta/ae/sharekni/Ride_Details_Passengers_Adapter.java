package rta.ae.sharekni;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.List;

import rta.ae.sharekni.Arafa.Classes.GetData;

public class Ride_Details_Passengers_Adapter extends BaseAdapter {
    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 2;

    int NoOfStars, New_Starts;
    private Activity activity;
    private LayoutInflater inflater;
    private List<Ride_Details_Passengers_DataModel> PassengersItems;


    public Ride_Details_Passengers_Adapter(Activity activity, List<Ride_Details_Passengers_DataModel> PassengersItems) {
        this.activity = activity;
        this.PassengersItems = PassengersItems;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return PassengersItems.size();
    }

    @Override
    public Object getItem(int position) {
        return PassengersItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.ride_details_passengers_list_item, null);


        TextView AccountName = (TextView) convertView.findViewById(R.id.AccountName);
        TextView AccountNationalityEn = (TextView) convertView.findViewById(R.id.AccountNationalityEn);
        ImageView Driver_Remove_passenger = (ImageView) convertView.findViewById(R.id.Driver_Remove_Passenger);
        ImageView passenger_lits_item_call = (ImageView) convertView.findViewById(R.id.passenger_lits_item_call);
        ImageView passenger_lits_item_Msg = (ImageView) convertView.findViewById(R.id.passenger_lits_item_Msg);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar2);
        ratingBar.setStepSize(1);
//        ratingBar.setRating(m.getRate());
        final Ride_Details_Passengers_DataModel m = PassengersItems.get(position);
        final StringBuffer res = new StringBuffer();
        String[] strArr = m.getAccountName().split(" ");
        NoOfStars = m.getRate();
        for (String str : strArr) {
            char[] stringArray = str.trim().toCharArray();
            stringArray[0] = Character.toUpperCase(stringArray[0]);
            str = new String(stringArray);
            res.append(str).append(" ");
        }
        AccountName.setText(res);
        AccountNationalityEn.setText(m.getAccountNationalityEn());
        ratingBar.setRating(NoOfStars);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                New_Starts = (int) rating;
                int passengetId = m.getPassengerId();
                new ratePassenger(passengetId, New_Starts, m.getDriverId(), m.getRouteId()).execute();
            }
        });

        Driver_Remove_passenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(activity)
                        .setTitle(R.string.Delete_msg)
                        .setMessage(R.string.please_confirm_to_delete)
                        .setPositiveButton(R.string.Confirm_str, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                GetData gd = new GetData();
                                try {
                                    String response = gd.Driver_Remove_Passenger(PassengersItems.get(position).getID());
                                    Log.d("delete passenger", response);
//                    Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();
                                    if (response.equals("\"1\"")) {
                                        Toast.makeText(activity, R.string.passenger_deleted_successfully, Toast.LENGTH_SHORT).show();
                                        activity.recreate();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        })
                        .setNegativeButton(R.string.Cancel_msg, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
            }
        });


        passenger_lits_item_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m.getAccountMobile() == null || m.getAccountMobile().equals("")) {
                    Toast.makeText(activity, R.string.No_Phone_Number_msg, Toast.LENGTH_SHORT).show();
                } else {
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Request missing location permission.
                        ActivityCompat.requestPermissions(activity,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE
                        );
                    } else {

                        try {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + PassengersItems.get(position).getAccountMobile()));
                            activity.startActivity(intent);
                        } catch (SecurityException e) {
                            Log.d("Passngr list", e.toString());
                        }


                    }


                }
            }
        });


        passenger_lits_item_Msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m.getAccountMobile() == null || m.getAccountMobile().equals("") || m.getAccountMobile().equals("null")) {
                    Toast.makeText(activity, "No Phone Number", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + PassengersItems.get(position).getAccountMobile()));
                    intent.putExtra("sms_body", "Hello " + m.getAccountMobile());
                    activity.startActivity(intent);
                }

            }
        });


        return convertView;


    }

    private class ratePassenger extends AsyncTask {

        String res;
        int passengerId;
        int Stars, Driver_ID, Route_ID;

        public ratePassenger(int passengetId, int Stars, int Driver_ID, int Route_ID) {
            this.passengerId = passengetId;
            this.Stars = Stars;
            this.Driver_ID = Driver_ID;
            this.Route_ID = Route_ID;

        }

        @Override
        protected void onPostExecute(Object o) {
            Log.d("res", res);
            if (res.equals("\"1\"")) {
                Toast.makeText(activity, R.string.rate_submitted, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, R.string.rate_submit_failed, Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(o);
        }

        @Override
        protected Object doInBackground(Object[] params) {
            GetData gd = new GetData();
            try {
                res = gd.Driver_RatePassenger(Driver_ID, passengerId, Route_ID, Stars);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }








    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED  ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

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

}
