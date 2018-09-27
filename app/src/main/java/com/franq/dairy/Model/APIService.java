package com.franq.dairy.Model;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.POST;

public interface APIService {

    @POST("/getTest")
    Call <String> testLoad();


}
