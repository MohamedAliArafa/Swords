package rta.ae.sharekni;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.pkmmte.view.CircularImageView;

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
                    break;
                case "true":
                    txt_Accepted_or_Rejected.setText(R.string.accept_request);
                    break;
                case "null":
                    if (model.getDriverPending().equals("Driver_Pending_Request")){
                        txt_Accepted_or_Rejected.setText(R.string.Driver_pending_request);
                    }else {
                        txt_Accepted_or_Rejected.setText(R.string.pending_request);
                    }

                    break;
                case "DriverToPassenger":
                    txt_Accepted_or_Rejected.setText(R.string.Sent_Invite);
                    break;
                case "PassengerToDriver":
                    txt_Accepted_or_Rejected.setText(R.string.join_request);
                    break;

            }

        } //  IF  * 1
        else {
            txt_Accepted_or_Rejected.setText(R.string.join_request);
        }
        return convertView;
    }
}
