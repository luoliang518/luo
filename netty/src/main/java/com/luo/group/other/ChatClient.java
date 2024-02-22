package com.luo.group.other;


import java.io.*;
import java.net.Socket;

public class ChatClient {
    private BufferedReader reader;
    private BufferedWriter writer;
    private Socket socket;
    public static void main(String[] args) {
        new ChatClient().start();
    }
    public void sendToServer(String msg) throws IOException {
        if (!socket.isOutputShutdown()) {
            writer.write(msg + "\n");
            writer.flush();
        }
    }
    //从服务器接收消息
    public String receive() throws IOException {
        String msg = null;
        if (!socket.isInputShutdown()) {
            msg = reader.readLine();
        }
        return msg;
    }
    public void start() {
        try {
            socket = new Socket("192.168.3.14", 518);
            reader=new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            writer=new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );
            new Thread(new UserInputHandler(this)).start();
            String msg=null;
            while ((msg=receive())!=null){
                System.out.println(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
               if(writer!=null){
                   writer.close();
               }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    static class UserInputHandler implements Runnable {
        private ChatClient client;
        public UserInputHandler(ChatClient client) {
            this.client = client;
        }
        @Override
        public void run() {
            try {
                //接收用户输入的消息
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    String input = reader.readLine();
                    //向服务器发送消息
                    client.sendToServer(input);
                    if (input.equals("quit"))break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}