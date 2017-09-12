package com.example.zlq_pc.miudemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zlq_pc.miudemo.weight_flash.BorderView;
import com.example.zlq_pc.miudemo.weight_flash.FocusBorder;
import com.example.zlq_pc.miudemo.weight_flash.MarqueeText;
import com.example.zlq_pc.miudemo.weight_flash.RoundedFrameLayout;
import com.example.zlq_pc.miudemo.weight_flash.TvZorderRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class RecommendFragment extends Fragment implements FocusBorder.OnFocusCallback, View.OnFocusChangeListener {

    @BindView(R.id.mt_1)
    MarqueeText mt1;
    @BindView(R.id.rf_1)
    RoundedFrameLayout rf1;
    @BindView(R.id.mt_2)
    MarqueeText mt2;
    @BindView(R.id.rf_2)
    RoundedFrameLayout rf2;
    @BindView(R.id.mt_3)
    MarqueeText mt3;
    @BindView(R.id.rf_3)
    RoundedFrameLayout rf3;
    @BindView(R.id.mt_4)
    MarqueeText mt4;
    @BindView(R.id.rf_4)
    RoundedFrameLayout rf4;
    @BindView(R.id.mt_5)
    MarqueeText mt5;
    @BindView(R.id.rf_5)
    RoundedFrameLayout rf5;
    @BindView(R.id.mt_6)
    MarqueeText mt6;
    @BindView(R.id.rf_6)
    RoundedFrameLayout rf6;
    @BindView(R.id.mt_7)
    MarqueeText mt7;
    @BindView(R.id.rf_7)
    RoundedFrameLayout rf7;
    @BindView(R.id.tv_relativelayout_list)
    TvZorderRelativeLayout tvRelativelayoutList;
    Unbinder unbinder;
    private FocusBorder mFocusBorder;

    //mt 是滚动字幕控件  rf 是流光特效控件
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recommend, container, false);
        unbinder = ButterKnife.bind(this, v);
        initView();
        return v;
    }
//初始化view
    private void initView() {
        // 实例话流光特效控件
        mFocusBorder = new FocusBorder.Builder().asColor()
                .shadowWidth(TypedValue.COMPLEX_UNIT_DIP, 18f)
                .borderColor(getResources().getColor(R.color.white))
                .build(getActivity());
        // 绑定流光特效回调
        mFocusBorder.boundGlobalFocusListener(this);
        BorderView border = new BorderView(getContext());
        border.setBackgroundResource(R.drawable.border_drawable);
        border.attachTo(tvRelativelayoutList);
        rf1.setOnFocusChangeListener(this);
        // 确保首页首项获取焦点
        rf1.post(new Runnable()
        {
            @Override
            public void run()
            {
                rf1.setFocusable(true);
                rf1.requestFocus();
            }
        });
        rf3.setOnFocusChangeListener(this);
        rf4.setOnFocusChangeListener(this);
        rf5.setOnFocusChangeListener(this);
        rf6.setOnFocusChangeListener(this);
        rf7.setOnFocusChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public FocusBorder.Options onFocus(View oldFocus, View newFocus) {
        if (newFocus != null && oldFocus != null)
        {
            switch (newFocus.getId())
            {
                case R.id.rf_1:
                    Log.d("Mr.U", "onFocus: rf1");
                    return FocusBorder.OptionsFactory.get(1.1f, 1.1f, 0);
                case R.id.rf_2:
                    Log.d("Mr.U", "onFocus: rf2");
                    return FocusBorder.OptionsFactory.get(1.1f, 1.1f, 0);
                case R.id.rf_3:
                    Log.d("Mr.U", "onFocus: rf3");
                    return FocusBorder.OptionsFactory.get(1.1f, 1.1f, 0);
                case R.id.rf_4:
                    Log.d("Mr.U", "onFocus: rf4");
                    return FocusBorder.OptionsFactory.get(1.1f, 1.1f, 0);
                case R.id.rf_5:
                    Log.d("Mr.U", "onFocus: rf5");
                    return FocusBorder.OptionsFactory.get(1.1f, 1.1f, 0);
                case R.id.rf_6:
                    Log.d("Mr.U", "onFocus: rf6");
                    return FocusBorder.OptionsFactory.get(1.1f, 1.1f, 0);
                case R.id.rf_7:
                    Log.d("Mr.U", "onFocus: rf7");
                    return FocusBorder.OptionsFactory.get(1.1f, 1.1f, 0);
                default:
                    break;
            }
            mFocusBorder.setVisible(false);
        }

        return null;
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        switch (view.getId()){
            case R.id.rf_1:
                scrollAnimation(hasFocus, mt1);
                break;
            case R.id.rf_2:
                scrollAnimation(hasFocus, mt2);
                break;
            case R.id.rf_3:
                scrollAnimation(hasFocus, mt3);
                break;
            case R.id.rf_4:
                scrollAnimation(hasFocus, mt4);
                break;
            case R.id.rf_5:
                scrollAnimation(hasFocus, mt5);
                break;
            case R.id.rf_6:
                scrollAnimation(hasFocus, mt6);
                break;
            case R.id.rf_7:
                scrollAnimation(hasFocus, mt7);
                break;
            default:
                break;
        }
    }
    // 滚动动画实例
    private void scrollAnimation(boolean hasFocus, MarqueeText view)
    {
        if (hasFocus)
        {
            view.startScroll();
        }
        else
        {
            view.stopScroll();
        }
    }
}
