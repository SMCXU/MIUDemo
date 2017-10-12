package com.example.customrecycle.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.customrecycle.IjkVideoActivity;
import com.example.customrecycle.MainActivity;
import com.example.customrecycle.R;
import com.example.customrecycle.frame.utils.FileUtils;
import com.example.customrecycle.frame.utils.MyToast;
import com.example.customrecycle.frame.utils.entity.VideoEntity;
import com.example.customrecycle.weight.appweight.MarqueeText;
import com.example.customrecycle.weight.appweight.RoundedFrameLayout;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class RecommendpFragment extends Fragment implements View.OnFocusChangeListener, View.OnClickListener {
    //    流光控件
    private RoundedFrameLayout iv1, iv2, iv3, iv4, iv5;
    //    滚动字幕控件
    private MarqueeText mt1, mt2, mt3, mt4, mt5;
    //    Video列表
    private List<VideoEntity> mList;
    private Intent intent;

    //mt 是滚动字幕控件  rf 是流光特效控件
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.test_page1, container, false);
        iv1 = (RoundedFrameLayout) v.findViewById(R.id.rf_1);
        iv2 = (RoundedFrameLayout) v.findViewById(R.id.rf_2);
        iv3 = (RoundedFrameLayout) v.findViewById(R.id.rf_3);
        iv4 = (RoundedFrameLayout) v.findViewById(R.id.rf_4);
        iv5 = (RoundedFrameLayout) v.findViewById(R.id.rf_5);
        iv1.setOnFocusChangeListener(this);
        iv2.setOnFocusChangeListener(this);
        iv3.setOnFocusChangeListener(this);
        iv4.setOnFocusChangeListener(this);
        iv5.setOnFocusChangeListener(this);
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
        iv5.setOnClickListener(this);
        mt1 = (MarqueeText) v.findViewById(R.id.mt_1);
        mt2 = (MarqueeText) v.findViewById(R.id.mt_2);
        mt3 = (MarqueeText) v.findViewById(R.id.mt_3);
        mt4 = (MarqueeText) v.findViewById(R.id.mt_4);
        mt5 = (MarqueeText) v.findViewById(R.id.mt_5);
        initView();
        return v;
    }

    //初始化view
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {
        mList = new ArrayList<VideoEntity>();
        // 扫描功能
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请CAMERA权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
        } else {
            scanMediaFile();
        }
        intent = new Intent(getActivity(),IjkVideoActivity.class);
        setData();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setData() {
        if (mList.size()>5){
//            iv1.setBackground(getResources().getDrawable(R.mipmap.mainview_huodong));
//            mt1.setText(mList.get(0).getName());
//            iv2.setBackground(getResources().getDrawable(R.mipmap.mainview_huodong));
//            mt2.setText(mList.get(1).getName());
//            iv3.setBackground(getResources().getDrawable(R.mipmap.mainview_huodong));
//            mt3.setText(mList.get(2).getName());
//            iv4.setBackground(getResources().getDrawable(R.mipmap.mainview_tuijian));
//            mt4.setText(mList.get(3).getName());
//            iv5.setBackground(getResources().getDrawable(R.mipmap.mainview_tuijian));
//            mt5.setText(mList.get(4).getName());
        }

    }

    //  获取封面Drawable
    @NonNull
    private BitmapDrawable getBitmapDrawable(int i) {
        Bitmap bp= FileUtils.getLocalVideoBitmap(mList.get(i).getUri());
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

    //扫描获取U盘内数据
    private void scanMediaFile() {
        String[] args = {"mp4", "wmv", "rmvb", "mkv", "avi", "flv"};
        mList.clear();
        mList = FileUtils.getSpecificTypeOfFile(getContext(), args);
    }

    @Override
    public void onClick(View view) {
        int index=0;
        switch (view.getId()){
            case R.id.rf_1:
                index=0;
            break;
            case R.id.rf_2:
                index=1;
            break;
            case R.id.rf_3:
                index=2;
            break;
            case R.id.rf_4:
                index=3;
            break;
            case R.id.rf_5:
                index=4;
            break;
        }

        intent.putExtra("mList",(Serializable)mList);
        intent.putExtra("index",index);
        intent.putExtra("type",0);//本地视频传0
        if(mList.size()>5){
            startActivity(intent);
        }else {
            MyToast.showToast("请检查U盘设备是否插入");
        }


    }
}
