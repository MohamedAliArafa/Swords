package rta.ae.sharekni.StartScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import happiness.Application;
import happiness.Header;
import happiness.Transaction;
import happiness.User;
import happiness.Utils;
import happiness.VotingManager;
import happiness.VotingRequest;
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
    private static final String SECRET = "aaaf179f5f4b852f"; //TODO: To be replaced by one provided by DSG.
    private static final String SERVICE_PROVIDER = "DSG"; //TODO: To be replaced by the spName e.g. RTA, DEWA.
    private static final String CLIENT_ID = "dsg123"; //TODO: Replace with your own client id
    private static final String MICRO_APP = "SharekniDEmo123"; //TODO: To be replaced by the name of your microapp.
    private static String LANGUAGE = "en"; //TODO: set your preferred language accordingly.

    public static WebView webView;

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
        webView = (WebView) findViewById(R.id.webView);


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
                webView.setVisibility(View.VISIBLE);
                load(VotingManager.TYPE.WITH_MICROAPP);


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


    }//oncreate

    private void load(VotingManager.TYPE type) {

        boolean result = checkConstantValues();

        if (!result) {
            showErrorToast();
            return;
        }
        WebView webView = (WebView) findViewById(R.id.webView);

        String secret = SECRET;
        String serviceProvider = SERVICE_PROVIDER;
        String clientID = CLIENT_ID;

        VotingRequest request = new VotingRequest();
        User user = new User();
        if (type == VotingManager.TYPE.TRANSACTION) {
            Transaction transaction = new Transaction();
            //TODO: Set the below values accordingly.
            transaction.setGessEnabled("false");
            transaction.setNotes("MobileSDK Vote");
            transaction.setServiceDescription("Demo Transaction");
            transaction.setChannel("SMARTAPP");
            transaction.setServiceCode("");
            transaction.setTransactionID("SAMPLE123-REPLACEWITHACTUAL!");

            request.setTransaction(transaction);
        } else {
            //TODO: Set the below values accordingly.
            Application application = new Application("12345", "http://mpay.qa.adeel.dubai.ae", "SMARTAPP", "ANDROID");
            application.setNotes("MobileSDK Vote");
            request.setApplication(application);
        }
        String timeStamp = Utils.getUTCDate();
        Header header = new Header();
        header.setTimeStamp(timeStamp);
        header.setThemeColor("#ff0000");
        header.setServiceProvider(serviceProvider);
        if (type == VotingManager.TYPE.WITH_MICROAPP) {
            if (LANGUAGE.equals("ar"))
                header.setMicroAppDisplay("تطبيق");
            else
                header.setMicroAppDisplay("Micro App");
            header.setMicroApp(MICRO_APP);
        }


        request.setHeader(header);
        request.setUser(user);
        VotingManager.loadHappiness(webView, request, secret, serviceProvider, clientID, LANGUAGE);
    }

    private void showErrorToast() {
        Toast.makeText(this, "Please setup constant values in MainActivity.java", Toast.LENGTH_LONG).show();
    }

    private boolean checkConstantValues() {
        if (SECRET.isEmpty() || SERVICE_PROVIDER.isEmpty()
                || CLIENT_ID.isEmpty() || MICRO_APP.isEmpty())
            return false;
        return true;
    }

    public static StartScreenActivity getInstance() {
        return onboardingActivity;
    }

}
