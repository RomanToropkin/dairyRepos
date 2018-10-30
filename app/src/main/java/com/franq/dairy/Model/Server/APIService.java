package com.franq.dairy.Model.Server;

import com.franq.dairy.Model.Server.JsonModels.Result;
import com.franq.dairy.Model.Server.JsonModels.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Интерфейс, описывающий аннотации REST API сервера.
 **/
public interface APIService {

    /**POST запрос на регистрацию пользователя
     * @param user Пользователь
     * @return ответ от сервера*/
    @POST("/register")
    Call<Result> registerUser(@Body User user);

    /**POST запрос на авторизацию пользователя
     * @param user Пользователь
     * @return ответ от сервера**/
    @POST("/login")
    Call<Result> loginUser(@Body User user);

    @POST("/sync")
    /**POST запрос на проверку синхронизации пользователя
     * @param cookie куки клиента
     * @return session_id*/
    Call<Result> syncUser(@Header("cookie") String cookie);

    /**
     * POST запрос на завершение сессии
     *
     * @param cookie куки клиента
     * @return session_id
     */
    Call<Result> clearSession(@Header("cookie") String cookie);
}
