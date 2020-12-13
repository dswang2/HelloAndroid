package com.example.pekon.helloandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create by dsw on 2020-12-13
 **/
public class AlarmReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 接受定时任务的广播
        Log.e("dsw","收到广播 - "
                + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
        Intent i = new Intent(context, AlarmService.class);
        context.startService(i);
    }
}
