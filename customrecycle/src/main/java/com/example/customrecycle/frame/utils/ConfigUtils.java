package com.example.customrecycle.frame.utils;


import com.example.customrecycle.base.BaseApp;

public class ConfigUtils {

    public enum KEY {
        /**
         * java服务器地址
         */
//        server("http://proxy.zbzbx.com/"),//正式版本 false发布模式
//        server("http://eproxy.dev.zbzbx.com/"),//测试版本加密  false发布模式
//        server("http://proxy.dev.zbzbx.com/"),//测试版本 true调试模式
        server("https://api.douban.com/"),
        /**
         * 是否为调试模式 true调试模式  false发布模式
         */
        debug(true),
//        debug(false),
        /**
         * Web端服务器地址
         */
        webServer("http://n.zbzbx.com/"),
        /**
         * 配置文件名
         */
        privateInfo("zbzbx"),
        /**
         * 默认每一页可以装载的信息数量
         */
        pageSize(12),
        /**
         * 用户信息
         */
        userInfo("tempInfo"),
        /**
         * 数据库版本
         */
        dbVersion(100);

        Object value;

        KEY(Object value) {
            this.value = value;
        }
    }

    public static final String getConfig(KEY key) {
        switch (key) {
//		case server:
//			return AESUtils.decode(BUNDLE.getString(name.toString()));
            default:
                return String.valueOf(key.value);
        }
    }

    //Hybrid混合开发压缩包下载地址 SDCard/Android/data/你的应用包名/cache/目录
    public static String HYBRID_DOWNLOAD_DIR = BaseApp.getContext().getExternalCacheDir() + "/";
    //Hybrid混合开发目录地址
    public static String HYBRID_DIR = BaseApp.getContext().getFilesDir().getAbsolutePath() + "/" + "H5Dir" + "/";
}
