package com.microcave.masjidtimetable.util.classes;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class AlarmService extends Service {
    public AlarmService() {
    }


    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
    Ringtone r;

    SharedPreferences pref;
    @Override

    public void onCreate() {

// TODO Auto-generated method stub

        Toast.makeText(this, "MyAlarmService.onCreate()", Toast.LENGTH_LONG).show();
        pref= this.getSharedPreferences("com.microcave.masjidtimetable", Context.MODE_PRIVATE);
    }



    @Override

    public IBinder onBind(Intent intent) {

// TODO Auto-generated method stub

        Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG).show();
        r= RingtoneManager.getRingtone( getApplicationContext(), notification);
        //r.play();
        return null;

    }



    @Override

    public void onDestroy() {

// TODO Auto-generated method stub

        super.onDestroy();
        r.stop();
        Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show();

    }



    @Override

    public void onStart(Intent intent, int startId) {

// TODO Auto-generated method stub

        super.onStart(intent, startId);

        Toast.makeText(this, "MyAlarmService.onStart()", Toast.LENGTH_LONG).show();
        r= RingtoneManager.getRingtone( getApplicationContext(), notification);

      boolean play= pref.getBoolean("play",false);
        Log.e("play", ""+play);

        r.stop();


    }



    @Override

    public boolean onUnbind(Intent intent) {

// TODO Auto-generated method stub

        Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG).show();
        r.stop();
        return super.onUnbind(intent);

    }




}
