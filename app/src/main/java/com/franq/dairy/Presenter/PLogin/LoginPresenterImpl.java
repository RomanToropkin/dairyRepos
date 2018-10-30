package com.franq.dairy.Presenter.PLogin;

import android.util.Log;

import com.franq.dairy.Model.Server.JsonModels.Result;
import com.franq.dairy.Model.Server.Server;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.Utility.PreferencesData;
import com.franq.dairy.View.Fragments.LoginFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Представитель, авторизирующий пользователя в системе
 **/
public class LoginPresenterImpl extends BasePresenter<LoginFragment> implements LoginPresenter {

    /**Объект серверного взаимодействия*/
    private Server server;
    /**Хранилище настроек*/
    private PreferencesData data;

    /**Переопределение базового метода с инициализацией хранилища настроек*/
    @Override
    public void onAttachView(LoginFragment view) {
        super.onAttachView(view);
        data = new PreferencesData(view.getContext());
    }

    /**Отправление запроса на сервер с целью авторизации пользователя.
     * Во время обработки данных крутиться спиннер.
     * После завершения происходит смена view.
     * @see LoginFragment#refreshFragment()
     * @param login логин
     * @param pass пароль*/
    @Override
    public void authenticateUser(String login, String pass, String cookie) {
        view.showLoading();
        view.setClicked(true);
        server = new Server();
        server.loginUser(login, pass, new Callback<Result>() {
            //В случаи успеха получение ответа
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("http", response.message());
                //Обработка ответа от сервера
                Result result = response.body();
                if (result == null) {
                    view.showError("Ошибка подключения к серверу!");
                } else if (result.getResult().equals("ok")) {
                    String c1 = response.headers().get("Set-Cookie");
                    data.addCookie(c1);
                    data.addLoginPass(login, pass);
                    view.hideLoading();
                    view.refreshFragment();
                } else if (result.getResult().equals("login or pass is not current")) {
                    view.showError("Неверный логин или пароль!");
                    view.setClicked(false);
                    view.hideLoading();
                }
            }

            //В случаи ошибки получение ответа
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("http", t.getMessage());
                view.showError("Ошибка подключения к серверу!");
                view.hideLoading();
                view.setClicked(false);
            }
        });
    }
}
