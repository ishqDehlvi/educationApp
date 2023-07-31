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
import com.mp.neetjeeiitprep.adapter.VideoListingAdapter;
import com.mp.neetjeeiitprep.dataModel.VideoListDataModel;
import com.mp.neetjeeiitprep.network.ApiClient;
import com.mp.neetjeeiitprep.network.WebApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoListFragment extends Fragment {

    private View rootView;
    private Bundle navigationData;
    //private OnSubjectClickListener onSubjectClickListener;
    private VideoListingAdapter videoListingAdapter;
    private ArrayList<VideoListDataModel.Video> videoArrayList;
    private ProgressDialog pd;
    private String TAG = "VideoListFragTAG";

    public VideoListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_pdf_listing, container, false);

        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        navigationData = getArguments();

        String videoChapterId = getArguments().getString("video_chapter_id");

        videoArrayList = new ArrayList<>();
        RecyclerView rcvPdfList = rootView.findViewById(R.id.rcvPdfList);
        rcvPdfList.setLayoutManager(new LinearLayoutManager(getContext()));
        videoListingAdapter = new VideoListingAdapter(getContext(), videoArrayList, navigationData.getInt(Constant.SUBJECT_ICON), getArguments());
        rcvPdfList.setAdapter(videoListingAdapter);

        if (AppUtils.isOnline(getContext())) {
            fetchVideoList(videoChapterId);
        } else {
            Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }

    private void fetchVideoList(String videoChapterId) {
        pd.show();

        WebApi webApi = ApiClient.getApiInstance().create(WebApi.class);
        Call<VideoListDataModel> callApi = webApi.getVideoList(videoChapterId);
        callApi.enqueue(new Callback<VideoListDataModel>() {
            @Override
            public void onResponse(Call<VideoListDataModel> call, Response<VideoListDataModel> response) {
           //     Log.d(TAG, "fetchPdfList onResponse: " + response.body().toString());
                loadPdfList(response);
                pd.dismiss();
                //  dataFetched = true;
            }

            @Override
            public void onFailure(Call<VideoListDataModel> call, Throwable t) {
                Log.e(TAG, "fetchPdfList onFailure: " + t.toString());
                pd.dismiss();
            }
        });

    }

    private void loadPdfList(Response<VideoListDataModel> response) {
        if (response.body().getCode().contentEquals(Constant.CODE_SUCCESS)) {
            if (response.body().getVideolist().size() > 0) {
                videoArrayList.addAll(response.body().getVideolist());
                videoListingAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "no chapter found", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
