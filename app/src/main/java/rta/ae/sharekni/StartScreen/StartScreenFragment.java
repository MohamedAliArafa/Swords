package rta.ae.sharekni.StartScreen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import rta.ae.sharekni.Arafa.Activities.BestDrivers;
import rta.ae.sharekni.Arafa.Activities.MostRides;
import rta.ae.sharekni.Arafa.Classes.AppAdapter;
import rta.ae.sharekni.R;


/**
 * Created by nezar on 8/11/2015.
 */


public class
StartScreenFragment extends Fragment {
    Dialog list_dialog;
    ListView mListView;
    List<TreeMap<String, String>> Lang_List = new ArrayList<>();


    RelativeLayout im_best_rides;
    RelativeLayout im_best_drivers;
    ImageView OnBoard_Changelanguae;
    String Locale_Str;
    private int Language_ID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.onboard_new_3
                ,
                container,
                false
        );
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        im_best_drivers = (RelativeLayout) view.findViewById(R.id.im_best_drivers);
        im_best_rides = (RelativeLayout) view.findViewById(R.id.im_best_rides);
        OnBoard_Changelanguae = (ImageView) view.findViewById(R.id.OnBoard_Changelanguae);


        OnBoard_Changelanguae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAlertDialog();


//                Configuration config = new Configuration();
//                Locale locale = Locale.getDefault();
//                Locale_Str = locale.toString();
//                Log.d("test locale", Locale_Str);
//
//
//               if (Locale_Str.contains("en")){
//
//                   Locale locale2 = new Locale("ar");
//                   Locale.setDefault(locale2);
//                   Configuration config2 = new Configuration();
//                   config2.locale = locale2;
//                   getActivity().getApplicationContext().getResources().updateConfiguration(config2, null);
//                   getActivity().finish();
//                   startActivity(getActivity().getIntent());
//
//
//               }else {
//
//
//                   Locale locale3 = new Locale("en");
//                   Locale.setDefault(locale3);
//                   Configuration config3 = new Configuration();
//                   config3.locale = locale3;
//                   getActivity().getApplicationContext().getResources().updateConfiguration(config3, null);
//                   getActivity().finish();
//                   startActivity(getActivity().getIntent());
//               }


            }
        });


        im_best_rides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MostRides.class);
                startActivity(intent);
            }
        });

        im_best_drivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BestDrivers.class);
                startActivity(intent);
            }
        });


    }

    private void showAlertDialog() {

        Lang_List.clear();
        TreeMap<String, String> valuePairs = new TreeMap<>();
        valuePairs.put("LanguageId", "1");
        valuePairs.put("LanguageEnName", "Arabic");
        Lang_List.add(valuePairs);
        TreeMap<String, String> valuePairs2 = new TreeMap<>();
        valuePairs2.put("LanguageId", "2");
        valuePairs2.put("LanguageEnName", "English");
        Lang_List.add(valuePairs2);
        TreeMap<String, String> valuePairs3 = new TreeMap<>();
        valuePairs3.put("LanguageId", "3");
        valuePairs3.put("LanguageEnName", "Chinese");
        Lang_List.add(valuePairs3);
        TreeMap<String, String> valuePairs4 = new TreeMap<>();
        valuePairs4.put("LanguageId", "4");
        valuePairs4.put("LanguageEnName", "filippine");
        Lang_List.add(valuePairs4);

        final SimpleAdapter adapter2 = new SimpleAdapter(getActivity(), Lang_List
                , R.layout.autocomplete_row
                , new String[]{"LanguageId", "LanguageEnName"}
                , new int[]{R.id.row_id, R.id.row_name});


        list_dialog = new Dialog(getActivity());
        list_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        list_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        list_dialog.setContentView(R.layout.language_dialog);
        mListView = (ListView) list_dialog.findViewById(R.id.lang_dialog_lv_id);
        mListView.setAdapter(adapter2);

        list_dialog.show();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt_lang_name = (TextView) view.findViewById(R.id.row_name);
                TextView txt_lang_id = (TextView) view.findViewById(R.id.row_id);
                Language_ID = Integer.parseInt(txt_lang_id.getText().toString());
                Log.d("id of lang", "" + Language_ID);
                switch (Language_ID) {
                    case 1:
                        Locale locale2 = new Locale("ar");
                        Locale.setDefault(locale2);
                        Configuration config2 = new Configuration();
                        config2.locale = locale2;
                        getActivity().getApplicationContext().getResources().updateConfiguration(config2, null);
                        getActivity().finish();
                        startActivity(getActivity().getIntent());
                        break;
                    case 2:
                        Locale locale3 = new Locale("en");
                        Locale.setDefault(locale3);
                        Configuration config3 = new Configuration();
                        config3.locale = locale3;
                        getActivity().getApplicationContext().getResources().updateConfiguration(config3, null);
                        getActivity().finish();
                        startActivity(getActivity().getIntent());
                        break;
                    case 3:
                        Locale locale4 = new Locale("zh");
                        Locale.setDefault(locale4);
                        Configuration config4 = new Configuration();
                        config4.locale = locale4;
                        getActivity().getApplicationContext().getResources().updateConfiguration(config4, null);
                        getActivity().finish();
                        startActivity(getActivity().getIntent());
                        break;
                    case 4:
                        Locale locale5 = new Locale("fil");
                        Locale.setDefault(locale5);
                        Configuration config5 = new Configuration();
                        config5.locale = locale5;
                        getActivity().getApplicationContext().getResources().updateConfiguration(config5, null);
                        getActivity().finish();
                        startActivity(getActivity().getIntent());
                        break;
                    default:
                        break;
                }
                list_dialog.dismiss();
            }
        });


    }


}