package com.mp.neetjeeiitprep.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mp.neetjeeiitprep.AppUtils.AppUtils;
import com.mp.neetjeeiitprep.AppUtils.Pref;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.adapter.HistoryAdapter;
import com.mp.neetjeeiitprep.dataModel.HistoryListDataModel;
import com.mp.neetjeeiitprep.network.ApiClient;
import com.mp.neetjeeiitprep.network.WebApi;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryFragment extends Fragment {

    private View rootview;
    private RecyclerView rcvHistory;
    private HistoryAdapter historyAdapter;
    private ArrayList<HistoryListDataModel.Result> arrayList;
    private ProgressDialog pd;
    private String TAG = "HistoryFragmentTAG";

    public HistoryFragment() {
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
        historyAdapter = new HistoryAdapter(getContext(), arrayList);
        rcvHistory.setAdapter(historyAdapter);

        /*if (AppUtils.isOnline(getContext())) {
            getQHistoryList();
        } else {
            Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
        }*/


        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();

        //arrayList = new ArrayList<>();
        arrayList.clear();
        if (AppUtils.isOnline(getContext())) {
            getQHistoryList();
        } else {
            Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void getQHistoryList() {
        pd.show();
        Pref pref = new Pref(getContext());
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", pref.getString(Constant.USER_ID));


        Log.d(TAG, "getQHistoryList: " + params.toString());
        WebApi apiClient = ApiClient.getApiInstance().create(WebApi.class);
        Call<HistoryListDataModel> callApi = apiClient.getHistoryList(params);
        callApi.enqueue(new Callback<HistoryListDataModel>() {
            @Override
            public void onResponse(Call<HistoryListDataModel> call, Response<HistoryListDataModel> response) {
                //   Log.d(TAG, "onResponse: "+new Gson().toJson(response));
                if (response.body().getCode().contentEquals(Constant.CODE_SUCCESS)) {
                    arrayList.addAll(response.body().getResults());
                    historyAdapter.notifyDataSetChanged();
                }
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<HistoryListDataModel> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure: getQHistoryList " + t.toString());
            }
        });

    }

}
