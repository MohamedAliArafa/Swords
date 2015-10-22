package com.example.nezarsaleh.shareknitest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.nezarsaleh.shareknitest.Arafa.Classes.GetData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nezar Saleh on 10/19/2015.
 */

public class MyNotifications extends Service {

    int i = 0;
    int y = 0;
    boolean ISRunning = true;
    SharedPreferences myPrefs;
    String ID;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        i = intent.getIntExtra("Flag", 0);
        Log.d("alert numbers", String.valueOf(i));
        myPrefs = this.getSharedPreferences("myPrefs", 0);
        ID = myPrefs.getString("account_id", null);


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (ISRunning) {
                    try {
                        if (i > 0) {

                            Thread.sleep(2000);
                            CreateNotification(y++);
                            i--;
                        }
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }


                }


            }
        });

        t.start();


        return super.onStartCommand(intent, flags, startId);

    }


    private void CreateNotification(int y) {
        GetData gd = new GetData();
        try {
            JSONArray jsonArray = gd.GetDriverAlertsForRequest(Integer.parseInt(ID));
            for (int i = 0;i< jsonArray.length();i++){
                JSONObject j = jsonArray.getJSONObject(i);
                Intent intent = new Intent(this, DriverAlertsForRequest.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                Notification.Builder builder = new Notification.Builder(this);
                builder.setContentTitle("Route :" + j.getString("RouteName"));
                builder.setContentText(j.getString("PassengerName") + " Send You A Join Request ");
                builder.setSmallIcon(R.drawable.sharekni_logo);
                builder.setContentIntent(pendingIntent);
                Notification notification = builder.build();
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(y, notification);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }




        try {
            JSONArray jsonArray = gd.Get_Passenger_GetAcceptedRequestsFromDriver(Integer.parseInt(ID));
            for (int i = 0;i< jsonArray.length();i++){
                JSONObject j = jsonArray.getJSONObject(i);
                Intent intent = new Intent(this, DriverAlertsForRequest.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                Notification.Builder builder = new Notification.Builder(this);
                builder.setContentTitle("Route :" + j.getString("RouteName"));
                builder.setContentText(j.getString("DriverName") + " Accepted Your Request ");
                builder.setSmallIcon(R.drawable.sharekni_logo);
                builder.setContentIntent(pendingIntent);
                Notification notification = builder.build();
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(y, notification);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }







    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ISRunning = false;
    }
}
