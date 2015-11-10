package rta.ae.sharekni;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class TermsAndCond extends AppCompatActivity {

    Toolbar toolbar;
    WebView webview;
    Button agreeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_cond);
        webview = (WebView) findViewById(R.id.webview);
        agreeBtn = (Button) findViewById(R.id.agreeBtn);
        initToolbar();
        Locale locale = Locale.getDefault();
        String loca = locale.toString();
        Log.d("locale", loca);
        if (loca.equals("en_GB")) {

            webview.loadUrl(getString(R.string.terms_file));
        } else if (loca.equals("ar")) {


            webview.loadUrl(getString(R.string.terms_file_ar));
        }


        agreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TermsAndCond.this.finish();
            }
        });


    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar2);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);

        textView.setText(R.string.terms_con);
        //  toolbar.setElevation(10);

        setSupportActionBar(toolbar);
//        TextView mytext = (TextView) toolbar.findViewById(R.id.mytext_appbar);
//        mytext.setText("Most Rides");


    }

}