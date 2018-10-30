package com.franq.dairy.Presenter.PMain;

/**
 * Интерфейс, описывающий представителя основной активности
 */
public interface MainPresenter {

    void openDB();

    void onChangeNoteFragmentData(int day, int month, int year);

    void closeDB();

    boolean checkInternetConnection();

    void checkAuthorization();

}
