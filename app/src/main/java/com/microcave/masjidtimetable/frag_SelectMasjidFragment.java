package com.microcave.masjidtimetable;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.microcave.masjidtimetable.util.classes.Communicator_fragment;
import com.microcave.masjidtimetable.util.classes.ConnectionDetector;
import com.microcave.masjidtimetable.util.classes.CustomListView;
import com.microcave.masjidtimetable.util.classes.CustomListViewAdapter;
import com.microcave.masjidtimetable.util.classes.GetDataFromWebservice;
import com.microcave.masjidtimetable.util.classes.I_MasjiddetailPage;
import com.microcave.masjidtimetable.util.classes.I_getObject;
import com.microcave.masjidtimetable.util.classes.MasjidDetail;
import com.microcave.masjidtimetable.util.classes.MasjidSQLContract;
import com.microcave.masjidtimetable.util.classes.MasjidSQLLiteOpenHelper;
import com.microcave.masjidtimetable.util.classes.Select_masjid_Communicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A placeholder fragment containing a simple view.
 */
public class frag_SelectMasjidFragment extends Fragment  implements Select_masjid_Communicator{

    JSONObject obj;
    Context context;
    int count=0;
    JSONArray arr;
    ListView MasjidList;
    ArrayList<String> Masjid;
    ArrayList<String> Local_Area;
    private static ArrayList<MasjidDetail> MasjidDetailArray;

    ArrayList<String> Larger_area;
    String temp;
    ProgressDialog loader;
    ConnectionDetector cd;
    EditText searchbox;
    ArrayList<CustomListView> listViewItems;
    ArrayList<CustomListView> ShowValue;
    CustomListViewAdapter ListViewAdapter;
    View vj;

    Communicator_fragment data;

    I_MasjiddetailPage DetailPage;
    static I_getObject get;
    SharedPreferences pref;

    String Masjid_Prayer_Time_URL="http://www.masjid-timetable.com/data/timetable.php?masjid_id=";
    boolean PrayerTime=false;

    public frag_SelectMasjidFragment() {

    }
    SQLiteDatabase db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       pref=  getActivity().getSharedPreferences("Primary",Context.MODE_PRIVATE);

        data=(Communicator_fragment)getActivity();
        ( (frag_SelectMasjid)getActivity() ).FC=this;


        vj= inflater.inflate(R.layout.fragment_frag__select_masjid, container, false);

        if(get!=null)
        {
         //   Log.e("OBJECT VALUE" , "Value is found");
            DetailPage= (MasjidDetail_fragment)get.getobject();
        }
        //------------------- initialization ----------------------------------------------------
        MasjidDetailArray = new ArrayList<MasjidDetail>();
        Masjid=new ArrayList<String>();
        Local_Area=new ArrayList<String>();
        Larger_area=new ArrayList<String>();
        loader = new ProgressDialog(getActivity().getApplicationContext());
        cd = new ConnectionDetector(getActivity().getApplicationContext());
        listViewItems=new ArrayList<CustomListView>();
        ShowValue=new ArrayList<CustomListView>();

        db= new MasjidSQLLiteOpenHelper(getActivity()).getWritableDatabase();


        MasjidList=(ListView) vj. findViewById(R.id.masjid_list);
        searchbox= (EditText)vj.findViewById(R.id.search);
//-------------------------------------------------------------------------------------------------
        MasjidList.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                //   view.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));
                String name=    listViewItems.get(position).getMasjidName();
                String loc= listViewItems.get(position).getloc1();
                for(int i=0; i< getMasjidDetailArray().size() ; i++)
                {
                    if(getMasjidDetailArray().get(i).getMasjid_name().contains(name)
                            &&
                            getMasjidDetailArray().get(i).getMasjid_local_area().contains(loc)  )
                    {
                        DetailPage.SetDetail(name,
                                getMasjidDetailArray().get(i).getMasjid_local_area(),
                                getMasjidDetailArray().get(i).getMasjid_larger_area(),
                                getMasjidDetailArray().get(i).getMasjid_post_code(),
                                getMasjidDetailArray().get(i).getMasjid_country(),
                                getMasjidDetailArray().get(i).getMasjid_telephone(),
                                getMasjidDetailArray().get(i).getMasjid_ID()
                        );

                        DetailPage.setcontext(context); // passing context to next fragment

                        data.getListview(ListViewAdapter,position);
                        //method is used for setting primary secondary etc values
                    }


                }
                ((frag_SelectMasjid) getActivity()).Tab.setCurrentItem(1);

            }
        });

        if(cd.isConnectingToInternet()) {
            // start loader
            data.Loader(true);
            // -------------------------get data from web service --------------------
            new HttpAsyncTask().execute("http://www.masjid-timetable.com/data/masjids.php");
            new HttpAsyncTask().execute(Masjid_Prayer_Time_URL+"1");
        }
        else {Toast.makeText(getActivity().getApplicationContext(),"You don`t have any network access now.",
                Toast.LENGTH_LONG).show();}
        //-------------------------------------------------------------------------
