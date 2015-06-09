package com.microcave.masjidtimetable;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.TimeUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.microcave.masjidtimetable.util.classes.Communicator_fragment;
import com.microcave.masjidtimetable.util.classes.ConnectionDetector;
import com.microcave.masjidtimetable.util.classes.GetDataFromWebservice;
import com.microcave.masjidtimetable.util.classes.MasjidSQLContract;
import com.microcave.masjidtimetable.util.classes.MasjidSQLLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.jar.JarEntry;

public class TimeTable_Primary extends Fragment {

    /*
        ID's are assigned hard coded. just to check value is present or not ....
        ------ IMP------------------
        ==> Must have to change hard coded Values with MasjidID
          -------------------------
     */

    JSONArray arr;
    JSONObject obj;
    Long subtract;
    Date d;
    SharedPreferences prefs;
    Communicator_fragment data;
    ConnectionDetector cd;
    boolean Event=false;
    boolean notes=false;
    boolean donation=false;
    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(" dd:MMMM:yyyy ");
    SimpleDateFormat df = new SimpleDateFormat(" HH:mm ");
    String strDate = sdf.format(c.getTime());

String _subha_sadiq;
String _fajar_jamat;
String _sunrise;
String _Zohar;
String _Zohar_j;
String _Asar;
String _Asar_j;
String _Maghrib;
String _Maghirb_j;
String _Esha;
String _Esha_j;
    String[] _subha_sadiqs;
    String [] _fajar_jamats;
    String[]  _sunrises;
    String [] _Zohars;
    String [] _Zohar_js;
    String[]  _Asars;
    String [] _Asar_js;
    String [] _Maghribs;
    String [] _Maghirb_js;
    String [] _Eshas;
    String [] _Esha_js;



    String MasjidDetail_URL="http://www.masjid-timetable.com/data/masjids.php?masjid_id=";
    String Masjid_Prayer_Time_URL="http://www.masjid-timetable.com/data/timetable.php?masjid_id=";
    String Event_URL="http://www.masjid-timetable.com/data/events.php?masjid_id=";
    String Notes_URL="http://www.masjid-timetable.com/data/notes.php?masjid_id=";
    String Donation_URL="http://www.masjid-timetable.com/data/donations.php?masjid_id=";
    String Ramzan_URL="http://www.masjid-timetable.com/data/ramadhantimetable.php?masjid_id=";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


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

    SQLiteDatabase db;
    ContentValues value = new ContentValues();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new MasjidSQLLiteOpenHelper(getActivity()).getReadableDatabase();
        value = new ContentValues();


        cd = new ConnectionDetector(getActivity().getApplicationContext());

