package com.example.customrecycle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customrecycle.R;
import com.example.customrecycle.entity.MyAppInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZLQ-PC on 2018/5/14.
 */

public class AppAdapter extends BaseAdapter {
    private List<MyAppInfo> myAppInfos = new ArrayList<MyAppInfo>();
    private Context mContext;
    private int[] drawables = {
            R.drawable.app_blue,
            R.drawable.app_green,
            R.drawable.app_jasper,
            R.drawable.app_lawngreen,
            R.drawable.app_red,
            R.drawable.app_yellow
    };

    public AppAdapter(List<MyAppInfo> myAppInfos, Context mContext) {
        this.myAppInfos = myAppInfos;
        this.mContext = mContext;
    }
    public void setData(List<MyAppInfo> myAppInfos) {
        this.myAppInfos = myAppInfos;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return myAppInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return myAppInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_app, null);
            convertView.setFocusable(true);
            viewHolder = new ViewHolder(convertView);
            viewHolder.ivAppIcon = (ImageView) convertView.findViewById(R.id.iv_app_icon);
            viewHolder.tvAppName = (TextView) convertView.findViewById(R.id.tv_app_name);
            viewHolder.itemAppBg = (LinearLayout) convertView.findViewById(R.id.item_app_bg);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MyAppInfo myAppInfo = myAppInfos.get(position);
        Random random = new Random();
        viewHolder.itemAppBg.setBackgroundResource(drawables[random.nextInt(6)]);
        viewHolder.tvAppName.setText(myAppInfo.getLabelName());
        viewHolder.ivAppIcon.setImageDrawable(myAppInfo.getImage());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_app_icon)
        ImageView ivAppIcon;
        @BindView(R.id.tv_app_name)
        TextView tvAppName;
        @BindView(R.id.item_app_bg)
        LinearLayout itemAppBg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
