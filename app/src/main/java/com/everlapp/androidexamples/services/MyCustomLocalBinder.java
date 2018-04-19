package com.everlapp.androidexamples.services;

import android.os.Binder;

public class MyCustomLocalBinder extends Binder {

    // implements own methods

    public MyService getService() {
        // Return this instance of LocalService so clients can call public methods
        return new MyService();
    }

}
