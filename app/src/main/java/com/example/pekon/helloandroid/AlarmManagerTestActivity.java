package com.example.pekon.helloandroid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create by dsw on 2020-12-13
 **/
public class AlarmManagerTestActivity  extends AppCompatActivity implements View.OnClickListener {

    private Button btn_1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_manager_test);
        initView();
    }

    private void initView() {
        btn_1 = findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
                alarmManagerTest();
                break;
        }
    }

    private void alarmManagerTest() {
        Intent intent = new Intent(this, AlarmReceive.class);
        intent.setAction(Constants.TIMER_ACTION_REPEATING);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // 往系统注册一个定时任务，到达时间，系统发送广播，app接受广播，
        // 定时间隔时间，小于60s的，按照60s处理
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 3 * 1000, sender);
        Log.e("dsw","发送定时1 - "
                + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
    }
}
