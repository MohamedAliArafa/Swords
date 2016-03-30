package rta.ae.sharekni.Arafa.Activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import rta.ae.sharekni.Arafa.Classes.GetData;
import rta.ae.sharekni.Arafa.DataModel.BestDriverDataModel;
import rta.ae.sharekni.Arafa.DataModelAdapter.BestDriverDataModelAdapter;
import rta.ae.sharekni.R;

public class BestDriversBeforeLogin extends AppCompatActivity {

    TextView tv;
    ListView lv;
    Toolbar toolbar;
    jsoning jsoning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arafa);

        tv = (TextView) findViewById(R.id.info);
        lv = (ListView) findViewById(R.id.lvMain);
       initToolbar();
        jsoning = new jsoning();

        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);

        jsoning.execute();
    }

    public class jsoning extends AsyncTask {

        private ProgressDialog pDialog;
        private List<BestDriverDataModel> arr = new ArrayList<>();
        boolean exists = false;
        BestDriverDataModelAdapter adapter;
        BestDriverDataModel driver;
        JSONObject obj;

        @Override
        protected void onPreExecute() {
            adapter = new BestDriverDataModelAdapter(BestDriversBeforeLogin.this, arr);
            lv.setAdapter(adapter);
            pDialog = new ProgressDialog(BestDriversBeforeLogin.this);
            pDialog.setMessage(getString(R.string.loading) + "...");
            pDialog.setIndeterminate(false);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            if (exists){
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent in = new Intent(BestDriversBeforeLogin.this, Profile.class);
                        in.putExtra("DriverID", arr.get(i).getID());
                        Log.d("Array Id :", String.valueOf(arr.get(i).getID()));
                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        BestDriversBeforeLogin.this.startActivity(in);
                    }
                });
            }
            hidePDialog();
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            pDialog.setProgress((Integer) values[0]);
            lv.setAdapter(adapter);
            lv.requestLayout();
        }

        private void hidePDialog() {
            if (pDialog != null) {
                pDialog.dismiss();
                pDialog = null;
            }
        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                SocketAddress sockaddr = new InetSocketAddress("www.google.com", 80);
                Socket sock = new Socket();
                int timeoutMs = 20000;   // 2 seconds
                sock.connect(sockaddr, timeoutMs);
                exists = true;
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(BestDriversBeforeLogin.this)
                                .setTitle(getString(R.string.connection_problem))
                                .setMessage(getString(R.string.con_problem_message))
                                .setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        startActivity(getIntent());
                                    }
                                })
                                .setNegativeButton(getString(R.string.goBack), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();
                        Toast.makeText(BestDriversBeforeLogin.this,getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                JSONArray response = null;
                try {
                    response = new GetData().GetBestDrivers();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < response.length(); i++) {
                    try {
                        obj = response.getJSONObject(i);
                        driver = new BestDriverDataModel(Parcel.obtain());
                        driver.setID(obj.getInt("AccountId"));
                        driver.setName(obj.getString("AccountName"));
                        driver.setPhotoURL(obj.getString("AccountPhoto"));
                        driver.setNationality(obj.getString(getString(R.string.nat_name2)));
                        driver.setRating(obj.getInt("Rating"));
                        driver.setPhoneNumber(obj.getString("AccountMobile"));

                        int x1 = obj.getInt("GreenPoints");
                        int x3 = obj.getInt("CO2Saved");
                        x3 = x3/1000;

                        driver.setGreenPoints(String.valueOf(x1));
                        driver.setCO2Saved(String.valueOf(x3));



                       // driver.setGreenPoints(obj.getString("GreenPoints"));
                     //   driver.setCO2Saved(obj.getString("CO2Saved"));


                        if (!obj.getString("AccountPhoto").equals("NoImage.png")){
                            GetData gd = new GetData();
                            driver.setPhoto(gd.GetImage(obj.getString("AccountPhoto")));
                        }
                        driver.setLastSeen(obj.getString("LastSeen"));


                        arr.add(driver);
                        publishProgress((int)(i*100/response.length()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_most_rides_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id==android.R.id.home){
            onBackPressed();
            return true;
        }
        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }





    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText(getString(R.string.onboard_best_drivers));
//        toolbar.setElevation(10);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onBackPressed() {
        if (jsoning.getStatus() == AsyncTask.Status.RUNNING){
            jsoning.cancel(true);
        }
        finish();
    }

}
