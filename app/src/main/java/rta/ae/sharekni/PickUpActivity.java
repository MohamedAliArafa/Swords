package rta.ae.sharekni;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import rta.ae.sharekni.Arafa.Classes.GetData;

public class PickUpActivity extends AppCompatActivity {

    int From_Em_Id = -1;
    int From_Reg_Id = -1;
    int To_Em_Id;
    int To_Reg_Id;
    int FLAG_ID;

    Double Start_Latitude,Start_Longitude,End_Latitude,End_Longitude;

    TextView Emirates_txt, Emirates_txt_2;

    Button dis_submit;

    JSONArray Regions = null;
    JSONArray Emirates = null;

    String To_EmirateEnName, From_EmirateEnName, To_RegionEnName, From_RegionEnName;

    List<TreeMap<String, String>> Create_CarPool_Emirates_List = new ArrayList<>();


    private ArrayList<RegionsDataModel> arr = new ArrayList<>();
    private ArrayList<RegionsDataModel> arr_2 = new ArrayList<>();


    Toolbar toolbar;
    SimpleAdapter Create_CarPool_EmAdapter;
    Context mContext;
    Dialog Emirates_Dialog;
    ListView Emirates_lv;
    ImageView selectarrow_im,selectarrow_im_2;
    RelativeLayout Emirates_Reltive_1,Emirates_Reltive_2;

    static PickUpActivity pickUpActivity;
    backTread back1;
    backTread2 back2;

    AutoCompleteTextView Create_CarPool_txt_regions, Create_CarPool_txt_regions_2;
    ImageView sweep_icon, sweep_icon_2;

    public static PickUpActivity getInstance() {
        return pickUpActivity;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up);
        pickUpActivity = this;

        Intent intent = getIntent();
        FLAG_ID = intent.getIntExtra("FALG_SEARCH", 0);
        Log.d("Flag id", String.valueOf(FLAG_ID));

        back1 = new backTread();
        back2 = new backTread2();

        initToolbar();
        mContext = this;
        selectarrow_im= (ImageView) findViewById(R.id.selectarrow_im);
        selectarrow_im_2= (ImageView) findViewById(R.id.selectarrow_im_2);
        Emirates_Reltive_1= (RelativeLayout) findViewById(R.id.Emirates_Reltive_1);
        Emirates_Reltive_2= (RelativeLayout) findViewById(R.id.Emirates_Reltive_2);

        dis_submit = (Button) findViewById(R.id.dis_submit);
        Emirates_txt = (TextView) findViewById(R.id.Emirates_spinner);
        Create_CarPool_txt_regions = (AutoCompleteTextView) findViewById(R.id.mainDialog_Regions_auto);

        Emirates_txt_2 = (TextView) findViewById(R.id.Emirates_spinner_2);
        Create_CarPool_txt_regions_2 = (AutoCompleteTextView) findViewById(R.id.mainDialog_Regions_auto_2);
        sweep_icon = (ImageView) findViewById(R.id.sweep_icon);
        sweep_icon_2 = (ImageView) findViewById(R.id.sweep_icon_2);

