package com.microcave.masjidtimetable.util.classes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.microcave.masjidtimetable.TimeTable_Primary;
import com.microcave.masjidtimetable.TimeTable_Secondary;
import com.microcave.masjidtimetable.TimeTable_Ternary;
import com.microcave.masjidtimetable.TimeTime_Quatary;


public class TimeTableFragment extends FragmentStatePagerAdapter {
    public TimeTableFragment(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TimeTable_Primary();
            case 1:
                return new TimeTable_Secondary();
            case 2:
                return new TimeTable_Ternary();
            case 3:
                return new TimeTime_Quatary();

        };
        Log.e("IN time table fragmetn","");
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
