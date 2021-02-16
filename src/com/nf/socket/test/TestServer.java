package com.nf.socket.test;

import com.nf.socket.client.ClientService;
import com.nf.socket.client.TcpClient;
import com.nf.socket.constant.Constant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class TestServer {
    List<String> result = null;

    public List<String> getListResult() {
        TcpClient tcpClient = new TcpClient(new ClientService() {
            @Override
            public void sendMessagesByDIY(PrintWriter printWriter, BufferedReader bufferedReader) throws IOException {
                String sendMsg = "LIST";
                 result = this.sendMessages(printWriter, bufferedReader, sendMsg);
            }
        });
        try {
            tcpClient.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Test
    public void tcpClientMain() {

        List<String> startListResult = getListResult();
        System.out.println(startListResult);

        //解析结果


        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int j = 0; j < 5; j++) {

            new Thread(() -> {

                TcpClient tcpClient = new TcpClient(new ClientService() {

                    @Override
                    public void sendMessagesByDIY(PrintWriter printWriter, BufferedReader bufferedReader) throws IOException {
                        String sendMsg = null;
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
                            List<String> result = this.sendMessages(printWriter, bufferedReader, sendMsg);
                            result.forEach(System.out::print);
                        }
                    }
                });
                try {
                    tcpClient.start();
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

        List<String> endListResult = getListResult();
        System.out.println(endListResult);

        //解析结果


    }

}
