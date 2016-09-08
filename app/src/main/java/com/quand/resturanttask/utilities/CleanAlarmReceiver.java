package com.quand.resturanttask.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Created by salmohamady on 9/9/2015.
 */
public class CleanAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmReceiver", "AlarmReceiver");

        DBHandler handler = new DBHandler(context);
        handler.clearReservations();

    }
}