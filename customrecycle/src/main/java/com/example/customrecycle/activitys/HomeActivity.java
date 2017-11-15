package com.example.customrecycle.activitys;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.storage.StorageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.customrecycle.R;
import com.example.customrecycle.activitys.movie.VideoGridViewActivity;
import com.example.customrecycle.adapter.MyFragmentPagerAdapter;
import com.example.customrecycle.adapter.OpenTabTitleAdapter;
import com.example.customrecycle.base.BaseActivity;
import com.example.customrecycle.base.BaseApp;
import com.example.customrecycle.base.DaoTools;
import com.example.customrecycle.bridge.EffectNoDrawBridge;
import com.example.customrecycle.bridge.OpenEffectBridge;
import com.example.customrecycle.fragment.CartoonFragment;
import com.example.customrecycle.fragment.MovieFragment;
import com.example.customrecycle.fragment.RecommendpFragment;
import com.example.customrecycle.fragment.SearchFragment;
import com.example.customrecycle.fragment.SortFragment;
import com.example.customrecycle.fragment.TVFragment;
import com.example.customrecycle.fragment.VIPFragment;
import com.example.customrecycle.frame.EventCustom;
import com.example.customrecycle.frame.utils.ActivityUtils;
import com.example.customrecycle.frame.utils.FileUtils;
import com.example.customrecycle.frame.utils.KEY;
import com.example.customrecycle.frame.utils.entity.VideoEntity;
import com.example.customrecycle.frame.weightt.ZBXAlertDialog;
import com.example.customrecycle.frame.weightt.ZBXAlertListener;
import com.example.customrecycle.view.MainUpView;
import com.example.customrecycle.view.OpenTabHost;
import com.example.customrecycle.view.TextViewWithTTF;
import com.example.customrecycle.weight.appweight.MarqueeText;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * ViewPager demo：
 * 注意标题栏和viewpager的焦点控制.(在XML布局中控制了, ids)
 * 移动边框的问题也需要注意.
 *
 * @author hailongqiu
 */
public class HomeActivity extends BaseActivity implements OpenTabHost.OnTabSelectListener {

    public static List<VideoEntity> videoList;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private List<Fragment> fragmentList;//
    ViewPager viewpager;
    OpenTabHost mOpenTabHost;
    OpenTabTitleAdapter mOpenTabTitleAdapter;
    // 移动边框.
    MainUpView mainUpView1;
    EffectNoDrawBridge mEffectNoDrawBridge;
    View mNewFocus;
    View mOldView;
    private ZBXAlertDialog dialog;

