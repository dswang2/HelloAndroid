package com.example.pekon.component.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.pekon.component.MyApplication;
import com.example.pekon.helloandroid.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CrashHandler implements Thread.UncaughtExceptionHandler{
    private static final String TAG = "CrashHandler";

    /*系统默认的UncaughtException处理类*/
    private Thread.UncaughtExceptionHandler defaultHandler;

    private Context context;

    private static CrashHandler instance = new CrashHandler();

    /*用来存储设备信息和异常信息*/
    private Map<String, String> infos = new HashMap<String, String>();

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return instance;
    }


    public void init(Context context) {
        this.context = context;
        //获取系统默认的UncaughtException处理器
        this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 【说明】：当UncaughtException发生时会转入该方法来处理
     *
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && defaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器处理
            defaultHandler.uncaughtException(thread, ex);
        }
    }

    /**
     * 【说明】：自定义错误处理（包括收集错误信息，生成错误日志文件）
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        collectDeviceInfo(context);
        saveCrashInfo2File(ex);
        return true;
    }

    /**
     * 【说明】：收集应用参数信息
     */
    private void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();//获取应用包管理者对象
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                String packageName = pi.packageName;
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
                infos.put("packageName", packageName);
                infos.put("appName", ctx.getString(R.string.app_name));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occurred when collect package info...", e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (IllegalAccessException e) {
                Log.e(TAG, "an error occurred when collect crash info...", e);
            }
        }
    }

    /**
     * 【说明】：保存错误信息到指定文件中
     */
    private String saveCrashInfo2File(Throwable ex) {
        Toast.makeText(MyApplication.getInstance(), ex.toString(), 1);
        StringBuffer sbf = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sbf.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);

        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();

        String result = writer.toString();
        sbf.append(result);
        try {
            //格式化日期，作为文件名的一部分
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = dateFormat.format(new Date());
            long timestamp = System.currentTimeMillis();
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                File dir = FileUtil.getCrashDir(context);
                String filePath = dir.getAbsoluteFile() + File.separator + fileName;
                FileOutputStream fos = new FileOutputStream(filePath);
                Log.e(TAG, "log file path:" + filePath);
                fos.write(sbf.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "an error occurred while find file...", e);
        } catch (IOException e) {
            Log.e(TAG, "an error occurred while writing file...", e);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

