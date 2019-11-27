package com.example.SpinIt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class NewAccessorAdapter extends FragmentPagerAdapter {
    public NewAccessorAdapter (@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {


        FriendsFragment friendsFragment = new FriendsFragment();
        return friendsFragment;


    }

    @Override
    public int getCount() {
        return 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {


        return "friends";
    }


}