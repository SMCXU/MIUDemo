package com.example.zlq_pc.miudemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zlq_pc.miudemo.weight_flash.BorderView;
import com.example.zlq_pc.miudemo.weight_flash.RoundedFrameLayout;
import com.example.zlq_pc.miudemo.weight_flash.TvZorderLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    @BindView(R.id.rf2_2)
    RoundedFrameLayout rf2;
    @BindView(R.id.rf3_3)
    RoundedFrameLayout rf3;
    @BindView(R.id.rf1_1)
    RoundedFrameLayout rf1;
    Unbinder unbinder;
    @BindView(R.id.tv_linear)
    TvZorderLinearLayout tvLinear;

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
        BorderView border = new BorderView(getContext());
        border.setBackgroundResource(R.drawable.border_drawable);
        border.attachTo(tvLinear);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
