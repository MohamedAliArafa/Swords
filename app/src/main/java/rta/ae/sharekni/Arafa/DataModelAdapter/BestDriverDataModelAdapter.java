package rta.ae.sharekni.Arafa.DataModelAdapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.pkmmte.view.CircularImageView;

import java.util.List;

import rta.ae.sharekni.Arafa.Classes.AppController;
import rta.ae.sharekni.Arafa.Classes.GetData;
import rta.ae.sharekni.Arafa.DataModel.BestDriverDataModel;
import rta.ae.sharekni.R;

public class BestDriverDataModelAdapter extends BaseAdapter {


    private Activity activity;
    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 2;
    private LayoutInflater inflater;
    private List<BestDriverDataModel> driverItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    String URL = GetData.PhotoURL;

    public BestDriverDataModelAdapter(Activity activity, List<BestDriverDataModel> driverItems) {
        this.activity = activity;
        this.driverItems = driverItems;
    }

    @Override
    public int getCount() {
        return driverItems.size();
    }

    @Override
    public Object getItem(int location) {
        return driverItems.get(location);
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
            convertView = inflater.inflate(R.layout.best_drivers_list_item, null);


        if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();

        CircularImageView Photo = (CircularImageView) convertView.findViewById(R.id.ivProfile);
        TextView Name = (TextView) convertView.findViewById(R.id.tvName);
        TextView Nat = (TextView) convertView.findViewById(R.id.tvNat);
        TextView Rat = (TextView) convertView.findViewById(R.id.Best_Drivers_Item_rate);
        TextView   LastSeenText= (TextView) convertView.findViewById(R.id.LastSeenText);
        TextView LastSeenTvValue = (TextView) convertView.findViewById(R.id.LastSeenTvValue);
        TextView Green_Points_txt = (TextView) convertView.findViewById(R.id.Green_Points_txt);
        TextView Green_co2_saving_txt = (TextView) convertView.findViewById(R.id.Green_co2_saving_txt);

        //RatingBar rating = (RatingBar) convertView.findViewById(R.id.ratingBar);

        //  Button Best_Drivers_Item_Details= (Button) convertView.findViewById(R.id.Best_Drivers_Item_Details);
        ImageView Phone_Call = (ImageView) convertView.findViewById(R.id.im5);
        ImageView Phone_Message = (ImageView) convertView.findViewById(R.id.im1);


        final BestDriverDataModel m = driverItems.get(position);
        if (m.getPhoto() != null){
            Photo.setImageBitmap(m.getPhoto());
        }else {
            Photo.setImageResource(R.drawable.defaultdriver);
        }

        StringBuffer res = new StringBuffer();

        String[] strArr = m.getName().split(" ");

        for (String str : strArr) {
            char[] stringArray = str.trim().toCharArray();
            if (stringArray.length != 0) {
                stringArray[0] = Character.toUpperCase(stringArray[0]);
                str = new String(stringArray);

                res.append(str).append(" ");
            }

        }
        Name.setText(res);

        Nat.setText(m.getNationality());
        Rat.setText(Integer.toString(m.getRating()));

        Green_Points_txt.setText(m.getGreenPoints());
        Green_co2_saving_txt.setText(m.getCO2Saved());




        LastSeenText.setVisibility(View.INVISIBLE);
        LastSeenTvValue.setVisibility(View.INVISIBLE);

        if(m.getLastSeen().equals("null")){
            LastSeenText.setVisibility(View.INVISIBLE);
            LastSeenTvValue.setVisibility(View.INVISIBLE);
        }else {
            LastSeenText.setVisibility(View.VISIBLE);
            LastSeenTvValue.setVisibility(View.VISIBLE);
            LastSeenTvValue.setText(m.getLastSeen());

        }

//        Best_Drivers_Item_Details.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activity, Profile.class);
//                intent.putExtra("DriverID", m.getID());
//                activity.startActivity(intent);
//            }
//        });


        Phone_Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m.getPhoneNumber() == null || m.getPhoneNumber().equals("")) {

                    Toast.makeText(activity, "No Phone Number" , Toast.LENGTH_SHORT).show();
                } else {

                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Request missing location permission.
                        ActivityCompat.requestPermissions(activity,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE
                        );

                    } else {


                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + m.getPhoneNumber()));
                        activity.startActivity(intent);

                    }


                }
            }
        });

        Phone_Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m.getPhoneNumber()==null || m.getPhoneNumber().equals("")){
                    Toast.makeText(activity, "No Phone Number" , Toast.LENGTH_SHORT).show();
                }else {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + m.getPhoneNumber()));
                    intent.putExtra("sms_body", "Hello " + m.getName());
                    activity.startActivity(intent);
                }
            }
        });


        return convertView;
    }


    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED  ) {
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