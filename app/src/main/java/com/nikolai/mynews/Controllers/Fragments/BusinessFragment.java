package com.nikolai.mynews.Controllers.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikolai.mynews.R;

/**
 * Created by Philippe on 17/11/2017.
 */

public class BusinessFragment extends Fragment {

    public static BusinessFragment newInstance() {
        return (new BusinessFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page_param, container, false);
    }
}
