package com.microcave.masjidtimetable.util.classes;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.microcave.masjidtimetable.DashBoard;
import com.microcave.masjidtimetable.R;
import com.microcave.masjidtimetable.Setting;

import java.io.IOException;

public class DemoAlarmActivity extends ActionBarActivity {
    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
    Ringtone r;
    MediaPlayer mp;
    SharedPreferences pref;
    boolean play=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_alarm);
        pref= this.getSharedPreferences("com.microcave.masjidtimetable", Context.MODE_PRIVATE);


    }


    @Override
    protected void onStart() {
        super.onStart();
        Boolean azan=pref.getBoolean("Fajar_Azan", false);
        if(azan)
        {
            Log.e("azan value",""+azan);
            mp=MediaPlayer.create(this,R.raw.azan);                 //azan  /prayer
        }
        else {
            Log.e("azan value",""+azan);
            mp = MediaPlayer.create(this, notification);            //simple ringtine
        }

//        NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE) ;
//        Notification n  = new Notification.Builder(this)
//		.setContentTitle("Prayer Notification")
//		.setContentText("Jamat Prayer alert!!!!").build();
//        nm.notify(0, n);
//

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                + WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                +WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                +WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);           // for waking lock
        
        PowerManager pm = (PowerManager)getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                                                PowerManager.ACQUIRE_CAUSES_WAKEUP,
                "TRAININGCOUNTDOWN");
        wl.acquire();

        r= RingtoneManager.getRingtone( getApplicationContext(), notification);
     //  boolean play= pref.getBoolean("play",false);
        Log.e("play", "" + play);
       // t.start();
        mp.start();
        mp.setLooping(true);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mp.stop();


    }

    public  void stop(View v)
    {
        play=false;
       // r.stop();
        mp.stop();




    }
    public  void dashboard(View v)
    {
        mp.stop();

        Intent i = new Intent(this, DashBoard.class);
        startActivity(i);
    }
}
