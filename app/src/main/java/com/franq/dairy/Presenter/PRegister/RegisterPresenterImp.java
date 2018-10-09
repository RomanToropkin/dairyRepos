package com.franq.dairy.Presenter.PRegister;

import com.franq.dairy.Model.Server.JsonModels.Result;
import com.franq.dairy.Model.Server.Server;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.Utility.PreferencesData;
import com.franq.dairy.View.Fragments.RegisterFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenterImp extends BasePresenter<RegisterFragment> implements RegisterPresenter {

    private Server server;

    public RegisterPresenterImp() {
        server = new Server();
    }

    @Override
    public void registerUser(String login, String pass) {
        view.showLoading();
        server.registerUser(login, pass, new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                if (result.getResult().equals("ok")) {
                    PreferencesData data = new PreferencesData(view.getContext());
                    data.addLoginPass(login, pass);
                } else {
                    view.showError("Error : " + response.body().getResult());
                }
                view.hideLoading();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                view.showError("Error : " + t.getMessage());
                view.hideLoading();
            }
        });
    }
}
