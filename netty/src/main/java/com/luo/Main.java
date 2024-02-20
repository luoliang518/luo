package com.luo;

import com.luo.util.ExecutorService;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author luoliang
 * @description
 * @date 2024/1/18 16:40
 */
public class Main {
    // 主线程维护连接
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public void run() {
        try {
            ThreadPoolExecutor executor = ExecutorService.getExecutor();
            ServerSocket serverSocket = new ServerSocket(518);
            while (true) {
                Socket socket = serverSocket.accept();
                //提交线程池处理
                executor.submit(new Handler(socket));
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 处理读写服务
    class Handler implements Runnable {
        Socket socket;

        public void run() {
            try {
                // 处理请求
                int len = -1;
                byte[] data = new byte[1024];//每次读取1k
                InputStream inputStream = socket.getInputStream();
                while ((len = inputStream.read(data)) != -1) {
                    System.out.print(new String(data, 0, len));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Handler(Socket socket) {
            this.socket = socket;
        }
    }
}