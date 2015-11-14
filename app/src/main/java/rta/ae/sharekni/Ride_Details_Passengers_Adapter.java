package rta.ae.sharekni;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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


        final Ride_Details_Passengers_DataModel m = PassengersItems.get(position);
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
                        .setTitle(R.string.Delete_Passenger_dialog_str)
                        .setMessage(R.string.Are_You_Sure_msg_dilog)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
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
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
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
                    Toast.makeText(activity, "No Phone Number", Toast.LENGTH_SHORT).show();
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
}
