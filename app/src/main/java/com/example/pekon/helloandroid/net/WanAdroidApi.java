package com.example.pekon.helloandroid.net;

import com.example.pekon.helloandroid.entity.JsonsRootBean;
import com.example.pekon.helloandroid.entity.WanAndroidResponseBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * create by dsw on 2020-12-11
 **/
public interface WanAdroidApi {
    // baseUrl: https://wanandroid.com/
    //完整url: https://wanandroid.com/wxarticle/chapters/json

    @GET("wxarticle/chapters/json")
    Observable<WanAndroidResponseBean> getWxarticle();

    @GET("article/list/0/json")
    Observable<JsonsRootBean> getArticle();
}
