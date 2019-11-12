package com.coding.wk.cataloguemovieextendedapplication.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.coding.wk.cataloguemovieextendedapplication.R;
import com.coding.wk.cataloguemovieextendedapplication.fragments.NowPlayingFragment;
import com.coding.wk.cataloguemovieextendedapplication.fragments.UpcomingFragment;

public class ViewTabPager extends FragmentPagerAdapter {
    private static final int numberTab = 2;
    private Context context;
    public ViewTabPager(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }
    @Override
    public int getCount() {
        return numberTab;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new NowPlayingFragment();
            case 1:
                return new UpcomingFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getResources().getString(R.string.title_now_playing);
            case 1:
                return context.getResources().getString(R.string.title_upcoming);
            default:
                return null;
        }
    }
}
