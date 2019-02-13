package com.pkl.wartajazz.api;

import com.pkl.wartajazz.models.LoginResponse;
import com.pkl.wartajazz.models.SignupResponse;

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
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("phone") String phone,
            @Field("address") String address
    );

    @FormUrlEncoded
    @POST("Auth/login")
    Call<LoginResponse> userLogin(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("Auth/googleAuth")
    Call<LoginResponse> googleAuth(
            @Field("providerId") String providerId,
            @Field("email") String email,
            @Field("displayName") String displayName,
            @Field("thumbnail") String thumbnail
    );

}
