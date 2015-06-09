package com.microcave.masjidtimetable;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import com.microcave.masjidtimetable.util.classes.MasjidSQLContract;
import com.microcave.masjidtimetable.util.classes.MasjidSQLLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    SQLiteDatabase db;
    String Month="Current";
    SimpleDateFormat sdf= new SimpleDateFormat("dd-MM");
    int count=0;
    int _ID;

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

    Date d ;
    String[] date;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db= new MasjidSQLLiteOpenHelper(getActivity()).getWritableDatabase();
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
    public void SetDetail(String Masjid, String LocalArea, String LargerArea, String postcode, String Country, String phone,String ID) {
      //  Log.e("value of masjid", Masjid + "\n" + LocalArea + "\n" + LargerArea + "\n" + Country);

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



        d= new Date();
        String s = sdf.format(d);
        date = s.split("-");

        Log.e("Masjid ID", "" + ID);
        Log.e("Day", "" + date[0]);
        Log.e("month", "" + date[1]);


        new HttpAsyncTask().execute("http://www.masjid-timetable.com/data/timetable.php?masjid_id=" + ID +"&&month="+
                                    date[1] );


    }

    @Override
    public void setcontext(Context c) {
        context=c;
    }

//    public void MakeCall(View v) {
//        Log.e("MAKE call", "meathod found");
//        Intent intent = new Intent(Intent.ACTION_CALL);
//
//        intent.setData(Uri.parse("tel:" + phone_number));
//        getActivity().getApplicationContext().startActivity(intent);
//    }


    public void NamazTime() {


    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls)
        {
            return GetDataFromWebservice.GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.


        ContentValues val= new ContentValues();

        @Override
        protected void onPostExecute(String result)

        {
                try {
                    arr = new JSONArray(result);
                    Log.e("JSON TRY block =>", " ");
                    for(int i=0;i<arr.length();i++) {
                        obj = arr.getJSONObject(i);

                        val.put(MasjidSQLContract.tables.PrayerTime.DATE,obj.getString("DATE"));
                        val.put(MasjidSQLContract.tables.PrayerTime.SubhaSadiq,obj.getString("Subah Sadiq"));

                        val.put(MasjidSQLContract.tables.PrayerTime.Fajar_J,obj.getString("Fajar"));
                        val.put(MasjidSQLContract.tables.PrayerTime.Sunrise,obj.getString("Sunrise"));

                        val.put(MasjidSQLContract.tables.PrayerTime.Asar_b,obj.getString("Asar"));
                        val.put(MasjidSQLContract.tables.PrayerTime.Asar_j,obj.getString("Asar-j"));

                        val.put(MasjidSQLContract.tables.PrayerTime.Esha_b,obj.getString("Esha"));
                        val.put(MasjidSQLContract.tables.PrayerTime.Esha_j,obj.getString("Esha-j"));

                        val.put(MasjidSQLContract.tables.PrayerTime.Maghrib_j,obj.getString("Maghrib"));
                        val.put(MasjidSQLContract.tables.PrayerTime.Maghrib_b,obj.getString("Sunset"));


                        val.put(MasjidSQLContract.tables.PrayerTime.Zohar_b,obj.getString("Zohar"));
                        val.put(MasjidSQLContract.tables.PrayerTime.Zohar_j,obj.getString("Zohar-j"));
                        val.put(MasjidSQLContract.tables.PrayerTime.Type,Month);
                        Log.e("Type",Month+count);


                        db.insert(MasjidSQLContract.tables.PrayerTime.TableName,null,val);
                    }
                    if(count==0)
                    {
                            int n= Integer.parseInt(date[1])+1;
                        new HttpAsyncTask().execute("http://www.masjid-timetable.com/data/timetable.php?masjid_id=" + _ID +"&&month="+
                                n  );
                        Log.e("NExt Month", n + "");
                        Month="Next";
                        count++;
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


        }
    }

}
