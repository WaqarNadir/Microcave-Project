package com.microcave.masjidtimetable;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Setting_Mute_Unmute extends Fragment {
  private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
  private String mParam1;
    private String mParam2;

public static Setting_Mute_Unmute newInstance(String param1, String param2) {
        Setting_Mute_Unmute fragment = new Setting_Mute_Unmute();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Setting_Mute_Unmute() {
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
        return inflater.inflate(R.layout.fragment_setting__mute__unmute, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
