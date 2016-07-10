package rta.ae.sharekni;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

public class FAQ_Details extends AppCompatActivity {

    TextView FAQ_Question_txt, FAQ_Answer_txt;
    int Value = 0;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq__details);
        FAQ_Question_txt = (TextView) findViewById(R.id.FAQ_Question_txt);
        FAQ_Answer_txt = (TextView) findViewById(R.id.FAQ_Answer_txt);
        initToolbar();

        Intent intent = getIntent();
        Value = intent.getIntExtra("Value", 0);

        switch (Value) {
            case 1:
                FAQ_Question_txt.setText(R.string.FAQ_Question_1);
                FAQ_Answer_txt.setText(R.string.FAQ_Answer_1);
                break;
            case 2:
                FAQ_Question_txt.setText(R.string.FAQ_Question_1);
                FAQ_Answer_txt.setText(R.string.FAQ_Answer_2);
                break;
            case 3:
                FAQ_Question_txt.setText(R.string.FAQ_Question_3);
                FAQ_Answer_txt.setText(R.string.FAQ_Answer_3);
                break;
            case 4:
                FAQ_Question_txt.setText(R.string.FAQ_Question_4);
                FAQ_Answer_txt.setText(R.string.FAQ_Answer_4);
                break;
            case 5:
                FAQ_Question_txt.setText(R.string.FAQ_Question_5);
                FAQ_Answer_txt.setText(R.string.FAQ_Answer_5);
                break;
            case 6:
                FAQ_Question_txt.setText(R.string.FAQ_Question_6);
                FAQ_Answer_txt.setText(R.string.FAQ_Answer_6);
                break;
            case 7:
                FAQ_Question_txt.setText(R.string.FAQ_Question_7);
                FAQ_Answer_txt.setText(R.string.FAQ_Answer_7);
                break;
            case 8:
                FAQ_Question_txt.setText(R.string.FAQ_Question_8);
                FAQ_Answer_txt.setText(R.string.FAQ_Answer_8);
                break;
            case 9:
                FAQ_Question_txt.setText(R.string.FAQ_Question_9);
                FAQ_Answer_txt.setText(R.string.FAQ_Answer_9);
                break;
            case 10:
                FAQ_Question_txt.setText(R.string.FAQ_Question_10);
                FAQ_Answer_txt.setText(R.string.FAQ_Answer_10);
                break;
            default:
                Log.d("Switch", "Default");
                break;

        }

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
