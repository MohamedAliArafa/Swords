package rta.ae.sharekni;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
 * Created by Nezar Saleh on 10/8/2015.
 */
public class DriverGetReviewAdapter extends BaseAdapter {


    private Activity activity;
    List<DriverGetReviewDataModel> driverGetReviewDataModels_items;
    LayoutInflater layoutInflater;
    SharedPreferences myPrefs;

    public DriverGetReviewAdapter(Activity activity,  List<DriverGetReviewDataModel> items) {

        this.driverGetReviewDataModels_items=items;
        this.activity=activity;

        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }//constructor







    @Override
    public int getCount() {
        return driverGetReviewDataModels_items.size();
    }

    @Override
    public Object getItem(int position) {
        return driverGetReviewDataModels_items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (layoutInflater == null)
            layoutInflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.driver_get_review_listitem, null);


        ImageView DriverPhoto = (ImageView) convertView.findViewById(R.id.Driver_Get_Review_im);
        TextView Review = (TextView) convertView.findViewById(R.id.Driver_Get_Review_Review);
        TextView AccountName = (TextView) convertView.findViewById(R.id.Driver_Get_Review_AccountName);
        TextView  AccountNationalityEn= (TextView) convertView.findViewById(R.id.Driver_Get_Review_Nat);
        ImageView Driver_Delete = (ImageView) convertView.findViewById(R.id.Driver_Delete_Review);

        Driver_Delete.setVisibility(View.INVISIBLE);
        final DriverGetReviewDataModel driverGetReviewDataModel = driverGetReviewDataModels_items.get(position);
        int Passenger = driverGetReviewDataModel.getDriverID();
        myPrefs = activity.getSharedPreferences("myPrefs", 0);
        int AccountType = Integer.parseInt(myPrefs.getString("account_id", "0"));
        if (Passenger == AccountType){
            Driver_Delete.setVisibility(View.VISIBLE);
            Driver_Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GetData gd = new GetData();
                    try {
                        String response = gd.Driver_Delete_Review(driverGetReviewDataModel.getReviewID());
                        if (response.equals("\"1\"")) {
                            Toast.makeText(activity, activity.getString(R.string.review_deleted), Toast.LENGTH_SHORT).show();
                            activity.recreate();
                        } else {
                            Toast.makeText(activity, activity.getString(R.string.error), Toast.LENGTH_SHORT).show();
                        }
                        Log.d("res = ", response);
                    } catch (JSONException e) {
                        Toast.makeText(activity, activity.getString(R.string.request_failed), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
        }

        if (driverGetReviewDataModel.getPhoto() != null){
            DriverPhoto.setImageBitmap(driverGetReviewDataModel.getPhoto());
        }else {
            DriverPhoto.setImageResource(R.drawable.defaultdriver);
        }

        AccountName.setText(driverGetReviewDataModel.getAccountName());
        AccountNationalityEn.setText(driverGetReviewDataModel.getAccountNationalityEn());
        Review.setText(driverGetReviewDataModel.getReview());



        return convertView;


    }




}
