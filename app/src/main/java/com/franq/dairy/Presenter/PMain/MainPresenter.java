package com.franq.dairy.Presenter.PMain;

public interface MainPresenter {

    void openDB();
    void onChangeDate(int day, int month, int year);
    void closeDB();

    boolean checkInternetConnection();

    void checkAuthorization();
}
