package com.mp.neetjeeiitprep.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.adapter.IntroPagerAdapter;
import com.mp.neetjeeiitprep.interfaces.CallbackToLandingListener;

public class AppIntroFragment extends Fragment {


    private View rootView;
    private ViewPager pager;
    private TabLayout tabLayoutIndicator;
    private int tabCount=2;
    private CallbackToLandingListener introToLandingListener;

    public AppIntroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_app_intro, container, false);

        initialize();
        onActionPerformed();
        return rootView;
    }

    private void initialize() {
        introToLandingListener=(CallbackToLandingListener)getActivity();

        pager=rootView.findViewById(R.id.vpIntro);
        pager.setAdapter(new IntroPagerAdapter(tabCount,getContext()));
        pager.setCurrentItem(0);

        tabLayoutIndicator=rootView.findViewById(R.id.tabLayoutIndicator);
        tabLayoutIndicator.setupWithViewPager(pager,true);
    }

    private void onActionPerformed() {
        rootView.findViewById(R.id.btnSkip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                introToLandingListener.changeFragment(Constant.FRAGMENT_LANDING);

            }
        });

        rootView.findViewById(R.id.btnIntroNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pager.getCurrentItem()<tabCount-1){
                    pager.setCurrentItem(1);
                    ((MaterialButton)v).setText("Done");
                }else{
                    introToLandingListener.changeFragment(Constant.FRAGMENT_LANDING);
                }

            }
        });
    }


}
