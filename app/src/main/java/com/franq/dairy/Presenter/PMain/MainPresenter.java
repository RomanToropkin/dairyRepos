package com.franq.dairy.Presenter.PMain;

/**
 * Интерфейс, описывающий представителя основной активности
 */
public interface MainPresenter {

    void onChangeNoteFragmentData(int day, int month, int year);


    boolean checkInternetConnection();

    void checkAuthorization();

}
