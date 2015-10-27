package com.example.nezarsaleh.shareknitest;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.example.nezarsaleh.shareknitest.MainNavigationDrawerFragment.NavigationDrawerFragment;

public class TermsAndCond extends AppCompatActivity {

    Toolbar toolbar;
    WebView webview;
    Button agreeBtn;
    boolean isAgreed = false, isRules = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_cond);
        webview = (WebView) findViewById(R.id.webview);
        agreeBtn = (Button) findViewById(R.id.agreeBtn);
        initToolbar();

        webview.loadUrl("file:///android_asset/terms_en.html");
        isRules = true;

        agreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAgreed = true;
                TermsAndCond.this.finish();
            }
        });


    }


    @Override
    public void onBackPressed() {
        if (isRules) {

            final Dialog dialog = new Dialog(TermsAndCond.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.terms_dialog);
            TextView terms_btn_ok = (TextView) dialog.findViewById(R.id.terms_btn_ok);
            dialog.show();

            terms_btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


        } else {
            super.onBackPressed();
        }

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar2);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);

        textView.setText("TERMS AND CONDITIONS");
        //  toolbar.setElevation(10);

        setSupportActionBar(toolbar);
//        TextView mytext = (TextView) toolbar.findViewById(R.id.mytext_appbar);
//        mytext.setText("Most Rides");


    }

}