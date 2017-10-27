package com.example.customrecycle.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.customrecycle.R;
import com.example.customrecycle.base.BaseActivity;
import com.example.customrecycle.bridge.EffectNoDrawBridge;
import com.example.customrecycle.view.MainUpView;
import com.example.customrecycle.weight.appweight.TvZorderRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_name)
    ImageView ivName;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_length)
    TextView tvLength;
    @BindView(R.id.ll_detail)
    LinearLayout llDetail;
    @BindView(R.id.tv_dec)
    TextView tvDec;
    @BindView(R.id.tv_play)
    TextView tvPlay;
    @BindView(R.id.tv_pilot)
    TextView tvPilot;
    @BindView(R.id.tv_fee)
    TextView tvFee;
    @BindView(R.id.tv_relativelayout_list)
    TvZorderRelativeLayout tvRelativelayoutList;
    MainUpView mainUpView1;
    @BindView(R.id.rl_container)
    RelativeLayout rlContainer;
    private EffectNoDrawBridge mEffectNoDrawBridge;
    private View mOldFocus;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        initView();
        //初始化移动边框
        initMoveBridge();
    }

    private void initView() {
        intent = new Intent(this,MovieIntroActivity.class);
        tvPlay.requestFocus();
        tvPlay.setOnClickListener(this);
        tvPilot.setOnClickListener(this);
        tvFee.setOnClickListener(this);
        tvDec.setOnClickListener(this);
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
        rlContainer.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(final View oldFocus, final View newFocus) {
                if (newFocus != null)
                    newFocus.bringToFront(); // 防止放大的view被压在下面. (建议使用MainLayout)
                float scale = 1.2f;
                mainUpView1.setFocusView(newFocus, mOldFocus, scale);
                mOldFocus = newFocus; // 4.3以下需要自己保存.
            }
        });

    }

    private float getDimension(int id) {
        return getResources().getDimension(id);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_play:
                break;
            case R.id.tv_pilot:
                break;
            case R.id.tv_fee:
                break;
            case R.id.tv_dec:
                startActivity(intent);
                break;
        }
    }
}
