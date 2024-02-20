package com.luo;

import com.luo.util.ExecutorService;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author luoliang
 * @description
 * @date 2024/1/18 16:41
 */
public class Bio {
    public static void main(String[] args) {
        while (true) {
            ServerSocket serverSocket = null;
            try {
                // 创建ServerSocket并绑定端口
                serverSocket = new ServerSocket(518);
                // 等待客户端连接
                Socket clientSocket = serverSocket.accept();
                ExecutorService.getExecutor().submit(new Handler(clientSocket));
                // 关闭客户端Socket
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                // 在finally块中关闭ServerSocket
                if (serverSocket != null && !serverSocket.isClosed()) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    // 处理读写服务
    @Data
    @AllArgsConstructor
    static class Handler implements Runnable {
        private Socket socket;
        public void run() {
            try {
                //获取Socket的输入流，接收数据
                BufferedReader buf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String readData = buf.readLine();
                while (readData != null) {
                    readData = buf.readLine();
                    System.out.println(readData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}