package com.example.customrecycle.activitys.movie;

import android.app.Dialog;
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
import com.example.customrecycle.activitys.HomeActivity;
import com.example.customrecycle.base.BaseActivity;
import com.example.customrecycle.base.BaseApp;
import com.example.customrecycle.bridge.EffectNoDrawBridge;
import com.example.customrecycle.frame.EventCustom;
import com.example.customrecycle.frame.utils.ActivityUtils;
import com.example.customrecycle.frame.utils.KEY;
import com.example.customrecycle.frame.weightt.ImageDialog;
import com.example.customrecycle.frame.weightt.ZBXAlertDialog;
import com.example.customrecycle.frame.weightt.ZBXAlertListener;
import com.example.customrecycle.view.MainUpView;
import com.example.customrecycle.weight.appweight.TvZorderRelativeLayout;

import org.greenrobot.eventbus.Subscribe;

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
    @BindView(R.id.tv_remain)
    TextView tvRemain;
    private EffectNoDrawBridge mEffectNoDrawBridge;
    private View mOldFocus;
    private Intent intent, intent1;
    private ZBXAlertDialog dialog;

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
        intent = new Intent(this, MovieIntroActivity.class);
        intent1 = new Intent(this, IjkVideoActivity.class);
        tvPlay.requestFocus();
        tvPlay.setOnClickListener(this);
        tvPilot.setOnClickListener(this);
        tvFee.setOnClickListener(this);
        tvDec.setOnClickListener(this);
        int index = getIntent().getIntExtra("index", 0);
        int type = getIntent().getIntExtra("type", 0);
        if (index <= HomeActivity.videoList.size()) {
            tvName.setText(HomeActivity.videoList.get(index).getName());
        }
        intent1.putExtra("index", index);
        intent1.putExtra("type", type);

        intent.putExtra("index", index);
        intent.putExtra("type", type);
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
        switch (view.getId()) {
            case R.id.tv_play:
            case R.id.tv_pilot:
                startActivity(intent1);
                break;
            case R.id.tv_fee:
                new ImageDialog(MovieDetailActivity.this).show();
                break;
            case R.id.tv_dec:
                startActivity(intent);
                break;
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
            if (ActivityUtils.isForeground(MovieDetailActivity.this, "com.example.customrecycle.activitys.movie.MovieDetailActivity")) {
                dialog.show();
            }
        }else if (KEY.FLAG_TIMING_START.equals(eventCustom.getTag())) {
            //计费倒计时开始
            if (tvRemain.getVisibility()==View.GONE){
                tvRemain.setVisibility(View.VISIBLE);
            }
            tvRemain.setText(getResources().getString(R.string.Locked_Propt)+ (CharSequence)eventCustom.getObj());
        } else if (KEY.FLAG_TIMING_END.equals(eventCustom.getTag())) {
            //计费倒计时结束
            tvRemain.setVisibility(View.GONE);
        }
    }
}
