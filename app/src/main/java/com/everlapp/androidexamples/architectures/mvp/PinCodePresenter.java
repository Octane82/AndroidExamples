package com.everlapp.androidexamples.architectures.mvp;

public class PinCodePresenter extends PresenterBase<PinCodeContract.View>
                                            implements PinCodeContract.Presenter {



    @Override
    public void onGetMessage() {
        getView().showMessage("On get message YAY");
    }

    @Override
    public void viewIsReady() {

    }
}
