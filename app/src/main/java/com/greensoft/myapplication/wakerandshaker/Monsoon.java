package com.greensoft.myapplication.wakerandshaker;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Monsoon extends BroadcastReceiver {

    public static final String ALARM_TYPE_REPEAT = "ALARM_TYPE_REPEAT";
    public static final String ALARM_TYPE = "ALARM_TYPE";
    public static final String ALARM_DESCRIPTION = "ALARM_DESCRIPTION";

    private AlarmManager alarmManager = null;

    private PendingIntent pendingIntent = null;

    @SuppressLint("UnspecifiedImmutableFlag")
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent intent_m = new Intent(context, AlarmTriggerBroadcastReceiver.class);
            intent_m.putExtra(ALARM_TYPE, ALARM_TYPE_REPEAT);
            intent_m.putExtra(ALARM_DESCRIPTION, "Repeat alarm start this broadcast.");
            pendingIntent = PendingIntent.getBroadcast(context, 0, intent_m, PendingIntent.FLAG_UPDATE_CURRENT);

            long alarmStartTime = System.currentTimeMillis();
            // This is too short, it will be expanded by android os to 60 seconds by default.
            long alarmExecuteInterval = 10*1000;
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmExecuteInterval, pendingIntent);

          //  Toast.makeText(context, "A repeat alarm has been created. This alarm will send to a broadcast receiver.", Toast.LENGTH_LONG).show();
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }
}