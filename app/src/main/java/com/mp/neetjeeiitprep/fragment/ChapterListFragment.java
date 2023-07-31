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
import com.mp.neetjeeiitprep.adapter.ChapterAdapter;
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
public class ChapterListFragment extends Fragment {

    private View rootView;
    private Bundle navigationData;
    //private OnSubjectClickListener onSubjectClickListener;
    private ChapterAdapter chapterAdapter;
    private ArrayList<SubTypeDataModel.List> chapterList;
    private ProgressDialog pd;
    private String pdfUrl;
    private String TAG = "ChapterListFragmentTAG";

    public ChapterListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (chapterList == null) {
            chapterList = new ArrayList<>();
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chapter_list, container, false);
        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        navigationData = getArguments();

        String catId = getArguments().getString(Constant.CATEGORY_ID);
        String subCatId = getArguments().getString(Constant.SUB_CATEGORY_ID);
        String subType = Constant.SUB_TYPE_STUDY;
        //loadData();
        chapterList = new ArrayList<>();
      //  if (chapterList.size() == 0) {
            if(AppUtils.isOnline(getContext())) {
                fetchSubtypeList(catId, subCatId, subType);
            }else{
                Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
            }
        //}
        RecyclerView rcvChapterList = rootView.findViewById(R.id.rcvChapterList);
        rcvChapterList.setLayoutManager(new LinearLayoutManager(getContext()));
        chapterAdapter = new ChapterAdapter(getContext(), chapterList, navigationData.getInt(Constant.SUBJECT_ICON), getArguments());
        rcvChapterList.setAdapter(chapterAdapter);


        return rootView;
    }

   /* private void loadData() {
        chapterList.add("Chapter 1");
        chapterList.add("Chapter 2");
        chapterList.add("Chapter 3");
        chapterList.add("Chapter 4");
        chapterList.add("Chapter 5");
        chapterList.add("Chapter 6");
        chapterList.add("Chapter 7");
        chapterList.add("Chapter 8");
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
                chapterList.addAll(response.body().getList());
                pdfUrl=response.body().getPdfPath();
                chapterAdapter.setPdfUrl(pdfUrl);
                chapterAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "no chapter found", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
