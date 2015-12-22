package rta.ae.sharekni;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
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

import org.json.JSONException;

import java.net.URLEncoder;
import java.util.List;

import rta.ae.sharekni.Arafa.Classes.GetData;

/**
 * Created by Nezar Saleh on 10/8/2015.
 */
public class DriverGetReviewAdapter extends BaseAdapter {


    private Activity activity;
    List<DriverGetReviewDataModel> driverGetReviewDataModels_items;
    LayoutInflater layoutInflater;
    SharedPreferences myPrefs;

    public DriverGetReviewAdapter(Activity activity, List<DriverGetReviewDataModel> items) {

        this.driverGetReviewDataModels_items = items;
        this.activity = activity;

        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }//constructor


    @Override
    public int getCount() {
        return driverGetReviewDataModels_items.size();
    }

    @Override
    public Object getItem(int position) {
        return driverGetReviewDataModels_items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (layoutInflater == null)
            layoutInflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.driver_get_review_listitem, null);


        ImageView DriverPhoto = (ImageView) convertView.findViewById(R.id.Driver_Get_Review_im);
        TextView Review = (TextView) convertView.findViewById(R.id.Driver_Get_Review_Review);
        TextView AccountName = (TextView) convertView.findViewById(R.id.Driver_Get_Review_AccountName);
        TextView AccountNationalityEn = (TextView) convertView.findViewById(R.id.Driver_Get_Review_Nat);
        ImageView Driver_Delete = (ImageView) convertView.findViewById(R.id.Driver_Delete_Review);
        ImageView Edit_Review_Im = (ImageView) convertView.findViewById(R.id.Edit_Review_Im);

        Driver_Delete.setVisibility(View.INVISIBLE);
        Edit_Review_Im.setVisibility(View.INVISIBLE);
        final DriverGetReviewDataModel driverGetReviewDataModel = driverGetReviewDataModels_items.get(position);
        int Passenger = driverGetReviewDataModel.getDriverID();
        myPrefs = activity.getSharedPreferences("myPrefs", 0);
        int AccountType = Integer.parseInt(myPrefs.getString("account_id", "0"));

        Log.d("Review id", String.valueOf(driverGetReviewDataModel.getAccountID()));
        Log.d("Account id", String.valueOf(AccountType));


        if (driverGetReviewDataModel.getAccountID() == AccountType) {
            Edit_Review_Im.setVisibility(View.VISIBLE);
            Edit_Review_Im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final GetData j = new GetData();
                    final String[] Review_str = {driverGetReviewDataModel.getReview()};
                    final Dialog dialog = new Dialog(activity);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.review_dialog);
                    Button btn = (Button) dialog.findViewById(R.id.Review_Btn);
                    final TextView Review_Empty_Error = (TextView) dialog.findViewById(R.id.Review_Empty_Error);
                    final EditText Edit_Review_txt = (EditText) dialog.findViewById(R.id.Edit_Review_txt);
                    Edit_Review_txt.setText(Review_str[0]);
                    Log.d("Review_ID", String.valueOf(driverGetReviewDataModel.getReviewID()));
                    dialog.show();
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Review_str[0] = Edit_Review_txt.getText().toString();

                            if (!Review_str[0].equals("")) {
                                Review_Empty_Error.setVisibility(View.INVISIBLE);
                                try {
                                    String response = j.Passenger_Edit_Review(driverGetReviewDataModel.getReviewID(), URLEncoder.encode(Review_str[0]));
                                    if (response.equals("\"-1\"") || response.equals("\"-2\'")) {
                                        Toast.makeText(activity, (R.string.cannot_review), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(activity, (R.string.Review_updated), Toast.LENGTH_SHORT).show();
                                        activity.recreate();
                                    }
                                    dialog.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }//  if review is null
                            else {
                                Review_Empty_Error.setVisibility(View.VISIBLE);

                            }


                        }
                    });

                }
            });
        }

        if (driverGetReviewDataModel.getAccountID() == AccountType) {

            Driver_Delete.setVisibility(View.VISIBLE);
            Driver_Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AlertDialog.Builder(activity)
                            .setTitle(R.string.Delete_msg)
                            .setMessage(R.string.please_confirm_to_delete)
                            .setPositiveButton(R.string.Confirm_str, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                    GetData gd = new GetData();
                                    try {
                                        String response = gd.Driver_Delete_Review(driverGetReviewDataModel.getReviewID());
                                        if (response.equals("\"1\"")) {
                                            Toast.makeText(activity, activity.getString(R.string.review_deleted), Toast.LENGTH_SHORT).show();
                                            activity.recreate();
                                        } else {
                                            Toast.makeText(activity, activity.getString(R.string.error), Toast.LENGTH_SHORT).show();
                                        }
                                        Log.d("res = ", response);
                                    } catch (JSONException e) {
                                        Toast.makeText(activity, activity.getString(R.string.request_failed), Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }


                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();

                                }
                            }).setIcon(android.R.drawable.ic_dialog_alert).show();


                }
            });

        }


        if (Passenger == AccountType) {
            Driver_Delete.setVisibility(View.VISIBLE);

            Driver_Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AlertDialog.Builder(activity)
                            .setTitle(R.string.Delete_msg)
                            .setMessage(R.string.please_confirm_to_delete)
                            .setPositiveButton(R.string.Confirm_str, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                    GetData gd = new GetData();
                                    try {
                                        String response = gd.Driver_Delete_Review(driverGetReviewDataModel.getReviewID());
                                        if (response.equals("\"1\"")) {
                                            Toast.makeText(activity, activity.getString(R.string.review_deleted), Toast.LENGTH_SHORT).show();
                                            activity.recreate();
                                        } else {
                                            Toast.makeText(activity, activity.getString(R.string.error), Toast.LENGTH_SHORT).show();
                                        }
                                        Log.d("res = ", response);
                                    } catch (JSONException e) {
                                        Toast.makeText(activity, activity.getString(R.string.request_failed), Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }


                                }
                            })
                            .setNegativeButton(R.string.Cancel_msg, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();

                                }
                            }).setIcon(android.R.drawable.ic_dialog_alert).show();


                }
            });
        }

        if (driverGetReviewDataModel.getPhoto() != null) {
            DriverPhoto.setImageBitmap(driverGetReviewDataModel.getPhoto());
        } else {
            DriverPhoto.setImageResource(R.drawable.defaultdriver);
        }

        AccountName.setText(driverGetReviewDataModel.getAccountName());
        AccountNationalityEn.setText(driverGetReviewDataModel.getAccountNationalityEn());
        Review.setText(driverGetReviewDataModel.getReview());


        return convertView;


    }


}
