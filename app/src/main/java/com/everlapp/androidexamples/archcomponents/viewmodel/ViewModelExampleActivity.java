package com.everlapp.androidexamples.archcomponents.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.everlapp.androidexamples.R;

import timber.log.Timber;


public class ViewModelExampleActivity extends AppCompatActivity {

    private MyViewModel viewModel;

    private EditText etName;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);

        // ViewModel нужен, чтобы сохранить процесс получения данных при повороте экрана
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        // LiveData нужен для удобного асинхронного получения данных.
        LiveData<String> data = viewModel.getData();
        data.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // ...
                // Timber.d("On changed String: " + s);
            }
        });

        // Get name
        viewModel.getNameData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Timber.d("Get stored NAME: " + s);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        viewModel.setNameData(etName.getText().toString());
    }


    public void onClickNext(View view) {
        startActivity(new Intent(this, NextActivity.class));
    }


}
