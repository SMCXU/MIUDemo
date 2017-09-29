package com.example.customrecycle.frame.utils.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by U
 * <p/>
 * on 2017/9/29
 * <p/>
 * QQ:1347414707
 * <p/>
 * For:
 */
public class MyAppInfo {
    private Drawable image;
    private String appName;

    public MyAppInfo(Drawable image, String appName) {
        this.image = image;
        this.appName = appName;
    }
    public MyAppInfo() {

    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}