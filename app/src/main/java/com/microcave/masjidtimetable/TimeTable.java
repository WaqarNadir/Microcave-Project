package com.microcave.masjidtimetable;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.microcave.masjidtimetable.util.classes.TimeTableFragment;
import com.microcave.masjidtimetable.util.classes.myclass;


public class TimeTable extends FragmentActivity {
    ViewPager Tab;
    TimeTableFragment TabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        TabAdapter= new TimeTableFragment(getSupportFragmentManager());
        Tab=(ViewPager)findViewById(R.id.TimeTable_pager);
        Tab.setAdapter(TabAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_table, menu);
        return true;
    }


}
