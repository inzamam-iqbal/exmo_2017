package com.moratuwa.exhibition.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.moratuwa.exhibition.Tabs_Schedule.DayOne;
import com.moratuwa.exhibition.Tabs_Schedule.DayTwo;


public class SchedulePagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public SchedulePagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                DayOne tab1 = new DayOne();
                return tab1;
            case 1:
                DayTwo tab2 = new DayTwo();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}