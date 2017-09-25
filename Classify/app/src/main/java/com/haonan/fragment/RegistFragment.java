package com.haonan.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import haonan.classify.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistFragment extends Fragment {


    public RegistFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.id.fragment_regist, container, false);
        return root;
    }

}
