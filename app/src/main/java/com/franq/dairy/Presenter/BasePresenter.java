package com.franq.dairy.Presenter;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Абстрактный класс представителя.
 */
public abstract class BasePresenter<V> {

    /**
     * View элемент, подконтрольный представлению
     */
    protected V view;
    protected CompositeDisposable disposables = new CompositeDisposable( );
    protected String TAG;

    /**Связать view с представителем
     * @param view view элемент*/
    public void onAttachView(V view) {
        this.view = view;
        TAG = view.getClass( ).getSimpleName( );
    }

    /**Отвязать view от представителя*/
    public void onDetachView() {
        view = null;
        if ( disposables != null ) {
            disposables.dispose( );
        }
    }


}
