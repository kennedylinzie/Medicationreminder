package com.greensoft.myapplication.notifcation_monolith;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NOTIFICATION_CHANNEL extends Application {
    public static final String CHANNEL_ID = "meds_notification_servicewoman";
    @Override
    public void onCreate() {
        super.onCreate();
        createNotifcationChannel();
    }



    private void createNotifcationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceChanel = new NotificationChannel(
                    CHANNEL_ID,
                    "meds_service_channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChanel);
        }

    }
}
