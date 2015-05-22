package com.microcave.masjidtimetable;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.microcave.masjidtimetable.util.classes.Communicator_fragment;
import com.microcave.masjidtimetable.util.classes.ConnectionDetector;
import com.microcave.masjidtimetable.util.classes.GetDataFromWebservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

public class TimeTable_Primary extends Fragment {
    JSONArray arr;
    JSONObject obj;
    Communicator_fragment data;
    ConnectionDetector cd;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimeTable_Primary.
     */
    // TODO: Rename and change types and number of parameters
    public static TimeTable_Primary newInstance(String param1, String param2) {
        TimeTable_Primary fragment = new TimeTable_Primary();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TimeTable_Primary() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cd = new ConnectionDetector(getActivity().getApplicationContext());

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_table__primary, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e("PRimary ID",""+frag_SelectMasjid.Primary_Masjid_ID);
//        if(cd.isConnectingToInternet()) {
            // start loader
          //  data.Loader(true);
            // -------------------------get data from web service --------------------
            new HttpAsyncTask().execute("http://www.masjid-timetable.com/data/masjids.php?masjid_id="+frag_SelectMasjid.Primary_Masjid_ID);
//        }
//        else {Toast.makeText(getActivity().getApplicationContext(),"You don`t have any network access now.",
//                Toast.LENGTH_LONG).show();}


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls)
        {
            return GetDataFromWebservice.GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result)
        {

            TextView tv=(TextView)getView().findViewById(R.id.Primary_Masjid_name);
            TextView tv1=(TextView)getView().findViewById(R.id.Primary_LocalArea);
            TextView tv2=(TextView)getView().findViewById(R.id.Primary_country);
            try {
                arr = new JSONArray(result);
                Log.e("Primary data ",""+result);
                for(int i=0;i<arr.length();i++){
                    obj=arr.getJSONObject(i);
                Log.e("Primary data ",""+obj);

                tv.setText(obj.getString("masjid_name"));
                 tv1.setText(obj.getString("masjid_local_area") + "," +
                         obj.getString("masjid_larger_area"));
                  tv2.setText(obj.getString("full_name"));


                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
