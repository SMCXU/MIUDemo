package com.example.zlq_pc.miudemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zlq_pc.miudemo.R;
import com.example.zlq_pc.miudemo.weight_flash.FocusBorder;
import com.example.zlq_pc.miudemo.weight_flash.RoundedFrameLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: ysj
 * @date: 2017-03-21 16:01
 */
public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList;
    private String selectId;
    private int type;
    ViewHolder holder;

    public GridAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.grid_item_simple, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(mList.get(position));
        return convertView;
    }





    static class ViewHolder {
        @BindView(R.id.iv_app)
        RoundedFrameLayout ivApp;
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
