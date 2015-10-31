package com.example.nezarsaleh.shareknitest;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.example.nezarsaleh.shareknitest.Arafa.Classes.GetData;

public class ViewPermit extends AppCompatActivity {


    WebView webview;

    int Permit_ID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_permit);

        Intent intent = getIntent();
        Permit_ID = intent.getIntExtra("ID", 0);
        Log.d("ID", String.valueOf(Permit_ID));


        webview = (WebView) findViewById(R.id.webview);

        webview.loadUrl("http://sharekni.sdgstaff.com/en/Route_PrintMobilePermit.aspx?p="+Permit_ID);






    }


}
