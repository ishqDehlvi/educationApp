package com.mp.neetjeeiitprep.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Class11_12mockTestLiveFragment extends Fragment implements View.OnClickListener{

    private View rootView;
    private boolean dataFetched;
    private Bundle navigationData;
    private ProgressDialog pd;
    private OnSubjectClickListener onSubjectClickListener;
    private ArrayList<CategoryDataModel.Category> subCategoryArrayList;
    private String TAG = "CLASS11&12MockTestFragmentTAG";
    private String whechStandard;

    public Class11_12mockTestLiveFragment(){
        // Required empty public constructor
    }

    public void onCreate(@Nullable Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        if (subCategoryArrayList == null){
            subCategoryArrayList = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_class11_12mocktestlive, container, false);
        pd = new ProgressDialog(getContext());
        pd.setMessage("loading...");

        onSubjectClickListener = (OnSubjectClickListener) getActivity();
        navigationData = getArguments();
        whechStandard = getArguments().getString(Constant.WHICH_STANDARD);

//        navigationData = new Bundle();
//        whechStandard = getArguments().getString(Constant.WHICH_STANDARD);


//        if (whechStandard.contentEquals(Constant.FROM_CLASS_11)){
//            Toast.makeText(getContext(),"Class 11 students", Toast.LENGTH_SHORT).show();
////            Log.d("Which standard","Class 11th");
//        } else if (whechStandard.contentEquals(Constant.FROM_CLASS_12)){
//            Toast.makeText(getContext(),"Class 12 students", Toast.LENGTH_SHORT).show();
////            Log.d("Which standard","Class 12th");
//        } else {
//            Toast.makeText(getContext(),"Don't know", Toast.LENGTH_SHORT).show();
//        }

        if (subCategoryArrayList.size() == 0){
            if (AppUtils.isOnline(getContext())){
                fetchSubCategories(navigationData.getString(Constant.CATEGORY_ID));
            } else{
                Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
            }
        }

        ConstraintLayout clClass11_12Cat1 = rootView.findViewById(R.id.clClass11_12Cat1);
        ConstraintLayout clClass11_12Cat2 = rootView.findViewById(R.id.clClass11_12Cat2);
        ConstraintLayout clClass11_12Cat3 = rootView.findViewById(R.id.clClass11_12Cat3);


        clClass11_12Cat1.setOnClickListener(this);
        clClass11_12Cat2.setOnClickListener(this);
        clClass11_12Cat3.setOnClickListener(this);

//        rootView.findViewById(R.id.clClass11Cat1).setOnClickListener(view -> {
//            if (dataFetched){
//                navigationData.putString(Constant.WHICH_EXAM,Constant.FROM_NEET);
//                navigationData.putString(Constant.FROM_WHERE,Constant.FROM_NEETJEE_EXAM);
//                Navigation.findNavController(view).navigate(R.id.action_class11_12mockTestLiveFragment_to_mcqTestFragment, navigationData);
//            } else {
//                Toast.makeText(getContext(), "Data not loaded", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        rootView.findViewById(R.id.clClass11Cat2).setOnClickListener(view -> {
//            if (dataFetched){
//                navigationData.putString(Constant.WHICH_EXAM,Constant.FROM_JEE);
//                navigationData.putString(Constant.FROM_WHERE,Constant.FROM_NEETJEE_EXAM);
//                Navigation.findNavController(view).navigate(R.id.action_class11_12mockTestLiveFragment_to_mcqTestFragment, navigationData);
//            } else {
//                Toast.makeText(getContext(), "Data not loaded", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        rootView.findViewById(R.id.clClass11Cat3).setOnClickListener(view -> {
//            if (dataFetched){
//                navigationData.putString(Constant.WHICH_EXAM,Constant.FROM_BOTH);
//                navigationData.putString(Constant.FROM_WHERE,Constant.FROM_NEETJEE_EXAM);
//                Navigation.findNavController(view).navigate(R.id.action_class11_12mockTestLiveFragment_to_mcqTestFragment, navigationData);
//            } else {
//                Toast.makeText(getContext(), "Data not loaded", Toast.LENGTH_SHORT).show();
//            }
//        });



        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clClass11_12Cat1: {
                if (dataFetched){
                    sendData("cat3_subcat1");
                    onSubjectClickListener.subjectClicked(Constant.FROM_NEET);
                    navigationData.putString(Constant.FROM_WHERE, Constant.FROM_NEETJEE_EXAM);
                    navigationData.putString(Constant.WHICH_EXAM, Constant.FROM_NEET);
                    navigationData.putString(Constant.WHICH_STANDARD, whechStandard);
                  Navigation.findNavController(v).navigate(R.id.action_class11_12mockTestLiveFragment_to_mcqTestFragment, navigationData);
                } else {
                    Toast.makeText(getContext(), R.string.no_data_error, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.clClass11_12Cat2: {
                if (dataFetched){
                    sendData("cat3_subcat2");
                    onSubjectClickListener.subjectClicked(Constant.FROM_JEE);
                    navigationData.putString(Constant.FROM_WHERE, Constant.FROM_NEETJEE_EXAM);
                    navigationData.putString(Constant.WHICH_EXAM, Constant.FROM_JEE);
                    navigationData.putString(Constant.WHICH_STANDARD, whechStandard);
                    Navigation.findNavController(v).navigate(R.id.action_class11_12mockTestLiveFragment_to_mcqTestFragment, navigationData);
                } else{
                    Toast.makeText(getContext(), R.string.no_data_error, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.clClass11_12Cat3: {
                if (dataFetched) {
                    sendData("cat3_subcat3");
                    onSubjectClickListener.subjectClicked(Constant.FROM_BOTH);
                    navigationData.putString(Constant.FROM_WHERE,Constant.FROM_NEETJEE_EXAM);
                    navigationData.putString(Constant.WHICH_EXAM, Constant.FROM_BOTH);
                    navigationData.putString(Constant.WHICH_STANDARD, whechStandard);
                    Navigation.findNavController(v).navigate(R.id.action_class11_12mockTestLiveFragment_to_mcqTestFragment, navigationData);
                } else {
                    Toast.makeText(getContext(), R.string.no_data_error, Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void fetchSubCategories(String catId){

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

    private void loadSubCategories(Response<CategoryDataModel> response){
        if (response.body().getCode().contentEquals(Constant.CODE_SUCCESS)){
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