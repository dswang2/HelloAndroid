package com.example.pekon.helloandroid;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create by dsw on 2020-12-13
 **/
public class AlarmService extends IntentService {

    public AlarmService() {
        super("");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public AlarmService(String name) {
        super(name);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // 耗时任务
        Log.e("dsw","执行耗时任务");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 服务启动后，发送广播 // 也可以不发送
        Intent tempIntent = new Intent(this, AlarmReceive.class); // 到达指定时间，发送广播
        tempIntent.setAction(Constants.TIMER_ACTION_REPEATING);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, tempIntent, 0);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // 往系统注册一个定时任务，到达时间，系统发送广播，app接受广播，
        // 第三个参数，定时间隔时间，小于60s的，按照60s处理
        // 第二个参数，triggerAtMillis，当前时间至少加5秒
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+10*1000, 3 * 1000, sender);
        Log.e("dsw","发送定时2 - "
                + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
        return super.onStartCommand(intent, flags, startId);
    }
}
