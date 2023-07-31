package com.mp.neetjeeiitprep.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.adapter.Top10StudentsAdapter;
import com.mp.neetjeeiitprep.dataModel.Top10StudentsDataModel;

import java.util.ArrayList;


public class Top10StudentNavFragment extends Fragment {


    View rootView;
//    private WebApi apiClient;
//    private Pref pref;
//    private ProgressDialog pd;
//    private String TAG = "ResultFragmentTAG";
//    public Top10StudentNavFragment() {
//        // Required empty public constructor
//    }

    private RecyclerView rcvStudentsProfile;
    private Top10StudentsAdapter top10StudentsAdapter;
    private ArrayList<Top10StudentsDataModel> dataHolder;
//    private ArrayAdapter<String> arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       rootView = inflater.inflate(R.layout.fragment_top10_student_nav, container, false);



        rcvStudentsProfile = rootView.findViewById(R.id.rcvStudentsProfile);
        rcvStudentsProfile.setLayoutManager(new LinearLayoutManager(getContext()));
        dataHolder = new ArrayList<>();

        Top10StudentsDataModel ob1 = new Top10StudentsDataModel(25,"Akansha","Physics","Question Attempted:","62/250","Right Answer:","" +
                "60","Marks Obtained:","60","Date:","01-05-2023","25%");
        Top10StudentsDataModel ob2 = new Top10StudentsDataModel(25,"Devansh","Physics","Question Attempted:","62/250","Right Answer:","" +
                "60","Marks Obtained:","60","Date:","01-05-2023","25%");
        Top10StudentsDataModel ob3 = new Top10StudentsDataModel(25,"Raj Ranjan","Physics","Question Attempted:","62/250","Right Answer:","" +
                "60","Marks Obtained:","60","Date:","01-05-2023","25%");
        Top10StudentsDataModel ob4 = new Top10StudentsDataModel(25,"Sanket","Physics","Question Attempted:","62/250","Right Answer:","" +
                "60","Marks Obtained:","60","Date:","01-05-2023","25%");
        Top10StudentsDataModel ob5 = new Top10StudentsDataModel(25,"Bhusan","Physics","Question Attempted:","62/250","Right Answer:","" +
                "60","Marks Obtained:","60","Date:","01-05-2023","25%");
        Top10StudentsDataModel ob6 = new Top10StudentsDataModel(25,"Shubhamrit","Physics","Question Attempted:","62/250","Right Answer:","" +
                "60","Marks Obtained:","60","Date:","01-05-2023","25%");
        Top10StudentsDataModel ob7 = new Top10StudentsDataModel(25,"Harsh","Physics","Question Attempted:","62/250","Right Answer:","" +
                "60","Marks Obtained:","60","Date:","01-05-2023","25%");
        Top10StudentsDataModel ob8 = new Top10StudentsDataModel(25,"Deval","Physics","Question Attempted:","62/250","Right Answer:","" +
                "60","Marks Obtained:","60","Date:","01-05-2023","25%");
       Top10StudentsDataModel ob9 = new Top10StudentsDataModel(25,"Bhanu Singh","Physics","Question Attempted:","62/250","Right Answer:","" +
                "60","Marks Obtained:","60","Date:","01-05-2023","25%");
        Top10StudentsDataModel ob10 = new Top10StudentsDataModel(25,"Shivam","Physics","Question Attempted:","62/250","Right Answer:","" +
                "60","Marks Obtained:","60","Date:","01-05-2023","25%");

        dataHolder.add(ob1);
        dataHolder.add(ob2);
        dataHolder.add(ob3);
        dataHolder.add(ob4);
        dataHolder.add(ob5);
        dataHolder.add(ob6);
        dataHolder.add(ob7);
        dataHolder.add(ob8);
        dataHolder.add(ob9);
        dataHolder.add(ob10);
        top10StudentsAdapter = new Top10StudentsAdapter(dataHolder);
        rcvStudentsProfile.setAdapter(top10StudentsAdapter);

       return rootView;
    }





































//    private void getResult() {
//        pd.show();
//
//        String subId = getArguments().getString(Constant.SUB_CATEGORY_ID);
//        String typeId = getArguments().getString(Constant.SELECTED_TYPE_ID);
//        String totalCorrectAns = getArguments().getString(Constant.TOTAL_CORRECT_QUESTION);
//        String totalAttamptAns = getArguments().getString(Constant.TOTAL_ATTEMPTED_QUESTION);
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("sub_id", subId);
//        params.put("type_id", typeId);
//        params.put("user_id", pref.getString(Constant.USER_ID));
//        params.put("total_correct_answer", totalCorrectAns);
//        params.put("total_attempted", totalAttamptAns);
//
//        Log.d(TAG, "getResult: " + params.toString());
//        Call<ResultDataModel> callApi = apiClient.getResultData(params);
//        callApi.enqueue(new Callback<ResultDataModel>() {
//            @Override
//            public void onResponse(Call<ResultDataModel> call, Response<ResultDataModel> response) {
//                Log.d(TAG, "onResponse: getResult " + response.body().toString());
//                loadResult(response);
//                pd.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<ResultDataModel> call, Throwable t) {
//                pd.dismiss();
//                Log.e(TAG, "onFailure: getResult " + t.toString());
//            }
//        });
//
//    }
//    private void loadResult(Response<ResultDataModel> response) {
//        if (response.body().getCode().contentEquals(Constant.CODE_SUCCESS)) {
//            RecyclerView rcvStudentsProfile = rootView.findViewById(R.id.rcvStudentsProfile);
//            rcvStudentsProfile.setLayoutManager(new LinearLayoutManager(getContext()));
//            rcvStudentsProfile.setAdapter(new Top10StudentsAdapter(getContext(), ));
//        }
//    }
}