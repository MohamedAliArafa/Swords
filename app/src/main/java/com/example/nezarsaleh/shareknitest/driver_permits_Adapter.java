package com.example.nezarsaleh.shareknitest;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nezarsaleh.shareknitest.Arafa.DataModel.BestRouteDataModel;

import java.util.List;

/**
 * Created by Nezar Saleh on 10/31/2015.
 */
public class driver_permits_Adapter extends ArrayAdapter<BestRouteDataModel> {


    int resourse;
    Context context;
    BestRouteDataModel[] BestrouteArray;
    LayoutInflater layoutInflater;

    public driver_permits_Adapter(Context context, int resource, BestRouteDataModel[] objects) {
        super(context, resource, objects);
        this.context=context;
        this.resourse=resource;
        this.BestrouteArray =objects;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public driver_permits_Adapter(Activity con, int top_rides_custom_row, List<BestRouteDataModel> arr) {
        super(con, top_rides_custom_row, arr);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder vh;
        if (v==null)
        {
            v = layoutInflater.inflate(resourse,parent,false);
            vh= new ViewHolder();
            vh.FromEm = (TextView) v.findViewById(R.id.em_from);
            vh.ToEm = (TextView) v.findViewById(R.id.em_to);
            vh.ToReg = (TextView) v.findViewById(R.id.reg_to);
            vh.RouteEnName= (TextView) v.findViewById(R.id.driver_profile_RouteEnName);


            v.setTag(vh);
        }else
        {
            vh = (ViewHolder) v.getTag();
        }
        BestRouteDataModel bestRouteDataModel = BestrouteArray[position];
        vh.FromEm.setText(bestRouteDataModel.getFromEm());
        vh.ToEm.setText(bestRouteDataModel.getToEm());
        vh.ToReg.setText(bestRouteDataModel.getToReg());



        StringBuffer res = new StringBuffer();

        String[] strArr = bestRouteDataModel.getRouteName().split(" ");
        for (String str : strArr) {
            char[] stringArray = str.trim().toCharArray();
            stringArray[0] = Character.toUpperCase(stringArray[0]);
            str = new String(stringArray);
            res.append(str).append(" ");
        }
        vh.RouteEnName.setText(res);

        return v;
    }


    static class ViewHolder
    {
        TextView FromEm;
        TextView ToEm;
        TextView ToReg;
        TextView RouteEnName;

    }

}
