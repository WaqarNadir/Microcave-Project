package com.microcave.masjidtimetable;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.microcave.masjidtimetable.util.classes.Communicator_fragment;
import com.microcave.masjidtimetable.util.classes.I_MasjiddetailPage;

public class checkfrag extends Fragment  implements I_MasjiddetailPage {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static checkfrag newInstance(String param1, String param2) {
        checkfrag fragment = new checkfrag();


        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public checkfrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkfrag, container, false);
    }


    @Override
    public void SetMasjid(String s) {


        TextView tv= (TextView) getView().findViewById(R.id.changevalue);
        tv.setText(s);

    }
}
