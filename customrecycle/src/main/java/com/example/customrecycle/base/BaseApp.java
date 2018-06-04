package com.example.customrecycle.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;

import com.example.customrecycle.broadcast.SdcardReceiver;
import com.example.customrecycle.entity.DaoMaster;
import com.example.customrecycle.entity.DaoSession;
import com.example.customrecycle.frame.utils.ConfigUtils;
import com.example.customrecycle.frame.utils.DeviceUtils;
import com.example.customrecycle.frame.utils.FileAccessor;

import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;


public class BaseApp extends Application {
    public static Context CONTEXT = null;

    public static boolean DEBUG = false;
    //数据库
    public static DaoSession daoSession;

    public static Context getContext() {
        if (CONTEXT != null) {
            return CONTEXT.getApplicationContext();
        } else {
            return new BaseApp().getApplicationContext();
        }
    }

    @Override
    public void onCreate() {
        CONTEXT = this;

        String debug = ConfigUtils.getConfig(ConfigUtils.KEY.debug);
        if (debug != null && debug.equals("true")) {
            DEBUG = true;
        }
        //文件工具类
        FileAccessor.initFileAccess();
        //buggly的初始化
        CrashReport.initCrashReport(getApplicationContext(), "b2064ae541", false);

        //初始化数据库
        setupDatabase();
        /**
         * 防止7.0及以上 FileUriExposedException
         * 如果报错，或没有N 可以把 Build.VERSION_CODES.N 换成 24
         */
        if (Build.VERSION.SDK_INT >= 24) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

        super.onCreate();
    }


    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "video.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    //获取数据库操作类
    public static DaoSession getDaoInstant() {
        return daoSession;
    }

    /**
     * 获取该应用公共缓存路径
     *
     * @return
     * @author GISirFive
     */
    public static String getDiskCachePath() {
        return Environment.getExternalStorageDirectory() + "/SeaDreams/";
    }
}
