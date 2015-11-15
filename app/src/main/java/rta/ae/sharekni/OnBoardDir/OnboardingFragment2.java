package rta.ae.sharekni.OnBoardDir;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Locale;

import rta.ae.sharekni.Arafa.Activities.BestDriversBeforeLogin;
import rta.ae.sharekni.Arafa.Activities.BestRideBeforeLogin;
import rta.ae.sharekni.R;


/**
 * Created by nezar on 8/11/2015.
 */


public class OnboardingFragment2 extends Fragment {

    RelativeLayout im_best_rides;
    RelativeLayout im_best_drivers;
    ImageView OnBoard_Changelanguae;

    String Locale_Str;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.onboard_new_3
                ,
                container,
                false
        );
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        im_best_drivers = (RelativeLayout) view.findViewById(R.id.im_best_drivers);
        im_best_rides = (RelativeLayout) view.findViewById(R.id.im_best_rides);
        OnBoard_Changelanguae = (ImageView) view.findViewById(R.id.OnBoard_Changelanguae);


        OnBoard_Changelanguae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Configuration config = new Configuration();
                Locale locale = Locale.getDefault();
                Locale_Str = locale.toString();
                Log.d("test locale", Locale_Str);


               if (Locale_Str.contains("en")){

                   Locale locale2 = new Locale("ar");
                   Locale.setDefault(locale2);
                   Configuration config2 = new Configuration();
                   config2.locale = locale2;
                   getActivity().getApplicationContext().getResources().updateConfiguration(config2, null);
                   getActivity().finish();
                   startActivity(getActivity().getIntent());


               }else {


                   Locale locale3 = new Locale("en");
                   Locale.setDefault(locale3);
                   Configuration config3 = new Configuration();
                   config3.locale = locale3;
                   getActivity().getApplicationContext().getResources().updateConfiguration(config3, null);
                   getActivity().finish();
                   startActivity(getActivity().getIntent());
               }



            }
        });


        im_best_rides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BestRideBeforeLogin.class);
                startActivity(intent);
            }
        });

        im_best_drivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BestDriversBeforeLogin.class);
                startActivity(intent);
            }
        });


// im_register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =  new Intent(getActivity(), RegisterNewTest.class);
//                startActivity(intent);
//            }
//        });
//
//        im_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =  new Intent(getActivity(), LoginApproved.class);
//                startActivity(intent);
//            }
//        });
    }
}