package com.franq.dairy.Presenter.PLogin;

/**
 * Интерфейс, описывающий представителя логирования в системе
 */
public interface LoginPresenter {

    void authenticateUser(String login, String pass, String cookie);

}