        dis_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (FLAG_ID == 1) {

                    Intent in = new Intent(PickUpActivity.this, QSearch.class);
                    if (From_Em_Id != -1 && From_Reg_Id != -1) {
                        in.putExtra("From_Em_Id", From_Em_Id);
                        in.putExtra("From_EmirateEnName", From_EmirateEnName);
                        in.putExtra("From_RegionEnName", From_RegionEnName);
                        in.putExtra("From_Reg_Id", From_Reg_Id);
                        in.putExtra("To_Em_Id", To_Em_Id);
                        in.putExtra("To_EmirateEnName", To_EmirateEnName);
                        in.putExtra("To_RegionEnName", To_RegionEnName);
                        in.putExtra("To_Reg_Id", To_Reg_Id);

                        Log.d("From_Em_Id_1", String.valueOf(From_Em_Id));
                        Log.d("From_Reg_Id_1", String.valueOf(From_Reg_Id));
                        Log.d("To_Em_Id_1", String.valueOf(To_Em_Id));
                        Log.d("To_Reg_Id_1", String.valueOf(To_Reg_Id));
                        startActivity(in);
                        back1.cancel(true);
                        back2.cancel(true);
                        finish();
                    }else {
                        Toast.makeText(PickUpActivity.this, "Please enter Emitrate and Region", Toast.LENGTH_SHORT).show();
                    }
                }  // flag id
                else if (FLAG_ID == 2) {

                    Intent in = new Intent(PickUpActivity.this, Advanced_Search.class);
                    if (From_Em_Id != -1 && From_Reg_Id != -1) {
                        in.putExtra("From_Em_Id", From_Em_Id);
                        in.putExtra("From_EmirateEnName", From_EmirateEnName);
                        in.putExtra("From_RegionEnName", From_RegionEnName);
                        in.putExtra("From_Reg_Id", From_Reg_Id);
                        in.putExtra("To_Em_Id", To_Em_Id);
                        in.putExtra("To_EmirateEnName", To_EmirateEnName);
                        in.putExtra("To_RegionEnName", To_RegionEnName);
                        in.putExtra("To_Reg_Id", To_Reg_Id);

                        Log.d("From_Em_Id_1", String.valueOf(From_Em_Id));
                        Log.d("From_Reg_Id_1", String.valueOf(From_Reg_Id));
                        Log.d("To_Em_Id_1", String.valueOf(To_Em_Id));
                        Log.d("To_Reg_Id_1", String.valueOf(To_Reg_Id));
                        startActivity(in);
                        back1.cancel(true);
                        back2.cancel(true);
                        finish();
                    }else {
                        Toast.makeText(PickUpActivity.this, "Please enter Emitrate and Region", Toast.LENGTH_SHORT).show();
                    }
                } //  else if 2

