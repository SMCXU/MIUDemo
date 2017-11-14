package com.example.customrecycle.frame.weightt;

import android.app.Dialog;
import android.content.Context;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.customrecycle.R;
import com.example.customrecycle.bridge.EffectNoDrawBridge;
import com.example.customrecycle.frame.utils.StringUtils;
import com.example.customrecycle.view.MainUpView;

import butterknife.BindView;


/**
 * Created by Administrator on 2017/3/24.
 */
public class ZBXAlertDialog extends Dialog {
    private Context m_Context;

    private TextView m_textInfo;
    private TextView m_dialogOk;
    private TextView m_textTitle;
    private TextView m_dialogCancel;

    private ZBXAlertListener m_listener;
    private String title = "";
    private String info = "";
    private TextView m_tvLine;
    private EffectNoDrawBridge mEffectNoDrawBridge;
    private View mOldFocus;
    private MainUpView mainUpView1;
    private RelativeLayout rlContioner;

    public ZBXAlertDialog(Context context, ZBXAlertListener listener, String title, String info) {
        super(context, R.style.dialog_alert_bg);
        this.m_Context = context;
        this.m_listener = listener;
        this.title = title;
        this.info = info;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_zbxalert);
        setCanceledOnTouchOutside(true);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        m_textInfo = (TextView) findViewById(R.id.tv_Info);
        m_textTitle = (TextView) findViewById(R.id.tv_title);
        if (StringUtils.isEmpty(title)) {
            m_textTitle.setVisibility(View.GONE);
        } else {
            m_textTitle.setText(title);
        }

        if (StringUtils.isEmpty(info)) {
            m_textInfo.setVisibility(View.GONE);
        } else {
            m_textInfo.setText(info);
        }

        initMoveBridge();

        m_dialogOk = (TextView) findViewById(R.id.dialog_ok);
        m_dialogCancel = (TextView) findViewById(R.id.dialog_cancel);
//        m_tvLine = ((TextView) findViewById(R.id.tv_line));
        m_dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_listener.onDialogOk(ZBXAlertDialog.this);
            }
        });
        m_dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_listener.onDialogCancel(ZBXAlertDialog.this);
            }
        });
    }

    /**
     * 隐藏左边的按钮，只显示一个按钮，需在.show之后调用
     */
    public void setCancelGone() {
        m_dialogCancel.setVisibility(View.GONE);
//        m_tvLine.setVisibility(View.GONE);
        m_dialogOk.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.style_bt_nav_trans1));
    }

    private void onBtnOk() {
        cancel();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBtnOk();
        }
        return super.onKeyDown(keyCode, event);
    }

    //移动边框
    private void initMoveBridge() {
        float density = m_Context.getResources().getDisplayMetrics().density;
        mainUpView1 = (MainUpView) findViewById(R.id.mainUpView1);
        rlContioner = (RelativeLayout) findViewById(R.id.rl_contioner);
        mEffectNoDrawBridge = new EffectNoDrawBridge();
        mainUpView1.setEffectBridge(mEffectNoDrawBridge);
//        mEffectNoDrawBridge.setUpRectResource(R.drawable.white_light_10); // 设置移动边框图片.
        RectF rectF = new RectF(getDimension(R.dimen.x14) * density, getDimension(R.dimen.x14) * density,
                getDimension(R.dimen.x14) * density, getDimension(R.dimen.x14) * density);
        mEffectNoDrawBridge.setDrawUpRectPadding(rectF);
        rlContioner.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(final View oldFocus, final View newFocus) {
                float scale = 1.5f;
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
        return m_Context.getResources().getDimension(id);
    }
}
