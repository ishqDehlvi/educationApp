package com.mp.neetjeeiitprep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.guilhe.views.CircularProgressView;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.dataModel.ResultListDataModel;
import com.mp.neetjeeiitprep.interfaces.OnSubjectClickListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<ResultListDataModel.Result> arrayList;
    private int iconId;
    private OnSubjectClickListener onSubjectClickListener;
    private String TAG = "ResultListAdapterTAG";

    public ResultListAdapter(Context context, ArrayList<ResultListDataModel.Result> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

        onSubjectClickListener = (OnSubjectClickListener) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.previous_result_cell, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        String resHead=arrayList.get(position).getParentCat()+"("+arrayList.get(position).getCname()+" - "+arrayList.get(position).getCname()+")";

        holder.resultHead.setText(resHead);
        holder.resQAttempted.setText(arrayList.get(position).getAttemptedAnswer() + "/"+arrayList.get(position).getTotalQuestion());
        holder.resRight.setText(arrayList.get(position).getTotalCorrectAnswer());
        holder.ResMarksObtained.setText(arrayList.get(position).getMarksObtained());
        holder.ResAttemptDate.setText(arrayList.get(position).getAttempDate());
        holder.resProgressPercent.setText(roundFloatTo2Descimal(arrayList.get(position).getPercentage()));
        holder.resCircularProgressView.setProgress(Float.valueOf(arrayList.get(position).getPercentage()));
     /*   holder.cardHlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle navigationData=new Bundle();
                navigationData.putString(Constant.FROM_WHERE,Constant.FROM_HISTORY);
                navigationData.putString(Constant.SUB_CATEGORY_ID,arrayList.get(position).getSubcatId());
                navigationData.putString(Constant.SUB_CATEGORY_NAME,arrayList.get(position).getSubcatName());
                navigationData.putString(Constant.SELECTED_TYPE_ID,arrayList.get(position).getTypeId());
                navigationData.putString(Constant.SELECTED_TYPE_NAME,arrayList.get(position).getTypeName());
                navigationData.putString(Constant.LAST_QUESTION_ID,arrayList.get(position).getLastId());
                Navigation.findNavController(v).navigate(R.id.action_historyFragment_to_mcqTestFragment,navigationData);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardResRoot;
        CircularProgressView resCircularProgressView;
        TextView resultHead, resQAttempted, resRight, ResMarksObtained, ResAttemptDate,resProgressPercent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            resultHead = itemView.findViewById(R.id.resultHead);
            resQAttempted = itemView.findViewById(R.id.resQAttempted);
            resRight = itemView.findViewById(R.id.resRight);
            ResMarksObtained = itemView.findViewById(R.id.ResMarksObtained);
            ResAttemptDate = itemView.findViewById(R.id.ResAttemptDate);
            cardResRoot = itemView.findViewById(R.id.cardResRoot);
            resProgressPercent = itemView.findViewById(R.id.resProgressPercent);

            resCircularProgressView = itemView.findViewById(R.id.resCircularProgressView);
        }
    }

    private String roundFloatTo2Descimal(String value){

        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(Float.valueOf(value));
    }

}
