package rta.ae.sharekni;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.pkmmte.view.CircularImageView;

import org.json.JSONException;

import java.util.List;

import rta.ae.sharekni.Arafa.Classes.AppController;
import rta.ae.sharekni.Arafa.Classes.GetData;

/**
 * Created by Nezar Saleh on 10/10/2015.
 */
public class DriverAlertsForRequestAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<DriverAlertsForRequestDataModel> AlertsItem;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    String URL = GetData.PhotoURL;


    public DriverAlertsForRequestAdapter(Activity activity, List<DriverAlertsForRequestDataModel> driverItems) {
        this.activity = activity;
        this.AlertsItem = driverItems;
    }




    @Override
    public int getCount() {
        return AlertsItem.size();
    }

    @Override
    public Object getItem(int position) {
        return AlertsItem.get(position);
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
            convertView = inflater.inflate(R.layout.driver_alerts_request_list_items, null);


        if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();


        CircularImageView Photo = (CircularImageView) convertView.findViewById(R.id.AccountPhoto);
        TextView PassengerName= (TextView) convertView.findViewById(R.id.PassengerName);
        TextView NationalityEnName= (TextView) convertView.findViewById(R.id.NationalityEnName);
        TextView txt_Accepted_or_Rejected = (TextView) convertView.findViewById(R.id.txt_Accepted_or_Rejected);
        ImageView Delete_Notification_im = (ImageView) convertView.findViewById(R.id.Delete_Notification_im);

        final DriverAlertsForRequestDataModel model = AlertsItem.get(position);
//        Photo.setImageUrl(URL + model.getAccountPhoto(), imageLoader);
        PassengerName.setText(model.getPassengerName());
        NationalityEnName.setText(model.getNationalityEnName());

        if (model.getAccountPhoto() != null){
            if (!model.getAccountPhoto().equals("NoImage.png")){
                Photo.setImageBitmap(model.getPhoto());
            }else {
                Photo.setImageResource(R.drawable.defaultdriver);
            }
        }else {
            Photo.setImageResource(R.drawable.defaultdriver);
        }

        if (model.getDriverAccept()!=null) {
            switch (model.getDriverAccept()) {
                case "false":
                    txt_Accepted_or_Rejected.setText(R.string.reject_request);
                    Delete_Notification_im.setVisibility(View.VISIBLE);
                    break;
                case "true":
                    txt_Accepted_or_Rejected.setText(R.string.accept_request);
                    Delete_Notification_im.setVisibility(View.VISIBLE);
                    break;
                case "null":
                    if (model.getDriverPending().equals("Driver_Pending_Request")){
                        txt_Accepted_or_Rejected.setText(R.string.Driver_pending_request);
                        Delete_Notification_im.setVisibility(View.INVISIBLE);
                    }else {
                        txt_Accepted_or_Rejected.setText(R.string.pending_request);
                        Delete_Notification_im.setVisibility(View.INVISIBLE);
                    }

                    break;
                case "DriverToPassenger":
                    txt_Accepted_or_Rejected.setText(R.string.Sent_Invite);
                    Delete_Notification_im.setVisibility(View.INVISIBLE);
                    break;
                case "PassengerToDriver":
                    txt_Accepted_or_Rejected.setText(R.string.join_request);
                    Delete_Notification_im.setVisibility(View.INVISIBLE);
                    break;

            }

        } //  IF  * 1
        else {
            txt_Accepted_or_Rejected.setText(R.string.join_request);
            Delete_Notification_im.setVisibility(View.INVISIBLE);
        }




        Delete_Notification_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] res = new String[1];
                new AlertDialog.Builder(activity)
                        .setTitle(R.string.Delete_Request)
                        .setMessage(R.string.please_confirm_to_cancel)
                        .setPositiveButton(R.string.invite_yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    res[0] = new GetData().Passenger_RemoveRequest(model.RequestId);
                                    if (res[0].equals("\"1\"")) {
                                        Toast.makeText(activity, activity.getString(R.string.request_removed), Toast.LENGTH_SHORT).show();
//                                                finish();
                                        Intent in = new Intent(activity, DriverAlertsForRequest.class);
                                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        activity.startActivity(in);
                                        activity.finish();
                                    } else {
                                        Toast.makeText(activity, activity.getString(R.string.error), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();


            }
        });





        return convertView;
    }
}
