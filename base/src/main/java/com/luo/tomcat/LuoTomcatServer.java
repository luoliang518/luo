package com.luo.tomcat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LuoTomcatServer {
    public static void main(String[] args) throws IOException {
        //开启ServerSocket服务，设置端口号为8080
        ServerSocket serverSocket=new ServerSocket(8080);
        System.out.println("======服务启动成功========");
        //当服务没有关闭时
        while(!serverSocket.isClosed()){
            //使用socket进行通信
            Socket socket=serverSocket.accept();
            //收到客户端发出的inputstream
            InputStream inputStream=socket.getInputStream();
            System.out.println("执行客户请求:"+Thread.currentThread());
            System.out.println("收到客户请求");
            //读取inputstream的内容
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
            String msg=null;
            while((msg=reader.readLine())!=null){
                if(msg.length()==0) break;
                System.out.println(msg);
            }
            //返回outputstream，主体内容是OK
            String resp=LuoResponse.responseBody+"OK";
            OutputStream outputStream=socket.getOutputStream();
            System.out.println(resp);
            outputStream.write(resp.getBytes());
            outputStream.flush();
            outputStream.close();
            socket.close();
        }
    }
}
