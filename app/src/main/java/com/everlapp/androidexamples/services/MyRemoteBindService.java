package com.everlapp.androidexamples.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.everlapp.androidexamples.ICallback;
import com.everlapp.androidexamples.ITestService;

public class MyRemoteBindService extends Service {

    ICallback mActivityCallback;


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


    /**
     * Преимущества! Сервис может находиться в другом процессе.
     *
     * AIDL - https://developer.android.com/guide/components/aidl.html
     *
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // return the interface
        return mBinder;
    }



    private final ITestService.Stub mBinder = new ITestService.Stub() {
        @Override
        public String getString() throws RemoteException {
//            Process.myPid();
            return "Say Hello!";
        }

        @Override
        public void getStringAsync() throws RemoteException {
            mActivityCallback.onNewString("Hello from Service");
        }

        // todo Bind activity !!!
        @Override
        public void bindActivity(IBinder callback) throws RemoteException {
            mActivityCallback = ICallback.Stub.asInterface(callback);
        }
    };

}
