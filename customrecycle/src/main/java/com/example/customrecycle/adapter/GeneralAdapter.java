package com.example.customrecycle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.customrecycle.R;

import java.util.List;

/**
 * RecyclerView 通用 Adapter.
 * Created by hailongqiu on 2016/8/22.
 */
public class GeneralAdapter extends RecyclerView.Adapter {
    List<String> mList;
    Context mContext;
    int prePosition = 0;

    public GeneralAdapter(List<String> mList, Context mContent) {
        this.mList = mList;
        this.mContext = mContent;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder mHolder = (MyViewHolder) holder;
        Log.d("Mr.U", "onBindViewHolder: PrePosition"+prePosition);
        if (mList.size() >= position) {
            mHolder.tv.setText(mList.get(position));
            prePosition=position;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.textView);
        }
    }
}
