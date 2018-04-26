package com.example.customrecycle.activitys;

import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.customrecycle.R;
import com.example.customrecycle.base.BaseActivity;
import com.example.customrecycle.base.BaseApp;
import com.example.customrecycle.bridge.EffectNoDrawBridge;
import com.example.customrecycle.frame.EventCustom;
import com.example.customrecycle.frame.utils.DeviceUtils;
import com.example.customrecycle.frame.utils.KEY;
import com.example.customrecycle.frame.utils.PreferencesUtils;
import com.example.customrecycle.frame.utils.SocketUtils.ChatServer;
import com.example.customrecycle.frame.utils.VirturlKeyPadCtr;
import com.example.customrecycle.view.MainUpView;
import org.greenrobot.eventbus.Subscribe;
import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ChooseActivity extends BaseActivity {

    @BindView(R.id.tv_visitor)
    TextView tvVisitor;
    @BindView(R.id.tv_settings)
    TextView tvSettings;
    @BindView(R.id.mainUpView1)
    MainUpView mainUpView1;
    @BindView(R.id.rl_container)
    RelativeLayout rlContioner;
    private View mOldFocus;
    private Intent intent;
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        intent = new Intent(this, HomeActivity.class);
        initMoveBridge();
        Log.d("Mr.U", "IP地址是:" + DeviceUtils.getInstance(this).getIPAddress());
        initSocket();

    }

    private void initSocket() {
        try {
            new ChatServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.tv_visitor, R.id.tv_settings})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_visitor:
                skipTo(true);
                break;
            case R.id.tv_settings:
                skipTo(false);
                break;

        }
    }

    private void skipTo(boolean isVisitor) {
        PreferencesUtils.putBoolean(this, KEY.FLAG_ISVISITOR, isVisitor);
        intent.putExtra("isVisitor", isVisitor);
        startActivity(intent);
        finish();
    }

    private void initMoveBridge() {
        float density = getResources().getDisplayMetrics().density;
        mainUpView1 = (MainUpView) findViewById(R.id.mainUpView1);
        EffectNoDrawBridge mEffectNoDrawBridge = new EffectNoDrawBridge();
        mainUpView1.setEffectBridge(mEffectNoDrawBridge);
        mEffectNoDrawBridge.setUpRectResource(R.drawable.white_light_10); // 设置移动边框图片.
        RectF rectF = new RectF(getDimension(R.dimen.x12) * density, getDimension(R.dimen.x12) * density,
                getDimension(R.dimen.x12) * density, getDimension(R.dimen.x12) * density);
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
    }

}
