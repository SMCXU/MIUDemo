package com.example.customrecycle.activitys.setting;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.customrecycle.R;
import com.example.customrecycle.entity.NetworkSpeedInfo;
import com.example.customrecycle.frame.utils.ReadFileUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpeedTestActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.speedtest_btn_start)
    Button didNotStart;
    @BindView(R.id.speedtset_didinotlayout)
    LinearLayout didNotStartLayout;
    @BindView(R.id.speed_test_percent)
    TextView percent;
    @BindView(R.id.speedtest_progressBar)
    ProgressBar speedProgressBar;
    @BindView(R.id.speedtset_btn_stoptest)
    Button inStart;
    @BindView(R.id.speedtest_instartlayout)
    LinearLayout inStartLayout;
    @BindView(R.id.speedtest_speed)
    TextView speed;
    @BindView(R.id.speed_movietype)
    TextView movieType;
    @BindView(R.id.speedtest_btn_startagain)
    Button startAgain;
    @BindView(R.id.speedtest_startagainlayout)
    LinearLayout startAgainLayout;
    @BindView(R.id.speed_test_fl)
    FrameLayout speedTestFl;
    @BindView(R.id.nandb_tittle)
    TextView nandbTittle;

    private static final int PROGRESSCHANGE = 0;
    private static final int SPEEDUPDATE = 1;
    private static final int SPEED_FINISH = 2;
    private int progress = 0;
    private long currenSpeed;//当前速度
    private long averageSpeed;//平均速度
    private long speedTaital;
    private final List<Long> list = new ArrayList<>();
    private Boolean threadCanRun = true;
    private Boolean progressThreadCanRun = true;
    private NetworkSpeedInfo networkSpeedInfo;
    private static final String URL = "http://gdown.baidu.com/data/wisegame/6546ec811c58770b/labixiaoxindamaoxian_8.apk";
    private byte[] fileData;
    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROGRESSCHANGE:
                    progress = NetworkSpeedInfo.progress;
                    percent.setText(progress + "%");
                    if (progress < 100) {
                        speedProgressBar.setProgress(progress);
                    } else {
                        inStart.performClick();
                        progressThreadCanRun = false;
                        progress = 0;
                        speedProgressBar.setProgress(progress);
                    }
                    break;
                case SPEEDUPDATE:
                    currenSpeed = NetworkSpeedInfo.Speed;
                    list.add(currenSpeed);
                    for (long speed : list) {
                        speedTaital += speed;
                    }
                    averageSpeed = speedTaital / list.size();
                    speed.setText(averageSpeed + "kb/s");
                    if (averageSpeed <= 200) {
                        movieType.setText("普清电影");
                    } else if (averageSpeed <= 400) {
                        movieType.setText("高清电影");
                    } else if (averageSpeed > 400) {
                        movieType.setText("超清电影");
                    }
                    speedTaital = 0;
                    break;
                case SPEED_FINISH:
                    speed.setText(averageSpeed + "kb/s");
                    if (averageSpeed <= 200) {
                        movieType.setText("普清电影");
                    } else if (averageSpeed <= 400) {
                        movieType.setText("高清电影");
                    } else if (averageSpeed > 400) {
                        movieType.setText("超清电影");
                    }
                    progressThreadCanRun = false;
                    threadCanRun = false;
                    NetworkSpeedInfo.FILECANREAD = false;
                    break;
                default:
                    break;
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_test);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        networkSpeedInfo = new NetworkSpeedInfo();
        didNotStart.setOnClickListener(this);
        inStart.setOnClickListener(this);
        startAgain.setOnClickListener(this);
    }

    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.speedtest_btn_start:
                didNotStartLayout.setVisibility(View.GONE);
                inStartLayout.setVisibility(View.VISIBLE);
                startAgainLayout.setVisibility(View.GONE);
                inStart.requestFocus();
                inStart.requestFocusFromTouch();
                progressThreadCanRun = true;
                threadCanRun = true;
                NetworkSpeedInfo.FILECANREAD = true;
                new Thread() {

                    @Override
                    public void run() {
                        fileData = ReadFileUtil.ReadFileFromURL(URL,
                                networkSpeedInfo);
                    }
                }.start();
              new Thread() {

                    @Override
                    public void run() {
                        while (threadCanRun) {
                            try {
                                sleep(50);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            handler.sendEmptyMessage(SPEEDUPDATE);
                            if (NetworkSpeedInfo.FinishBytes >= NetworkSpeedInfo.totalBytes) {
                                handler.sendEmptyMessage(SPEED_FINISH);
                                NetworkSpeedInfo.FinishBytes = 0;
                            }
                        }
                    }
                }.start();
                new Thread() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        while (progressThreadCanRun) {
                            try {
                                sleep(500);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            handler.sendEmptyMessage(PROGRESSCHANGE);
                        }
                    }
                }.start();
                break;
            case R.id.speedtset_btn_stoptest:
                startAgainLayout.setVisibility(View.VISIBLE);
                inStartLayout.setVisibility(View.GONE);
                didNotStartLayout.setVisibility(View.GONE);
                startAgain.requestFocus();
                startAgain.requestFocusFromTouch();
                NetworkSpeedInfo.progress = 0;
                NetworkSpeedInfo.FinishBytes = 0;
                handler.sendEmptyMessage(SPEED_FINISH);
                break;
            case R.id.speedtest_btn_startagain:
                didNotStartLayout.setVisibility(View.VISIBLE);
                startAgainLayout.setVisibility(View.GONE);
                inStartLayout.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}
