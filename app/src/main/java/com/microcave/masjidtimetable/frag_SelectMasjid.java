package com.microcave.masjidtimetable;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.microcave.masjidtimetable.util.classes.Communicator_fragment;
import com.microcave.masjidtimetable.util.classes.ConnectionDetector;
import com.microcave.masjidtimetable.util.classes.CustomListView;
import com.microcave.masjidtimetable.util.classes.CustomListViewAdapter;
import com.microcave.masjidtimetable.util.classes.GetDataFromWebservice;
import com.microcave.masjidtimetable.util.classes.MasjidDetail;
import com.microcave.masjidtimetable.util.classes.MasjidSQLContract;
import com.microcave.masjidtimetable.util.classes.MasjidSQLLiteOpenHelper;
import com.microcave.masjidtimetable.util.classes.Select_masjid_Communicator;
import com.microcave.masjidtimetable.util.classes.myclass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class frag_SelectMasjid extends FragmentActivity implements Communicator_fragment{


    String MasjidDetail_URL="http://www.masjid-timetable.com/data/masjids.php?masjid_id=";
    String Masjid_Prayer_Time_URL="http://www.masjid-timetable.com/data/timetable.php?masjid_id=";
    String Event_URL="http://www.masjid-timetable.com/data/events.php?masjid_id=";
    String Notes_URL="http://www.masjid-timetable.com/data/notes.php?masjid_id=";
    String Donation_URL="http://www.masjid-timetable.com/data/donations.php?masjid_id=";
    String Ramzan_URL="http://www.masjid-timetable.com/data/ramadhantimetable.php?masjid_id=";

    JSONArray arr;
    JSONObject obj;
    SimpleDateFormat sdf= new SimpleDateFormat("dd-MM");
    ViewPager Tab;
    myclass TabAdapter;
    ProgressDialog loader;
    Select_masjid_Communicator FC;

    CustomListViewAdapter _listItem;
    int position;
    static boolean Primary_value=false;
    static boolean S_value=false;
    static boolean T_value=false;
    static boolean Q_value=false;
    public  Context context;
    ArrayList<MasjidDetail> Masjidinfo= new ArrayList<MasjidDetail>();
    CustomListView _item= new CustomListView();

    // static variable to use in timetable class
    static  int Primary_Masjid_ID;
    static  int Secondary_Masjid_ID=0;
    static  int Ternary_Masjid_ID=0;
    static  int Quatary_Masjid_ID=0;



SharedPreferences pref;
    //-----------------------------------------
    SQLiteDatabase db;
    ContentValues value;
    String _ID_for_Search=null;
    String Type=null;

    Date d;
    String[] date;

    public void Primary(View v)
    {
       // Log.e("Primary val checking ", position+"");
        _listItem.setSelectedPosition(position);

        if(!Primary_value)
        {
            _listItem.already_clicked=Primary_value;
            Primary_value=true;
            Tab.setCurrentItem(0);

          Masjidinfo=  frag_SelectMasjidFragment.getMasjidDetailArray();
            _item=(CustomListView ) _listItem.getItem(position);

            for(int i=0; i<Masjidinfo.size();i++)
            {
                        if(Masjidinfo.get(i).getMasjid_name().toLowerCase().contains(_item.getMasjidName().toLowerCase())
                                &&
                               Masjidinfo.get(i).getMasjid_local_area().toLowerCase().contains(_item.getloc1().toLowerCase()) )
                        {
                                //Log.e("primary function.ID", ""+Masjidinfo.get(i).getMasjid_ID());

                                    Primary_Masjid_ID=Integer.parseInt( Masjidinfo.get(i).getMasjid_ID());

                            _ID_for_Search= Masjidinfo.get(i).getMasjid_ID();
                            Type="Primary";
                                    pref.edit().putString("Primary_Name",Masjidinfo.get(i).getMasjid_name()).apply();
                                    pref.edit().commit();
                                    Log.e("primary function.ID", "" + Masjidinfo.get(i).getMasjid_name());
                                save_masjid_detail(Masjidinfo.get(i));

                        }

            }

            d= new Date();
            String s = sdf.format(d);
            date = s.split("-");

            new HttpAsyncTask().execute(Masjid_Prayer_Time_URL+_ID_for_Search +"&&day="+date[0]+"&&month="+date[1] );    //frag_SelectMasjid.Primary_Masjid_ID
        //    new HttpAsyncTask().execute(Event_URL+"179");    //frag_SelectMasjid.Primary_Masjid_ID
        //    new HttpAsyncTask().execute(Notes_URL+"21");    //frag_SelectMasjid.Primary_Masjid_ID
         //   new HttpAsyncTask().execute(Donation_URL+"21");    //frag_SelectMasjid.Primary_Masjid_ID


        }
        else
        {
            _listItem.already_clicked=Primary_value;
            Primary_value=false;
            Tab.setCurrentItem(0);
            pref.edit().putString("Primary_Name", null).apply();
            pref.edit().commit();

        }


    }
    public void Secondary(View v)
    {
        _listItem.setSecondary(position);
        if(!S_value)
        {
            _listItem.S_already_clicked=S_value;
            S_value=true;
            Tab.setCurrentItem(0);
            Masjidinfo=  frag_SelectMasjidFragment.getMasjidDetailArray();
            _item=(CustomListView ) _listItem.getItem(position);

            for(int i=0; i<Masjidinfo.size();i++)
            {
                if(Masjidinfo.get(i).getMasjid_name().toLowerCase().contains(_item.getMasjidName().toLowerCase())
                        &&
                        Masjidinfo.get(i).getMasjid_local_area().toLowerCase().contains(_item.getloc1().toLowerCase()) )
                {

                    Secondary_Masjid_ID=Integer.getInteger( Masjidinfo.get(i).getMasjid_ID(),-999);

                    pref.edit().putString("Sec_Name",Masjidinfo.get(i).getMasjid_name()).apply();
                    pref.edit().commit();
                    Log.e("Sec function.ID", "" + Masjidinfo.get(i).getMasjid_name());

//                            Toast.makeText(this,"Secondary \n"+Masjidinfo.get(i).getMasjid_name()+"\n"+
//                                            Masjidinfo.get(i).getMasjid_local_area()+"\n"+
//                                            Masjidinfo.get(i).getMasjid_larger_area()+"\n"+
//                                            Masjidinfo.get(i).getMasjid_country(),Toast.LENGTH_SHORT
//                            ).show();         //checking working
                }
            }

        }
        else
        {
            _listItem.S_already_clicked=S_value;
            S_value=false;
            Tab.setCurrentItem(0);
            pref.edit().putString("Sec_Name",null).apply();
            pref.edit().commit();
            Log.e("Sec function.ID", "");
        }


    }
    public void Ternary(View v)
    {
        _listItem.setTernary(position);
        if(!T_value)
        {
            _listItem.T_already_clicked=T_value;
            T_value=true;
            Tab.setCurrentItem(0);
            Masjidinfo=  frag_SelectMasjidFragment.getMasjidDetailArray();
            _item=(CustomListView ) _listItem.getItem(position);

            for(int i=0; i<Masjidinfo.size();i++)
            {
                if(Masjidinfo.get(i).getMasjid_name().toLowerCase().contains(_item.getMasjidName().toLowerCase())
                        &&
                        Masjidinfo.get(i).getMasjid_local_area().toLowerCase().contains(_item.getloc1().toLowerCase()) )
                {

                    Ternary_Masjid_ID=Integer.getInteger( Masjidinfo.get(i).getMasjid_ID(),-999);
                    pref.edit().putString("Ter_Name",Masjidinfo.get(i).getMasjid_name()).apply();
                    pref.edit().commit();
                    Log.e("Terfunction.ID", "" + Masjidinfo.get(i).getMasjid_name());
//
//                    Toast.makeText(this,"Ternary \n"+Masjidinfo.get(i).getMasjid_name()+"\n"+
//                                            Masjidinfo.get(i).getMasjid_local_area()+"\n"+
//                                            Masjidinfo.get(i).getMasjid_larger_area()+"\n"+
//                                            Masjidinfo.get(i).getMasjid_country(),Toast.LENGTH_SHORT
//                            ).show();         //checking working
                }
            }

        }
        else
        {
            _listItem.T_already_clicked=T_value;
            T_value=false;
            Tab.setCurrentItem(0);
            pref.edit().putString("Ter_Name",null).apply();
            pref.edit().commit();
            Log.e("Terfunction.ID", "");
        }


    }

    public void Quatary(View v)
    {
            _listItem.setQuatary(position);
        if(!Q_value)
        {
            _listItem.Q_already_clicked=Q_value;
            Q_value=true;
            Tab.setCurrentItem(0);
            Masjidinfo=  frag_SelectMasjidFragment.getMasjidDetailArray();
            _item=(CustomListView ) _listItem.getItem(position);

            for(int i=0; i<Masjidinfo.size();i++)
            {
                if(Masjidinfo.get(i).getMasjid_name().toLowerCase().contains(_item.getMasjidName().toLowerCase())
                        &&
                        Masjidinfo.get(i).getMasjid_local_area().toLowerCase().contains(_item.getloc1().toLowerCase()) )
                {

                    Quatary_Masjid_ID=Integer.getInteger( Masjidinfo.get(i).getMasjid_ID(),-999);
                    pref.edit().putString("Quat_Name",Masjidinfo.get(i).getMasjid_name()).apply();
                    pref.edit().commit();
                    Log.e("Quat function.ID", "" + Masjidinfo.get(i).getMasjid_name());

//                            Toast.makeText(this,"Quatary \n"+Masjidinfo.get(i).getMasjid_name()+"\n"+
//                                            Masjidinfo.get(i).getMasjid_local_area()+"\n"+
//                                            Masjidinfo.get(i).getMasjid_larger_area()+"\n"+
//                                            Masjidinfo.get(i).getMasjid_country(),Toast.LENGTH_SHORT
//                            ).show();         //checking working
                }
            }

        }
        else
        {
            _listItem.Q_already_clicked=Q_value;
            Q_value=false;
            Tab.setCurrentItem(0);
            pref.edit().putString("Quat_Name",null).apply();
            pref.edit().commit();
            Log.e("quat function.ID", "");
        }


    }
    public void MakeCall(View v)
    {
     //   Log.e("MAKE call" , "method found");
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + "123455679"));
        this.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //-------------------------Intializing DB-----------------------------
        db= new MasjidSQLLiteOpenHelper(getApplicationContext()).getWritableDatabase();
        value= new ContentValues();
        //------------------------------------------------------

        setContentView(R.layout.activity_frag__select_masjid);
        loader = new ProgressDialog(this);
