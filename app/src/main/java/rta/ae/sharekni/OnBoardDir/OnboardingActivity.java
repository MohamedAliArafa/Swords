package rta.ae.sharekni.OnBoardDir;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import rta.ae.sharekni.HomePage;
import rta.ae.sharekni.LoginApproved;
import rta.ae.sharekni.QSearch;
import rta.ae.sharekni.R;
import rta.ae.sharekni.RegisterNewTest;
import rta.ae.sharekni.Sharekni;
import rta.ae.sharekni.TakeATour;

/*
 * Created by nezar on 8/11/2015.
 */
public class OnboardingActivity extends FragmentActivity {


    static OnboardingActivity onboardingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onboardingActivity=this;

//        Intent intent =  new Intent(this,TestVedio.class);
//        startActivity(intent);
        try {
            if (Sharekni.getInstance() != null) {
                Sharekni.getInstance().finish();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
        String ID = myPrefs.getString("account_id", null);

        if (ID != null){
            Log.d("ID = :", ID);
            Intent in = new Intent(this, HomePage.class);
            startActivity(in);
        }



//        else {
//            try {
//                if (HomePage.getInstance() == null) {
//                    Intent intent =  new Intent(this,TestVedio.class);
//                    startActivity(intent);
//                }else {
//                    HomePage.getInstance().finish();
//                }
//            } catch (NullPointerException e) {
//                e.printStackTrace();
//            }
//        }





        setContentView(R.layout.activity_log_in_form_concept_one);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        //  indicator = (SmartTabLayout)findViewById(R.id.indicator);
        //  indicator.setVisibility(View.INVISIBLE);

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
                Intent intent = new Intent(getBaseContext(), QSearch.class);
                startActivity(intent);
            }
        });





        btn_top_rides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//               Intent intent =  new Intent(getBaseContext(), TestVedio.class);
//                startActivity(intent);

                Intent intent = new Intent(getBaseContext(), TakeATour.class);
                startActivity(intent);



            }
        });



    FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return new OnboardingFragment2();

//                switch (position) {
//                    case 0 : return new OnboardingFragment1();
//                    case 1 : return new OnboardingFragment2();
//                    default: return null;
//                }
            }

            @Override
            public int getCount() {
            return 1;
            }
        };


        pager.setAdapter(adapter);



    }//oncreate

//    public void finishOnBoarding() {
//        // Get the shared preferences
//        SharedPreferences preferences = getSharedPreferences("my_onboard_preferences", MODE_PRIVATE);
//
//        // Set onboarding_complete to true
//        preferences.edit()
//                .putBoolean("onboarding_complete",true).apply();
//
//        // Launch the main Activity, called MainActivity
//        Intent main = new Intent(this, Sharekni.class);
//        startActivity(main);
//
//        // Close the OnboardingActivity
//        finish();
//
//
//    }


    public static OnboardingActivity getInstance(){
        return  onboardingActivity ;
    }


}
