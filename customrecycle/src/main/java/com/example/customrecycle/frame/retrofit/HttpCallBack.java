package com.example.customrecycle.frame.retrofit;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.customrecycle.R;
import com.example.customrecycle.base.BaseApp;
import com.example.customrecycle.frame.utils.DeviceUtils;
import com.example.customrecycle.frame.utils.MyToast;
import com.example.customrecycle.frame.weightt.MyProgressDialog;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zbx on 16/3/10.
 */
public abstract class HttpCallBack<T> implements Callback {

    public abstract void onSuccess(ResultData mResult);

    public abstract void onFailure(String string);

    private Class<T> mClazz;
    private Context mContext;

    private Dialog progressDialog;// 自定义加载弹框

    /**
     * @param clazz
     * @param context
     */
    public HttpCallBack(Class<T> clazz, Context context) {
        init(clazz, context);
    }

    /**
     * @param clazz
     * @param context
     * @param isShowProgress 是否显示进度条
     */
    public HttpCallBack(Class<T> clazz, Context context, boolean isShowProgress) {
        init(clazz, context);
        if (isShowProgress) {
            showDialog();
        }
    }

    private void init(Class<T> clazz, Context context) {
        mClazz = clazz;
        mContext = context;

        if (!DeviceUtils.getInstance(context).hasInternet()) {//未联网
            Toast.makeText(context, "无网络连接，请检查网络设置！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Call call, Response response) {
        cancelDialog();
        if (response.code() == 200) {
            onSuccess(new ResultData<T>().parse((JsonObject) response.body(), mClazz));
            return;
        } else if (response.code() == 401) {
            MyToast.showToast("会话失效，请重新登录");
            Intent intent = new Intent();
            ComponentName cn = new ComponentName("com.zbxn", "com.zbxn.main.activity.MainActivity");
            intent.setComponent(cn);
            intent.putExtra("exit", "exit");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//这句必须加
            Context context = BaseApp.getContext();
            context.startActivity(intent);
        } else {
            String errorData = "";
            try {
                errorData = response.errorBody().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new ResultData<T>().parse(errorData, mClazz);
            /*if (response.body() == null) {
                onFailure(null, null);
                return;
            }
            onSuccess(new ResultData<T>().parse((JsonObject) response.body(), mClazz));*/
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        cancelDialog();
        if (t != null && t.getMessage() != null && (t.getMessage().equals("Canceled") || t.getMessage().equals("Socket closed"))) {
            return;
        }
        //Toast.makeText(BaseApp.getContext(), R.string.NETWORKERROR, Toast.LENGTH_SHORT).show();

        onFailure(BaseApp.getContext().getString(R.string.NETWORKERROR));
    }

    /**
     * 显示弹窗
     */
    private void showDialog() {
        try {
            if (progressDialog == null) {
                progressDialog = new MyProgressDialog(mContext, true);
            }
            progressDialog.show();
        } catch (Exception e) {
        }
    }

    /**
     * 关闭弹窗
     */
    private void cancelDialog() {
        try {
            if (null != progressDialog) {
                progressDialog.cancel();
            }
        } catch (Exception e) {
        }
    }
}