// ----------------Search function calling on text changed-----------------------

        final TextWatcher myhandler = new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

// ---------------------------------- initializer---------------------------------------------
                //    SearchedDataResult.clear();         // deletes the previous data of searched
                ShowValue.clear();
                String value = searchbox.getText().toString();
//----------------------------------------------------------------------------------------------

//------------------ first select the desired Masjid--------------------------------
                for (int i = 0; i < Masjid.size(); i++) {
                    temp = Masjid.get(i).toString();              // to check ignorecase sensitivity
                    if (temp.toLowerCase().contains(value.toLowerCase())) {


//----------------------Now check that specific mosque in list-----------------------------------
                        for (int val = 0; val < listViewItems.size(); val++) {
                            if(listViewItems.get(val).getMasjidName().contains(Masjid.get(i)) )
                            {
                                ShowValue.add(listViewItems.get(val));

                            }
                        }
//------------------------------------------------------------------------------------------------
                    }
                }
//---------------------------------Find local area first--------------------------------------------
                String[] _localarea;
                for (int j = 0; j < Local_Area.size(); j++) {
                    _localarea = Local_Area.get(j).split(" ");        // spliting on space for searching complete Name

                    for (int inner = 0; inner < _localarea.length; inner++) {
                        //area is found
                        temp = _localarea[inner].toLowerCase();                  // ignore Case Senstivity
                        if (temp.startsWith(value.toLowerCase())) {
//----------------------------find specific area in list view --------------------------------------
                            for (int val = 0; val < listViewItems.size(); val++) {       //Now check that area in  lsit
                                if (listViewItems.get(val).getloc1().contains(Local_Area.get(j)))
                                {
                                    ShowValue.add(listViewItems.get(val));      // list value index found
                                }
                            }

                        }
                    }

                }
//-------------------------------------------------------------------------------------------------
//-------------------------Find larger area --------------------------------------------------------
                for (int i = 0; i < Larger_area.size(); i++) {
                    temp = Larger_area.get(i).toString();              // to check ignorecase sensitivity
                    if (temp.toLowerCase().startsWith(value.toLowerCase())) {       //Larger area identified
//----------------------------find specific area in list view --------------------------------------
                        for (int val = 0; val < listViewItems.size(); val++) {

                            if (listViewItems.get(val).getloc2().contains(Larger_area.get(i)))         // check Larger area in al list
                            {//  area in  list found
                                ShowValue.add(listViewItems.get(val));
                            }
                        }
//--------------------------------------------------------------------------------------------------

                    }
                }
//--------------------------------------------------------------------------------------------------

// ------------------------Assign adapter to ListView---------------------------------------------
                ListViewAdapter = new CustomListViewAdapter(getActivity().getApplicationContext(),ShowValue);
                MasjidList.setAdapter(ListViewAdapter);
//------------------------------------------------------------------------------------------------

            }

            public void afterTextChanged(Editable s) {

            }
        };
