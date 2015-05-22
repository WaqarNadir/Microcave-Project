package com.microcave.masjidtimetable.util.classes;

import android.content.Context;

/**
 * Created by Vicky on 5/17/2015.
 */
public interface I_MasjiddetailPage {

    void SetDetail(String Masjid, String LocalArea, String LargerArea , String Postcode ,String Country, String phone,String ID);
    void setcontext(Context c);
}
