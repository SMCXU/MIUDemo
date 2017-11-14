package com.example.customrecycle.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.customrecycle.activitys.HomeActivity;
import com.example.customrecycle.activitys.movie.IjkVideoActivity;
import com.example.customrecycle.R;
import com.example.customrecycle.frame.EventCustom;
import com.example.customrecycle.frame.utils.FileUtils;
import com.example.customrecycle.frame.utils.KEY;
import com.example.customrecycle.frame.utils.MyToast;
import com.example.customrecycle.frame.utils.entity.VideoEntity;
import com.example.customrecycle.weight.appweight.MarqueeText;
import com.example.customrecycle.weight.appweight.RoundedFrameLayout;
import com.example.customrecycle.weight.appweight.TvZorderRelativeLayout;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class RecommendpFragment extends Fragment implements View.OnFocusChangeListener, View.OnClickListener {

    @BindView(R.id.mt_1)
    MarqueeText mt1;
    @BindView(R.id.rf_1)
    RoundedFrameLayout rf1;
    @BindView(R.id.mt_2)
    MarqueeText mt2;
    @BindView(R.id.rf_2)
    RoundedFrameLayout rf2;
    @BindView(R.id.mt_3)
    MarqueeText mt3;
    @BindView(R.id.rf_3)
    RoundedFrameLayout rf3;
    @BindView(R.id.mt_4)
    MarqueeText mt4;
    @BindView(R.id.rf_4)
    RoundedFrameLayout rf4;
    @BindView(R.id.mt_5)
    MarqueeText mt5;
    @BindView(R.id.rf_5)
    RoundedFrameLayout rf5;
    @BindView(R.id.tv_relativelayout_list)
    TvZorderRelativeLayout tvRelativelayoutList;
    @BindView(R.id.content11)
    RelativeLayout content11;
    Unbinder unbinder;
    //    Video列表
    private List<VideoEntity> mList;
    private Intent intent;

    //mt 是滚动字幕控件  rf 是流光特效控件
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.test_page1, container, false);
        unbinder = ButterKnife.bind(this, v);
        initView();
        return v;
    }

    //初始化view
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {
        rf1.setOnFocusChangeListener(this);
        rf2.setOnFocusChangeListener(this);
        rf3.setOnFocusChangeListener(this);
        rf4.setOnFocusChangeListener(this);
        rf5.setOnFocusChangeListener(this);
        rf1.setOnClickListener(this);
        rf2.setOnClickListener(this);
        rf3.setOnClickListener(this);
        rf4.setOnClickListener(this);
        rf5.setOnClickListener(this);
        intent = new Intent(getActivity(), IjkVideoActivity.class);
        setData();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setData() {
        mList = HomeActivity.videoList;
        if (mList != null && mList.size() > 5) {
            setClickEnable(true);
            mt1.setText(mList.get(0).getName());
            mt2.setText(mList.get(1).getName());
            mt3.setText(mList.get(2).getName());
            mt4.setText(mList.get(3).getName());
            mt5.setText(mList.get(4).getName());
        } else if (mList.size() == 0) {
            mt1.setText("暂无数据");
            mt2.setText("暂无数据");
            mt3.setText("暂无数据");
            mt4.setText("暂无数据");
            mt5.setText("暂无数据");
            setClickEnable(false);
        }

    }

    private void setClickEnable(boolean istrue) {
        rf1.setClickable(istrue);
        rf2.setClickable(istrue);
        rf3.setClickable(istrue);
        rf4.setClickable(istrue);
        rf5.setClickable(istrue);
    }

    //  获取封面Drawable
    @NonNull
    private BitmapDrawable getBitmapDrawable(int i) {
        Bitmap bp = FileUtils.getLocalVideoBitmap(mList.get(i).getUri());
        return new BitmapDrawable(bp);
    }

    // 滚动动画实例
    private void scrollAnimation(boolean hasFocus, MarqueeText view) {
        if (view != null) {
            if (hasFocus) {
                view.startScroll();
            } else {
                view.stopScroll();
            }
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        switch (view.getId()) {
            case R.id.rf_1:
                scrollAnimation(hasFocus, mt1);
                break;
            case R.id.rf_2:
                scrollAnimation(hasFocus, mt2);
                break;
            case R.id.rf_3:
                scrollAnimation(hasFocus, mt3);
                break;
            case R.id.rf_4:
                scrollAnimation(hasFocus, mt4);
                break;
            case R.id.rf_5:
                scrollAnimation(hasFocus, mt5);
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View view) {
        int index = 0;
        switch (view.getId()) {
            case R.id.rf_1:
                index = 0;
                break;
            case R.id.rf_2:
                index = 1;
                break;
            case R.id.rf_3:
                index = 2;
                break;
            case R.id.rf_4:
                index = 3;
                break;
            case R.id.rf_5:
                index = 4;
                break;
        }
        intent.putExtra("index", index);
        intent.putExtra("type", 0);//本地视频传0
        if (mList.size() > 5) {
            startActivity(intent);
        } else {
            MyToast.showToast("请检查U盘设备是否插入");
        }


    }

    @Subscribe
    public void onEventThread(EventCustom eventCustom) {
        //拔出移动存储设备
        if (KEY.FLAG_USB_OUT.equals(eventCustom.getTag())) {
            setData();
            //插入移动存储设备
        } else if (KEY.FLAG_USB_IN.equals(eventCustom.getTag())) {
            setData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
