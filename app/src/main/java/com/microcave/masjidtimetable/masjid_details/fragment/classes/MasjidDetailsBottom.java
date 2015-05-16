package com.microcave.masjidtimetable.masjid_details.fragment.classes;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.microcave.masjidtimetable.R;


public class MasjidDetailsBottom extends Fragment {

    public MasjidDetailsBottom() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_masjid_details_bottom, container, false);
    }


}
