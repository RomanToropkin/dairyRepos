package com.franq.dairy.Presenter.PSync;

import android.util.Log;

import com.franq.dairy.Model.JsonModels.Result;
import com.franq.dairy.Model.local.PreferencesData;
import com.franq.dairy.Model.remote.Server;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.View.Fragments.SyncInfoFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
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
        data = PreferencesData.getInstance( view.getContext( ) );
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
        view.toLoginFragment( );
    }

    /**Отправление запроса на сервер с целью проверки синхронизации пользователя.
     * Данные получаются из хранилища
     **/
    @Override
    public void checkAuthorizatiton() {
        view.showLoading();
        Server server = Server.getInstance( view.getContext( ) );
        disposables.add( server.syncUser( )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .subscribeWith( new DisposableSubscriber <Response <Result>>( ) {
                    @Override
                    public void onNext(Response <Result> resultResponse) {
                        Result result = resultResponse.body( );
                        if ( result != null ) {
                            Log.d( Server.TAG, "Result : " + result.getResult( ) );
                            if ( result.getResult( ).equals( "Ok" ) ) {
                                view.changeStatus( true );
                            } else {
                                data.addLoginPass( PreferencesData.DEFAULT_LOGIN, "" );
                                view.toLoginFragment();
                            }
                        } else {
                            Log.d( Server.TAG, "Result : is null" );
                            view.changeStatus( false );
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d( Server.TAG, "Error : " + t.getMessage( ) );
                        view.hideLoading( );
                        view.changeStatus( false );
                    }

                    @Override
                    public void onComplete() {
                        Log.d( Server.TAG, "Result on complete!" );
                        view.hideLoading( );
                    }
                } ) );
    }
}
