package com.nf.socket.test;

import com.nf.socket.constant.Constant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class TestServer {


    @Test
    public void tcpClientMain() {

        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int j = 0; j < 5; j++) {

            new Thread(() -> {

                System.out.println("客户端启动。。。。。");
                Socket socket = null;
                try {
                    socket = new Socket(Constant.IP, Constant.PORT);
                    String sendMsg = null;
                    if (socket.isConnected()) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                        try {
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
                            printWriter.close();
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
                countDownLatch.countDown();
            }).start();
        }


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @AfterEach
    public void after() {
        Socket socket = null;
        try {
            socket = new Socket(Constant.IP, Constant.PORT);
            String sendMsg = null;
            if (socket.isConnected()) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                try {
                    printWriter.println("LIST");
                    printWriter.flush();
                    //确认消息
                    String receiveMsg = null;//客户端发过来的信息
                    char[] ch = new char[1024];
                    int read = bufferedReader.read(ch);
                    receiveMsg = new String(ch, 0, read);


                    Assertions.assertEquals("cat:125\ndog:125\nchicken:125\nparrot:125\nOK",receiveMsg, "数据结果错误");
                    System.out.print(receiveMsg);

                } finally {
                    printWriter.close();
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
    }



}
