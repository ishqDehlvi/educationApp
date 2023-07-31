package com.mp.neetjeeiitprep.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.mp.neetjeeiitprep.dataModel.LoginRegisterResponseDataModel;
import com.mp.neetjeeiitprep.network.ApiClient;
import com.mp.neetjeeiitprep.network.WebApi;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private View rootView;
    private TextInputLayout tipLogPass;
    private EditText edtEpNa, edtEpAdd, edtEpPhNo, edtEpEmail;
    private TextView tvName, tvEmail;
    private ImageView imgEnableEdit;
    private MaterialButton btnUpdateProfile;
    private Pref pref;
    private ProgressBar pb;
    private WebApi webApi;
    private String TAG = "ProfileFragmentTAG";

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        webApi = ApiClient.getApiInstance().create(WebApi.class);
        pref = new Pref(getContext());
        pb = rootView.findViewById(R.id.pbProfile);

        tvName = rootView.findViewById(R.id.tvName);
        tvEmail = rootView.findViewById(R.id.tvEmail);
        edtEpNa = rootView.findViewById(R.id.edtEpNa);
        edtEpAdd = rootView.findViewById(R.id.edtEpAdd);
        edtEpPhNo = rootView.findViewById(R.id.edtEpPhNo);
        edtEpEmail = rootView.findViewById(R.id.edtEpEmail);
        imgEnableEdit = rootView.findViewById(R.id.imgEnableEdit);
        btnUpdateProfile = rootView.findViewById(R.id.btnUpdateProfile);
        Button btnPasswordChangeDialog = rootView.findViewById(R.id.btnPasswordChangeDialog);
        loadSavedUserData();
        EnableEdittext(false);

        imgEnableEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnUpdateProfile.getVisibility() != View.VISIBLE) {
                    EnableEdittext(true);
                    btnUpdateProfile.setVisibility(View.VISIBLE);
                } else {
                    EnableEdittext(false);
                    btnUpdateProfile.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate()) {
                    if (AppUtils.isOnline(getContext())) {
                        updateProfile(edtEpNa.getText().toString(),
                                edtEpAdd.getText().toString(),
                                edtEpPhNo.getText().toString(),
                                edtEpEmail.getText().toString());
                    }
                } else {
                    Toast.makeText(getContext(), R.string.chk_internet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnPasswordChangeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPasswordChangeDialog();
            }
        });


        return rootView;
    }

    private void loadSavedUserData() {

        tvName.setText(pref.getString(Constant.USER_NAME));
        tvEmail.setText(pref.getString(Constant.USER_EMAIL));
        edtEpNa.setText(pref.getString(Constant.USER_NAME));
        edtEpAdd.setText(pref.getString(Constant.USER_ADDRESS));
        edtEpPhNo.setText(pref.getString(Constant.USER_PH));
        edtEpEmail.setText(pref.getString(Constant.USER_EMAIL));

    }

    private boolean isValidate() {
        if (edtEpNa.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Name Cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtEpAdd.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Address Cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtEpPhNo.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Phone Cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtEpEmail.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Email Cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void updateProfile(String name, String address, String phone, String email) {
        AppUtils.startLoadingAnimation(btnUpdateProfile, pb);
        HashMap<String, String> params = new HashMap<>();

        params.put("user_id", pref.getString(Constant.USER_ID));
        params.put("name", name);
        params.put("phone", phone);
        params.put("email", email);
        params.put("address", address);

        Call<LoginRegisterResponseDataModel> callApi = webApi.editProfile(params);
        callApi.enqueue(new Callback<LoginRegisterResponseDataModel>() {
            @Override
            public void onResponse(Call<LoginRegisterResponseDataModel> call, Response<LoginRegisterResponseDataModel> response) {
                Log.d(TAG, "onResponse: " + response.body().toString());
                parseUpdateProfileData(response);
                AppUtils.stopLoadingAnimation(btnUpdateProfile, pb);
                btnUpdateProfile.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<LoginRegisterResponseDataModel> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                AppUtils.stopLoadingAnimation(btnUpdateProfile, pb);
            }
        });
    }

    private void parseUpdateProfileData(Response<LoginRegisterResponseDataModel> response) {

        LoginRegisterResponseDataModel resp = response.body();
        if (resp.getCode().contentEquals(Constant.CODE_SUCCESS)) {

            tvName.setText(resp.getData().getName());
            tvEmail.setText(resp.getData().getEmail());

            pref.putString(Constant.USER_ID, resp.getData().getUserId());
            pref.putString(Constant.USER_NAME, resp.getData().getName());
            pref.putString(Constant.USER_EMAIL, resp.getData().getEmail());
            pref.putString(Constant.USER_ADDRESS, resp.getData().getAddress());
            pref.putString(Constant.USER_PH, resp.getData().getPhone().toString());
            Toast.makeText(getContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
            EnableEdittext(false);
        } else {
            Toast.makeText(getContext(), resp.getMessage(), Toast.LENGTH_SHORT).show();
            loadSavedUserData();
        }

    }

    private void openPasswordChangeDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.change_password_layout);


        final EditText edtCpNewPass = dialog.findViewById(R.id.edtCpNewPass);
        final EditText edtCpOldPass = dialog.findViewById(R.id.edtCpOldPass);
        final MaterialButton btnSubmitPasswordChange = dialog.findViewById(R.id.btnSubmitPasswordChange);
        final ProgressBar pbChangePass = dialog.findViewById(R.id.pbChangePass);
        btnSubmitPasswordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newPass = edtCpNewPass.getText().toString();
                String oldPass = edtCpOldPass.getText().toString();
                if (newPass.isEmpty() || oldPass.isEmpty()) {
                    Toast.makeText(getContext(), "Enter old and new password", Toast.LENGTH_SHORT).show();
                } else {
                    if (AppUtils.isOnline(getContext())) {
                        requestForPasswordChange(newPass, oldPass, dialog, btnSubmitPasswordChange, pbChangePass);
                    }
                }
                //  dialog.dismiss();
                // Toast.makeText(LogAndRegActivity.this, fgEmail, Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();

        /***   getting display height and width   ***/
        //Grab the window of the dialog, and change the width
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        /*****/
    }

    private void requestForPasswordChange(String newPass, String oldPass, final Dialog dialog, final MaterialButton btnSubmitPasswordChange, final ProgressBar pbChangePass) {
        AppUtils.startLoadingAnimation(btnSubmitPasswordChange, pbChangePass);
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", pref.getString(Constant.USER_ID));
        params.put("current_pass", oldPass);
        params.put("new_pass", newPass);

        Log.d(TAG, "requestForPasswordChange: " + params.toString());
        Call<JsonElement> callApi = webApi.changePassword(params);
        callApi.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d(TAG, "onResponse: " + response.body().toString());
                parseRequestData(response, dialog, btnSubmitPasswordChange, pbChangePass);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                Toast.makeText(getContext(), "incorrect password", Toast.LENGTH_SHORT).show();
                AppUtils.stopLoadingAnimation(btnSubmitPasswordChange, pbChangePass);
            }
        });
    }

    private void parseRequestData(Response<JsonElement> response, Dialog dialog, final MaterialButton btnSubmitPasswordChange, final ProgressBar pbChangePass) {
        JsonObject obj = response.body().getAsJsonObject();
        if (obj.get("code").getAsString().contentEquals(Constant.CODE_SUCCESS)) {

            Toast.makeText(getContext(), obj.get("message").getAsString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), obj.get("message").getAsString(), Toast.LENGTH_SHORT).show();
            AppUtils.stopLoadingAnimation(btnSubmitPasswordChange, pbChangePass);
        }
        dialog.dismiss();
    }

    private void EnableEdittext(boolean enableState) {

        edtEpNa.setFocusable(enableState);
        edtEpNa.setFocusableInTouchMode(enableState);
        edtEpAdd.setFocusable(enableState);
        edtEpAdd.setFocusableInTouchMode(enableState);
        edtEpPhNo.setFocusable(enableState);
        edtEpPhNo.setFocusableInTouchMode(enableState);
        edtEpEmail.setFocusable(enableState);
        edtEpEmail.setFocusableInTouchMode(enableState);

    }
}
