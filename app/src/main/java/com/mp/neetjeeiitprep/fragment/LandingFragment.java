package com.mp.neetjeeiitprep.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mp.neetjeeiitprep.AppUtils.Pref;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.activity.LandingActivity;
import com.mp.neetjeeiitprep.activity.MainActivity;
import com.mp.neetjeeiitprep.interfaces.CallbackToLandingListener;

import androidx.fragment.app.Fragment;

public class LandingFragment extends Fragment {

    private ProgressDialog pd;
    private View rootView;
    private TextView tvSignUp;
    private CallbackToLandingListener loginToLandingListener;

    public LandingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_landing, container, false);
        initialize();
        onActionPerformed();

        return rootView;
    }
    private void initialize() {
        loginToLandingListener=(CallbackToLandingListener) getContext();
        tvSignUp=rootView.findViewById(R.id.tvSignUp);
    }

    private void onActionPerformed() {
        rootView.findViewById(R.id.btnStudentLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginToLandingListener.changeFragment(Constant.FRAGMENT_LOGIN);
            }
        });

        rootView.findViewById(R.id.btnGuestLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra(Constant.LOGIN_USING,Constant.LOGIN_TYPE_GUEST);
                new Pref(getContext()).putString(Constant.LOGIN_USING ,Constant.LOGIN_TYPE_GUEST);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginToLandingListener.changeFragment(Constant.FRAGMENT_REGISTER);
            }
        });

        // Google Login
        rootView.findViewById(R.id.cardG).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LandingActivity)getActivity()).signIn("google");
                //loginToLandingListener.changeFragment(Constant.FRAGMENT_LOGIN);
            }
        });

        // Facebook Login
        rootView.findViewById(R.id.cardFb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LandingActivity)getActivity()).signIn("facebook");
                //loginToLandingListener.changeFragment(Constant.FRAGMENT_LOGIN);
            }
        });
    }



}