       prefs = getActivity().getSharedPreferences(
                "com.microcave.masjidtimetable", Context.MODE_PRIVATE);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_time_table__primary, container, false);
    }

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
            new HttpAsyncTask().execute(MasjidDetail_URL+frag_SelectMasjid.Primary_Masjid_ID);

              //------------------------IMP---------------------------------------------------------
        //        ----------------Remember to change value --------------------------------
             new HttpAsyncTask().execute(Masjid_Prayer_Time_URL+"1"+"&&day="+5+ "&&month="+6 );    //frag_SelectMasjid.Primary_Masjid_ID
             new HttpAsyncTask().execute(Event_URL+"179");    //frag_SelectMasjid.Primary_Masjid_ID
             new HttpAsyncTask().execute(Notes_URL+"21");    //frag_SelectMasjid.Primary_Masjid_ID
             new HttpAsyncTask().execute(Donation_URL+"21");    //frag_SelectMasjid.Primary_Masjid_ID
        //                 -------------------------------------
        //--------------------------------------------------------------------------------------------
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
        boolean PrimaryTimeTable=false;

        @Override
        protected String doInBackground(String... urls)
        {
            Log.e("URL Matched",""+urls[0]);
            if(urls[0].contentEquals(Masjid_Prayer_Time_URL+"1"+"&&day="+5+ "&&month="+6))  //frag_SelectMasjid.Primary_Masjid_ID
            {
                Log.e("URL Matched","");
                PrimaryTimeTable=true;
            }
            if(urls[0].contentEquals(Event_URL+"179"))  //checking url
            {
                Log.e("Event_URL","matched");
                Event=true;
            }
            if(urls[0].contentEquals(Notes_URL+"21"))  //checking url
            {
                Log.e("Notes_URL","matched");
                notes=true;
            }
            if(urls[0].contentEquals(Donation_URL+"21"))  //checking url
            {
                Log.e("Notes_URL","matched");
                donation=true;
            }




            return GetDataFromWebservice.GET(urls[0]);

        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result)
        {


if(!PrimaryTimeTable &&!Event &&!notes) {
    TextView tv = (TextView) getView().findViewById(R.id.Primary_Masjid_name);
    TextView tv1 = (TextView) getView().findViewById(R.id.Primary_LocalArea);
    TextView tv2 = (TextView) getView().findViewById(R.id.Primary_country);
    try {
        arr = new JSONArray(result);
        for (int i = 0; i < arr.length(); i++) {
            obj = arr.getJSONObject(i);
            Log.e("Primary data ", "" + obj);

            tv.setText(obj.getString("masjid_name"));
            tv1.setText(obj.getString("masjid_local_area") + "," +
                    obj.getString("masjid_larger_area"));
            tv2.setText(obj.getString("full_name"));


        }

    } catch (JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}else
{
    if(!Event) {
        try {

//        Toast.makeText(getActivity().getApplicationContext(),strDate +"\n",
//               Toast.LENGTH_LONG).show();
            arr = new JSONArray(result);
            // obj= new JSONObject(result);

            for (int i = 0; i < 1; i++) {
                obj = arr.getJSONObject(i);
                //  arr = new JSONArray(result);
                Log.e("Else part execute ", "---" + obj);
                TableLayout _TimeTable = (TableLayout) getView().findViewById(R.id.PrimaryTableLayout);

                //  TextView  Date=(TextView )getView().findViewById(R.id.);
                TextView SubhaSadiq = (TextView) getView().findViewById(R.id.textView12);
                TextView Currenttime = (TextView) getView().findViewById(R.id.textView28);
                TextView Fajar = (TextView) getView().findViewById(R.id.textView13);
                TextView Sunrise = (TextView) getView().findViewById(R.id.textView14);

                TextView Zohar = (TextView) getView().findViewById(R.id.textView16);
                TextView Zohar_j = (TextView) getView().findViewById(R.id.textView17);

                TextView Asar = (TextView) getView().findViewById(R.id.textView19);
                TextView Asar_j = (TextView) getView().findViewById(R.id.textView20);

                TextView Maghrib_j = (TextView) getView().findViewById(R.id.textView22);
                TextView Maghrib = (TextView) getView().findViewById(R.id.textView23);

                TextView Esha = (TextView) getView().findViewById(R.id.textView25);
                TextView Esha_j = (TextView) getView().findViewById(R.id.textView26);

                 _subha_sadiq=obj.getString("Subah Sadiq").replace(":",".");
                _fajar_jamat=obj.getString("Fajar").replace(":", ".");
                _sunrise=obj.getString("Sunrise").replace(":", ".");
                _Zohar=obj.getString("Zohar").replace(":", ".");
                _Zohar_j=obj.getString("Zohar-j").replace(":", ".");
                _Asar=obj.getString("Asar").replace(":", ".");
                _Asar_j=obj.getString("Asar-j").replace(":", ".");
                _Maghrib=obj.getString("Sunset").replace(":", ".");
                _Maghirb_j=obj.getString("Maghrib").replace(":", ".");
                _Esha=obj.getString("Esha").replace(":", ".");
                _Esha_j=obj.getString("Esha-j").replace(":", ".");

                Log.e("ZOhar val", _Zohar_j);
                Zohar.setText(obj.getString("Zohar"));

                Zohar_j.setText(obj.getString("Zohar-j"));

                SubhaSadiq.setText(obj.getString("Subah Sadiq"));

                Fajar.setText(obj.getString("Fajar"));
                Sunrise.setText(obj.getString("Sunrise"));

                Maghrib_j.setText(obj.getString("Maghrib"));
                Maghrib.setText(obj.getString("Sunset"));
                Esha.setText(obj.getString("Esha"));
                Esha_j.setText(obj.getString("Esha-j"));

                Asar.setText(obj.getString("Asar"));
                Asar_j.setText(obj.getString("Asar-j"));
                Currenttime.setText(strDate);
                calculateTime();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

    }else
    {
        if(!notes)
        {
        TextView EventDate = (TextView) getView().findViewById(R.id.textView30);
        TextView EventTme = (TextView) getView().findViewById(R.id.textView31);
        TextView EventTitle = (TextView) getView().findViewById(R.id.textView32);
        TextView EventDetail = (TextView) getView().findViewById(R.id.textView33);
        try {
            arr = new JSONArray(result);
            for (int i = 0; i < arr.length(); i++) {
                obj = arr.getJSONObject(i);
                Log.e("Event value", "" + obj);
                EventDate.setText(obj.getString("event_date"));
                EventTme.setText(obj.getString("event_time"));
                EventTitle.setText(obj.getString("event_title"));
                EventDetail.setText(obj.getString("event_details"));

                value.put(MasjidSQLContract.tables.Masjid_Detail.masjid_name,
                        obj.getString("event_date")           + "|" +
                                obj.getString("event_time")   + "|" +
                                obj.getString("event_title")  + "|" +
                                obj.getString("event_details") +"^"
                );

                db.update(MasjidSQLContract.tables.Masjid_Detail.TableName,value
                ,MasjidSQLContract.tables.Masjid_Detail.masjid_ID +" = 188 ",null);

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        }
        else
        {
                if(!donation)
                {
                    String notes_val="";
                    TextView Notes = (TextView) getView().findViewById(R.id.textView34);
                    try {
                        arr = new JSONArray(result);
                        for (int i = 0; i < arr.length(); i++) {
                            obj = arr.getJSONObject(i);
                            Log.e("Notes value",""+obj);
                            notes_val +="* "+obj.getString("note_text");
                            notes_val +="\n";

                        }

                        Notes.setText(notes_val);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            else{
                    String _donation="";
                    TextView Donation = (TextView) getView().findViewById(R.id.textView35);
                    try {
                        arr = new JSONArray(result);
                        for (int i = 0; i < arr.length(); i++) {
                            obj = arr.getJSONObject(i);
                           _donation += "bank_details : "+ obj.getString("bank_details");
                            _donation+="\nDetail: "+obj.getString("encouragement_text");
                        }
                        Donation.setText(_donation);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

        }

    }
}
        }
    }

public void calculateTime() throws ParseException {
    TextView NextPrayer =(TextView)getView().findViewById(R.id.NextPrayer);
    TextView SecondPrayer =(TextView)getView().findViewById(R.id.SecondPrayer);
    int[] result;
    Date d= new Date();
    String s= df.format(d);
    s=s.replace(":",".");

    Double hour = Double.parseDouble(s);
    Log.e("double time", hour + "");
        s=hour+"";

    double fajar = Double.parseDouble(_fajar_jamat);
    _fajar_jamat=String.format( "%.2f",fajar);

    double subha_sadiq= Double.parseDouble(_subha_sadiq) ;
    _subha_sadiq=String.format( "%.2f",subha_sadiq);

    double sunrise=Double.parseDouble(_sunrise);
    _sunrise =String.format( "%.2f",sunrise);

    double zohar = Double.parseDouble(_Zohar) +12;
    _Zohar=String.format( "%.2f",zohar);

    double zoharj = Double.parseDouble(_Zohar_j)+12;

    _Zohar_j=String.format( "%.2f",zoharj );

    double asar= Double.parseDouble(_Asar)+12;
    _Asar=String.format( "%.2f",asar);

    double asarj= Double.parseDouble(_Asar_j)+12;
    _Asar_j=String.format( "%.2f",asarj);

    double maghrib=Double.parseDouble(_Maghrib)+12;
    _Maghrib=String.format( "%.2f",maghrib);

    double maghribj=Double.parseDouble(_Maghirb_j)+12;
        _Maghirb_j=String.format( "%.2f",maghribj);

    double esha =Double.parseDouble(_Esha)+12;
    _Esha=String.format( "%.2f",esha);

    double eshaj =Double.parseDouble(_Esha_j)+12;
    _Esha_j=String.format( "%.2f",eshaj);

    prefs.edit().putString("Primary_Fajar_jamat",_fajar_jamat);
    prefs.edit().putString("Primary_Zohar_jamat",_Zohar_j);
    prefs.edit().putString("Primary_Asar_jamat",_Asar_j);
    prefs.edit().putString("Primary_Maghrib_jamat", _Maghirb_j);
    prefs.edit().putString("Primary_Esha_jamat", _Esha_j);
    prefs.edit().commit();


    if(hour < subha_sadiq ||hour >eshaj )
    {
      result=  RemainingTime(s , _subha_sadiq);
        NextPrayer.setText("Subha sadiq begins in " +result[0] +"hour"+result[1] +"min" );
        result=   RemainingTime(s , _fajar_jamat);
        SecondPrayer.setText("Fajar begins in "+result[0] +"hour"+result[1] +"min" );

    }
    if(hour < fajar && hour > subha_sadiq)
{
    result=   RemainingTime(s , _fajar_jamat);
   NextPrayer.setText("Fajar begins in " + result[0] + "hour" + result[1] + "min");

    result=  RemainingTime(s , _sunrise);
SecondPrayer.setText("Sunrise in "+result[0] +"hour"+result[1] +"min");
}

    if(hour < sunrise && hour > fajar)
    {
        result=  RemainingTime(s , _sunrise);
        NextPrayer.setText("Sunrise in " + result[0] +"hour"+result[1] +"min");
        result=   RemainingTime(s , _Zohar);
        SecondPrayer.setText("Zohar in " + result[0] +"hour"+result[1] +"min");
    }

    if(hour < zohar && hour > sunrise )
    {
        result=   RemainingTime(s , _Zohar);
        NextPrayer.setText("Zohar begins in " + result[0] + "hour" + result[1] + "min");

        result=     RemainingTime(s , _Zohar_j);
        SecondPrayer.setText("Zohar jamat in " + result[0] +"hour"+result[1] +"min");

    }
    if(hour < zoharj && hour > zohar )
    {
        result=     RemainingTime(s , _Zohar_j);
        NextPrayer.setText("Zohar Jamat begins in " + result[0] + "hour" + result[1] + "min");

        result=     RemainingTime(s , _Asar);
        SecondPrayer.setText("Asar in " + result[0] +"hour"+result[1] +"min");
    }
    if(hour < asar && hour > zoharj)
    {
        result=     RemainingTime(s , _Asar);
        NextPrayer.setText("Asar begins in " + result[0] + "hour" + result[1] + "min");

        result=     RemainingTime(s , _Asar_j);
        SecondPrayer.setText("Asar jamat begins in " + result[0] +"hour"+result[1] +"min");
    }
    if(hour < asarj&& hour > asar)
    {
        result=     RemainingTime(s , _Asar_j);
        NextPrayer.setText("ASAR Jamat begins in " + result[0] + "hour" + result[1] + "min");

        result=      RemainingTime(s , _Maghrib);
        SecondPrayer.setText("Maghrib begins in " + result[0] +"hour"+result[1] +"min");
    }
    if(hour < maghrib && hour > asarj)
    {
        result=      RemainingTime(s , _Maghrib);
        NextPrayer.setText("Maghrib begins in " + result[0] +"hour"+result[1] +"min");

       result=     RemainingTime(s , _Maghirb_j);
        SecondPrayer.setText("Maghrib jamat begins in " + result[0] +"hour"+result[1] +"min");
    }
    if(hour < maghribj && hour > maghrib)
    {
        result=     RemainingTime(s , _Maghirb_j);
        NextPrayer.setText("Maghrib jamat begins in " + result[0] + "hour" + result[1] + "min");

        result=    RemainingTime(s , _Esha);
        SecondPrayer.setText("Esha  begins in " + result[0] +"hour"+result[1] +"min");
    }
    if(hour < esha && hour > maghribj)
    {
        result=    RemainingTime(s , _Esha);
        NextPrayer.setText("Esha  begins in " + result[0] + "hour" + result[1] + "min");

        result=     RemainingTime(s , _subha_sadiq);        // chagned
        SecondPrayer.setText("Esha jamat begins in " + result[0] +"hour"+result[1] +"min");
    }
    if(hour < eshaj&& hour > esha)
    {
        result=     RemainingTime(s , _Esha_j);
        NextPrayer.setText("Esha Jamat begins in " + result[0] + "hour" + result[1] + "min");

        result=     RemainingTime(s , _subha_sadiq);
        SecondPrayer.setText("Subha sadiq begins in " + result[0] +"hour"+result[1] +"min");
    }

}
    public int[] RemainingTime(String CurrentTime,String  prayer_time) {
        String[] ans;
        String[] CurrentTime_ans;
        int[] result = new int[2];

        ans = prayer_time.split("\\.");
        int hour = Integer.parseInt(ans[0]);
        int min = Integer.parseInt(ans[1]);
        min = min + hour * 60;
        Log.e("Now ", CurrentTime);
        Log.e("Now prayr", prayer_time);
        CurrentTime_ans = CurrentTime.split("\\.");
        int Current_hour = Integer.parseInt(CurrentTime_ans[0].trim());
        int Current_min = Integer.parseInt(CurrentTime_ans[1].trim());
        Current_min = Current_min + Current_hour * 60;

        int Total_min = min - Current_min;

        int R_hour = Total_min / 60;
        int R_min = Total_min % 60;
        if(R_hour < 0)
        {
            Total_min= 24*60 +Total_min;
             R_hour = Total_min / 60;
             R_min = Total_min % 60;

        }
        Log.e("Remain hour : min", "" + Total_min + "\n" + R_hour + ":" + R_min);

        //---- changing for meters----------------
        if (R_hour < 2) {          // lesser than 2hours

            ImageView Image1 = (ImageView) getView().findViewById(R.id.imageView);
            ImageView Image2 = (ImageView) getView().findViewById(R.id.imageView2);
            ImageView Image3 = (ImageView) getView().findViewById(R.id.imageView3);
            ImageView Image4 = (ImageView) getView().findViewById(R.id.imageView4);
            ImageView Image5 = (ImageView) getView().findViewById(R.id.imageView5);
            ImageView Image6 = (ImageView) getView().findViewById(R.id.imageView6);
            ImageView Image7 = (ImageView) getView().findViewById(R.id.imageView7);
            ImageView Image8 = (ImageView) getView().findViewById(R.id.imageView8);
            ImageView Image9 = (ImageView) getView().findViewById(R.id.imageView9);
            ImageView Image10 = (ImageView) getView().findViewById(R.id.imageView12);
            ImageView Image11 = (ImageView) getView().findViewById(R.id.imageView13);
            ImageView Image12 = (ImageView) getView().findViewById(R.id.imageView14);

// each green dot represent 10 min ...  If 1 dots is present it means that 10 mints are passed and 50 mints remaining

                    if (Total_min < 10) {
                        Image1.setBackgroundResource(R.drawable.dot_green);
                        Log.e("1:60", "");
                    }
                    if (Total_min < 20) {
                        Image2.setBackgroundResource(R.drawable.dot_green);
                        Log.e("2:60", "");
                    }
                    if (Total_min < 30) {
                        Image3.setBackgroundResource(R.drawable.dot_green);
                        Log.e("3:60", "");
                    }
                    if (Total_min < 40) {
                        Image4.setBackgroundResource(R.drawable.dot_green);
                        Log.e("4:60", "");
                    }
                    if (Total_min< 50) {
                        Image5.setBackgroundResource(R.drawable.dot_green);
                        Log.e("5:60", "");
                    }
                    if (Total_min < 60) {
                        Image6.setBackgroundResource(R.drawable.dot_green);
                        Log.e("6:60", "");
                    }

                    if (Total_min < 70) {
                        Image7.setBackgroundResource(R.drawable.dot_green);
                        Log.e("10", "");
                    }
                    if (Total_min < 80) {
                        Image8.setBackgroundResource(R.drawable.dot_green);
                        Log.e("20", "");
                    }
                    if (Total_min < 90) {
                        Image9.setBackgroundResource(R.drawable.dot_green);
                        Log.e("30", "");
                    }
                    if (Total_min < 100) {
                        Image10.setBackgroundResource(R.drawable.dot_green);
                        Log.e("40", "");
                    }
                    if (Total_min < 110) {
                        Image11.setBackgroundResource(R.drawable.dot_green);
                        Log.e("50", "");
                    }
                    if (Total_min < 120) {
                        Image12.setBackgroundResource(R.drawable.dot_green);
                        Log.e("60", "");
                    }
                }
        else {
                ImageView Image1 = (ImageView) getView().findViewById(R.id.imageView15);
                ImageView Image10 = (ImageView) getView().findViewById(R.id.imageView16);
                ImageView Image7 = (ImageView) getView().findViewById(R.id.imageView17);
                ImageView Image8 = (ImageView) getView().findViewById(R.id.imageView18);
                ImageView Image9 = (ImageView) getView().findViewById(R.id.imageView19);
                ImageView Image11 = (ImageView) getView().findViewById(R.id.imageView20);
                ImageView Image12 = (ImageView) getView().findViewById(R.id.imageView21);
                ImageView Image2 = (ImageView) getView().findViewById(R.id.imageView22);
                ImageView Image3 = (ImageView) getView().findViewById(R.id.imageView23);
                ImageView Image4 = (ImageView) getView().findViewById(R.id.imageView24);
                ImageView Image5 = (ImageView) getView().findViewById(R.id.imageView25);
                ImageView Image6 = (ImageView) getView().findViewById(R.id.imageView26);

                if (Total_min < 130) {
                    Image3.setBackgroundResource(R.drawable.dot_red);
                }
                if (Total_min < 140) {
                    Image4.setBackgroundResource(R.drawable.dot_red);
                }
                if (Total_min< 150) {
                    Image5.setBackgroundResource(R.drawable.dot_red);
                }
                if (Total_min < 160) {
                    Image6.setBackgroundResource(R.drawable.dot_red);
                }

                if (Total_min < 170) {
                    Image7.setBackgroundResource(R.drawable.dot_red);
                }
                if (Total_min < 180) {
                    Image8.setBackgroundResource(R.drawable.dot_red);
                }
                if (Total_min < 190) {
                    Image9.setBackgroundResource(R.drawable.dot_red);
                }
                if (Total_min < 200) {
                    Image10.setBackgroundResource(R.drawable.dot_red);
                }
                if (Total_min < 210) {
                    Image11.setBackgroundResource(R.drawable.dot_red);
                }
                if (Total_min < 220) {
                    Image12.setBackgroundResource(R.drawable.dot_red);
                }
                if (Total_min < 230) {
                    Image1.setBackgroundResource(R.drawable.dot_red);
                }
                if (Total_min < 240) {
                    Image2.setBackgroundResource(R.drawable.dot_red);
                }

            }

            result[0] =R_hour;
            result[1] =R_min;

            return result;
        }

}

