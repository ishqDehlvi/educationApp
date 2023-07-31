package com.mp.neetjeeiitprep.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mp.neetjeeiitprep.R;


public class Top10StudentCellNavFragment extends Fragment {



    public Top10StudentCellNavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top10_student_cell_nav, container, false);
    }
}