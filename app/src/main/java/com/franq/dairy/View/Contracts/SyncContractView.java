package com.franq.dairy.View.Contracts;

import android.view.View;

public interface SyncContractView extends BaseContractView {

    void refreshList();

    void onLogoutButtonClick(View view);

    void changeStatus(boolean boolStatus);

    void showLoading();

    void hideLoading();

}
