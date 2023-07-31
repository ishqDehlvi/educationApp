package com.mp.neetjeeiitprep.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mp.neetjeeiitprep.AppUtils.Pref;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.activity.VideoPlayerActivity;
import com.mp.neetjeeiitprep.dataModel.VideoListDataModel;
import com.mp.neetjeeiitprep.interfaces.OnSubjectClickListener;

import java.util.ArrayList;

public class VideoListingAdapter extends RecyclerView.Adapter<VideoListingAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<VideoListDataModel.Video> arrayList;
    private int iconId;
    private OnSubjectClickListener onSubjectClickListener;
    private String pdfUrl;
    private Bundle navigationData;
    private Pref pref;
    private String TAG = "ChapterAdapterTAG";

    public VideoListingAdapter(Context context, ArrayList<VideoListDataModel.Video> arrayList, int iconId, Bundle navigationData) {
        this.context = context;
        this.arrayList = arrayList;
        this.iconId = iconId;
        this.navigationData = navigationData;
        onSubjectClickListener = (OnSubjectClickListener) context;
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
        if(!arrayList.get(holder.getAdapterPosition()).getVideoTitle().contentEquals(""))
            holder.tvChapterName.setText(arrayList.get(holder.getAdapterPosition()).getVideoTitle());
        else
            holder.tvChapterName.setText("Video "+(holder.getAdapterPosition()+1));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                navigationData.putString(Constant.VIDEO_URL, arrayList.get(holder.getAdapterPosition()).getVideoUrl());
                Intent i = new Intent(context, VideoPlayerActivity.class);
                String url = arrayList.get(holder.getAdapterPosition()).getVideoUrl();
                String title = arrayList.get(holder.getAdapterPosition()).getVideoTitle();
                Bundle bundle = new Bundle();
                bundle.putString(Constant.VIDEO_URL, url);
                bundle.putString("title", title);
                i.putExtras(bundle);
                context.startActivity(i);
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

    private void browseUrl(String url){
        if(!url.isEmpty()) {
            Intent browserIntent =
                    new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(browserIntent);
        }
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl.replace("\\", "");
    }
}
