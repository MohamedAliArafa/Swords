package rta.ae.sharekni.Arafa.DataModelAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.pkmmte.view.CircularImageView;

import rta.ae.sharekni.Arafa.Classes.AppController;
import rta.ae.sharekni.Arafa.Classes.CircularNetworkImageView;
import rta.ae.sharekni.Arafa.Classes.GetData;
import rta.ae.sharekni.Arafa.Classes.ImageDecoder;
import rta.ae.sharekni.Arafa.DataModel.BestDriverDataModel;

import rta.ae.sharekni.R;


import java.util.List;

public class BestDriverDataModelAdapter extends BaseAdapter {


    private Activity activity;
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

        //RatingBar rating = (RatingBar) convertView.findViewById(R.id.ratingBar);

        //  Button Best_Drivers_Item_Details= (Button) convertView.findViewById(R.id.Best_Drivers_Item_Details);
        ImageView Phone_Call = (ImageView) convertView.findViewById(R.id.im5);
        ImageView Phone_Message = (ImageView) convertView.findViewById(R.id.im1);


        final BestDriverDataModel m = driverItems.get(position);
//        GetData gd = new GetData();
//        Photo.setImageBitmap(gd.GetImage(m.getPhotoURL()));
        if (!m.getPhotoURL().equals("NoImage.png")){
            ImageDecoder im = new ImageDecoder();
            im.stringRequest(m.getPhotoURL(), Photo, activity);
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
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + m.getPhoneNumber()));
                    activity.startActivity(intent);
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


}