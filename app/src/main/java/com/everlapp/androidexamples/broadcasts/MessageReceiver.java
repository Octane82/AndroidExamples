package com.everlapp.androidexamples.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MessageReceiver extends BroadcastReceiver {

    public MessageReceiver() {
    }


    /**
     * После приема сообщения фактически завершится
     * и context станет не валидным, поэтому его не надо передавать
     *
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Message received: " +
                intent.getStringExtra(BroadcastsExampleActivity.TEST_BR),           // Get message
                Toast.LENGTH_LONG)
                .show();
    }
}
