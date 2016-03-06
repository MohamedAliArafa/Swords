package rta.ae.sharekni;

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

import rta.ae.sharekni.Arafa.Classes.GetData;
import rta.ae.sharekni.Arafa.DataModel.BestRouteDataModel;
import rta.ae.sharekni.Arafa.DataModelAdapter.BestRouteDataModelAdapter;

public class MrMohamedAliTest extends AppCompatActivity {


    ListView AllRides;
    Toolbar toolbar;
    jsoning jsoning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mr_mohamed_ali_test);
        AllRides = (ListView) findViewById(R.id.TestAllRides);
        initToolbar();

        jsoning = new jsoning();

        jsoning.execute();


    }


    public class jsoning extends AsyncTask {

        private ProgressDialog pDialog;
        boolean exists = false;
        BestRouteDataModel[] driver;

        @Override
        protected void onPreExecute() {

            pDialog = new ProgressDialog(MrMohamedAliTest.this);
            pDialog.setMessage(getString(R.string.loading) + "...");
            pDialog.setIndeterminate(false);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            if (exists){
                BestRouteDataModelAdapter arrayAdapter = new BestRouteDataModelAdapter(MrMohamedAliTest.this, R.layout.top_rides_custom_row, driver);
                AllRides.setAdapter(arrayAdapter);
                AllRides.requestLayout();
                AllRides.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent in = new Intent(MrMohamedAliTest.this, MostRidesDetails.class);
                        in.putExtra("ID", i);
                        Bundle b = new Bundle();
                        b.putParcelable("Data", driver[i]);
                        in.putExtras(b);
                        MrMohamedAliTest.this.startActivity(in);
                    }
                });



            }
            hidePDialog();
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
                int timeoutMs = 2000;   // 2 seconds
                sock.connect(sockaddr, timeoutMs);
                exists = true;
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(MrMohamedAliTest.this)
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
                        Toast.makeText(MrMohamedAliTest.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                JSONArray response = null;
                try {
                    response = new GetData().AllMostDesiredRoutes();
                    assert response != null;
                    driver = new BestRouteDataModel[response.length()];
                    Log.d("length : ", String.valueOf(response.length()));
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject json = response.getJSONObject(i);
                        final BestRouteDataModel item = new BestRouteDataModel(Parcel.obtain());
                        item.setFromEm(json.getString(getString(R.string.from_em_name_en)));
                        item.setFromReg(json.getString(getString(R.string.from_reg_name_en)));
                        item.setToEm(json.getString(getString(R.string.to_em_name_en)));
                        item.setToReg(json.getString(getString(R.string.to_reg_name_en)));
                        item.setFromEmId(json.getInt("FromEmirateId"));
                        item.setFromRegid(json.getInt("FromRegionId"));
                        item.setToEmId(json.getInt("ToEmirateId"));
                        item.setToRegId(json.getInt("ToRegionId"));
                        item.setRouteName(json.getString("NoOfRoutes"));
//                    arr.add(item);
                        driver[i] = item;
                        publishProgress((int)(i*100/response.length()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }
    }







    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText("All Rides");
        //   toolbar.setElevation(10);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
