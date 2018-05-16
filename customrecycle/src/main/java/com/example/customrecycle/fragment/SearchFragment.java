package com.example.customrecycle.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.customrecycle.R;
import com.example.customrecycle.activitys.HomeActivity;
import com.example.customrecycle.activitys.movie.MovieDetailActivity;
import com.example.customrecycle.activitys.movie.SearchActivity;
import com.example.customrecycle.base.BaseFragment;
import com.example.customrecycle.frame.EventCustom;
import com.example.customrecycle.frame.utils.KEY;
import com.example.customrecycle.frame.utils.MyToast;
import com.example.customrecycle.entity.VideoEntity;
import com.example.customrecycle.frame.weightt.ZBXAlertDialog;
import com.example.customrecycle.weight.appweight.MarqueeText;
import com.example.customrecycle.weight.appweight.RoundedFrameLayout;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_hotSearch)
    TextView tvHotSearch;
    @BindView(R.id.tv_hotSelect)
    TextView tvHotSelect;
    Unbinder unbinder;
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
    @BindView(R.id.mt_11)
    MarqueeText mt11;
    @BindView(R.id.rf_11)
    RoundedFrameLayout rf11;
    @BindView(R.id.mt_12)
    MarqueeText mt12;
    @BindView(R.id.rf_12)
    RoundedFrameLayout rf12;
    @BindView(R.id.mt_13)
    MarqueeText mt13;
    @BindView(R.id.rf_13)
    RoundedFrameLayout rf13;
    @BindView(R.id.mt_14)
    MarqueeText mt14;
    @BindView(R.id.rf_14)
    RoundedFrameLayout rf14;
    @BindView(R.id.mt_15)
    MarqueeText mt15;
    @BindView(R.id.rf_15)
    RoundedFrameLayout rf15;
    @BindView(R.id.mt_16)
    MarqueeText mt16;
    @BindView(R.id.rf_16)
    RoundedFrameLayout rf16;
    private List<VideoEntity> mList;
    private ZBXAlertDialog dialog;
    private Intent intent;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    //刷新数据
    private void initData() {
        mList = HomeActivity.videoList;
        Log.d("Mr.U", "initData: " + HomeActivity.videoList.size());
        if (mList != null && mList.size() > 6) {
            mList = mList.subList(0, 6);
            mt1.setText(mList.get(0).getName());
            mt2.setText(mList.get(1).getName());
            mt3.setText(mList.get(2).getName());
            mt4.setText(mList.get(3).getName());
            mt5.setText(mList.get(4).getName());
            mt6.setText(mList.get(5).getName());
            mt11.setText(mList.get(1).getName());
            mt12.setText(mList.get(2).getName());
            mt13.setText(mList.get(3).getName());
            mt14.setText(mList.get(4).getName());
            mt15.setText(mList.get(5).getName());
            mt16.setText(mList.get(0).getName());
            setClickable(true);
        } else  {
            mt1.setText("暂无数据");
            mt2.setText("暂无数据");
            mt3.setText("暂无数据");
            mt4.setText("暂无数据");
            mt5.setText("暂无数据");
            mt6.setText("暂无数据");
            mt11.setText("暂无数据");
            mt12.setText("暂无数据");
            mt13.setText("暂无数据");
            mt14.setText("暂无数据");
            mt15.setText("暂无数据");
            mt16.setText("暂无数据");
            MyToast.showToast("请检查U盘设备是否插入");
            setClickable(false);

        }
    }

    private void setClickable(boolean isClick) {
        rf1.setClickable(isClick);
        rf2.setClickable(isClick);
        rf3.setClickable(isClick);
        rf4.setClickable(isClick);
        rf5.setClickable(isClick);
        rf6.setClickable(isClick);
        rf11.setClickable(isClick);
        rf12.setClickable(isClick);
        rf13.setClickable(isClick);
        rf14.setClickable(isClick);
        rf15.setClickable(isClick);
        rf16.setClickable(isClick);
    }

    private void initView() {
        intent = new Intent(getContext(), MovieDetailActivity.class);
        tvSearch.setOnClickListener(this);
        rf1.setOnClickListener(this);
        rf2.setOnClickListener(this);
        rf3.setOnClickListener(this);
        rf4.setOnClickListener(this);
        rf5.setOnClickListener(this);
        rf6.setOnClickListener(this);
        rf11.setOnClickListener(this);
        rf12.setOnClickListener(this);
        rf13.setOnClickListener(this);
        rf14.setOnClickListener(this);
        rf15.setOnClickListener(this);
        rf16.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    protected void initialize(View root, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
            case R.id.rf_1:
                setIntents(0);
                break;
            case R.id.rf_2:
                setIntents(1);
                break;
            case R.id.rf_3:
                setIntents(2);
                break;
            case R.id.rf_4:
                setIntents(3);
                break;
            case R.id.rf_5:
                setIntents(4);
                break;
            case R.id.rf_6:
                setIntents(5);
                break;
            case R.id.rf_11:
                setIntents(1);
                break;
            case R.id.rf_12:
                setIntents(2);
                break;
            case R.id.rf_13:
                setIntents(3);
                break;
            case R.id.rf_14:
                setIntents(4);
                break;
            case R.id.rf_15:
                setIntents(5);
                break;
            case R.id.rf_16:
                setIntents(0);
                break;

        }
    }

    private void setIntents(int index) {
        intent.putExtra("index", index);
        intent.putExtra("type", 0);//本地视频传0
        startActivity(intent);
    }

    @Subscribe
    public void onEventThread(EventCustom eventCustom) {
        Log.d("Mr.U", "onEventThread: search=========");
        //拔出移动存储设备
        if (KEY.FLAG_USB_OUT.equals(eventCustom.getTag())) {
            initData();
            //插入移动存储设备
        } else if (KEY.FLAG_USB_IN.equals(eventCustom.getTag())) {
            initData();
        }
    }
}
