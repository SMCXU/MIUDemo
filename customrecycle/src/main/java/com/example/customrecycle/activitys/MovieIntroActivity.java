package com.example.customrecycle.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customrecycle.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieIntroActivity extends Activity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_intro);
        ButterKnife.bind(this);

    }
}
