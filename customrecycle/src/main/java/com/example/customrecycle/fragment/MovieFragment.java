package com.example.customrecycle.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.customrecycle.activitys.HomeActivity;
import com.example.customrecycle.activitys.movie.IjkVideoActivity;
import com.example.customrecycle.R;
import com.example.customrecycle.activitys.movie.VideoGridViewActivity;
import com.example.customrecycle.frame.EventCustom;
import com.example.customrecycle.frame.utils.KEY;
import com.example.customrecycle.frame.utils.MyToast;
import com.example.customrecycle.frame.utils.entity.VideoEntity;
import com.example.customrecycle.view.SmoothHorizontalScrollView;
import com.example.customrecycle.weight.appweight.MarqueeText;
import com.example.customrecycle.weight.appweight.RoundedFrameLayout;
import com.example.customrecycle.weight.appweight.TvZorderRelativeLayout;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MovieFragment extends Fragment implements View.OnFocusChangeListener {

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
    @BindView(R.id.tv_hot)
    TextView tvHot;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.tv_first)
    TextView tvFirst;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_relativelayout_list)
    TvZorderRelativeLayout tvRelativelayoutList;
    @BindView(R.id.test_hscroll)
    SmoothHorizontalScrollView testHscroll;
    @BindView(R.id.content11)
    RelativeLayout content11;
    Unbinder unbinder;
    private List<VideoEntity> mList;
    private Intent intent;
    private Intent intent1;

    //mt 是滚动字幕控件  rf 是流光特效控件
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.test_page2, container, false);
        unbinder = ButterKnife.bind(this, v);

        initView();
        return v;
    }

    //初始化view
    private void initView() {
        intent = new Intent(getActivity(), IjkVideoActivity.class);
        intent1 = new Intent(getActivity(), VideoGridViewActivity.class);
        refreshUI();
        rf1.setOnFocusChangeListener(this);
        rf2.setOnFocusChangeListener(this);
        rf3.setOnFocusChangeListener(this);
    }

    private void refreshUI() {
        mList = HomeActivity.videoList;
        if (mList != null && mList.size() > 8) {
            setClickEnable(true);
            mt1.setText(mList.get(5).getName());
            mt2.setText(mList.get(6).getName());
            mt3.setText(mList.get(7).getName());
        } else if (mList.size() == 0) {
            mt1.setText("暂无数据");
            mt2.setText("暂无数据");
            mt3.setText("暂无数据");
            setClickEnable(false);
        }
    }

    private void setClickEnable(boolean isTrue) {
        rf1.setClickable(isTrue);
        rf2.setClickable(isTrue);
        rf3.setClickable(isTrue);
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

    @OnClick({R.id.rf_1, R.id.rf_2, R.id.rf_3, R.id.tv_hot, R.id.tv_new,
            R.id.tv_first, R.id.tv_all})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rf_1:
                playVideo(5);
                break;
            case R.id.rf_2:
                playVideo(6);
                break;
            case R.id.rf_3:
                playVideo(7);
                break;
            case R.id.tv_hot:
            case R.id.tv_new:
            case R.id.tv_first:
            case R.id.tv_all:
                startActivity(intent1);
                break;
        }
    }

    private void playVideo(int index) {
        if (mList.size() > 8) {
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
        }
    }

    @Subscribe
    public void onEventThread(EventCustom eventCustom) {
        //拔出移动存储设备
        if (KEY.FLAG_USB_OUT.equals(eventCustom.getTag())) {
            refreshUI();
            Log.d("Mr.U", "onEventThread:FLAG_USB_OUT ");
            //插入移动存储设备
        } else if (KEY.FLAG_USB_IN.equals(eventCustom.getTag())) {
            refreshUI();
            Log.d("Mr.U", "onEventThread:FLAG_USB_IN ");
        }
    }
}
