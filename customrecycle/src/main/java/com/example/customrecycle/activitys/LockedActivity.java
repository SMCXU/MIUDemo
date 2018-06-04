package com.example.customrecycle.activitys;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.customrecycle.R;
import com.example.customrecycle.activitys.movie.IjkVideoActivity;
import com.example.customrecycle.activitys.movie.VideoGridViewActivity;
import com.example.customrecycle.base.BaseActivity;
import com.example.customrecycle.base.BaseApp;
import com.example.customrecycle.frame.EventCustom;
import com.example.customrecycle.frame.utils.ActivityUtils;
import com.example.customrecycle.frame.utils.KEY;
import com.example.customrecycle.frame.utils.MyToast;
import com.example.customrecycle.frame.weightt.ZBXAlertDialog;
import com.example.customrecycle.frame.weightt.ZBXAlertListener;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LockedActivity extends BaseActivity {

    @BindView(R.id.search_display_text)
    TextView searchDisplayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locked);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {


    }

    //输入按键的点击事件
    public void searchVideo(View view) {
        CharSequence oldText = searchDisplayText.getText();
        String newText = oldText.toString() + view.getTag();
        searchDisplayText.setText(newText);
        if ("123".equalsIgnoreCase(newText.toString().trim())){
            finish();
        }else {
//            MyToast.showToast("请输入正确的续费验证码");
        }
    }

    //删除,提交按键按键的点击事件
    public void cleanVideo(View view) {
        String oldText = searchDisplayText.getText().toString().trim();
        switch (view.getId()) {
            case R.id.iv_del:
                if (oldText.length() > 0) {
                    oldText = oldText.substring(0, oldText.length() - 1);
                }
                searchDisplayText.setText(oldText);
                break;
            case R.id.iv_submit:
                if ("123".equalsIgnoreCase(oldText)){
                    finish();
                }else {
                    MyToast.showToast("请输入正确的续费验证码");
                }
                break;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            MyToast.showToast("请输入续费验证码");
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
    @Subscribe
    public void onEventThread(EventCustom eventCustom) {

    }

}
