package com.example.pekon.helloandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

public class AnimatorTestActivity extends AppCompatActivity {

    private ImageView iv_animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator_test);
        initView();
        initData();
    }

    private void initData() {
        RequestOptions options = new RequestOptions();
        options.skipMemoryCache(false); // 禁用缓存
        options.transform(new CircleCrop()); // 加载圆形图片
//        options.placeholder(iv_animator.getDrawable()); // 占位图
//        options.centerCrop(); // 填充
        Glide.with(this)
                .load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=102847656,1644065754&fm=26&gp=0.jpg")
                .apply(options)
                .into(iv_animator);
    }

    private void initView() {
        iv_animator = findViewById(R.id.iv_animator);
    }


}