package rta.ae.sharekni;

import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FAQ extends AppCompatActivity {

    private Toolbar toolbar;

    RelativeLayout FAQ_Relative_1, FAQ_Relative_2, FAQ_Relative_3, FAQ_Relative_4, FAQ_Relative_5,
            FAQ_Relative_6, FAQ_Relative_7, FAQ_Relative_8, FAQ_Relative_9, FAQ_Relative_10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        FAQ_Relative_1 = (RelativeLayout) findViewById(R.id.FAQ_Relative_1);
        FAQ_Relative_2 = (RelativeLayout) findViewById(R.id.FAQ_Relative_2);
        FAQ_Relative_3 = (RelativeLayout) findViewById(R.id.FAQ_Relative_3);
        FAQ_Relative_4 = (RelativeLayout) findViewById(R.id.FAQ_Relative_4);
        FAQ_Relative_5 = (RelativeLayout) findViewById(R.id.FAQ_Relative_5);
        FAQ_Relative_6 = (RelativeLayout) findViewById(R.id.FAQ_Relative_6);
        FAQ_Relative_7 = (RelativeLayout) findViewById(R.id.FAQ_Relative_7);
        FAQ_Relative_8 = (RelativeLayout) findViewById(R.id.FAQ_Relative_8);
        FAQ_Relative_9 = (RelativeLayout) findViewById(R.id.FAQ_Relative_9);
        FAQ_Relative_10 = (RelativeLayout) findViewById(R.id.FAQ_Relative_10);

        initToolbar();


        FAQ_Relative_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), FAQ_Details.class);
                intent.putExtra("Value", 1);
                startActivity(intent);

            }
        });


        FAQ_Relative_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), FAQ_Details.class);
                intent.putExtra("Value", 2);
                startActivity(intent);

            }
        });


        FAQ_Relative_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), FAQ_Details.class);
                intent.putExtra("Value", 3);
                startActivity(intent);

            }
        });

        FAQ_Relative_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), FAQ_Details.class);
                intent.putExtra("Value", 4);
                startActivity(intent);

            }
        });

        FAQ_Relative_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), FAQ_Details.class);
                intent.putExtra("Value", 5);
                startActivity(intent);

            }
        });

        FAQ_Relative_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), FAQ_Details.class);
                intent.putExtra("Value", 6);
                startActivity(intent);


            }
        });

        FAQ_Relative_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), FAQ_Details.class);
                intent.putExtra("Value", 7);
                startActivity(intent);


            }
        });

        FAQ_Relative_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), FAQ_Details.class);
                intent.putExtra("Value", 8);
                startActivity(intent);

            }
        });

        FAQ_Relative_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), FAQ_Details.class);
                intent.putExtra("Value", 9);
                startActivity(intent);


            }
        });

        FAQ_Relative_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), FAQ_Details.class);
                intent.putExtra("Value", 10);
                startActivity(intent);

            }
        });


    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);

        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText("FAQ");
//        toolbar.setElevation(10);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }


}
