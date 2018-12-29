package com.franq.dairy.Presenter.PRegister;

import android.util.Log;

import com.franq.dairy.Model.JsonModels.Result;
import com.franq.dairy.Model.local.PreferencesData;
import com.franq.dairy.Model.remote.Server;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.View.Fragments.RegisterFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Response;

/**
 * Представитель, регистрирующий пользователя в системе
 **/
public class RegisterPresenterImp extends BasePresenter<RegisterFragment> implements RegisterPresenter {

    /**Отправление запроса на сервер с целью регистрации пользователя
     * Переход на фрагмент с записями
     * @param login логин
     * @param pass пароль*/
    @Override
    public void registerUser(String login, String pass) {
        view.showLoading();
        view.setClick(true);
        Server server = Server.getInstance( view.getContext( ) );
        disposables.add( server.
                registerUser( login, pass )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .subscribeWith( new DisposableSubscriber <Response <Result>>( ) {
                    @Override
                    public void onNext(Response <Result> resultResponse) {
                        Result result = resultResponse.body( );
                        if ( result != null ) {
                            Log.d( Server.TAG, "Result : " + result.getResult( ) );
                            if ( result.getResult( ).equals( "Ok" ) ) {
                                PreferencesData data = PreferencesData.getInstance( view.getContext( ) );
                                String c1 = resultResponse.headers( ).get( "Set-Cookie" );
                                data.addCookie( c1 );
                                data.addLoginPass( login, pass );
                            } else {
                                view.showError( "Такой пользователь уже существует!" );
                            }
                        } else {
                            Log.d( Server.TAG, "Result is null!" );
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d( Server.TAG, "Error :" + t.getMessage( ) );
                        view.showError( "Ошибка подключения к серверу!" );
                        view.setClick( false );
                        view.hideLoading( );
                    }

                    @Override
                    public void onComplete() {
                        Log.d( Server.TAG, "Result on complete!" );
                        view.setClick( false );
                        view.hideLoading( );
                        view.refreshFragment( );
                    }
                } ) );
    }
}
