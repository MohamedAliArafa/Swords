package rta.ae.sharekni;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

import rta.ae.sharekni.R;

public class DriverJoinedRides extends AppCompatActivity {
    Toolbar toolbar;

    ListView user_ride_joined;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_joined_rides);
        initToolbar();
        user_ride_joined = (ListView) findViewById(R.id.user_ride_joined);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText("Joined Rides");
//        toolbar.setElevation(10);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            Locale locale = Locale.getDefault();
            String Locale_Str2 = locale.toString();
            if (Locale_Str2.contains("en")) {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_arrow_back);
            } else {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_arrow_forward);
            }            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
