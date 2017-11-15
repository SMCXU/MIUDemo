package com.example.customrecycle.activitys.movie;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.telecom.VideoProfile;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
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
import com.example.customrecycle.view.ListViewTV;
import com.example.customrecycle.view.MainUpView;
import com.example.customrecycle.weight.appweight.MarqueeText;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.search_display_text)
    TextView searchDisplayText;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.textView_hotvideo_title)
    TextView textViewHotvideoTitle;
    @BindView(R.id.mListView)
    ListViewTV mListView;
    @BindView(R.id.mainUpView1)
    MainUpView mainUpView1;
    @BindView(R.id.rl_container)
    RelativeLayout rlContainer;
    @BindView(R.id.mGridView)
    GridViewTV mGridView;
    private List<VideoEntity> videoEntities;
    private LayoutInflater mInflater;
    private View mOldView,mOldView2;
    private ZBXAlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();
        initBridge();
    }

    private void initBridge() {
        // 默认是 OpenEff...，建议使用 NoDraw... ...
        mainUpView1.setEffectBridge(new EffectNoDrawBridge());
        EffectNoDrawBridge bridget = (EffectNoDrawBridge) mainUpView1.getEffectBridge();
        bridget.setTranDurAnimTime(200);
        //
        mainUpView1.setUpRectResource(R.drawable.white_light_10); // 设置移动边框的图片.
        mainUpView1.setDrawUpRectPadding(new Rect(25, 25, 23, 23)); // 边框图片设置间距.
        rlContainer.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(final View oldFocus, final View newFocus) {
                if (newFocus != null)
                    newFocus.bringToFront(); // 防止放大的view被压在下面. (建议使用MainLayout)
                float scale = 1.1f;
                mainUpView1.setFocusView(newFocus, mOldView, scale);
                mOldView = newFocus; // 4.3以下需要自己保存.
            }
        });
        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                view.bringToFront();
                mainUpView1.setUnFocusView(mOldView);
                mainUpView1.setFocusView(view, mOldView, 1.1f);
                mOldView = view;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mGridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                view.bringToFront();
                if (view != null) {
                    mainUpView1.setFocusView(view, mOldView, 1.1f);
                }
                //跑马灯开始和停止
                if (mOldView2!=null){
                    mainUpView1.setUnFocusView(mOldView2);
                    GridViewAdapter.ViewHolder holder = (GridViewAdapter.ViewHolder) mOldView2.getTag();
                    if (holder.titleTv!=null){
                        holder.titleTv.stopScroll();
                    }
                }
                GridViewAdapter.ViewHolder holder = (GridViewAdapter.ViewHolder) view.getTag();
                holder.titleTv.startScroll();
                mOldView2 = view;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void initView() {
        this.mInflater = LayoutInflater.from(getApplicationContext());
        videoEntities = new ArrayList<>();
        DemoAdapter adapter = new DemoAdapter();
        GridViewAdapter adapter1 = new GridViewAdapter(this, videoEntities);
        mListView.setAdapter(adapter);
        mGridView.setAdapter(adapter1);
        getData();
        adapter.notifyDataSetChanged();
        adapter1.notifyDataSetChanged();
        mListView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    if (videoEntities.size() > 0) {
                        mListView.setDefualtSelect(0);
                    }
                } else {
                    mainUpView1.setUnFocusView(view);
                }
            }
        });

    }

    private void getData() {
        if (HomeActivity.videoList.size() > 6) {
            videoEntities = HomeActivity.videoList.subList(0, 6);
        }
    }

    //输入按键的点击事件
    public void searchVideo(View view) {
        CharSequence oldText = searchDisplayText.getText();
        String newText = oldText.toString() + view.getTag();
        searchDisplayText.setText(newText);
        //本地搜索稍后添加
    }

    //删除按键的点击事件
    public void cleanVideo(View view) {
        switch (view.getId()) {
            case R.id.iv_del:
                String oldText = searchDisplayText.getText().toString();
                if (oldText.length() > 0) {
                    oldText = oldText.substring(0, oldText.length() - 1);
                }
                searchDisplayText.setText(oldText);
                break;
            case R.id.iv_clear:
                searchDisplayText.setText("");
                break;
        }
    }

    //======================listadapter start==================================
    public class DemoAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return videoEntities.size();
        }

        @Override
        public Object getItem(int position) {
            return videoEntities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_list_movie, null);
                holder.name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.type = (TextView) convertView.findViewById(R.id.tv_type);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.name.setText(videoEntities.get(position).getName());
            holder.type.setText("2017 内地");
            return convertView;
        }

        public class ViewHolder {
            public TextView name;
            public TextView type;
        }
    }
    //======================listadapter end====================================


    @Subscribe
    public void onEventThread(EventCustom eventCustom) {
        Log.d("Mr.U", "onEventThread: searchhhhhhhhhhhhhhhhh");
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
            if (ActivityUtils.isForeground(SearchActivity.this, "com.example.customrecycle.activitys.movie.SearchActivity")) {
                dialog.show();
            }
        }
    }

    ///// Adapter 类 start start //////////

    class GridViewAdapter extends BaseAdapter {

        private List<VideoEntity> mDatas;
        private final LayoutInflater mInflater;

        public GridViewAdapter(Context context, List<VideoEntity> data) {
            mDatas = data;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return videoEntities.size();
        }

        @Override
        public Object getItem(int position) {
            return videoEntities.get(position);
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
            if (mDatas.size()>position){
                String title = mDatas.get(position).getName();
                viewHolder.titleTv.setText(title);
            }

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
