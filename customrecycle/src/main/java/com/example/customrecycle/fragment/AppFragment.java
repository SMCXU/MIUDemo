package com.example.customrecycle.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.customrecycle.R;
import com.example.customrecycle.activitys.HomeActivity;
import com.example.customrecycle.activitys.movie.MovieDetailActivity;
import com.example.customrecycle.activitys.movie.VideoGridViewActivity;
import com.example.customrecycle.adapter.AppsAdapter;
import com.example.customrecycle.adapter.GeneralAdapter;
import com.example.customrecycle.base.BaseApp;
import com.example.customrecycle.base.BaseFragment;
import com.example.customrecycle.bridge.EffectNoDrawBridge;
import com.example.customrecycle.entity.MyAppInfo;
import com.example.customrecycle.frame.EventCustom;
import com.example.customrecycle.frame.utils.ApkTool;
import com.example.customrecycle.frame.utils.MyToast;
import com.example.customrecycle.leanback.GridLayoutManagerTV;
import com.example.customrecycle.leanback.LinearLayoutManagerTV;
import com.example.customrecycle.leanback.recycle.RecyclerViewTV;
import com.example.customrecycle.view.MainUpView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AppFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.mRecyclerView)
    RecyclerViewTV mRecyclerView;
    private List<MyAppInfo> myAppInfos;
    private AppsAdapter adapter;
    Handler handler = new Handler();
    View mOldView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
//        initRecyclerViewGridLayout(RecyclerView.VERTICAL);
        initRecyclerViewLinerLayout(RecyclerViewTV.HORIZONTAL);
    }

    private void initRecyclerViewGridLayout(int orientation) {
        GridLayoutManagerTV gridlayoutManager = new GridLayoutManagerTV(getContext(), 4); // 解决快速长按焦点丢失问题.
        gridlayoutManager.setOrientation(orientation);
        mRecyclerView.setLayoutManager(gridlayoutManager);
        mRecyclerView.setFocusable(false);
        mRecyclerView.setSelectedItemAtCentered(true); // 设置item在中间移动.
        myAppInfos = new ArrayList<>();
        adapter = new AppsAdapter(myAppInfos, getContext());
        mRecyclerView.setAdapter(adapter);
        final Context mContext =  BaseApp.getContext();
        mRecyclerView.setOnItemClickListener(new RecyclerViewTV.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerViewTV parent, View itemView, int position) {

                Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(
                        myAppInfos.get(position).getAppName().trim());
                if (intent!=null){
                    mContext.startActivity(intent);
                }
            }
        });
        mRecyclerView.setOnItemListener(new RecyclerViewTV.OnItemListener() {
            //未选中
            @Override
            public void onItemPreSelected(RecyclerViewTV parent, View itemView, int position) {

            }
            //选中
            @Override
            public void onItemSelected(RecyclerViewTV parent, View itemView, int position) {

            }

            @Override
            public void onReviseFocusFollow(RecyclerViewTV parent, View itemView, int position) {

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                myAppInfos = ApkTool.scanLocalInstallAppList(BaseApp.getContext().getPackageManager());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setData(myAppInfos);
                    }
                });

            }
        }).start();
    }

    /**
     * 测试LinerLayout.
     */
    private void initRecyclerViewLinerLayout(int orientation) {
        LinearLayoutManagerTV layoutManager = new LinearLayoutManagerTV(getContext());
        layoutManager.setOrientation(orientation);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setFocusable(false);
        myAppInfos = new ArrayList<>();
        adapter = new AppsAdapter(myAppInfos, getContext());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setSelectedItemAtCentered(true);
        mRecyclerView.setDefaultItem(0);
        final Context mContext =  BaseApp.getContext();

        mRecyclerView.setOnItemClickListener(new RecyclerViewTV.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerViewTV parent, View itemView, int position) {

                Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(
                        myAppInfos.get(position).getAppName().trim());
                if (intent!=null){
                    mContext.startActivity(intent);
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                myAppInfos = ApkTool.scanLocalInstallAppList(BaseApp.getContext().getPackageManager());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setData(myAppInfos);
                    }
                });

            }
        }).start();
    }

    @Override
    protected void initialize(View root, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
    }

    @Subscribe
    public void onEventThread(EventCustom eventCustom) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_app, container, false);
    }
}
