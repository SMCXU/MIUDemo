package com.example.customrecycle;

import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.customrecycle.adapter.GeneralAdapter;
import com.example.customrecycle.bridge.RecyclerViewBridge;
import com.example.customrecycle.leanback.LinearLayoutManagerTV;
import com.example.customrecycle.leanback.recycle.RecyclerViewTV;
import com.example.customrecycle.view.MainUpView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewTV.OnItemListener {

    //    private ProgressBar mLoadMore_pb;
    private MainUpView mMainUpView;
    private RecyclerViewTV mRecyclerView;
    private RecyclerViewBridge mRecyclerViewBridge;
    private List<String> mList;
    private GeneralAdapter mAdapter;
    private View mOldView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    //添加数据
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
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setDefaultItem(1);

    }



    //初始化布局
    private void initView() {
//        mLoadMore_pb = (ProgressBar) findViewById(R.id.load_pb);
        mMainUpView = (MainUpView) findViewById(R.id.mMainUpView);
        mRecyclerView = (RecyclerViewTV) findViewById(R.id.mRecyclerView);
        mMainUpView.setEffectBridge(new RecyclerViewBridge());
        // 注意这里，需要使用 RecyclerViewBridge 的移动边框 Bridge.
        mRecyclerViewBridge = (RecyclerViewBridge) mMainUpView.getEffectBridge();
        //设置边框
        mRecyclerViewBridge.setUpRectResource(R.drawable.video_cover_cursor);
        //设置dp和px相互转换
        float density = getResources().getDisplayMetrics().density;
        RectF receF = new RectF(getDimension(R.dimen.x45) * density, getDimension(R.dimen.x40) * density,
                getDimension(R.dimen.x45) * density, getDimension(R.dimen.x40) * density);
        mRecyclerViewBridge.setDrawUpRectPadding(receF);

        testRecyclerViewLinerLayout(RecyclerView.VERTICAL);
        //
        mRecyclerView.setOnItemListener(this);
        // item 单击事件处理.
        mRecyclerView.setOnItemClickListener(new RecyclerViewTV.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerViewTV parent, View itemView, int position) {
                startActivity(new Intent(MainActivity.this,TestEffectActivity.class));
            }
        });
    }

    public float getDimension(int id) {
        return getResources().getDimension(id);
    }

    /**
     * 测试LinerLayout.
     */
    private void testRecyclerViewLinerLayout(int orientation) {
        LinearLayoutManagerTV layoutManager = new LinearLayoutManagerTV(this);
        layoutManager.setOrientation(orientation);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setFocusable(false);
        mList = new ArrayList<>();
        mAdapter = new GeneralAdapter(mList, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.requestFocusFromTouch();
//        mRecyclerView.setSelectedItemOffset(111, 111); // 测试移动间距.
//        mRecyclerView.setSelectedItemAtCentered(false);
        mRecyclerView.setPagingableListener(new RecyclerViewTV.PagingableListener() {
            @Override
            public void onLoadMoreItems() {

            }
        });
        initData();
    }


    @Override
    public void onItemPreSelected(RecyclerViewTV parent, View itemView, int position) {
        if (mOldView != null) {
            mRecyclerViewBridge.setUnFocusView(mOldView);
        }
    }

    @Override
    public void onItemSelected(RecyclerViewTV parent, View itemView, int position) {
        mRecyclerViewBridge.setFocusView(itemView, 1.2f);
        mOldView = itemView;
    }

    @Override
    public void onReviseFocusFollow(RecyclerViewTV parent, View itemView, int position) {
        mRecyclerViewBridge.setFocusView(itemView, mOldView, 1.2f);
        mOldView = itemView;
    }
}
