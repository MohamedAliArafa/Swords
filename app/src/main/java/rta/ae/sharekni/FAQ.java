package rta.ae.sharekni;

import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

public class FAQ extends AppCompatActivity {

    private Toolbar toolbar;


    WebView webView;
    private String Locale_Str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_webview);

        webView = (WebView) findViewById(R.id.FAQ_WebView);


        Locale locale = Locale.getDefault();
        Locale_Str = locale.toString();
        Log.d("Main  Home locale", Locale_Str);

        if (Locale_Str.contains("en")) {
            webView.loadUrl("http://sharekni-web.sdg.ae/mob/en/Accordion.aspx");

        } else {
            webView.loadUrl("http://sharekni-web.sdg.ae/mob/ar/accordion.aspx");
        }


        initToolbar();


    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);

        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText(R.string.help_and_faq);
//        toolbar.setElevation(10);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar);
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
