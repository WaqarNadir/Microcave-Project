package com.microcave.masjidtimetable;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.microcave.masjidtimetable.masjid_details.fragment.classes.DonationsFragment;
import com.microcave.masjidtimetable.masjid_details.fragment.classes.EventDetailsFragment;
import com.microcave.masjidtimetable.masjid_details.fragment.classes.MapFragment;
import com.microcave.masjidtimetable.masjid_details.fragment.classes.MasjidDetailsBottom;


public class Tasbeeh extends ActionBarActivity {

    Fragment frag=null;
    ImageView map_normal,events_normal,donations_normal,map_pressed,events_pressed,donations_pressed;
    int clickCheck=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasbeeh);

        map_normal=(ImageView) findViewById(R.id.map_normal);
        events_normal=(ImageView) findViewById(R.id.events_normal);
        donations_normal=(ImageView) findViewById(R.id.donation_normal);
        map_pressed=(ImageView) findViewById(R.id.map_pressed);
        events_pressed=(ImageView) findViewById(R.id.events_pressed);
        donations_pressed=(ImageView) findViewById(R.id.donations_pressed);

        map_pressed.setVisibility(View.GONE);
        events_pressed.setVisibility(View.GONE);
        donations_pressed.setVisibility(View.GONE);

        frag = new MasjidDetailsBottom();
        showFragment();
    }

    public void mapNormal(View v) {
        if(clickCheck==0) {
            frag = new MapFragment();
            map_pressed.setVisibility(View.VISIBLE);
            map_normal.setVisibility(View.GONE);
            showFragment();
            clickCheck=1;
        }
    }
    public void eventsNormal(View v) {
        if(clickCheck==0) {
            frag = new EventDetailsFragment();
            events_pressed.setVisibility(View.VISIBLE);
            events_normal.setVisibility(View.GONE);
            showFragment();
            clickCheck=2;
        }
    }
    public void donationNormal(View v) {
        if(clickCheck==0) {
            frag = new DonationsFragment();
            donations_pressed.setVisibility(View.VISIBLE);
            donations_normal.setVisibility(View.GONE);
            showFragment();
            clickCheck=3;
        }
    }
    public void mapPressed(View v) {
        if(clickCheck==1) {
            frag = new MasjidDetailsBottom();
            map_normal.setVisibility(View.VISIBLE);
            map_pressed.setVisibility(View.GONE);
            showFragment();
            clickCheck=0;
        }
    }
    public void eventsPressed(View v) {
        if(clickCheck==2) {
            frag = new MasjidDetailsBottom();
            events_normal.setVisibility(View.VISIBLE);
            events_pressed.setVisibility(View.GONE);
            showFragment();
            clickCheck=0;
        }
    }
    public void donationsPressed(View v) {
        if(clickCheck==3) {
            frag = new MasjidDetailsBottom();
            donations_normal.setVisibility(View.VISIBLE);
            donations_pressed.setVisibility(View.GONE);
            showFragment();
            clickCheck = 0;
        }
    }

    public void showFragment(){
        if (frag != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_portion, frag).commit();

        }
    }
}
