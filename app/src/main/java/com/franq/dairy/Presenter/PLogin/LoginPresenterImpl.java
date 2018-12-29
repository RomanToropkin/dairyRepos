package com.franq.dairy.Presenter.PLogin;

import android.util.Log;

import com.franq.dairy.Model.JsonModels.Result;
import com.franq.dairy.Model.local.PreferencesData;
import com.franq.dairy.Model.remote.Server;
import com.franq.dairy.Presenter.BasePresenter;
import com.franq.dairy.View.Fragments.LoginFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Response;

/**
 * Представитель, авторизирующий пользователя в системе
 **/
public class LoginPresenterImpl extends BasePresenter<LoginFragment> implements LoginPresenter {

    /**Хранилище настроек*/
    private PreferencesData data;

    /**Переопределение базового метода с инициализацией хранилища настроек*/
    @Override
    public void onAttachView(LoginFragment view) {
        super.onAttachView(view);
        data = PreferencesData.getInstance( view.getContext( ) );
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
        Server server = Server.getInstance( view.getContext( ) );
        disposables.add( server.loginUser( login, pass )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .subscribeWith( new DisposableSubscriber <Response <Result>>( ) {
                    @Override
                    public void onNext(Response <Result> resultResponse) {
                        Result result = resultResponse.body( );
                        if ( result != null ) {
                            Log.d( Server.TAG, "Result : " + result.getResult( ) );
                            if ( result.getResult( ).equals( "Ok" ) ) {
                                String c1 = resultResponse.headers( ).get( "Set-Cookie" );
                                data.addCookie( c1 );
                                data.addLoginPass( login, pass );
                                view.refreshFragment( );
                            } else if ( result.getResult( ).equals( "Login or pass is not current" ) ) {
                                view.showError( "Неверный логин или пароль!" );
                            }
                        } else {
                            Log.d( Server.TAG, "Result is null!" );
                            view.showError( "Ошибка подключения к серверу!" );
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d( Server.TAG, "Error : " + t.getMessage( ) );
                        view.setClicked( false );
                        view.showError( "Ошибка подключения к серверу!" );
                        view.hideLoading( );
                    }

                    @Override
                    public void onComplete() {
                        Log.d( Server.TAG, "Result on complete!" );
                        view.hideLoading( );
                        view.setClicked( false );
                    }
                } ) );
//        server.loginUser(login, pass, new Callback<Result>() {
//            //В случаи успеха получение ответа
//            @Override
//            public void onResponse(Call<Result> call, Response<Result> response) {
//                Log.d(Server.TAG,  call.toString());
//                //Обработка ответа от сервера
//                Result result = response.body();
//                if (result == null) {
//                    view.showError("Ошибка подключения к серверу!");
//                } else if (result.getResult().equals("Ok")) {
//                    String c1 = response.headers().get("Set-Cookie");
//                    data.addCookie(c1);
//                    data.addLoginPass(login, pass);
//                    view.hideLoading();
//                    view.refreshFragment();
//                } else if (result.getResult().equals("Login or pass is not current")) {
//                    view.showError("Неверный логин или пароль!");
//                    view.setClicked(false);
//                    view.hideLoading();
//                }
//            }
//
//            //В случаи ошибки получение ответа
//            @Override
//            public void onFailure(Call<Result> call, Throwable t) {
//                Log.d(Server.TAG, call.toString());
//                Log.d(Server.TAG, t.getMessage());
//                view.showError("Ошибка подключения к серверу!");
//                view.hideLoading();
//                view.setClicked(false);
//            }
//        });
    }
}
