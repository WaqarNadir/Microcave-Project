package com.microcave.masjidtimetable;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag__select_masjid);


        loader = new ProgressDialog(this);

        TabAdapter= new myclass(getSupportFragmentManager());
        Tab=(ViewPager)findViewById(R.id.pager);
        Tab.setAdapter(TabAdapter);
        frag_SelectMasjidFragment.get= TabAdapter;

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
