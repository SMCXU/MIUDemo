package com.example.customrecycle.frame.utils;

import android.app.Instrumentation;

import java.io.IOException;

/**
 * Created by ZLQ-PC on 2018/4/25.
 * <p>
 * 遥控器操作类,输入遥控器keycode,执行相应操作
 */

public class VirturlKeyPadCtr {
    private static Instrumentation mInstrumentation;

    /**
     * Wrapper-function taking a KeyCode. A complete KeyStroke is DOWN and UP
     * Action on a key!
     */
    public static void simulateKeystroke(final int KeyCode) {
        if (mInstrumentation == null) {
            mInstrumentation = new Instrumentation();
        }
//        try {
//            Process process = Runtime.getRuntime().exec("su");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (KeyCode >= 0 && KeyCode <= 999) {
                    mInstrumentation.sendKeyDownUpSync(KeyCode);
                }
            }
        }).start();

    }
}
