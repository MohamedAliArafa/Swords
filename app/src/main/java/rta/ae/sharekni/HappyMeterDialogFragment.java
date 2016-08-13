package rta.ae.sharekni;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

import java.util.Locale;

import happiness.Application;
import happiness.Header;
import happiness.Transaction;
import happiness.User;
import happiness.Utils;
import happiness.VotingManager;
import happiness.VotingRequest;

/**
 * Created by nezar on 7/16/16.
 */
public class HappyMeterDialogFragment extends DialogFragment {
    /**
     * The system calls this to get the DialogFragment's layout, regardless
     * of whether it's being displayed as a dialog or an embedded fragment.
     */


    public static DialogFragment newFragment;


    private static final String SECRET = "aaaf179f5f4b852f"; //TODO: To be replaced by one provided by DSG.
    private static final String SERVICE_PROVIDER = "DSG"; //TODO: To be replaced by the spName e.g. RTA, DEWA.
    private static final String CLIENT_ID = "dsg123"; //TODO: Replace with your own client id
    private static final String MICRO_APP = "SharekniDEmo123"; //TODO: To be replaced by the name of your microapp.
    private static String LANGUAGE = "en"; //TODO: set your preferred language accordingly.


    public static WebView webView;
    private String Locale_Str;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment


        return inflater.inflate(R.layout.happy_meter_dailog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = (WebView) view.findViewById(R.id.webView);
        webView.setVisibility(View.VISIBLE);
        webView.setTag(webView.getVisibility());
        load(VotingManager.TYPE.WITH_MICROAPP);

        webView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int newVis = webView.getVisibility();
                if ((int) webView.getTag() != newVis) {
                    webView.setTag(webView.getVisibility());
                    HappyMeterDialogFragment.newFragment.dismiss();
                    //visibility has changed
                }

            }
        });


    }

    /**
     * The system calls this only when creating the layout in a dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        return dialog;
    }


    private void load(VotingManager.TYPE type) {

        boolean result = checkConstantValues();

        if (!result) {
            showErrorToast();
            return;
        }

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

        Locale locale = Locale.getDefault();
        Locale_Str = locale.toString();
        Log.d("Main  Home locale", Locale_Str);
        if (Locale_Str.contains("en")) {
            LANGUAGE = "en";
        } else {
            LANGUAGE = "ar";
        }

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
        Toast.makeText(getActivity(), "Please setup constant values in MainActivity.java", Toast.LENGTH_LONG).show();
    }

    private boolean checkConstantValues() {
        if (SECRET.isEmpty() || SERVICE_PROVIDER.isEmpty()
                || CLIENT_ID.isEmpty() || MICRO_APP.isEmpty())
            return false;
        return true;
    }


}
