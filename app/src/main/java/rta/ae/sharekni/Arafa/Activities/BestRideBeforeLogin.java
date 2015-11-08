package rta.ae.sharekni.Arafa.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import rta.ae.sharekni.Arafa.Classes.GetData;
import rta.ae.sharekni.Arafa.DataModel.BestRouteDataModel;
import rta.ae.sharekni.Arafa.DataModelAdapter.BestRouteDataModelAdapter;
import rta.ae.sharekni.MostRidesDetails;
import rta.ae.sharekni.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class BestRideBeforeLogin extends AppCompatActivity {


    ListView lv;
    ProgressDialog pDialog;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rides);
        lv = (ListView) findViewById(R.id.lv_top_rides);
        initToolbar();

//        GetData j = new GetData();
//        j.GetBestRoutes(lv, this);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.loading) + "...");
//        pDialog.setCancelable(false);
//        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

        new jsoning(lv,pDialog, this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    public class jsoning extends AsyncTask {

        ListView lv;
        Activity con;
        private ProgressDialog pDialog;
        BestRouteDataModel[] driver;
        boolean exists = false;

        public jsoning(final ListView lv, ProgressDialog pDialog, final Activity con) {

            this.lv = lv;
            this.con = con;
            this.pDialog = pDialog;
        }

        @Override
        protected void onPostExecute(Object o) {
            if (exists){
                BestRouteDataModelAdapter arrayAdapter = new BestRouteDataModelAdapter(con, R.layout.top_rides_custom_row, driver);
                lv.setAdapter(arrayAdapter);
                lv.requestLayout();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent in = new Intent(con, MostRidesDetails.class);
                        in.putExtra("ID", i);
                        Bundle b = new Bundle();
                        b.putParcelable("Data", driver[i]);
                        in.putExtras(b);
                        con.startActivity(in);
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
                        new AlertDialog.Builder(BestRideBeforeLogin.this)
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
                        Toast.makeText(BestRideBeforeLogin.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                JSONArray response = null;
                try {
                    response = new GetData().MostDesiredRoutes();
                    assert response != null;
                    driver = new BestRouteDataModel[response.length()];
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
                            item.setRouteName(json.getString("RoutesCount"));
//                    arr.add(item);
                            driver[i] = item;
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
        textView.setText(getString(R.string.most_rides));
        //   toolbar.setElevation(10);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if (id==android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}