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

    jsoning jsoning;

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


        if (item.getMapKey().equals("Passenger")) {

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
            TextView LastSeenText = (TextView) convertView.findViewById(R.id.LastSeenText);
            final Button PassengerSendInvite = (Button) convertView.findViewById(R.id.PassengerSendInvite);


//        Photo.sectImageUrl(URL + item.getAccountPhoto(), imageLoader);


            if (item.getInviteType().equals("MapLookUp")) {
                PassengerSendInvite.setVisibility(View.INVISIBLE);
            } else if (item.getInviteType().equals("DriverRide")) {
                PassengerSendInvite.setVisibility(View.VISIBLE);
            }


            if (item.getInviteStatus() == 1) {
                PassengerSendInvite.setVisibility(View.INVISIBLE);
            }

            if (item.getDriverPhoto() != null) {
                Photo.setImageBitmap(item.getDriverPhoto());
            } else {
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

            // we collct data to be able to send invite to passnger so don't get cnfused
            Driver_ID = item.getAccountID();
            Passenger_ID = item.getDriverId();
            Route_ID=item.getSDG_Route_ID();


            Nationality_en.setText(item.getNationality_en());

            Best_Drivers_Item_rate.setText(item.getRating());

            if (item.getLastSeen().equals("hide") || item.getLastSeen().equals("0")) {
                LastSeenTvValue.setVisibility(View.INVISIBLE);
                LastSeenText.setVisibility(View.INVISIBLE);
            } else {
                LastSeenText.setVisibility(View.VISIBLE);
                LastSeenTvValue.setVisibility(View.VISIBLE);
                LastSeenTvValue.setText(item.getLastSeen());
            }

            PassengerSendInvite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    ProgressDialog pDialog = new ProgressDialog(activity);
                    pDialog.setMessage(activity.getString(R.string.loading) + "...");
                    pDialog.show();
                    jsoning = new jsoning(pDialog, activity);
                    jsoning.execute();


                }
            });


        } else if (item.getMapKey().equals("Driver")) {

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


        }


        return convertView;


    }


    public class jsoning extends AsyncTask {

        ListView lv;
        Activity con;
        BestRouteDataModel[] driver;
        private ProgressDialog pDialog;
        private List<BestDriverDataModel> arr = new ArrayList<>();
        boolean exists = false;

        public jsoning(ProgressDialog pDialog, final Activity con) {

            this.lv = lv;
            this.con = con;
            this.pDialog = pDialog;

        }

        @Override
        protected void onPostExecute(Object o) {


            if (exists) {
                String response = null;

                try {
                    response = j.Driver_SendInvite(Driver_ID, Passenger_ID, Route_ID, URLEncoder.encode(activity.getString(R.string.InvitePassenger)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                assert response != null;
                switch (response) {
                    case "\"-1\"":
                        Toast.makeText(activity, activity.getString(R.string.already_sent_request), Toast.LENGTH_SHORT).show();

                        break;
                    case "\"0\"":
                        Toast.makeText(activity, activity.getString(R.string.login_network_error), Toast.LENGTH_SHORT).show();

                        break;
                    case "\"1\"":
                        Toast.makeText(activity, R.string.req_sent_succ, Toast.LENGTH_LONG).show();

                        activity.finish();
                        break;
                    default:

                        activity.finish();
                        break;
                }

                Log.d("Send Invite" + "Driver id", String.valueOf(Driver_ID));
                Log.d("Send Invite" + "Route id", String.valueOf(Route_ID));
                Log.d("Send Invite" + "Passenger ID", String.valueOf(Passenger_ID));


            }
            hidePDialog();
        }

        private void hidePDialog() {
            if (pDialog != null) {
                pDialog.dismiss();
                pDialog = null;
            }
        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                SocketAddress sockaddr = new InetSocketAddress("www.google.com", 80);
                Socket sock = new Socket();
                int timeoutMs = 2000;   // 2 seconds
                sock.connect(sockaddr, timeoutMs);
                exists = true;
            } catch (final Exception e) {
                e.printStackTrace();
                con.runOnUiThread(new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(con)
                                .setTitle(con.getString(R.string.connection_problem))
                                .setMessage(con.getString(R.string.con_problem_message))
                                .setPositiveButton(con.getString(R.string.retry), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        con.finish();
                                        Intent in = con.getIntent();
                                        in.putExtra("DriverID", Driver_ID);
                                        con.startActivity(con.getIntent());
                                    }
                                })
                                .setNegativeButton(con.getString(R.string.goBack), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        con.finish();
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();
                        Toast.makeText(con, con.getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }
    }

}
