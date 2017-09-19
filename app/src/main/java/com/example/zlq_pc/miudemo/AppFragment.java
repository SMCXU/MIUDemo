package com.example.zlq_pc.miudemo;


import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.zlq_pc.miudemo.adapter.GridAdapter;
import com.example.zlq_pc.miudemo.weight_flash.BorderView;
import com.example.zlq_pc.miudemo.weight_flash.TvZorderRelativeLayout;
import com.example.zlq_pc.miudemo.weight_flash.bridge.EffectNoDrawBridge;
import com.example.zlq_pc.miudemo.weight_flash.view.GridViewTV;
import com.example.zlq_pc.miudemo.weight_flash.view.MainUpView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.mMainUpView)
    MainUpView mMainUpView;
    @BindView(R.id.gv_app)
    GridViewTV gvApp;


    private List<String> mList;
    private GridAdapter adapter;


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

    private void initView() {
        BorderView border = new BorderView(getContext());
        border.setBackgroundResource(R.drawable.border_drawable);
        border.attachTo(gvApp);
        mList = new ArrayList<>();
        adapter = new GridAdapter(getContext(), mList);
        gvApp.setAdapter(adapter);

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

//    //初始化view
//    private void initView() {
//        mList = new ArrayList<>();
//
//        // 建议使用 NoDraw.
//        mMainUpView.setEffectBridge(new EffectNoDrawBridge());
//        EffectNoDrawBridge bridget = (EffectNoDrawBridge) mMainUpView.getEffectBridge();
//        bridget.setTranDurAnimTime(200);
//        // 设置移动边框的图片.
////        mMainUpView.setUpRectResource(R.drawable.border_drawable);
//        // 移动方框缩小的距离.
//        mMainUpView.setDrawUpRectPadding(new Rect(10, 10, 10, -55));
//        adapter = new GridAdapter(getContext(), mList);
//        gvApp.setAdapter(adapter);
//
//        gvApp.setSelector(new ColorDrawable(Color.TRANSPARENT));
//        //
//        gvApp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                /**
//                 * 这里注意要加判断是否为NULL.
//                 * 因为在重新加载数据以后会出问题.
//                 */
//                if (view != null) {
//                    mMainUpView.setFocusView(view, mOldView, 1.17f);
//                }
//                mOldView = view;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//        gvApp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mFindhandler.removeCallbacksAndMessages(null);
//                mSavePos = position; // 保存原来的位置
//
//                mFindhandler.sendMessageDelayed(mFindhandler.obtainMessage(), 111);
//                Toast.makeText(getContext(), "GridView Item " + position + " pos:" + mSavePos, Toast.LENGTH_LONG).show();
//            }
//        });
////        mFirstHandler.sendMessageDelayed(mFirstHandler.obtainMessage(), 188);
//    }

//    // 延时请求初始位置的item.
//    Handler mFirstHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            gvApp.setDefualtSelect(0);
//        }
//    };

//    // 更新数据后还原焦点框.
//    Handler mFindhandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            if (mSavePos != -1) {
//                gvApp.requestFocusFromTouch();
//                gvApp.setSelection(mSavePos);
//            }
//        }
//    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
