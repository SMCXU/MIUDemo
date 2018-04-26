package com.example.customrecycle.activitys.setting;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.customrecycle.R;
import com.example.customrecycle.activitys.movie.VideoGridViewActivity;
import com.example.customrecycle.base.BaseActivity;
import com.example.customrecycle.base.BaseApp;
import com.example.customrecycle.bridge.EffectNoDrawBridge;
import com.example.customrecycle.frame.EventCustom;
import com.example.customrecycle.frame.utils.ActivityUtils;
import com.example.customrecycle.frame.utils.KEY;
import com.example.customrecycle.frame.utils.MyToast;
import com.example.customrecycle.frame.weightt.ZBXAlertDialog;
import com.example.customrecycle.frame.weightt.ZBXAlertListener;
import com.example.customrecycle.view.MainUpView;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingInfoActivity extends BaseActivity {

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_sound)
    TextView tvSound;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.tv_net)
    TextView tvNet;
    @BindView(R.id.mainUpView1)
    MainUpView mainUpView1;
    @BindView(R.id.rl_contioner)
    RelativeLayout rlContioner;
    @BindView(R.id.tv_remain)
    TextView tvRemain;
    private EffectNoDrawBridge mEffectNoDrawBridge;
    private View mOldFocus;
    private boolean isClose;
    private ZBXAlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_info);
        ButterKnife.bind(this);
        initView();
        initMoveBridge();
    }

    private void initView() {

    }


    @OnClick({R.id.tv_sound, R.id.tv_about, R.id.tv_net})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sound:
                startActivity(new Intent(SettingInfoActivity.this, SoundActivity.class));
                break;
            case R.id.tv_about:
                startActivity(new Intent(SettingInfoActivity.this, AboutActivity.class));
                break;
            case R.id.tv_net:
                MyToast.showToast("敬请期待");
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
            if (ActivityUtils.isForeground(SettingInfoActivity.this, "com.example.customrecycle.activitys.setting.SettingInfoActivity")) {
                dialog.show();
            }

        }
    }

}
