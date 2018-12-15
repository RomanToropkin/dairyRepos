package com.franq.dairy.Model.Server;

import android.content.Context;
import android.util.Log;

import com.franq.dairy.Model.JsonModels.MDeleteNote;
import com.franq.dairy.Model.JsonModels.MReq;
import com.franq.dairy.Model.JsonModels.Note;
import com.franq.dairy.Model.JsonModels.Result;
import com.franq.dairy.Model.JsonModels.User;
import com.franq.dairy.Model.PreferencesData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Класс, отправляющий и получающий response и request от сервера.
 */
public class Server {

    public static final String TAG = "DairyAPI";
    /**
     * URL сервера
     */
    public static String baseURL = "http://192.168.1.217:8080/";
    /**Retrofit объект, взаимодействуюший с сервером*/
    private Retrofit retrofit;
    /**API сервера
     * @see APIService*/
    private APIService apiService;
    private static volatile Server instance;
    private PreferencesData preferencesData;

    /**Конструктор - инициализация Retrofit и объекта интерфейса*/
    private Server(Context context) {
        Log.d( "Http", "ip : " + baseURL );

        retrofit = new Retrofit.Builder()
                .baseUrl( baseURL )
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory( RxJava2CallAdapterFactory.create( ) )
                .build();
        apiService = retrofit.create(APIService.class);
        preferencesData = PreferencesData.getInstance( context );

    }

    public static Server getInstance(Context context) {
        Server localInstance = instance;
        if ( localInstance == null ) {
            synchronized (Server.class) {
                localInstance = instance;
                if ( localInstance == null ) {
                    instance = localInstance = new Server( context );
                }
            }
        }
        return localInstance;
    }

    /**Запрос на регистрацию пользователя
     * @param login логин пользователя
     * @param pass пароль пользователя*/
    public Flowable <Response <Result>> registerUser(String login, String pass) {
        User user = new User();
        user.setLogin(login);
        user.setPass(pass);
        return apiService.registerUser( user );
    }

    /**
     * Запрос на авторизацию пользователя
     * @param login    логин пользователя
     * @param pass     пароль пользователя
     */
    public Flowable <Response <Result>> loginUser(String login, String pass) {
        User user = new User();
        user.setLogin(login);
        user.setPass(pass);
        return apiService.loginUser( user );
    }

    /**
     * Запрос на синхронизацию аккаунта
     *
     */
    public Flowable <Response <Result>> syncUser() {
        String sessionID = preferencesData.getCookie( );
        return apiService.syncUser( sessionID );
    }

    /**
     * Запрос на удалении сессии
     */
    @Deprecated
    public Flowable <Response <Result>> clearSession() {
        String sessionID = preferencesData.getCookie( );
        return apiService.clearSession( sessionID );
    }

    public Flowable <Response <Result>> addNote(Note note) {
        String sessionID = preferencesData.getCookie( );
        return apiService.addNote( sessionID, note );
    }

    public Flowable <Response <Result>> deleteNote(MDeleteNote deleteNote) {
        String sessionID = preferencesData.getCookie( );
        return apiService.deleteNote( sessionID, deleteNote );
    }

    public Flowable <Response <List <Note>>> getSyncNotes(MReq noteList) {
        String sessionID = preferencesData.getCookie( );
        return apiService.getNotes( sessionID, noteList );
    }

    public Flowable <Response <Result>> sendImages(String note_id, List <File> images) {
        ArrayList <MultipartBody.Part> listImages = new ArrayList <>( );
        RequestBody noteRequest = RequestBody.create( MediaType.parse( "multipart/form-data" ), note_id );
        for (File image : images) {
            RequestBody requestFile = RequestBody.create( MediaType.parse( "multipart/form-data" ), image );
            MultipartBody.Part part =
                    MultipartBody.Part.createFormData( "image", image.getName( ), requestFile );
            listImages.add( part );
        }
        return apiService.sendImages( noteRequest, listImages );
    }

//    public Flowable<Response<ResponseBody>> downloadImages(String id){
//        String sessionID = preferencesData.getCookie();
//        RImage rImage = new RImage();
//        rImage.setNote_id( id );
//        return apiService.downloadImages( sessionID, rImage );
//    }

}
