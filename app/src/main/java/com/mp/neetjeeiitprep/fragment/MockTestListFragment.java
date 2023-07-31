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
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.adapter.MockTestListAdapter;
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
public class  MockTestListFragment extends Fragment {

    private View rootView;
    private Bundle navigationData;
    //private OnSubjectClickListener onSubjectClickListener;
    private MockTestListAdapter mockTestListAdapter;
    private ArrayList<SubTypeDataModel.List> mockTestList;
    private ProgressDialog pd;
    private String TAG = "PreviousYearsListFragmentTAG";

    public MockTestListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_mock_test_list, container, false);
        pd=new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        navigationData = getArguments();
        mockTestList = new ArrayList<>();
       // loadData();
        String catId = getArguments().getString(Constant.CATEGORY_ID);
        String subCatId = getArguments().getString(Constant.SUB_CATEGORY_ID);
        String subType = Constant.SUB_TYPE_MOCK;
        //loadData();
        /*Log.d(TAG, "onCreateView: catId ->"+catId);
        Log.d(TAG, "onCreateView: subCatId ->"+subCatId);
        Log.d(TAG, "onCreateView: subType ->"+subType);*/

        if (mockTestList.size() == 0) {
            if(AppUtils.isOnline(getContext())) {
                fetchSubtypeList(catId, subCatId, subType);
            }else{
                Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
            }
        }
        RecyclerView rcvMockTestList = rootView.findViewById(R.id.rcvMockTestList);
        rcvMockTestList.setLayoutManager(new LinearLayoutManager(getContext()));
        mockTestListAdapter = new MockTestListAdapter(getContext(), mockTestList, navigationData.getInt(Constant.SUBJECT_ICON),navigationData);
        rcvMockTestList.setAdapter(mockTestListAdapter);


        return rootView;
    }

    /*
    private void loadData() {
        mockTestList.add("Mock Test 1");
        mockTestList.add("Mock Test 2");
        mockTestList.add("Mock Test 3");
        mockTestList.add("Mock Test 4");
        mockTestList.add("Mock Test 5");
        mockTestList.add("Mock Test 6");
        mockTestList.add("Mock Test 7");
        mockTestList.add("Mock Test 8");
        mockTestList.add("Mock Test 9");
        mockTestList.add("Mock Test 10");


    }
    */

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
                mockTestList.addAll(response.body().getList());
                mockTestListAdapter.setPdfUrl(response.body().getPdfPath());
                mockTestListAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "no chapter found", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
