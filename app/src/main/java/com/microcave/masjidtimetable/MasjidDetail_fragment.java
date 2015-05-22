package com.microcave.masjidtimetable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.microcave.masjidtimetable.util.classes.GetDataFromWebservice;
import com.microcave.masjidtimetable.util.classes.I_MasjiddetailPage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.zip.CheckedOutputStream;


public class MasjidDetail_fragment extends Fragment implements I_MasjiddetailPage {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private String phone_number;
    JSONObject obj;
    JSONArray arr;
    Context context;

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
        new HttpAsyncTask().execute("http://www.masjid-timetable.com/data/timetable.php?masjid_id=1");
        return inflater.inflate(R.layout.fragment_masjid_detail_fragment, container, false);
    }


    @Override
    public void SetDetail(String Masjid, String LocalArea, String LargerArea, String postcode, String Country, String phone,String ID) {
        Log.e("value of masjid", Masjid + "\n" + LocalArea + "\n" + LargerArea + "\n" + Country);

        TextView tv2 = (TextView) getView().findViewById(R.id.textView7);
        TextView tv3 = (TextView) getView().findViewById(R.id.textView8);
        TextView tv4 = (TextView) getView().findViewById(R.id.textView9);
        TextView tv5 = (TextView) getView().findViewById(R.id.textView6);
        TextView tv6 = (TextView) getView().findViewById(R.id.textView10);
        tv2.setText(Masjid);
        tv3.setText(LocalArea);
        tv4.setText(LargerArea);
        tv5.setText(postcode);
        tv6.setText(Country);
        phone_number = phone;
        Log.e("Masjid ID", ""+ID);


    }

    @Override
    public void setcontext(Context c) {
        context=c;
        Log.e("Context value seting", c.toString());
    }

    public void MakeCall(View v) {
        Log.e("MAKE call", "meathod found");
        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:" + phone_number));
        getActivity().getApplicationContext().startActivity(intent);
    }


    public void NamazTime() {


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
            Log.e("IN post function", " vlaue is true" + result);
            if(context!=null) {
                Toast.makeText(context, "Context is available.", Toast.LENGTH_LONG).show();
            }
            try {
                arr = new JSONArray(result);
                Log.e("JSON TRY block =>", " "+arr.get(0));
                for(int i=0;i<arr.length();i++) {
                    obj = arr.getJSONObject(i);
                    TableLayout _TimeTable= (TableLayout)getView().findViewById(R.id.NamazTable);

                  TableRow TR= new TableRow(getActivity().getApplicationContext());

                    TR.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    TextView  Date=new TextView(getActivity().getApplicationContext());

                    TextView  SubhaSadiq=new TextView(getActivity().getApplicationContext());
                    TextView  Fajar=new TextView(getActivity().getApplicationContext());
                    TextView  Sunrise=new TextView(getActivity().getApplicationContext());

                    TextView  Zohar=new TextView(getActivity().getApplicationContext());
                    TextView  Zohar_j=new TextView(getActivity().getApplicationContext());

                    TextView  Asar=new TextView(getActivity().getApplicationContext());
                    TextView  Asar_j=new TextView(getActivity().getApplicationContext());

                    TextView  Maghrib_j=new TextView(getActivity().getApplicationContext());
                    TextView  Maghrib=new TextView(getActivity().getApplicationContext());

                    TextView  Esha=new TextView(getActivity().getApplicationContext());
                    TextView  Esha_j=new TextView(getActivity().getApplicationContext());

                    Date.setText(obj.getString("DATE"));
                    SubhaSadiq.setText(obj.getString("Subah Sadiq"));

                    Fajar.setText(obj.getString("Fajar"));
                    Sunrise.setText(obj.getString("Sunrise"));

                    Asar.setText(obj.getString(" Asar"));
                    Asar_j.setText(obj.getString("Asar-j"));

                    Esha.setText(obj.getString("Esha"));
                    Esha_j.setText(obj.getString("Esha-j"));

                    Maghrib_j.setText(obj.getString("Maghrib"));
                    Maghrib.setText(obj.getString("Sunset"));


                    Zohar.setText(obj.getString("Zohar"));
                    Zohar_j.setText(obj.getString("Zohar-j"));

                    Log.e("Fajar:", obj.getString("Fajar"));

                    TR.addView(Date);
                    TR.addView(SubhaSadiq);
                    TR.addView(Fajar);
                    TR.addView(Sunrise);
                    TR.addView(Zohar);
                    TR.addView(Zohar_j);
                    TR.addView(Asar);
                    TR.addView(Asar_j);
                    TR.addView(Maghrib);
                    TR.addView(Maghrib_j);
                    TR.addView(Esha);
                    TR.addView(Esha_j);
                    _TimeTable.addView(TR);

                    Toast.makeText(getActivity().getApplicationContext(),
                             "hello ", Toast.LENGTH_SHORT).show();
                             ;





                }


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
