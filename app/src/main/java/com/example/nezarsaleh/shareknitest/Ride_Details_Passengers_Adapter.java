package com.example.nezarsaleh.shareknitest;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nezarsaleh.shareknitest.Arafa.Classes.GetData;

import org.json.JSONException;

import java.util.List;

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

        final Ride_Details_Passengers_DataModel m = PassengersItems.get(position);
        StringBuffer res = new StringBuffer();
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
                GetData gd = new GetData();
                try {
                    String response = gd.Driver_Remove_Passenger(m.getPassengerId());
                    Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return convertView;


    }
}
