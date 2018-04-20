package com.everlapp.androidexamples.workwithdata;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SharedPreferencesExample extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Context.getSharedPreferences()
        // Activity.getPreferences


        SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("value", 1);
        editor.apply();

        int value = sharedPreferences.getInt("value", -1);
    }


}
