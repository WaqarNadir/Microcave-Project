package com.microcave.masjidtimetable.util.classes;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Vicky on 5/17/2015.
 */
public interface Select_masjid_Communicator {

    void Reload();
    void all();
    void dashboard();
    void scroll(String s);
    void check();
    void getcontext(Context c);
}
