package com.pkl.wartajazz.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Arunstop on 28-Feb-19.
 */

public class RetrofitRssClient {
    private static final String RSS_URL = "https://api.rss2json.com/v1/";
    private static RetrofitRssClient mInstance;
    private Retrofit retrofit;

    private RetrofitRssClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(RSS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitRssClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitRssClient();
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
