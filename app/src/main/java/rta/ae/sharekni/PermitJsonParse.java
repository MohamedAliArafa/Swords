package rta.ae.sharekni;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import rta.ae.sharekni.Arafa.Classes.VolleySingleton;

/**
 * Created by Nezar Saleh on 10/31/2015.
 */
public class PermitJsonParse {

    public void stringRequest(final String url, final Context context) {

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = response.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                        response = response.replaceAll("<string xmlns=\"http://Sharekni-MobAndroid-Data.org/\">", "");
                        response = response.replaceAll("</string>", "");
                        // Display the first 500 characters of the response string.
                        Log.d("Check permit URl",url);

                        try {
                            String data = response.substring(40);
                            Log.d("Data:", data);

                            if (data.equals("\"1\"")){
                                Toast.makeText(context, "Permit Granted", Toast.LENGTH_LONG).show();

                            }else if(data.equals("-1")){
                                Toast.makeText(context, "You can't gain permit", Toast.LENGTH_LONG).show();
                                Log.d("inside -1",data);
                            }
                        } catch (StringIndexOutOfBoundsException e) {
                            Toast.makeText(context, "Permit json parse", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }






                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error : ", error.toString());
            }
        });
//      Add a request (in this example, called stringRequest) to your RequestQueue.
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);



    }


}
