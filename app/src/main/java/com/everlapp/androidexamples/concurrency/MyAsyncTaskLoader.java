package com.everlapp.androidexamples.concurrency;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

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
