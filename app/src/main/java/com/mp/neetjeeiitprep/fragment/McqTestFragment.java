package com.mp.neetjeeiitprep.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mp.neetjeeiitprep.AppUtils.AppUtils;
import com.mp.neetjeeiitprep.AppUtils.Pref;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.CustomWebView.ScrollSensitiveWebView;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.activity.MainActivity;
import com.mp.neetjeeiitprep.dataModel.QuestionDataModel;
import com.mp.neetjeeiitprep.dataModel.QuestionListDataModel;
import com.mp.neetjeeiitprep.network.ApiClient;
import com.mp.neetjeeiitprep.network.WebApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mp.neetjeeiitprep.Constant.IMAGE_URL;


public class McqTestFragment extends Fragment {

    private View root;
    private TextView tvQno, tvQTitle, tvExplain, tvMTSubjectHead;
    private RadioButton rdbOpt1, rdbOpt2, rdbOpt3, rdbOpt4;
    private RadioGroup rdgOptions;
    private MaterialButton btnPrev, btnNext, btMTSubmit;
    private SwitchCompat swNightMode;
    private View mtBar1, mtBar2, mtBar3;
    private ConstraintLayout clMcqRoot;
    private RelativeLayout rlMarkSection, rlTimeSection;
    private TextView tvPM, tvPMHead, tvNMHead, tvNM, tvTTHead, tvTT, tvTLHead, tvTL;
   // private NestedScrollView svQimgSec;
    private ScrollSensitiveWebView webViewForImg;
   // private ImageView imgQ;
    private WebApi apiClient;
    private int selectedAnswer;
    private Pref pref;
    private String subCatId, typeId, lastAnsweredQuestionId;
    private int pageNo = 1;
    private ProgressDialog pd;
    private int EXAM_START = 1, EXAM_END = 0;
    private long startTime, timeLeft;
    private CountDownTimer timer = null;
    private int interval;
    private boolean isRunning, isQuestionAvailable;
    private int totalPage = 1;
    private String fromWhare;
    private String whechStandard;
    private String whechExam;
    private String loginUsing;
    private ProgressBar pbQimg;
    private String TAG = "McqTestFragmentTAG";
    private String imgBaseUrl = IMAGE_URL;
    //private TextView btnPrev, btnNext;

    //  private ArrayList<QuestionDataModel> questionItem;
    //  private ArrayList<SingleQuestionSetDataModel> singleQuestionSet;
    private int selectedQuestion = 0;
    private int Questionno=0;

