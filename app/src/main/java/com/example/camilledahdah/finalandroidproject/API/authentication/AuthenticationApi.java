package com.example.camilledahdah.finalandroidproject.API.authentication;

import com.example.camilledahdah.finalandroidproject.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by camilledahdah on 4/17/18.
 */

public interface AuthenticationApi {

    @POST("register")
    Call<User> register(@Body User user);

    @POST("login")
    Call<User> login(@Body User user);

}
