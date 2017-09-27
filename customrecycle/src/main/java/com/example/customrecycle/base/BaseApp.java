package com.example.customrecycle.base;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;

import com.example.customrecycle.frame.application.IAppInitControl;
import com.example.customrecycle.frame.utils.ConfigUtils;
import com.example.customrecycle.frame.utils.DeviceUtils;
import com.example.customrecycle.frame.utils.FileAccessor;


public class BaseApp extends Application {
    public static Context CONTEXT = null;
    private IAppInitControl mInitController;


    public static boolean DEBUG = false;

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

        /**
         * 防止7.0及以上 FileUriExposedException
         * 如果报错，或没有N 可以把 Build.VERSION_CODES.N 换成 24
         */
        if (Build.VERSION.SDK_INT >= 24) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }


        Class<?> clazz = null;
        try {
            clazz = Class
                    .forName("com.pub.application.AppInitControl");
            mInitController = (IAppInitControl) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mInitController.init(this);


        super.onCreate();
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
