package com.nf.socket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户端服务
 * <p>
 * 接口
 */
public interface ClientService {
    /**
     * 拦截输入
     */
    default void interceptInput(String inputStr) {


    }

    /**
     * 自定义发送消息
     *
     * @param printWriter
     * @param bufferedReader
     */
    void sendMessagesByDIY(PrintWriter printWriter, BufferedReader bufferedReader) throws IOException;

    default List<String> sendMessages(PrintWriter printWriter, BufferedReader bufferedReader, String message) throws IOException {
        List<String> list = new ArrayList<>();

        printWriter.println(message);
        printWriter.flush();

        //确认消息
        String receiveMsg = null;//服务端发过来的信息
        while (!("OK".equals(receiveMsg = bufferedReader.readLine()) || "ERR".equals(receiveMsg))) {
            list.add(receiveMsg);
        }
        list.add(receiveMsg);

        return list;
    }

}
