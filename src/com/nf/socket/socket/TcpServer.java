package com.nf.socket.socket;

import com.nf.socket.constant.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpServer {

    /**
     * 定义一个线程池
     */
    private static final ExecutorService executorService = Executors.newFixedThreadPool(100);

    public static void main(String[] args) throws IOException {
        System.out.println("服务端serverSocket项目已经启动。。。。");
        //创建ServerSocket对象，建议一个服务 端口8080
        ServerSocket serverSocket = new ServerSocket(Constant.PORT);
        while (true) {
            //获取客户端对象
            Socket socketClient = serverSocket.accept();
            executorService.execute(new Task(socketClient));
        }
    }
}


