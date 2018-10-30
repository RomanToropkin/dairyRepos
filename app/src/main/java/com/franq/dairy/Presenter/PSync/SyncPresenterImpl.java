package com.franq.dairy.Presenter.PSync;

import com.franq.dairy.Model.Server.JsonModels.Result;
import com.franq.dairy.Model.Server.Server;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.Utility.PreferencesData;
import com.franq.dairy.View.Fragments.SyncInfoFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Представитель, синхронизирующий пользователя
 */
public class SyncPresenterImpl extends BasePresenter<SyncInfoFragment> implements SyncPresenter {
    /**Хранилище настроек*/
    private PreferencesData data;

    /**Переопределение базового метода с инициализацией хранилища настроек*/
    @Override
    public void onAttachView(SyncInfoFragment view) {
        super.onAttachView(view);
        data = new PreferencesData(view.getContext());
    }

    /**Вовращает логин из хранилища
     * @return логин*/
    @Override
    public String getLogin() {
        return data.getLogin();
    }

    /**Удаляет данные из хранилища. Пользователь вышел из системы*/
    @Override
    public void clearAuthorizationData() {
        data.clearData();
        Server server = new Server();
        server.clearSession(data.getCookie(), new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
            }
        });
    }

    /**Отправление запроса на сервер с целью проверки синхронизации пользователя.
     * Данные получаются из хранилища
     **/
    @Override
    public void checkAuthorizatiton() {
        view.showLoading();
        Server server = new Server();
        server.syncUser(data.getCookie(), new Callback<Result>() {
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
