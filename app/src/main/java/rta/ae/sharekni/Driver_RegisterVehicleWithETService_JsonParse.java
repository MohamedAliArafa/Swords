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
public class Driver_RegisterVehicleWithETService_JsonParse {


    public void stringRequest(final String url, final Context context) {
        Log.d("Register Vehicle URl",url);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,


                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = response.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                        response = response.replaceAll("<string xmlns=\"http://Sharekni-MobAndroid-Data.org/\">", "");
                        response = response.replaceAll("</string>", "");
                        // Display the first 500 characters of the response string.
                        Log.d("Register Vehicle URl",url);

                        try {
                            String data = response.substring(40);
                            Log.d("Data:", data);

                            if (data.equals("\"1\"")){
                                Toast.makeText(context, R.string.verified, Toast.LENGTH_LONG).show();

                            }else if(data.equals("\"-3\"")){
                                Toast.makeText(context, R.string.invalid_dob, Toast.LENGTH_LONG).show();
                                Log.d("inside -3",data);
                            }else if (data.equals("\"-4\"")){
                                Toast.makeText(context, R.string.lic_ver_but_no_cars, Toast.LENGTH_LONG).show();

                            }else if (data.equals("\"-5\"") || data.equals("\"-6\"") ){
                                Toast.makeText(context, R.string.invalid_data, Toast.LENGTH_LONG).show();

                            }else if (data.equals("\"0\"")){
                               //  Toast.makeText(context, "license verified, but no cars found ", Toast.LENGTH_LONG).show();
                                Log.d("license no json",data+" Error in Connection with the DataBase Server");
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
