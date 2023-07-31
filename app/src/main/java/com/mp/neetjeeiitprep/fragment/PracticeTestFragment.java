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


public class PracticeTestFragment extends Fragment implements View.OnClickListener {


    private View rootView;
    private Bundle navigationData;
    private OnSubjectClickListener onSubjectClickListener;
    private ArrayList<CategoryDataModel.Category> subCategoryArrayList;
    private boolean dataFetched;
    private ProgressDialog pd;
    private String TAG = "PracticeTestFragmentTAG";

    public PracticeTestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (subCategoryArrayList == null)
            subCategoryArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_practice_test, container, false);

        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");

        onSubjectClickListener = (OnSubjectClickListener) getActivity();
        navigationData = getArguments();

        if (subCategoryArrayList.size() == 0) {
            if (AppUtils.isOnline(getContext())) {
                fetchSubCategories(navigationData.getString(Constant.CATEGORY_ID));
            } else {
                Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
            }
        }

        ConstraintLayout clPracCat1 = rootView.findViewById(R.id.clPracCat1);
        ConstraintLayout clPracCat2 = rootView.findViewById(R.id.clPracCat2);
        ConstraintLayout clPracCat3 = rootView.findViewById(R.id.clPracCat3);
        ConstraintLayout clPracCat4 = rootView.findViewById(R.id.clPracCat4);
        ConstraintLayout clPracCat5 = rootView.findViewById(R.id.clPracCat5);

        clPracCat1.setOnClickListener(this);
        clPracCat2.setOnClickListener(this);
        clPracCat3.setOnClickListener(this);
        clPracCat4.setOnClickListener(this);
        clPracCat5.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clPracCat1: {
                if (dataFetched) {
                    sendData("cat3_subcat1");
                    onSubjectClickListener.subjectClicked(Constant.PHYSICS);
                    navigationData.putString(Constant.FROM_WHERE, Constant.FROM_MOCK_TEST);
                    navigationData.putString(Constant.SELECTED_SUBJECT, Constant.PHYSICS);
                    navigationData.putInt(Constant.SUBJECT_ICON, R.drawable.ic_phy);
                    //navigationData.putString(Constant.SELECTED_SUBJECT_ID,"");
                    Navigation.findNavController(v).navigate(R.id.action_practiceTestFragment_to_mockTestListFragment, navigationData);
                } else {
                    Toast.makeText(getContext(), R.string.no_data_error, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.clPracCat2: {
                if (dataFetched) {
                    sendData("cat3_subcat2");
                    onSubjectClickListener.subjectClicked(Constant.CHEMISTRY);
                    navigationData.putString(Constant.FROM_WHERE, Constant.FROM_MOCK_TEST);
                    navigationData.putString(Constant.SELECTED_SUBJECT, Constant.CHEMISTRY);
                    navigationData.putInt(Constant.SUBJECT_ICON, R.drawable.ic_chemistry);
                    //navigationData.putString(Constant.SELECTED_SUBJECT_ID,"");
                    Navigation.findNavController(v).navigate(R.id.action_practiceTestFragment_to_mockTestListFragment, navigationData);
                } else {
                    Toast.makeText(getContext(), R.string.no_data_error, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.clPracCat3: {
                if (dataFetched) {
                    sendData("cat3_subcat3");
                    onSubjectClickListener.subjectClicked(Constant.BIOLOGY);
                    navigationData.putString(Constant.FROM_WHERE, Constant.FROM_MOCK_TEST);
                    navigationData.putString(Constant.SELECTED_SUBJECT, Constant.BIOLOGY);
                    navigationData.putInt(Constant.SUBJECT_ICON, R.drawable.ic_biology);
                    //navigationData.putString(Constant.SELECTED_SUBJECT_ID,"");
                    Navigation.findNavController(v).navigate(R.id.action_practiceTestFragment_to_mockTestListFragment, navigationData);
                } else {
                    Toast.makeText(getContext(), R.string.no_data_error, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.clPracCat4: {
                if (dataFetched) {
                    sendData("cat3_subcat4");
                    onSubjectClickListener.subjectClicked(Constant.MATHEMATICS);
                    navigationData.putString(Constant.FROM_WHERE, Constant.FROM_MOCK_TEST);
                    navigationData.putString(Constant.SELECTED_SUBJECT, Constant.MATHEMATICS);
                    navigationData.putInt(Constant.SUBJECT_ICON, R.drawable.ic_math);
                    //navigationData.putString(Constant.SELECTED_SUBJECT_ID,"");
                    Navigation.findNavController(v).navigate(R.id.action_practiceTestFragment_to_mockTestListFragment, navigationData);
                } else {
                    Toast.makeText(getContext(), R.string.no_data_error, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.clPracCat5: {
                if (dataFetched) {
                    sendData("cat2_subcat4");
                    onSubjectClickListener.subjectClicked(Constant.COMPLETE_SYLLABUS);
                    navigationData.putString(Constant.FROM_WHERE, Constant.FROM_MOCK_TEST);
                    navigationData.putString(Constant.SELECTED_SUBJECT, Constant.COMPLETE_SYLLABUS);
                    navigationData.putInt(Constant.SUBJECT_ICON, R.drawable.ic_syllabus);
                    //navigationData.putString(Constant.SELECTED_SUBJECT_ID,"");
                    //Log.d(TAG, "onClick: navigationData"+navigationData.toString());
                    Navigation.findNavController(v).navigate(R.id.action_practiceTestFragment_to_mockTestListFragment, navigationData);
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
        Call<CategoryDataModel> callApi = webApi.getSubCategoryList(catId);
        callApi.enqueue(new Callback<CategoryDataModel>() {
            @Override
            public void onResponse(Call<CategoryDataModel> call, Response<CategoryDataModel> response) {
                Log.d(TAG, "fetchSubCategories onResponse: " + response.body().getSubCategory().toString());
                loadSubCategories(response);
                pd.dismiss();
                dataFetched = true;
            }

            @Override
            public void onFailure(Call<CategoryDataModel> call, Throwable t) {
                Log.e(TAG, "fetchSubCategories onFailure: " + t.toString());
                pd.dismiss();
            }
        });
    }

    private void loadSubCategories(Response<CategoryDataModel> response) {

        if (response.body().getCode().contentEquals(Constant.CODE_SUCCESS)) {
            subCategoryArrayList.addAll(response.body().getSubCategory());
        }
    }

    private void sendData(String catCode) {

        for (int position = 0; position < subCategoryArrayList.size(); position++) {
            if (subCategoryArrayList.get(position).getCatCode().contentEquals(catCode)) {
                Log.d(TAG, "sendData: catId-> " + subCategoryArrayList.get(position).getCatId());
                Log.d(TAG, "sendData: catName-> " + subCategoryArrayList.get(position).getCatName());
                navigationData.putString(Constant.SUB_CATEGORY_ID, subCategoryArrayList.get(position).getCatId());
                navigationData.putString(Constant.SUB_CATEGORY_NAME, subCategoryArrayList.get(position).getCatName());
            }
        }
    }
}
