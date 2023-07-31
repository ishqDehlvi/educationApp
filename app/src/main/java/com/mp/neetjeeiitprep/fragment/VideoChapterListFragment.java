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
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.adapter.VideoChapterListAdapter;
import com.mp.neetjeeiitprep.dataModel.VideoChapterListDataModel;
import com.mp.neetjeeiitprep.network.ApiClient;
import com.mp.neetjeeiitprep.network.WebApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoChapterListFragment extends Fragment {

    private View rootView;
    private Bundle navigationData;
    //private OnSubjectClickListener onSubjectClickListener;
    private VideoChapterListAdapter videoChapterListAdapter;
    private ArrayList<VideoChapterListDataModel.Typelist.Typ> videoChapterList;
    private ProgressDialog pd;
    private String TAG = "VideoChapterListFragTAG";

    public VideoChapterListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_mock_test_list, container, false);
        pd=new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        navigationData = getArguments();
        videoChapterList = new ArrayList<>();
       // loadData();
        String catId = getArguments().getString(Constant.CATEGORY_ID);
        String subCatId = getArguments().getString(Constant.SUB_CATEGORY_ID);
        String subType = Constant.SUB_TYPE_MOCK;
        //loadData();
        /*Log.d(TAG, "onCreateView: catId ->"+catId);
        Log.d(TAG, "onCreateView: subCatId ->"+subCatId);
        Log.d(TAG, "onCreateView: subType ->"+subType);*/

        if (videoChapterList.size() == 0) {
            if(AppUtils.isOnline(getContext())) {
                fetchVideoChapterList(subCatId);
            }else{
                Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
            }
        }
        RecyclerView rcvMockTestList = rootView.findViewById(R.id.rcvMockTestList);
        rcvMockTestList.setLayoutManager(new LinearLayoutManager(getContext()));
        videoChapterListAdapter = new VideoChapterListAdapter(getContext(), videoChapterList, navigationData.getInt(Constant.SUBJECT_ICON),navigationData);
        rcvMockTestList.setAdapter(videoChapterListAdapter);


        return rootView;
    }


    private void fetchVideoChapterList(String subCatId) {
        pd.show();
        WebApi webApi = ApiClient.getApiInstance().create(WebApi.class);
        Call<VideoChapterListDataModel> callApi = webApi.getVideoChapters(subCatId);
        callApi.enqueue(new Callback<VideoChapterListDataModel>() {
            @Override
            public void onResponse(Call<VideoChapterListDataModel> call, Response<VideoChapterListDataModel> response) {
                Log.d(TAG, "fetchSubtypeList onResponse: " + response.body().toString());
                loadSubTypeList(response);
                pd.dismiss();
                //  dataFetched = true;
            }

            @Override
            public void onFailure(Call<VideoChapterListDataModel> call, Throwable t) {
                Log.e(TAG, "fetchSubtypeList onFailure: " + t.toString());
                pd.dismiss();
            }
        });

    }

    private void loadSubTypeList(Response<VideoChapterListDataModel> response) {

        if (response.body().getCode().contentEquals(Constant.CODE_SUCCESS)) {
            if (response.body().getTypelist().getTyp().size() > 0) {
                videoChapterList.addAll(response.body().getTypelist().getTyp());
                videoChapterListAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "no chapter found", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
