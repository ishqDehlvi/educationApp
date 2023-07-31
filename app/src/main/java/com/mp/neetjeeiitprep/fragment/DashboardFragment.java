package com.mp.neetjeeiitprep.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mp.neetjeeiitprep.AppUtils.AppUtils;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.dataModel.CategoryDataModel;
import com.mp.neetjeeiitprep.network.ApiClient;
import com.mp.neetjeeiitprep.network.WebApi;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private View rootView;
    private ArrayList<CategoryDataModel.Category> categoryArrayList;
    private Bundle navigationData;
    private ProgressDialog pd;
    private boolean dataFetched;
    private String TAG = "DashboardFragmentTAG";

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        navigationData = new Bundle();
        categoryArrayList = new ArrayList<>();
        if (AppUtils.isOnline(getContext())) {
            fetchCategories();
        } else {
            Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
        }

        rootView.findViewById(R.id.mainCat1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataFetched) {
                    sendData("main_cat1");
                    Navigation.findNavController(v).navigate(R.id.action_dashboardFragment_to_studyMetFragment, navigationData);
                } else {
                    Toast.makeText(getContext(), "Data not loaded", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rootView.findViewById(R.id.mainCat2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataFetched) {
                    sendData("main_cat2");
                    Navigation.findNavController(v).navigate(R.id.action_dashboardFragment_to_prevYearQFragment, navigationData);
                } else {
                    Toast.makeText(getContext(), "Data not loaded", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rootView.findViewById(R.id.mainCat3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataFetched) {
                    sendData("main_cat3");
                    Navigation.findNavController(v).navigate(R.id.action_dashboardFragment_to_practiceTestFragment, navigationData);
                } else {
                    Toast.makeText(getContext(), "Data not loaded", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rootView.findViewById(R.id.mainCat4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataFetched) {

                    sendData("main_cat4");
                    Navigation.findNavController(v).navigate(R.id.action_dashboardFragment_to_videoClassesFragment, navigationData);
                } else {
                    Toast.makeText(getContext(), "Data not loaded", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(getContext(), "Coming soon", Toast.LENGTH_SHORT).show();
            }
        });
        //==== BUILDING IT FOR MOCK TESTS LIVE BY DEVANSH
        rootView.findViewById(R.id.mainCat5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataFetched){
                    sendData("main_cat5");
                    Navigation.findNavController(v).navigate(R.id.action_dashboardFragment_to_mockTestLiveFragment, navigationData);
                } else {
                    Toast.makeText(getContext(), "Data not loaded", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    private void fetchCategories() {
        pd.show();
        WebApi webApi = ApiClient.getApiInstance().create(WebApi.class);
        Call<CategoryDataModel> callApi = webApi.getCategoryList();
        callApi.enqueue(new Callback<CategoryDataModel>() {
            @Override
            public void onResponse(Call<CategoryDataModel> call, Response<CategoryDataModel> response) {
                Log.d(TAG, "onResponse: " + response.body().getCategory().toString());
                loadCategories(response);
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<CategoryDataModel> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, "fetchCategories onFailure: " + t.toString());
            }
        });

    }

    private void loadCategories(Response<CategoryDataModel> response) {

        if (response.body().getCode().contentEquals(Constant.CODE_SUCCESS)) {
            categoryArrayList.addAll(response.body().getCategory());
            dataFetched = true;
        }
    }

    private void sendData(String catCode) {

        for (int position = 0; position < categoryArrayList.size(); position++) {
            if (categoryArrayList.get(position).getCatCode().contentEquals(catCode)) {
                Log.d(TAG, "sendData: catId-> " + categoryArrayList.get(position).getCatId());
                navigationData.putString(Constant.CATEGORY_ID, categoryArrayList.get(position).getCatId());
                navigationData.putString(Constant.CATEGORY_NAME, categoryArrayList.get(position).getCatName());
            }
        }
    }

}
