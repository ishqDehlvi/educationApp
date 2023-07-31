package com.mp.neetjeeiitprep.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mp.neetjeeiitprep.AppUtils.AppUtils;
import com.mp.neetjeeiitprep.AppUtils.Pref;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.adapter.ResultListAdapter;
import com.mp.neetjeeiitprep.dataModel.ResultListDataModel;
import com.mp.neetjeeiitprep.network.ApiClient;
import com.mp.neetjeeiitprep.network.WebApi;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultListingFragment extends Fragment {

    private View rootview;
    private RecyclerView rcvHistory;
    private ResultListAdapter resultListAdapter;
    private ArrayList<ResultListDataModel.Result> arrayList;
    private ProgressDialog pd;
    private String TAG = "ResultListingFragmentTAG";

    public ResultListingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_history, container, false);

        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");

        arrayList = new ArrayList<>();
        rcvHistory = rootview.findViewById(R.id.rcvHistory);
        rcvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        resultListAdapter = new ResultListAdapter(getContext(), arrayList);
        rcvHistory.setAdapter(resultListAdapter);

        if (AppUtils.isOnline(getContext())) {
            getResultList();
        } else {
            Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
        }


        return rootview;
    }

    private void getResultList() {
        pd.show();
        Pref pref = new Pref(getContext());
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", pref.getString(Constant.USER_ID));


        Log.d(TAG, "getResultList: " + params.toString());
        WebApi apiClient = ApiClient.getApiInstance().create(WebApi.class);
        Call<ResultListDataModel> callApi = apiClient.getResultList(params);
        callApi.enqueue(new Callback<ResultListDataModel>() {
            @Override
            public void onResponse(Call<ResultListDataModel> call, Response<ResultListDataModel> response) {
                   Log.d(TAG, "onResponse: "+response.body().toString());
                if (response.body().getCode().contentEquals(Constant.CODE_SUCCESS)) {
                    arrayList.addAll(response.body().getResults());
                    resultListAdapter.notifyDataSetChanged();
                }
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<ResultListDataModel> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure: getResultList " + t.toString());
            }
        });

    }

}
