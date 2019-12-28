package com.example.adminquickmed;

import android.app.DownloadManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    public MyPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override    public Fragment getItem(int position) {
        switch (position){
            case 0: return new ReqFragment();
            case 1: return new OngoingFragment();
            case 2: return new DoneFragment();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 3;
    }
    @Override    public CharSequence getPageTitle(int position) {        switch (position){
        case 0: return "Request";
        case 1: return "Queue";
        case 2: return "Done";
        default: return null;
    }
    }
}
