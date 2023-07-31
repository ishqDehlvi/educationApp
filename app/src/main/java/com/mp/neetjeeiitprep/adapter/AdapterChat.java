package com.mp.neetjeeiitprep.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mp.neetjeeiitprep.AppUtils.Pref;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.dataModel.ModelChat;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.Myholder> {
    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPR_RIGHT = 1;
    Context context;
    List<ModelChat> list;
    String imageurl;
    String newDate="uu";

    public AdapterChat(Context context, List<ModelChat> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == MSG_TYPE_LEFT) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_left, parent, false);
            return new Myholder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_right, parent, false);
            return new Myholder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, @SuppressLint("RecyclerView") final int position) {
        String message = list.get(position).getMessage();
        String timeStamp = list.get(position).getTimestamp();
        String name=list.get(position).getName();
        String uid=list.get(position).getSender();

        String type = list.get(position).getType();
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Long.parseLong(timeStamp));
        String time = DateFormat.format("hh:mm aa", calendar).toString();

        Log.d("Boolean",String.valueOf(position));
        if (position == 0) {
            holder.mDate.setText(getDate(list.get(position).getTimestamp()));
        } else {
            String currentDate = getDate(list.get(position).getTimestamp());
            String previousDate = getDate(list.get(position - 1).getTimestamp());

            if (!currentDate.equals(previousDate)) {
                holder.mDate.setText(currentDate);
            } else {
                holder.mDate.setVisibility(View.GONE);
            }
        }



        holder.message.setText(message);
        holder.name.setText(name);
        holder.time.setText(time);
        try {
            Glide.with(context).load(imageurl).into(holder.image);
        } catch (Exception e) {

        }

        if (type.equals("text")) {
            holder.message.setVisibility(View.VISIBLE);
            holder.mimage.setVisibility(View.GONE);
            holder.message.setText(message);
        } else {
            holder.message.setVisibility(View.GONE);
            holder.mimage.setVisibility(View.VISIBLE);
            Glide.with(context).load(message).into(holder.mimage);
        }

        holder.msglayput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.custom_dialog_layout, null);
                builder.setView(dialogView);

                Button deleteButton = dialogView.findViewById(R.id.dialog_delete_button);
                Button cancelButton = dialogView.findViewById(R.id.dialog_cancel_button);

                AlertDialog dialog = builder.create();
                dialog.show();

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteMsg(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });


        holder.name.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.chat_person_info, null);
                builder.setView(dialogView);

                MaterialButton cancelButton = dialogView.findViewById(R.id.btnClose);
                TextView fullname=dialogView.findViewById(R.id.edtEpNa);
                TextView pAddress=dialogView.findViewById(R.id.edtEpAdd);

                TextView pEmail=dialogView.findViewById(R.id.edtEpEmail);
                TextView pPhone=dialogView.findViewById(R.id.edtEpPhNo);

                Query userquery = FirebaseDatabase.getInstance().getReference("Users");
                userquery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            if(dataSnapshot1.child(Constant.USER_ID).getValue().equals(uid)) {
                                String name = "" + dataSnapshot1.child(Constant.USER_NAME).getValue();
                                String email = "" + dataSnapshot1.child(Constant.USER_EMAIL).getValue();
                                String address = "" + dataSnapshot1.child(Constant.USER_ADDRESS).getValue();
                                String phone = "" + dataSnapshot1.child(Constant.USER_PH).getValue();
                                fullname.setText(name);
                                pAddress.setText(address);
                                pPhone.setText(phone);
                                pEmail.setText(email);
                                break;
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });



        }


    private void deleteMsg(int position) {
        Pref pref = new Pref(context);
        final String myuid = pref.getString(Constant.USER_ID);
        String msgtimestmp = list.get(position).getTimestamp();
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Chats");
        Query query = dbref.orderByChild("timestamp").equalTo(msgtimestmp);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.child("sender").getValue().equals(myuid)) {
                        // any two of below can be used
                        dataSnapshot1.getRef().removeValue();
					/* HashMap<String, Object> hashMap = new HashMap<>();
						hashMap.put("message", "This Message Was Deleted");
						dataSnapshot1.getRef().updateChildren(hashMap);
						Toast.makeText(context,"Message Deleted.....",Toast.LENGTH_LONG).show();
*/
                    } else {
                        Toast.makeText(context, "you can delete only your msg....", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        Pref pref = new Pref(context);
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Long.parseLong(list.get(position).getTimestamp()));
        String date = DateFormat.format("yyyy-MM-dd", calendar).toString();

        final String myuid = pref.getString(Constant.USER_ID);
        if (list.get(position).getSender().equals(myuid)) {
            return MSG_TYPR_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }

    class Myholder extends RecyclerView.ViewHolder {

        CircleImageView image;
        ImageView mimage;
        TextView message, time, mDate, isSee, name;
        RelativeLayout msglayput;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.msgc);
            time = itemView.findViewById(R.id.timetv);
            mDate=itemView.findViewById(R.id.date);
            isSee = itemView.findViewById(R.id.isSeen);
            msglayput = itemView.findViewById(R.id.msglayout);
            mimage = itemView.findViewById(R.id.images);
            name=itemView.findViewById(R.id.fullName);
        }
    }
    private String getDate(String timeStamp){
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Long.parseLong(timeStamp));
        String date = DateFormat.format("yyyy-MM-dd", calendar).toString();
        return date;
    }

}

