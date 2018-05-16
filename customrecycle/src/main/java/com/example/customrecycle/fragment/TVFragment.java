package com.example.customrecycle.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.customrecycle.activitys.HomeActivity;
import com.example.customrecycle.R;
import com.example.customrecycle.activitys.movie.MovieDetailActivity;
import com.example.customrecycle.base.BaseFragment;
import com.example.customrecycle.frame.EventCustom;
import com.example.customrecycle.frame.utils.KEY;
import com.example.customrecycle.frame.utils.MyToast;
import com.example.customrecycle.entity.VideoEntity;
import com.example.customrecycle.view.SmoothHorizontalScrollView;
import com.example.customrecycle.weight.appweight.MarqueeText;
import com.example.customrecycle.weight.appweight.RoundedFrameLayout;
import com.example.customrecycle.weight.appweight.TvZorderRelativeLayout;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class TVFragment extends BaseFragment implements View.OnFocusChangeListener {


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
    @BindView(R.id.mt_6)
    MarqueeText mt6;
    @BindView(R.id.rf_6)
    RoundedFrameLayout rf6;
    @BindView(R.id.mt_7)
    MarqueeText mt7;
    @BindView(R.id.rf_7)
    RoundedFrameLayout rf7;
    @BindView(R.id.mt_8)
    MarqueeText mt8;
    @BindView(R.id.rf_8)
    RoundedFrameLayout rf8;
    @BindView(R.id.mt_9)
    MarqueeText mt9;
    @BindView(R.id.rf_9)
    RoundedFrameLayout rf9;
    @BindView(R.id.tv_relativelayout_list)
    TvZorderRelativeLayout tvRelativelayoutList;
    @BindView(R.id.test_hscroll)
    SmoothHorizontalScrollView testHscroll;
    @BindView(R.id.content11)
    RelativeLayout content11;
    Unbinder unbinder;
    private List<VideoEntity> mList;
    private Intent intent;

    //mt 是滚动字幕控件  rf 是流光特效控件
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.test_page3, container, false);
        unbinder = ButterKnife.bind(this, v);
        initView();
        return v;
    }

    //初始化view
    private void initView() {
        intent = new Intent(getActivity(), MovieDetailActivity.class);
        mList = HomeActivity.videoList;
        if (mList != null && mList.size() > 17) {
            mt1.setText(mList.get(9).getName());
            mt2.setText(mList.get(10).getName());
            mt3.setText(mList.get(11).getName());
            mt4.setText(mList.get(12).getName());
            mt5.setText(mList.get(13).getName());
            mt6.setText(mList.get(14).getName());
            mt7.setText(mList.get(15).getName());
            mt8.setText(mList.get(16).getName());
            mt9.setText(mList.get(17).getName());
            setClickEnable(true);
        } else if (mList.size() == 0) {
            mt1.setText("暂无数据");
            mt2.setText("暂无数据");
            mt3.setText("暂无数据");
            mt4.setText("暂无数据");
            mt5.setText("暂无数据");
            mt6.setText("暂无数据");
            mt7.setText("暂无数据");
            mt8.setText("暂无数据");
            mt9.setText("暂无数据");
            setClickEnable(false);
        }
        rf1.setOnFocusChangeListener(this);
        rf2.setOnFocusChangeListener(this);
        rf3.setOnFocusChangeListener(this);
        rf4.setOnFocusChangeListener(this);
        rf5.setOnFocusChangeListener(this);
        rf6.setOnFocusChangeListener(this);
        rf7.setOnFocusChangeListener(this);
        rf8.setOnFocusChangeListener(this);
        rf9.setOnFocusChangeListener(this);

    }

    private void setClickEnable(boolean isTrue) {
        rf1.setClickable(isTrue);
        rf2.setClickable(isTrue);
        rf3.setClickable(isTrue);
        rf4.setClickable(isTrue);
        rf5.setClickable(isTrue);
        rf6.setClickable(isTrue);
        rf7.setClickable(isTrue);
        rf8.setClickable(isTrue);
        rf9.setClickable(isTrue);
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

    @OnClick({R.id.rf_1, R.id.rf_2, R.id.rf_3, R.id.rf_4, R.id.rf_5,
            R.id.rf_6, R.id.rf_7, R.id.rf_8, R.id.rf_9})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rf_1:
                playVideo(9);
                break;
            case R.id.rf_2:
                playVideo(10);
                break;
            case R.id.rf_3:
                playVideo(11);
                break;
            case R.id.rf_4:
                playVideo(12);
                break;
            case R.id.rf_5:
                playVideo(13);
                break;
            case R.id.rf_6:
                playVideo(14);
                break;
            case R.id.rf_7:
                playVideo(15);
                break;
            case R.id.rf_8:
                playVideo(16);
                break;
            case R.id.rf_9:
                playVideo(17);
                break;

        }
    }

    private void playVideo(int index) {
        if (mList != null && mList.size() > 17) {
            intent.putExtra("index", index);
            intent.putExtra("type", 0);//本地视频传0
            startActivity(intent);
        } else {
            MyToast.showToast("请检查U盘设备是否插入");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_sort, container, false);
    }

    @Override
    protected void initialize(View root, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {

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
            case R.id.rf_6:
                scrollAnimation(hasFocus, mt6);
                break;
            case R.id.rf_7:
                scrollAnimation(hasFocus, mt7);
                break;
            case R.id.rf_8:
                scrollAnimation(hasFocus, mt8);
                break;
            case R.id.rf_9:
                scrollAnimation(hasFocus, mt9);
                break;

        }
    }
    @Subscribe
    public void onEventThread(EventCustom eventCustom) {
        //拔出移动存储设备
        if (KEY.FLAG_USB_OUT.equals(eventCustom.getTag())) {
            initView();
            //插入移动存储设备
        } else if (KEY.FLAG_USB_IN.equals(eventCustom.getTag())) {
            initView();
        }
    }
}
