package com.everlapp.androidexamples.archcomponents.livedata;

import android.arch.lifecycle.LiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * LiveData is an observable data holder. It notifies observers when data
 * changes so that you can update the UI.
 */
public class NetworkLiveData extends LiveData<String> {

    private Context context;
    private BroadcastReceiver broadcastReceiver;
    private static NetworkLiveData instance;

    public static NetworkLiveData getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkLiveData(context.getApplicationContext());
        }
        return instance;
    }


    private NetworkLiveData(Context context) {
        this.context = context;
    }


    @Override
    protected void onActive() {
        prepareReceiver(context);
    }

    @Override
    protected void onInactive() {
        context.unregisterReceiver(broadcastReceiver);
        broadcastReceiver = null;
    }

    private void prepareReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.wifi.supplicant.CONNECTION_CHANGE");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                WifiManager wifiMgr = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
                String name = wifiInfo.getSSID();
                if (name.isEmpty()) {
                    setValue(null);
                } else {
                    setValue(name);
                }
            }
        };
        context.registerReceiver(broadcastReceiver, filter);
    }
}
