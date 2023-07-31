package com.mp.neetjeeiitprep.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mp.neetjeeiitprep.AppUtils.AppUtils;
import com.mp.neetjeeiitprep.AppUtils.Pref;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.dataModel.VideoChapterListDataModel;

import java.util.ArrayList;

public class VideoChapterListAdapter extends RecyclerView.Adapter<VideoChapterListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<VideoChapterListDataModel.Typelist.Typ> arrayList;
    private int iconId;
    private Bundle navigationData;
    private Pref pref;
    private String pdfUrl;
    private String TAG = "MockTestListAdapterTAG";

    public VideoChapterListAdapter(Context context, ArrayList<VideoChapterListDataModel.Typelist.Typ> arrayList, int iconId, Bundle navigationData) {
        this.context = context;
        this.arrayList = arrayList;
        this.iconId = iconId;
        this.navigationData=navigationData;
        pref = new Pref(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chapter_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        Glide.with(context).load(iconId).into(holder.imgChapterLogo);
        holder.tvChapterName.setText(arrayList.get(position).getTypeTitle());
        holder.clChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pref.getString(Constant.LOGIN_USING).contentEquals(Constant.LOGIN_TYPE_GUEST) &&
                        (holder.getAdapterPosition() > Constant.GUEST_USER_LIMIT - 1)) {
                    AppUtils.openSignInDialog(context);
                } else {

                  /*  navigationData.putString(Constant.MOCKTEST_POSITIVE_MARK, arrayList.get(holder.getAdapterPosition()).getMark());
                    navigationData.putString(Constant.MOCKTEST_NEGATIVE_MARK, arrayList.get(holder.getAdapterPosition()).getMinusMarks());
                    navigationData.putString(Constant.MOCKTEST_DURATION, arrayList.get(holder.getAdapterPosition()).getDuration());

                    navigationData.putString(Constant.SELECTED_TYPE_ID, arrayList.get(holder.getAdapterPosition()).getTypeId());
                    navigationData.putString(Constant.SELECTED_TYPE_NAME, arrayList.get(holder.getAdapterPosition()).getTypeTitle());*/

                    navigationData.putString("video_chapter_id", arrayList.get(position).getVideoTypeId());
                    Navigation.findNavController(v).navigate(R.id.action_videoChapterListFragment_to_videoListFragment, navigationData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout clChapter;
        ImageView imgChapterLogo;
        TextView tvChapterName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            clChapter = itemView.findViewById(R.id.clChapter);
            imgChapterLogo = itemView.findViewById(R.id.imgChapterLogo);
            tvChapterName = itemView.findViewById(R.id.tvChapterName);
        }
    }
}
