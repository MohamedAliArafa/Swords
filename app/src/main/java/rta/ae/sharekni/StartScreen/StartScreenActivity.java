package rta.ae.sharekni.StartScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

import happiness.Application;
import happiness.Header;
import happiness.Transaction;
import happiness.User;
import happiness.Utils;
import happiness.VotingManager;
import happiness.VotingRequest;
import rta.ae.sharekni.HappyMeterDialogFragment;
import rta.ae.sharekni.HomePage;
import rta.ae.sharekni.LoginApproved;
import rta.ae.sharekni.QuickSearch;
import rta.ae.sharekni.R;
import rta.ae.sharekni.RegisterNewTest;
import rta.ae.sharekni.MainActivityClass.Sharekni;
import rta.ae.sharekni.TakeATour.TakeATour;

/*
 * Created by nezar on 8/11/2015.
 */
public class StartScreenActivity extends FragmentActivity {

    static StartScreenActivity onboardingActivity;
    private String Locale_Str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onboardingActivity = this;

        try {
            if (Sharekni.getInstance() != null) {
                Sharekni.getInstance().finish();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
        String ID = myPrefs.getString("account_id", null);

        if (ID != null) {
            Log.d("ID = :", ID);
            Intent in = new Intent(this, HomePage.class);
            startActivity(in);
        }

        setContentView(R.layout.activity_log_in_form_concept_one);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);


        ImageView btn_register = (ImageView) findViewById(R.id.fr_register);
        ImageView btn_search = (ImageView) findViewById(R.id.fr_search);
        ImageView btn_top_rides = (ImageView) findViewById(R.id.fr_top_rides_id);
        ImageView btn_log_in = (ImageView) findViewById(R.id.fr_login);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisterNewTest.class);
                startActivity(intent);
            }
        });

        btn_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginApproved.class);
                startActivity(intent);
            }
        });


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), QuickSearch.class);
                startActivity(intent);
            }
        });

        btn_top_rides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getBaseContext(), TakeATour.class);
//                startActivity(intent);

                HappyMeterDialogFragment.newFragment = new HappyMeterDialogFragment();
                HappyMeterDialogFragment.newFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.theme_sms_receive_dialog);
                HappyMeterDialogFragment.newFragment.show(getSupportFragmentManager(), "missiles");

            }
        });


        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new StartScreenFragment();
            }

            @Override
            public int getCount() {
                return 1;
            }
        };

        pager.setAdapter(adapter);

        Locale locale = Locale.getDefault();
        Locale_Str = locale.toString();
        Log.d("test locale", Locale_Str);

        switch (Locale_Str){
            case "en":
                btn_register.setImageResource(R.drawable.frregisternew);
                btn_log_in.setImageResource(R.drawable.frloginnew);
                btn_search.setImageResource(R.drawable.frsearchnew);
                btn_top_rides.setImageResource(R.drawable.frhapppymeternew);
                break;
            case "ar":
                btn_register.setImageResource(R.drawable.frregsiterarabic);
                btn_log_in.setImageResource(R.drawable.frloginarabic);
                btn_search.setImageResource(R.drawable.frsearcharabic);
                btn_top_rides.setImageResource(R.drawable.happymeterarabic);
                break;

            case "zh":
                btn_register.setImageResource(R.drawable.frregisternew_ch);
                btn_log_in.setImageResource(R.drawable.frloginnew_ch);
                btn_search.setImageResource(R.drawable.frsearchnew_ch);
                btn_top_rides.setImageResource(R.drawable.frhapppymeternew_ch);
                break;

            case "fil":
                btn_register.setImageResource(R.drawable.frregisternew_fi);
                btn_log_in.setImageResource(R.drawable.frloginnew);
                btn_search.setImageResource(R.drawable.frsearchnew_fi);
                btn_top_rides.setImageResource(R.drawable.frhapppymeternew_fi);
                break;
            default:

                break;

        }

    }//oncreate


    public static StartScreenActivity getInstance() {
        return onboardingActivity;
    }

}
