package com.example.pekon.helloandroid;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

public class AnimatorTestActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_animator;
    private boolean tag = false;
    private Button btn_1,btn_2,btn_3,btn_4,btn_5;

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
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607823376823&di=4c8c87ad981b4e02f016affad5ee580e&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F7b99dbfea454fda499c92c1fe83dda75ba2703f1c53e-iwMQJw_fw658")
                .apply(options)
                .into(iv_animator);
    }

    private void initView() {
        iv_animator = findViewById(R.id.iv_animator);
        iv_animator.setOnClickListener(this);
        btn_1 = findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);
        btn_2 = findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);
        btn_3 = findViewById(R.id.btn_3);
        btn_3.setOnClickListener(this);
        btn_4 = findViewById(R.id.btn_4);
        btn_4.setOnClickListener(this);
        btn_5 = findViewById(R.id.btn_5);
        btn_5.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
                animatorTest1();
                break;
            case R.id.btn_2:
                animatorTest2();
                break;
            case R.id.btn_3:
                animatorTest3();
                break;
            case R.id.btn_4:
                animatorTest4();
                break;
            case R.id.btn_5:
                animatorTest5();
                break;
        }
    }

    // 旋转属性动画
    @SuppressLint("WrongConstant")
    private void animatorTest5() {
        //这个是按照某一点进行旋转，默认是view的
        ObjectAnimator oaAnimator=ObjectAnimator.ofFloat(iv_animator, "rotation", 0,360);
        //如果不指定中心点的话就是按照图标自己的中心进行旋转
//        iv_animator.setPivotX(100);//设置指定旋转中心点X坐标
//        iv_animator.setPivotY(100);//设置指定旋转中心点X坐标，注意的是这个点（100,100）是想对于view的坐标，不是屏幕的左上角的0，0位置，有了这你就可以实现和补间动画一样的效果
        oaAnimator.setDuration(5000);
        oaAnimator.setInterpolator(new LinearInterpolator()); // 设置匀速播放 ！！！
        oaAnimator.setRepeatCount(-1);//设置动画重复次数，这里-1代表无限
        oaAnimator.setRepeatMode(Animation.RESTART);//设置动画循环模式。
        oaAnimator.start();
    }

    private void animatorTest4() {
        AnimatorSet set = new AnimatorSet() ;
        ObjectAnimator anim = ObjectAnimator .ofFloat(iv_animator, "rotationX", 0f, 180f);
        anim.setDuration(2000);
        ObjectAnimator anim2 = ObjectAnimator .ofFloat(iv_animator, "rotationX", 180f, 0f);
        anim2.setDuration(2000);
        ObjectAnimator anim3 = ObjectAnimator .ofFloat(iv_animator, "rotationY", 0f, 180f);
        anim3.setDuration(2000);
        ObjectAnimator anim4 = ObjectAnimator .ofFloat(iv_animator, "rotationY", 180f, 0f);
        anim4.setDuration(2000);

        set.play(anim).before(anim2); //先执行anim动画之后在执行anim2
        set.play(anim3).before(anim4) ;
        set.start();
    }

    private void animatorTest3() {
        AnimatorSet animatorSet = new AnimatorSet();//组合动画
        ObjectAnimator translationUp = ObjectAnimator.ofFloat(iv_animator, "Y",
                iv_animator.getY(), 0);
        ObjectAnimator translationDown = ObjectAnimator.ofFloat(iv_animator, "Y",
                0, iv_animator.getY());
        animatorSet.play(translationUp).with(translationDown);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.setDuration(1500);
        animatorSet.start();
    }

    private void animatorTest2() {
        AnimatorSet animatorSet = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv_animator, "scaleX", 0f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv_animator, "scaleY", 0f, 1f);
        animatorSet.setDuration(2000);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play(scaleX).with(scaleY);//两个动画同时开始
        animatorSet.start();
    }

    private void animatorTest1() {
        //第一个参数为 view对象，第二个参数为 动画改变的类型，第三，第四个参数依次是开始透明度和结束透明度。
        ObjectAnimator alpha = ObjectAnimator.ofFloat(iv_animator, "alpha", 0f, 1f);
        alpha.setDuration(2000);//设置动画时间
        alpha.setInterpolator(new DecelerateInterpolator());//设置动画插入器，减速
        alpha.setRepeatCount(-1);//设置动画重复次数，这里-1代表无限
        alpha.setRepeatMode(Animation.REVERSE);//设置动画循环模式。
        alpha.start();//启动动画。
    }
}