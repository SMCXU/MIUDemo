package com.example.customrecycle.frame.weightt;

import android.app.Dialog;
import android.content.Context;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customrecycle.R;
import com.example.customrecycle.bridge.EffectNoDrawBridge;
import com.example.customrecycle.view.MainUpView;

import java.util.List;


/**
 * Created by Administrator on 2017/3/24.
 */
public class ImageDialog extends Dialog {
    /**
     * 参数一：上下文对象
     * 参数二：监听器
     * 参数三：title
     * 参数四：条目内容
     *
     * */
    public ImageDialog(Context context) {
        super(context, R.style.dialog_alert_bg);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_image);
        setCanceledOnTouchOutside(true);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }


    private void onBtnOk() {
        cancel();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBtnOk();
//        }
        return super.onKeyDown(keyCode, event);
    }

}
