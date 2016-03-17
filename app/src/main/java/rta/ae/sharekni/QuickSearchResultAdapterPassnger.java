package rta.ae.sharekni;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

import org.json.JSONException;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import rta.ae.sharekni.Arafa.Classes.AppController;
import rta.ae.sharekni.Arafa.Classes.GetData;
import rta.ae.sharekni.Arafa.DataModel.BestDriverDataModel;
import rta.ae.sharekni.Arafa.DataModel.BestRouteDataModel;

/**
 * Created by nezar on 9/20/2015.
 */
public class QuickSearchResultAdapterPassnger extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<QuickSearchDataModel> searchItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    String URL = GetData.PhotoURL;
    String str_Remarks = "";
    EditText Edit_Review_txt;
    GetData j = new GetData();
    ListView Routes_Lv;
    SimpleAdapter DriverRidesAdapter;
    int Route_ID_2;
    String Route_Name_2;
    List<TreeMap<String, String>> arr_2 = new ArrayList<>();

    jsoning jsoning;

    public QuickSearchResultAdapterPassnger(Activity activity, List<QuickSearchDataModel> searchItems) {
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
        ImageView rate_pic = (ImageView) convertView.findViewById(R.id.rate_pic);
        final Button PassengerSendInvite = (Button) convertView.findViewById(R.id.PassengerSendInvite);


//        Photo.sectImageUrl(URL + item.getAccountPhoto(), imageLoader

        PassengerSendInvite.setVisibility(View.VISIBLE);

//        if (item.getInviteType().equals("MapLookUp")) {
//            PassengerSendInvite.setVisibility(View.INVISIBLE);
//        } else if (item.getInviteType().equals("DriverRide")) {
//            PassengerSendInvite.setVisibility(View.VISIBLE);
//        }

        Log.d("nat Search", item.getNationality_en());
        if (item.getNationality_en().equals("Not Specified")) {
            Nationality_en.setVisibility(View.GONE);
        } else {
            Nationality_en.setText(item.getNationality_en());
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




        //   Best_Drivers_Item_rate.setText(item.getRating());
        Best_Drivers_Item_rate.setVisibility(View.INVISIBLE);
        rate_pic.setVisibility(View.INVISIBLE);

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


                Log.d("Driver_ID", String.valueOf(item.getAccountID()));
                Log.d("Passenger_ID", String.valueOf(item.getPassenger_ID()));
                Log.d("Route_ID", String.valueOf(item.getSDG_Route_ID()));

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
                btn.setText(R.string.Send_Invite_Str);
                dialog.show();
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        str_Remarks = Edit_Review_txt.getText().toString();
                        dialog.dismiss();
                        ProgressDialog pDialog = new ProgressDialog(activity);
                        pDialog.setMessage(activity.getString(R.string.loading) + "...");
                        pDialog.show();
                        jsoning = new jsoning(pDialog, activity, item.getAccountID(), item.getPassenger_ID(), item.getSDG_Route_ID(), str_Remarks);
                        jsoning.execute();


                    }
                });

            }
        });


        return convertView;


    }


    public class jsoning extends AsyncTask {

        ListView lv;
        Activity con;
        BestRouteDataModel[] driver;
        private ProgressDialog pDialog;
        private List<BestDriverDataModel> arr = new ArrayList<>();
        boolean exists = false;
        int Driver_ID, Route_ID, Passenger_ID;
        String Remarks;


        public jsoning(ProgressDialog pDialog, final Activity con, int Driver_ID, int Passenger_ID, int Route_ID, String remarks) {

            this.lv = lv;
            this.con = con;
            this.pDialog = pDialog;
            this.Route_ID = Route_ID;
            this.Passenger_ID = Passenger_ID;
            this.Driver_ID = Driver_ID;
            this.Remarks = remarks;


        }

        @Override
        protected void onPostExecute(Object o) {


            if (exists) {
                String response = null;

                try {
                    response = j.Driver_SendInvite(Driver_ID, Passenger_ID, Route_ID, URLEncoder.encode(Remarks));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                assert response != null;
                switch (response) {
                    case "\"-1\"":
                        Toast.makeText(activity, activity.getString(R.string.already_sent_request), Toast.LENGTH_SHORT).show();
                        activity.finish();
                        break;
                    case "\"0\"":
                        Toast.makeText(activity, activity.getString(R.string.login_network_error), Toast.LENGTH_SHORT).show();

                        break;
                    case "\"1\"":
                        Toast.makeText(activity, R.string.req_sent_succ2, Toast.LENGTH_LONG).show();
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
