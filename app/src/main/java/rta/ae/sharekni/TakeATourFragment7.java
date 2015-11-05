package rta.ae.sharekni;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import rta.ae.sharekni.OnBoardDir.OnboardingActivity;

import com.example.nezarsaleh.shareknitest.R;

/**
 * Created by Nezar Saleh on 10/17/2015.
 */
public class TakeATourFragment7 extends Fragment {

    Button fr7_btn1;
    Button fr7_btn2;
    Button fr7_btn3;
    Button fr7_btn4;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.take_tour_fragment_7
                ,
                container,
                false
        );
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fr7_btn1 = (Button) view.findViewById(R.id.fr7_btn1);

        fr7_btn2 = (Button) view.findViewById(R.id.fr7_btn2);

        fr7_btn3 = (Button) view.findViewById(R.id.fr7_btn3);

        fr7_btn4 = (Button) view.findViewById(R.id.fr7_btn4);


        fr7_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OnboardingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });



        fr7_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginApproved.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });



        fr7_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RegisterNewTest.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });



        fr7_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), QSearch.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });





    }

}
