package com.example.pekon.component;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pekon.component.util.CrashHandler;
import com.example.pekon.helloandroid.greendao.gen.DaoMaster;
import com.example.pekon.helloandroid.greendao.gen.DaoSession;

/**
 * create by dsw on 2020-11-21
 **/
public class MyApplication extends Application {

    public static MyApplication instance;
    private DaoSession mDaoSession;

    public static MyApplication getInstance(){
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initCrashHandler();
        initGreenDao();
    }

    private void initCrashHandler() {
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }

    public void initGreenDao() {
        try {
            //创建数据库mydb.db
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext(),"mydb.db");
            //获取可写数据库
            SQLiteDatabase database = helper.getWritableDatabase();
            //获取数据库对象
            DaoMaster daoMaster = new DaoMaster(database);
            //获取Dao对象管理者
            mDaoSession = daoMaster.newSession();
        }catch (Exception e){
            Log.e("TAG-APP", e.toString());
        }
    }

    public DaoSession getmDaoSession(){
        return mDaoSession;
    }
}
