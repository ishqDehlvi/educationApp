package com.mp.neetjeeiitprep.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.guilhe.views.CircularProgressView;
import com.mp.neetjeeiitprep.AppUtils.Pref;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.activity.MainActivity;
import com.mp.neetjeeiitprep.dataModel.QuestionDataModel;
import com.mp.neetjeeiitprep.dataModel.ResultDataModel;
import com.mp.neetjeeiitprep.interfaces.OnSubjectClickListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.mp.neetjeeiitprep.Constant.IMAGE_URL;

public class ResultMcqAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<QuestionDataModel> arrayList;
    private OnSubjectClickListener onSubjectClickListener;
    private Bundle navigationData;
    private Pref pref;
    ResultDataModel result;
    String imgBaseUrl = IMAGE_URL;
    private String TAG = "ResultMcqAdapter";

    public ResultMcqAdapter(Context context, ArrayList<QuestionDataModel> arrayList, ResultDataModel result, Bundle navigationData) {
        this.context = context;
        this.arrayList = arrayList;
        this.navigationData = navigationData;
        this.result = result;
        onSubjectClickListener = (OnSubjectClickListener) context;
        pref = new Pref(context);
    }

    @Override
    public int getItemViewType(int position) {
        return position;//super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.result_chart_cell, parent, false);
            return new MyViewHolder1(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.result_mcq_cell, parent, false);
            return new MyViewHolder2(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder mainholder, int position) {

        if (mainholder.getItemViewType() == 0) {
            MyViewHolder1 holder = (MyViewHolder1) mainholder;
            holder.circularProgressView.setProgress(Float.valueOf(result.getResultsData().getPercentage()));
            holder.hisProgressPercent.setText(roundFloatTo2Descimal(result.getResultsData().getPercentage()) + "%");
            holder.tvTotalAttempt.setText(result.getResultsData().getAttemptedAnswer());
            int unAttampt = Integer.parseInt(result.getResultsData().getTotalQuestion()) - Integer.parseInt(result.getResultsData().getAttemptedAnswer());
            holder.tvUnAttempt.setText(unAttampt + "");
            holder.tvRightAns.setText(result.getResultsData().getTotalCorrectAnswer());
            int wrongAns = Integer.parseInt(result.getResultsData().getAttemptedAnswer()) - Integer.parseInt(result.getResultsData().getTotalCorrectAnswer());
            holder.tvWrongAns.setText(wrongAns + "");
            holder.tvTotalMarks.setText(result.getResultsData().getMarksObtained());
            holder.tvNegativeMark.setText(result.getResultsData().getNegativeMarks());
            //  holder.tvRemarks.setText(result.getResults());
        } else {

            MyViewHolder2 holder = (MyViewHolder2) mainholder;
            loadQuestion(holder);

        }


    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + arrayList.size());
        return (arrayList.size() + 1);
    }

    public class MyViewHolder1 extends RecyclerView.ViewHolder {

        TextView hisProgressPercent, tvUnAttempt, tvTotalAttempt, tvRightAns, tvWrongAns, tvTotalMarks, tvNegativeMark;
        CircularProgressView circularProgressView;


        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);

            circularProgressView = itemView.findViewById(R.id.circularProgressView);

            hisProgressPercent = itemView.findViewById(R.id.hisProgressPercent);
            tvUnAttempt = itemView.findViewById(R.id.tvUnAttempt);
            tvTotalAttempt = itemView.findViewById(R.id.tvTotalAttempt);
            tvRightAns = itemView.findViewById(R.id.tvRightAns);
            tvWrongAns = itemView.findViewById(R.id.tvWrongAns);
            tvTotalMarks = itemView.findViewById(R.id.tvTotalMarks);
            tvNegativeMark = itemView.findViewById(R.id.tvNegativeMark);
            //   tvRemarks = itemView.findViewById(R.id.tvRemarks);
        }
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {

        TextView tvRQno, tvRQTitle, tvRExplain;
        RadioButton rdbROpt1, rdbROpt2, rdbROpt3, rdbROpt4;
        RadioGroup rdgROptions;
        NestedScrollView svQimgSec;
        ImageView imgQ;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);

            tvRQno = itemView.findViewById(R.id.tvRQno);
            tvRQTitle = itemView.findViewById(R.id.tvRQTitle);
            tvRExplain = itemView.findViewById(R.id.tvRExplain);

            svQimgSec = itemView.findViewById(R.id.svQimgSec);
            imgQ = itemView.findViewById(R.id.imgQ);

            rdgROptions = itemView.findViewById(R.id.rdgROptions);
            rdbROpt1 = itemView.findViewById(R.id.rdbROpt1);
            rdbROpt2 = itemView.findViewById(R.id.rdbROpt2);
            rdbROpt3 = itemView.findViewById(R.id.rdbROpt3);
            rdbROpt4 = itemView.findViewById(R.id.rdbROpt4);


        }
    }

    private void loadQuestion(MyViewHolder2 holder) {
        QuestionDataModel item = arrayList.get(holder.getAdapterPosition() - 1);
        holder.tvRQno.setText("Question " + holder.getAdapterPosition());

        if(item.getQuestion()!=null) {
            String trimedText=HtmlCompat.fromHtml(item.getQuestion(),HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim();
            holder.tvRQTitle.setText(trimedText);//arrayList.get(holder.getAdapterPosition()).getQuestion());
        }else{
            holder.tvRQTitle.setText("?");
        }

        if (item.getQuestionImage().contentEquals("noimage")) {
            if (holder.tvRQTitle.getVisibility() == View.GONE) {
                holder.tvRQTitle.setVisibility(View.VISIBLE);
                holder.svQimgSec.setVisibility(View.GONE);
            }
            if(item.getQuestion()!=null) {
                String trimedText=HtmlCompat.fromHtml(item.getQuestion(),HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim();
                holder.tvRQTitle.setText(trimedText);
            }else{
                holder.tvRQTitle.setText("?");
            }
        } else {
            Log.d(TAG, "loadQuestion: image");
            if (holder.svQimgSec.getVisibility() == View.GONE) {
                holder.svQimgSec.setVisibility(View.VISIBLE);
                //holder.tvRQTitle.setVisibility(View.GONE);
                //  imgQ.setVisibility(View.VISIBLE);
            }
            if (item.getQuestion_image_detail() != null && !item.getQuestion_image_detail().contentEquals("")) {
                if (holder.tvRQTitle.getVisibility() == View.GONE)
                    holder.tvRQTitle.setVisibility(View.VISIBLE);
                String trimedText=HtmlCompat.fromHtml(item.getQuestion_image_detail(),HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim();
                holder.tvRQTitle.setText(trimedText);
            } else {
                holder.tvRQTitle.setVisibility(View.GONE);
            }

            Glide.with(context)
                    .load(imgBaseUrl + item.getQuestionImage())
                    .into(holder.imgQ);
        }
        if (!item.getExplaination().isEmpty())
            holder.tvRExplain.setText("Explanation: " + HtmlCompat.fromHtml(item.getExplaination(),HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim());
        else
            holder.tvRExplain.setText("Explanation: NA");

        if (item.getAnserList().size() == 4) {
            String trimedText = HtmlCompat.fromHtml(item.getAnserList().get(0).getAnswer(), HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim();
            holder.rdbROpt1.setText(trimedText);
            trimedText = HtmlCompat.fromHtml(item.getAnserList().get(1).getAnswer(), HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim();
            holder.rdbROpt2.setText(trimedText);
            trimedText = HtmlCompat.fromHtml(item.getAnserList().get(2).getAnswer(), HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim();
            holder.rdbROpt3.setText(trimedText);
            trimedText = HtmlCompat.fromHtml(item.getAnserList().get(3).getAnswer(), HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim();
            holder.rdbROpt4.setText(trimedText);
        }
            int ans = getCorrectAnswer(item);

        /*** answer chk and  wrong ans color set ***/

        switch (item.getUserSelectedAnswerId()) {
            case 1: {
                holder.rdbROpt1.setChecked(true);
                if (!(item.getUserSelectedAnswerId() == ans)) {
                    holder.rdbROpt1.setTextColor(context.getResources().getColor(R.color.wrongAnswerColor));
                }
                break;
            }
            case 2: {
                holder.rdbROpt2.setChecked(true);
                if (!(item.getUserSelectedAnswerId() == ans)) {
                    holder.rdbROpt2.setTextColor(context.getResources().getColor(R.color.wrongAnswerColor));
                }
                break;
            }
            case 3: {
                holder.rdbROpt3.setChecked(true);
                if (!(item.getUserSelectedAnswerId() == ans)) {
                    holder.rdbROpt3.setTextColor(context.getResources().getColor(R.color.wrongAnswerColor));
                }
                break;
            }
            case 4: {
                holder.rdbROpt4.setChecked(true);
                if (!(item.getUserSelectedAnswerId() == ans)) {
                    holder.rdbROpt4.setTextColor(context.getResources().getColor(R.color.wrongAnswerColor));
                }
                break;
            }
        }

        /***/

        /*** right ans color set ***/
        switch (ans) {
            case 1:
                holder.rdbROpt1.setTextColor(context.getResources().getColor(R.color.rightAnswerColor));
                break;
            case 2:
                holder.rdbROpt2.setTextColor(context.getResources().getColor(R.color.rightAnswerColor));
                break;
            case 3:
                holder.rdbROpt3.setTextColor(context.getResources().getColor(R.color.rightAnswerColor));
                break;
            case 4:
                holder.rdbROpt4.setTextColor(context.getResources().getColor(R.color.rightAnswerColor));
                break;
        }

        /***/

    }

    private int getCorrectAnswer(QuestionDataModel item) {
        //QuestionDataModel item = ((MainActivity) getActivity()).questionItem.get(selectedQuestion);
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

    private String roundFloatTo2Descimal(String value){

        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(Float.valueOf(value));
    }


}
