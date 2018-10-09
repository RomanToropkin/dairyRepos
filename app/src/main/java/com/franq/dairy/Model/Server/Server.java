package com.franq.dairy.Model.Server;

import com.franq.dairy.Model.Server.JsonModels.Result;
import com.franq.dairy.Model.Server.JsonModels.User;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Server {

    private static final String BASE_URL = "http://192.168.1.217:8080";
    private static final String SERVER_TAG = "Server work";
    private Retrofit retrofit;
    private APIService apiService;


    public Server() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);

    }

    public void registerUser(String login, String pass, Callback<Result> callback) {
        User user = new User();
        user.setLogin(login);
        user.setPass(pass);
        apiService.registerUser(user).enqueue(callback);
    }

    public void loginUser(String login, String pass, Callback<Result> callback) {
        User user = new User();
        user.setLogin(login);
        user.setPass(pass);
        apiService.loginUser(user).enqueue(callback);
    }

}
