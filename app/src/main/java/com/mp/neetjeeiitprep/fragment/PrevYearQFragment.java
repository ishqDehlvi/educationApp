package com.mp.neetjeeiitprep.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mp.neetjeeiitprep.AppUtils.AppUtils;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.dataModel.CategoryDataModel;
import com.mp.neetjeeiitprep.dataModel.PrevYearRangDataModel;
import com.mp.neetjeeiitprep.interfaces.OnSubjectClickListener;
import com.mp.neetjeeiitprep.network.ApiClient;
import com.mp.neetjeeiitprep.network.WebApi;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrevYearQFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private Bundle navigationData;
    private OnSubjectClickListener onSubjectClickListener;
    private ArrayList<CategoryDataModel.Category> subCategoryArrayList;
    private ArrayList<PrevYearRangDataModel.Year> yearRangArrayList;
    private boolean dataFetched;
    private TextView tvCatDate1,tvCatDate2,tvCatDate3,tvCatDate4;
    private ProgressDialog pd;
    private String TAG = "PrevYearQFragmentTAG";

    public PrevYearQFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (subCategoryArrayList == null) {
            subCategoryArrayList = new ArrayList<>();
            yearRangArrayList = new ArrayList<>();
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_prev_year_q, container, false);

        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        onSubjectClickListener = (OnSubjectClickListener) getActivity();
        navigationData = getArguments();

        subCategoryArrayList = new ArrayList<>();
        yearRangArrayList = new ArrayList<>();

       // if (subCategoryArrayList.size() == 0) {
            if (AppUtils.isOnline(getContext())) {
                fetchSubCategories(navigationData.getString(Constant.CATEGORY_ID));
            } else {
                Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
            }
      //  }

        ConstraintLayout clPrevCat1 = rootView.findViewById(R.id.clPrevCat1);
        ConstraintLayout clPrevCat2 = rootView.findViewById(R.id.clPrevCat2);
        ConstraintLayout clPrevCat3 = rootView.findViewById(R.id.clPrevCat3);
        ConstraintLayout clPrevCat4 = rootView.findViewById(R.id.clPrevCat4);

        tvCatDate1 = rootView.findViewById(R.id.tvCatDate1);
        tvCatDate2 = rootView.findViewById(R.id.tvCatDate2);
        tvCatDate3 = rootView.findViewById(R.id.tvCatDate3);
        tvCatDate4 = rootView.findViewById(R.id.tvCatDate4);

        clPrevCat1.setOnClickListener(this);
        clPrevCat2.setOnClickListener(this);
        clPrevCat3.setOnClickListener(this);
        clPrevCat4.setOnClickListener(this);



        return rootView;
    }

    /* @Override
     public void subjectClicked(String subjectName) {

     }
 */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clPrevCat1: {
                if (dataFetched) {
                    sendData("cat2_subcat1");
                    navigationData.putString(Constant.FROM_WHERE, Constant.FROM_PREV_YEARS);
                    onSubjectClickListener.subjectClicked(Constant.NEET_AIPMT);
                 //   navigationData.putString(Constant.FROM_WHERE, Constant.NEET_AIPMT);
                    navigationData.putInt(Constant.SUBJECT_ICON, R.drawable.ic_medical);
                    //navigationData.putString(Constant.SELECTED_SUBJECT_ID,"");
                    Navigation.findNavController(v).navigate(R.id.action_prevYearQFragment_to_previousYearsListFragment, navigationData);
                } else {
                    Toast.makeText(getContext(), R.string.no_data_error, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.clPrevCat2: {
                if (dataFetched) {
                    sendData("cat2_subcat2");
                    navigationData.putString(Constant.FROM_WHERE, Constant.FROM_PREV_YEARS);
                    onSubjectClickListener.subjectClicked(Constant.JEE_MAIN);
                  //  navigationData.putString(Constant.FROM_WHERE, Constant.JEE_MAIN);
                    navigationData.putInt(Constant.SUBJECT_ICON, R.drawable.ic_gear);
                    //navigationData.putString(Constant.SELECTED_SUBJECT_ID,"");
                    Navigation.findNavController(v).navigate(R.id.action_prevYearQFragment_to_previousYearsListFragment, navigationData);
                } else {
                    Toast.makeText(getContext(), R.string.no_data_error, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.clPrevCat3: {
                if (dataFetched) {
                    sendData("cat2_subcat3");
                    navigationData.putString(Constant.FROM_WHERE, Constant.FROM_PREV_YEARS);
                    onSubjectClickListener.subjectClicked(Constant.AIIMS);
                //    navigationData.putString(Constant.FROM_WHERE, Constant.AIIMS);
                    navigationData.putInt(Constant.SUBJECT_ICON, R.drawable.ic_medical_plus);
                    //navigationData.putString(Constant.SELECTED_SUBJECT_ID,"");
                    Navigation.findNavController(v).navigate(R.id.action_prevYearQFragment_to_previousYearsListFragment, navigationData);
                } else {
                    Toast.makeText(getContext(), R.string.no_data_error, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.clPrevCat4: {
                if (dataFetched) {
                    sendData("cat2_subcat4");
                    navigationData.putString(Constant.FROM_WHERE, Constant.FROM_PREV_YEARS);
                    onSubjectClickListener.subjectClicked(Constant.JEE_ADVANCE);
                   // navigationData.putString(Constant.FROM_WHERE, Constant.JEE_ADVANCE);
                    navigationData.putInt(Constant.SUBJECT_ICON, R.drawable.ic_gear_with_helmet);
                    //navigationData.putString(Constant.SELECTED_SUBJECT_ID,"");
                    Navigation.findNavController(v).navigate(R.id.action_prevYearQFragment_to_previousYearsListFragment, navigationData);
                } else {
                    Toast.makeText(getContext(), R.string.no_data_error, Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void fetchSubCategories(String catId) {
        pd.show();
        WebApi webApi = ApiClient.getApiInstance().create(WebApi.class);
        Call<JsonElement> callApi = webApi.getPreYearSubCategoryList(catId);
        callApi.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //  Log.d(TAG, "fetchSubCategories onResponse: " + response.body().getSubCategory().toString());

                loadSubCategories(response);
                pd.dismiss();
                dataFetched = true;
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e(TAG, "fetchSubCategories onFailure: " + t.toString());
                pd.dismiss();
            }
        });
    }

    private void loadSubCategories(Response<JsonElement> response) {

        CategoryDataModel data = new Gson().fromJson(response.body().getAsJsonObject(), CategoryDataModel.class);
        if (data.getCode().contentEquals(Constant.CODE_SUCCESS)) {
            subCategoryArrayList.addAll(data.getSubCategory());
            PrevYearRangDataModel preYearsRang = new Gson().fromJson(response.body().getAsJsonObject(), PrevYearRangDataModel.class);
            // Log.d(TAG, "loadSubCategories: "+preYearsRang);
            yearRangArrayList.addAll(preYearsRang.getYear());

            setUpYearRange("cat2_subcat1", tvCatDate1);
            setUpYearRange("cat2_subcat2", tvCatDate2);
            setUpYearRange("cat2_subcat3", tvCatDate3);
            setUpYearRange("cat2_subcat4", tvCatDate4);
        }
    }

    private void sendData(String catCode) {

        for (int position = 0; position < subCategoryArrayList.size(); position++) {
            if (subCategoryArrayList.get(position).getCatCode().contentEquals(catCode)) {
                Log.d(TAG, "sendData: catId-> " + subCategoryArrayList.get(position).getCatId());
                Log.d(TAG, "sendData: catName-> " + subCategoryArrayList.get(position).getCatName());
                navigationData.putString(Constant.SUB_CATEGORY_ID, subCategoryArrayList.get(position).getCatId());
                navigationData.putString(Constant.SUB_CATEGORY_NAME, subCategoryArrayList.get(position).getCatName());
                /*for(int i=0;i<yearRangArrayList.size();i++){
                    if(subCategoryArrayList.get(position).getCatId().contentEquals(yearRangArrayList.get(i).getSubcatId())){
                        tv.setText(yearRangArrayList.get(i).getYearRange());
                    }
                }*/
            }
        }
    }

    private void setUpYearRange(String catCode, TextView tv) {
        for (int position = 0; position < subCategoryArrayList.size(); position++) {
            if (subCategoryArrayList.get(position).getCatCode().contentEquals(catCode)) {
                for (int i = 0; i < yearRangArrayList.size(); i++) {
                    if (subCategoryArrayList.get(position).getCatId().contentEquals(yearRangArrayList.get(i).getSubcatId())) {
                        tv.setText(yearRangArrayList.get(i).getYearRange());
                    }
                }
            }
        }
    }
}
