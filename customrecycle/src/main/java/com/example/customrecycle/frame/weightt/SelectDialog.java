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
import com.example.customrecycle.frame.utils.StringUtils;
import com.example.customrecycle.view.MainUpView;

import java.util.List;


/**
 * Created by Administrator on 2017/3/24.
 */
public class SelectDialog extends Dialog {
    private Context m_Context;
    private TextView m_dialogFirst;
    private TextView m_dialogSecond;
    private TextView m_dialogThird;
    private TextView m_textTitle;
    private List<String> items;
    private SelectAlertListener m_listener;
    private String title = "";
    private String info = "";
    private MainUpView mainUpView1;
    private LinearLayout llContainer;
    private EffectNoDrawBridge mEffectNoDrawBridge;
    private View mOldFocus;

    /**
     * 参数一：上下文对象
     * 参数二：监听器
     * 参数三：title
     * 参数四：条目内容
     *
     * */
    public SelectDialog(Context context, SelectAlertListener listener, String title, List<String> items) {
        super(context, R.style.dialog_alert_bg);
        this.m_Context = context;
        this.m_listener = listener;
        this.title = title;
        this.items = items;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select);
        setCanceledOnTouchOutside(true);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        initView();
        initMoveBridge();
    }

    private void initView() {
        llContainer = (LinearLayout)findViewById(R.id.ll_contioner);
        mainUpView1 =(MainUpView) findViewById(R.id.mainUpView1);
        m_textTitle = (TextView) findViewById(R.id.tv_title);
        m_textTitle.setText(title);
        m_dialogFirst = (TextView) findViewById(R.id.tv_first);
        m_dialogSecond = (TextView) findViewById(R.id.tv_second);
        m_dialogThird = (TextView) findViewById(R.id.tv_third);
        setItemVisibility(items.size());

        m_dialogFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_listener.onDialogFirst(SelectDialog.this);
            }
        });
        m_dialogSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_listener.onDialogSecond(SelectDialog.this);
            }
        });
        m_dialogThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_listener.onDialogthird(SelectDialog.this);
            }
        });
    }

    /**
     * 根据要求显示相应的条目数，最多三条
     */
    public void setItemVisibility(int num) {
        if (0 == num) {
            m_dialogFirst.setVisibility(View.GONE);
            m_dialogSecond.setVisibility(View.GONE);
            m_dialogThird.setVisibility(View.GONE);
        } else if (1 == num) {
            m_dialogSecond.setVisibility(View.GONE);
            m_dialogThird.setVisibility(View.GONE);
            m_dialogFirst.setText(items.get(0));
        } else if (2==num){
            m_dialogThird.setVisibility(View.GONE);

            m_dialogFirst.setText(items.get(0));
            m_dialogSecond.setText(items.get(1));
        } else {
            m_dialogFirst.setText(items.get(0));
            m_dialogSecond.setText(items.get(1));
            m_dialogThird.setText(items.get(2));
        }
    }

    //移动边框
    private void initMoveBridge() {
        float density = m_Context.getResources().getDisplayMetrics().density;
        mainUpView1 = (MainUpView) findViewById(R.id.mainUpView1);
        mEffectNoDrawBridge = new EffectNoDrawBridge();
        mainUpView1.setEffectBridge(mEffectNoDrawBridge);
//        mEffectNoDrawBridge.setUpRectResource(R.drawable.white_light_10); // 设置移动边框图片.
        RectF rectF = new RectF(getDimension(R.dimen.x14) * density, getDimension(R.dimen.x14) * density,
                getDimension(R.dimen.x14) * density, getDimension(R.dimen.x14) * density);
        mEffectNoDrawBridge.setDrawUpRectPadding(rectF);
        llContainer.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(final View oldFocus, final View newFocus) {
//                if (newFocus != null)
//                    newFocus.bringToFront(); // 防止放大的view被压在下面. (建议使用MainLayout)
                float scale = 1.5f;
                mainUpView1.setFocusView(newFocus, mOldFocus, scale);
                mOldFocus = newFocus; // 4.3以下需要自己保存.
            }
        });

        for (int i = 0; i < llContainer.getChildCount(); i++) {
            llContainer.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
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

}
