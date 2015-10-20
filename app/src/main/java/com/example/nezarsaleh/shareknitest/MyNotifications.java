package com.example.nezarsaleh.shareknitest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Nezar Saleh on 10/19/2015.
 */

public class MyNotifications extends Service {

   int i =0;
    int y =0;
boolean ISRunning=true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

          i = intent.getIntExtra("Flag",0);
            Log.d("alert numbers", String.valueOf(i));



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
                            }catch(InterruptedException e){

                                e.printStackTrace();
                            }


                        }


                    }
            });

            t.start();



        return super.onStartCommand(intent, flags, startId);

    }


    private void CreateNotification(int y){




        Intent intent =  new Intent(this,MyNotifications.class);
        PendingIntent pendingIntent  = PendingIntent.getActivity(this,0,intent,0);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("New Notification");
        builder.setContentText("subject");
        builder.setSmallIcon(R.drawable.sharekni_logo);
        builder.setContentIntent(pendingIntent);
        builder.addAction(android.R.drawable.ic_media_play, "play", pendingIntent);
        builder.addAction(android.R.drawable.ic_media_pause, "pause", pendingIntent);

        Notification notification = builder.build();
        NotificationManager manager  = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(y,notification);

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        ISRunning=false;
    }
}
