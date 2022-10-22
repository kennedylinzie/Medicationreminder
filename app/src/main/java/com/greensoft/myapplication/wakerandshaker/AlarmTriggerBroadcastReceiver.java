package com.greensoft.myapplication.wakerandshaker;

import static android.content.ContentValues.TAG;
import static android.content.Context.JOB_SCHEDULER_SERVICE;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.greensoft.myapplication.notifcation_monolith.My_notification_service;

public class AlarmTriggerBroadcastReceiver extends BroadcastReceiver {

    private final static String TAG_ALARM_TRIGGER_BROADCAST = "ALARM_TRIGGER_BROADCAST";

    public static final String ALARM_TYPE_REPEAT = "ALARM_TYPE_REPEAT";
    public static final String ALARM_TYPE = "ALARM_TYPE";
    public static final String ALARM_DESCRIPTION = "ALARM_DESCRIPTION";

    private AlarmManager alarmManager = null;
    private PendingIntent pendingIntent = null;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String alarmType = intent.getStringExtra(ALARM_TYPE);

        String alarmDescription = intent.getStringExtra(ALARM_DESCRIPTION);

        Log.d(TAG_ALARM_TRIGGER_BROADCAST, alarmDescription);

      //  Toast.makeText(context, alarmDescription, Toast.LENGTH_LONG).show();

      foregroundServicesRunning(context);

    }
    public void foregroundServicesRunning(Context context) {

        ComponentName componentName = new ComponentName(context, My_notification_service.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(false)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();
        JobScheduler scheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        int resultcode = scheduler.schedule(info);
        if (resultcode == JobScheduler.RESULT_SUCCESS) {
           // Log.d(TAG, "JOB SCHEDULED");
           // Toast.makeText(context,"JOB SCHEDULED",Toast.LENGTH_SHORT).show();
        } else {
           // Toast.makeText(context,"JOB SCHEDULing failed",Toast.LENGTH_SHORT).show();

        }

    }
}