package com.example.testtelecontroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.SocketUtils.ChatServer;
import com.example.SocketUtils.ChatServer1;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_ip)
    EditText etIp;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.bt_top)
    Button btTop;
    @BindView(R.id.bt_ok)
    Button btOk;
    @BindView(R.id.bt_left)
    Button btLeft;
    @BindView(R.id.bt_right)
    Button btRight;
    @BindView(R.id.bt_bottom)
    Button btBottom;
    @BindView(R.id.bt_back)
    Button btBack;
    @BindView(R.id.bt_addv)
    Button btAddv;
    @BindView(R.id.bt_reducev)
    Button btReducev;
    @BindView(R.id.bt_home)
    Button btHome;
    @BindView(R.id.rl_controller)
    RelativeLayout rlController;

    private boolean canWrite = false;
    //value值的填充具体参照Android KeyCode列表
    private String value = "23";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.d("Mr.U", "onCreate: " + DeviceUtils.getInstance(this).getIPAddress());
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new ChatServer1();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void initView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = null;
                    BufferedWriter writer = null;
                    String ip = etIp.getText().toString();
                    ip = "192.168.1.34";
                    if (ip.trim() != "") {
                        //绑定IP
                        socket = new Socket(ip, 8888);
//                      //设置两秒连接时间
                        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    }
                    canWrite = true;
                    if (canWrite && writer != null) {
                        writer.write(23);
                        writer.newLine();
                        writer.flush();
                        writer.close();
                        socket.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "IP地址或者网络出错", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }

//    public void btOnclick(View v) {
//        try {
//
//            switch (v.getId()) {
//                case R.id.bt_submit:
//                    canWrite = false;
//                    break;
//                case R.id.bt_top:
//                    canWrite = true;
//                    value = "19";
//                    break;
//                case R.id.bt_bottom:
//                    canWrite = true;
//                    value = "20";
//                    break;
//                case R.id.bt_left:
//                    canWrite = true;
//                    value = "21";
//                    break;
//                case R.id.bt_right:
//                    canWrite = true;
//                    value = "22";
//                    break;
//                case R.id.bt_ok:
//                    canWrite = true;
//                    value = "23";
//                    break;
//                case R.id.bt_addv:
//                    canWrite = true;
//                    value = "24";
//                    break;
//                case R.id.bt_reducev:
//                    canWrite = true;
//                    value = "25";
//                    break;
//                case R.id.bt_back:
//                    canWrite = true;
//                    value = "4";
//                    break;
//                case R.id.bt_home:
//                    canWrite = true;
//                    value = "3";
//                    break;
//            }
//            Socket socket = null;
//            BufferedWriter writer = null;
//            String ip = etIp.getText().toString();
//            ip = "172.23.158.31";
//            if (ip.trim() != "") {
//                //绑定IP
//                socket = new Socket(ip, 8888);
//                //设置两秒连接时间
//                OutputStream ou = socket.getOutputStream();
//                writer = new BufferedWriter(new OutputStreamWriter(ou));
//                rlController.setVisibility(View.VISIBLE);
//                btSubmit.setVisibility(View.GONE);
//                etIp.setVisibility(View.GONE);
//            }
//            if (canWrite && writer != null) {
//                writer.write(value);
//                writer.newLine();
//                writer.flush();
//                writer.close();
//                socket.close();
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "IP地址或者网络出错", Toast.LENGTH_SHORT).show();
//            rlController.setVisibility(View.GONE);
//            btSubmit.setVisibility(View.VISIBLE);
//            etIp.setVisibility(View.VISIBLE);
//        }
//    }


}
