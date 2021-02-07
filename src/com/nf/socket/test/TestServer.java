package com.nf.socket.test;

import com.nf.socket.constant.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TestServer {

    public static void main(String[] args) {

        for (int j = 0; j < 5; j++) {

            new Thread(()->{

                System.out.println("客户端启动。。。。。");
                Socket socket = null;
                try {
                    socket = new Socket(Constant.IP, Constant.PORT);
                    System.out.println(socket);
                    String sendMsg = null;
                    BufferedReader bufferedReader = null;
                    PrintWriter printWriter = null;
                    if (socket.isConnected()) {
                        try {
                            printWriter = new PrintWriter(socket.getOutputStream());
                            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            for (int i = 0; i < 100; i++) {

                                switch (i % 4) {
                                    case 0:
                                        sendMsg = "GET:dog";

                                        break;
                                    case 1:
                                        sendMsg = "GET:cat";
                                        break;
                                    case 2:
                                        sendMsg = "GET:parrot";
                                        break;
                                    case 3:
                                        sendMsg = "GET:chicken";
                                        break;
                                    default:
                                        break;
                                }

                                printWriter.println(sendMsg);
                                printWriter.flush();
                                //确认消息
                                String receiveMsg = null;//客户端发过来的信息
                                char[] ch = new char[1024];
                                int read = bufferedReader.read(ch);
                                receiveMsg = new String(ch, 0, read);
                                System.out.print(receiveMsg);
                            }

                        } finally {
                            if (printWriter != null)
                                printWriter.close();
                            if (bufferedReader != null)
                                bufferedReader.close();
                        }
                    }
                    if (!socket.isClosed()) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();

        }

    }

}
