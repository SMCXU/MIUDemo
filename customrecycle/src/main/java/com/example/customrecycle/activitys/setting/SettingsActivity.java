package com.example.customrecycle.activitys.setting;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.customrecycle.R;
import com.example.customrecycle.activitys.HomeActivity;
import com.example.customrecycle.activitys.movie.VideoGridViewActivity;
import com.example.customrecycle.activitys.setting.SettingInfoActivity;
import com.example.customrecycle.base.BaseActivity;
import com.example.customrecycle.base.BaseApp;
import com.example.customrecycle.bridge.EffectNoDrawBridge;
import com.example.customrecycle.frame.EventCustom;
import com.example.customrecycle.frame.utils.ActivityUtils;
import com.example.customrecycle.frame.utils.KEY;
import com.example.customrecycle.frame.weightt.ZBXAlertDialog;
import com.example.customrecycle.frame.weightt.ZBXAlertListener;
import com.example.customrecycle.view.MainUpView;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_storage)
    TextView tvStorage;
    @BindView(R.id.tv_settings)
    TextView tvSettings;
    @BindView(R.id.mainUpView1)
    MainUpView mainUpView1;
    @BindView(R.id.rl_contioner)
    RelativeLayout rlContioner;
    private EffectNoDrawBridge mEffectNoDrawBridge;
    private View mOldFocus;
    private ZBXAlertDialog dialog;

    //在主线程里面处理消息并更新UI界面
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    setTime();
                    break;
                default:
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        new TimeThread().start(); //启动新的线程
        initView();
        initMoveBridge();
    }

    private void initView() {
        setTime();
        if (HomeActivity.videoList.size()>0){
            tvStorage.setVisibility(View.VISIBLE);
        }else {
            tvStorage.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick({R.id.tv_storage, R.id.tv_settings})
    protected void onClick(View view) {
        switch (view.getId()) {
            //移动硬盘
            case R.id.tv_storage:
                startActivity(new Intent(SettingsActivity.this, VideoGridViewActivity.class));
                break;
            //设置
            case R.id.tv_settings:
                startActivity(new Intent(this, SettingInfoActivity.class));
                break;
        }
    }
    //移动边框
    private void initMoveBridge() {
        float density = getResources().getDisplayMetrics().density;
        mainUpView1 = (MainUpView) findViewById(R.id.mainUpView1);
        mEffectNoDrawBridge = new EffectNoDrawBridge();
        mainUpView1.setEffectBridge(mEffectNoDrawBridge);
        mEffectNoDrawBridge.setUpRectResource(R.drawable.white_light_10); // 设置移动边框图片.
        RectF rectF = new RectF(getDimension(R.dimen.x14) * density, getDimension(R.dimen.x14) * density,
                getDimension(R.dimen.x14) * density, getDimension(R.dimen.x14) * density);
        mEffectNoDrawBridge.setDrawUpRectPadding(rectF);
        rlContioner.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(final View oldFocus, final View newFocus) {
                if (newFocus != null)
                    newFocus.bringToFront(); // 防止放大的view被压在下面. (建议使用MainLayout)
                float scale = 1.2f;
                mainUpView1.setFocusView(newFocus, mOldFocus, scale);
                mOldFocus = newFocus; // 4.3以下需要自己保存.
            }
        });

        for (int i = 0; i < rlContioner.getChildCount(); i++) {
            rlContioner.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
//						v.performClick();
                        v.requestFocus();
                    }
                    return false;
                }
            });
        }

    }

    private float getDimension(int id) {
        return getResources().getDimension(id);
    }

    @Subscribe
    public void onEventThread(EventCustom eventCustom) {
        if (KEY.FLAG_USB_IN.equals(eventCustom.getTag())) {
            dialog = new ZBXAlertDialog(this, new ZBXAlertListener() {
                @Override
                public void onDialogOk(Dialog dlg) {
                    startActivity(new Intent(BaseApp.getContext(), VideoGridViewActivity.class));
                    dialog.dismiss();
                }

                @Override
                public void onDialogCancel(Dialog dlg) {

                    dialog.dismiss();
                }
            }, "提示", "外部存储设备已连接");
            if (ActivityUtils.isForeground(SettingsActivity.this, "com.example.customrecycle.activitys.setting.SettingsActivity")) {
                dialog.show();
            }
            tvStorage.setVisibility(View.VISIBLE);
        } else if (KEY.FLAG_USB_OUT.equals(eventCustom.getTag())) {
            tvStorage.setVisibility(View.INVISIBLE);
        }
    }
    private void setTime() {
        long sysTime = System.currentTimeMillis();//获取系统时间
        CharSequence sysTimeStr = DateFormat.format("HH:mm", sysTime);//时间显示格式
        tvTime.setText(sysTimeStr); //更新时间
    }
    //时间线程
    class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;  //消息(一个整型值)
                    mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }
}
