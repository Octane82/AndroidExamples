package com.everlapp.androidexamples.concurrency;


import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class LoaderExampleActivity
        extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Object> {


    static final int LOADER_ID = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startLoading();
    }


    private void startLoading() {
        getLoaderManager().initLoader(0, null, this);

        // Deprecated !!!
//        Loader<String> loader =
//                getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
//        loader.forceLoad();
    }


    @Override
    public Loader<Object> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object o) {

    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }


    // todo Deprecated !!!

    /*@NonNull
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

    }*/

}
