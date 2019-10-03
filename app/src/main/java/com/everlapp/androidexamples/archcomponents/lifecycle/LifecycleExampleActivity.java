package com.everlapp.androidexamples.archcomponents.lifecycle;

import androidx.lifecycle.Lifecycle;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
