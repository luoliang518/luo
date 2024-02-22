package com.luo.group.server;

import com.luo.util.ExecutorService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author luoliang
 * @description
 * @date 2024/2/21 10:07
 */
public class GroupServer {
    private static final int PORT = 518;
    /**
     * 创建一个Map存储在线用户的信息
     * key就是客户端的端口号 实际中肯定不会用端口号区分用户，
     * 如果是web的话一般用session value是IO的Writer，用以存储客户端发送的消息
     */
    private Map<Integer,Writer> map = new ConcurrentHashMap<>();
    private ThreadPoolExecutor executor = ExecutorService.getExecutor();
    //客户端连接时往map添加客户端
    public void addClient(Socket socket) throws IOException {
        if (socket != null) {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );
            map.put(socket.getPort(), writer);
            System.out.println("Client["+socket.getPort()+"]:Online");
        }
    }

    //断开连接时map里移除客户端
    public void removeClient(Socket socket) throws Exception {
        if (socket != null) {
            if (map.containsKey(socket.getPort())) {
                map.get(socket.getPort()).close();
                map.remove(socket.getPort());
            }
            System.out.println("Client[" + socket.getPort() + "]Offline");
        }
    }

    //转发客户端消息，这个方法就是把消息发送给在线的其他的所有客户端
    public void sendMessage(Socket socket, String msg) throws IOException {
        //遍历在线客户端
        for (Integer port : map.keySet()) {
            //发送给在线的其他客户端
            if (port != socket.getPort()) {
                Writer writer = map.get(port);
                writer.write(msg);
                writer.flush();
            }
        }
    }

    //接收客户端请求，并分配Handler去处理请求
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true){
                //等待客户端连接
                Socket socket=serverSocket.accept();
                //为客户端分配一个ChatHandler线程
                executor.execute(new ChatHandler(this,socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupServer server=new GroupServer();
        server.start();
    }
}