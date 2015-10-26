package com.example.nezarsaleh.shareknitest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nezarsaleh.shareknitest.Arafa.Activities.Profile;
import com.example.nezarsaleh.shareknitest.Arafa.DataModel.BestRouteDataModel;

/**
 * Created by Nezar Saleh on 10/13/2015.
 */
public class PassngerApprovedRidesAdapter extends ArrayAdapter<BestRouteDataModel> {

    int resourse;
    Context context;
    BestRouteDataModel[] BestrouteArray;
    LayoutInflater layoutInflater;


    public PassngerApprovedRidesAdapter(Context context, int resource, BestRouteDataModel[] objects) {
        super(context, resource, objects);
        this.context=context;
        this.resourse=resource;
        this.BestrouteArray =objects;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final ViewHolder vh;
        if (v==null)
        {
            v = layoutInflater.inflate(resourse,parent,false);
            vh= new ViewHolder();
            vh.FromEm = (TextView) v.findViewById(R.id.em_from);
            vh.ToEm = (TextView) v.findViewById(R.id.em_to);
            vh.FromReg= (TextView) v.findViewById(R.id.reg_from);
            vh.ToReg = (TextView) v.findViewById(R.id.reg_to);
            vh.RouteEnName= (TextView) v.findViewById(R.id.driver_profile_RouteEnName);
            vh.StartFromTime= (TextView) v.findViewById(R.id.StartFromTime);
            vh.EndToTime_= (TextView) v.findViewById(R.id.EndToTime_);
            // vh.driver_profile_dayWeek= (TextView) v.findViewById(R.id.driver_profile_dayWeek);

            vh.Relative_Reviews= (RelativeLayout) v.findViewById(R.id.Relative_Reviews);
            vh.Relative_Details= (RelativeLayout) v.findViewById(R.id.Relative_Details);
            vh.Relative_Driver = (RelativeLayout) v.findViewById(R.id.Relative_Driver);



            vh.Relative_Driver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Profile.class);
                    intent.putExtra("DriverID", vh.Driver_Id);
                    context.startActivity(intent);
                }
            });


            v.setTag(vh);
        }else
        {
            vh = (ViewHolder) v.getTag();
        }
        BestRouteDataModel bestRouteDataModel = BestrouteArray[position];
        vh.FromEm.setText(bestRouteDataModel.getFromEm());
        vh.ToEm.setText(bestRouteDataModel.getToEm());
        vh.FromReg.setText(bestRouteDataModel.getFromReg());
        vh.ToReg.setText(bestRouteDataModel.getToReg());
        vh.FromEmId=bestRouteDataModel.getFromEmId();
        vh.FromRegid=bestRouteDataModel.getFromRegid();
        vh.ToEmId=bestRouteDataModel.getToEmId();
        vh.ToRegId=bestRouteDataModel.getToRegId();
        vh.RouteEnName.setText(bestRouteDataModel.getRouteName());
        vh.StartFromTime.setText(bestRouteDataModel.getStartFromTime());
        vh.EndToTime_.setText(bestRouteDataModel.getEndToTime_());
        vh.Driver_Id = bestRouteDataModel.getDriver_ID();








        //   vh.driver_profile_dayWeek.setText(bestRouteDataModel.getDriver_profile_dayWeek());
        return v;




    }


    static class ViewHolder
    {
        TextView FromEm;
        TextView ToEm;
        TextView FromReg;
        TextView ToReg;
        TextView RouteEnName;
        TextView StartFromTime;
        TextView EndToTime_;
        // TextView driver_profile_dayWeek;
        int FromEmId,ToEmId,FromRegid,ToRegId;

        RelativeLayout Relative_Reviews,Relative_Details,Relative_Driver;
        int Driver_Id;



    }



}
