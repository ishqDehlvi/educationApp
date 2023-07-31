package com.mp.neetjeeiitprep.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.github.guilhe.views.CircularProgressView;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.dataModel.HistoryListDataModel;
import com.mp.neetjeeiitprep.interfaces.OnSubjectClickListener;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<HistoryListDataModel.Result> arrayList;
    private int iconId;
    private OnSubjectClickListener onSubjectClickListener;
    private String TAG = "HistoryAdapterTAG";

    public HistoryAdapter(Context context, ArrayList<HistoryListDataModel.Result> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

        onSubjectClickListener = (OnSubjectClickListener) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_cell, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.historyHead.setText(arrayList.get(position).getTitle());
        holder.hisQattempted.setText(arrayList.get(position).getAttempAnswer() + "/"+arrayList.get(position).getTotalQuestion());
        holder.hisRight.setText(arrayList.get(position).getTotalCorrectAnswer());
        holder.hisAttemptDate.setText(arrayList.get(position).getAttempDate());
        holder.hisProgressPercent.setText(String.format("%.2f", Double.valueOf(arrayList.get(position).getPercentage())));
        holder.circularProgressView.setProgress(Float.valueOf(arrayList.get(position).getPercentage()));
        holder.cardHlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle navigationData=new Bundle();
                navigationData.putString(Constant.FROM_WHERE,Constant.FROM_HISTORY);
                navigationData.putString(Constant.SUB_CATEGORY_ID,arrayList.get(position).getSubcatId());
                navigationData.putString(Constant.SUB_CATEGORY_NAME,arrayList.get(position).getSubcatName());
                navigationData.putString(Constant.SELECTED_TYPE_ID,arrayList.get(position).getTypeId());
                navigationData.putString(Constant.SELECTED_TYPE_NAME,arrayList.get(position).getTypeName());
                navigationData.putString(Constant.LAST_QUESTION_ID,arrayList.get(position).getLastId());
                navigationData.putString(Constant.TOTAL_ATTEMPTED_QUESTION,arrayList.get(position).getAttempAnswer());

                Navigation.findNavController(v).navigate(R.id.action_historyFragment_to_mcqTestFragment,navigationData);
            }
        });
    }

    @Override
    public int getItemCount() {
        //return arrayList.size();
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardHlRoot;
        CircularProgressView circularProgressView;
        TextView historyHead, hisQattempted, hisRight, hisAttemptDate, hisProgressPercent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            historyHead = itemView.findViewById(R.id.historyHead);
            hisQattempted = itemView.findViewById(R.id.hisQattempted);
            hisRight = itemView.findViewById(R.id.hisRight);
            hisAttemptDate = itemView.findViewById(R.id.hisAttemptDate);
            hisProgressPercent = itemView.findViewById(R.id.hisProgressPercent);
            cardHlRoot = itemView.findViewById(R.id.cardHlRoot);

            circularProgressView = itemView.findViewById(R.id.circularProgressView);
        }
    }


}
