package com.everlapp.androidexamples.broadcasts;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class BroadcastsExampleActivity extends AppCompatActivity {

    public static final String TEST_BR = "com.everlapp.broadcasts.TEST_BR";

    public static final String REQUIRED_PERMISSION = "com.everlapp.broadcasts.TEST_BR_PERMISSION";

    private MessageReceiver receiver;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        receiver = new MessageReceiver();

        IntentFilter filter = new IntentFilter(TEST_BR);

        registerReceiver(receiver, filter);

    }


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }




    public void sendBroadcastTest() {
        Intent intent = new Intent(TEST_BR);
        intent.putExtra("data1", 3.14f);
        intent.putExtra("data2", "Hello message");
        sendBroadcast(intent);
    }


    public void sendOrderedBroadcastTest() {
        Intent intent = new Intent(TEST_BR);
        intent.putExtra("data1", 3.14f);
        intent.putExtra("data2", "Hello message");
        sendOrderedBroadcast(intent, REQUIRED_PERMISSION);
    }


}