context=this;
        TabAdapter= new myclass(getSupportFragmentManager());
        Tab=(ViewPager)findViewById(R.id.pager);
        Tab.setAdapter(TabAdapter);
        frag_SelectMasjidFragment.get= TabAdapter;
        pref= getSharedPreferences("Primary",Context.MODE_PRIVATE);


//        Tab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            public void onPageScrollStateChanged(int state) {
//                Log.e("State page", ""+move);
//                if(move)
//                {
//                    Tab.setCurrentItem(1);
//                }
//                else {
//                    Tab.setCurrentItem(0);
//                }
//            }
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.e("scroll", ""+move);
//                if(move)
//                {
//                    Tab.setCurrentItem(1);
//                }
//                else {
//                    Tab.setCurrentItem(1);
//                }
//            }
//
//            public void onPageSelected(int position) {
//                // Check if this is the page you want.
//            }
//        });


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void getData(String s) {
        Toast.makeText(this,s, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void Loader(boolean show) {
        // starting loader
        if(!show)
        {
                loader.dismiss();
        }else {
            loader.setMessage("Fetching data, Please wait...");
            loader.setIndeterminate(true);
            loader.setCancelable(false);
            loader.show();
//            FC.check();

        }

    }

    @Override
    public void getListview(CustomListViewAdapter v,int pos) {
        position=pos;
        _listItem=v;

    }

    @Override
    public void setcontext() {
        FC.getcontext(this);

    }

    public void All(View v)
    {
        FC.all();
    }
    public void scroll(View v) {
        String alphabet = (String)v.getTag();
        FC.scroll(alphabet);
    }

    public void dashboard(View v){
        FC.dashboard();
    }
    public void reload(View v)
    {
       // FC.Reload();
        String Table = MasjidSQLContract.tables.PrayerTime.TableName;
        SQLiteDatabase db1 = new MasjidSQLLiteOpenHelper(this).getReadableDatabase();

        String [] columns = {MasjidSQLContract.tables.PrayerTime.Type,

        };

        //String where= MasjidSQLContract.tables.Masjid_Detail.masjid_ID +" = 188" ;
        Cursor c = db1.query(Table,columns,null,null,null,null,null);

        c.moveToLast();
        Toast.makeText(getApplicationContext(),c.getString(
                c.getColumnIndex(MasjidSQLContract.tables.PrayerTime.Type)
        )
                ,
                Toast.LENGTH_SHORT).show();

    }
    public void timetable(View v)
    {
        Intent i= new Intent(this,TimeTable.class);
        startActivity(i);

    }

    private void save_masjid_detail(MasjidDetail detail)
    {
        Log.e("Saving funtiom", "");
        ContentValues value = new ContentValues();
        value.put(MasjidSQLContract.tables.Masjid_Detail.masjid_name, detail.getMasjid_name());
        value.put(MasjidSQLContract.tables.Masjid_Detail.masjid_ID,detail.getMasjid_ID());

        value.put(MasjidSQLContract.tables.Masjid_Detail.masjid_add_1,detail.getMasjid_add_1());

        value.put(MasjidSQLContract.tables.Masjid_Detail.masjid_local_area,detail.getMasjid_local_area());
        value.put(MasjidSQLContract.tables.Masjid_Detail.masjid_larger_area,detail.getMasjid_larger_area());

        value.put(MasjidSQLContract.tables.Masjid_Detail.masjid_post_code,detail.getMasjid_post_code());
        value.put(MasjidSQLContract.tables.Masjid_Detail.masjid_telephone,detail.getMasjid_telephone());

        value.put(MasjidSQLContract.tables.Masjid_Detail.masjid_country,detail.getMasjid_country());
        value.put(MasjidSQLContract.tables.Masjid_Detail.masjid_created,detail.getMasjid_created());

        value.put(MasjidSQLContract.tables.Masjid_Detail.masjid_modified,detail.getMasjid_modified());
        value.put(MasjidSQLContract.tables.Masjid_Detail.status,detail.getStatus());

        value.put(MasjidSQLContract.tables.Masjid_Detail.code,detail.getCode());
        value.put(MasjidSQLContract.tables.Masjid_Detail.full_name,detail.getFull_name());
        db.insert(MasjidSQLContract.tables.Masjid_Detail.TableName,
                null,value);

    }




    boolean Event=false;
    boolean notes=false;
    boolean donation=false;


    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        boolean PrimaryTimeTable=false;

        @Override
        protected String doInBackground(String... urls)
        {
            Log.e("date", date[0]+":"+date[1]);

            if(urls[0].contentEquals(Masjid_Prayer_Time_URL+_ID_for_Search +"&&day="+date[0]+"&&month="+date[1]))
              //frag_SelectMasjid.Primary_Masjid_ID
            {
                Log.e("URL Masjid prayer",""+urls[0]);
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
////                TextView tv = (TextView) getView().findViewById(R.id.Primary_Masjid_name);
////                TextView tv1 = (TextView) getView().findViewById(R.id.Primary_LocalArea);
////                TextView tv2 = (TextView) getView().findViewById(R.id.Primary_country);
// try {
//                    arr = new JSONArray(result);
//                    for (int i = 0; i < arr.length(); i++) {
//                        obj = arr.getJSONObject(i);
//                        Log.e("Primary data ", "" + obj);
//
//                        tv.setText(obj.getString("masjid_name"));
//                        tv1.setText(obj.getString("masjid_local_area") + "," +
//                                obj.getString("masjid_larger_area"));
//                        tv2.setText(obj.getString("full_name"));
//
//
//                    }
//
//                } catch (JSONException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
            }else
            {
                if(!Event) {
                    Log.e("Value in pryare","");
                    try {
                        arr = new JSONArray(result);
                        // obj= new JSONObject(result);

                        for (int i = 0; i < arr.length(); i++) {
                            obj = arr.getJSONObject(i);
                            //  arr = new JSONArray(result);
                         ContentValues Prayervalues= new ContentValues();
                            Prayervalues.put(MasjidSQLContract.tables.PrayerTime.SubhaSadiq, obj.getString("Subah Sadiq").replace(":",".") );
                            Prayervalues.put(MasjidSQLContract.tables.PrayerTime.Fajar_J,obj.getString("Fajar").replace(":", "."));;
                            Prayervalues.put(MasjidSQLContract.tables.PrayerTime.Sunrise,obj.getString("Sunrise").replace(":", "."));;
                            Prayervalues.put(MasjidSQLContract.tables.PrayerTime.Zohar_b,obj.getString("Zohar").replace(":", "."));
                            Prayervalues.put(MasjidSQLContract.tables.PrayerTime.Zohar_j,obj.getString("Zohar-j").replace(":", "."));
                            Prayervalues.put(MasjidSQLContract.tables.PrayerTime.Asar_b,obj.getString("Asar").replace(":", "."));
                            Prayervalues.put(MasjidSQLContract.tables.PrayerTime.Asar_j,obj.getString("Asar-j").replace(":", "."));
                            Prayervalues.put(MasjidSQLContract.tables.PrayerTime.Maghrib_b,obj.getString("Sunset").replace(":", "."));
                            Prayervalues.put(MasjidSQLContract.tables.PrayerTime.Maghrib_j,obj.getString("Maghrib").replace(":", "."));
                            Prayervalues.put(MasjidSQLContract.tables.PrayerTime.Esha_b,obj.getString("Esha").replace(":", "."));
                            Prayervalues.put(MasjidSQLContract.tables.PrayerTime.Esha_j,obj.getString("Esha-j").replace(":", "."));
                            Prayervalues.put(MasjidSQLContract.tables.PrayerTime.Type,Type);

                            db.insert(MasjidSQLContract.tables.PrayerTime.TableName, null
                                    , Prayervalues);

                            Log.e("Value to db" , "values saved to db PRayers");

                        }

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.getMessage();

                    }

                }else
                {
                    if(!notes)
                    {
//                        TextView EventDate = (TextView) getView().findViewById(R.id.textView30);
//                        TextView EventTme = (TextView) getView().findViewById(R.id.textView31);
//                        TextView EventTitle = (TextView) getView().findViewById(R.id.textView32);
//                        TextView EventDetail = (TextView) getView().findViewById(R.id.textView33);
                        try {
                            arr = new JSONArray(result);
                            for (int i = 0; i < arr.length(); i++) {
                                obj = arr.getJSONObject(i);
                                Log.e("Event value", "" + obj);
//                                EventDate.setText(obj.getString("event_date"));
//                                EventTme.setText(obj.getString("event_time"));
//                                EventTitle.setText(obj.getString("event_title"));
//                                EventDetail.setText(obj.getString("event_details"));

                                value.clear();

                                value.put(MasjidSQLContract.tables.Primary_Masjid.Event,
                                        obj.getString("event_date")           + "|" +
                                                obj.getString("event_time")   + "|" +
                                                obj.getString("event_title")  + "|" +
                                                obj.getString("event_details") +"^"
                                );

                                db.update(MasjidSQLContract.tables.Primary_Masjid.TableName, value
                                        , MasjidSQLContract.tables.Primary_Masjid.ID + " =" + _ID_for_Search, null);

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
                          //  TextView Notes = (TextView) getView().findViewById(R.id.textView34);
                            try {
                                arr = new JSONArray(result);
                                for (int i = 0; i < arr.length(); i++) {
                                    obj = arr.getJSONObject(i);
                                    Log.e("Notes value",""+obj);
                                    notes_val +="* "+obj.getString("note_text");
                                    notes_val +="\n";

                                }
                                value.clear();
                                value.put(MasjidSQLContract.tables.Primary_Masjid.Notes,
                                        notes_val);

                                db.update(MasjidSQLContract.tables.Primary_Masjid.TableName,value
                                        ,MasjidSQLContract.tables.Primary_Masjid.ID +" = "+_ID_for_Search,null);
    //                                Notes.setText(notes_val);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        else{
                            String _donation="";
                         //   TextView Donation = (TextView) getView().findViewById(R.id.textView35);
                            try {
                                arr = new JSONArray(result);
                                for (int i = 0; i < arr.length(); i++) {
                                    obj = arr.getJSONObject(i);
                                    _donation += "bank_details : "+ obj.getString("bank_details");
                                    _donation+="\nDetail: "+obj.getString("encouragement_text");
                                }
                                value.clear();
                                value.put(MasjidSQLContract.tables.Primary_Masjid.Donation,
                                     _donation );

                                db.update(MasjidSQLContract.tables.Primary_Masjid.TableName,value
                                        ,MasjidSQLContract.tables.Primary_Masjid.ID +" = "+_ID_for_Search,null);
                              //  Donation.setText(_donation);
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













}