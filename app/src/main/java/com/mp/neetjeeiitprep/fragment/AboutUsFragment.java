package com.mp.neetjeeiitprep.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.dataModel.ContactUsDataModel;
import com.mp.neetjeeiitprep.network.ApiClient;
import com.mp.neetjeeiitprep.network.WebApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {

    private View rootView;
    private ProgressDialog pd;

    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_about_us, container, false);

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
                if(response.body().getCode().contentEquals(Constant.CODE_SUCCESS)){
                    TextView tvAboutUs =rootView.findViewById(R.id.tvAboutUs);
                    tvAboutUs.setText(response.body().getGlobal().getAboutUs());
                }
            }

            @Override
            public void onFailure(Call<ContactUsDataModel> call, Throwable t) {
                pd.dismiss();
            }
        });
    }

}
