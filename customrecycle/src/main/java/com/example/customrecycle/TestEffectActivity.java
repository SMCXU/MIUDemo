package com.example.customrecycle;

import android.app.Activity;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.customrecycle.bridge.EffectNoDrawBridge;
import com.example.customrecycle.view.MainUpView;

public class TestEffectActivity extends Activity {

    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;
    private MainUpView mMainUpView;
    private LinearLayout llContioner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_effect);
        initView();
    }

    private void initView() {
        iv1 = (ImageView) findViewById(R.id.iv_1);
        iv2 = (ImageView) findViewById(R.id.iv_2);
        iv3 = (ImageView) findViewById(R.id.iv_3);
        iv4 = (ImageView) findViewById(R.id.iv_4);
        iv5 = (ImageView) findViewById(R.id.iv_5);
        mMainUpView = findViewById(R.id.mMainUpView);
        llContioner = findViewById(R.id.ll_contioner);
        switchNoDrawBridgeVersion();
        llContioner.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldView, View newView) {
                float scale = 1.2f;
                mMainUpView.setFocusView(newView, oldView, scale);
            }
        });
    }


//设置边框
    private void switchNoDrawBridgeVersion() {
        float density = getResources().getDisplayMetrics().density;
        RectF rectf = new RectF(getDimension(R.dimen.x10) * density, getDimension(R.dimen.x10) * density,
                getDimension(R.dimen.x10) * density, getDimension(R.dimen.x10) * density);
        EffectNoDrawBridge effectNoDrawBridge = new EffectNoDrawBridge();
        effectNoDrawBridge.setTranDurAnimTime(200);
        effectNoDrawBridge.setDrawUpRectPadding(rectf);
        mMainUpView.setEffectBridge(effectNoDrawBridge); // 4.3以下版本边框移动.
        mMainUpView.setUpRectResource(R.drawable.white_light_10); // 设置移动边框的图片.
        mMainUpView.setDrawUpRectPadding(rectf); // 边框图片设置间距.
    }
    public float getDimension(int id) {
        return getResources().getDimension(id);
    }

}
