package com.franq.dairy.Presenter.PSync;

/**
 * Интерфейс, описывающий представителя состояния синхронизации пользователя
 */
public interface SyncPresenter {

    String getLogin();

    void checkAuthorizatiton();

    void clearAuthorizationData();

}
