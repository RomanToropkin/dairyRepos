package com.franq.dairy.Presenter;

/**
 * Абстрактный класс представителя.
 */
public abstract class BasePresenter<V> {

    /**
     * View элемент, подконтрольный представлению
     */
    protected V view;

    /**Связать view с представителем
     * @param view view элемент*/
    public void onAttachView(V view) {
        this.view = view;
    }

    /**Отвязать view от представителя*/
    public void onDetachView() {
        view = null;
    }


}
