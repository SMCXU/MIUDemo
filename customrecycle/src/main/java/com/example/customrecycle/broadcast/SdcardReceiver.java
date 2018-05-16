package com.example.customrecycle.broadcast;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.storage.StorageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.customrecycle.activitys.HomeActivity;
import com.example.customrecycle.base.DaoTools;
import com.example.customrecycle.frame.EventCustom;
import com.example.customrecycle.frame.utils.FileUtils;
import com.example.customrecycle.frame.utils.KEY;
import com.example.customrecycle.frame.utils.MyToast;
import com.example.customrecycle.entity.VideoEntity;
import com.example.customrecycle.frame.weightt.ZBXAlertDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by U
 * <p/>
 * on 2017/10/26
 * <p/>
 * QQ:1347414707
 * <p/>
 * For:
 */
public class SdcardReceiver extends BroadcastReceiver {

    private ZBXAlertDialog dialog;
    private List<VideoEntity> mList;
    private EventCustom eventCustom;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        eventCustom = new EventCustom();
        if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
            eventCustom.setTag(KEY.FLAG_USB_SCAN);
            mList = new ArrayList<VideoEntity>();
            // 扫描功能 获取U盘视频列表
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //申请内存读取权限
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS}, 3);
            } else {
                ScanFile(context);
            }
            Log.d("Mr.U", "sdcard mounted 已经挂载了");
        } else if (action.equals(Intent.ACTION_MEDIA_UNMOUNTED)) {
            eventCustom.setTag(KEY.FLAG_USB_OUT);
            DaoTools.deleteAll();
            HomeActivity.videoList.clear();
            MyToast.showToast("外部存储设备已经拔出！");
        }
        EventBus.getDefault().post(eventCustom);
    }

    private void ScanFile(Context context) {
        String[] result = null;
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Method method = StorageManager.class.getMethod("getVolumePaths");
            method.setAccessible(true);
            try {
                result = (String[]) method.invoke(storageManager);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            DaoTools.deleteAll();
            HomeActivity.videoList.clear();
            for (int i = 0; i < result.length; i++) {
                FileUtils.getAllFiles(new File(result[i]));
            }
            HomeActivity.videoList = DaoTools.queryAll();
            EventCustom eventCustom = new EventCustom();
            eventCustom.setTag(KEY.FLAG_USB_IN);
            EventBus.getDefault().post(eventCustom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    //扫描获取U盘内数据
//    private void scanMediaFile(Context context) {
//        String[] args = {"mp4", "wmv", "rmvb", "mkv", "avi", "flv", "3gp", "mov", "mpg", "webm", "wob"};
//        mList.clear();
//        DaoTools.deleteAll();
//        HomeActivity.videoList.clear();
//        mList = FileUtils.getSpecificTypeOfFile(context, args);
//        HomeActivity.videoList = mList;
//        HomeActivity.videoList = DaoTools.queryAll();
//        Log.d("Mr.U", "scanMediaFile: ----" + mList.size());
//    }
}