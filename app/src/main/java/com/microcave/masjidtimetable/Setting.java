package com.microcave.masjidtimetable;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.text.style.TtsSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.microcave.masjidtimetable.util.classes.AlarmService;
import com.microcave.masjidtimetable.util.classes.DemoAlarmActivity;
import com.microcave.masjidtimetable.util.classes.MasjidSQLContract;
import com.microcave.masjidtimetable.util.classes.MasjidSQLLiteOpenHelper;
import com.microcave.masjidtimetable.util.classes.Setting_fragments;
import com.microcave.masjidtimetable.util.classes.myclass;

public class Setting extends FragmentActivity{
    SharedPreferences pref;
    final Handler Handler= new Handler();
    SimpleDateFormat df = new SimpleDateFormat(" HH:mm ");
    DecimalFormat decimalformat = new DecimalFormat("00");
    static  Boolean Bool_fajar;
    static  Boolean Bool_Zohar;
    static  Boolean Bool_Asar ;
    static  Boolean Bool_Maghrib;
    static Boolean Bool_Esha ;;

    String SubhaSadiq;
    String Fajar;
    String Subrise;
    String Zohar;
    String Zohar_J;
    String Asar;
    String Asar_J;
    String Esha;
    String Esha_J;
    String Maghrib;
    String Maghrib_J;
    String Type;

    static int _Fajar_alarm;
    static int _Zohar_alarm;
    static int _Asar_alarm;
    static int _Maghrib_alarm;
    static int _Esha_alarm;

    ViewPager Tab;
    Setting_fragments TabAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        TabAdapter = new Setting_fragments(getSupportFragmentManager());
        Tab= (ViewPager) findViewById(R.id.Setting_Pager);
        Tab.setAdapter(TabAdapter);

        pref= this.getSharedPreferences("com.microcave.masjidtimetable", Context.MODE_PRIVATE);

        GetPrayerTime();
        Bool_fajar =true;
        Bool_Zohar =true;
        Bool_Asar =true;
        Bool_Maghrib =true;
        Bool_Esha =true;



    }

    @Override
    protected void onStart() {
        super.onStart();

            }

