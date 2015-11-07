package rta.ae.sharekni;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import rta.ae.sharekni.R;

public class Privacy_Policy extends AppCompatActivity {

    WebView webview;
    Button agreeBtn;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy__policy);
        initToolbar();

        webview = (WebView) findViewById(R.id.webview);
        agreeBtn = (Button) findViewById(R.id.agreeBtn);
        webview.loadUrl("file:///android_asset/policy.html");


        agreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Privacy_Policy.this.finish();
            }
        });
    }




    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText("Security and Privacy Policies");
//        toolbar.setElevation(10);

        setSupportActionBar(toolbar);

    }
}
