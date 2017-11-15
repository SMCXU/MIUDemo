/*
Copyright 2016 The Open Source Project

Author: hailongqiu <356752238@qq.com>
Maintainer: hailongqiu <356752238@qq.com>
					  pengjunkun <junkun@mgtv.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.example.customrecycle.activitys.movie;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.customrecycle.R;
import com.example.customrecycle.activitys.HomeActivity;
import com.example.customrecycle.base.BaseActivity;
import com.example.customrecycle.base.BaseApp;
import com.example.customrecycle.bridge.EffectNoDrawBridge;
import com.example.customrecycle.frame.EventCustom;
import com.example.customrecycle.frame.utils.ActivityUtils;
import com.example.customrecycle.frame.utils.KEY;
import com.example.customrecycle.frame.utils.entity.VideoEntity;
import com.example.customrecycle.frame.weightt.ZBXAlertDialog;
import com.example.customrecycle.frame.weightt.ZBXAlertListener;
import com.example.customrecycle.view.GridViewTV;
import com.example.customrecycle.view.MainUpView;
import com.example.customrecycle.weight.appweight.MarqueeText;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * GridView Demo测试.
 */
public class VideoGridViewActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    private List<String> data;
    private MainUpView mainUpView1;
    private View mOldView;
    private GridViewTV gridView;
    private GridViewAdapter mAdapter;
    private List<VideoEntity> mList;
    private Intent intent;
    private ZBXAlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_grid_view);
        ButterKnife.bind(this);

        initView();
        initBridge();

    }

    private void initView() {
        gridView = (GridViewTV) findViewById(R.id.gridView);
        mainUpView1 = (MainUpView) findViewById(R.id.mainUpView1);
        String title = getIntent().getStringExtra("title");
        if (title != null&&!"".equals(title.trim())) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }
    }

    //    载入边框
    private void initBridge() {
        // 建议使用 NoDraw.
        mainUpView1.setEffectBridge(new EffectNoDrawBridge());
        EffectNoDrawBridge bridget = (EffectNoDrawBridge) mainUpView1.getEffectBridge();
        bridget.setTranDurAnimTime(200);
        // 设置移动边框的图片.
        mainUpView1.setUpRectResource(R.drawable.white_light_10);
        //设置dp和px相互转换
        float density = getResources().getDisplayMetrics().density;
        RectF receF = new RectF(getDimension(R.dimen.x10) * density, getDimension(R.dimen.x10) * density,
                getDimension(R.dimen.x10) * density, -getDimension(R.dimen.x50) * density);
        // 移动方框缩小的距离.
        mainUpView1.setDrawUpRectPadding(receF);
//        // 移动方框缩小的距离.
//        mainUpView1.setDrawUpRectPadding(new Rect(10, 10, 10, -55));
        // 加载数据.
        getData();
        intent = new Intent(this, IjkVideoActivity.class);
        //
        updateGridViewAdapter();
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        //
        gridView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * 这里注意要加判断是否为NULL.
                 * 因为在重新加载数据以后会出问题.
                 */
                if (view != null) {
                    mainUpView1.setFocusView(view, mOldView, 1.2f);
                }
                //跑马灯开始和停止
                if (mOldView != null) {
                    GridViewAdapter.ViewHolder holder = (GridViewAdapter.ViewHolder) mOldView.getTag();
                    holder.titleTv.stopScroll();
                }
                GridViewAdapter.ViewHolder holder = (GridViewAdapter.ViewHolder) view.getTag();
                holder.titleTv.startScroll();
                mOldView = view;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mFindhandler.removeCallbacksAndMessages(null);
                intent.putExtra("index", position);
                intent.putExtra("type", 0);//本地视频传0
                startActivity(intent);
            }
        });
    }


    public List<String> getData() {
        data = new ArrayList<String>();
        mList = HomeActivity.videoList;
        for (int i = 0; i < mList.size(); i++) {
            data.add(mList.get(i).getName());
        }
        return data;
    }

    private void updateGridViewAdapter() {
        mAdapter = new GridViewAdapter(this, data);
        gridView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public float getDimension(int id) {
        return getResources().getDimension(id);
    }


    @Subscribe
    public void onEventThread(EventCustom eventCustom) {
        if (KEY.FLAG_USB_IN.equals(eventCustom.getTag())) {
            dialog = new ZBXAlertDialog(this, new ZBXAlertListener() {
                @Override
                public void onDialogOk(Dialog dlg) {
                    startActivity(new Intent(BaseApp.getContext(), VideoGridViewActivity.class));
                    dialog.dismiss();
                }

                @Override
                public void onDialogCancel(Dialog dlg) {

                    dialog.dismiss();
                }
            }, "提示", "外部存储设备已连接");
            if (ActivityUtils.isForeground(VideoGridViewActivity.this, "com.example.customrecycle.activitys.movie.VideoGridViewActivity")) {
                dialog.show();
            }
        }
    }
    ///// Adapter 类 start start //////////

    class GridViewAdapter extends BaseAdapter {

        private List<String> mDatas;
        private final LayoutInflater mInflater;

        public GridViewAdapter(Context context, List<String> data) {
            mDatas = data;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_gridview, parent, false);
                convertView.setTag(new ViewHolder(convertView));
            }
            viewHolder = (ViewHolder) convertView.getTag();
            bindViewData(position, viewHolder);
            return convertView;
        }

        private void bindViewData(int position, ViewHolder viewHolder) {
            String title = mDatas.get(position);
            viewHolder.titleTv.setText(title);
        }

        class ViewHolder {
            View itemView;
            MarqueeText titleTv;

            public ViewHolder(View view) {
                this.itemView = view;
                this.titleTv = (MarqueeText) view.findViewById(R.id.mt_1);
            }
        }
    }

    ///// Adapter 类 end end //////////


}