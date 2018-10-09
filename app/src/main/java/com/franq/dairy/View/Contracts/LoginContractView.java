package com.franq.dairy.View.Contracts;

import android.view.View;

public interface LoginContractView extends BaseContractView {

    void showError(String errorText);

    void refreshFragment();

    void onButtonLoginClick(View view);

    void onRegisterButtonClick(View view);
}
