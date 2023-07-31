package com.mp.neetjeeiitprep.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mp.neetjeeiitprep.AppUtils.Pref;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.activity.ChatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kotlin.Triple;

public class AdapterDmChat extends RecyclerView.Adapter<AdapterDmChat.Myholder> implements Filterable {

    Context context;
    String uid;
    private HashMap<String, Triple<String,String,String>> lastMessageMap;
    private HashMap<String,String> userNameMap;
    private List<String> usersListFull;
    public AdapterDmChat(Context context, List<String> usersList) {
        lastMessageMap = new HashMap<>();
        userNameMap=new HashMap<>();
        this.context = context;
        this.usersListFull = usersList;
        this.usersList=new ArrayList<>(usersListFull);

    }

    List<String> usersList;

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_chatlist, parent, false);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, final int position) {

        final String hisuid = usersList.get(position);
        String username = userNameMap.get(hisuid);
        Triple lastmess = lastMessageMap.get(hisuid);

        holder.name.setText(username);

        // holder.msgTime.setText(time);

        if (lastMessageMap.get(hisuid) == null) {
            // if no last message then Hide the layout
                holder.lastmessage.setVisibility(View.GONE);
                holder.senderName.setVisibility(View.GONE);
                holder.msgTime.setVisibility(View.GONE);

        }
        else{
            String lastmessage=(String) lastmess.component1();
            String senderUid=(String) lastmess.component2();
            String msgTime=(String) lastmess.component3();

            holder.lastmessage.setVisibility(View.VISIBLE);
            holder.lastmessage.setText(lastmessage);
            if((username.equals(Constant.Group_name)) && !senderUid.isEmpty()){
                if(senderUid.equals(new Pref(context).getString(Constant.USER_ID))){
                    holder.senderName.setVisibility(View.VISIBLE);
                    holder.senderName.setText("You: ");
                }
                else {
                    holder.senderName.setVisibility(View.VISIBLE);
                    holder.senderName.setText(userNameMap.get(senderUid));
                }
            }
            else{
                if(!senderUid.isEmpty() && senderUid.equals(new Pref(context).getString(Constant.USER_ID))){
                    holder.senderName.setVisibility(View.VISIBLE);
                    holder.senderName.setText("You: ");
                }
            }
            holder.msgTime.setVisibility(View.VISIBLE);
            holder.msgTime.setText(msgTime);
        }

            // redirecting to chat activity on item click
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChatActivity.class);

                    intent.putExtra("uid", hisuid);
                    intent.putExtra("name", username);
                    context.startActivity(intent);

                }
            });


        }




    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public void setlastMessageMap(String userId, Triple<String,String,String> lastmessage) {
        lastMessageMap.put(userId, lastmessage);
    }
    public void setUidToNameMap(String userId, String name) {
        userNameMap.put(userId, name);
    }

    @Override
    public Filter getFilter() {
        return userFilter;
    }

    private  final Filter userFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<String> filteredUserList=new ArrayList<>();

            if(constraint==null || constraint.length()==0){
                filteredUserList.addAll(usersListFull);
            }
            else {
                String filterPattern=constraint.toString().toLowerCase().trim();
                for(String userId:usersListFull){
                    String username=userNameMap.get(userId);
                    if(username.toLowerCase().contains(filterPattern)){
                        filteredUserList.add(userId);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredUserList;
            results.count=filteredUserList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
             usersList.clear();
             usersList.addAll((ArrayList)results.values);
             notifyDataSetChanged();
        }
    };

    class Myholder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView name, lastmessage, senderName,msgTime;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profileimage);
            name = itemView.findViewById(R.id.nameonline);
            lastmessage = itemView.findViewById(R.id.lastmessge);
            senderName = itemView.findViewById(R.id.nameSender);
            msgTime = itemView.findViewById(R.id.msgTime);

        }
    }

}


