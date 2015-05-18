package com.microcave.masjidtimetable;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.microcave.masjidtimetable.util.classes.I_MasjiddetailPage;

import java.util.zip.CheckedOutputStream;


public class MasjidDetail_fragment extends Fragment implements I_MasjiddetailPage {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private String phone_number;

    public static MasjidDetail_fragment newInstance(String param1, String param2) {
        MasjidDetail_fragment fragment = new MasjidDetail_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MasjidDetail_fragment() {
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
        return inflater.inflate(R.layout.fragment_masjid_detail_fragment, container, false);
    }


    @Override
    public void SetDetail(String Masjid, String LocalArea, String LargerArea,String postcode, String Country, String phone) {
        Log.e("value of masjid", Masjid + "\n" + LocalArea + "\n" + LargerArea + "\n" + Country);

        TextView tv2= (TextView) getView().findViewById(R.id.textView7);
        TextView tv3= (TextView) getView().findViewById(R.id.textView8);
        TextView tv4= (TextView) getView().findViewById(R.id.textView9);
        TextView tv5= (TextView) getView().findViewById(R.id.textView6);
        TextView tv6= (TextView) getView().findViewById(R.id.textView10);
        tv2.setText(Masjid);
        tv3.setText(LocalArea);
        tv4.setText(LargerArea);
        tv5.setText(postcode);
        tv6.setText(Country);
        phone_number=phone;



    }

public void MakeCall(View v)
{
    Log.e("MAKE call" , "meathod found");
    Intent intent = new Intent(Intent.ACTION_CALL);

    intent.setData(Uri.parse("tel:" +phone_number));
    getActivity().getApplicationContext().startActivity(intent);
}
}
