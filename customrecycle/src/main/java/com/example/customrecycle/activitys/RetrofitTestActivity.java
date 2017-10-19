package com.example.customrecycle.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.customrecycle.R;
import com.example.customrecycle.base.BaseActivity;
import com.example.customrecycle.frame.retrofit.HttpCallBack;
import com.example.customrecycle.frame.retrofit.HttpRequest;
import com.example.customrecycle.frame.retrofit.ResultData;
import com.example.customrecycle.frame.retrofit.UserEntity;
import com.example.customrecycle.frame.utils.ApkTool;
import com.example.customrecycle.frame.utils.MyToast;
import com.example.customrecycle.frame.utils.entity.MyAppInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class RetrofitTestActivity extends BaseActivity {


    private ImageView iv1;

    private ListView lv_app_list;
    private AppAdapter mAppAdapter;
    public Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_test);
        iv1 = (ImageView) findViewById(R.id.iv_1);
        lv_app_list = (ListView) findViewById(R.id.mListView);
        mAppAdapter = new AppAdapter();
        lv_app_list.setAdapter(mAppAdapter);
        initAppList();
//        getData();
    }
    //获取apk列表
    private void initAppList(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                //扫描得到APP列表
                final List<MyAppInfo> appInfos = ApkTool.scanLocalInstallAppList(RetrofitTestActivity.this.getPackageManager());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAppAdapter.setData(appInfos);
                    }
                });
            }
        }.start();
    }

    class AppAdapter extends BaseAdapter {

        List<MyAppInfo> myAppInfos = new ArrayList<MyAppInfo>();

        public void setData(List<MyAppInfo> myAppInfos) {
            this.myAppInfos = myAppInfos;
            notifyDataSetChanged();
        }

        public List<MyAppInfo> getData() {
            return myAppInfos;
        }

        @Override
        public int getCount() {
            if (myAppInfos != null && myAppInfos.size() > 0) {
                return myAppInfos.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (myAppInfos != null && myAppInfos.size() > 0) {
                return myAppInfos.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mViewHolder;
            MyAppInfo myAppInfo = myAppInfos.get(position);
            if (convertView == null) {
                mViewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_app_info, null);
                mViewHolder.iv_app_icon = (ImageView) convertView.findViewById(R.id.iv_app_icon);
                mViewHolder.tx_app_name = (TextView) convertView.findViewById(R.id.tv_app_name);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }
            mViewHolder.iv_app_icon.setImageDrawable(myAppInfo.getImage());
            mViewHolder.tx_app_name.setText(myAppInfo.getAppName());
            return convertView;
        }

        class ViewHolder {

            ImageView iv_app_icon;
            TextView tx_app_name;
        }
    }

    private void getData() {
        Call call = HttpRequest.getIResource().getTop();
        callRequest(call, new HttpCallBack(UserEntity.SubjectsBean.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                MyToast.showToast(mResult.getTotal() + "");
                List<UserEntity.SubjectsBean> mList = (List<UserEntity.SubjectsBean>) mResult.getDataList();

//
                Glide.with(RetrofitTestActivity.this).
                        load(mList.get(0).getImages().getLarge())
                        .into(iv1);

            }
            @Override
            public void onFailure(String string) {
                MyToast.showToast(R.string.NETWORKERROR);
            }
        });

    }
}
