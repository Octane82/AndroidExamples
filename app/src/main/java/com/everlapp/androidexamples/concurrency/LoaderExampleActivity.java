package com.everlapp.androidexamples.concurrency;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

public class LoaderExampleActivity extends AppCompatActivity
                            implements LoaderManager.LoaderCallbacks<String> {

    static final int LOADER_ID = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startLoading();
    }



    private void startLoading() {
        Loader<String> loader =
                getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
        loader.forceLoad();
    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        Loader<String> loader = null;
        if (id == LOADER_ID) {
            loader = new MyAsyncTaskLoader(this);
            // showProgressDialog()
        }

        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        // hideProgressDialog()
        // updateUI()

        // Create and start new loader (IF NECESSARY !!!)
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
