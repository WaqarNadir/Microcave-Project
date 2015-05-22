package com.microcave.masjidtimetable;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class DashBoard extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
    }

public  void  SelectMasjid(View v)
{
    Intent i= new Intent(this,Select_Masjid.class);
    startActivity(i);

}
    public  void  NearestMasjid(View v)
    {
        Intent i= new Intent(this,Nearest_Masjid.class);
        startActivity(i);

    }
    public  void  Tasbeeh(View v)
    {
        Intent i= new Intent(this,Tasbeeh.class);
        startActivity(i);

    }
    public  void  Wakeup(View v)
    {
        Intent i= new Intent(this,frag_SelectMasjid.class);
        //Intent i= new Intent(this,.class);
        startActivity(i);


    }

    public  void  Learning(View v)
    {
        Intent i= new Intent(this,Learning_Center.class);
        startActivity(i);

    }
    public  void  Asmaulhusna(View v)
    {
        Intent i= new Intent(this,Asma_ul_Husna.class);
        startActivity(i);

    }
    public  void  Instruction(View v)
    {
        Intent i= new Intent(this,Instruction.class);
        startActivity(i);

    }
    public  void  sponsor(View v)
    {
        Intent i= new Intent(this,Sponsor.class);
        startActivity(i);

    }

    public  void  Theme(View v)
    {
        Intent i= new Intent(this,Themes.class);
        startActivity(i);

    }
    public  void  Timetable(View v)
    {
        Intent i= new Intent(this,TimeTable.class);
        startActivity(i);

    }


}
