package com.everlapp.androidexamples.concurrency;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class HandlerWorkingClass implements Runnable {

    public static final int SUCCESS = 1;

    public static final int FAIL = 2;

    private boolean dummyResult;

    private TextView textView;

    public HandlerWorkingClass(boolean dummyResult, TextView textView) {
        this.dummyResult = dummyResult;
        this.textView = textView;
    }

    @Override
    public void run() {
        // фоновая работа
        // Looper.prepare();

        // отправить хэндлеру сообщение
        if (dummyResult) {
            // Можно отправить пустое сообщение со статусом
            uiHandler.sendEmptyMessage(SUCCESS);
        } else {
            // Или передать вместе с сообщением данные
            Message message = Message.obtain();
            message.what = FAIL;
            message.obj = "An error occurred";
            uiHandler.sendMessage(message);
        }

        // Looper.loop();
    }


    // По умолчанию Handler создается на main потоке
    Handler uiHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case HandlerWorkingClass.SUCCESS:
                    textView.setText("Success");
                    return true;
                case HandlerWorkingClass.FAIL:
                    textView.setText((String) message.obj);
                    return true;
            }
            return false;
        }
    });


}
