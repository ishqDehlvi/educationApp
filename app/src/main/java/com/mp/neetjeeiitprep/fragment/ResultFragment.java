package com.mp.neetjeeiitprep.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.mp.neetjeeiitprep.AppUtils.AppUtils;
import com.mp.neetjeeiitprep.AppUtils.Pref;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.activity.MainActivity;
import com.mp.neetjeeiitprep.adapter.ResultMcqAdapter;
import com.mp.neetjeeiitprep.dataModel.ResultDataModel;
import com.mp.neetjeeiitprep.network.ApiClient;
import com.mp.neetjeeiitprep.network.WebApi;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultFragment extends Fragment {


    View rootView;
    private TextView tvResSubjectHead, tvTotalAttempt, tvUnAttempt, tvRightAns, tvWrongAns, tvTotalMarks, tvPercentage, tvRemarks, tvMTResExplain;
    private WebApi apiClient;
    private Pref pref;
    private InterstitialAd mInterstitialAd;
    private ProgressDialog pd;
    // AnimatedPieView mAnimatedPieView;

    private String TAG = "ResultFragmentTAG";

    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_result, container, false);

        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        apiClient = ApiClient.getApiInstance().create(WebApi.class);
        pref = new Pref(getContext());

        tvResSubjectHead = rootView.findViewById(R.id.tvResSubjectHead);
        tvResSubjectHead.setText(getArguments().getString(Constant.SUB_CATEGORY_NAME) + "(" + getArguments().getString(Constant.SELECTED_TYPE_NAME) + ")");

        if (AppUtils.isOnline(getContext())) {
            loadAd();
        } else {
            Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
        }
        getResult();

        return rootView;
    }

    private void getResult() {
        pd.show();

        String subId = getArguments().getString(Constant.SUB_CATEGORY_ID);
        String typeId = getArguments().getString(Constant.SELECTED_TYPE_ID);
        String totalCorrectAns = getArguments().getString(Constant.TOTAL_CORRECT_QUESTION);
        String totalAttamptAns = getArguments().getString(Constant.TOTAL_ATTEMPTED_QUESTION);

        HashMap<String, String> params = new HashMap<>();
        params.put("sub_id", subId);
        params.put("type_id", typeId);
        params.put("user_id", pref.getString(Constant.USER_ID));
        params.put("total_correct_answer", totalCorrectAns);
        params.put("total_attempted", totalAttamptAns);

        Log.d(TAG, "getResult: " + params.toString());
        Call<ResultDataModel> callApi = apiClient.getResultData(params);
        callApi.enqueue(new Callback<ResultDataModel>() {
            @Override
            public void onResponse(Call<ResultDataModel> call, Response<ResultDataModel> response) {
                Log.d(TAG, "onResponse: getResult " + response.body().toString());
                loadResult(response);
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<ResultDataModel> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure: getResult " + t.toString());
            }
        });

    }

    private void loadResult(Response<ResultDataModel> response) {
        if (response.body().getCode().contentEquals(Constant.CODE_SUCCESS)) {
            RecyclerView rcvResultMcq = rootView.findViewById(R.id.rcvResultMcq);
            rcvResultMcq.setLayoutManager(new LinearLayoutManager(getContext()));
            rcvResultMcq.setAdapter(new ResultMcqAdapter(getContext(), ((MainActivity) getActivity()).questionItem, response.body(), getArguments()));
        }
    }

    private void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
//        ca-app-pub-5709221063224134/6760789826
        InterstitialAd.load(getContext(),"ca-app-pub-5709221063224134/6760789826", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });


        delayForSomeTime(3000, true);
    }

    private void delayForSomeTime(long timeToDelay, final boolean moreTime) {
        pd.show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms

                if (mInterstitialAd != null) {
                    mInterstitialAd.show(getActivity());


                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }
        }, timeToDelay);

    }

}