//========----------------- Util functions ----------===============================================
    public int RemainingTime(String CurrentTime,String  prayer_time,String _Name_prayer) {
        String[] ans;
        String[] CurrentTime_ans;
        int[] result = new int[2];

        ans = prayer_time.split("\\.");
        ans[0].trim();
        ans[1].trim();
        int hour = Integer.parseInt(ans[0].trim());
        int min = Integer.parseInt(ans[1].trim());
        min = min + hour * 60;
        Log.e("hour:min before", hour + ":" + min);

        //---- for alarm before specific mint -----------
        if(_Name_prayer.equals("Fajar"))
        {
            min=min-_Fajar_alarm -1;   // min 1 for ring before one min
        }
        if(_Name_prayer.equals("Zohar"))
        {
            min=min-_Zohar_alarm -1;   // min 1 for ring before one min
        }
        if(_Name_prayer.equals("Asar"))
        {
            min=min-_Asar_alarm -1;   // min 1 for ring before one min
        }
        if(_Name_prayer.equals("Maghrib"))
        {
            min=min-_Maghrib_alarm -1;   // min 1 for ring before one min
        }
        if(_Name_prayer.equals("Esha"))
        {
            min=min-_Esha_alarm -1;   // min 1 for ring before one min
        }
        Log.e("Remain hour : min after", hour + ":" + min);
//=================================================================
        CurrentTime_ans = CurrentTime.split("\\.");
        int Current_hour = Integer.parseInt(CurrentTime_ans[0].trim());
        int Current_min = Integer.parseInt(CurrentTime_ans[1].trim());
        Current_min = Current_min + Current_hour * 60;

    //   Log.e("Remain hour : min", hour + ":" + min);
    //    Log.e("Current hour : min",  + Current_hour + ":" + Current_min );

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

        result[0] = R_hour;
        result[1] = R_min;

        if(result[0]==0)
        {
            Toast.makeText(this,"Alarm set after "+R_min+" Mints.",Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(this,"Alarm set after "+R_hour+" hours"+R_min+" Mints.",Toast.LENGTH_SHORT).show();
        }


        return Total_min;       // changed result
    }
Boolean a=true;
    private  int[] Split_Time(String Time)
    {
        String[] ans = Time.split("\\.");
        int hour = Integer.parseInt(ans[0]);
        int min = Integer.parseInt(ans[1]);

        int[] result=new int[]{hour,min};

        return  result;


    }

    void GetPrayerTime()
    {
        SQLiteDatabase db= new MasjidSQLLiteOpenHelper(this).getReadableDatabase();
        String[] Columns={MasjidSQLContract.tables.PrayerTime.SubhaSadiq,
                MasjidSQLContract.tables.PrayerTime.Fajar_J,
                MasjidSQLContract.tables.PrayerTime.Sunrise,
                MasjidSQLContract.tables.PrayerTime.Zohar_b,
                MasjidSQLContract.tables.PrayerTime.Zohar_j,
                MasjidSQLContract.tables.PrayerTime.Asar_b,
                MasjidSQLContract.tables.PrayerTime.Asar_j,
                MasjidSQLContract.tables.PrayerTime.Maghrib_b,
                MasjidSQLContract.tables.PrayerTime.Maghrib_j,
                MasjidSQLContract.tables.PrayerTime.Esha_j,
                MasjidSQLContract.tables.PrayerTime.Esha_b,
                MasjidSQLContract.tables.PrayerTime.Type
        };
        Cursor c =db.query(MasjidSQLContract.tables.PrayerTime.TableName, Columns, MasjidSQLContract.tables.PrayerTime.Type + "= \"Primary\"",
                null, null, null, null);
        c.moveToFirst();
        if(c!=null) {
            SubhaSadiq = c.getString(c.getColumnIndex(MasjidSQLContract.tables.PrayerTime.SubhaSadiq));
            Fajar = c.getString(c.getColumnIndex(MasjidSQLContract.tables.PrayerTime.Fajar_J));
            Subrise = c.getString(c.getColumnIndex(MasjidSQLContract.tables.PrayerTime.Sunrise));
            Zohar = c.getString(c.getColumnIndex(MasjidSQLContract.tables.PrayerTime.Zohar_b));
            Zohar_J = c.getString(c.getColumnIndex(MasjidSQLContract.tables.PrayerTime.Zohar_j));
            Asar = c.getString(c.getColumnIndex(MasjidSQLContract.tables.PrayerTime.Asar_b));
            Asar_J = c.getString(c.getColumnIndex(MasjidSQLContract.tables.PrayerTime.Asar_j));
            Maghrib = c.getString(c.getColumnIndex(MasjidSQLContract.tables.PrayerTime.Maghrib_b));
            Maghrib_J = c.getString(c.getColumnIndex(MasjidSQLContract.tables.PrayerTime.Maghrib_j));
            Esha = c.getString(c.getColumnIndex(MasjidSQLContract.tables.PrayerTime.Esha_j));
            Esha_J = c.getString(c.getColumnIndex(MasjidSQLContract.tables.PrayerTime.Esha_b));
            Type = c.getString(c.getColumnIndex(MasjidSQLContract.tables.PrayerTime.Type));
            Log.e("Pryer value form db", Type);
        }
        Log.e("Pryer value form no db", Type);
    }
//--------------------------------------------------------------------------------------------------

//================================ View Function for activity=======================================

    public void cancelIT(View v)
    {
        if(a)
        {Mute(); a=false;}
        else {UnMute();a=true;}
//        pref.edit().putBoolean("play",false);
//        pref.edit().commit();
//
//        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        //alarmManager.cancel(pendingIntent);
    }
    public void Fajar(View v)
    {

        EditText _et=(EditText)findViewById(R.id.Fajar_alarm);
        RadioButton RB= (RadioButton) findViewById(R.id.radioButton);
        try {
            String data=_et.getText().toString();
            _Fajar_alarm = Integer.parseInt(data);
            if(RB.isChecked())
            {
                Log.e("azan value","true");
                pref.edit().putBoolean("Fajar_Azan", true).apply();
                pref.edit().commit();
            }else
            {
                pref.edit().putBoolean("Fajar_Azan", false).apply();
                pref.edit().commit();
                Log.e("azan value", "false" );
            }
        //Toast.makeText(this,""+_Fajar_alarm, Toast.LENGTH_SHORT).show();
            _fajar(this, false);

       }catch(Exception e){
            Toast.makeText(this,"Not a valid time. ", Toast.LENGTH_SHORT).show();
    }



    }
    public void Zohar(View v)
    {

        EditText _et=(EditText)findViewById(R.id.Zohar_alarm);
        RadioButton RB= (RadioButton) findViewById(R.id.radioButton);
        try {
            String data=_et.getText().toString();
            _Zohar_alarm = Integer.parseInt(data);
            if(RB.isChecked())
            {
                Log.e("azan value","true");
                pref.edit().putBoolean("Fajar_Azan", true).apply();
                pref.edit().commit();
            }else
            {
                pref.edit().putBoolean("Fajar_Azan", false).apply();
                pref.edit().commit();
                Log.e("azan value", "false" );
            }
            //Toast.makeText(this,""+_Fajar_alarm, Toast.LENGTH_SHORT).show();
            _zohar(this, false);

        }catch(Exception e){
            Toast.makeText(this,"Not a valid time. ", Toast.LENGTH_SHORT).show();
        }



    }
    public void Asar(View v)
    {

        EditText _et=(EditText)findViewById(R.id.Asar_alarm);
        RadioButton RB= (RadioButton) findViewById(R.id.radioButton);
        try {
            String data=_et.getText().toString();
            _Asar_alarm = Integer.parseInt(data);
            if(RB.isChecked())
            {
                Log.e("azan value","true");
                pref.edit().putBoolean("Fajar_Azan", true).apply();
                pref.edit().commit();
            }else
            {
                pref.edit().putBoolean("Fajar_Azan", false).apply();
                pref.edit().commit();
                Log.e("azan value", "false" );
            }
            //Toast.makeText(this,""+_Fajar_alarm, Toast.LENGTH_SHORT).show();
            _asar(this, false);

        }catch(Exception e){
            Toast.makeText(this,"Not a valid time. ", Toast.LENGTH_SHORT).show();
        }



    }
    public void Maghrib(View v)
    {

        EditText _et=(EditText)findViewById(R.id.Maghrib_alarm);
        RadioButton RB= (RadioButton) findViewById(R.id.radioButton);
        try {
            String data=_et.getText().toString();
            _Maghrib_alarm = Integer.parseInt(data);
            if(RB.isChecked())
            {
                Log.e("azan value","true");
                pref.edit().putBoolean("Fajar_Azan", true).apply();
                pref.edit().commit();
            }else
            {
                pref.edit().putBoolean("Fajar_Azan", false).apply();
                pref.edit().commit();
                Log.e("azan value", "false" );
            }
            //Toast.makeText(this,""+_Fajar_alarm, Toast.LENGTH_SHORT).show();
            _maghrib(this, false);

        }catch(Exception e){
            Toast.makeText(this,"Not a valid time. ", Toast.LENGTH_SHORT).show();
        }



    }
    public void Esha(View v)
    {

        EditText _et=(EditText)findViewById(R.id.Esha_alarm);
        RadioButton RB= (RadioButton) findViewById(R.id.radioButton);
        try {
            String data=_et.getText().toString();
            _Esha_alarm = Integer.parseInt(data);
            if(RB.isChecked())
            {
                Log.e("azan value","true");
                pref.edit().putBoolean("Fajar_Azan", true).apply();
                pref.edit().commit();
            }else
            {
                pref.edit().putBoolean("Fajar_Azan", false).apply();
                pref.edit().commit();
                Log.e("azan value", "false" );
            }
            _esha(this, false);


        }catch(Exception e){
            Toast.makeText(this,"Not a valid time. ", Toast.LENGTH_SHORT).show();
        }



    }
    public void Mute(View v)
    {
        Mute();
    }
    public void UnMute(View v)
    {
        UnMute();
    }
//==================================================================================================

//========================== Local Functions for computation =======================================
public void _fajar( Context context,Boolean _val_from_Reciever)
{
    Intent myIntent = new Intent(context, DemoAlarmActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, 0);
     AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    Calendar calendar = Calendar.getInstance();
    Date d= new Date();
    String s =df.format(d).replace(":", ".");


    if(Fajar!=null) {
        int[] r = Split_Time(Fajar);



        calendar.set(Calendar.HOUR_OF_DAY, r[0]);
        calendar.set(Calendar.MINUTE, r[1]);
        Calendar now= Calendar.getInstance();
        if(calendar.before(now))
        {
            Log.e("Passes", "value alreday pased");
            calendar.set(Calendar.DATE, (d.getDate() + 1));
        }
        Date ds= calendar.getTime();
        String ss=df.format(ds).replace(":", ".");

        //Toast.makeText(context, r[0] + ":" + r[1], Toast.LENGTH_SHORT).show();
        pref.edit().putBoolean("play", true).apply();



        if (Bool_fajar || _val_from_Reciever) {


            RemainingTime(s, ss, "Fajar");
            Log.e("Calender value", calendar.getTime() + "\n" + calendar.getTimeInMillis());
//================================================Dummy value=======================================
//          alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(),
//                  AlarmManager.INTERVAL_DAY, pendingIntent);
//==================================================================================================
//
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
            Toast.makeText(context, "Start Alarm fajar ", Toast.LENGTH_SHORT).show();
            Bool_fajar = false;
        }
        else
        {
            Toast.makeText(context, "Cancel Alarm fajar", Toast.LENGTH_SHORT).show();
            alarmManager.cancel(pendingIntent);
            Bool_fajar=true;

        }

        pref.edit().commit();
    }
}
public void _zohar( Context context,Boolean _val_from_Reciever)
{
    Intent myIntent = new Intent(context, DemoAlarmActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, 0);
     AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    Calendar calendar = Calendar.getInstance();
    Date d= new Date();
    String s =df.format(d).replace(":", ".");


    if(Zohar_J!=null) {
        int[] r = Split_Time(Zohar_J);
        r[0]=r[0]+12;
        calendar.set(Calendar.HOUR_OF_DAY, r[0]);
        calendar.set(Calendar.MINUTE, r[1]);
        Calendar now= Calendar.getInstance();
       //calendar.set(Calendar.AM_PM,Calendar.AM);
        if(calendar.before(now))
        {
            Log.e("Passes", "value alreday pased");
            calendar.set(Calendar.DATE, (d.getDate() + 1));
        }
        Date ds= calendar.getTime();
        String ss=df.format(ds).replace(":", ".");

        Toast.makeText(context, r[0] + ":" + r[1] +"\n"+calendar.getTime(), Toast.LENGTH_SHORT).show();
        pref.edit().putBoolean("play", true).apply();
        pref.edit().commit();

        if (Bool_Zohar || _val_from_Reciever) {

            RemainingTime(s,ss,"Zohar");

            Log.e("Calender value", calendar.getTime()+"\n"+calendar.getTimeInMillis());
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);

//================================================Dummy value=======================================
//            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(),
//                    AlarmManager.INTERVAL_DAY, pendingIntent);
//==================================================================================================
            Toast.makeText(context, "Start Alarm ZOhar ", Toast.LENGTH_SHORT).show();
            Bool_Zohar = false;
        } else {
            Toast.makeText(context, "Cancel Alarm ZOhar", Toast.LENGTH_SHORT).show();
            alarmManager.cancel(pendingIntent);
            Bool_Zohar=true;

        }
    }
}
public void _asar( Context context,Boolean _val_from_Reciever)
{
    Intent myIntent = new Intent(context, DemoAlarmActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, 0);
     AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    Calendar calendar = Calendar.getInstance();
    Date d= new Date();
    String s =df.format(d).replace(":", ".");


    if(Asar_J!=null) {
        int[] r = Split_Time(Asar_J);
        r[0]=r[0]+12;
        calendar.set(Calendar.HOUR_OF_DAY, r[0]);
        calendar.set(Calendar.MINUTE, r[1]);
        Calendar now= Calendar.getInstance();
        if(calendar.before(now))
        {
            Log.e("Passes", "value alreday pased");
            calendar.set(Calendar.DATE, (d.getDate()+1));
        }
        Date ds= calendar.getTime();
        String ss=df.format(ds).replace(":", ".");

        //Toast.makeText(context, r[0] + ":" + r[1], Toast.LENGTH_SHORT).show();
        pref.edit().putBoolean("play", true).apply();

        pref.edit().commit();

        if (Bool_Asar || _val_from_Reciever) {
            RemainingTime(s,ss,"Asar");
            Log.e("Calender value", calendar.getTime()+"\n"+calendar.getTimeInMillis());
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);

//================================================Dummy value=======================================
//            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(),
//                    AlarmManager.INTERVAL_DAY, pendingIntent);
//==================================================================================================
            Toast.makeText(context, "Start Alarm Asar", Toast.LENGTH_SHORT).show();
            Bool_Asar = false;
        } else {
            Toast.makeText(context, "Cancel Alarm Asar", Toast.LENGTH_SHORT).show();
            alarmManager.cancel(pendingIntent);
            Bool_Asar = true;

        }
    }
}
public void _maghrib( Context context,Boolean _val_from_Reciever)
{
    Intent myIntent = new Intent(context, DemoAlarmActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, 0);
     AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    Calendar calendar = Calendar.getInstance();
    Date d= new Date();
    String s =df.format(d).replace(":", ".");


    if(Maghrib_J!=null) {
        int[] r = Split_Time(Maghrib_J);
        r[0]=r[0]+12;
        calendar.set(Calendar.HOUR_OF_DAY, r[0]);
        calendar.set(Calendar.MINUTE, r[1]);
        Calendar now= Calendar.getInstance();
        if(calendar.before(now))
        {
            Log.e("Passes", "value alreday pased");
            calendar.set(Calendar.DATE, (d.getDate()+1));
        }
        Date ds= calendar.getTime();
        String ss=df.format(ds).replace(":", ".");

        //Toast.makeText(context, r[0] + ":" + r[1], Toast.LENGTH_SHORT).show();
        pref.edit().putBoolean("play", true).apply();
        pref.edit().commit();

        if (Bool_Maghrib || _val_from_Reciever) {
            RemainingTime(s,ss,"Maghrib");
            Log.e("Calender value", calendar.getTime() + "\n" + calendar.getTimeInMillis());
//            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                    AlarmManager.INTERVAL_DAY, pendingIntent);

//================================================Dummy value=======================================
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
//==================================================================================================
            Toast.makeText(context, "Start Alarm Maghrib ", Toast.LENGTH_SHORT).show();
            Bool_Maghrib = false;
        } else {
            Toast.makeText(context, "Cancel Alarm Maghrib", Toast.LENGTH_SHORT).show();
            alarmManager.cancel(pendingIntent);
            Bool_Maghrib=true;

        }
    }
}
public void _esha( Context context,Boolean _val_from_Reciever)
{
    Intent myIntent = new Intent(context, DemoAlarmActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, 0);
     AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    Calendar calendar = Calendar.getInstance();
    Date d= new Date();
    String s =df.format(d).replace(":",".");


    if(Esha_J!=null) {
        int[] r = Split_Time(Esha_J);
        r[0]=r[0]+12;        calendar.set(Calendar.HOUR_OF_DAY, r[0]);
        calendar.set(Calendar.MINUTE, r[1]);
        Calendar now= Calendar.getInstance();
        if(calendar.before(now))
        {
            Log.e("Passes", "value alreday pased");
            calendar.set(Calendar.DATE, (d.getDate()+1));
        }
        Date ds= calendar.getTime();
        String ss=df.format(ds).replace(":", ".");

        //Toast.makeText(context, r[0] + ":" + r[1], Toast.LENGTH_SHORT).show();
        pref.edit().putBoolean("play", true).apply();
        pref.edit().commit();

        if (Bool_Esha || _val_from_Reciever) {
            RemainingTime(s,ss,"Esha");
            Log.e("Calender value", calendar.getTime()+"\n"+calendar.getTimeInMillis());
//            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                    AlarmManager.INTERVAL_DAY, pendingIntent);

//================================================Dummy value=======================================
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
//==================================================================================================
            Toast.makeText(context, "Start Alarm Esha ", Toast.LENGTH_SHORT).show();
            Bool_Esha= false;
        } else {
            Toast.makeText(context, "Cancel Alarm Esha", Toast.LENGTH_SHORT).show();
            alarmManager.cancel(pendingIntent);
            Bool_Esha=true;

        }
    }
}


    public void Mute()
    {
        AudioManager audiomanage = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }
    public void UnMute()
    {
        AudioManager audiomanage = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audiomanage.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }
//==================================================================================================

}// class
