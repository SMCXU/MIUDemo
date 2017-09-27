package com.example.customrecycle.frame.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.example.customrecycle.base.BaseApp;


/**
 * 吐司工具类
 */
public class MyToast {

    /**
     * 消息提示
     */
    private static Toast mToast;


    /**
     * 显示消息提示
     *
     * @param resources 消息内容int
     */
    public static void showToast(int resources) {
        showToast(BaseApp.getContext().getResources().getString(resources));
    }

    /**
     * 显示消息提示
     *
     * @param msg 消息内容
     */
    public static void showToast(String msg) {
        try {
            if (TextUtils.isEmpty(msg)) {
                msg = msg + "";
            }
            if (mToast == null) {
                mToast = Toast.makeText(BaseApp.getContext(), msg, Toast.LENGTH_SHORT);
            } else {
                mToast.cancel();// 关闭吐司显示
                mToast = Toast.makeText(BaseApp.getContext(), msg, Toast.LENGTH_SHORT);
            }
            mToast.show();
        } catch (Exception e) {
System.out.println("吐司：e:"+e.toString());
        }
    }
}
