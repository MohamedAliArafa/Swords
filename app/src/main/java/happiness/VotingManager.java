package happiness;

import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import rta.ae.sharekni.StartScreen.StartScreenActivity;

/**
 * Created by dsg216 on 3/24/16.
 */
public class VotingManager {

    /**
     * This is QA URL. Replace it with production once it is ready for production.
     */
    private static final String HAPPINESS_URL = "https://happinessmeterqa.dubai.gov.ae/MobileSubmitFeedback";
    public static StartScreenActivity startScreenActivity = new StartScreenActivity();

    public enum TYPE {
        TRANSACTION,
        WITH_MICROAPP,
        WITHOUT_MICROAPP
    }

    public static void loadHappiness(@NonNull final WebView webView, @NonNull final VotingRequest votingRequest, @NonNull final String secret, @NonNull final String serviceProvider, @NonNull final String clientId, @NonNull final String language) {

        String json = null;
        try {
            json = votingRequest.toJson();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String signature = json + "|" + secret;
        String random = Utils.generate16DigitRandom();
        String nonceText = random + "|" + votingRequest.getHeader().getTimeStamp() + "|" + secret;

        String nonce = Utils.generateHash(nonceText);
        String signatureHash = Utils.generateHash(signature);
        try {
            VotingManager.loadHappiness(webView, json, signatureHash, clientId, language, random, votingRequest.getHeader().getTimeStamp(), nonce, votingRequest.getAdditionalParams());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load Happiness Widget
     *
     * @param webView
     * @param jsonPayLoad      Json PayLoad
     * @param signature        Signature of Application
     * @param clientId         Client Id of Application
     * @param language         Language of Application
     * @param random           Generated Random
     * @param timestamp        Current Timestamp
     * @param nonce            Nonce Value
     * @param additionalParams Additional Parameters
     */
    private static void loadHappiness(final WebView webView, String jsonPayLoad, String signature, String clientId, String language,
                                      String random, String timestamp, String nonce, Map<String, String> additionalParams) throws UnsupportedEncodingException {

        webView.loadUrl("about:blank");
        StringBuilder addParams = new StringBuilder();
        if (additionalParams != null) {
            for (String key :
                    additionalParams.keySet()) {
                String value = additionalParams.get(key);
                addParams.append(String.format("&%s=%s", key, Utils.encode(value)));
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("json_payload=");
        stringBuilder.append(Utils.encode(jsonPayLoad));
        stringBuilder.append("&");
        stringBuilder.append("signature=");
        stringBuilder.append(Utils.encode(signature));
        stringBuilder.append("&");
        stringBuilder.append("client_id=");
        stringBuilder.append(Utils.encode(clientId));
        stringBuilder.append("&");
        stringBuilder.append("lang=");
        stringBuilder.append(Utils.encode(language));
        stringBuilder.append("&");
        stringBuilder.append("random=");
        stringBuilder.append(Utils.encode(random));
        stringBuilder.append("&");
        stringBuilder.append("timestamp=");
        stringBuilder.append(Utils.encode(timestamp));
        stringBuilder.append("&");
        stringBuilder.append("nonce=");
        stringBuilder.append(Utils.encode(nonce));
        stringBuilder.append(addParams);

        String params = stringBuilder.toString();
        WebSettings webViewSettings = webView.getSettings();
        webViewSettings.setJavaScriptEnabled(true);
        webView.clearCache(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("happiness://done")) {
                    if (webView.getParent() != null)
                        webView.setVisibility(View.GONE);
                    StartScreenActivity.webView.setVisibility(View.GONE);


                    return true;
                }
                return false;
            }
        });

        webView.postUrl(HAPPINESS_URL, params.getBytes());
        webView.setVisibility(View.VISIBLE);
    }

}
