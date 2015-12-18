package rta.ae.sharekni;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import rta.ae.sharekni.Arafa.Activities.Profile;
import rta.ae.sharekni.Arafa.Classes.GetData;
import rta.ae.sharekni.Arafa.DataModel.BestRouteDataModel;

/**
 * Created by Nezar Saleh on 10/13/2015.
 */
public class PassngerApprovedRidesAdapter extends ArrayAdapter<BestRouteDataModel> {

    int resourse;
    Activity activity;
    BestRouteDataModel[] BestrouteArray;
    LayoutInflater layoutInflater;


    public PassngerApprovedRidesAdapter(Activity activity, int resource, BestRouteDataModel[] objects) {
        super(activity, resource, objects);
        this.activity = activity;
        this.resourse = resource;
        this.BestrouteArray = objects;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final BestRouteDataModel bestRouteDataModel = BestrouteArray[position];
        View v = convertView;
        final ViewHolder vh;
        if (v == null) {
            v = layoutInflater.inflate(resourse, parent, false);
            vh = new ViewHolder();
            vh.FromEm = (TextView) v.findViewById(R.id.em_from);
            vh.ToEm = (TextView) v.findViewById(R.id.em_to);
            vh.FromReg = (TextView) v.findViewById(R.id.reg_from);
            vh.ToReg = (TextView) v.findViewById(R.id.reg_to);
            vh.RouteEnName = (TextView) v.findViewById(R.id.driver_profile_RouteEnName);
            vh.StartFromTime = (TextView) v.findViewById(R.id.StartFromTime);
            //   vh.EndToTime_= (TextView) v.findViewById(R.id.EndToTime_);
            // vh.driver_profile_dayWeek= (TextView) v.findViewById(R.id.driver_profile_dayWeek);

            vh.Relative_Leave = (RelativeLayout) v.findViewById(R.id.Relative_Leave);
            vh.Relative_Details = (RelativeLayout) v.findViewById(R.id.Relative_Details);
            vh.Relative_Driver = (RelativeLayout) v.findViewById(R.id.Relative_Driver);


            vh.Relative_Driver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, Profile.class);
                    intent.putExtra("DriverID", vh.Driver_Id);
                    activity.startActivity(intent);
                }
            });


            vh.Relative_Leave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    new AlertDialog.Builder(activity)
                            .setTitle(R.string.Leave_Ride_Str)
                            .setMessage(R.string.Are_you_sure_leave_ride)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                    GetData gd = new GetData();
                                    try {
                                        String response = gd.Passenger_LeaveRide(bestRouteDataModel.getRoutePassengerId());
                                        if (response.equals("\"1\"")) {
                                            Toast.makeText(activity, R.string.You_have_leaved_this_ride, Toast.LENGTH_SHORT).show();
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


            vh.Relative_Details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, RideDetailsPassenger.class);
                    intent.putExtra("RouteID", bestRouteDataModel.getRoute_id());
                    intent.putExtra("PassengerID", bestRouteDataModel.getPassenger_ID());
                    intent.putExtra("DriverID", vh.Driver_Id);
                    intent.putExtra("FLAG_1", 1);
                    activity.startActivity(intent);
                }
            });


            v.setTag(vh);
        } else {
            vh = (ViewHolder) v.getTag();
        }

        vh.FromEm.setText(bestRouteDataModel.getFromEm());
        vh.ToEm.setText(bestRouteDataModel.getToEm());
        vh.FromReg.setText(bestRouteDataModel.getFromReg());
        vh.ToReg.setText(bestRouteDataModel.getToReg());
        vh.FromEmId = bestRouteDataModel.getFromEmId();
        vh.FromRegid = bestRouteDataModel.getFromRegid();
        vh.ToEmId = bestRouteDataModel.getToEmId();
        vh.ToRegId = bestRouteDataModel.getToRegId();

        StringBuffer res = new StringBuffer();
        String[] strArr = bestRouteDataModel.getRouteName().split(" ");
        for (String str : strArr) {
            char[] stringArray = str.trim().toCharArray();
            stringArray[0] = Character.toUpperCase(stringArray[0]);
            str = new String(stringArray);
            res.append(str).append(" ");
        }
        vh.RouteEnName.setText(res);
        vh.StartFromTime.setText(bestRouteDataModel.getStartFromTime());
//        vh.EndToTime_.setText(bestRouteDataModel.getEndToTime_());
        vh.Driver_Id = bestRouteDataModel.getDriver_ID();
        vh.Route_id = bestRouteDataModel.getRoute_id();
        vh.Passenger_id = bestRouteDataModel.getPassenger_ID();


        //   vh.driver_profile_dayWeek.setText(bestRouteDataModel.getDriver_profile_dayWeek());
        return v;


    }


    static class ViewHolder {
        TextView FromEm;
        TextView ToEm;
        TextView FromReg;
        TextView ToReg;
        TextView RouteEnName;
        TextView StartFromTime;
        //   TextView EndToTime_;
        // TextView driver_profile_dayWeek;
        int FromEmId, ToEmId, FromRegid, ToRegId;

        RelativeLayout Relative_Leave, Relative_Details, Relative_Driver;
        int Driver_Id;
        int Route_id;
        int Passenger_id;


    }


}
