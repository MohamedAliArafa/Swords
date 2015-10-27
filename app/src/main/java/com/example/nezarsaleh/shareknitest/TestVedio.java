package com.example.nezarsaleh.shareknitest;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class TestVedio extends Activity {

    private String filename;
    private static final int INSERT_ID = Menu.FIRST;

    DisplayMetrics dm;

    RelativeLayout Relative_Splash;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//    public void onVideoSelected(String uri, String mimeType) {
//        Uri video=Uri.fromFile(new File(uri));
//        Intent i=new Intent(Intent.ACTION_VIEW);
//
//        i.setDataAndType(video, mimeType);
//        startActivity(i);
//    }

          setContentView(R.layout.vediotest);
//        System.gc();
//        Intent i = getIntent();
//        Bundle extras = i.getExtras();
//
//        filename = "android.resource://" + getPackageName() + "/" + R.raw.introfinal;
//        VideoView vv = (VideoView) findViewById(R.id.surfaceViewFrame);
//        dm = new DisplayMetrics();
//        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int height = dm.heightPixels;
//        int width = dm.widthPixels;
//        vv.setMinimumWidth(width);
//        vv.setMinimumHeight(height);



        //setContentView(vv);
        //vv.setVideoPath(filename);
//        vv.setMediaController(new MediaController(this));
//        vv.requestFocus();
      //  vv.start();



       // vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
           // @Override
          //  public void onCompletion(MediaPlayer mp) {

              //  finish();
          //  }
       // });



        Relative_Splash = (RelativeLayout) findViewById(R.id.Relative_Splash);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Relative_Splash.setBackgroundResource(R.drawable.splashtwo);

                // Do something after 5s = 5000ms

            }
        }, 5000);

        final Handler handler2 = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

               finish();

                // Do something after 5s = 5000ms

            }
        }, 5000);




    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        menu.add(0, INSERT_ID, 0, "FullScreen");
//
//        return true;
//    }
//
//    @Override
//    public boolean onMenuItemSelected(int featureId, MenuItem item) {
//        switch(item.getItemId()) {
//            case INSERT_ID:
//                createNote();
//        }
//        return true;
//    }
//
//    private void createNote() {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//    }
//

    }





