package com.everlapp.androidexamples.concurrency;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.loader.content.Loader;

public class TimeLoader extends Loader {
    /**
     * Stores away the application context associated with context.
     * Since Loaders can be used across multiple activities it's dangerous to
     * store the context directly; always use {@link #getContext()} to retrieve
     * the Loader's Context, don't use the constructor argument directly.
     * The Context returned by {@link #getContext} is safe to use across
     * Activity instances.
     *
     * @param context used to retrieve the application context.
     */
    public TimeLoader(@NonNull Context context) {
        super(context);
    }


}
