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
import com.example.pekon.helloandroid.entity.PhoneInfoEntity;
import com.example.pekon.helloandroid.greendao.gen.PhoneInfoEntityDao;
import com.example.pekon.helloandroid.net.ApiRetrofit;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_screen_info;
    private Button tv_exception_test;
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
        }
    }

    private void retrofitTest() {
        if((boolean)tv_exception_test.getTag()){
            //异步测试
            Log.e("dsw", "异步测试");
            ApiRetrofit.getGankCategories(new ApiRetrofit.ResultCallback<Categories>() {
                @Override
                public void onSuccess(Categories results) {
                    tv_screen_info.setText(results.toString());
                }
                @Override
                public void onError(String message) {
                    tv_screen_info.setText(message);
                }
            });
        }else {
            //同步测试
            Log.e("dsw", "同步测试");
            //同步方式,比较麻烦，下面测试都是异步方法测试
            Callable<Categories> categoriesCallable = new Callable<Categories>() {
                @Override
                public Categories call() throws Exception {
                    Categories responseEntity = ApiRetrofit.getGankCategoriesSync();
                    tv_screen_info.setText(responseEntity.toString());
                    return ApiRetrofit.getGankCategoriesSync();
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