                else if (FLAG_ID == 3) {

                    Intent in = new Intent(PickUpActivity.this, DriverCreateCarPool.class);
                    if (From_Em_Id != -1 && From_Reg_Id != -1) {
                        in.putExtra("From_Em_Id", From_Em_Id);
                        in.putExtra("From_EmirateEnName", From_EmirateEnName);
                        in.putExtra("From_RegionEnName", From_RegionEnName);
                        in.putExtra("From_Reg_Id", From_Reg_Id);
                        in.putExtra("To_Em_Id", To_Em_Id);
                        in.putExtra("To_EmirateEnName", To_EmirateEnName);
                        in.putExtra("To_RegionEnName", To_RegionEnName);
                        in.putExtra("To_Reg_Id", To_Reg_Id);
                        in.putExtra("Start_Latitude",Start_Latitude);
                        in.putExtra("Start_Longitude",Start_Longitude);
                        in.putExtra("End_Latitude",End_Latitude);
                        in.putExtra("End_Longitude",End_Longitude);

                        Log.d("From_Em_Id_1", String.valueOf(From_Em_Id));
                        Log.d("From_Reg_Id_1", String.valueOf(From_Reg_Id));
                        Log.d("To_Em_Id_1", String.valueOf(To_Em_Id));
                        Log.d("To_Reg_Id_1", String.valueOf(To_Reg_Id));
                        startActivity(in);
                        back1.cancel(true);
                        back2.cancel(true);

                        finish();


                    }else {
                        Toast.makeText(PickUpActivity.this, "Please enter Emitrate and Region", Toast.LENGTH_SHORT).show();
                    }
                } //  else if 2


            }// on click
        });

        Create_CarPool_Emirates_List.clear();
        try {
            String ret;
            try {
                InputStream inputStream = openFileInput("Emirates.txt");
                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((receiveString = bufferedReader.readLine()) != null) {
                        stringBuilder.append(receiveString);
                    }
                    inputStream.close();
                    ret = stringBuilder.toString();
                    Emirates = new JSONArray(ret);
                }
            } catch (FileNotFoundException e) {
                Log.e("login activity", "File not found: " + e.toString());
            } catch (IOException e) {
                Log.e("login activity", "Can not read file: " + e.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray j;
            if (Emirates == null) {
                j = new GetData().GetEmitares();
            } else {
                j = Emirates;
            }
            for (int i = 0; i < j.length(); i++) {


                TreeMap<String, String> valuePairs = new TreeMap<>();
                JSONObject jsonObject = j.getJSONObject(i);
                valuePairs.put("EmirateId", jsonObject.getString("EmirateId"));
                valuePairs.put("EmirateEnName", jsonObject.getString(getString(R.string.em_name)));
                Create_CarPool_Emirates_List.add(valuePairs);
                Log.d("test Emirates ", Create_CarPool_Emirates_List.toString());


            }
            Log.d("test Emirates ", Create_CarPool_Emirates_List.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Create_CarPool_EmAdapter = new SimpleAdapter(PickUpActivity.this, Create_CarPool_Emirates_List
                , R.layout.dialog_pick_emirate_lv_row
                , new String[]{"EmirateId", "EmirateEnName"}
                , new int[]{R.id.row_id_search, R.id.row_name_search});


        Emirates_Reltive_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Emirates_Dialog = new Dialog(mContext);
                Emirates_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Emirates_Dialog.setContentView(R.layout.languages_dialog);
                TextView Lang_Dialog_txt_id = (TextView) Emirates_Dialog.findViewById(R.id.Lang_Dialog_txt_id);
                Lang_Dialog_txt_id.setText("Emirates");
                Emirates_lv = (ListView) Emirates_Dialog.findViewById(R.id.Langs_list);
                Emirates_lv.setAdapter(Create_CarPool_EmAdapter);
                Emirates_Dialog.show();
                Emirates_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        TextView txt_em_name = (TextView) view.findViewById(R.id.row_name_search);
                        TextView txt_em_id = (TextView) view.findViewById(R.id.row_id_search);

                        From_Em_Id = Integer.parseInt(txt_em_id.getText().toString());
                        From_EmirateEnName = txt_em_name.getText().toString();
                        Emirates_txt.setText(txt_em_name.getText().toString());
                        Emirates_Dialog.dismiss();
                        Create_CarPool_txt_regions.setText("");
                        From_Reg_Id = -1;
                    }
                });


            }
        });


        Emirates_Reltive_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Emirates_Dialog = new Dialog(mContext);
                Emirates_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Emirates_Dialog.setContentView(R.layout.languages_dialog);
                TextView Lang_Dialog_txt_id = (TextView) Emirates_Dialog.findViewById(R.id.Lang_Dialog_txt_id);
                Lang_Dialog_txt_id.setText("Emirates");
                Emirates_lv = (ListView) Emirates_Dialog.findViewById(R.id.Langs_list);
                Emirates_lv.setAdapter(Create_CarPool_EmAdapter);
                Emirates_Dialog.show();
                Emirates_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        TextView txt_em_name = (TextView) view.findViewById(R.id.row_name_search);
                        TextView txt_em_id = (TextView) view.findViewById(R.id.row_id_search);

                        To_Em_Id = Integer.parseInt(txt_em_id.getText().toString());
                        To_EmirateEnName = txt_em_name.getText().toString();
                        Emirates_txt_2.setText(txt_em_name.getText().toString());
                        Emirates_Dialog.dismiss();
                        Create_CarPool_txt_regions_2.setText("");
                        To_Reg_Id = -1;
                    }
                });


            }
        });


        Create_CarPool_txt_regions_2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    arr_2.clear();
                    if (!back2.getStatus().equals(AsyncTask.Status.RUNNING)){
                        back2 = new backTread2();
                        back2.execute();
                    }
                }

            }
        });



        Create_CarPool_txt_regions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arr.clear();
                if (!back1.getStatus().equals(AsyncTask.Status.RUNNING)){
                    back1 = new backTread();
                    back1.execute();
                }
            }
        });



