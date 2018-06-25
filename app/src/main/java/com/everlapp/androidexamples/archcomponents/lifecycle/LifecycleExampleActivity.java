package com.everlapp.androidexamples.archcomponents.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.everlapp.androidexamples.R;

public class LifecycleExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyServer server = new MyServer();
        getLifecycle().addObserver(server);

        // getLifecycle().removeObserver(server);  Unsubscribe observer

        if (getLifecycle().getCurrentState() == Lifecycle.State.RESUMED) {
            // Текущее состояние Activity
        }

        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            // Проверка что текущее состояние Activity не ниже определенного состояния.
        }
    }
}
