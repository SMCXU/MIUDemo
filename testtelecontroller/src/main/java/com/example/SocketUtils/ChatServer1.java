package com.example.SocketUtils;

import android.util.Log;

import com.google.common.util.concurrent.Runnables;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.regex.*;
import java.util.*;

import Decoder.BASE64Encoder;


/**
 * Created by ZLQ-PC on 2018/4/24.
 */

public class ChatServer1 {

    // 记录所有的客户端Soccket
    public static List<Socket> clientSockets
            = new ArrayList<Socket>();
    private ServerSocket ss;
    private Socket socket;

    public ChatServer1() throws IOException {
        // 启动线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 创建ServerSocket，准备接受客户端连接
                    ss = new ServerSocket(30000);
                    socket = ss.accept();
//                    while (true) {
                    // 得到Socket对应的输入流
                    InputStream in = socket.getInputStream();
                    // 得到Socket对应的输出流
                    OutputStream out = socket.getOutputStream();
                    byte[] buff = new byte[1024];
                    String req = "";
                    // 读取数据，此时建立与WebSocket的"握手"。
                    int count = in.read(buff);
                    // 如果读取的数据长度大于0
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
//                            Log.d("Mr.U", "secAccept = " + getSecWebSocketAccept(secKey));
                        out.write(response.getBytes());
                    }

                    int hasRead = 0;
                    // 不断读取WebSocket发送过来的数据
                    while ((hasRead = in.read(buff)) > 0) {
//                            Log.d("Mr.U", "接收的字节数：" + hasRead);
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
                        Log.d("Mr.U", "run: " + pushMsg.substring(0,2));
                        // 遍历Socket集合，依次向每个Socket发送数据
                        for (Iterator<Socket> it = ChatServer1.clientSockets.iterator()
                             ; it.hasNext(); ) {
                            try {
                                Socket s = it.next();
                                // 发送数据时，第一个字节必须与读到的第一个字节相同
                                byte[] pushHead = new byte[2];
                                pushHead[0] = buff[0];
                                // 发送数据时，第二个字节记录发送数据的长度
                                pushHead[1] = (byte) pushMsg.getBytes("UTF-8").length;
                                // 发送前两个字节
                                s.getOutputStream().write(pushHead);
                                // 发送有效数据
                                s.getOutputStream().write(pushMsg.getBytes("UTF-8"));
                            } catch (SocketException ex) {
                                // 如果捕捉到异常，表明该Socket已经关闭
                                // 将该Socket从Socket集合中删除
                                it.remove();
                            }
                        }
                    }
//                    }

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


