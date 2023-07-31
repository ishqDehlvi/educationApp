package com.mp.neetjeeiitprep.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
public class ContactUsFragment extends Fragment {

private View rootView;
private ProgressDialog pd;
    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.fragment_contact_us, container, false);
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
                loadData(response);
            }
            @Override
            public void onFailure(Call<ContactUsDataModel> call, Throwable t) {

            }
        });
    }

    private void loadData(final Response<ContactUsDataModel> response) {

        if (response.body().getCode().contentEquals(Constant.CODE_SUCCESS)) {

            TextView tvMainAddress = rootView.findViewById(R.id.tvMainAddress);
            TextView tvBusinessMail = rootView.findViewById(R.id.tvBusinessMail);
            TextView tvQueryMail = rootView.findViewById(R.id.tvQueryMail);
            TextView tvWaUs = rootView.findViewById(R.id.tvWaUs);
            TextView tvPhNo = rootView.findViewById(R.id.tvPhNo);

            ImageView imgFbIcon = rootView.findViewById(R.id.imgFbIcon);
            ImageView imgLinkedInIcon = rootView.findViewById(R.id.imgLinkedInIcon);
            ImageView imgTwitterIcon = rootView.findViewById(R.id.imgTwitterIcon);
            ImageView imgWhatsappIcon = rootView.findViewById(R.id.imgWhatsappIcon);

            tvMainAddress.setText(response.body().getGlobal().getCorporateAddress());
            tvBusinessMail.setText(response.body().getGlobal().getBuisEmail());
            tvQueryMail.setText(response.body().getGlobal().getCanEmail());
            tvWaUs.setText(response.body().getGlobal().getWatsPhone());
            tvPhNo.setText(response.body().getGlobal().getTollPhone());

            imgFbIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openUrl(response.body().getGlobal().getFbLink());
                }
            });

            imgLinkedInIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openUrl(response.body().getGlobal().getLinkedLink());
                }
            });

            imgTwitterIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openUrl(response.body().getGlobal().getTwtLink());
                }
            });

            imgWhatsappIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openUrl(response.body().getGlobal().getGoogleLink());
                }
            });
        }
        pd.dismiss();
    }

    private void openUrl(String url) {
        final String HTTPS = "https://";
        final String HTTP = "http://";

        if (!url.startsWith(HTTP) && !url.startsWith(HTTPS)) {
            url = HTTP + url;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        getContext().startActivity(Intent.createChooser(intent, "Choose browser"));// Choose browser is arbitrary :)
    }

}
