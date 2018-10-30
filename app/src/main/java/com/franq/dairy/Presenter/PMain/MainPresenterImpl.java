package com.franq.dairy.Presenter.PMain;

import android.content.Context;
import android.net.ConnectivityManager;

import com.franq.dairy.Model.DataBase.NotesModel;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.R;
import com.franq.dairy.Utility.PreferencesData;
import com.franq.dairy.View.Activities.MainActivity;
import com.franq.dairy.View.Fragments.NoteFragment;

/**
 * Представитель, взаимодействующий с главной активностью
 */
public class MainPresenterImpl extends BasePresenter<MainActivity> implements MainPresenter {
    /**Репозиторий*/
    private NotesModel model;

    /**Связь модели с представителем и инициализация БД*/
    @Override
    public void openDB() {
        model = new NotesModel(view.getApplicationContext());
        model.init();
    }

    /**Смена записей во фрагменте с записями
     * @param day день
     * @param month десяц
     * @param year год*/
    @Override
    public void onChangeNoteFragmentData(int day, int month, int year) {
        NoteFragment fragment = (NoteFragment) view.getSupportFragmentManager().findFragmentById(R.id.content_main_layout);
        fragment.refreshList(model.getNotesByDate(day, month, year));
    }

    /**Закрытие БД*/
    @Override
    public void closeDB() {
        model.close();
    }

    /**Проверка на авторизированного пользователя*/
    @Override
    public void checkAuthorization() {
        //Поиск данных о клиенте во внутреннем хранилище
        PreferencesData preferencesData = new PreferencesData(view.getApplicationContext());
        String login = preferencesData.getLogin();
        String pass = preferencesData.getPass();

        if (!checkInternetConnection()) {
            view.showFailError("Нет подключения к интернету!");
        } else if (login.equals("") & pass.equals("")) {
            view.chooseLoginFragment();
        } else if (checkInternetConnection()) {
            view.chooseSyncInfoFragment();
        }

    }

    /**Проверка соединения с интернетом*/
    @Override
    public boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) view.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
