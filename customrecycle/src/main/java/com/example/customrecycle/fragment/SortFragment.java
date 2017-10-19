package com.example.customrecycle.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.customrecycle.R;
import com.example.customrecycle.frame.utils.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SortFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.tv_hotSearch)
    TextView tvHotSearch;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_4)
    TextView tv4;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.tv_5)
    TextView tv5;
    @BindView(R.id.tv_11)
    TextView tv11;
    @BindView(R.id.tv_22)
    TextView tv22;
    @BindView(R.id.tv_44)
    TextView tv44;
    @BindView(R.id.tv_33)
    TextView tv33;
    @BindView(R.id.tv_55)
    TextView tv55;
    @BindView(R.id.tv_movie)
    TextView tvMovie;
    @BindView(R.id.tv_a)
    TextView tvA;
    @BindView(R.id.tv_b)
    TextView tvB;
    @BindView(R.id.tv_c)
    TextView tvC;
    @BindView(R.id.tv_d)
    TextView tvD;
    @BindView(R.id.tv_e)
    TextView tvE;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sort, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv11.setOnClickListener(this);
        tv22.setOnClickListener(this);
        tv33.setOnClickListener(this);
        tv44.setOnClickListener(this);
        tv55.setOnClickListener(this);
        tvA.setOnClickListener(this);
        tvB.setOnClickListener(this);
        tvC.setOnClickListener(this);
        tvD.setOnClickListener(this);
        tvE.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        MyToast.showToast("跳转到分类列表");
    }
}
