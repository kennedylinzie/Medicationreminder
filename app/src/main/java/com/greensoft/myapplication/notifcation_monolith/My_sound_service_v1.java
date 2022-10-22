package com.greensoft.myapplication.notifcation_monolith;

import static com.greensoft.myapplication.notifcation_monolith.NOTIFICATION_CHANNEL.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.greensoft.myapplication.R;

public class My_sound_service_v1 extends Service {



    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String input = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(this, The_alarm_man.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("Time to take your medicine")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_baseline_notifications)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1,notification);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
