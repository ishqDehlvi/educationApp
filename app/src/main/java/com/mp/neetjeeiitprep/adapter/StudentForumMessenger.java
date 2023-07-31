package com.mp.neetjeeiitprep.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mp.neetjeeiitprep.fragment.AboutUsFragment;
import com.mp.neetjeeiitprep.fragment.ChatListFragment;
import com.mp.neetjeeiitprep.fragment.SolvedPapersFragment;
import com.mp.neetjeeiitprep.fragment.StudentForumChatsFragment;
import com.mp.neetjeeiitprep.fragment.StudentForumPostsFragment;

public class StudentForumMessenger extends FragmentPagerAdapter {
    public StudentForumMessenger(@NonNull FragmentManager fm) {super(fm);}



    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new ChatListFragment();
        } else {
            return new StudentForumPostsFragment();
        }
    }

    @Override
    public int getCount() {return 2;}

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
        if (position==0){
            return "Chats";
        } else {
            return "Posts";
        }
    }
}
