package com.nf.socket.client;

import com.nf.socket.constant.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpClient {

    private ClientService clientService;

    public TcpClient() {
    }

    public TcpClient(ClientService clientService) {
        this.clientService = clientService;
    }

    public static void main(String[] args) throws IOException {
        TcpClient tcpClient = new TcpClient(new ClientScannerInputService());
        tcpClient.start();
    }

    public void start() throws IOException {
        System.out.println("客户端启动。。。。。");

        Socket socket = new Socket(Constant.IP, Constant.PORT);
        if (socket.isConnected()) {
            try (PrintWriter printWriter = new PrintWriter(socket.getOutputStream()); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                //消息发送
                clientService.sendMessagesByDIY(printWriter, bufferedReader);
            }
        }
        if (!socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
