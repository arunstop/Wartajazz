package com.bimo.wartajazz.api;

import com.bimo.wartajazz.models.LoginResponse;
import com.bimo.wartajazz.models.SignupResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {


    @FormUrlEncoded
    @POST("Auth/register")
    Call<SignupResponse> createUser(
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email,
            @Field("fullname") String fullname,
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("Auth/login")
    Call<LoginResponse> userLogin(
            @Field("username") String username,
            @Field("password") String password
    );

}
