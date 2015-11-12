package rta.ae.sharekni;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import rta.ae.sharekni.Arafa.Classes.GetData;


public class ForgetPassword extends AppCompatActivity {

    Activity c;
    String mobileNumber,Email;
    EditText edit_number;
    EditText edit_mail;
    Button btn_submit;
    TextView txt_submit;
    String url=GetData.DOMAIN + "/ForgetPassword?";
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        edit_number = (EditText) findViewById(R.id.edit_Forgetpass_mobile);
        edit_mail = (EditText) findViewById(R.id.edit_Forgetpass_email);
        btn_submit = (Button) findViewById(R.id.btn_Forgetpass_submit);
        txt_submit = (TextView) findViewById(R.id.txt_submit);
        initToolbar();
        c=this;


//
//        btn_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mobileNumber =edit_number.getText().toString();
//                Email =edit_mail.getText().toString();
//
//                if (edit_mail == null||mobileNumber.length()<= 9) {
//                    Toast.makeText(getBaseContext(), R.string.check_user_pass, Toast.LENGTH_SHORT).show();
//                }else {
//
//                    new back().execute();
//
//                        url = url + "mobile=" + mobileNumber + "&email=" + Email;
//                        Log.d("URL Login : ", url);
//                        GetData j = new GetData();
//                        j.ForgetPasswordForm(mobileNumber, Email, getBaseContext());
//                    }
//                }
//
//        });


    }


    /*
    private class back extends AsyncTask {

        ProgressDialog pDialog;
        boolean exists = false;
        String data;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(ForgetPassword.this);
            pDialog.setMessage(getString(R.string.loading) + "...");
            pDialog.show();
        }


        @Override
        protected void onPostExecute(Object o) {

            if (data.equals("\"1\"")) {
                Toast.makeText(getBaseContext(), getString(R.string.verified), Toast.LENGTH_LONG).show();
                c.finish();
            } else if (data.equals("\"-3\"")) {
                Toast.makeText(getBaseContext(), getString(R.string.invalid_dob), Toast.LENGTH_LONG).show();
                Log.d("inside -3", data);
            } else if (data.equals("\"-4\"")) {
                Toast.makeText(getBaseContext(), getString(R.string.lic_ver_but_no_cars), Toast.LENGTH_LONG).show();

            } else if (data.equals("\"-5\"") || data.equals("\"-6\"")) {
                Toast.makeText(getBaseContext(), getString(R.string.invalid_data), Toast.LENGTH_LONG).show();

            } else if (data.equals("\"0\"")) {
                //  Toast.makeText(context, "license verified, but no cars found ", Toast.LENGTH_LONG).show();
                Log.d("license no json", data + " Error in Connection with the DataBase Server");
            } else if (data.equals("\"-2\"")) {
                Toast.makeText(getBaseContext(), getString(R.string.Register_vehicle_update), Toast.LENGTH_LONG).show();
                c.finish();
            }


            hidePDialog();
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
                        new AlertDialog.Builder(ForgetPassword.this)
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
                        Toast.makeText(ForgetPassword.this, getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {

                GetData j = new GetData();

                try {

                    data = j.RegisterVehicle(Driver_ID, FileNo, full_date);

                } catch (JSONException e) {
                    hidePDialog();
                    e.printStackTrace();

                }
            }
            return null;
        }


        private void hidePDialog() {
            if (pDialog != null) {
                pDialog.dismiss();
                pDialog = null;
            }
        }

    }

    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forget_password, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText(R.string.forget_password);
//        toolbar.setElevation(10);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



}
