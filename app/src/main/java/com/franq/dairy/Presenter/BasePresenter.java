package com.franq.dairy.Presenter;

public abstract class BasePresenter<V> {

    protected V view;

    public void onAttachView(V view) {
        this.view = view;
    }

    public void onDetachView() {
        view = null;
    }


}
