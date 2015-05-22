package com.microcave.masjidtimetable.util.classes;

import android.view.View;

/**
 * Created by Vicky on 5/17/2015.
 */
public interface Communicator_fragment {
    void getData(String s);
    void Loader(boolean show);
    void getListview(CustomListViewAdapter v, int pos);
    void setcontext();      // set context to next page.
}
