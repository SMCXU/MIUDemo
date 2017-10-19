package com.example.customrecycle.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.customrecycle.R;
import com.example.customrecycle.frame.utils.MyToast;
import com.example.customrecycle.frame.utils.entity.VideoEntity;
import com.example.customrecycle.weight.appweight.MarqueeText;
import com.example.customrecycle.weight.appweight.RoundedFrameLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_hotSearch)
    TextView tvHotSearch;
    @BindView(R.id.tv_hotSelect)
    TextView tvHotSelect;
    Unbinder unbinder;
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
    @BindView(R.id.mt_11)
    MarqueeText mt11;
    @BindView(R.id.rf_11)
    RoundedFrameLayout rf11;
    @BindView(R.id.mt_12)
    MarqueeText mt12;
    @BindView(R.id.rf_12)
    RoundedFrameLayout rf12;
    @BindView(R.id.mt_13)
    MarqueeText mt13;
    @BindView(R.id.rf_13)
    RoundedFrameLayout rf13;
    @BindView(R.id.mt_14)
    MarqueeText mt14;
    @BindView(R.id.rf_14)
    RoundedFrameLayout rf14;
    @BindView(R.id.mt_15)
    MarqueeText mt15;
    @BindView(R.id.rf_15)
    RoundedFrameLayout rf15;
    @BindView(R.id.mt_16)
    MarqueeText mt16;
    @BindView(R.id.rf_16)
    RoundedFrameLayout rf16;
    private List<VideoEntity> mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mList = (List<VideoEntity>) getArguments().getSerializable("mList");
        if (mList.size() > 5) {
            mList = mList.subList(0, 5);
        }
        rf1.setOnClickListener(this);
        rf2.setOnClickListener(this);
        rf3.setOnClickListener(this);
        rf4.setOnClickListener(this);
        rf5.setOnClickListener(this);
        rf6.setOnClickListener(this);
        rf11.setOnClickListener(this);
        rf12.setOnClickListener(this);
        rf13.setOnClickListener(this);
        rf14.setOnClickListener(this);
        rf15.setOnClickListener(this);
        rf16.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onClick(View view) {
        MyToast.showToast("点击跳转到详情界面");
    }
}
