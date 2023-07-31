package com.mp.neetjeeiitprep.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mp.neetjeeiitprep.AppUtils.AppUtils;
import com.mp.neetjeeiitprep.AppUtils.Pref;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.activity.LandingActivity;
import com.mp.neetjeeiitprep.activity.MainActivity;
import com.mp.neetjeeiitprep.dataModel.LoginRegisterResponseDataModel;
import com.mp.neetjeeiitprep.network.ApiClient;
import com.mp.neetjeeiitprep.network.WebApi;

import androidx.fragment.app.Fragment;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mp.neetjeeiitprep.Constant.CODE_SUCCESS;

public class LoginFragment extends Fragment {


    private View rootView;
    private TextInputLayout tipLogPass;
    private EditText edtLogEmail, edtLogPassword;
    private TextView tvForgotPassword, tvLogBackTOLanding;
    private MaterialButton btnLogin;
    private ProgressBar pb;
    private float loaderViewWidth;
    private Pref pref;
    private WebApi webApi;
    private String TAG = "LoginFragmentTAG";
    //  private CallbackToLandingListener loginToLandingListener;

    public LoginFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);


        initialize();

        onActionPerformed();

        tvLogBackTOLanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().getSupportFragmentManager().popBackStack();
                ((LandingActivity) getActivity()).loadFragment(new RegisterFragment());
            }
        });

        return rootView;
    }

    private void initialize() {
        pref = new Pref(getContext());
        pb = rootView.findViewById(R.id.pbLogin);
        webApi = ApiClient.getApiInstance().create(WebApi.class);
        tipLogPass = rootView.findViewById(R.id.tipLogPass);
        edtLogEmail = rootView.findViewById(R.id.edtLogEmail);
        edtLogPassword = rootView.findViewById(R.id.edtLogPassword);
        tvForgotPassword = rootView.findViewById(R.id.tvForgotPassword);
        tvLogBackTOLanding = rootView.findViewById(R.id.tvLogBackTOLanding);
        btnLogin = rootView.findViewById(R.id.btnLogin);

        SpannableString ss = new SpannableString(getString(R.string.sign_up_txt));
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.buttonBgRed)), 23, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 23, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvLogBackTOLanding.setText(ss);
    }

    private void onActionPerformed() {
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvForgotPassword.getTag().toString().contentEquals("true")) {
                    tvForgotPassword.setText("I remembered my password");
                    tipLogPass.setVisibility(View.GONE);
                    btnLogin.setText("Reset Password");
                    tvForgotPassword.setTag("false");
                } else {
                    tvForgotPassword.setText("Forgot Password ?");
                    tipLogPass.setVisibility(View.VISIBLE);
                    btnLogin.setText("Login");
                    tvForgotPassword.setTag("true");
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tipLogPass.getVisibility() == View.VISIBLE) {
                    if (isValidate()) {
                        if (AppUtils.isOnline(getContext())) {
                            startUserLogin(edtLogEmail.getText().toString().trim(), edtLogPassword.getText().toString());
                        } else {
                            Toast.makeText(getContext(), "Check internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (AppUtils.isOnline(getContext())) {
                        if (isValidEmail(edtLogEmail.getText().toString().trim())) {
                            requestForPasswordChange(edtLogEmail.getText().toString().trim());
                        }else{
                            Toast.makeText(getContext(), "Email is not valid", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
    }

    private boolean isValidate() {
        /*if (edtLogEmail.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Enter Email", Toast.LENGTH_SHORT).show();
            return false;
        } else */
        if (!isValidEmail(edtLogEmail.getText().toString().trim())) {
            Toast.makeText(getContext(), "Email is not valid", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtLogPassword.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "enter password", Toast.LENGTH_SHORT).show();
            return false;
        } /*else if (edtLogPassword.getText().toString().length() < 6) {
            Toast.makeText(getContext(), "minimum 6 character for password", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return true;
    }

    private static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void startUserLogin(String email, String pass) {
        AppUtils.startLoadingAnimation(btnLogin, pb);
        HashMap<String, String> params = new HashMap<>();

        params.put("user-login", email);
        params.put("pass-login", pass);

        Call<LoginRegisterResponseDataModel> callApi = webApi.loginUser(params);
        callApi.enqueue(new Callback<LoginRegisterResponseDataModel>() {
            @Override
            public void onResponse(Call<LoginRegisterResponseDataModel> call, Response<LoginRegisterResponseDataModel> response) {
                Log.d(TAG, "onResponse: " + response.body().toString());
                parseLoginData(response);
            }

            @Override
            public void onFailure(Call<LoginRegisterResponseDataModel> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                AppUtils.stopLoadingAnimation(btnLogin, pb);
            }
        });
    }

    private void parseLoginData(Response<LoginRegisterResponseDataModel> response) {

        LoginRegisterResponseDataModel resp = response.body();
        if (resp.getCode().contentEquals(CODE_SUCCESS)) {

            pref.putString(Constant.USER_ID, resp.getData().getUserId());
            pref.putString(Constant.USER_NAME, resp.getData().getName());
            pref.putString(Constant.USER_EMAIL, resp.getData().getEmail());
            pref.putString(Constant.USER_ADDRESS, resp.getData().getAddress());
            pref.putString(Constant.USER_PH, resp.getData().getPhone().toString());
            pref.putString(Constant.LOGIN_USING, Constant.LOGIN_TYPE_EMAIL);

            Intent intent = new Intent(getContext(), MainActivity.class);
            //    intent.putExtra(Constant.LOGIN_USING,Constant.LOGIN_TYPE_EMAIL);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            AppUtils.stopLoadingAnimation(btnLogin, pb);
            Toast.makeText(getContext(), resp.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void requestForPasswordChange(String userEmail) {
        AppUtils.startLoadingAnimation(btnLogin, pb);
        HashMap<String, String> params = new HashMap<>();
        params.put("forgt_email", userEmail);
        Log.d(TAG, "requestForPasswordChange: " + params.toString());
        Call<JsonElement> callApi = webApi.requestForgotPassword(params);
        callApi.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //         Log.d(TAG, "onResponse: "+ new Gson().toJson(response));
                parseRequestData(response);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                AppUtils.stopLoadingAnimation(btnLogin, pb);
            }
        });
    }

    private void parseRequestData(Response<JsonElement> response) {
        JsonObject obj = response.body().getAsJsonObject();
        if (obj.get("code").getAsString().contentEquals(CODE_SUCCESS)) {
            edtLogEmail.setText("");
            Toast.makeText(getContext(), obj.get("message").getAsString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Email does not exist", Toast.LENGTH_SHORT).show();
        }
        AppUtils.stopLoadingAnimation(btnLogin, pb);
    }

}
