package com.example.zlq_pc.miudemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.zlq_pc.miudemo.adapter.GridAdapter;
import com.example.zlq_pc.miudemo.weight_flash.FocusBorder;
import com.example.zlq_pc.miudemo.weight_flash.weight_recycle.CustomRecyclerView;
import com.example.zlq_pc.miudemo.weight_flash.weight_recycle.HomeTvAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppFragment extends Fragment {

    @BindView(R.id.gv_app)
    CustomRecyclerView gvApp;
    Unbinder unbinder;
    private List<String> mList;
    private HomeTvAdapter adapter;
    private FocusBorder mFocusBorder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    //获取数据
    private void initData() {
        mList.add("应用商店");
        mList.add("游戏中心");
        mList.add("智能家庭TV");
        mList.add("高清播放器");
        mList.add("辰星盒子设置");
        mList.add("天气");
        mList.add("晨星商城");
        mList.add("电视相册");
        mList.add("电视管家");
        mList.add("日历");
        mList.add("时尚画报");
        mList.add("网络电台");
        mList.add("无线投屏");
        mList.add("通知中心");
        adapter.notifyDataSetChanged();
    }

    //初始化view
    private void initView() {
        // 实例话流光特效控件
        mFocusBorder = new FocusBorder.Builder().asColor()
                .shadowWidth(TypedValue.COMPLEX_UNIT_DIP, 18f)
                .borderColor(getResources().getColor(R.color.white))
                .build(getActivity());
        mList = new ArrayList<>();
        adapter = new HomeTvAdapter(getContext(), mList);
        gvApp.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
