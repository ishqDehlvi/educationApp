package com.mp.neetjeeiitprep.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.mp.neetjeeiitprep.AppUtils.AppUtils;
import com.mp.neetjeeiitprep.AppUtils.Pref;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.dataModel.SubTypeDataModel;
import com.mp.neetjeeiitprep.interfaces.OnSubjectClickListener;

import java.util.ArrayList;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<SubTypeDataModel.List> arrayList;
    private int iconId;
    private OnSubjectClickListener onSubjectClickListener;
    private String pdfUrl;
    private Bundle navigationData;
    private Pref pref;
    private String TAG = "ChapterAdapterTAG";

    public ChapterAdapter(Context context, ArrayList<SubTypeDataModel.List> arrayList, int iconId, Bundle navigationData) {
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
        holder.tvChapterName.setText(arrayList.get(position).getTypeTitle());

        holder.clChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigation.findNavController(v).navigate(R.id.action_chapterListFragment_to_mcqTestFragment);
                if (pref.getString(Constant.LOGIN_USING).contentEquals(Constant.LOGIN_TYPE_GUEST) &&
                        (holder.getAdapterPosition() > Constant.GUEST_USER_LIMIT - 1)) {
                    AppUtils.openSignInDialog(context);
                } else {
                    openChoiceDialog(v,holder.getAdapterPosition());
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

    private void openChoiceDialog(final View view, final int pos) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.what_to_do_choice);

        MaterialButton btnChooseNcrt = dialog.findViewById(R.id.btnChooseNcrt);
        MaterialButton btnChooseSolvedPapers = dialog.findViewById(R.id.btnChooseSolvedPapers);
        MaterialButton btnChoosePractice = dialog.findViewById(R.id.btnChoosePractice);
        final MaterialButton btnChooseCancel = dialog.findViewById(R.id.btnChooseCancel);

        btnChooseNcrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                navigationData.putString(Constant.PDF_LIST_TYPE, Constant.PDF_LIST_NCERT);
                navigationData.putString(Constant.SELECTED_TYPE_ID, arrayList.get(pos).getTypeId());
                Navigation.findNavController(view).navigate(R.id.action_chapterListFragment_to_pdfListingFragment, navigationData);
               /* if (!ncrtUrl.isEmpty()) {
                    dialog.dismiss();
                    navigationData.putString(Constant.PDF_URL, pdfUrl + ncrtUrl);
                    Navigation.findNavController(view).navigate(R.id.action_chapterListFragment_to_pdfListingFragment, navigationData);
                  //  onSubjectClickListener.subjectClicked("NCRT");
                } else {
                    Toast.makeText(context, "no file found", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        btnChooseSolvedPapers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                navigationData.putString(Constant.PDF_LIST_TYPE, Constant.PDF_LIST_PDF);
                navigationData.putString(Constant.SELECTED_TYPE_ID, arrayList.get(pos).getTypeId());
                Navigation.findNavController(view).navigate(R.id.action_chapterListFragment_to_pdfListingFragment, navigationData);
                /*if (!prevYearUrl.isEmpty()) {
                    dialog.dismiss();
                    navigationData.putString(Constant.PDF_URL, pdfUrl + prevYearUrl);
                    Navigation.findNavController(view).navigate(R.id.action_chapterListFragment_to_pdfListingFragment, navigationData);
                  //  onSubjectClickListener.subjectClicked("Solved Paper");
                } else {
                    Toast.makeText(context, "no file found", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        btnChoosePractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                navigationData.putString(Constant.SELECTED_TYPE_ID, arrayList.get(pos).getTypeId());
                navigationData.putString(Constant.SELECTED_TYPE_NAME, arrayList.get(pos).getTypeTitle());
                Navigation.findNavController(view).navigate(R.id.action_chapterListFragment_to_mcqTestFragment, navigationData);
            //    onSubjectClickListener.subjectClicked("Practice Paper");

              //  Toast.makeText(context,"Under Development",Toast.LENGTH_SHORT).show();
            }
        });

        btnChooseCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl.replace("\\", "");
    }
}
