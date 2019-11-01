package com.pkl.wartajazz.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

//        private static final String BASE_URL = "http://wartajazz.000webhostapp.com/Api/";
        private static final String BASE_URL = "http://wartajazz.herokuapp.com/Api/";
//    private static final String BASE_URL = "http://192.168.1.67/pushwarta/";
    //    private static final String BASE_URL = "http://192.168.66.11/wartajazz-api-master/Api/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;


    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
