package com.microcave.masjidtimetable;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.microcave.masjidtimetable.util.classes.Communicator_fragment;
import com.microcave.masjidtimetable.util.classes.ConnectionDetector;
import com.microcave.masjidtimetable.util.classes.CustomListView;
import com.microcave.masjidtimetable.util.classes.CustomListViewAdapter;
import com.microcave.masjidtimetable.util.classes.GetDataFromWebservice;
import com.microcave.masjidtimetable.util.classes.Select_masjid_Communicator;
import com.microcave.masjidtimetable.util.classes.myclass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;


public class frag_SelectMasjid extends FragmentActivity implements Communicator_fragment{
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



    public void Primary(View v)
    {
        _listItem.setSelectedPosition(position);
        if(!Primary_value)
        {
            _listItem.already_clicked=Primary_value;
            Primary_value=true;
            Tab.setCurrentItem(0);
        }
        else
        {
            _listItem.already_clicked=Primary_value;
            Primary_value=false;
            Tab.setCurrentItem(0);
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
        }
        else
        {
            _listItem.S_already_clicked=S_value;
            S_value=false;
            Tab.setCurrentItem(0);
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
        }
        else
        {
            _listItem.T_already_clicked=T_value;
            T_value=false;
            Tab.setCurrentItem(0);
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
        }
        else
        {
            _listItem.Q_already_clicked=Q_value;
            Q_value=false;
            Tab.setCurrentItem(0);
        }


    }
    public void MakeCall(View v)
    {
        Log.e("MAKE call" , "method found");
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setPackage("com.android.server.telecom");
        intent.setData(Uri.parse("tel:" + "123455679"));
        this.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag__select_masjid);


        loader = new ProgressDialog(this);

        TabAdapter= new myclass(getSupportFragmentManager());
        Tab=(ViewPager)findViewById(R.id.pager);
        Tab.setAdapter(TabAdapter);
        frag_SelectMasjidFragment.get= TabAdapter;

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
        FC.Reload();
    }


}