//-------------------------------------------------------------------------
        searchbox.addTextChangedListener(myhandler);



        return vj;

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void Reload() {

        if(cd.isConnectingToInternet()) {
            Masjid.clear();
            Local_Area.clear();
            Larger_area.clear();
            listViewItems.clear();


            ListViewAdapter.notifyDataSetChanged();
            // start loader
            // showLoader();
            data.Loader(true);
            // -------------------------get data from web service --------------------
            new HttpAsyncTask().execute("http://www.masjid-timetable.com/data/masjids.php");

            searchbox.setText("");
        }
        else {Toast.makeText(getActivity().getApplicationContext(),"You don`t have any network access now.",Toast.LENGTH_LONG).show();}

    }

    public static ArrayList<MasjidDetail> getMasjidDetailArray() {
        return MasjidDetailArray;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls)
        {
            if(urls[0].contentEquals(Masjid_Prayer_Time_URL+"1"))  //frag_SelectMasjid.Primary_Masjid_ID
            {
                Log.e("URL Matched","");
                PrayerTime=true;
            }
            return GetDataFromWebservice.GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        String Month="Current";
        @Override
        protected void onPostExecute(String result)
        {
            ContentValues val = new ContentValues();
    try {
        arr = new JSONArray(result);
        String Primary_val = pref.getString("Primary_Name", null);
        String Sec_val = pref.getString("Sec_Name", null);
        String Ter_val = pref.getString("Ter_Name", null);
        String Quat_val = pref.getString("Quat_Name", null);

        for (int i = 0; i < arr.length(); i++) {
            obj = arr.getJSONObject(i);

            Masjid.add(i, obj.getString("masjid_name"));
            Local_Area.add(i, obj.getString("masjid_local_area"));
            Larger_area.add(i, obj.getString("masjid_larger_area"));

            fillMasjiddetail();
            CustomListView cv;
            if (Larger_area.get(i).equals("")) {

                cv = new CustomListView(Masjid.get(i),
                        Local_Area.get(i),
                        obj.getString("masjid_country"),
                        R.drawable.locationicon_blue,
                        R.drawable.locationicon_green,
                        R.drawable.arrow_right);

            } else {
                cv = new CustomListView(Masjid.get(i),
                        Local_Area.get(i),
                        Larger_area.get(i)
                                + "," +
                                obj.getString("masjid_country"),
                        R.drawable.locationicon_blue,
                        R.drawable.locationicon_green,
                        R.drawable.arrow_right);

            }
            if (Primary_val != null && Primary_val.equals(obj.getString("masjid_name"))) {
                cv.setColor(Color.GREEN);
                listViewItems.add(cv);
            } else {
                if (Sec_val != null && Sec_val.equals(obj.getString("masjid_name"))) {
                    cv.setColor(Color.GRAY);
                    listViewItems.add(cv);
                } else {
                    if (Ter_val != null && Ter_val.equals(obj.getString("masjid_name"))) {
                        cv.setColor(Color.YELLOW);
                        listViewItems.add(cv);
                    } else {
                        if (Quat_val != null && Quat_val.equals(obj.getString("masjid_name"))) {
                            cv.setColor(Color.BLUE);
                            listViewItems.add(cv);
                        } else {
                            listViewItems.add(cv);  // by default color is white
                        }
                    }
                }
            }

        }

        Collections.sort(Masjid);
        Collections.sort(Local_Area);
        Collections.sort(Larger_area);
        Collections.sort(listViewItems);

        // dismiss loader
        data.Loader(false);
        all();                  //set value to adapter

    } catch (JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

        }
    }

    //-----------------------Function for slide Alphabets----------------------

    public int SearchMasjid(String s)
    {
        Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        for(int i=0; i<Masjid.size();i++)
        {
            temp=Masjid.get(i).toString();              // to check ignorecase sensitivity
            if( temp.toLowerCase().startsWith(s.toLowerCase())  )
            {
                return  i;          // send starting index of Alphabet from list
            }
        }
        char val= s.charAt(0);
        val++;
        String  _value= String.valueOf(val) ;
        return SearchMasjid(_value);            // find next valid index
    }
//-----------------------------------------------------------------------------

    @Override
    public void all()
    {
        ListViewAdapter = new CustomListViewAdapter(getActivity().getApplicationContext(),
                listViewItems);
        MasjidList.setAdapter(ListViewAdapter);             // Assign adapter to ListView
    }

    @Override
    public void dashboard() {
        Intent i= new Intent(getActivity().getApplicationContext(),DashBoard.class);
        startActivity(i);
    }
    @Override
    public void scroll(String s) {

            String alphabet = s;
            int index = SearchMasjid(alphabet);
            MasjidList.setSelectionFromTop(index, 0);
    }

    @Override
    public void check() {
        DetailPage= (MasjidDetail_fragment)get.getobject();

    }

    @Override
    public void getcontext(Context c) {
        context=c;
    }

    public void fillMasjiddetail()
    {
        try {
            MasjidDetail MD = new MasjidDetail();
            MD.setMasjid_ID(obj.getString("masjid_id"));
            MD.setMasjid_name(obj.getString("masjid_name"));
            MD.setFull_name(obj.getString("full_name"));
            MD.setMasjid_add_1(obj.getString("masjid_add_1"));
            MD.setMasjid_local_area(obj.getString("masjid_local_area"));
            MD.setMasjid_larger_area(obj.getString("masjid_larger_area"));
            MD.setMasjid_post_code(obj.getString("masjid_post_code"));
            MD.setMasjid_telephone(obj.getString("masjid_telephone"));
            MD.setMasjid_country(obj.getString("masjid_country"));
            MD.setMasjid_created(obj.getString("masjid_created"));
            MD.setMasjid_modified(obj.getString("masjid_modified"));
            MD.setStatus(obj.getString("status"));
            MD.setCode(obj.getString("code"));
        MasjidDetailArray.add(MD);
        }catch(Exception e)
        {

        }


    }


}
