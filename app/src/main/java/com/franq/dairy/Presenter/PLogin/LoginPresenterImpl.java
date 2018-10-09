package com.franq.dairy.Presenter.PLogin;

import com.franq.dairy.Model.Server.JsonModels.Result;
import com.franq.dairy.Model.Server.Server;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.Utility.PreferencesData;
import com.franq.dairy.View.Fragments.LoginFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenterImpl extends BasePresenter<LoginFragment> implements LoginPresenter {

    private static final String PRESENTER_TAG = "loginPresenter";
    private Server server;
    private PreferencesData data;

    @Override
    public void onAttachView(LoginFragment view) {
        super.onAttachView(view);
        data = new PreferencesData(view.getContext());
    }

    @Override
    public void authenticateUser(String login, String pass) {
        server = new Server();
        server.loginUser(login, pass, new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                if (result == null) {
                    view.showError("Ошибка подключения к серверу!");
                } else if (result.getResult().equals("ok")) {
                    data.addLoginPass(login, pass);
                    view.refreshFragment();
                } else if (result.getResult().equals("login or pass is not current")) {
                    view.showError("Неверный логин или пароль!");
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                view.showError("Ошибка подключения к серверу!");
            }
        });
    }
}