    //在主线程里面处理消息并更新UI界面
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    long sysTime = System.currentTimeMillis();//获取系统时间
                    CharSequence sysTimeStr = DateFormat.format("HH:mm", sysTime);//时间显示格式
                    tvTime.setText(sysTimeStr); //更新时间
                    break;
                default:
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        // 初始化标题栏.
        initAllTitleBar();
        videoList = new ArrayList<VideoEntity>();
        //从usb获取数据
        initUSB();
        initAllViewPager();
        // 初始化移动边框.
        initMoveBridge();
        new TimeThread().start(); //启动新的线程
    }

    private void initUSB() {
        // 扫描功能 获取U盘视频列表
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请内存读取权限
            ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS}, 3);
        } else {
            ScanFile(this);
        }
    }

    //扫描文件
    private void ScanFile(Context context) {
        String[] result = null;
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Method method = StorageManager.class.getMethod("getVolumePaths");
            method.setAccessible(true);
            try {
                result = (String[]) method.invoke(storageManager);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            DaoTools.deleteAll();
            for (int i = 0; i < result.length; i++) {
                FileUtils.getAllFiles(new File(result[i]));
            }
//            HomeActivity.videoList = DaoTools.queryAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    //扫描获取U盘内数据
//    private void scanMediaFile(Context context) {
//        String[] args = {".mp4", ".wmv", ".rmvb", ".mkv", ".avi", ".flv", ".3gp", ".mov", ".mpg", ".webm", ".wob"};
//        HomeActivity.videoList.clear();
//        HomeActivity.videoList = FileUtils.getSpecificTypeOfFile(context, args);
//
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initMoveBridge() {
        float density = getResources().getDisplayMetrics().density;
        mainUpView1 = (MainUpView) findViewById(R.id.mainUpView1);
        mEffectNoDrawBridge = new EffectNoDrawBridge();
        mainUpView1.setEffectBridge(mEffectNoDrawBridge);
        mEffectNoDrawBridge.setUpRectResource(R.drawable.white_light_10); // 设置移动边框图片.
        RectF rectF = new RectF(getDimension(R.dimen.x10) * density, getDimension(R.dimen.x10) * density,
                getDimension(R.dimen.x10) * density, getDimension(R.dimen.x10) * density);
        mEffectNoDrawBridge.setDrawUpRectPadding(rectF);
    }

    private void initAllTitleBar() {
        mOpenTabHost = (OpenTabHost) findViewById(R.id.openTabHost);
        mOpenTabTitleAdapter = new OpenTabTitleAdapter();
        mOpenTabHost.setOnTabSelectListener(this);
        mOpenTabHost.setAdapter(mOpenTabTitleAdapter);
    }

    private void initAllViewPager() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        addFragmentLists();
        viewpager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        // 全局焦点监听. (这里只是demo，为了方便这样写，你可以不这样写)
        viewpager.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                if (newFocus != null)
//                    newFocus.bringToFront(); // 防止放大的view被压在下面. (建议使用MainLayout),如果使用的话tab会错乱
                    // 判断 : 避免焦点框跑到标题栏. (只是demo，你自己处理逻辑)
                    // 你也可以让标题栏放大，有移动边框.
                    if (newFocus != null && !(newFocus instanceof TextViewWithTTF)) {
                        mEffectNoDrawBridge.setVisibleWidget(false);
                        mNewFocus = newFocus;
                        mOldView = oldFocus;
                        mainUpView1.setFocusView(newFocus, oldFocus, 1.2f);
                        // 让被挡住的焦点控件在前面.
                        newFocus.bringToFront();
                    } else { // 标题栏处理.
                        mNewFocus = null;
                        mOldView = null;
                        mainUpView1.setUnFocusView(oldFocus);
                        mEffectNoDrawBridge.setVisibleWidget(true);
                    }


            }
        });
        viewpager.setOffscreenPageLimit(1);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switchTab(mOpenTabHost, position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // viewPager 正在滚动中.
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE: // viewpager 滚动结束.
                        mainUpView1.setFocusView(mNewFocus, mOldView, 1.2f);
                        // 监听动画事件.
                        mEffectNoDrawBridge.setOnAnimatorListener(new OpenEffectBridge.NewAnimatorListener() {
                            @Override
                            public void onAnimationStart(OpenEffectBridge bridge, View view, Animator animation) {
                            }

                            @Override
                            public void onAnimationEnd(OpenEffectBridge bridge, View view, Animator animation) {
                                // 动画结束的时候恢复原来的时间. (这里只是DEMO)
                                mEffectNoDrawBridge.setTranDurAnimTime(OpenEffectBridge.DEFAULT_TRAN_DUR_ANIM);
                            }
                        });
                        // 让被挡住的焦点控件在前面.
                        if (mNewFocus != null)
                            mNewFocus.bringToFront();
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING: // viewPager开始滚动.
                        mEffectNoDrawBridge.clearAnimator(); // 清除之前的动画.
                        mEffectNoDrawBridge.setTranDurAnimTime(0); // 避免边框从其它地方跑出来.
                        break;
                }
            }
        });
        // 初始化.
        viewpager.setCurrentItem(0);
        switchTab(mOpenTabHost, 0);
    }

    private void addFragmentLists() {
        SearchFragment searchFragment = new SearchFragment();
        SortFragment sortFragment = new SortFragment();
        MovieFragment movieFragment = new MovieFragment();
        VIPFragment vipFragment = new VIPFragment();
        RecommendpFragment recomFragment = new RecommendpFragment();
        TVFragment tvFragment = new TVFragment();
        CartoonFragment cartoonFragment = new CartoonFragment();
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(searchFragment);
        fragmentList.add(sortFragment);
        fragmentList.add(recomFragment);
        fragmentList.add(vipFragment);
        fragmentList.add(movieFragment);
        fragmentList.add(tvFragment);
//        fragmentList.add(cartoonFragment);
    }

    @Override
    public void onTabSelect(OpenTabHost openTabHost, View titleWidget, int position) {
        if (viewpager != null) {
            viewpager.setCurrentItem(position);
        }
    }

    /**
     * demo (翻页的时候改变状态)
     * 将标题栏的文字颜色改变. <br>
     * 你可以写自己的东西，我这里只是DEMO.
     */
    public void switchTab(OpenTabHost openTabHost, int postion) {
        List<View> viewList = openTabHost.getAllTitleView();
        for (int i = 0; i < viewList.size(); i++) {
            TextViewWithTTF view = (TextViewWithTTF) openTabHost.getTitleViewIndexAt(i);
            if (view != null) {
                Resources res = view.getResources();
                if (res != null) {
                    if (i == postion) {
                        view.setTextColor(res.getColor(android.R.color.white));
                        view.setTypeface(null, Typeface.BOLD);
                        view.setSelected(true); // 为了显示 失去焦点，选中为 true的图片.
                    } else {
                        view.setTextColor(res.getColor(R.color.white_50));
                        view.setTypeface(null, Typeface.NORMAL);
                        view.setSelected(false);
                    }
                }
            }
        }
    }

    private float getDimension(int id) {
        return getResources().getDimension(id);
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
            if (ActivityUtils.isForeground(HomeActivity.this, "com.example.customrecycle.activitys.HomeActivity")) {
                dialog.show();
            }
        } else if (KEY.FLAG_USB_SCAN.equals(eventCustom.getTag())) {
            initUSB();
        }
    }

    //时间线程
    class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;  //消息(一个整型值)
                    mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }

}


