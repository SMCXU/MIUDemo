package com.example.customrecycle.activitys.movie;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.example.customrecycle.frame.utils.MyToast;
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
    @BindView(R.id.rl_left)
    RelativeLayout rlLeft;
    @BindView(R.id.button_r)
    Button buttonR;
    private List<VideoEntity> videoEntities;
    private LayoutInflater mInflater;
    private View mOldView;
    private ZBXAlertDialog dialog;
    private List<VideoEntity> nameList;
    private DemoAdapter adapter;
    private GridViewAdapter adapter1;
    private Intent intent;


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
        mainUpView1.setDrawUpRectPadding(new Rect(15, 15, 15, 15)); // 边框图片设置间距.
        mGridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        /**
         * view.getvisibility的值
         * （1）0    --------   VISIBLE    可见
         * （1）4    --------   INVISIBLE    不可见但是占用布局空间
         * （1）8    --------   GONE    不可见也不占用布局空间
         * */
        rlContainer.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldView, View newView) {
                if (mListView.getFocusedChild() == newView) {
                    rlLeft.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);
                    textViewHotvideoTitle.setVisibility(View.VISIBLE);
                } else if (mGridView == newView) {
                    mainUpView1.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                    textViewHotvideoTitle.setVisibility(View.GONE);
                    rlLeft.setVisibility(View.GONE);
                }
            }
        });
    }


    private void initView() {
        intent = new Intent(this, MovieDetailActivity.class);
        nameList = new ArrayList<>();
        this.mInflater = LayoutInflater.from(getApplicationContext());
        videoEntities = new ArrayList<>();
        adapter = new DemoAdapter();
        adapter1 = new GridViewAdapter(this, nameList);
        mListView.setAdapter(adapter);
        mGridView.setAdapter(adapter1);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                intent.putExtra("type", 2);
                intent.putExtra("index", position);
                startActivity(intent);
                MyToast.showToast(position+"--------");
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                intent.putExtra("type", 2);
                intent.putExtra("index", position);
                startActivity(intent);
            }
        });
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
        videoEntities = HomeActivity.videoList;
    }

    //输入按键的点击事件
    public void searchVideo(View view) {
        CharSequence oldText = searchDisplayText.getText();
        String newText = oldText.toString() + view.getTag();
        searchDisplayText.setText(newText);
        upDateUI(newText);
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
                upDateUI(oldText);
                break;
            case R.id.iv_clear:
                searchDisplayText.setText("");
                upDateUI("");
                break;
        }
    }

    //更改输入，刷新UI
    private void upDateUI(String newText) {
        nameList.clear();
        //本地搜索====>以后要替换成网络搜索
        for (int i = 0; i < videoEntities.size(); i++) {
            if (videoEntities.get(i).getName().contains(newText.trim().toLowerCase())) {
                nameList.add(videoEntities.get(i));
                Log.d("Mr.U", "searchVideo: " + videoEntities.get(i).getName());
            }
        }
        adapter.notifyDataSetChanged();
        adapter1.notifyDataSetChanged();
    }


    //======================listadapter start==================================
    public class DemoAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return nameList.size();
        }

        @Override
        public Object getItem(int position) {
            return nameList.get(position);
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
            holder.name.setText(nameList.get(position).getName());
//            holder.type.setText("2017 内地");
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
            if (mDatas.size() > position) {
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


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        final boolean uniqueDown = event.getRepeatCount() == 0 && event.getAction() == KeyEvent.ACTION_DOWN;
        //rlLIFT和mListView不可见说明这次点击事件在gridview上，再次点击左键时显示mlistview即可
        if (uniqueDown && keyCode == KeyEvent.KEYCODE_DPAD_LEFT && rlLeft.getVisibility() == View.GONE
                && mListView.getVisibility() == View.GONE) {
            mListView.setVisibility(View.VISIBLE);
            textViewHotvideoTitle.setVisibility(View.VISIBLE);
            mListView.getChildAt(0).requestFocus();
            mainUpView1.setUnFocusView(mOldView);
            mainUpView1.setVisibility(View.GONE);
            Log.d("Mr.U", "dispatchKeyEvent: 1111111111111");
            return true;
        } else if (uniqueDown && keyCode == KeyEvent.KEYCODE_DPAD_LEFT && rlLeft.getVisibility() == View.GONE
                && mListView.getVisibility() == View.VISIBLE) {
            rlLeft.setVisibility(View.VISIBLE);
            buttonR.requestFocus();
            mainUpView1.setUnFocusView(mOldView);
            mainUpView1.setVisibility(View.GONE);
            Log.d("Mr.U", "dispatchKeyEvent: 22222222222222");
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
