package rta.ae.sharekni;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.pkmmte.view.CircularImageView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import rta.ae.sharekni.Arafa.Classes.AppController;
import rta.ae.sharekni.Arafa.Classes.GetData;

public class PendingRequestDetails extends AppCompatActivity {

    List<DriverAlertsForRequestDataModel> arr = new ArrayList<>();
    private Toolbar toolbar;
    String PassengerName, Driver_Pending;
    String RouteName, NationalityEnName, PassengerMobile, Remarks, RequestDate;
    Bitmap AccountPhoto;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    int RequestId;
    String URL = GetData.PhotoURL;
    TextView RouteName_txt, NationalityEnName_txt, AccountPhoto_txt, PassengerMobile_txt, Remarks_txt, RequestDate_txt, PassengerName_txt;

    Button Alert_Decline, Alert_Accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_request_details);
        initToolbar();
        Intent intent = getIntent();
        PassengerName = intent.getStringExtra("PassengerName");
        RouteName = intent.getStringExtra("RouteName");
        NationalityEnName = intent.getStringExtra("NationalityEnName");
        //  AccountPhoto = intent.getParcelableExtra("AccountPhoto");
        PassengerMobile = intent.getStringExtra("PassengerMobile");
        Remarks = intent.getStringExtra("Remarks");
        RequestDate = intent.getStringExtra("RequestDate");
        RequestId = intent.getIntExtra("RequestId", 0);
        Driver_Pending = intent.getStringExtra("Driver_Pending_Request");

        Log.d("RouteName", RouteName);
        Log.d("PassengerName", PassengerName);
        Log.d("Driver Pending Request", Driver_Pending);


        RouteName_txt = (TextView) findViewById(R.id.RouteName);
        PassengerName_txt = (TextView) findViewById(R.id.PassengerName);
        NationalityEnName_txt = (TextView) findViewById(R.id.NationalityEnName);
        PassengerMobile_txt = (TextView) findViewById(R.id.PassengerMobile);
        Remarks_txt = (TextView) findViewById(R.id.Remarks);
        RequestDate_txt = (TextView) findViewById(R.id.RequestDate);
        // Alert_Decline= (Button) findViewById(R.id.Alert_Decline);
        Alert_Accept = (Button) findViewById(R.id.Alert_Accept);


        RouteName_txt.setText(RouteName);
        PassengerName_txt.setText(PassengerName);
        NationalityEnName_txt.setText(NationalityEnName);
        PassengerMobile_txt.setText(PassengerMobile);
        Remarks_txt.setText(Remarks);
        RequestDate_txt.setText(RequestDate);


        if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();
        CircularImageView Photo = (CircularImageView) findViewById(R.id.AccountPhoto);

        if (AccountPhoto != null) {
            Photo.setImageBitmap(AccountPhoto);
        } else {
            Photo.setImageResource(R.drawable.defaultdriver);
        }

        Alert_Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Driver_Pending.equals("Driver_Pending_Request")) {


                    final String[] res = new String[1];
                    new AlertDialog.Builder(PendingRequestDetails.this)
                            .setTitle(R.string.Delete_Request)
                            .setMessage(R.string.please_confirm_to_cancel)
                            .setPositiveButton(R.string.invite_yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        res[0] = new GetData().Driver_RemoveInvitation(RequestId);
                                        if (res[0].equals("\"1\"")) {
                                            Toast.makeText(getBaseContext(), getString(R.string.request_removed), Toast.LENGTH_SHORT).show();
//                                                finish();
                                            Intent in = new Intent(PendingRequestDetails.this, DriverAlertsForRequest.class);
                                            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(in);
                                            finish();
                                        } else {
                                            Toast.makeText(getBaseContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
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


                } else {
                    final String[] res = new String[1];
                    new AlertDialog.Builder(PendingRequestDetails.this)
                            .setTitle(R.string.Delete_Request)
                            .setMessage(R.string.please_confirm_to_cancel)
                            .setPositiveButton(R.string.invite_yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        res[0] = new GetData().Passenger_RemoveRequest(RequestId);
                                        if (res[0].equals("\"1\"")) {
                                            Toast.makeText(getBaseContext(), getString(R.string.request_removed), Toast.LENGTH_SHORT).show();
//                                                finish();
                                            Intent in = new Intent(PendingRequestDetails.this, DriverAlertsForRequest.class);
                                            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(in);
                                            finish();
                                        } else {
                                            Toast.makeText(getBaseContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
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


            }
        });


    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText(R.string.Driver_request_details_address);
        //   toolbar.setElevation(10);


        setSupportActionBar(toolbar);
//        TextView mytext = (TextView) toolbar.findViewById(R.id.mytext_appbar);
//        mytext.setText("Most Rides");


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            Locale locale = Locale.getDefault();
            String Locale_Str2 = locale.toString();
            if (Locale_Str2.contains("en")) {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_arrow_back);
            } else {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_arrow_forward);
            }
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}
