package rta.ae.sharekni;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.pkmmte.view.CircularImageView;

import org.json.JSONException;

import java.net.URLEncoder;
import java.util.List;

import rta.ae.sharekni.Arafa.Classes.AppController;
import rta.ae.sharekni.Arafa.Classes.GetData;

/**
 * Created by nezar on 9/20/2015.
 */
public class QuickSearchResultAdapter extends BaseAdapter {


    private Activity activity;
    private LayoutInflater inflater;
    private List<QuickSearchDataModel> searchItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    String URL = GetData.PhotoURL;
    String str_Remarks = "";
    EditText Edit_Review_txt;
    GetData j = new GetData();

    public QuickSearchResultAdapter(Activity activity, List<QuickSearchDataModel> searchItems) {
        this.activity = activity;
        this.searchItems = searchItems;
    }


    @Override
    public int getCount() {
        return searchItems.size();
    }

    @Override
    public Object getItem(int position) {
        return searchItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        final QuickSearchDataModel item = searchItems.get(position);


        if (item.getMapKey().equals("Passenger")){

            if (inflater == null)
                inflater = (LayoutInflater) activity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null)
                convertView = inflater.inflate(R.layout.passenger_list_search, null);


            if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();

            CircularImageView Photo = (CircularImageView) convertView.findViewById(R.id.search_list_photo);
         //   TextView SDG_Route_Start_FromTime = (TextView) convertView.findViewById(R.id.SDG_Route_Start_FromTime);
            TextView DriverEnName = (TextView) convertView.findViewById(R.id.DriverEnName);
            TextView Nationality_en = (TextView) convertView.findViewById(R.id.Nationality_en);
           // TextView SDG_RouteDays = (TextView) convertView.findViewById(R.id.search_results_days);
            TextView Best_Drivers_Item_rate = (TextView) convertView.findViewById(R.id.Best_Drivers_Item_rate);
            TextView LastSeenTvValue = (TextView) convertView.findViewById(R.id.LastSeenTvValue);
            final Button PassengerSendInvite = (Button) convertView.findViewById(R.id.PassengerSendInvite);


//        Photo.sectImageUrl(URL + item.getAccountPhoto(), imageLoader);



            if (item.getDriverPhoto() != null){
                Photo.setImageBitmap(item.getDriverPhoto());
            }else {
                Photo.setImageResource(R.drawable.defaultdriver);
            }



            StringBuffer res = new StringBuffer();

            String[] strArr = item.getAccountName().split(" ");

            for (String str : strArr) {
                char[] stringArray = str.trim().toCharArray();
                if (stringArray.length != 0) {


                    stringArray[0] = Character.toUpperCase(stringArray[0]);
                    str = new String(stringArray);

                    res.append(str).append(" ");

                }

            }

            DriverEnName.setText(res);




            Nationality_en.setText(item.getNationality_en());

            Best_Drivers_Item_rate.setText(item.getRating());
            LastSeenTvValue.setText(item.getLastSeen());

            PassengerSendInvite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getAccountID()==0){
                        final Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.please_log_in_dialog);
                        Button btn = (Button) dialog.findViewById(R.id.noroute_id);
                        TextView Text_3 = (TextView) dialog.findViewById(R.id.Text_3);
                        Button No_Btn = (Button) dialog.findViewById(R.id.No_Btn);
                        Text_3.setText(activity.getString(R.string.login_first));
                        dialog.show();
                        No_Btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                Intent intent = new Intent(activity, LoginApproved.class);
                                activity.startActivity(intent);
                            }
                        });
                    }else
                    {


                        String ID = String.valueOf(item.getAccountID());
                        final Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.review_dialog);
                        Button btn = (Button) dialog.findViewById(R.id.Review_Btn);
                        TextView Lang_Dialog_txt_id = (TextView) dialog.findViewById(R.id.Lang_Dialog_txt_id);
                        TextView Review_text_address = (TextView) dialog.findViewById(R.id.Review_text_address);
                        Edit_Review_txt = (EditText) dialog.findViewById(R.id.Edit_Review_txt);
                        Lang_Dialog_txt_id.setText(R.string.write_remark);
                        Review_text_address.setText(R.string.your_remarks);
                        Edit_Review_txt.setText(R.string.InvitePassenger);
                        dialog.show();
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                str_Remarks = Edit_Review_txt.getText().toString();
                                String response = null;
