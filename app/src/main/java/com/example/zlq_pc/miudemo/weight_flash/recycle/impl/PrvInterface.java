package com.example.zlq_pc.miudemo.weight_flash.recycle.impl;


import com.example.zlq_pc.miudemo.weight_flash.recycle.RecyclerViewTV;

/**
 *  按键加载更多接口.
 * Created by hailongqiu on 2016/9/5.
 */
public interface PrvInterface {

    void setOnLoadMoreComplete(); // 按键加载更多-->完成.
    void setPagingableListener(RecyclerViewTV.PagingableListener pagingableListener);

}
