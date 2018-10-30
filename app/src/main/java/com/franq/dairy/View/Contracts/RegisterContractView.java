package com.franq.dairy.View.Contracts;

import android.view.View;

/**
 * Интерфейс, описывающий поведение отображения регистрации клиента
 */
public interface RegisterContractView extends BaseContractView {

    void showError(String text);

    void showLoading();

    void hideLoading();

    void onRegisterButtonClick(View view);

    void refreshFragment();

}
