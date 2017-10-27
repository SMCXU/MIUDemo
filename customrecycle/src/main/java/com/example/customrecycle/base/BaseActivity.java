package com.example.customrecycle.base;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.customrecycle.frame.EventCustom;
import com.example.customrecycle.frame.retrofit.HttpCallBack;
import com.example.customrecycle.frame.utils.KEY;
import com.example.customrecycle.frame.utils.MyToast;
import com.example.customrecycle.frame.weightt.ZBXAlertDialog;
import com.example.customrecycle.frame.weightt.ZBXAlertListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 *
 *
 */
public abstract class BaseActivity extends AppCompatActivity {

    private List<Call> m_listRequest;
    private ZBXAlertDialog dialog;

    private List<Call> getListRequest() {
        if (m_listRequest == null) {
            m_listRequest = new ArrayList<>();
        }
        return m_listRequest;
    }

    /**
     * 调用网络请求
     *
     * @param call
     * @param httpCallBack
     */
    public void callRequest(Call call, HttpCallBack httpCallBack) {
        call.enqueue(httpCallBack);
        addRequest(call);
    }


    /**
     * 添加到请求队列，以便销毁
     *
     * @param call
     */
    public void addRequest(Call call) {
        getListRequest().add(call);
    }

    @Subscribe
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }
    @Subscribe
    public void onEventThread(EventCustom eventCustom) {

        if (KEY.FLAG_USB.equals(eventCustom.getTag())){
            dialog = new ZBXAlertDialog(this, new ZBXAlertListener() {
                @Override
                public void onDialogOk(Dialog dlg) {
                    MyToast.showToast("跳转到文件列表");
                    dialog.dismiss();
                }
                @Override
                public void onDialogCancel(Dialog dlg) {

                    dialog.dismiss();
                }
            }, "提示", "外部存储设备已连接");
            dialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        //取消网络请求
        if (m_listRequest != null && m_listRequest.size() > 0) {
            for (Call call : m_listRequest) {
                call.cancel();


            }
        }
    }

}
