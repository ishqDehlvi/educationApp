package com.mp.neetjeeiitprep.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.guilhe.views.CircularProgressView;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.dataModel.Top10StudentsDataModel;

import java.util.ArrayList;

public class Top10StudentsAdapter extends RecyclerView.Adapter<Top10StudentsAdapter.MyViewHolder> {



    private ArrayList<Top10StudentsDataModel> dataholder;

    public Top10StudentsAdapter( ArrayList<Top10StudentsDataModel> dataholder){

        this.dataholder = dataholder;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_top10_student_cell_nav, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.resCircularProgressView.setProgress(dataholder.get(position).getCircularProgress());
        holder.tvStudentsName.setText(dataholder.get(position).getName());
        holder.resultHead.setText(dataholder.get(position).getResultHead());
        holder.ResQAttemptedHead.setText(dataholder.get(position).getQaAttemptedHead());
        holder.resQAttempted.setText(dataholder.get(position).getQaAttempted());
        holder.ResRightAnsHead.setText(dataholder.get(position).getAnswerHead());
        holder.resRight.setText(dataholder.get(position).getAnswer());
        holder.ResMarksObtainedHead.setText(dataholder.get(position).getMarksHead());
        holder.ResMarksObtained.setText(dataholder.get(position).getMarks());
        holder.ResAttemptDateHead.setText(dataholder.get(position).getDateHead());
        holder.ResAttemptDate.setText(dataholder.get(position).getDate());
        holder.resProgressPercent.setText(dataholder.get(position).getProgressPercent());




//        String resHead=arrayList.get(position).getParentCat()+"("+arrayList.get(position).getCname()+" - "+arrayList.get(position).getCname()+")";
//
//        holder.tvStudentsName.setText("hello world!");
//        holder.resultHead.setText(resHead);
//        holder.resQAttempted.setText(arrayList.get(position).getAttemptedAnswer() + "/"+arrayList.get(position).getTotalQuestion());
//        holder.resRight.setText(arrayList.get(position).getTotalCorrectAnswer());
//        holder.ResMarksObtained.setText(arrayList.get(position).getMarksObtained());
//        holder.ResAttemptDate.setText(arrayList.get(position).getAttempDate());
//        holder.resProgressPercent.setText(roundFloatTo2Descimal(arrayList.get(position).getPercentage()));
//        holder.resCircularProgressView.setProgress(Float.valueOf(arrayList.get(position).getPercentage()));

    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
//        CardView cardTop10StudentsRoot;
        CircularProgressView resCircularProgressView;
        TextView resultHead, resQAttempted, resRight, ResMarksObtained, ResAttemptDate,resProgressPercent,tvStudentsName,
                ResQAttemptedHead,ResRightAnsHead,ResMarksObtainedHead,ResAttemptDateHead;
        public MyViewHolder(View itemView) {
            super(itemView);

//            cardTop10StudentsRoot = itemView.findViewById(R.id.cardTop10StudentsRoot);
            resCircularProgressView = itemView.findViewById(R.id.resCircularProgressView);
            tvStudentsName = itemView.findViewById(R.id.tvStudentsName);
            resultHead = itemView.findViewById(R.id.resQAttempted);
            resQAttempted = itemView.findViewById(R.id.resQAttempted);
            resRight = itemView.findViewById(R.id.resRight);
            ResMarksObtained = itemView.findViewById(R.id.ResMarksObtained);
            ResAttemptDate = itemView.findViewById(R.id.ResAttemptDate);
            resProgressPercent = itemView.findViewById(R.id.resProgressPercent);
            ResQAttemptedHead = itemView.findViewById(R.id.ResQAttemptedHead);
            ResRightAnsHead = itemView.findViewById(R.id.ResRightAnsHead);
            ResMarksObtainedHead = itemView.findViewById(R.id.ResMarksObtainedHead);
            ResAttemptDateHead = itemView.findViewById(R.id.ResAttemptDateHead);


        }
    }
//    private String roundFloatTo2Descimal(String value){
//
//        DecimalFormat df = new DecimalFormat("#.##");
//        return df.format(Float.valueOf(value));
//    }

}
