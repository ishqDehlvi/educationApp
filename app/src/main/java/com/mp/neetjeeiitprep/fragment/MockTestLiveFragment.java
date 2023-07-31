package com.mp.neetjeeiitprep.fragment;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;


public class MockTestLiveFragment extends Fragment {



    public MockTestLiveFragment() {
        // Required empty public constructor
    }

    private View rootView;
    private Boolean dataFetch;
    private Bundle navigationData;
    private ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_mock_test_live, container, false);
//        getActivity().setTitle("Mock Test Live");

        dataFetch = true;
        navigationData = new Bundle();
        pd = new ProgressDialog(getContext());
        pd.setMessage("loading...");

        rootView.findViewById(R.id.clMockTest1).setOnClickListener(v ->{
            if (dataFetch){
                Navigation.findNavController(v).navigate(R.id.action_mockTestLiveFragment_to_examroutine_mockTestLiveFragment, navigationData);
            } else {
                Toast.makeText(getContext(), "Data not loaded", Toast.LENGTH_SHORT).show();
            }
        });

        rootView.findViewById(R.id.clMockTest2).setOnClickListener(v ->{
            if (dataFetch){
                navigationData.putString(Constant.WHICH_STANDARD, Constant.FROM_CLASS_11);
                Navigation.findNavController(v).navigate(R.id.action_mockTestLiveFragment_to_class11_12mockTestLiveFragment, navigationData);
            } else {
                Toast.makeText(getContext(), "Data not loaded", Toast.LENGTH_SHORT).show();
            }
        });

        rootView.findViewById(R.id.clMockTest3).setOnClickListener(v ->{
            if (dataFetch){
                navigationData.putString(Constant.WHICH_STANDARD, Constant.FROM_CLASS_12);
                Navigation.findNavController(v).navigate(R.id.action_mockTestLiveFragment_to_class11_12mockTestLiveFragment, navigationData);
            } else {
                Toast.makeText(getContext(), "Data not loaded", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}