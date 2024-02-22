package com.luo.oneOnOne;

import java.io.*;
import java.net.Socket;

/**
 * @author luoliang
 * @description
 * @date 2024/2/21 9:09
 */
public class Luo {
    private static final String IP = "192.168.3.14";
    private static final int PORT = 518;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(IP, PORT);
            // 接受消息
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 发送消息
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // 获取用户发送的消息
            BufferedReader userReader = new BufferedReader(new InputStreamReader(System.in));

            String msg = null;
            // 循环输入
            while (true){
                String input = userReader.readLine();
                // 写入客户端要发送的消息 用readLine读取消息 以\n 换行结尾
                bufferedWriter.write(input + "\n");
                bufferedWriter.flush();
                msg = bufferedReader.readLine();
                System.out.println(msg);
                // 增加退出聊天指令
                if ("exit".equals(input)){
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
