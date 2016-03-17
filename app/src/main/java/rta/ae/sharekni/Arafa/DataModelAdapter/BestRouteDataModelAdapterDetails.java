package rta.ae.sharekni.Arafa.DataModelAdapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.pkmmte.view.CircularImageView;

import rta.ae.sharekni.Arafa.Classes.AppController;
import rta.ae.sharekni.Arafa.Classes.GetData;
import rta.ae.sharekni.Arafa.DataModel.BestRouteDataModelDetails;
import rta.ae.sharekni.R;


public class BestRouteDataModelAdapterDetails extends ArrayAdapter<BestRouteDataModelDetails> {

    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 2;
    int resourse;
    Activity context;
    BestRouteDataModelDetails[] BestrouteArray;
    LayoutInflater layoutInflater;
    SharedPreferences myPrefs;
    int Passenger_ID;
    String Review_str;
    EditText Edit_Review_txt;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    String URL = GetData.PhotoURL;


    public BestRouteDataModelAdapterDetails(Activity context, int resource, BestRouteDataModelDetails[] objects) {
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

            if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();



            vh.DriverName = (TextView) v.findViewById(R.id.DriverEnName);
            vh.SDG_Route_Start_FromTime = (TextView) v.findViewById(R.id.SDG_Route_Start_FromTime);
            vh.Nationality_en = (TextView) v.findViewById(R.id.Nationality_en);
            vh.SDG_RouteDays = (TextView) v.findViewById(R.id.search_results_days);
            vh.Phone_Message = (ImageView) v.findViewById(R.id.im1);
            vh.Phone_Call = (ImageView) v.findViewById(R.id.im5);
            vh.Rating = (TextView) v.findViewById(R.id.Best_Drivers_Item_rate);
            vh.LastSeenText= (TextView) v.findViewById(R.id.LastSeenText);
            vh.LastSeenTvValue = (TextView) v.findViewById(R.id.LastSeenTvValue);
            vh.Green_Points_txt= (TextView) v.findViewById(R.id.Green_Points_txt);
            vh.Green_co2_saving_txt= (TextView) v.findViewById(R.id.Green_co2_saving_txt);


            // Testing Line
            vh.LastSeenText.setVisibility(View.GONE);
            vh.LastSeenTvValue.setVisibility(View.GONE);

           // vh.Route_Join = (ImageView) v.findViewById(R.id.driver_add_pic);
           // vh.Route_Review = (ImageView) v.findViewById(R.id.driver_review);
            vh.Photo = (CircularImageView) v.findViewById(R.id.search_list_photo);
            v.setTag(vh);
        }else
        {
         vh = (ViewHolder) v.getTag();
        }
        final BestRouteDataModelDetails bestRouteDataModel = BestrouteArray[position];
        StringBuffer res = new StringBuffer();
        String[] strArr = bestRouteDataModel.getDriverName().split(" ");
        for (String str : strArr) {
            char[] stringArray = str.trim().toCharArray();
            if (stringArray.length != 0){
                stringArray[0] = Character.toUpperCase(stringArray[0]);
                str = new String(stringArray);
                res.append(str).append(" ");
            }
        }
        vh.DriverName.setText(res);
        vh.SDG_Route_Start_FromTime.setText((bestRouteDataModel.getSDG_Route_Start_FromTime()));
        vh.Nationality_en.setText(bestRouteDataModel.getNationality_en());
        vh.SDG_RouteDays.setText(bestRouteDataModel.getSDG_RouteDays());
        vh.Rating.setText(bestRouteDataModel.getDriverRating());

        vh.Green_co2_saving_txt.setText(bestRouteDataModel.getGreenCo2Saving());
        vh.Green_Points_txt.setText(bestRouteDataModel.getGreenPoints());



            // Producation Line
       if(bestRouteDataModel.getLastSeen().equals("null")){
            vh.LastSeenText.setVisibility(View.GONE);
            vh.LastSeenTvValue.setVisibility(View.GONE);
        }else {
            vh.LastSeenText.setVisibility(View.VISIBLE);
            vh.LastSeenTvValue.setVisibility(View.VISIBLE);
            vh.LastSeenTvValue.setText(bestRouteDataModel.getLastSeen());

        }


       // vh.LastSeenText.setVisibility(View.GONE);
//        vh.Photo.setImageUrl(URL + bestRouteDataModel.getPhotoURl() , imageLoader);
        if (bestRouteDataModel.getDriverPhoto() != null){
            vh.Photo.setImageBitmap(bestRouteDataModel.getDriverPhoto());
        }else {
            vh.Photo.setImageResource(R.drawable.defaultdriver);
        }
        Log.d("Photo path",URL + bestRouteDataModel.getPhotoURl());


        vh.Phone_Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tel:", bestRouteDataModel.getDriverMobile());

                if (bestRouteDataModel.getDriverMobile()==null || bestRouteDataModel.getDriverMobile().equals("")) {
                    Toast.makeText(context, R.string.No_Phone_number , Toast.LENGTH_SHORT).show();
                }else {

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Request missing location permission.
                        ActivityCompat.requestPermissions(context,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE
                        );
                    } else {


                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + bestRouteDataModel.getDriverMobile()));
                        context.startActivity(intent);

                    }






                }



            }
        });

        vh.Phone_Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bestRouteDataModel.getDriverMobile() == null || bestRouteDataModel.getDriverMobile().equals("")) {
                    Toast.makeText(context, R.string.No_Phone_number , Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + bestRouteDataModel.getDriverMobile()));
                    intent.putExtra("sms_body", "Hello " + bestRouteDataModel.getDriverName());
                    context.startActivity(intent);
                }


                }
        });

        /*
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

*/

        /*
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


        */

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
        TextView Rating;
        CircularImageView Photo;
        TextView LastSeenText;
        TextView LastSeenTvValue;
        TextView Green_Points_txt;
        TextView Green_co2_saving_txt;


    }



    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED  ) {
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
