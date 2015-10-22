package com.example.nezarsaleh.shareknitest.Arafa.DataModelAdapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nezarsaleh.shareknitest.Arafa.Classes.GetData;
import com.example.nezarsaleh.shareknitest.Arafa.DataModel.BestRouteDataModelDetails;
import com.example.nezarsaleh.shareknitest.R;

import org.json.JSONException;

import java.net.URLEncoder;


public class BestRouteDataModelAdapterDetails extends ArrayAdapter<BestRouteDataModelDetails> {

    int resourse;
    Context context;
    BestRouteDataModelDetails[] BestrouteArray;
    LayoutInflater layoutInflater;
    SharedPreferences myPrefs;
    int Passenger_ID;
    String Review_str;
    EditText Edit_Review_txt;

    public BestRouteDataModelAdapterDetails(Context context, int resource, BestRouteDataModelDetails[] objects) {
        super(context, resource, objects);
        this.context=context;
        this.resourse=resource;
        this.BestrouteArray =objects;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        myPrefs = context.getSharedPreferences("myPrefs", 0);
        Passenger_ID = Integer.parseInt(myPrefs.getString("account_id", "0"));
        View v = convertView;
        ViewHolder vh = null;
        if (v==null)
        {
            v = layoutInflater.inflate(R.layout.quick_search_list_item_2,parent,false);
            vh= new ViewHolder();

            vh.DriverName = (TextView) v.findViewById(R.id.DriverEnName);
            vh.SDG_Route_Start_FromTime = (TextView) v.findViewById(R.id.SDG_Route_Start_FromTime);
            vh.Nationality_en = (TextView) v.findViewById(R.id.Nationality_en);
            vh.SDG_RouteDays = (TextView) v.findViewById(R.id.search_results_days);
            vh.Phone_Message = (ImageView) v.findViewById(R.id.im1);
            vh.Phone_Call = (ImageView) v.findViewById(R.id.im5);
            vh.Route_Join = (ImageView) v.findViewById(R.id.driver_add_pic);
            vh.Route_Review = (ImageView) v.findViewById(R.id.driver_review);

            v.setTag(vh);
        }else
        {
         vh = (ViewHolder) v.getTag();
        }
        final BestRouteDataModelDetails bestRouteDataModel = BestrouteArray[position];
        vh.DriverName.setText(bestRouteDataModel.getDriverName());
        vh.SDG_Route_Start_FromTime.setText((bestRouteDataModel.getSDG_Route_Start_FromTime()));
        vh.Nationality_en.setText(bestRouteDataModel.getNationality_en());
        vh.SDG_RouteDays.setText(bestRouteDataModel.getSDG_RouteDays());

        vh.Phone_Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + bestRouteDataModel.getDriverMobile()));
                context.startActivity(intent);
            }
        });

        vh.Phone_Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + bestRouteDataModel.getDriverMobile()));
                intent.putExtra("sms_body", "Hello " + bestRouteDataModel.getDriverName());
                context.startActivity(intent);
            }
        });

        vh.Route_Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Passenger_ID != 0) {

                    GetData j = new GetData();
                    String response = null;
                    try {
                        response = j.Passenger_SendAlert(bestRouteDataModel.getDriverId(), Passenger_ID, bestRouteDataModel.getRouteId(), "TestCase2");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (response.equals("\"-1\"") || response.equals("\"-2\'")) {
                        Toast.makeText(context, "Cannot Join This Route", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "successfully  Joined", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("join ride res", String.valueOf(response));
                }else {
                    Toast.makeText(context, "Please Login First", Toast.LENGTH_SHORT).show();
                }
            }
        });


        vh.Route_Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Passenger_ID != 0){
                    final GetData j = new GetData();
                    Review_str = "";
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.review_dialog);
                    Button btn = (Button) dialog.findViewById(R.id.Review_Btn);
                    Edit_Review_txt = (EditText) dialog.findViewById(R.id.Edit_Review_txt);
                    dialog.show();
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Review_str = Edit_Review_txt.getText().toString();
                            try {
                                String response = j.Passenger_Review_Driver(bestRouteDataModel.getDriverId(), Passenger_ID,bestRouteDataModel.getRouteId(), URLEncoder.encode(Review_str));
                                if (response.equals("\"-1\"") || response.equals("\"-2\'")) {
                                    Toast.makeText(context, "Cannot Review", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }else {
                    Toast.makeText(context, "Please Login First", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return v;
    }



    static class ViewHolder
    {
        ImageView Phone_Message;
        ImageView Phone_Call;
        ImageView Route_Join;
        ImageView Route_Review;
        TextView DriverName;
        TextView SDG_Route_Start_FromTime ;
        TextView Nationality_en;
        TextView SDG_RouteDays ;


    }

}
