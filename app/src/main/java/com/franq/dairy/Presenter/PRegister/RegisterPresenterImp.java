package com.franq.dairy.Presenter.PRegister;

import com.franq.dairy.Model.Server.JsonModels.Result;
import com.franq.dairy.Model.Server.Server;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.Utility.PreferencesData;
import com.franq.dairy.View.Fragments.RegisterFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Представитель, регистрирующий пользователя в системе
 **/
public class RegisterPresenterImp extends BasePresenter<RegisterFragment> implements RegisterPresenter {

    /**Объект серверного взаимодействия*/
    private Server server;

    /**Инициализация сервера*/
    public RegisterPresenterImp() {
        server = new Server();
    }

    /**Отправление запроса на сервер с целью регистрации пользователя
     * Переход на фрагмент с записями
     * @param login логин
     * @param pass пароль*/
    @Override
    public void registerUser(String login, String pass) {
        view.showLoading();
        view.setClick(true);
        server.registerUser(login, pass, new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                if (result == null) {
                    view.showError("Ошибка подключения к серверу!");
                } else {
                    if (result.getResult().equals("ok")) {
                        PreferencesData data = new PreferencesData(view.getContext());
                        String c1 = response.headers().get("Set-Cookie");
                        data.addCookie(c1);
                        data.addLoginPass(login, pass);
                        view.refreshFragment();
                    } else {
                        view.showError("Такой пользователь уже существует!");
                        view.setClick(false);
                    }
                }
                view.hideLoading();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                view.showError("Ошибка подключения к серверу!");
                view.hideLoading();
                view.setClick(false);
            }
        });
    }
}
