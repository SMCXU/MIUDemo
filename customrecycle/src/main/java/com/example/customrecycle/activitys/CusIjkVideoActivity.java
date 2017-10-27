package com.example.customrecycle.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.customrecycle.R;
import com.example.customrecycle.base.BaseActivity;
import com.example.customrecycle.weight.CustomMediaController;
import com.example.customrecycle.weight.IjkVideoView;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class CusIjkVideoActivity extends BaseActivity {

    private String url1 = "http://2449.vod.myqcloud.com/2449_bfbbfa3cea8f11e5aac3db03cda99974.f20.mp4";
    private String url2 = "/storage/601A-A257/伴我们一路同行~我不愿让你一个人[超清版].mp4";
    private String url3 = "/storage/601A-A257/广告视频/雷克萨斯-宽20161207.mp4";
    private String url5 = "/storage/601A-A257/曳步舞T1m/中国龙队 11月成员大合集_标清.avi";
    private String url4 = "/storage/601A-A257/曳步舞T1m/沧州鬼步舞教学第二期。沧州Czs丶c1(大帝)个人花式教学_标清.avi";
    private IjkVideoView mVideoView;
    private CustomMediaController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ijk_video_demo);
        initView();
    }

    private void initVideo() {
        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        //false代表不加速
        mController = new CustomMediaController(this, false);
        mVideoView.setMediaController(mController);
        mVideoView.setVideoURI(Uri.parse(url3));
        mVideoView.start();
    }

    private void initView() {
        mVideoView = (IjkVideoView) findViewById(R.id.mVideoView);

        // 扫描功能
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请CAMERA权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
        } else {
            initVideo();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!mVideoView.isBackgroundPlayEnabled()) {
            mVideoView.stopPlayback();
            mVideoView.release(true);
            mVideoView.stopBackgroundPlay();
        } else {
            mVideoView.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_up_in, R.anim.activity_up_out);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (3 == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 授权,开始扫描文件,这里应该是异步,暂时
                initVideo();
            } else {
                // 未授权
            }
        }
    }
}
