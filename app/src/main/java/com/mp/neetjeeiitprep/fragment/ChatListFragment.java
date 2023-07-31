package com.mp.neetjeeiitprep.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mp.neetjeeiitprep.AppUtils.Pref;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.adapter.AdapterDmChat;
import com.mp.neetjeeiitprep.dataModel.ModelChat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kotlin.Triple;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {

    RecyclerView recyclerView;
    AdapterDmChat adapterDmChat;
    List<ModelChat> chatList;


    public ChatListFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);

        // getting current user
        String currentUserId=new Pref(requireContext()).getString(Constant.USER_ID);

        recyclerView = view.findViewById(R.id.chatlistrecycle);
        chatList = new ArrayList<>();
        List<String> DmChats=new ArrayList<>();

        Query userquery = FirebaseDatabase.getInstance().getReference("ChatList");
        userquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DmChats.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String uidFromChatList=String.valueOf(dataSnapshot1.getKey());
                    if(!uidFromChatList.equals(Constant.Group_id) && !uidFromChatList.equals(new Pref(requireContext()).getString(Constant.USER_ID))) {
                        DmChats.add(uidFromChatList);
                        Log.d("User-Id", String.valueOf(dataSnapshot1.getKey()));
                    }
                }
                DmChats.add(0,Constant.Group_id);

                adapterDmChat = new AdapterDmChat(getActivity(), DmChats);
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerView.setAdapter(adapterDmChat);

                TextView editText=view.findViewById(R.id.edtxt);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        //adapterDmChat.filter(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String searchText = s.toString();
                        adapterDmChat.getFilter().filter(searchText);
                    }


                });

                for (int i = 0; i < DmChats.size(); i++) {
                    lastMessage(DmChats.get(i));
                }

               // adapterDmChat.filter("tt");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;

   }

private void lastMessage(final String uid) {

        String currentUserId=new Pref(requireContext()).getString(Constant.USER_ID);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Chats");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userName = "";
                String lastmess = "No Messages Yet";
                String name="";
                String time="";

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ModelChat chat = dataSnapshot1.getValue(ModelChat.class);
                    if (chat == null) {
                        continue;
                    }
                    Log.d("Chat-Last", String.valueOf(chat));
                    String sender = chat.getSender();
                    String receiver = chat.getReceiver();
                    if (sender == null || receiver == null) {
                        continue;
                    }
                    if(uid.equals(Constant.Group_id)){
                        userName=Constant.Group_name;
                    }
                    else if(chat.getSender().equals(uid) && chat.getReceiver().equals(Constant.Group_id)){
                        userName=chat.getName();
                    }
                    // checking for the type of message if
                    // message type is image then set
                    // last message as sent a photo
                    if (chat.getReceiver().equals(currentUserId) &&
                            chat.getSender().equals(uid) ||
                            chat.getReceiver().equals(uid) && chat.getSender().equals(currentUserId) ) {

                        if (chat.getType().equals("images")) {
                            lastmess = "Sent a Photo";
                            name= chat.getSender();
                            time=chat.getTimestamp();
                        } else {
                            lastmess = chat.getMessage();
                            name= chat.getSender();
                            String timeStamp=chat.getTimestamp();
                            Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
                            calendar.setTimeInMillis(Long.parseLong(timeStamp));
                            time = DateFormat.format("d MMM hh:mm aa", calendar).toString();
                        }
                    }
                    else if (uid.equals(Constant.Group_id) && chat.getReceiver().equals(uid)) {

                        if (chat.getType().equals("images")) {
                            lastmess = "Sent a Photo";
                            name= chat.getSender();
                            time=chat.getTimestamp();
                        } else {
                            lastmess = chat.getMessage();
                            name= chat.getSender();
                            String timeStamp=chat.getTimestamp();
                            Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
                            calendar.setTimeInMillis(Long.parseLong(timeStamp));
                            time = DateFormat.format("d MMM hh:mm aa", calendar).toString();
                        }
                    }
                }
                Triple triple = new Triple<>(lastmess,name,time);
                adapterDmChat.setlastMessageMap(uid, triple);
                adapterDmChat.setUidToNameMap(uid, userName);
                adapterDmChat.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

}
