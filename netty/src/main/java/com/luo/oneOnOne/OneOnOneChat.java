package com.luo.oneOnOne;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author luoliang
 * @description
 * @date 2024/2/21 9:28
 */
public class OneOnOneChat {
    private static final int PORT = 518;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true){
                // 阻塞监听端口
                Socket accept = serverSocket.accept();
                // 接收消息
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                // 发送消息
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));
                BufferedReader userReader = new BufferedReader(new InputStreamReader(System.in));
                String msg = null;
                do {
                    msg = bufferedReader.readLine();
                    System.out.println("msg:" + msg);
                    // 写入服务端要发送的消息
                    bufferedWriter.write("Server:" + userReader.readLine() + "\n");
                    bufferedWriter.flush();
                    // 客户端发送exit代表退出
                } while (!"exit".equals(msg));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
