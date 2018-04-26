package com.example.SocketUtils;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created by ZLQ-PC on 2018/4/23.
 */

public class ChatServer {

    public ChatServer() throws IOException {
//        while (true) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket serverSocket = null;
                InputStream is = null;
                Socket socket = null;
                InputStreamReader isr = null;
                try {
                    serverSocket = new ServerSocket(30000);
                    socket = serverSocket.accept();
                    is = socket.getInputStream();
                    isr = new InputStreamReader(is, "UTF-8");

                    char[] buf = new char[1024];
                    int len = isr.read(buf);
                    String str = new String(buf, 0, len);
                    Log.d("Mr.U", "========KeyCode:======== " + str);

//                    byte[] bytes = new byte[1024];
//                    int hasRead = 0;
//                    while ((hasRead = is.read(bytes)) > 0) {
//                 /*
//                    因为WebSocket发送过来的数据遵循了一定的协议格式，
//                    其中第3个～第6个字节是数据掩码。
//                    从第7个字节开始才是真正的有效数据。
//                    因此程序使用第3个～第6个字节对后面的数据进行了处理
//                */
//                        for (int i = 0; i < hasRead - 6; i++) {
//                            bytes[i + 6] = (byte) (bytes[i % 4 + 2] ^ bytes[i + 6]);
//                        }
//                        // 获得从浏览器发送过来的数据
//                        String pushMsg = new String(bytes
//                                , 6, hasRead - 6, "UTF-8");
//                        Log.d("Mr.U", "===========KeyCode=========" + pushMsg);
//                        //调用指令转换方法
////                            DeviceUtils.getInstance(BaseApp.getContext()).doCmds(pushMsg.trim());
//                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (isr != null) {
                            isr.close();
                        }
                        if (socket != null) {
                            socket.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
//    }
}

