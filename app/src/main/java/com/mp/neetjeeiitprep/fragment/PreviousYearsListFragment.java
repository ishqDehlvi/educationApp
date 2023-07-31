package com.mp.neetjeeiitprep.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mp.neetjeeiitprep.AppUtils.AppUtils;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.adapter.PreviousYearsAdapter;
import com.mp.neetjeeiitprep.dataModel.SubTypeDataModel;
import com.mp.neetjeeiitprep.network.ApiClient;
import com.mp.neetjeeiitprep.network.WebApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreviousYearsListFragment extends Fragment {

    private View rootView;
    private Bundle navigationData;
    //private OnSubjectClickListener onSubjectClickListener;
    private PreviousYearsAdapter previousYearsAdapter;
    private ArrayList<SubTypeDataModel.List> prevYearsList;
    private ProgressDialog pd;
    private String TAG = "PreviousYearsListFragmentTAG";

    public PreviousYearsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(prevYearsList ==null)
        prevYearsList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_previous_years_list, container, false);

        pd=new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        navigationData = getArguments();

        String catId = getArguments().getString(Constant.CATEGORY_ID);
        String subCatId = getArguments().getString(Constant.SUB_CATEGORY_ID);
        String subType = Constant.SUB_TYPE_YEAR;

        //loadData();
        if(prevYearsList.size()==0) {
            if (AppUtils.isOnline(getContext())) {
                fetchSubtypeList(catId, subCatId, subType);
            }
        }
        RecyclerView rcvPreviousYearsList = rootView.findViewById(R.id.rcvPreviousYearsList);
        rcvPreviousYearsList.setLayoutManager(new LinearLayoutManager(getContext()));
        previousYearsAdapter = new PreviousYearsAdapter(getContext(), prevYearsList, navigationData.getInt(Constant.SUBJECT_ICON),getArguments());
        rcvPreviousYearsList.setAdapter(previousYearsAdapter);


        return rootView;
    }

    /*private void loadData() {
        prevYearsList.add("2009");
        prevYearsList.add("2010");
        prevYearsList.add("2011");
        prevYearsList.add("2012");
        prevYearsList.add("2013");
        prevYearsList.add("2014");
        prevYearsList.add("2015");
        prevYearsList.add("2016");
        prevYearsList.add("2017");
        prevYearsList.add("2018");
        prevYearsList.add("2019");

    }*/
    private void fetchSubtypeList(String catId, String subCatId, String type) {
        pd.show();
        WebApi webApi = ApiClient.getApiInstance().create(WebApi.class);
        Call<SubTypeDataModel> callApi = webApi.getSubTypeList(catId, subCatId, type);
        callApi.enqueue(new Callback<SubTypeDataModel>() {
            @Override
            public void onResponse(Call<SubTypeDataModel> call, Response<SubTypeDataModel> response) {
                Log.d(TAG, "fetchSubtypeList onResponse: " + response.body().toString());
                loadSubTypeList(response);
                pd.dismiss();
                //  dataFetched = true;
            }

            @Override
            public void onFailure(Call<SubTypeDataModel> call, Throwable t) {
                Log.e(TAG, "fetchSubtypeList onFailure: " + t.toString());
                pd.dismiss();
            }
        });

    }

    private void loadSubTypeList(Response<SubTypeDataModel> response) {

        if (response.body().getCode().contentEquals(Constant.CODE_SUCCESS)) {
            if (response.body().getList().size() > 0) {
                prevYearsList.addAll(response.body().getList());
                previousYearsAdapter.setPdfUrl(response.body().getPdfPath());
                previousYearsAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "no chapter found", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
