package com.everlapp.androidexamples.architectures.mvp;

public interface MvpPresenter<V extends MvpView> {

    /**
     * метод для передачи View презентеру. Т.е. View вызовет его и передаст туда себя.
     * @param mvpView
     */
    void attachView(V mvpView);

    /**
     * сигнал презентеру о том, что View готово к работе. Презентер может начинать, например, загружать данные.
     */
    void viewIsReady();

    /**
     * презентер должен отпустить View
     */
    void detachView();

    /**
     * сигнал презентеру о том, что View завершает свою работу и будет закрыто
     */
    void destroy();

}
