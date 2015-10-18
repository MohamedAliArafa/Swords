package com.example.nezarsaleh.shareknitest;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nezarsaleh.shareknitest.Arafa.Classes.GetData;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;


public class ForgetPassword extends AppCompatActivity {

    String mobileNumber,Email;
    EditText edit_number;
    EditText edit_mail;
    Button btn_submit;
    TextView txt_submit;
    String url="http://www.sharekni-web.sdg.ae/_mobfiles/CLS_MobAccount.asmx/ForgetPassword?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        edit_number = (EditText) findViewById(R.id.edit_Forgetpass_mobile);
        edit_mail = (EditText) findViewById(R.id.edit_Forgetpass_email);
        btn_submit = (Button) findViewById(R.id.btn_Forgetpass_submit);
        txt_submit = (TextView) findViewById(R.id.txt_submit);



        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNumber =edit_number.getText().toString();
                Email =edit_mail.getText().toString();

                if (edit_mail == null||mobileNumber.length()<= 9) {
                    Toast.makeText(getBaseContext(), "Check ur Username and Password", Toast.LENGTH_SHORT).show();
                }else {
                    boolean exists = false;
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
                                new AlertDialog.Builder(ForgetPassword.this)
                                        .setTitle("Connection Problem!")
                                        .setMessage("Make sure you have internet connection")
                                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intentToBeNewRoot = new Intent(ForgetPassword.this, ForgetPassword.class);
                                                ComponentName cn = intentToBeNewRoot.getComponent();
                                                Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                                                startActivity(mainIntent);
                                            }
                                        })
                                        .setNegativeButton("Exit!", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
                                Toast.makeText(ForgetPassword.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    if (exists) {
                        url = url + "mobile=" + mobileNumber + "&email=" + Email;
                        Log.d("URL Login : ", url);
                        GetData j = new GetData();
                        j.ForgetPasswordForm(mobileNumber, Email, getBaseContext());
                    }
                }
            }
        });


    }

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
