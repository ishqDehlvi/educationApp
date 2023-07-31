package com.mp.neetjeeiitprep.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mp.neetjeeiitprep.R;


public class MockTestNavFragment extends Fragment {



//    public MockTestNav() {
//        // Required empty public constructor
//    }
private View rootView;
    private Boolean dataFetch;
    private Bundle navigationData;
    private ProgressDialog pd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_mock_test_nav, container, false);
        dataFetch = true;
        navigationData = new Bundle();
        pd = new ProgressDialog(getContext());
        pd.setMessage("loading...");

        rootView.findViewById(R.id.clNavMockTest1).setOnClickListener(v ->{
            if (dataFetch){
                Navigation.findNavController(v).navigate(R.id.action_mockTestNavFragment_to_top10StudentNavFragment);
            }
        });


        return rootView;
    }

}