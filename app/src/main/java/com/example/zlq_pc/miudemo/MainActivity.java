package com.example.zlq_pc.miudemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;

import com.example.zlq_pc.miudemo.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnFocusChangeListener{

    @BindView(R.id.rb_search)
    RadioButton rbSearch;
    @BindView(R.id.rb_recommend)
    RadioButton rbRecommend;
    @BindView(R.id.rb_app)
    RadioButton rbApp;
    @BindView(R.id.vp_contener)
    ViewPager vpContener;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initFragment();
    }

    //初始化并设置view
    private void initView() {
        rbSearch.setOnFocusChangeListener(this);
        rbRecommend.setOnFocusChangeListener(this);
        rbApp.setOnFocusChangeListener(this);
    }

    //初始化Fragment
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new SearchFragment());
        fragments.add(new RecommendFragment());
        fragments.add(new AppFragment());
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        //页面变化,切换title
        vpContener.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setTitleTextColor(rbSearch);
                        rbSearch.setFocusable(true);
                        rbSearch.requestFocus();
                        break;
                    case 1:
                        setTitleTextColor(rbRecommend);
                        rbRecommend.setFocusable(true);
                        rbRecommend.requestFocus();
                        break;
                    case 2:
                        setTitleTextColor(rbApp);
                        rbApp.setFocusable(true);
                        rbApp.requestFocus();
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpContener.setAdapter(adapter);
//        //精品页为默认页
        rbRecommend.setFocusable(true);
        rbRecommend.requestFocus();

    }

    //设置选中时title的颜色
    private void setTitleTextColor(RadioButton rb) {
        List<RadioButton> mList = new ArrayList<>();
        mList.add(rbSearch);
        mList.add(rbRecommend);
        mList.add(rbApp);
        mList.remove(rb);
        rb.setTextColor(getResources().getColor(R.color.yellow));
        mList.get(0).setTextColor(getResources().getColor(R.color.white));
        mList.get(1).setTextColor(getResources().getColor(R.color.white));
    }

    //焦点变化,切换页面
    @Override
    public void onFocusChange(View view, boolean b) {
        if (b){
            switch (view.getId()) {
                case R.id.rb_search:
                    //当activity和fragment销毁时不执行跳转操作,当设置MainActivity不可退出时可隐藏该操作
                    if (getSupportFragmentManager().isDestroyed())
                        break;
                    Log.d("Mr.U", "onFocusChange: rb_search"+b);
                    setTitleTextColor(rbSearch);
                    vpContener.setCurrentItem(0, false);
                    break;
                case R.id.rb_recommend:
                    Log.d("Mr.U", "onFocusChange: rb_recommend"+b);
                    setTitleTextColor(rbRecommend);
                    vpContener.setCurrentItem(1, false);
                    break;
                case R.id.rb_app:
                    Log.d("Mr.U", "onFocusChange: rb_app"+b);
                    setTitleTextColor(rbApp);
                    vpContener.setCurrentItem(2, false);
                    break;
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
