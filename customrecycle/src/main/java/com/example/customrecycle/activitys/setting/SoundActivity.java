package com.example.customrecycle.activitys.setting;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
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
import com.example.customrecycle.frame.utils.PreferencesUtils;
import com.example.customrecycle.frame.utils.StringUtils;
import com.example.customrecycle.frame.weightt.MyProgressDialog;
import com.example.customrecycle.frame.weightt.SelectAlertListener;
import com.example.customrecycle.frame.weightt.SelectDialog;
import com.example.customrecycle.frame.weightt.ZBXAlertDialog;
import com.example.customrecycle.frame.weightt.ZBXAlertListener;
import com.example.customrecycle.view.MainUpView;
import com.example.customrecycle.weight.appweight.TvZorderRelativeLayout;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SoundActivity extends BaseActivity {

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_key)
    TextView tvKey;
    @BindView(R.id.tv_outPut)
    TextView tvOutPut;
    @BindView(R.id.mainUpView1)
    MainUpView mainUpView1;
    @BindView(R.id.rl_container)
    TvZorderRelativeLayout rlContainer;
    private EffectNoDrawBridge mEffectNoDrawBridge;
    private View mOldFocus;
    private SelectDialog mKeyDialog;
    private int REQUEST_CODE = 1;
    private boolean isClose;
    private List<String> keySounds;
    private List<String> sounds;
    private SelectDialog mSoundDialog;
    private ZBXAlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        ButterKnife.bind(this);
        initView();
        initMoveBridge();
    }

    private void initView() {
        keySounds = new ArrayList<>();
        sounds = new ArrayList<>();
        keySounds.add("开启");
        keySounds.add("关闭");

        sounds.add("Auto");
        sounds.add("PCM");
        sounds.add("Raw data");

        String keyState = PreferencesUtils.getString(this, KEY.FLAG_Sound);
        if (!StringUtils.isEmpty(keyState)){
            tvKey.setText(keyState);
        }
        tvKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mKeyDialog = new SelectDialog(SoundActivity.this, new SelectAlertListener() {
                    //开启声音
                    @Override
                    public void onDialogFirst(Dialog dlg) {
                        isClose = true;
                        setSound();
                        tvKey.setText("按键音:当前开启");
                        PreferencesUtils.putString(getApplicationContext(), KEY.FLAG_Sound, "按键音:当前开启");
                        mKeyDialog.dismiss();
                    }

                    //静音
                    @Override
                    public void onDialogSecond(Dialog dlg) {
                        isClose = false;
                        setSound();
                        tvKey.setText("按键音:当前关闭");
                        PreferencesUtils.putString(getApplicationContext(), KEY.FLAG_Sound, "按键音:当前关闭");
                        mKeyDialog.dismiss();
                    }

                    @Override
                    public void onDialogthird(Dialog dlg) {

                        mKeyDialog.dismiss();
                    }
                }, "声音", keySounds);
                mKeyDialog.show();
            }
        });

        tvOutPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSoundDialog = new SelectDialog(SoundActivity.this, new SelectAlertListener() {
                    @Override
                    public void onDialogFirst(Dialog dlg) {
                        tvOutPut.setText("数字音频输出:Auto");
                        mSoundDialog.dismiss();
                    }

                    @Override
                    public void onDialogSecond(Dialog dlg) {
                        tvOutPut.setText("数字音频输出:PCM");
                        mSoundDialog.dismiss();
                    }

                    @Override
                    public void onDialogthird(Dialog dlg) {
                        tvOutPut.setText("数字音频输出:Raw data");
                        mSoundDialog.dismiss();
                    }
                }, "数字音频输出", sounds);
                mSoundDialog.show();
            }
        });
    }

    //设置开关声音
    private void setSound() {

        if (Build.VERSION.SDK_INT >= 23) {
            checkPermission();
        }
    }

    //6.0以上才能调用
    @TargetApi(23)
    void checkPermission() {
        //跳转设置界面
        if (!Settings.System.canWrite(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Settings.System.putInt(this.getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED, isClose ? 1 : 0);
            AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            if (isClose) {
                mAudioManager.loadSoundEffects();
            } else {
                mAudioManager.unloadSoundEffects();
            }
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
        rlContainer.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(final View oldFocus, final View newFocus) {
                if (newFocus != null)
                    newFocus.bringToFront(); // 防止放大的view被压在下面. (建议使用MainLayout)
                float scale = 1.2f;
                mainUpView1.setFocusView(newFocus, mOldFocus, scale);
                mOldFocus = newFocus; // 4.3以下需要自己保存.
            }
        });

        for (int i = 0; i < rlContainer.getChildCount(); i++) {
            rlContainer.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
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
        Log.d("Mr.U", "onEventThread: soundddddddd1");
        if (KEY.FLAG_USB_IN.equals(eventCustom.getTag())) {
            Log.d("Mr.U", "onEventThread: soundddddddd2");
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
            if (ActivityUtils.isForeground(SoundActivity.this, "com.example.customrecycle.activitys.setting.SoundActivity")) {
                Log.d("Mr.U", "onEventThread: soundddddddd3");
                dialog.show();
            }
        }
    }
}
