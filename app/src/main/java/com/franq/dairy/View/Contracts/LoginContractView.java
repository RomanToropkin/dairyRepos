package com.franq.dairy.View.Contracts;

import android.view.View;

/**
 * Интерфейс, описывающий поведение отображения авторизации клиента
 */
public interface LoginContractView extends BaseContractView {

    void showError(String errorText);

    void refreshFragment();

    void onButtonLoginClick(View view);

    void onRegisterButtonClick(View view);

    void showLoading();

    void hideLoading();
}
