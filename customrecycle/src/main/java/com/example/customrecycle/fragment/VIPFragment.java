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
import com.example.customrecycle.activitys.movie.IjkVideoActivity;
import com.example.customrecycle.activitys.movie.MovieDetailActivity;
import com.example.customrecycle.activitys.movie.VideoGridViewActivity;
import com.example.customrecycle.base.BaseFragment;
import com.example.customrecycle.frame.EventCustom;
import com.example.customrecycle.frame.utils.KEY;
import com.example.customrecycle.frame.utils.MyToast;
import com.example.customrecycle.frame.utils.entity.VideoEntity;
import com.example.customrecycle.weight.appweight.MarqueeText;
import com.example.customrecycle.weight.appweight.RoundedFrameLayout;
import com.example.customrecycle.weight.appweight.TvZorderRelativeLayout;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class VIPFragment extends BaseFragment {

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
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.tv_4)
    TextView tv4;
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
    Unbinder unbinder;
    private List<VideoEntity> mList;
    private Intent intent, intent1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vi, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();

        return view;
    }

    private void initView() {
        mList = new ArrayList<>();
        intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent1 = new Intent(getActivity(), VideoGridViewActivity.class);
        initData();
    }

    private void initData() {
        mList = HomeActivity.videoList;
        if (mList != null && mList.size() > 10) {
            mt1.setText(mList.get(6).getName());
            mt2.setText(mList.get(7).getName());
            mt3.setText(mList.get(8).getName());
            mt4.setText(mList.get(9).getName());
            mt5.setText(mList.get(10).getName());
        } else {
            mt1.setText("暂无数据");
            mt2.setText("暂无数据");
            mt3.setText("暂无数据");
            mt4.setText("暂无数据");
            mt5.setText("暂无数据");
        }
    }

    @OnClick({R.id.rf_1, R.id.rf_2, R.id.rf_3, R.id.rf_4, R.id.rf_5, R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4})
    public void onClick(View view) {
        int index = 0;
        String title = "";
        switch (view.getId()) {
            case R.id.rf_1:
                index = 6;
                break;
            case R.id.rf_2:
                index = 7;
                break;
            case R.id.rf_3:
                index = 8;
                break;
            case R.id.rf_4:
                index = 9;
                break;
            case R.id.rf_5:
                index = 10;
                break;
            case R.id.tv_1:
                title = "最新上线";
                break;
            case R.id.tv_2:
                title = "院线首播";
                break;
            case R.id.tv_3:
                title = "独播电视剧";
                break;
            case R.id.tv_4:
                title = "精彩好莱坞";
                break;
        }
        if (view.getTag() != null && "sort".equals(view.getTag())) {
            intent1.putExtra("title", title);
            startActivity(intent1);
        } else {
            intent.putExtra("index", index);
            intent.putExtra("type", 0);//本地视频传0
            if (mList.size() > 10) {
                startActivity(intent);
            } else {
                MyToast.showToast("请检查U盘设备是否插入");
            }
        }
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


    @Subscribe
    public void onEventThread(EventCustom eventCustom) {
        Log.d("Mr.U", "onEventThread: VIP=========");
        //拔出移动存储设备
        if (KEY.FLAG_USB_OUT.equals(eventCustom.getTag())) {
            //插入移动存储设备
            initData();
        } else if (KEY.FLAG_USB_IN.equals(eventCustom.getTag())) {
            initData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
