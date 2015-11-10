package rta.ae.sharekni;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

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

        webview.loadUrl("https://www.sharekni.ae/en/Route_PrintMobilePermit.aspx?p="+Permit_ID);
        Log.d("ViewPermit:","https://www.sharekni.ae/en/Route_PrintMobilePermit.aspx?p="+Permit_ID);





    }


}
