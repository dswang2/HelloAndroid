package com.example.pekon.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.pekon.component.MyApplication;
import com.example.pekon.helloandroid.entity.Categories;
import com.example.pekon.helloandroid.entity.JsonsRootBean;
import com.example.pekon.helloandroid.entity.PhoneInfoEntity;
import com.example.pekon.helloandroid.entity.WanAndroidResponseBean;
import com.example.pekon.helloandroid.greendao.gen.PhoneInfoEntityDao;
import com.example.pekon.helloandroid.net.CommonGankRetrofit;
import com.example.pekon.helloandroid.net.WanAndroidRetrofit;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_screen_info;
    private Button tv_exception_test;
    private Button tv_retrofit_rxjava;
    private Button tv_Animator;
    private Button tv_alarmManager;
    private TextView tv_screen_info;
    private ImageView iv_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        btn_screen_info = (Button)findViewById(R.id.btn_screen_info);
        btn_screen_info.setOnClickListener(this);
        tv_exception_test = (Button)findViewById(R.id.tv_exception_test);
        tv_exception_test.setOnClickListener(this);
        tv_retrofit_rxjava = (Button)findViewById(R.id.tv_retrofit_rxjava);
        tv_retrofit_rxjava.setOnClickListener(this);
        tv_Animator = (Button)findViewById(R.id.tv_Animator);
        tv_Animator.setOnClickListener(this);
        tv_alarmManager = (Button)findViewById(R.id.tv_alarmManager);
        tv_alarmManager.setOnClickListener(this);
        tv_screen_info = (TextView) findViewById(R.id.tv_screen_info);
        iv_bg = (ImageView) findViewById(R.id.iv_bg);
        initBackGround();
    }

    private void initBackGround() {
        RequestOptions options = new RequestOptions();
        options.skipMemoryCache(false); // 禁用缓存
        options.placeholder(iv_bg.getDrawable()); // 占位图
        options.centerCrop(); // 填充
        Glide.with(this)
                .load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=102847656,1644065754&fm=26&gp=0.jpg")
                .apply(options)
                .into(iv_bg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_screen_info:
                greendaoTest();
                openScreenInfoActivity();
                break;
            case R.id.tv_exception_test:
                if(tv_exception_test.getTag() == null){
                    tv_exception_test.setTag(true);
                }else {
                    tv_exception_test.setTag(!(boolean)tv_exception_test.getTag());
                }
                // exceptionTest();
                retrofitTest();
                break;
            case R.id.tv_retrofit_rxjava:
                if(tv_retrofit_rxjava.getTag() == null){
                    tv_retrofit_rxjava.setTag(true);
                }else {
                    tv_retrofit_rxjava.setTag(!(boolean)tv_retrofit_rxjava.getTag());
                }
                retrofitRxjavaTest();
                break;
            case R.id.tv_Animator:
                startActivity(new Intent(this,AnimatorTestActivity.class));
                break;
            case R.id.tv_alarmManager:
                startActivity(new Intent(this,AlarmManagerTestActivity.class));
                break;
        }
    }

    private void retrofitRxjavaTest() {
        //异步测试
        tv_retrofit_rxjava.setClickable(false);
        Log.e("dsw", "Retrofit+Rxjava异步测试");
        CommonGankRetrofit.getObservableCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Categories>() {
                    @Override
                    public void accept(Categories categories) throws Exception {
                        if((boolean)tv_retrofit_rxjava.getTag()){
                            tv_screen_info.setText("Retrofit+Rxjava异步测试▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲\n"
                                    + categories.toString());
                        }else {
                            tv_screen_info.setText("Retrofit+Rxjava异步测试△△△△△△△△△△△\n"
                                    + categories.toString());
                        }

                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // 异常处理
                        tv_screen_info.setText("Retrofit+Rxjava异步测试数据错误!1111111111111111");
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<Categories, ObservableSource<WanAndroidResponseBean>>() {
                    @Override
                    public ObservableSource<WanAndroidResponseBean> apply(@NonNull Categories categories) throws Exception {
                        EventEntity eventEntity = new EventEntity();
                        eventEntity.setMessage(categories.toString());
                        if((boolean)tv_retrofit_rxjava.getTag()){
                            tv_retrofit_rxjava.setClickable(true);
                            return null;
                        }
                        // 利用上一次的请求数据，获得下一次的请求结果,
                        // 可能是网络请求组，请求结果是一个 Observable
                        // 这里手动发出一个请求
                        return WanAndroidRetrofit.getWanAdroidApi().getWxarticle();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<WanAndroidResponseBean>() {
                    @Override
                    public void accept(WanAndroidResponseBean wanAndroidResponseBean) throws Exception {
                        tv_screen_info.setText("Retrofit+Rxjava+公众号列表2222222222222\n" + wanAndroidResponseBean);
                        tv_retrofit_rxjava.setClickable(true);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        tv_retrofit_rxjava.setClickable(true);
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<WanAndroidResponseBean, ObservableSource<JsonsRootBean>>() {
                    @Override
                    public ObservableSource<JsonsRootBean> apply(@NonNull WanAndroidResponseBean wanAndroidResponseBean) throws Exception {
                        return WanAndroidRetrofit.getWanAdroidApi().getArticle();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<JsonsRootBean>() {
                    @Override
                    public void accept(JsonsRootBean jsonsRootBean) throws Exception {
                        String json = new Gson().toJson(jsonsRootBean);
                        tv_screen_info.setText("Retrofit+Rxjava+文章首页列表33333333333\n" + json);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        tv_retrofit_rxjava.setClickable(true);
                    }
                })
                .subscribe();



    }

    private void retrofitTest() {
        if((boolean)tv_exception_test.getTag()){
            //异步测试
            Log.e("dsw", "Retrofit异步测试");
            CommonGankRetrofit.getGankCategories(new CommonGankRetrofit.ResultCallback<Categories>() {
                @Override
                public void onSuccess(Categories results) {
                    tv_screen_info.setText("Retrofit异步测试"
                            + results.toString());
                }
                @Override
                public void onError(String message) {
                    tv_screen_info.setText(message);
                }
            });
        }else {
            //同步测试
            Log.e("dsw", "Retrofit同步测试");
            //同步方式,比较麻烦，下面测试都是异步方法测试
            Callable<Categories> categoriesCallable = new Callable<Categories>() {
                @Override
                public Categories call() throws Exception {
                    Categories responseEntity = CommonGankRetrofit.getGankCategoriesSync();
                    tv_screen_info.setText("Retrofit同步测试"
                            + responseEntity.toString());
                    return CommonGankRetrofit.getGankCategoriesSync();
                }
            };
            try {
                Executors.newSingleThreadScheduledExecutor().submit(categoriesCallable).get();
            } catch (Exception e) {
                e.printStackTrace();
                tv_screen_info.setText(e.getMessage());
            }
        }
    }

    private void greendaoTest() {
        PhoneInfoEntityDao phoneInfoEntityDao = MyApplication.getInstance().getmDaoSession().getPhoneInfoEntityDao();
        List<PhoneInfoEntity> phoneInfoEntities = phoneInfoEntityDao.loadAll();
        if(phoneInfoEntities.size()>0){
            return;
        }
        PhoneInfoEntity phoneInfoEntity = new PhoneInfoEntity();
        phoneInfoEntity.setName("设备尺寸");
        phoneInfoEntityDao.insert(phoneInfoEntity);
    }

    private void exceptionTest() {
        int s = 0,i = 100,j=0;
        s = i / j;
    }

    private void openScreenInfoActivity() {
        EventBus.getDefault().postSticky(new EventEntity().setType("尺寸信息获取").setMessage("To Home"));
        startActivity(new Intent(this,ScreenInfoActivity.class));
    }

    @Subscribe
    public void onScreenInfoRequest(EventEntity eventEntity){
        if(eventEntity == null || TextUtils.isEmpty(eventEntity.getMessage())){
            return;
        }
        tv_screen_info.setText(eventEntity.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().removeAllStickyEvents();
    }
}
