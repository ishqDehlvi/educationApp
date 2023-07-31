package com.mp.neetjeeiitprep.fragment;


import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.dataModel.ContactUsDataModel;
import com.mp.neetjeeiitprep.network.ApiClient;
import com.mp.neetjeeiitprep.network.WebApi;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrivacyAndTermsFragment extends Fragment {

    private View rootView;
    private ProgressDialog pd;

    public PrivacyAndTermsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_privacy_and_terms, container, false);

        //rootView = inflater.inflate(R.layout.fragment_contact_us, container, false);
        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading..");

        getContactDetails();
        return rootView;
    }

    private void getContactDetails() {
        pd.show();
        WebApi webApi = ApiClient.getApiInstance().create(WebApi.class);
        Call<ContactUsDataModel> callApi = webApi.getAppsDetails();
        callApi.enqueue(new Callback<ContactUsDataModel>() {
            @Override
            public void onResponse(Call<ContactUsDataModel> call, Response<ContactUsDataModel> response) {
                pd.dismiss();
                if (response.body().getCode().contentEquals(Constant.CODE_SUCCESS)) {
                    TextView tvPvAndTerms = rootView.findViewById(R.id.tvPvAndTerms);
                    tvPvAndTerms.setText(HtmlCompat.fromHtml(response.body().getGlobal().getPrivacy(),HtmlCompat.FROM_HTML_MODE_COMPACT));
                    Log.d("yo", "getContactDetails onResponse: "+response.body().getGlobal().getPrivacy());
                }
            }

            @Override
            public void onFailure(Call<ContactUsDataModel> call, Throwable t) {
                pd.dismiss();
            }
        });
    }
}
