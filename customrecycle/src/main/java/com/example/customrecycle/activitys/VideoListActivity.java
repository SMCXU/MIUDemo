package com.example.customrecycle.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.customrecycle.R;
import com.example.customrecycle.adapter.GeneralAdapter;
import com.example.customrecycle.base.BaseActivity;
import com.example.customrecycle.bridge.RecyclerViewBridge;
import com.example.customrecycle.frame.utils.FileUtils;
import com.example.customrecycle.frame.utils.entity.VideoEntity;
import com.example.customrecycle.leanback.GridLayoutManagerTV;
import com.example.customrecycle.leanback.recycle.RecyclerViewTV;
import com.example.customrecycle.view.MainUpView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListActivity extends BaseActivity implements RecyclerViewTV.OnItemListener {

    @BindView(R.id.mRecyclerView)
    RecyclerViewTV mRecyclerView;
    @BindView(R.id.mMainUpView)
    MainUpView mMainUpView;
    private RecyclerViewBridge mRecyclerViewBridge;
    private GeneralAdapter mAdapter;
    private List<VideoEntity> mList;
    private List<String> nameList;

    private View mOldView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        ButterKnife.bind(this);
        initView();
    }

    //扫描获取U盘内数据
    private void scanMediaFile() {
        String[] args = {"mp4", "wmv", "rmvb", "mkv", "avi", "flv"};
        mList.clear();
        nameList.clear();
        mList = FileUtils.getSpecificTypeOfFile(this, args);
        for (int i = 0; i < mList.size(); i++) {
            nameList.add(mList.get(i).getName());
        }
        mAdapter.notifyDataSetChanged();
        GeneralAdapter.MyViewHolder holder = (GeneralAdapter.MyViewHolder) mRecyclerView.findViewHolderForAdapterPosition(mAdapter.getItemCount() / 2);
        if (holder != null) {
            holder.itemView.requestFocus();
        }
    }
    private void initView() {
        intent = new Intent(this,IjkVideoActivity.class);
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

//        横竖方向的grid
        testRecyclerViewGridLayout(RecyclerView.VERTICAL);

        mRecyclerView.setOnItemListener(this);
        // item 单击事件处理.
        mRecyclerView.setOnItemClickListener(new RecyclerViewTV.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerViewTV parent, View itemView, int position) {
                intent.putExtra("mList",(Serializable)mList);
                intent.putExtra("index",position);
                intent.putExtra("type",0);//本地视频传0
                startActivity(intent);
            }
        });
    }
    private void initData() {
        // 扫描功能 获取U盘视频列表
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请CAMERA权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
        } else {
            scanMediaFile();
        }
    }
    public float getDimension(int id) {
        return getResources().getDimension(id);
    }

    /**
     * 测试GridLayout.
     */
    private void testRecyclerViewGridLayout(int orientation) {
        GridLayoutManagerTV gridlayoutManager = new GridLayoutManagerTV(this,6); // 解决快速长按焦点丢失问题.
        gridlayoutManager.setOrientation(orientation);
        mRecyclerView.setLayoutManager(gridlayoutManager);
        mRecyclerView.setFocusable(false);
        mRecyclerView.setSelectedItemAtCentered(true); // 设置item在中间移动.
        mList = new ArrayList<>();
        nameList = new ArrayList<>();
        mAdapter = new GeneralAdapter(nameList, this);
        mRecyclerView.setAdapter(mAdapter);
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
