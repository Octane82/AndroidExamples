package com.everlapp.androidexamples.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;

public class MyService extends Service {

    private final IBinder mLocalBinder = new MyCustomLocalBinder();

    private final Random mGenerator = new Random();



    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mLocalBinder;
    }


    /** method for clients */
    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }
}
