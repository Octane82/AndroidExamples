package com.everlapp.androidexamples.services;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.everlapp.androidexamples.ICallback;
import com.everlapp.androidexamples.ITestService;

public class ServicesExampleActivity extends AppCompatActivity {

    private MyService mLocalService;
    boolean mBound = false;

    // with AIDL
    private ITestService mTestService;
    private ICallback.Stub mServiceCallback = new ServiceCallback();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bind service without AIDL
        Intent intent1 = new Intent(this, MyService.class);
        bindService(intent1, mLocalServiceConnection, BIND_AUTO_CREATE);
        // get result from service
        if (mBound) {
            int result = mLocalService.getRandomNumber();
        }


        // Bind service with AIDL
        Intent intent = new Intent(this, MyRemoteBindService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);


        // Start JobScheduler
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ComponentName serviceComponent = new ComponentName(this, ServiceJob.class);
            JobInfo.Builder builder = new JobInfo.Builder(1, serviceComponent);
            /*builder.setBackoffCriteria(initialBackoff, policy)
                    .setExtras(bundle)
                    .setMinimumLatency(latency)
                    .setOverrideDeadline(maxDelay)
                    .setPeriodic(period)
                    .setPersisted(isPersisted)
                    .setRequiredNetworkType(type)
                    .setRequiresCharging(isRequiresCharging)
                    .setRequiresDeviceIdle(isRequiresIdle)
                    .setTriggerContentMaxDelay(maxDelay)
                    .setTriggerContentUpdateDelay(delay);*/
            JobInfo info = builder.build();

            JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            if (scheduler != null) {
                scheduler.schedule(info);
            }
        }


    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }

    /**
     * Связь с удаленным сервисом (или компонентом)
     */
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mTestService = ITestService.Stub.asInterface(service);
            try {
                mTestService.bindActivity(mServiceCallback);
                mTestService.getString();

                // mServiceButton.setText("Connected")
            } catch (RemoteException e) {
                // Connection error
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };



    private class ServiceCallback extends ICallback.Stub {

        @Override
        public void onNewString(String data) throws RemoteException {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // textView.setText(data);
                }
            });
        }
    }




    private ServiceConnection mLocalServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyCustomLocalBinder binder = (MyCustomLocalBinder) service;
            mLocalService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };


}
