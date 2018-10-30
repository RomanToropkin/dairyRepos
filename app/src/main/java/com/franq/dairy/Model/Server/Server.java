package com.franq.dairy.Model.Server;

import com.franq.dairy.Model.Server.JsonModels.Result;
import com.franq.dairy.Model.Server.JsonModels.User;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Класс, отправляющий и получающий response и request от сервера.
 */
public class Server {

    /**
     * URL сервера
     */
    private static final String BASE_URL = "http://192.168.1.217:8080";
    /**Retrofit объект, взаимодействуюший с сервером*/
    private Retrofit retrofit;
    /**API сервера
     * @see APIService*/
    private APIService apiService;

    /**Конструктор - инициализация Retrofit и объекта интерфейса*/
    public Server() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);

    }

    /**Запрос на регистрацию пользователя
     * @param login логин пользователя
     * @param pass пароль пользователя
     * @param callback ассинхронный обратный вызов, для доступа к response*/
    public void registerUser(String login, String pass, Callback<Result> callback) {
        User user = new User();
        user.setLogin(login);
        user.setPass(pass);
        apiService.registerUser(user).enqueue(callback);
    }

    /**
     * Запрос на авторизацию пользователя
     *
     * @param login    логин пользователя
     * @param pass     пароль пользователя
     * @param callback ассинхронный обратный вызов, для доступа к response
     */
    public void loginUser(String login, String pass, Callback<Result> callback) {
        User user = new User();
        user.setLogin(login);
        user.setPass(pass);
        apiService.loginUser(user).enqueue(callback);
    }

    /**
     * Запрос на синхронизацию аккаунта
     *
     * @param sessionID id сессии
     * @param callback  ассинхронный обратный вызов, для доступа к response
     */
    public void syncUser(String sessionID, Callback<Result> callback) {
        apiService.syncUser(sessionID).enqueue(callback);
    }

    /**
     * Запрос на удалении сессии
     *
     * @param sessionID id сессии
     * @param callback  ассинхронный обратный вызов, для доступа к response
     */
    public void clearSession(String sessionID, Callback<Result> callback) {
        apiService.clearSession(sessionID).enqueue(callback);
    }

}
