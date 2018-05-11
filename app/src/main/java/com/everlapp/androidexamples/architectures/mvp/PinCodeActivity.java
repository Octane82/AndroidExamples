package com.everlapp.androidexamples.architectures.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class PinCodeActivity extends AppCompatActivity implements PinCodeContract.View {

    private PinCodePresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        presenter = new PinCodePresenter();



        // Attach view to presenter
        presenter.attachView(this);

        // View is ready to work
        presenter.viewIsReady();
    }


    @Override
    public void showMessage(String message) {
        Log.d(this.getClass().getSimpleName(), "Message: " + message);
    }

    @Override
    public String getInfo() {
        return "This is information about ...";
    }
}
