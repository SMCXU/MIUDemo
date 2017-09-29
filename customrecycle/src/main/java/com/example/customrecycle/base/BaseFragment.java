package com.example.customrecycle.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.customrecycle.frame.retrofit.HttpCallBack;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 *使用借鉴:http://www.jianshu.com/p/104be7cd72b6
 */
public abstract class BaseFragment extends Fragment {

    /**
     * fragment的根布局
     */
    protected View mRootView = null;
    private List<Call> m_listRequest;
    //是否可见
    protected boolean isVisble;
    // 标志位，标志Fragment已经初始化完成。
    public boolean isPrepared = false;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //布局重复利用
        if (mRootView == null)
            mRootView = onCreateView(inflater, container);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化
        initialize(mRootView, savedInstanceState);
    }

    private List<Call> getListRequest() {
        if (m_listRequest == null) {
            m_listRequest = new ArrayList<>();
        }
        return m_listRequest;
    }

    /**
     * 调用网络请求
     *
     * @param call
     * @param httpCallBack
     */
    public void callRequest(Call call, HttpCallBack httpCallBack) {
        call.enqueue(httpCallBack);
        addRequest(call);
    }

    /**
     * 添加到请求队列，以便销毁
     *
     * @param call
     */
    public void addRequest(Call call) {
        getListRequest().add(call);
    }


    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消网络请求
        if (m_listRequest != null && m_listRequest.size() > 0) {
            for (Call call : m_listRequest) {
                call.cancel();
            }
        }
        mRootView = null;
    }

    /**
     * 为该Fragment绑定View<br>
     * 该方法会在{@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}中执行<br>
     * 该方法执行后，会立即执行{@link # initialize(View)}方法
     *
     * @param inflater
     * @param container
     * @return
     */
    protected abstract View onCreateView(LayoutInflater inflater, ViewGroup container);

    /**
     * 初始化Fragment相关操作<br>
     * 该方法会在{@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}中执行<br>
     * 在执行此方法前，程序会先执行{@link #onCreateView(LayoutInflater, ViewGroup)}
     *
     * @param root               该Fragment绑定的View
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    protected abstract void initialize(View root, @Nullable Bundle savedInstanceState);

    //实现懒加载
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisble = true;
            onVisible();
        } else {
            isVisble = false;
            onInVisible();
        }

    }

    protected void onInVisible() {
    }
    protected void onVisible() {
        //加载数据
        loadData();
    }
    protected abstract void loadData();
    //懒加载结束
}
