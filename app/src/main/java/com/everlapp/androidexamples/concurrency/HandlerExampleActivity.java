package com.everlapp.androidexamples.concurrency;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class HandlerExampleActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HandlerWorkingClass workingClass = new HandlerWorkingClass(true, textView);
        Thread thread = new Thread(workingClass);
        thread.start();

        // HandlerThread
    }
}
