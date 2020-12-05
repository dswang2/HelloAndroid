package com.example.pekon.helloandroid;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.pekon.component.MyApplication;
import com.example.pekon.helloandroid.entity.PhoneInfoEntity;
import com.example.pekon.helloandroid.greendao.gen.PhoneInfoEntityDao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class ScreenInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_info;
    private Button btn_ok;
    private String btnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_info);
        initViews();
        EventBus.getDefault().register(this);
        getScreenInfo();
    }

    private void initViews() {
        tv_info = (TextView)findViewById(R.id.tv_info);
        btn_ok = (Button)findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
    }

    private String getScreenInfo() {
        StringBuffer infoBuffer = new StringBuffer();
        PhoneInfoEntityDao phoneInfoEntityDao = MyApplication.getInstance().getmDaoSession().getPhoneInfoEntityDao();
        List<PhoneInfoEntity> phoneInfoEntities = phoneInfoEntityDao.loadAll();
        if(phoneInfoEntities!=null && phoneInfoEntities.size()>0){
            infoBuffer.append(phoneInfoEntities.get(0).getName()+"\n");
        }
        readRealDeviceInfo(infoBuffer);
        readEnableDeviceInfo(infoBuffer);

        String info = infoBuffer.toString();
        tv_info.setText(info);
        Log.e("ds",info);
        return info;
    }

    // 获取屏幕真实宽高（包含虚拟按键）
    private void readRealDeviceInfo(StringBuffer infoBuffer) {
        WindowManager windowManager =
                (WindowManager) getApplication().getSystemService(Context.
                        WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();
        if (Build.VERSION.SDK_INT >= 19) {
            // 可能有虚拟按键的情况
            display.getRealSize(outPoint);
        } else {
            // 不可能有虚拟按键
            display.getSize(outPoint);
        }
        int mRealSizeWidth;//手机屏幕真实宽度
        int mRealSizeHeight;//手机屏幕真实高度
        mRealSizeHeight = outPoint.y;
        mRealSizeWidth = outPoint.x;
        infoBuffer.append("屏幕真实高度（像素）= "+mRealSizeHeight+"\n");
        infoBuffer.append("屏幕真实宽度（像素）= "+mRealSizeWidth+"\n");
    }

    // 获取屏幕的宽高（不包含虚拟按键）
    private void readEnableDeviceInfo(StringBuffer infoBuffer) {
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        // 系统分配内存
        int memoryClass = mActivityManager.getMemoryClass();
        // 最大分配内存
        int largeMemoryClass = mActivityManager.getLargeMemoryClass();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //  屏幕可用高度
        int heightPixels = dm.heightPixels;
        // 屏幕可用宽度
        int widthPixels = dm.widthPixels;
        double density = dm.density;
        int densityDpi = dm.densityDpi;
        infoBuffer.append("屏幕可用高度，不包括虚拟按键部分（像素）= "+heightPixels+"\n");
        infoBuffer.append("屏幕可用宽度，不包括虚拟按键部分（像素）= "+widthPixels+"\n");
        infoBuffer.append("屏幕密度 = "+density+"\n");
        infoBuffer.append("屏幕密度DPI = "+densityDpi+"\n");

        infoBuffer.append("\n");
        infoBuffer.append("系统分配内存= "+memoryClass+"\n");
        infoBuffer.append("最大分配内存= "+largeMemoryClass+"\n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok:
                confirmClick();
                break;
        }
    }

    @Subscribe(sticky = true)
    public void getType(EventEntity eventEntity){
        // 先于 onCreate 执行
        Log.e("dsw-2", eventEntity.getMessage());
        btn_ok.setText("确定-"+eventEntity.getMessage());
        return;
    }

    private void confirmClick() {
        EventBus.getDefault().post(new EventEntity(getScreenInfo()));
        finish();
    }
}
