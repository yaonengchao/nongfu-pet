package com.nf.socket.socket;

import com.nf.socket.command.GetCommand;
import com.nf.socket.command.ListCommand;
import com.nf.socket.service.InventoryManagementService;
import com.nf.socket.service.PetManagementService;

import java.io.*;
import java.net.Socket;

public class Task implements Runnable {

    private Socket socketClient = null;
    private PrintWriter printWriter = null;
    private final BufferedReader bufferedReader;
    private ListCommand listCommand = null;
    private GetCommand getCommand = null;


    public Task(Socket socket) throws IOException {
        this.socketClient = socket;
        printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        bufferedReader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        listCommand = new ListCommand(printWriter);
        getCommand = new GetCommand(printWriter, petManagementService, inventoryManagementService);
    }

    //宠物管理服务
    PetManagementService petManagementService = new PetManagementService();
    //库存服务
    InventoryManagementService inventoryManagementService = new InventoryManagementService();


    @Override
    public void run() {
        try {

            String msg = null;
//            !Thread.interrupted()
            while (!socketClient.isInputShutdown()) {
                //读取到服务端发送过来的消息
                msg = bufferedReader.readLine();

                if (msg == null || "".equals(msg)) {
                    //错误，输入有误
                    printWriter.println("ERR");
                    break;
                } else if ("BEY".equals(msg)) {
                    printWriter.println("OK");
                    break;
                } else if ("LIST".equals(msg)) { //客户端需要查询宠物的受欢迎的程度
                    //执行
                    listCommand.execute(msg);
                } else {
                    //执行
                    getCommand.execute(msg);
                }
            }
        } catch (IOException e) {
            printWriter.println("ERR");
            e.printStackTrace();
        } finally {
            try {
                if (socketClient != null){
                    socketClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
