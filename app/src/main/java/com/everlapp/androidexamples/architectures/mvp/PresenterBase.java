package com.everlapp.androidexamples.architectures.mvp;


/**
 * базовый класс, который будет наследоваться всеми презентерами.
 * @param <T>
 */
public abstract class PresenterBase<T extends MvpView> implements MvpPresenter<T> {

    private T view;

    @Override
    public void attachView(T mvpView) {
        view = mvpView;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void destroy() { }


    public T getView() {
        return view;
    }

    protected boolean isViewAttached() {
        return view != null;
    }

}
