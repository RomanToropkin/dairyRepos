package com.franq.dairy.Model.Server;

import com.franq.dairy.Model.Server.JsonModels.Result;
import com.franq.dairy.Model.Server.JsonModels.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {

    @POST("/register")
    Call<Result> registerUser(@Body User user);

    @POST("/login")
    Call<Result> loginUser(@Body User user);

}
