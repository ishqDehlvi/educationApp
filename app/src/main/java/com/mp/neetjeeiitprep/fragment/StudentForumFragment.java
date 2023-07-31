package com.mp.neetjeeiitprep.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.mp.neetjeeiitprep.AppUtils.Pref;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.adapter.StudentForumMessenger;


public class StudentForumFragment extends Fragment {

 private TabLayout tab;
 private ViewPager viewPager;
 private View rootView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Student forum");
        rootView = inflater.inflate(R.layout.fragment_student_forum, container, false);
        tab = rootView.findViewById(R.id.tabLayout);
        viewPager = rootView.findViewById(R.id.viewPager);


        return rootView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StudentForumMessenger adapter = new StudentForumMessenger(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);
    }



}