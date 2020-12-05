package com.example.pekon.helloandroid.net;

import com.example.pekon.helloandroid.entity.Categories;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * create by dsw on 2020-12-05
 **/
public interface CommonGankApi {
    // baseUrl: https://gank.io/api/
    //完整url: https://gank.io/api/xiandu/categories

    @GET("xiandu/categories")
    Call<Categories> getCategories();

}
