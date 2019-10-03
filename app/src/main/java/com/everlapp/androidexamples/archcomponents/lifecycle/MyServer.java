package com.everlapp.androidexamples.archcomponents.lifecycle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import android.util.Log;

public class MyServer implements LifecycleObserver {

    private final String TAG = this.getClass().getSimpleName();

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void connect() {
        Log.d(TAG, "Server connected! (lifecycle)");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void disconnect() {
        Log.d(TAG, "Server disconnected! (lifecycle)");
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onAny(LifecycleOwner source, Lifecycle.Event event) {
        // Получаем все события жизненного цикла в этом методе
    }
}