//        Create_CarPool_txt_regions.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    arr.clear();
//                    new backTread().execute();
//                }
//            }
//        });





    } // oncreate


    private class backTread extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {

            RegionsAdapter regionsAdapter = new RegionsAdapter(getBaseContext(), R.layout.dialog_pick_regions_lv_row, arr);


            Create_CarPool_txt_regions.setAdapter(regionsAdapter);
            Create_CarPool_txt_regions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Create_CarPool_txt_regions.setText(arr.get(position).getRegionEnName());
                    From_Reg_Id = arr.get(position).getID();
                    From_RegionEnName = arr.get(position).getRegionEnName();

                    Start_Latitude=arr.get(position).getRegionLatitude();
                    Start_Longitude= arr.get(position).getRegionLongitude();


                    Log.d("Start lat", String.valueOf(Start_Latitude));
                    Log.d("Start lat", String.valueOf(Start_Longitude));
                    Log.d("Em Name : ", From_EmirateEnName);
                    Log.d("Reg Name", From_RegionEnName);
                    Log.d("Reg id ", String.valueOf(From_Reg_Id));
                }
            });


            //  if


            sweep_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Create_CarPool_txt_regions.setText("");
                    Create_CarPool_txt_regions.setHint(getString(R.string.enter_region));

                }
            });


        }

        @Override
        protected Object doInBackground(Object[] params) {

            String ret;
            try {
                InputStream inputStream = openFileInput("Regions" + From_Em_Id + ".txt");
                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((receiveString = bufferedReader.readLine()) != null) {
                        stringBuilder.append(receiveString);
                    }
                    inputStream.close();
                    ret = stringBuilder.toString();
                    Regions = new JSONArray(ret);

                    JSONArray jsonArray = Regions;


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        final RegionsDataModel regions = new RegionsDataModel(Parcel.obtain());
                        regions.setID(jsonObject.getInt("ID"));
                        regions.setRegionEnName(jsonObject.getString(getString(R.string.reg_name)));
                        regions.setRegionLatitude(jsonObject.getDouble("RegionLatitude"));
                        regions.setRegionLongitude(jsonObject.getDouble("RegionLongitude"));
                        arr.add(regions);
                    }
                }
            } catch (FileNotFoundException e) {
                Log.e("login activity", "File not found: " + e.toString());
            } catch (IOException e) {
                Log.e("login activity", "Can not read file: " + e.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


    }    // back thread classs


    private class backTread2 extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {

            RegionsAdapter regionsAdapter = new RegionsAdapter(getBaseContext(), R.layout.dialog_pick_regions_lv_row, arr_2);


            Create_CarPool_txt_regions_2.setAdapter(regionsAdapter);
            Create_CarPool_txt_regions_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Create_CarPool_txt_regions_2.setText(arr_2.get(position).getRegionEnName());
                    To_Reg_Id = arr_2.get(position).getID();
                    To_RegionEnName = arr_2.get(position).getRegionEnName();
                    End_Latitude=arr_2.get(position).getRegionLatitude();
                    End_Longitude=arr_2.get(position).getRegionLongitude();


                    Log.d("Start lat", String.valueOf(End_Latitude));
                    Log.d("Start lat", String.valueOf(End_Longitude));
                    Log.d("Em Name : ", To_EmirateEnName);
                    Log.d("Reg Name", To_RegionEnName);
                    Log.d("Reg id ", String.valueOf(To_Reg_Id));

                }
            });


            //  if

            sweep_icon_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Create_CarPool_txt_regions_2.setText("");
                    Create_CarPool_txt_regions_2.setHint(getString(R.string.enter_region));
                }
            });


        }

        @Override
        protected Object doInBackground(Object[] params) {

            String ret;
            try {
                InputStream inputStream = openFileInput("Regions" + To_Em_Id + ".txt");
                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((receiveString = bufferedReader.readLine()) != null) {
                        stringBuilder.append(receiveString);
                    }
                    inputStream.close();
                    ret = stringBuilder.toString();
                    Regions = new JSONArray(ret);

                    JSONArray jsonArray = Regions;


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        final RegionsDataModel regions = new RegionsDataModel(Parcel.obtain());
                        regions.setID(jsonObject.getInt("ID"));
                        regions.setRegionEnName(jsonObject.getString(getString(R.string.reg_name)));
                        regions.setRegionLatitude(jsonObject.getDouble("RegionLatitude"));
                        regions.setRegionLongitude(jsonObject.getDouble("RegionLongitude"));
                        arr_2.add(regions);
                    }
                }
            } catch (FileNotFoundException e) {
                Log.e("login activity", "File not found: " + e.toString());
            } catch (IOException e) {
                Log.e("login activity", "Can not read file: " + e.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


    }    // back thread classs


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_driver_create_car_pool, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back1.cancel(true);
        back2.cancel(true);
        finish();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        textView.setText(R.string.set_direction);
//        toolbar.setElevation(10);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }




}  //  Class
