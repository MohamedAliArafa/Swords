package rta.ae.sharekni;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Nezar Saleh on 11/14/2015.
 */
public class User_vehicles_Adapter extends ArrayAdapter<User_Vehicles_Data_Model> {


    int resourse;
    Activity activity;
    User_Vehicles_Data_Model[] user_vehicles_array;
    LayoutInflater layoutInflater;


    public User_vehicles_Adapter(Activity activity, int resource, User_Vehicles_Data_Model[] objects) {
        super(activity, resource, objects);

        this.activity = activity;
        this.resourse = resource;
        this.user_vehicles_array = objects;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final User_Vehicles_Data_Model user_vehicles_data_model = user_vehicles_array[position];
        View v = convertView;
        final ViewHolder vh;
        if (v==null)
        {
            v = layoutInflater.inflate(resourse,parent,false);
            vh= new ViewHolder();
            vh.user_vehicles_plate_code = (TextView) v.findViewById(R.id.user_vehicles_plate_code);
            vh.user_vehicles_plate_number = (TextView) v.findViewById(R.id.user_vehicles_plate_number);

            v.setTag(vh);
        }else
        {
            vh = (ViewHolder) v.getTag();
        }

        vh.user_vehicles_plate_code.setText(user_vehicles_data_model.getPlateCode());
        vh.user_vehicles_plate_number.setText(user_vehicles_data_model.getPlateNumber());


        //   vh.driver_profile_dayWeek.setText(bestRouteDataModel.getDriver_profile_dayWeek());
        return v;

    }

    static class ViewHolder{

        TextView user_vehicles_plate_code;
        TextView user_vehicles_plate_number;
    }


}
