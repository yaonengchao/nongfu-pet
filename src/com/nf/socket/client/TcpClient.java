package com.nf.socket.client;

import com.nf.socket.constant.Constant;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TcpClient {

    public static void main(String[] args) throws IOException {
        System.out.println("客户端启动。。。。。");
        Socket socket = new Socket(Constant.IP, Constant.PORT);
        String sendMsg = null;
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        if (socket.isConnected()) {
            try {
                printWriter = new PrintWriter(socket.getOutputStream());
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in);
                while ((sendMsg = scanner.nextLine()) != null) {
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


    }


}
