package com.example.customrecycle.frame.utils.SocketUtils;

import android.util.Log;

import com.example.customrecycle.base.BaseApp;
import com.example.customrecycle.frame.utils.DemoUtils;
import com.example.customrecycle.frame.utils.DeviceUtils;
import com.example.customrecycle.frame.utils.VirturlKeyPadCtr;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Decoder.BASE64Encoder;


/**
 * Created by ZLQ-PC on 2018/4/24.
 */

public class ChatServer {

    // 记录所有的客户端Soccket
    public static List<Socket> clientSockets
            = new ArrayList<Socket>();
    private ServerSocket ss;
    public Socket socket;

    public void StopServerSocket() {

        try {
            if (ss != null)
                ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ChatServer() throws IOException {
        // 启动线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 创建ServerSocket，准备接受客户端连接
                    ss = new ServerSocket(30000);

                    while (true) {
                        socket = ss.accept();
                        // 得到Socket对应的输入流
                        InputStream in = socket.getInputStream();
                        // 得到Socket对应的输出流
                        OutputStream out = socket.getOutputStream();
                        byte[] buff = new byte[1024];
                        String req = "";
                        // 读取数据，此时建立与WebSocket的"握手"。
                        int count = in.read(buff);
//                    // 如果读取的数据长度大于0
                        if (count > 0) {
                            // 将读取的数据转化为字符串
                            req = new String(buff, 0, count);
//                            Log.d("Mr.U", "握手请求");
                            // 获取WebSocket的key
                            String secKey = getSecWebSocketKey(req);
//                            Log.d("Mr.U", "secKey = " + secKey);
                            String response = "HTTP/1.1 101 Switching Protocols\r\nUpgrade: "
                                    + "websocket\r\nConnection: Upgrade\r\nSec-WebSocket-Accept: "
                                    + getSecWebSocketAccept(secKey) + "\r\n\r\n";
                            out.write(response.getBytes());
                        }

                        int hasRead = 0;
                        // 不断读取WebSocket发送过来的数据
                        while ((hasRead = in.read(buff)) > 0) {
                /*
                    因为WebSocket发送过来的数据遵循了一定的协议格式，
                    其中第3个～第6个字节是数据掩码。
                    从第7个字节开始才是真正的有效数据。
                    因此程序使用第3个～第6个字节对后面的数据进行了处理
                */
                            for (int i = 0; i < hasRead - 6; i++) {
                                buff[i + 6] = (byte) (buff[i % 4 + 2] ^ buff[i + 6]);
                            }
                            // 获得从浏览器发送过来的数据
                            String pushMsg = new String(buff
                                    , 6, hasRead - 6, "UTF-8");
                            if (pushMsg.trim().length() > 0 && DemoUtils.filterUnNumber(pushMsg.trim()).length() == 0) {
                                StopServerSocket();
                                new ChatServer();
                                break;
                            }
                            Log.d("Mr.U", "run: " + pushMsg);
                            String str = "";
                            //这里用到的keycode都是一位或者两位的
                            if (pushMsg.length() == 1) {
                                str = pushMsg.trim().substring(0, 1);
                            } else {
                                str = pushMsg.trim().substring(0, 2);
                            }
                            VirturlKeyPadCtr.simulateKeystroke(Integer.parseInt(str));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        // 关闭Socket
                        if (socket != null) {
                            socket.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();
    }

    // 获取WebSocket请求的SecKey
    private String getSecWebSocketKey(String req) {
        //构建正则表达式，获取Sec-WebSocket-Key: 后面的内容
        Pattern p = Pattern.compile("^(Sec-WebSocket-Key:).+",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher m = p.matcher(req);
        if (m.find()) {
            // 提取Sec-WebSocket-Key
            String foundstring = m.group();
            return foundstring.split(":")[1].trim();
        } else {
            return null;
        }
    }

    // 根据WebSocket请求的SecKey计算SecAccept
    private String getSecWebSocketAccept(String key)
            throws Exception {
        String guid = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
        key += guid;
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(key.getBytes("ISO-8859-1"), 0, key.length());
        byte[] sha1Hash = md.digest();
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(sha1Hash);
    }
}


