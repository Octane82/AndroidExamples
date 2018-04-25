package com.everlapp.androidexamples;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SimpleNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Notification.Builder builder = new Notification.Builder(this);

        PendingIntent pi = PendingIntent.getBroadcast(this, 0, new Intent(""), 0);
        builder
                .setContentIntent(pi)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                //.setLargeIcon(BitmapFactory.decodeResource())
                .setTicker("Последнее китайское предупреждение")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Напоминание");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Notification notification = builder.build();

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.notify(1, notification);
            }
        }

    }


}
