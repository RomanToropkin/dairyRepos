package com.franq.dairy.Presenter.PMain;

import android.content.Context;
import android.net.ConnectivityManager;

import com.franq.dairy.Model.local.NotesModel;
import com.franq.dairy.Model.local.PreferencesData;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.Utility.NoteDate;
import com.franq.dairy.View.Activities.MainActivity;

/**
 * Представитель, взаимодействующий с главной активностью
 */
public class MainPresenterImpl extends BasePresenter<MainActivity> implements MainPresenter {
    /**Репозиторий*/
    private NotesModel model;

    @Override
    public void onDetachView() {
        super.onDetachView( );
    }

    /**Смена записей во фрагменте с записями
     * @param day день
     * @param month десяц
     * @param year год*/
    @Override
    public void onChangeNoteFragmentData(int day, int month, int year) {
        String date = day + "." + month + "." + year;
        NoteDate.setPickedDate( date );
        view.chooseNoteFragment( );
    }

    @Override
    public void onAttachView(MainActivity view) {
        super.onAttachView( view );
        model = NotesModel.getInstance( view.getApplicationContext( ) );
    }

    /**Проверка на авторизированного пользователя*/
    @Override
    public void checkAuthorization() {
        //Поиск данных о клиенте во внутреннем хранилище
        PreferencesData preferencesData = PreferencesData.getInstance( view.getApplicationContext( ) );
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