    public McqTestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_mcq_test, container, false);

        initialize();

        return root;
    }

    private void initialize() {
        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        pbQimg = root.findViewById(R.id.pbQimg);
        pref = new Pref(getContext());
        apiClient = ApiClient.getApiInstance().create(WebApi.class);
        fromWhare = getArguments().getString(Constant.FROM_WHERE);
//        whechStandard = getArguments().getString(Constant.WHICH_STANDARD);
//        whechExam = getArguments().getString(Constant.WHICH_EXAM);
        loginUsing = pref.getString(Constant.LOGIN_USING);


        tvQno = root.findViewById(R.id.tvQno);
        tvQTitle = root.findViewById(R.id.tvQTitle);
        tvMTSubjectHead = root.findViewById(R.id.tvMTSubjectHead);
        tvExplain = root.findViewById(R.id.tvExplain);
        rdgOptions = root.findViewById(R.id.rdgOptions);
        rdbOpt1 = root.findViewById(R.id.rdbOpt1);
        rdbOpt2 = root.findViewById(R.id.rdbOpt2);
        rdbOpt3 = root.findViewById(R.id.rdbOpt3);
        rdbOpt4 = root.findViewById(R.id.rdbOpt4);
        rdgOptions = root.findViewById(R.id.rdgOptions);

        btnNext = root.findViewById(R.id.btnNext);
        btnPrev = root.findViewById(R.id.btnPrev);
        btMTSubmit = root.findViewById(R.id.btMTSubmit);

        mtBar1 = root.findViewById(R.id.mtBar1);
        mtBar2 = root.findViewById(R.id.mtBar2);
        mtBar3 = root.findViewById(R.id.mtBar3);
        swNightMode = root.findViewById(R.id.swNightMode);
        clMcqRoot = root.findViewById(R.id.clMcqRoot);
        rlMarkSection = root.findViewById(R.id.rlMarkSection);
        rlTimeSection = root.findViewById(R.id.rlTimeSection);
        tvPMHead = root.findViewById(R.id.tvPMHead);
        tvPM = root.findViewById(R.id.tvPM);
        tvNMHead = root.findViewById(R.id.tvNMHead);
        tvNM = root.findViewById(R.id.tvNM);
        tvTTHead = root.findViewById(R.id.tvTTHead);
        tvTT = root.findViewById(R.id.tvTT);
        tvTLHead = root.findViewById(R.id.tvTLHead);
        tvTL = root.findViewById(R.id.tvTL);

        resetRdbTags();

        webViewForImg=root.findViewById(R.id.webViewForImg);
        webViewForImg.getSettings().setLoadsImagesAutomatically(true);
        webViewForImg.getSettings().setJavaScriptEnabled(true);
        webViewForImg.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        //svQimgSec = root.findViewById(R.id.svQimgSec);
        //imgQ = root.findViewById(R.id.imgQ);


        ((MainActivity) getActivity()).questionItem = new ArrayList<>();


        String catId = getArguments().getString(Constant.CATEGORY_ID);
        String catName = getArguments().getString(Constant.CATEGORY_NAME);
        subCatId = getArguments().getString(Constant.SUB_CATEGORY_ID);
        String subCatName = getArguments().getString(Constant.SUB_CATEGORY_NAME);
        typeId = getArguments().getString(Constant.SELECTED_TYPE_ID);
        String typeName = getArguments().getString(Constant.SELECTED_TYPE_NAME);

        if(getArguments().getString(Constant.TOTAL_ATTEMPTED_QUESTION)!=null){
            Questionno=Integer.parseInt(getArguments().getString(Constant.TOTAL_ATTEMPTED_QUESTION));
        }

        //====TO CHECK IS IT CLASS 11TH OR CLASS 12TH====
//        if (fromWhare.contentEquals(Constant.FROM_NEETJEE_EXAM)) {
//
//            if (whechStandard.contentEquals(Constant.FROM_CLASS_11)) {
////            Toast.makeText(getContext(),"Class 11 students", Toast.LENGTH_SHORT).show();
//                Log.d("Which standard", "Class 11th");
//            } else if (whechStandard.contentEquals(Constant.FROM_CLASS_12)) {
////            Toast.makeText(getContext(),"Class 12 students", Toast.LENGTH_SHORT).show();
//                Log.d("Which standard", "Class 12th");
//            } else if (whechStandard.contentEquals(Constant.WHICH_STANDARD)) {
//                Log.d("Which standard", "No one");
//            } else {
////            Toast.makeText(getContext(),"Don't know", Toast.LENGTH_SHORT).show();
//                Log.d("Which standard", "Null value");
//            }
//            //====TO CHECK IS IT NEET, JEE OR BOTH====
//            if (whechExam.contentEquals(Constant.FROM_NEET)) {
//                Log.d("Which exam", "NEET EXAMS");
//            } else if (whechExam.contentEquals(Constant.FROM_JEE)) {
//                Log.d("Which exam", "JEE EXAMS");
//            } else if (whechExam.contentEquals(Constant.WHICH_EXAM)) {
//                Log.d("Which exam", "No one");
//            } else {
//                Log.d("Which exam", "BOTH EXAMS");
//            }
//        }
        if (fromWhare.contentEquals(Constant.FROM_MOCK_TEST)) {
            tvPM.setText(getArguments().getString(Constant.MOCKTEST_POSITIVE_MARK));
            tvNM.setText(getArguments().getString(Constant.MOCKTEST_NEGATIVE_MARK));
            tvTT.setText(getArguments().getString(Constant.MOCKTEST_DURATION) + " minutes");
            startTime = TimeUnit.MINUTES.toMillis(Long.valueOf(getArguments().getString(Constant.MOCKTEST_DURATION)));

            tvMTSubjectHead.setText(subCatName + " - (" + typeName + ")");
            if (AppUtils.isOnline(getContext())) {
                getQuestionList(subCatId, typeId);
            } else {
                Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
            }
        } else if (fromWhare.contentEquals(Constant.FROM_STUDY_METERIALS) ||
                (fromWhare.contentEquals(Constant.FROM_PREV_YEARS)) ||
                (fromWhare.contentEquals(Constant.FROM_HISTORY))) {

            if (loginUsing.contentEquals(Constant.LOGIN_TYPE_GUEST)) {
                btMTSubmit.setVisibility(View.GONE);
            }
            btMTSubmit.setText(R.string.btnMcqSaveProgress);
            tvMTSubjectHead.setText(subCatName + " - (" + typeName + ")");
            rlMarkSection.setVisibility(View.GONE);
            rlTimeSection.setVisibility(View.GONE);
            if (AppUtils.isOnline(getContext())) {
                if ((fromWhare.contentEquals(Constant.FROM_HISTORY))) {
                    lastAnsweredQuestionId = getArguments().getString(Constant.LAST_QUESTION_ID);
                    getQuestionListFromHistory(subCatId, typeId);
                } else {
                    getStudyAndPreviousQuestionList(subCatId, typeId);
                }
            } else {
                Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
            }
        } /*else {
            if (AppUtils.isOnline(getContext())) {
                lastAnsweredQuestionId = getArguments().getString(Constant.LAST_QUESTION_ID);
                getQuestionListFromHistory(subCatId, typeId);
            } else {
                Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
            }
        }*/


        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetRdbTags();
                if (((MainActivity) getActivity()).questionItem.size() > 0) {
                    if (selectedQuestion > 0) {
                        selectedQuestion--;
                        if (fromWhare.contentEquals(Constant.FROM_MOCK_TEST)) {
                            if (!isRunning) {
                                StartTimer();
                            }
                        }
                        loadQuestion();
                    } else {
                        Toast.makeText(getContext(), "No more Question", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnNext = root.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetRdbTags();
                if (pbQimg.isIndeterminate())
                    pbQimg.setVisibility(View.GONE);

                if (((MainActivity) getActivity()).questionItem.size() > 0) {
                    if (selectedQuestion < ((MainActivity) getActivity()).questionItem.size() - 1) {
                        selectedQuestion++;
                        Questionno++;

                        if (fromWhare.contentEquals(Constant.FROM_MOCK_TEST)) {
                            if (!isRunning) {
                                StartTimer();
                            }
                        }
                        loadQuestion();
                    } else {
                        if (fromWhare.contentEquals(Constant.FROM_STUDY_METERIALS) ||
                                (fromWhare.contentEquals(Constant.FROM_PREV_YEARS))) {
                            if (totalPage >= pageNo) {
                                if (AppUtils.isOnline(getContext())) {
                                    pageNo = pageNo + 1;
                                    getStudyAndPreviousQuestionList(subCatId, typeId);
                                } else {
                                    Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else if (fromWhare.contentEquals(Constant.FROM_HISTORY)) {
                            if (totalPage >= pageNo) {
                                if (AppUtils.isOnline(getContext())) {
                                    getQuestionListFromHistory(subCatId, typeId);
                                } else {
                                    Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(getContext(), "No more Question", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        btMTSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), " Under Development ", Toast.LENGTH_SHORT).show();
                //finalSubmitAlert(v);
                if (isQuestionAvailable) {
                    if (btMTSubmit.getText().toString().contentEquals(getResources().getString(R.string.btnMcqSaveProgress))) {
                        submitProgress(subCatId, typeId);
                    } else {
                        startOrEndExamDialog(EXAM_END);
                    }
                } else {
                    Toast.makeText(getContext(), "Questions Not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rdgOptions.setOnCheckedChangeListener(rdgOptionCheckedChangeListener);

        swNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setNightMode(true);
                } else {
                    setNightMode(false);
                }
            }
        });
    }

    private void resetRdbTags() {
        rdbOpt1.setTag("");
        rdbOpt2.setTag("");
        rdbOpt3.setTag("");
        rdbOpt4.setTag("");
    }

    private void getQuestionList(String subId, String typeId) {
        pd.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("sub_id", subId);
        params.put("type_id", typeId);

        Log.d(TAG, "getQuestionList: " + params.toString());
        Call<QuestionListDataModel> callApi = apiClient.getMockTestQuestions(params);
        callApi.enqueue(new Callback<QuestionListDataModel>() {
            @Override
            public void onResponse(Call<QuestionListDataModel> call, Response<QuestionListDataModel> response) {
                //   Log.d(TAG, "onResponse: "+new Gson().toJson(response));
                pareseQuestionList(response);
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<QuestionListDataModel> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });

    }

    private void pareseQuestionList(Response<QuestionListDataModel> response) {
        QuestionListDataModel questionListDataModel = response.body();
        if (questionListDataModel.getCode().contentEquals(Constant.CODE_SUCCESS)) {
            isQuestionAvailable = true;
            //imgBaseUrl = "http://neetjeeiitprep.com/neet/old/uploads/question/";//response.body().getImageUrl().toString().replace("\\","");
            if (!fromWhare.contentEquals(Constant.FROM_HISTORY)) {
                totalPage = response.body().getTotalPage();
            }
            ((MainActivity) getActivity()).questionItem.addAll(response.body().getQuestions());
            loadQuestion();
            if (fromWhare.contentEquals(Constant.FROM_MOCK_TEST)) {
                startOrEndExamDialog(EXAM_START);
            }
        }
    }

    private void getStudyAndPreviousQuestionList(String subId, String typeId) {
        pd.show();

        String userId = pref.getString(Constant.USER_ID);
        if (loginUsing.contentEquals(Constant.LOGIN_TYPE_GUEST))
            userId = "0";

        HashMap<String, String> params = new HashMap<>();
        params.put("sub_id", subId);
        params.put("type_id", typeId);
        params.put("page_no", String.valueOf(pageNo));
        params.put("user_id", userId);

        Log.d(TAG, "getQuestionList: " + params.toString());
        Call<JsonElement> callApi = apiClient.getStudyAndPreviousQuestions(params);
        callApi.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //   Log.d(TAG, "onResponse: "+new Gson().toJson(response));
                pareseStudyAndPreviousQuestionList(response);
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });

    }

    private void pareseStudyAndPreviousQuestionList(Response<JsonElement> response) {

        // QuestionListDataModel questionListDataModel = response.body();
        if (response.body().getAsJsonObject().get("code").getAsString().contentEquals(Constant.CODE_SUCCESS)) {
            isQuestionAvailable = true;
            //  Log.d(TAG, "pareseStudyAndPreviousQuestionList: "+(response.body().getAsJsonObject().has("last_id")));
            //if (!response.body().getAsJsonObject().has("last_id")) {
            if (response.body().getAsJsonObject().get("last_id").getAsString().contentEquals("-1")) {
                QuestionListDataModel qDataModel = new Gson().fromJson(response.body().getAsJsonObject(), QuestionListDataModel.class);
                //  imgBaseUrl = "http://neetjeeiitprep.com/neet/old/uploads/question/";//response.body().getImageUrl().toString().replace("\\","");
                if (!fromWhare.contentEquals(Constant.FROM_HISTORY)) {
                    totalPage = qDataModel.getTotalPage();
                }
                ((MainActivity) getActivity()).questionItem.addAll(qDataModel.getQuestions());
                loadQuestion();
                if (fromWhare.contentEquals(Constant.FROM_MOCK_TEST)) {
                    startOrEndExamDialog(EXAM_START);
                }
            } else {
                if (AppUtils.isOnline(getContext())) {
                    fromWhare = Constant.FROM_HISTORY;
                    lastAnsweredQuestionId = response.body().getAsJsonObject().get("last_id").getAsString();
                    getQuestionListFromHistory(subCatId, typeId);
                } else {
                    Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(getContext(), response.body().getAsJsonObject().get("message").getAsString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getQuestionListFromHistory(String subId, String typeId) {
        pd.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("sub_id", subId);
        params.put("type_id", typeId);
        params.put("last_id", lastAnsweredQuestionId);

        Log.d(TAG, "getQuestionListFromHistory: " + params.toString());
        Call<QuestionListDataModel> callApi = apiClient.getQuestionListForHistory(params);
        callApi.enqueue(new Callback<QuestionListDataModel>() {
            @Override
            public void onResponse(Call<QuestionListDataModel> call, Response<QuestionListDataModel> response) {
                //   Log.d(TAG, "onResponse: "+new Gson().toJson(response));
                pareseQuestionList(response);
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<QuestionListDataModel> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure: getQuestionListFromHistory " + t.toString());
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void loadQuestion() {

        QuestionDataModel item = ((MainActivity) getActivity()).questionItem.get(selectedQuestion);

        tvQno.setText("Question " + (Questionno + 1));
        if (item.getQuestionImage().contentEquals("noimage")) {
           // if (tvQTitle.getVisibility() == View.GONE) {
                tvQTitle.setVisibility(View.VISIBLE);
               // svQimgSec.setVisibility(View.GONE);
            webViewForImg.setVisibility(View.GONE);
           // }
            if(item.getQuestion()!=null) {
                String trimedText=HtmlCompat.fromHtml(item.getQuestion(), HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim();
                tvQTitle.setText(trimedText);
            }else{
                tvQTitle.setText("?");
            }
            pbQimg.setVisibility(View.GONE);
        } else {
            if (fromWhare.contentEquals(Constant.FROM_MOCK_TEST)) {
                if (timer != null) {
                    timer.cancel();
                    startTime = timeLeft;
                }
            }

            Log.d(TAG, "loadQuestion: image");
            if (webViewForImg.getVisibility() == View.GONE) {
             //   svQimgSec.setVisibility(View.VISIBLE);
                webViewForImg.setVisibility(View.VISIBLE);
                //  imgQ.setVisibility(View.VISIBLE);
            }
            if (item.getQuestion_image_detail() != null && !item.getQuestion_image_detail().contentEquals("")) {
                if (tvQTitle.getVisibility() == View.GONE)
                    tvQTitle.setVisibility(View.VISIBLE);
                String trimedText=HtmlCompat.fromHtml(item.getQuestion_image_detail(), HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim();
                tvQTitle.setText(trimedText);
            } else {
                tvQTitle.setVisibility(View.GONE);
            }
            String htmlString="<html>" +
                    //"<head><style>img{ overflow-y: auto ; overflow-x: auto} </style></head>"+
                    "<body>"+
                    "<div class=\"image-box\" style=\"width: auto;height: auto;margin: 0 auto;overflow-y: auto;overflow-x: auto;\">"+
                    "<img src=\""+(imgBaseUrl + item.getQuestionImage())+"\" alt=\""+item.getQuestionImage()+"\" style=\"width: auto;height: auto; object-fit: cover;overflow-y: auto;overflow-x: auto;\" >" +
                    "</div>"+
                    "</body>" +
                    "</html>";
            webViewForImg.loadDataWithBaseURL(null, htmlString, "text/html", "utf-8", null);
            Log.d(TAG, "loadQuestion: htmlString -> "+htmlString);
            pbQimg.setVisibility(View.VISIBLE);

           /* Glide.with(getContext())
                    .load(imgBaseUrl + item.getQuestionImage())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (fromWhare.contentEquals(Constant.FROM_MOCK_TEST)) {
                                if (timer != null) {
                                    StartTimer();
                                }
                                pbQimg.setVisibility(View.GONE);
                            }
                            return false;
                        }
                    })
                    .into(imgQ);*/

        }
        tvExplain.setText("Explanation: " + HtmlCompat.fromHtml(item.getExplaination(),HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim());

        tvExplain.setVisibility(View.GONE);
        enableRdbOptions(true);
        rdgOptions.setOnCheckedChangeListener(null);
        rdgOptions.clearCheck();
        rdgOptions.setOnCheckedChangeListener(rdgOptionCheckedChangeListener);
        if (swNightMode.isChecked()) {
            rdbOpt1.setTextColor(ContextCompat.getColor(getContext(), R.color.nightModeActiveTextColor));
            rdbOpt2.setTextColor(ContextCompat.getColor(getContext(), R.color.nightModeActiveTextColor));
            rdbOpt3.setTextColor(ContextCompat.getColor(getContext(), R.color.nightModeActiveTextColor));
            rdbOpt4.setTextColor(ContextCompat.getColor(getContext(), R.color.nightModeActiveTextColor));
        } else {

            rdbOpt1.setTextColor((getResources().getColor(R.color.textColor1)));
            rdbOpt2.setTextColor((getResources().getColor(R.color.textColor1)));
            rdbOpt3.setTextColor((getResources().getColor(R.color.textColor1)));
            rdbOpt4.setTextColor((getResources().getColor(R.color.textColor1)));
        }

        if (item.getAnserList().size() == 4) {
            String regex = "\\s+$";

            String testAfterRemovedNewLine=HtmlCompat.fromHtml(item.getAnserList().get(0).getAnswer(),HtmlCompat.FROM_HTML_MODE_LEGACY).toString();
            //testAfterRemovedNewLine=testAfterRemovedNewLine.trim();
            testAfterRemovedNewLine=testAfterRemovedNewLine.replaceAll(regex, "");
            Log.d(TAG, "loadQuestion: testAfterRemovedNewLine 2 ->"+testAfterRemovedNewLine+"@");
            rdbOpt1.setText(testAfterRemovedNewLine);

            testAfterRemovedNewLine=HtmlCompat.fromHtml(item.getAnserList().get(1).getAnswer(),HtmlCompat.FROM_HTML_MODE_LEGACY).toString();
            //testAfterRemovedNewLine=testAfterRemovedNewLine.trim();
            testAfterRemovedNewLine=testAfterRemovedNewLine.replaceAll(regex, "");
            rdbOpt2.setText(testAfterRemovedNewLine);

            testAfterRemovedNewLine=HtmlCompat.fromHtml(item.getAnserList().get(2).getAnswer(),HtmlCompat.FROM_HTML_MODE_LEGACY).toString();
            //testAfterRemovedNewLine=testAfterRemovedNewLine.trim();
            testAfterRemovedNewLine=testAfterRemovedNewLine.replaceAll(regex, "");
            rdbOpt3.setText(testAfterRemovedNewLine);

            testAfterRemovedNewLine=HtmlCompat.fromHtml(item.getAnserList().get(3).getAnswer(),HtmlCompat.FROM_HTML_MODE_LEGACY).toString();
            //testAfterRemovedNewLine=testAfterRemovedNewLine.trim();
            testAfterRemovedNewLine=testAfterRemovedNewLine.replaceAll(regex, "");
            rdbOpt4.setText(testAfterRemovedNewLine);
        } else {
            Toast.makeText(getContext(), "Answers are not set", Toast.LENGTH_SHORT).show();
        }

        if (((MainActivity) getActivity()).questionItem.get(selectedQuestion).getQuestionStatus() == Constant.QUESTION_ANSWERED) {
            RadioButton rdbRightAns = root.findViewById(((MainActivity) getActivity()).questionItem.get(selectedQuestion).getSelectedRdoId());
            rdbRightAns.setChecked(true);
            enableRdbOptions(false);
        }
    }

    private void submitProgress(String subId, String typeId) {
        pd.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("sub_id", subId);
        params.put("type_id", typeId);
        params.put("user_id", pref.getString(Constant.USER_ID));
        params.put("correct_answer", getTotalCorrectAnswerCount());
        params.put("attempted_answer", getTotalAttemptedAnswerCount());
        params.put("last_id", lastAnsweredQuestionId);

        Log.d(TAG, "submitProgress: " + params.toString());
        Call<JsonElement> callApi = apiClient.saveMcqProgress(params);
        callApi.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //   Log.d(TAG, "onResponse: "+new Gson().toJson(response));
                if (response.body().getAsJsonObject().get("code").getAsString().contentEquals(Constant.CODE_SUCCESS)) {
                    String msg = response.body().getAsJsonObject().get("message").getAsString();
                    Toast.makeText(getActivity(), "Progress Saved Successfully", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: submitProgress " + msg);
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure: submitProgress" + t.toString());
            }
        });

    }

    private void enableRdbOptions(Boolean buttonStatus) {
        rdbOpt1.setEnabled(buttonStatus);
        rdbOpt2.setEnabled(buttonStatus);
        rdbOpt3.setEnabled(buttonStatus);
        rdbOpt4.setEnabled(buttonStatus);
    }

    private int getCorrectAnswer() {
        QuestionDataModel item = ((MainActivity) getActivity()).questionItem.get(selectedQuestion);
        if (item.getAnserList().get(0).getCorrect().toLowerCase().contentEquals(Constant.YES_ITS_CORRECT))
            return 1;
        if (item.getAnserList().get(1).getCorrect().toLowerCase().contentEquals(Constant.YES_ITS_CORRECT))
            return 2;
        if (item.getAnserList().get(2).getCorrect().toLowerCase().contentEquals(Constant.YES_ITS_CORRECT))
            return 3;
        if (item.getAnserList().get(3).getCorrect().toLowerCase().contentEquals(Constant.YES_ITS_CORRECT))
            return 4;
        return 0;
    }

    private void checkingQuestion(int checkedId) {
        //   if (!singleQuestionSet.get(selectedQuestion).getQuestionStatus()) {
        if (((MainActivity) getActivity()).questionItem.size() > 0) {
            lastAnsweredQuestionId = ((MainActivity) getActivity()).questionItem.get(selectedQuestion).getQuestionId();
            enableRdbOptions(false);
            RadioButton selectedRdb = root.findViewById(checkedId);
            int ans = getCorrectAnswer();
            if (selectedAnswer == ans) {
                if (!fromWhare.contentEquals(Constant.FROM_MOCK_TEST)) {
                    selectedRdb.setTextColor(getResources().getColor(R.color.rightAnswerColor));
                    selectedRdb.setTag("1");
                }
                ((MainActivity) getActivity()).questionItem.get(selectedQuestion).setSelectedRdoId(checkedId);
                ((MainActivity) getActivity()).questionItem.get(selectedQuestion).setUserAnswerCorrect(true);
                ((MainActivity) getActivity()).questionItem.get(selectedQuestion).setUserSelectedAnswerId(selectedAnswer); // its not id ,instead it's the selected option
                ((MainActivity) getActivity()).questionItem.get(selectedQuestion).setQuestionStatus(Constant.QUESTION_ANSWERED);
                Log.d("yo", "checkingQuestion: selectedQuestion" + selectedQuestion + " status" + ((MainActivity) getActivity()).questionItem.get(selectedQuestion).getQuestionStatus());
            } else if (ans != 0) {
                //tvExplain.setVisibility(View.VISIBLE);
                //selectedRdb.setTextColor(getResources().getColor(R.color.wrongAnswerColor));
                int id = getResources().getIdentifier("rdbOpt" + ans, "id", getContext().getPackageName());
                RadioButton rightAns = root.findViewById(id);
                //rightAns.setTextColor(getResources().getColor(R.color.rightAnswerColor));
                ((MainActivity) getActivity()).questionItem.get(selectedQuestion).setSelectedRdoId(checkedId);
                ((MainActivity) getActivity()).questionItem.get(selectedQuestion).setUserAnswerCorrect(false);
                ((MainActivity) getActivity()).questionItem.get(selectedQuestion).setUserSelectedAnswerId(selectedAnswer);// its not id ,instead it's the selected option
                ((MainActivity) getActivity()).questionItem.get(selectedQuestion).setQuestionStatus(Constant.QUESTION_ANSWERED);
                if (!fromWhare.contentEquals(Constant.FROM_MOCK_TEST)) {
                    tvExplain.setVisibility(View.VISIBLE);
                    rightAns.setTextColor(getResources().getColor(R.color.rightAnswerColor));
                    selectedRdb.setTextColor(getResources().getColor(R.color.wrongAnswerColor));
                    rightAns.setTag("1");
                    selectedRdb.setTag("1");
                }
                Log.d("yo", "checkingQuestion el: selectedQuestion" + selectedQuestion + " status" + ((MainActivity) getActivity()).questionItem.get(selectedQuestion).getQuestionStatus());
            } else {
                Toast.makeText(getContext(), "Correct Answer not Defined", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getTotalAttemptedAnswerCount() {
        int count = 0;
        for (int i = 0; i < ((MainActivity) getActivity()).questionItem.size(); i++) {
            if (((MainActivity) getActivity()).questionItem.get(i).getQuestionStatus() == Constant.QUESTION_ANSWERED)
                count++;
        }
        return String.valueOf(count);
    }

    private String getTotalCorrectAnswerCount() {
        int count = 0;
        for (int i = 0; i < ((MainActivity) getActivity()).questionItem.size(); i++) {
            if (((MainActivity) getActivity()).questionItem.get(i).getUserAnswerCorrect())
                count++;
        }
        return String.valueOf(count);
    }

    private RadioGroup.OnCheckedChangeListener rdgOptionCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rdbOpt1: {
                    selectedAnswer = 1;
                    checkingQuestion(checkedId);
                    break;
                }
                case R.id.rdbOpt2: {
                    selectedAnswer = 2;
                    checkingQuestion(checkedId);
                    break;
                }
                case R.id.rdbOpt3: {
                    selectedAnswer = 3;
                    checkingQuestion(checkedId);
                    break;
                }
                case R.id.rdbOpt4: {
                    selectedAnswer = 4;
                    checkingQuestion(checkedId);
                    break;
                }

            }
        }
    };

    private void loadResultPage() {
        Bundle nevigationData = getArguments();
        nevigationData.putString(Constant.TOTAL_QUESTION, String.valueOf(((MainActivity) getActivity()).questionItem.size()));
        nevigationData.putString(Constant.TOTAL_ATTEMPTED_QUESTION, getTotalAttemptedAnswerCount());
        nevigationData.putString(Constant.TOTAL_CORRECT_QUESTION, getTotalCorrectAnswerCount());
        NavHostFragment.findNavController(this).navigate(R.id.action_mcqTestFragment_to_resultFragment, nevigationData);
    }

    private void finalSubmitAlert(final View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AppDialog)
                .setTitle("Submit Test")
                .setMessage("you have answered " + getTotalAttemptedAnswerCount() + " out of " + ((MainActivity) getActivity()).questionItem.size() + ".Do you want to submit test ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //      loadResultPage(v);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void startOrEndExamDialog(final int examStatus) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.ask_signin_popup_layout);


        final TextView tvSgnPopUpHead = dialog.findViewById(R.id.tvSgnPopUpHead);
        final TextView tvSgnMsg = dialog.findViewById(R.id.tvSgnMsg);

        if (examStatus == EXAM_START) {
            tvSgnPopUpHead.setText("Start Exam!");
            tvSgnMsg.setText("you will have " + getArguments().getString(Constant.MOCKTEST_DURATION) + " minutes to complete the exam.\n Do you want to Start now ");
        } else {
            tvSgnPopUpHead.setText("Sign in to access all features");
            tvSgnMsg.setText("you have answered " + getTotalAttemptedAnswerCount() + " out of " + ((MainActivity) getActivity()).questionItem.size() + ".Do you want to submit test ?");
        }


        final MaterialButton btnCancel = dialog.findViewById(R.id.btnCancelSign);
        final MaterialButton btnProceed = dialog.findViewById(R.id.btnGotoSign);

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                if (examStatus == EXAM_START) {
                    interval = 1000;
                    // startTime = (long) 1.8e+6;
                    StartTimer();
                } else {
                    timer.cancel();
                    loadResultPage();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fromWhare.contentEquals(Constant.FROM_MOCK_TEST)) {
                    Log.d("aaa","aaaaaa");
//                    getActivity().getSupportFragmentManager().popBackStack();

                    getActivity().onBackPressed();
//                    getActivity().finish();
                }
                dialog.dismiss();
            }
        });


        dialog.show();

        /***   getting display height and width   ***/
        //Grab the window of the dialog, and change the width
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        /*****/
    }

    private void StartTimer() {
        timer = new CountDownTimer(startTime, interval) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                tvTL.setText(convertTimeForHumans(millisUntilFinished));
                //Log.d(TAG, "onTick: millisUntilFinished-> " + millisUntilFinished);
                isRunning = true;
            }

            @Override
            public void onFinish() {
                isRunning = false;
                // loadResultPage();
                Log.d(TAG, "onFinish: timer");
            }
        };
        timer.start();

    }

    private String convertTimeForHumans(long millis) {
        long minutes = millis / 1000 / 60;
        long seconds = (millis / 1000) % 60;
        return minutes + " min " + seconds + " sec";
    }

    private void setNightMode(boolean status) {

        int mColor1 = R.color.nightModeDeActiveTextColor;
        int mColor2 = R.color.pageBg;
        int mBorder = R.drawable.slightly_rounded_rectangle_border_black;
        int mBg = R.drawable.slightly_rounded_rectangle_bg;
        int mtvBg = R.drawable.bg3;
        int colorInt = getResources().getColor(mColor1);
        ColorStateList csl = ColorStateList.valueOf(colorInt);

        if (status) {

            mColor1 = R.color.nightModeActiveTextColor;
            mColor2 = R.color.nightModeActiveBgColorDark;
            mBorder = R.drawable.slightly_rounded_rectangle_border_white;
            mBg = R.drawable.slightly_rounded_rectangle__black_bg;
            mtvBg = R.color.nightModeActiveBgColorDark;
            colorInt = getResources().getColor(mColor1);
            csl = ColorStateList.valueOf(colorInt);

        }

        ((MainActivity) getActivity()).toolbar.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.tab_selector_indicator));
        ((MainActivity) getActivity()).tvCustomToolbar.setTextColor(ContextCompat.getColor(getContext(), mColor1));
        ((MainActivity) getActivity()).tvCustomToolbar.setBackground(ContextCompat.getDrawable(getContext(), mtvBg));
        mtBar1.setBackgroundColor(ContextCompat.getColor(getContext(), mColor1));
        mtBar2.setBackgroundColor(ContextCompat.getColor(getContext(), mColor1));
        mtBar3.setBackgroundColor(ContextCompat.getColor(getContext(), mColor1));
        tvMTSubjectHead.setTextColor(ContextCompat.getColor(getContext(), mColor1));
        tvPMHead.setTextColor(ContextCompat.getColor(getContext(), mColor1));
        tvPM.setTextColor(ContextCompat.getColor(getContext(), mColor1));
        tvNMHead.setTextColor(ContextCompat.getColor(getContext(), mColor1));
        tvNM.setTextColor(ContextCompat.getColor(getContext(), mColor1));
        tvTTHead.setTextColor(ContextCompat.getColor(getContext(), mColor1));
        tvTT.setTextColor(ContextCompat.getColor(getContext(), mColor1));
        tvTLHead.setTextColor(ContextCompat.getColor(getContext(), mColor1));
        tvTL.setTextColor(ContextCompat.getColor(getContext(), mColor1));

        tvQTitle.setTextColor(ContextCompat.getColor(getContext(), mColor1));
        tvQno.setTextColor(ContextCompat.getColor(getContext(), mColor1));
        tvExplain.setTextColor(ContextCompat.getColor(getContext(), mColor1));
        tvQno.setTextColor(ContextCompat.getColor(getContext(), mColor1));

        if(rdbOpt1.getTag().toString().contentEquals(""))
        rdbOpt1.setTextColor(ContextCompat.getColor(getContext(), mColor1));
        if(rdbOpt2.getTag().toString().contentEquals(""))
        rdbOpt2.setTextColor(ContextCompat.getColor(getContext(), mColor1));
        if(rdbOpt3.getTag().toString().contentEquals(""))
        rdbOpt3.setTextColor(ContextCompat.getColor(getContext(), mColor1));
        if(rdbOpt4.getTag().toString().contentEquals(""))
        rdbOpt4.setTextColor(ContextCompat.getColor(getContext(), mColor1));
        rdbOpt1.setButtonTintList(csl);
        rdbOpt2.setButtonTintList(csl);
        rdbOpt3.setButtonTintList(csl);
        rdbOpt4.setButtonTintList(csl);

        btnPrev.setTextColor(ContextCompat.getColor(getContext(), mColor1));
        btnNext.setTextColor(ContextCompat.getColor(getContext(), mColor1));
        btMTSubmit.setTextColor(ContextCompat.getColor(getContext(), mColor1));
        btMTSubmit.setStrokeColor(csl);
        swNightMode.setTextColor(ContextCompat.getColor(getContext(), mColor1));

        clMcqRoot.setBackgroundColor(ContextCompat.getColor(getContext(), mColor2));
        rlMarkSection.setBackground(getResources().getDrawable(mBorder));
        rlTimeSection.setBackground(getResources().getDrawable(mBorder));
        tvQTitle.setBackground(getResources().getDrawable(mBg));
        tvExplain.setBackground(getResources().getDrawable(mBg));
        rdgOptions.setBackground(getResources().getDrawable(mBg));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity) getActivity()).toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.nightModeDeActiveTextColor), PorterDuff.Mode.SRC_ATOP);
        ((MainActivity) getActivity()).tvCustomToolbar.setTextColor(ContextCompat.getColor(getContext(), R.color.nightModeDeActiveTextColor));
        ((MainActivity) getActivity()).tvCustomToolbar.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg3));
        /*if (!fromWhare.contentEquals(Constant.FROM_MOCK_TEST)) {
            submitProgress(subCatId, typeId);
        }*/
        if (isRunning) {
            timer.cancel();
        }
        //  ((MainActivity) getActivity()).questionItem.clear();
    }
}
