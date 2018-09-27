package com.franq.dairy.Presenter.PMain;

import android.app.Activity;
import android.support.v4.app.FragmentManager;

import com.franq.dairy.View.DairyNoteFragment;


public interface MainPresenter {

    void attachView(Activity activity, FragmentManager fragmentManager);
    void detachView();
    void openDB();
    void openAddNoteFragment();
    void openNotesFragment();
    void openSettingsFragment();
    void openSynFragment();
    void openStartFragment();
    void openDairyNoteFragment(DairyNoteFragment fragment);
    void refreshStack();
    int getStackSize();
    void onChangeDate(int day, int month, int year);
    void closeDB();
    void testServer();

}
