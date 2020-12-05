package com.example.pekon.component.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 【说明】：文件及文件夹工具类
 */

public class FileUtil {

    private static final String TAG = "FileUtil";

    /**
     * 【说明】：获取手机存储空间文件夹
     *
     * @param context 上下文
     * @return File 文件夹（/storage/emulated/0或/data/user/0/(packageName)）
     */
    private static File getAppDir(Context context) {
        String appDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            //获取外部存储路径（SD卡，在/storage/emulated/0目录下）
            appDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            //获取内部存储路径（默认在/date/user/0/(packageName)目录下）
            appDir = context.getCacheDir().getAbsolutePath();
        }
        File appFile = new File(appDir);
        if (!appFile.exists()) {
            appFile.mkdirs();
        }
        return appFile;
    }

    /**
     * 【说明】：获取应用基础文件夹（应用相关文件都会存储在该目录下）
     *
     * @param context 上下文
     * @return File 文件夹（.../AppNamexx/）
     */
    public static File getBaseDir(Context context) {
        File baseFile = new File(getAppDir(context), FileConstant.APP_BASE_DIR_NAME);
        if (!baseFile.exists()) {
            baseFile.mkdirs();
        }
        return baseFile;
    }



    /**
     * 【说明】：获取应用日志文件夹
     *
     * @param context 上下文
     * @return File 文件夹（.../AppNamexx/Log/）
     */
    public static File getLogDir(Context context) {
        File logFile = new File(getBaseDir(context), FileConstant.APP_LOG_DIR_NAME);
        if (!logFile.exists()) {
            logFile.mkdirs();
        }
        return logFile;
    }

    /**
     * 【说明】：获取应用崩溃日志文件夹
     *
     * @param context 上下文
     * @return File 文件夹（.../AppNamexx/Log/Crash/）
     */
    public static File getCrashDir(Context context) {
//        File crashFile = new File(getLogDir(context), FileConstant.APP_LOG_CRASH_DIR_NAME);
        File crashFile = new File(File.separator + "sdcard"
                +File.separator +"HELLO_ANDROID",
                FileConstant.APP_LOG_CRASH_DIR_NAME);
        if (!crashFile.exists()) {
            crashFile.mkdirs();
        }
        return crashFile;
    }


}
