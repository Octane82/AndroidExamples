package com.everlapp.androidexamples.archcomponents.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.everlapp.androidexamples.R;



public class ViewModelExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ViewModel нужен, чтобы сохранить процесс получения данных при повороте экрана
        MyViewModel model = ViewModelProviders.of(this).get(MyViewModel.class);
        // LiveData нужен для удобного асинхронного получения данных.
        LiveData<String> data = model.getData();
        data.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // ...
            }
        });
    }
}
