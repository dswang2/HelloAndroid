package com.example.pekon.helloandroid.net;

import com.example.pekon.helloandroid.entity.Categories;

import java.io.IOException;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * create by dsw on 2020-12-05
 **/
public class CommonGankRetrofit {
    public static final String BASE_URL = "https://gank.io/api/";

    private static CommonGankApi sCommonGankApi;
    private static CommonGankApi getCommonGankApi() {
        if (sCommonGankApi == null) {
            sCommonGankApi = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build().create(CommonGankApi.class);
        }
        return sCommonGankApi;
    }

    //异步操作方式
    public static void getGankCategories(final ResultCallback<Categories> resultCallback){
        getCommonGankApi().getCategories().enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                Categories body = response.body();
                if (body == null) {
                    resultCallback.onError("body is null!");
                    return;
                }
                resultCallback.onSuccess(body);
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                resultCallback.onError(t.getMessage());
            }
        });
    }
    //同步操作方式
    public static Categories getGankCategoriesSync() throws IOException {
        return getCommonGankApi().getCategories().execute().body();
    }

    public static Observable<Categories> getObservableCategories(){
        return getCommonGankApi().getObservableCategories();
    }

    public interface ResultCallback<T> {
        void onSuccess(T result);
        void onError(String message);
    }
}
