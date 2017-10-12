package com.example.customrecycle.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.customrecycle.IjkVideoActivity;
import com.example.customrecycle.MainActivity;
import com.example.customrecycle.R;
import com.example.customrecycle.VideoGridViewActivity;
import com.example.customrecycle.VideoListActivity;
import com.example.customrecycle.frame.utils.MyToast;
import com.example.customrecycle.frame.utils.entity.VideoEntity;
import com.example.customrecycle.view.SmoothHorizontalScrollView;
import com.example.customrecycle.weight.appweight.MarqueeText;
import com.example.customrecycle.weight.appweight.RoundedFrameLayout;
import com.example.customrecycle.weight.appweight.TvZorderRelativeLayout;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MovieFragment extends Fragment {

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
        intent = new Intent(getActivity(),IjkVideoActivity.class);
        intent1 = new Intent(getActivity(), VideoGridViewActivity.class);
        mList = (List<VideoEntity>) getArguments().getSerializable("mList");

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
        switch (v.getId()){
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
                intent1.putExtra("mList",(Serializable)mList);
                startActivity(intent1);
                break;
        }
    }
    private void playVideo(int index) {
        if (mList.size()>8){
            intent.putExtra("mList",(Serializable)mList);
            intent.putExtra("index",index);
            intent.putExtra("type",0);//本地视频传0
            startActivity(intent);
        }else {
            MyToast.showToast("请检查U盘设备是否插入");
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
