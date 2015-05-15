package com.microcave.masjidtimetable;

import com.microcave.masjidtimetable.util.classes.ConnectionDetector;
import com.microcave.masjidtimetable.util.classes.CustomListView;
import com.microcave.masjidtimetable.util.classes.CustomListViewAdapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;


public class Select_Masjid extends ActionBarActivity {

    ListView MasjidList;
    ArrayList<String> al;
    ArrayList<String> Masjid;
    ArrayList<String> Local_Area;
    ArrayList<String> Larger_area;
    ArrayList<String> SearchedDataResult;
    ArrayAdapter<String> adapter;
    String temp;
    ProgressDialog loader;
    ConnectionDetector cd;
    EditText searchbox;
    ArrayList<CustomListView> listViewItems;
    ArrayList<CustomListView> ShowValue;
    CustomListViewAdapter ListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__masjid);

        // initialization
        al=new ArrayList<String>();
        Masjid=new ArrayList<String>();
        Local_Area=new ArrayList<String>();
        Larger_area=new ArrayList<String>();
        SearchedDataResult= new ArrayList<String>();
        loader = new ProgressDialog(Select_Masjid.this);
        cd = new ConnectionDetector(this);
        listViewItems=new ArrayList<CustomListView>();
        ShowValue=new ArrayList<CustomListView>();
        MasjidList=(ListView) findViewById(R.id.masjid_list);
        searchbox= (EditText)findViewById(R.id.search);

        if(cd.isConnectingToInternet()) {
            // start loader
            showLoader();
            // -------------------------get data from web service --------------------
            new HttpAsyncTask().execute("http://www.masjid-timetable.com/data/masjids.php");
        }
        else {Toast.makeText(this,"You don`t have any network access now.",Toast.LENGTH_LONG).show();}

        //-------------------------------------------------------------------------
// ----------------Search function calling on text changed-----------------------

        final TextWatcher myhandler = new TextWatcher() {

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
// ---------------------------------- initializer---------------------------------------------
                    SearchedDataResult.clear();         // deletes the previous data of searched
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
                    ListViewAdapter = new CustomListViewAdapter(Select_Masjid.this,ShowValue);
                    MasjidList.setAdapter(ListViewAdapter);
//------------------------------------------------------------------------------------------------

                }

                public void afterTextChanged(Editable s) {

                }
            };
//-------------------------------------------------------------------------
            searchbox.addTextChangedListener(myhandler);


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

            try {
                JSONArray arr = new JSONArray(result);
                JSONObject obj;
                for(int i=0;i<arr.length();i++){
                    obj=arr.getJSONObject(i);

                    Masjid.add(i,obj.getString("masjid_name"));
                    Local_Area.add(i,obj.getString("masjid_local_area"));
                    Larger_area.add(i,obj.getString("masjid_larger_area"));

                    listViewItems.add(new CustomListView(Masjid.get(i),
                            Local_Area.get(i),
                            Larger_area.get(i)
                                    + "," +
                                    obj.getString("masjid_country"),
                            R.drawable.locationicon_blue,
                            R.drawable.locationicon_green,
                            R.drawable.arrow_right));

                }

                Collections.sort(Masjid);
                Collections.sort(Local_Area);
                Collections.sort(Larger_area);
                Collections.sort(listViewItems);

                // dismiss loader
                loader.dismiss();
                all();                  //set value to adapter

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    //-----------------------Function for slide Alphabets----------------------
    public void scroll(View v) {
        String alphabet = (String)v.getTag();
        int index=SearchMasjid(alphabet);

        MasjidList.setSelectionFromTop(index, 0);
    }

    public int SearchMasjid(String s)
    {
                for(int i=0; i<Masjid.size();i++)
                {
                    temp=Masjid.get(i).toString();              // to check ignorecase sensitivity
                    if( temp.toLowerCase().startsWith(s.toLowerCase())  )
                    {
                        return  i;          // send starting index of Alphabet from list
                    }
                }
                    return 0;
    }
//-----------------------------------------------------------------------------


    public void All(View v)
{
 all();
}
    public void reload(View v)
    {
        if(cd.isConnectingToInternet()) {

            al.clear();
            adapter.clear();
            adapter.notifyDataSetChanged();
            // start loader
            showLoader();
            // -------------------------get data from web service --------------------
            new HttpAsyncTask().execute("http://www.masjid-timetable.com/data/masjids.php");
            searchbox.setText("");
        }
        else {Toast.makeText(this,"You don`t have any network access now.",Toast.LENGTH_LONG).show();}
    }
    public void dashboard(View v){
        Intent i= new Intent(this,DashBoard.class);
        startActivity(i);
    }
    public void all()
    {
        /*adapter = new ArrayAdapter<String>(Select_Masjid.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, al);*/
        ListViewAdapter = new CustomListViewAdapter(Select_Masjid.this,
                listViewItems);


        // Assign adapter to ListView
        MasjidList.setAdapter(ListViewAdapter);
//        searchbox.setText("");
    }

    public void showLoader(){
        // starting loader
        loader.setMessage("Fetching data, Please wait...");
        loader.setIndeterminate(true);
        loader.setCancelable(false);
        loader.show();
    }

}