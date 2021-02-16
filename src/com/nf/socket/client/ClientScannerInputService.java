package com.nf.socket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

/**
 * 客户端服务
 * <p>
 * 接收到客户端输入的消息，然后进行处理，判断是否是合理的消息请求
 */
public class ClientScannerInputService implements ClientService{

    /**
     * 发送消息通过dos命令行
     *
     * @param printWriter
     * @param bufferedReader
     */
    public void sendMessagesByDIY(PrintWriter printWriter, BufferedReader bufferedReader) throws IOException {
        String message = null;
        Scanner scanner = new Scanner(System.in);
        while ((message = scanner.nextLine()) != null) {
            interceptInput(message);
            List<String> result = sendMessages(printWriter, bufferedReader, message);
            result.forEach(System.out::println);
        }
    }

}
