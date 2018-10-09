package com.franq.dairy.Presenter.PSync;

import com.franq.dairy.Model.Server.JsonModels.Result;
import com.franq.dairy.Model.Server.Server;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.Utility.PreferencesData;
import com.franq.dairy.View.Fragments.SyncInfoFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncPresenterImpl extends BasePresenter<SyncInfoFragment> implements SyncPresenter {

    private PreferencesData data;

    @Override
    public void onAttachView(SyncInfoFragment view) {
        super.onAttachView(view);
        data = new PreferencesData(view.getContext());
    }

    @Override
    public String getLogin() {
        return data.getLogin();
    }

    @Override
    public void clearAuthorizationData() {
        data.addLoginPass("", "");
    }

    @Override
    public void checkAuthorizatiton() {
        view.showLoading();
        Server server = new Server();
        server.loginUser(data.getLogin(), data.getPass(), new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                if (result.getResult().equals("ok")) {
                    view.changeStatus(true);
                    view.hideLoading();
                } else {
                    view.changeStatus(false);
                    view.hideLoading();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                view.changeStatus(false);
                view.hideLoading();
            }
        });
    }
}
