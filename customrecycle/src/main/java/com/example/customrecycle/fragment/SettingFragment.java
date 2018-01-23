package com.example.customrecycle.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.customrecycle.R;
import com.example.customrecycle.activitys.HomeActivity;
import com.example.customrecycle.activitys.movie.VideoGridViewActivity;
import com.example.customrecycle.activitys.setting.SettingInfoActivity;
import com.example.customrecycle.activitys.setting.SettingsActivity;
import com.example.customrecycle.base.BaseApp;
import com.example.customrecycle.base.BaseFragment;
import com.example.customrecycle.frame.EventCustom;
import com.example.customrecycle.frame.utils.ActivityUtils;
import com.example.customrecycle.frame.utils.KEY;
import com.example.customrecycle.frame.weightt.ZBXAlertDialog;
import com.example.customrecycle.frame.weightt.ZBXAlertListener;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class SettingFragment extends BaseFragment {

    @BindView(R.id.tv_storage)
    TextView tvStorage;
    @BindView(R.id.tv_settings)
    TextView tvSettings;
    Unbinder unbinder;
    private ZBXAlertDialog dialog;

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        tvStorage=(TextView) view.findViewById(R.id.tv_storage);
        tvSettings=(TextView) view.findViewById(R.id.tv_settings);
        initView();
        return view;
    }

    private void initView() {
        if (HomeActivity.videoList.size()>0){
            tvStorage.setVisibility(View.VISIBLE);
        }else {
            tvStorage.setVisibility(View.INVISIBLE);
        }
    }
    @OnClick({R.id.tv_storage, R.id.tv_settings})
    protected void onClick(View view) {
        switch (view.getId()) {
            //移动硬盘
            case R.id.tv_storage:
                Intent intent = new Intent(getContext(), VideoGridViewActivity.class);
                intent.putExtra("title","全部");
                startActivity(intent);
                break;
            //设置
            case R.id.tv_settings:
                startActivity(new Intent(getContext(), SettingInfoActivity.class));
                break;
        }
    }
    @Override
    protected void initialize(View root, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {

    }

    @Subscribe
    public void onEventThread(EventCustom eventCustom) {
        if (KEY.FLAG_USB_IN.equals(eventCustom.getTag())) {
            dialog = new ZBXAlertDialog(getContext(), new ZBXAlertListener() {
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
            if (ActivityUtils.isForeground(getContext(), "com.example.customrecycle.activitys.setting.SettingsActivity")) {
                dialog.show();
            }
            tvStorage.setVisibility(View.VISIBLE);
        } else if (KEY.FLAG_USB_OUT.equals(eventCustom.getTag())) {
            tvStorage.setVisibility(View.INVISIBLE);
        }
    }


}
