package com.mp.neetjeeiitprep.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mp.neetjeeiitprep.AppUtils.AppUtils;
import com.mp.neetjeeiitprep.AppUtils.Pref;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.activity.LandingActivity;
import com.mp.neetjeeiitprep.activity.MainActivity;
import com.mp.neetjeeiitprep.dataModel.LoginRegisterResponseDataModel;
import com.mp.neetjeeiitprep.network.ApiClient;
import com.mp.neetjeeiitprep.network.WebApi;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {


    private View rootView;
    private EditText edtRegNa, edtRegAdd, edtRegPhNo, edtRegEmail, edtRegPassword, edtRegConPassword;
    private MaterialButton btnReg;
    private TextView tvRegBack2Landing;
    private WebApi webApi;
    private Pref pref;
    private ProgressBar pb;
    private String loginType;
    private String TAG = "RegisterFragmentTAG";

    public RegisterFragment() {
        // Required empty public constructor
    }

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://neetjeeiitprep-1565071896340.firebaseio.com");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_register, container, false);
        webApi = ApiClient.getApiInstance().create(WebApi.class);
        pref = new Pref(getContext());

        SpannableString ss = new SpannableString(getString(R.string.sign_in_txt));
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.buttonBgRed)), 16, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 16, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        edtRegNa = rootView.findViewById(R.id.edtRegNa);
        edtRegAdd = rootView.findViewById(R.id.edtRegAdd);
        edtRegPhNo = rootView.findViewById(R.id.edtRegPhNo);
        edtRegEmail = rootView.findViewById(R.id.edtRegEmail);
        edtRegPassword = rootView.findViewById(R.id.edtRegPassword);
        edtRegConPassword = rootView.findViewById(R.id.edtRegConPassword);

        tvRegBack2Landing = rootView.findViewById(R.id.tvRegBack2Landing);
        tvRegBack2Landing.setText(ss);

        loginType = Constant.LOGIN_TYPE_EMAIL;
        // TO PASS DATA FROM THIS FRAGMENT TO CHAT FRAGMENT
//        Bundle bundle = new Bundle();
//        bundle.putString("name", String.valueOf(edtRegNa));
//        bundle.putString("email", String.valueOf(edtRegEmail));
//        bundle.putString("mobile",String.valueOf(edtRegPhNo));
//
//        StudentForumChatsFragment fragment = new StudentForumChatsFragment();
//        fragment.setArguments(bundle);
//        getFragmentManager()
//                .beginTransaction()
//                .replace(R.id.content,fragment)
//                .commit();
        // TO PASS DATA FROM THIS FRAGMENT TO CHAT FRAGMENT
        if (((LandingActivity) getActivity()).regType == 1) {
            loginType = loadSocialData();
        }

        pb = rootView.findViewById(R.id.pbRegister);
        btnReg = rootView.findViewById(R.id.btnReg);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValidate()) {
                    if (AppUtils.isOnline(getContext())) {
                        startUserRegistration(edtRegNa.getText().toString(), edtRegEmail.getText().toString().trim(), edtRegAdd.getText().toString(), edtRegPassword.getText().toString(), edtRegPhNo.getText().toString());
                       // databaseReference.child("users").child(String.valueOf(edtRegPhNo)).child("mobile").setValue(edtRegEmail);

                    } else {
                        Toast.makeText(getContext(), R.string.chk_internet, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        tvRegBack2Landing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        return rootView;
    }

    private String loadSocialData() {
        HashMap<String, String> socialData = ((LandingActivity) getActivity()).socialData;
        edtRegNa.setText(socialData.get("DisplayName"));
        edtRegEmail.setText(socialData.get("Email"));
        return socialData.get("loginType");
    }

    private boolean isValidate() {
        if (edtRegNa.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Name Cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtRegAdd.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Address Cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtRegPhNo.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Phone Cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtRegEmail.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Email Cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isValidEmail(edtRegEmail.getText().toString().trim())) {
            Toast.makeText(getContext(), "Email is not valid", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtRegPassword.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Password Cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtRegPassword.getText().toString().length() < 6) {
            Toast.makeText(getContext(), "minimum 6 character for password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtRegConPassword.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Confirm Password Cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!edtRegConPassword.getText().toString().contentEquals(edtRegPassword.getText().toString())) {
            Toast.makeText(getContext(), "Password Did not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    private void startUserRegistration(String na, String email, String add, String pass, String ph) {

        AppUtils.startLoadingAnimation(btnReg, pb);
        HashMap<String, String> params = new HashMap<>();
        params.put("name", na);
        params.put("email", email);
        params.put("address", add);
        params.put("password", pass);
        params.put("phone", ph);
        params.put("device_type", "android");
        params.put("login_using", loginType);
        params.put("firebase_token", "tokenXci123");
        params.put("device_unique_id", "someUniqueId");
        Call<LoginRegisterResponseDataModel> callApi = webApi.registerUser(params);
        callApi.enqueue(new Callback<LoginRegisterResponseDataModel>() {
            @Override
            public void onResponse(Call<LoginRegisterResponseDataModel> call, Response<LoginRegisterResponseDataModel> response) {
                Log.d(TAG, "onResponse: " + response.body().toString());
                saveRegisterData(response);
            }

            @Override
            public void onFailure(Call<LoginRegisterResponseDataModel> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                AppUtils.stopLoadingAnimation(btnReg, pb);
            }
        });
    }

    private void saveRegisterData(Response<LoginRegisterResponseDataModel> response) {

        LoginRegisterResponseDataModel resp = response.body();
        if (resp.getCode().contentEquals(Constant.CODE_SUCCESS)) {

            pref.putString(Constant.USER_ID, resp.getData().getUserId());
            pref.putString(Constant.USER_NAME, resp.getData().getName());
            pref.putString(Constant.USER_EMAIL, resp.getData().getEmail());
            pref.putString(Constant.USER_ADDRESS, resp.getData().getAddress());
            pref.putString(Constant.USER_PH, resp.getData().getPhone().toString());
            pref.putString(Constant.LOGIN_USING, Constant.LOGIN_TYPE_EMAIL);

            Intent intent = new Intent(getContext(), MainActivity.class);
            // intent.putExtra(Constant.LOGIN_USING,Constant.LOGIN_TYPE_EMAIL);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        } else {
            Toast.makeText(getContext(), resp.getMessage(), Toast.LENGTH_SHORT).show();
            AppUtils.stopLoadingAnimation(btnReg, pb);
        }

    }


}
