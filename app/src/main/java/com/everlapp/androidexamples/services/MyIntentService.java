package com.everlapp.androidexamples.services;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

public class MyIntentService extends IntentService {


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
