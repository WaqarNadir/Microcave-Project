package com.microcave.masjidtimetable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TimeChangedReciever extends BroadcastReceiver {
    public TimeChangedReciever() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Time is changed", Toast.LENGTH_SHORT).show();
       // Setting s = new Setting();
      //  s._fajar(context,true);
    }
}
