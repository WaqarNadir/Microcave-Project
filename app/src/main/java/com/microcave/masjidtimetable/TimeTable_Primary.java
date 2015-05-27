package com.microcave.masjidtimetable;

import android.app.Activity;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.microcave.masjidtimetable.util.classes.Communicator_fragment;
import com.microcave.masjidtimetable.util.classes.ConnectionDetector;
import com.microcave.masjidtimetable.util.classes.GetDataFromWebservice;

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
             new HttpAsyncTask().execute(Masjid_Prayer_Time_URL+"1");    //frag_SelectMasjid.Primary_Masjid_ID
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
            if(urls[0].contentEquals(Masjid_Prayer_Time_URL+"1"))  //frag_SelectMasjid.Primary_Masjid_ID
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

//                _subha_sadiqs=_subha_sadiq.split(":");
//                _fajar_jamats = _fajar_jamat.split(":");
//                _sunrises=_sunrise.split(":");
//                _Zohars=_Zohar.split(":");
//                _Zohar_js=_Zohar_j.split(":");
//                _Asars=_Asar.split(":");
//                _Asar_js=_Asar_j.split(":");
//                _Maghribs=_Maghrib.split(":");
//                _Maghirb_js=_Maghirb_j.split(":");
//                _Eshas=_Esha.split(":");
//                _Esha_js=_Esha_j.split(":");

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
    Date d= new Date();
    String s= df.format(d);
    s=s.replace(":",".");
//    Log.e("Sting time", s + "");
        Double hour = 4.0;//Double.parseDouble(s);
        Log.e("double time", hour + "");

    double fajar = Double.parseDouble(_fajar_jamat);

    double subha_sadiq= Double.parseDouble(_subha_sadiq) ;



    double sunrise=Double.parseDouble(_sunrise);

    double zohar = Double.parseDouble(_Zohar) +12;
    double zoharj = Double.parseDouble(_Zohar_j)+12;

    double asar= Double.parseDouble(_Asar)+12;
    double asarj= Double.parseDouble(_Asar_j)+12;

    double maghrib=Double.parseDouble(_Maghrib)+12;
    double maghribj=Double.parseDouble(_Maghirb_j)+12;

    double esha =Double.parseDouble(_Esha)+12;
    double eshaj =Double.parseDouble(_Esha_j)+12;

    Toast.makeText(getActivity().getApplicationContext(),"."+hour +" --"+subha_sadiq,
            Toast.LENGTH_LONG).show();
Log.e("subha sadiq", "" + subha_sadiq);
Log.e("Fajar", "" + fajar);
Log.e("subrise", "" + sunrise);
Log.e("zohar", "" + zohar);
Log.e("zohar j", "" + zoharj);
Log.e("asar", "" + asar);
Log.e("asar j", "" + asarj);
Log.e("maghirb ", "" + maghrib);
Log.e("magr b j", "" + maghribj);
Log.e("esha ", "" + esha);
Log.e("esha j",""+eshaj);
double remaining_Time=0;

    if(hour < subha_sadiq ||hour >eshaj )
    {
                remaining_Time=subha_sadiq- hour;
        Toast.makeText(getActivity().getApplicationContext(),"subha sadiq" , Toast.LENGTH_SHORT).show();
        Log.e("Next time ", " subha sadiq is next");
        NextPrayer.setText("Subha sadiq begins in " + remaining_Time);

        remaining_Time=fajar -hour;
        SecondPrayer.setText("Fajar begins in "+remaining_Time);

    }
    if(hour < fajar && hour > subha_sadiq)
{
    remaining_Time=fajar -hour;

    Toast.makeText(getActivity().getApplicationContext(),"fajr" , Toast.LENGTH_SHORT).show();
    Log.e("Next time ", " Fajar  is next");

   NextPrayer.setText("Fajar begins in "+remaining_Time );



    remaining_Time=sunrise-hour;
SecondPrayer.setText("Sunrise in "+remaining_Time);
}

    if(hour < sunrise && hour > fajar)
    {
        remaining_Time=sunrise-hour;
        Toast.makeText(getActivity().getApplicationContext(),"foundsunrise" , Toast.LENGTH_SHORT).show();
        Log.e("Next time ", " sunrise  is next");
        NextPrayer.setText("Sunrise in " + remaining_Time);
        remaining_Time=zohar - hour;
        SecondPrayer.setText("Sunrise in " + remaining_Time);
    }

    if(hour < zohar && hour > sunrise )
    {
        remaining_Time=zohar-hour;
        Toast.makeText(getActivity().getApplicationContext(),"zohar" , Toast.LENGTH_SHORT).show();
        Log.e("Next time ", " zohar is next");
        NextPrayer.setText("Zohar begins in " + remaining_Time);

        remaining_Time=zoharj-hour;

    }
    if(hour < zoharj && hour > zohar )
    {
        remaining_Time=zoharj-hour;
        Toast.makeText(getActivity().getApplicationContext(),"zohar jamat" , Toast.LENGTH_SHORT).show();
        Log.e("Next time ", " zohar jamat is next");
        NextPrayer.setText("Zohar Jamat begins in " + remaining_Time);

        remaining_Time=asar-hour;
    }
    if(hour < asar && hour > zoharj)
    {
        remaining_Time=asar-hour;
        Toast.makeText(getActivity().getApplicationContext(),"asar" , Toast.LENGTH_SHORT).show();
        Log.e("Next time ", " asar is next");
        NextPrayer.setText("Asar begins in " + remaining_Time);

        remaining_Time=asarj-hour;
    }
    if(hour < asarj&& hour > asar)
    {
        remaining_Time=asarj-hour;
        Toast.makeText(getActivity().getApplicationContext(),"asar jamt" , Toast.LENGTH_SHORT).show();
        Log.e("Next time ", " asar jamat is next");
        NextPrayer.setText("ASAR Jamat begins in " + remaining_Time);

        remaining_Time=maghrib-hour;
    }
    if(hour < maghrib && hour > asarj)
    {
        remaining_Time=maghrib-hour;
    Toast.makeText(getActivity().getApplicationContext(),"maghirb" , Toast.LENGTH_SHORT).show();
    Log.e("Next time ", " maghirb is next");
    NextPrayer.setText("Maghrib begins in " + remaining_Time);

        remaining_Time=maghribj-hour;
    }
    if(hour < maghribj && hour > maghrib)
    {
        remaining_Time=maghribj-hour;
        Toast.makeText(getActivity().getApplicationContext(),"maghrib jamat" , Toast.LENGTH_SHORT).show();
        Log.e("Next time ", " maghrib jamat is next");
        NextPrayer.setText("Maghrib jamat begins in " + remaining_Time);

        remaining_Time=esha-hour;
    }
    if(hour < esha && hour > maghribj)
    {
        remaining_Time=esha-hour;
        Toast.makeText(getActivity().getApplicationContext(),"esha" , Toast.LENGTH_SHORT).show();
        Log.e("Next time ", " esha is next");
        NextPrayer.setText("Esha  begins in "+remaining_Time);

        remaining_Time=eshaj-hour;
    }
    if(hour < eshaj&& hour > esha)
    {
        remaining_Time=eshaj-hour;
        Toast.makeText(getActivity().getApplicationContext(),"esha jamat" , Toast.LENGTH_SHORT).show();
        Log.e("Next time ", " esha jamat is next");
        NextPrayer.setText("Esha Jamat begins in "+remaining_Time);
    }







}
}

