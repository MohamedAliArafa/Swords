package com.example.nezarsaleh.shareknitest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Nezar Saleh on 10/17/2015.
 */
public class TakeATourFragment1 extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.take_tour_fragment_1
                ,
                container,
                false
        );
    }

}