//                                try {
//                                    response = j.Passenger_SendAlert(Driver_ID, Passenger_ID, Route_ID, URLEncoder.encode(str_Remarks));
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                                assert response != null;
//                                switch (response) {
//                                    case "\"-1\"":
//                                        Toast.makeText(activity, activity.getString(R.string.already_sent_request), Toast.LENGTH_SHORT).show();
//                                        dialog.dismiss();
//                                        break;
//                                    case "\"0\"":
//                                        Toast.makeText(activity, activity.getString(R.string.login_network_error), Toast.LENGTH_SHORT).show();
//                                        dialog.dismiss();
//                                        break;
//                                    default:
//                                        Toast.makeText(activity, R.string.req_sent_succ, Toast.LENGTH_LONG).show();
//                                        dialog.dismiss();
//                                        PassengerSendInvite.setVisibility(View.INVISIBLE);
//                                        activity.finish();
//                                        break;
//                                }
                            }
                        });



                    }
                }
            });





        }else if (item.getMapKey().equals("Driver")){

            if (inflater == null)
                inflater = (LayoutInflater) activity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null)
                convertView = inflater.inflate(R.layout.quick_search_list_item_2, null);


            if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();

            CircularImageView Photo = (CircularImageView) convertView.findViewById(R.id.search_list_photo);
            TextView SDG_Route_Start_FromTime = (TextView) convertView.findViewById(R.id.SDG_Route_Start_FromTime);
            TextView DriverEnName = (TextView) convertView.findViewById(R.id.DriverEnName);
            TextView Nationality_en = (TextView) convertView.findViewById(R.id.Nationality_en);
            TextView SDG_RouteDays = (TextView) convertView.findViewById(R.id.search_results_days);
            TextView Best_Drivers_Item_rate = (TextView) convertView.findViewById(R.id.Best_Drivers_Item_rate);
            TextView LastSeenTvValue = (TextView) convertView.findViewById(R.id.LastSeenTvValue);
            Button PassengerSendInvite = (Button) convertView.findViewById(R.id.PassengerSendInvite);


//        Photo.sectImageUrl(URL + item.getAccountPhoto(), imageLoader);



            if (item.getDriverPhoto() != null){
                Photo.setImageBitmap(item.getDriverPhoto());
            }else {
                Photo.setImageResource(R.drawable.defaultdriver);
            }
            SDG_Route_Start_FromTime.setText(item.getSDG_Route_Start_FromTime());


            StringBuffer res = new StringBuffer();

            String[] strArr = item.getAccountName().split(" ");

            for (String str : strArr) {
                char[] stringArray = str.trim().toCharArray();
                if (stringArray.length != 0) {


                    stringArray[0] = Character.toUpperCase(stringArray[0]);
                    str = new String(stringArray);

                    res.append(str).append(" ");

                }

            }

            DriverEnName.setText(res);


//        DriverEnName.setText(item.getAccountName());

            Nationality_en.setText(item.getNationality_en());
            SDG_RouteDays.setText(item.getSDG_RouteDays());
            Best_Drivers_Item_rate.setText(item.getRating());
            LastSeenTvValue.setText(item.getLastSeen());
            ImageView Phone_Message = (ImageView) convertView.findViewById(R.id.im1);
            ImageView Phone_Call = (ImageView) convertView.findViewById(R.id.im5);



            Phone_Call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + item.getAccountMobile()));
                    activity.startActivity(intent);
                }
            });

            Phone_Message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + item.getAccountMobile()));
                    intent.putExtra("sms_body", "Hello " + item.getAccountName());
                    activity.startActivity(intent);
                }
            });






        }


        return convertView;


    }
}
