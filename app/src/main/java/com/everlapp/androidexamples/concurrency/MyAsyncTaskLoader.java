package com.everlapp.androidexamples.concurrency;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public class MyAsyncTaskLoader extends AsyncTaskLoader<String> {


    public MyAsyncTaskLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public String loadInBackground() {
        // return Utils.loadData(url)
        return null;
    }
}
