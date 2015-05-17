package com.microcave.masjidtimetable.util.classes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.microcave.masjidtimetable.*;
import com.microcave.masjidtimetable.frag_SelectMasjidFragment;

/**
 * Created by Vicky on 5/16/2015.
 */
public class myclass extends FragmentStatePagerAdapter implements I_getObject {
    public myclass(FragmentManager fm) {
        super(fm);
    }
    checkfrag c= new checkfrag();
    frag_SelectMasjidFragment f= new frag_SelectMasjidFragment();

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                //Fragement for Android Tab
                return  f;
            case 1:
                //Fragment for Ios Tab
                return  c;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getobject() {
        return c;
    }
}
