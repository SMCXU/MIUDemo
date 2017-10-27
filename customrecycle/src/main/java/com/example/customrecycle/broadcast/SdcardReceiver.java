package com.example.customrecycle.broadcast;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.customrecycle.base.BaseApp;
import com.example.customrecycle.frame.EventCustom;
import com.example.customrecycle.frame.utils.KEY;
import com.example.customrecycle.frame.utils.MyToast;
import com.example.customrecycle.frame.weightt.ZBXAlertDialog;
import com.example.customrecycle.frame.weightt.ZBXAlertListener;
import com.google.common.eventbus.EventBus;

/**
 * Created by U
 * <p>
 * on 2017/10/26
 * <p>
 * QQ:1347414707
 * <p>
 * For:
 */
public class SdcardReceiver extends BroadcastReceiver {

    private ZBXAlertDialog dialog;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(Intent.ACTION_MEDIA_MOUNTED)){
            EventCustom eventCustom = new EventCustom();
            eventCustom.setTag(KEY.FLAG_USB);
            org.greenrobot.eventbus.EventBus.getDefault().post(eventCustom);
            Log.d("Mr.U","sdcard mounted 已经挂载了");
        }else if(action.equals(Intent.ACTION_MEDIA_UNMOUNTED)){
            MyToast.showToast("外部存储设备已经拔出！");
        }
    }
}