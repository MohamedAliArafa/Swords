package rta.ae.sharekni;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import rta.ae.sharekni.Arafa.Classes.AppController;
import rta.ae.sharekni.Arafa.Classes.GetData;

public class DriverRequestDetails extends AppCompatActivity {

    List<DriverAlertsForRequestDataModel> arr = new ArrayList<>();
    private Toolbar toolbar;
    String PassengerName, InviteState;
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
        setContentView(R.layout.activity_driver_request_details);
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
        InviteState = intent.getStringExtra("InviteState");
        Log.d("RouteName", RouteName);
        Log.d("PassengerName", PassengerName);
        Log.d("Invite Status " , InviteState);


        RouteName_txt = (TextView) findViewById(R.id.RouteName);
        PassengerName_txt = (TextView) findViewById(R.id.PassengerName);
        NationalityEnName_txt = (TextView) findViewById(R.id.NationalityEnName);
        PassengerMobile_txt = (TextView) findViewById(R.id.PassengerMobile);
        Remarks_txt = (TextView) findViewById(R.id.Remarks);
        RequestDate_txt = (TextView) findViewById(R.id.RequestDate);
        Alert_Decline = (Button) findViewById(R.id.Alert_Decline);
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
                if (InviteState.equals("DriverToPassenger")) {

                    new accept2().execute();
                } else {
                    new accept().execute();
                }
            }
        });


        Alert_Decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InviteState.equals("DriverToPassenger")) {

                    new decline2().execute();
                } else {
                    new decline().execute();
                }


            }
        });

    }


    private class decline extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            boolean exists = false;
            try {
                SocketAddress sockaddr = new InetSocketAddress("www.google.com", 80);
                Socket sock = new Socket();
                int timeoutMs = 20000;   // 2 seconds
                sock.connect(sockaddr, timeoutMs);
                exists = true;
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(DriverRequestDetails.this)
                                .setTitle(getString(R.string.connection_problem))
                                .setMessage(getString(R.string.con_problem_message))
                                .setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        Intent mainIntent = getIntent();
                                        mainIntent.putExtra("PassengerName", PassengerName);
                                        mainIntent.putExtra("RouteName", RouteName);
                                        mainIntent.putExtra("NationalityEnName", NationalityEnName);
                                        //  mainIntent.putExtra("AccountPhoto",AccountPhoto);
                                        mainIntent.putExtra("PassengerMobile", PassengerMobile);
                                        mainIntent.putExtra("Remarks", Remarks);
                                        mainIntent.putExtra("RequestDate", RequestDate);
                                        mainIntent.putExtra("RequestId", RequestId);
                                        startActivity(mainIntent);
                                    }
                                })
                                .setNegativeButton(getString(R.string.goBack), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();
                        Toast.makeText(DriverRequestDetails.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                GetData j = new GetData();
                try {
                    String n = j.DriverAcceptPassenger(RequestId, 0);
                    if (n.equals("1")) {
                        Intent in = new Intent(DriverRequestDetails.this, DriverAlertsForRequest.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(in);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            finish();
            return null;
        }
    }

    private class accept extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            boolean exists = false;
            try {
                SocketAddress sockaddr = new InetSocketAddress("www.google.com", 80);
                Socket sock = new Socket();
                int timeoutMs = 20000;   // 2 seconds
                sock.connect(sockaddr, timeoutMs);
                exists = true;
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(DriverRequestDetails.this)
                                .setTitle(getString(R.string.connection_problem))
                                .setMessage(getString(R.string.con_problem_message))
                                .setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        Intent mainIntent = getIntent();
                                        mainIntent.putExtra("PassengerName", PassengerName);
                                        mainIntent.putExtra("RouteName", RouteName);
                                        mainIntent.putExtra("NationalityEnName", NationalityEnName);
                                        //  mainIntent.putExtra("AccountPhoto",AccountPhoto);
                                        mainIntent.putExtra("PassengerMobile", PassengerMobile);
                                        mainIntent.putExtra("Remarks", Remarks);
                                        mainIntent.putExtra("RequestDate", RequestDate);
                                        mainIntent.putExtra("RequestId", RequestId);
                                        startActivity(mainIntent);
                                    }
                                })
                                .setNegativeButton(getString(R.string.goBack), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();
                        Toast.makeText(DriverRequestDetails.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                GetData j = new GetData();
                try {
                    String n = j.DriverAcceptPassenger(RequestId, 1);
                    if (n.equals("1")) {
                        Intent in = new Intent(DriverRequestDetails.this, DriverAlertsForRequest.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(in);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            finish();
            return null;
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////



    private class decline2 extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            boolean exists = false;
            try {
                SocketAddress sockaddr = new InetSocketAddress("www.google.com", 80);
                Socket sock = new Socket();
                int timeoutMs = 20000;   // 2 seconds
                sock.connect(sockaddr, timeoutMs);
                exists = true;
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(DriverRequestDetails.this)
                                .setTitle(getString(R.string.connection_problem))
                                .setMessage(getString(R.string.con_problem_message))
                                .setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        Intent mainIntent = getIntent();
                                        mainIntent.putExtra("PassengerName", PassengerName);
                                        mainIntent.putExtra("RouteName", RouteName);
                                        mainIntent.putExtra("NationalityEnName", NationalityEnName);
                                        //  mainIntent.putExtra("AccountPhoto",AccountPhoto);
                                        mainIntent.putExtra("PassengerMobile", PassengerMobile);
                                        mainIntent.putExtra("Remarks", Remarks);
                                        mainIntent.putExtra("RequestDate", RequestDate);
                                        mainIntent.putExtra("RequestId", RequestId);
                                        startActivity(mainIntent);
                                    }
                                })
                                .setNegativeButton(getString(R.string.goBack), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();
                        Toast.makeText(DriverRequestDetails.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                GetData j = new GetData();
                try {
                    String n = j.Passenger_AcceptInvitation(RequestId, 0);
                    if (n.equals("1")) {
                        Intent in = new Intent(DriverRequestDetails.this, DriverAlertsForRequest.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(in);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            finish();
            return null;
        }
    }

    private class accept2 extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            boolean exists = false;
            try {
                SocketAddress sockaddr = new InetSocketAddress("www.google.com", 80);
                Socket sock = new Socket();
                int timeoutMs = 20000;   // 2 seconds
                sock.connect(sockaddr, timeoutMs);
                exists = true;
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(DriverRequestDetails.this)
                                .setTitle(getString(R.string.connection_problem))
                                .setMessage(getString(R.string.con_problem_message))
                                .setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        Intent mainIntent = getIntent();
                                        mainIntent.putExtra("PassengerName", PassengerName);
                                        mainIntent.putExtra("RouteName", RouteName);
                                        mainIntent.putExtra("NationalityEnName", NationalityEnName);
                                        //  mainIntent.putExtra("AccountPhoto",AccountPhoto);
                                        mainIntent.putExtra("PassengerMobile", PassengerMobile);
                                        mainIntent.putExtra("Remarks", Remarks);
                                        mainIntent.putExtra("RequestDate", RequestDate);
                                        mainIntent.putExtra("RequestId", RequestId);
                                        startActivity(mainIntent);
                                    }
                                })
                                .setNegativeButton(getString(R.string.goBack), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();
                        Toast.makeText(DriverRequestDetails.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                GetData j = new GetData();
                try {
                    String n = j.Passenger_AcceptInvitation(RequestId, 1);
                    if (n.equals("1")) {
                        Intent in = new Intent(DriverRequestDetails.this, DriverAlertsForRequest.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(in);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            finish();
            return null;
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////



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


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}

