package com.franq.dairy.View.Contracts;

import android.view.View;

import com.franq.dairy.Model.DataBase.Note;

/**
 * Интерфейс, описывающий поведение отображения главной активности
 */
public interface MainContractVIew extends BaseContractView {

    void onAddButtonClick(View view);

    void showFailError(String error);

    void showMainView();

    void hideMainView();

    void chooseNoteFragment();

    void chooseDairyNoteFragment(Note item);

    void chooseSettingsFragment();

    void chooseCreatingFragment();

    void chooseLoginFragment();

    void chooseRegisterFragment();

    void chooseSyncInfoFragment();

    void showBlankFragment();

    void hideBlankFragment();
}
