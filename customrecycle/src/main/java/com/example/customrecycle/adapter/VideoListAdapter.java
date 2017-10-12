package com.example.customrecycle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.customrecycle.R;
import com.example.customrecycle.frame.utils.entity.VideoEntity;
import com.example.customrecycle.weight.appweight.MarqueeText;

import java.util.List;

/**
 * RecyclerView 通用 Adapter.
 * Created by hailongqiu on 2016/8/22.
 */
public class VideoListAdapter extends RecyclerView.Adapter {
    List<VideoEntity> mList;
    Context mContext;
    int prePosition = 0;

    public VideoListAdapter(List<VideoEntity> mList, Context mContent) {
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
        Log.d("Mr.U", "onBindViewHolder: PrePosition" + prePosition);
        if (mList.size() >= position) {
//            Glide.with(mContext).load(mList.get(position).
//                    getUri()).
//                    into(mHolder.ivVideo);
//            mHolder.mt.setText(mList.get(position).getName());
            mHolder.tv.setText(mList.get(position).getName());
            prePosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivVideo;
        MarqueeText mt;
        TextView tv;

        public MyViewHolder(View view) {
            super(view);
//            ivVideo = (ImageView) view.findViewById(R.id.iv_video);
//            mt = (MarqueeText) view.findViewById(R.id.mt_1);
            tv = (TextView) view.findViewById(R.id.textView);
        }
    }
}
