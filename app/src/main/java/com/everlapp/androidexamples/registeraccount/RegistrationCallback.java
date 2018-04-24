package com.everlapp.androidexamples.registeraccount;

public interface RegistrationCallback {

    void onSuccess(String msg);

    void onError(Throwable t);

}
