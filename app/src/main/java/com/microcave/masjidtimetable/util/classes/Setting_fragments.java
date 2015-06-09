package com.microcave.masjidtimetable.util.classes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.microcave.masjidtimetable.Setting_Mute_Unmute;
import com.microcave.masjidtimetable.Setting_alarm;

/**
 * Created by Vicky on 6/9/2015.
 */
public class Setting_fragments extends FragmentStatePagerAdapter {
    public Setting_fragments(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch ( position){
            case 0:
                    return new Setting_alarm();
            case 1:
                return  new Setting_Mute_Unmute();
        }
            return  new Setting_alarm();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
