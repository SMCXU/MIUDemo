package com.example.customrecycle;

import android.os.Bundle;

import com.example.customrecycle.base.BaseActivity;
import com.example.customrecycle.frame.retrofit.HttpCallBack;
import com.example.customrecycle.frame.retrofit.HttpRequest;
import com.example.customrecycle.frame.retrofit.ResultData;
import com.example.customrecycle.frame.retrofit.UserEntity;
import com.example.customrecycle.frame.utils.MyToast;

import retrofit2.Call;

public class RetrofitTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_test);
        getData();
    }
    private void getData() {
        Call call = HttpRequest.getIResource().getTop();
        callRequest(call, new HttpCallBack(UserEntity.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {

                MyToast.showToast(mResult.getTotal() + "");

            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast(R.string.NETWORKERROR);
            }
        });

    }
}
