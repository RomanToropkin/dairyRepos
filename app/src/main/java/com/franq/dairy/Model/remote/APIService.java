package com.franq.dairy.Model.remote;

import com.franq.dairy.Model.JsonModels.MDeleteNote;
import com.franq.dairy.Model.JsonModels.MReq;
import com.franq.dairy.Model.JsonModels.Note;
import com.franq.dairy.Model.JsonModels.Result;
import com.franq.dairy.Model.JsonModels.User;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Интерфейс, описывающий аннотации REST API сервера.
 **/
public interface APIService {

    /**POST запрос на регистрацию пользователя
     * @param user Пользователь
     * @return ответ от сервера*/
    @POST("register")
    Flowable <Response <Result>> registerUser(@Body User user);

    /**POST запрос на авторизацию пользователя
     * @param user Пользователь
     * @return ответ от сервера**/
    @POST("login")
    Flowable <Response <Result>> loginUser(@Body User user);

    @POST("sync")
    /**POST запрос на проверку синхронизации пользователя
     * @param cookie куки клиента
     * @return session_id*/
    Flowable <Response <Result>> syncUser(@Header("cookie") String cookie);

    /**
     * POST запрос на завершение сессии
     *
     * @param cookie куки клиента
     * @return session_id
     */
    @POST("clearSession")
    Flowable <Response <Result>> clearSession(@Header("cookie") String cookie);

    @POST("add")
    Flowable <Response <Result>> addNote(@Header("cookie") String cookie, @Body Note note);

    @POST("notesSync")
    Flowable <Response <List <Note>>> getNotes(@Header("cookie") String cookie, @Body MReq noteList);

    @POST("deleteNote")
    Flowable <Response <Result>> deleteNote(@Header("cookie") String coolie, @Body MDeleteNote deleteNote);

    @POST("addImage")
    @Multipart
    Flowable <Response <Result>> sendImages(@Part("note_id") RequestBody requestBody,
                                            @Part List <MultipartBody.Part> images);
//    @POST("getImages")
//    @Streaming
//    Flowable<Response<ResponseBody>> downloadImages(@Header( "cookie" ) String cookie, @Body RImage rImage);
}
