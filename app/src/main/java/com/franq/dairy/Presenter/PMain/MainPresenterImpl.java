package com.franq.dairy.Presenter.PMain;

import android.content.Context;
import android.net.ConnectivityManager;

import com.franq.dairy.Model.DataBase.NotesModel;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.R;
import com.franq.dairy.Utility.PreferencesData;
import com.franq.dairy.View.Activities.MainActivity;
import com.franq.dairy.View.Fragments.NoteFragment;

public class MainPresenterImpl extends BasePresenter<MainActivity> implements MainPresenter {

    private NotesModel model;

    private static final String PREFERENCES = "app";
    private static final String PREFERENCES_LOGIN = "login";
    private static final String PREFERENCES_PASS = "pass";

    @Override
    public void openDB() {
        model = new NotesModel(view.getApplicationContext());
        model.init();
    }

    @Override
    public void onChangeDate(int day, int month, int year) {
        NoteFragment fragment = (NoteFragment) view.getSupportFragmentManager().findFragmentById(R.id.content_main_layout);
        fragment.refreshList(model.getNotesByDate(day, month, year));
    }

    @Override
    public void closeDB() {
        model.close();
    }

    @Override
    public void checkAuthorization() {
        PreferencesData preferencesData = new PreferencesData(view.getApplicationContext());
        String login = preferencesData.getLogin();
        String pass = preferencesData.getPass();

        if (!checkInternetConnection()) {
            view.showFailError("Нет интернета!");
        } else if (login.equals("") & pass.equals("")) {
            view.chooseLoginFragment();
        } else if (checkInternetConnection()) {
            view.chooseSyncInfoFragment();
        }

    }

    @Override
    public boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) view.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
