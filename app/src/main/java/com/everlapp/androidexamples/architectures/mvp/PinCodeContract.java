package com.everlapp.androidexamples.architectures.mvp;

public interface PinCodeContract {

    interface View extends MvpView {
        void showMessage(String message);

        String getInfo();
    }

    interface Presenter extends MvpPresenter<View> {
        void onGetMessage();
    }

}
