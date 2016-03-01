package rta.ae.sharekni;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.pkmmte.view.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import rta.ae.sharekni.Arafa.Activities.Route;
import rta.ae.sharekni.Arafa.Classes.AppController;
import rta.ae.sharekni.Arafa.Classes.GetData;
import rta.ae.sharekni.Arafa.DataModel.BestDriverDataModel;
import rta.ae.sharekni.Arafa.DataModel.BestRouteDataModel;
import rta.ae.sharekni.Arafa.DataModelAdapter.ProfileRideAdapter;

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
    int Driver_ID, Route_ID, Passenger_ID;
    ListView Routes_Lv;
    SimpleAdapter DriverRidesAdapter;

    int Route_ID_2;
    String Route_Name_2;


    List<TreeMap<String, String>> arr_2 = new ArrayList<>();

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
        TextView LastSeenText = (TextView) convertView.findViewById(R.id.LastSeenText);


//        Photo.sectImageUrl(URL + item.getAccountPhoto(), imageLoader);


        if (item.getDriverPhoto() != null) {
            Photo.setImageBitmap(item.getDriverPhoto());
        } else {
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

        if (item.getLastSeen().equals("hide")) {
            LastSeenTvValue.setVisibility(View.INVISIBLE);
            LastSeenText.setVisibility(View.INVISIBLE);
        } else {
            LastSeenText.setVisibility(View.VISIBLE);
            LastSeenTvValue.setVisibility(View.VISIBLE);
            LastSeenTvValue.setText(item.getLastSeen());
        }
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


        return convertView;


    }


}
