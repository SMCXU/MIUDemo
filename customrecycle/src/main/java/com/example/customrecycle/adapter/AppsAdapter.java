package com.example.customrecycle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customrecycle.R;
import com.example.customrecycle.activitys.HomeActivity;
import com.example.customrecycle.entity.MyAppInfo;

import java.util.List;
import java.util.Random;

/**
 * RecyclerView 通用 Adapter.
 * Created by hailongqiu on 2016/8/22.
 */
public class AppsAdapter extends RecyclerView.Adapter {
    List<MyAppInfo> mList;
    Context mContext;
    int prePosition = 0;
    View mOldView;
    private int[] drawables = {
            R.drawable.app_blue,
            R.drawable.app_green,
            R.drawable.app_jasper,
            R.drawable.app_lawngreen,
            R.drawable.app_red,
            R.drawable.app_yellow
    };

    public void setData(List<MyAppInfo> myAppInfos) {
        this.mList = myAppInfos;
        notifyDataSetChanged();
    }

    public AppsAdapter(List<MyAppInfo> mList, Context mContent) {
        this.mList = mList;
        this.mContext = mContent;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_app, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder mHolder = (MyViewHolder) holder;
        Log.d("Mr.U", "onBindViewHolder: PrePosition" + prePosition);
        if (mList.size() >= position) {
            MyAppInfo myAppInfo = mList.get(position);
            Random random = new Random();
            mHolder.tvAppName.setText(myAppInfo.getLabelName());
            mHolder.itemAppBg.setBackgroundResource(drawables[random.nextInt(6)]);
            mHolder.ivAppIcon.setImageDrawable(myAppInfo.getImage());

            prePosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvAppName;
        LinearLayout itemAppBg;
        ImageView ivAppIcon;

        public MyViewHolder(View view) {
            super(view);
            tvAppName = (TextView) view.findViewById(R.id.tv_app_name);
            itemAppBg = (LinearLayout) view.findViewById(R.id.item_app_bg);
            ivAppIcon = (ImageView) view.findViewById(R.id.iv_app_icon);

        }
    }
}
