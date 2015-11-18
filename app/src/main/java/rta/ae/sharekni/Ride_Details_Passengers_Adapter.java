package rta.ae.sharekni;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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

/**
 * Created by Nezar Saleh on 10/6/2015.
 */
public class Ride_Details_Passengers_Adapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Ride_Details_Passengers_DataModel> PassengersItems;
    Ride_Details_Passengers_DataModel m;
    int NoOfStars;


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
    public View getView(int position, View convertView, ViewGroup parent) {


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

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                NoOfStars = (int) rating;
                new ratePassenger().execute();
            }
        });


        m = PassengersItems.get(position);
        final StringBuffer res = new StringBuffer();
        String[] strArr = m.getAccountName().split(" ");
        for (String str : strArr) {
            char[] stringArray = str.trim().toCharArray();
            stringArray[0] = Character.toUpperCase(stringArray[0]);
            str = new String(stringArray);
            res.append(str).append(" ");
        }
        AccountName.setText(res);
        AccountNationalityEn.setText(m.getAccountNationalityEn());


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
                                    String response = gd.Driver_Remove_Passenger(m.getPassengerId());
                                    Log.d("delete passenger", response);
//                    Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();
                                    if (response.equals("1")) {
                                        Toast.makeText(activity, R.string.passenger_deleted_successfully, Toast.LENGTH_SHORT).show();
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

                    try {


                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + m.getAccountMobile()));
                        activity.startActivity(intent);
                    } catch (SecurityException e) {
                        Log.d("Passngr list", e.toString());
                    }

                }
            }
        });


        passenger_lits_item_Msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m.getAccountMobile() == null || m.getAccountMobile().equals("")) {
                    Toast.makeText(activity, "No Phone Number", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + m.getAccountMobile()));
                    intent.putExtra("sms_body", "Hello " + m.getAccountMobile());
                    activity.startActivity(intent);
                }

            }
        });


        return convertView;


    }

    private class ratePassenger extends AsyncTask {

        String res;

        @Override
        protected void onPostExecute(Object o) {
            Log.d("res", res);
            if (res.equals("\"1\"")){
                Toast.makeText(activity, R.string.rate_submitted, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(activity, R.string.rate_submit_failed, Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(o);
        }

        @Override
        protected Object doInBackground(Object[] params) {
            GetData gd = new GetData();
            try {
                res = gd.Driver_RatePassenger(m.getDriverId(), m.getPassengerId(), m.getRouteId(), NoOfStars);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
