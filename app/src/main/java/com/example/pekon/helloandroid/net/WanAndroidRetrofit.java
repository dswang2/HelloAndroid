package com.example.pekon.helloandroid.net;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * create by dsw on 2020-12-05
 **/
public class WanAndroidRetrofit {
    public static final String BASE_URL = "https://wanandroid.com/";

    private static WanAdroidApi sCommonGankApi;
    public static WanAdroidApi getWanAdroidApi() {
        if (sCommonGankApi == null) {
            sCommonGankApi = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build().create(WanAdroidApi.class);
        }
        return sCommonGankApi;
    }
}
