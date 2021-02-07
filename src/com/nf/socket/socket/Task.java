package com.nf.socket.socket;

import com.nf.socket.beans.Animal;
import com.nf.socket.service.InventoryManagementService;
import com.nf.socket.service.PetManagementService;

import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.stream.Stream;

public class Task implements Runnable {

    private Socket socketClient = null;
    private final PrintWriter printWriter;
    private final BufferedReader bufferedReader;

    public Task(Socket socket) throws IOException {
        this.socketClient = socket;
        printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        bufferedReader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
    }

    //宠物管理服务
    PetManagementService petManagementService = new PetManagementService();
    //库存服务
    InventoryManagementService inventoryManagementService = new InventoryManagementService();

    @Override
    public void run() {
        try {

            String msg = null;
            while (!Thread.interrupted()) {
                //读取到服务端发送过来的消息
                msg = bufferedReader.readLine();
                if (msg == null || "".equals(msg)) {
                    //错误，输入有误
                    printWriter.println("ERR");

                } else if ("LIST".equals(msg)) { //客户端需要查询宠物的受欢迎的程度
                    //调用受欢迎程度的方法
                    Animal[] popularity = PetManagementService.popularity();

                    StringBuilder sb = new StringBuilder();

                    Stream.of(popularity).forEach(animal -> {
                        sb.append(animal.toString());
                        sb.append("\n");
                    });
                    sb.append("OK");
                    printWriter.println(sb.toString());
                } else {
                    try {
                        String name = msg.split(":")[1];
                        //判断宠物是否存在
                        Animal animal = petManagementService.getAnimal(name);
                        if (animal == null) {
                            printWriter.println("ERR");
                        } else if (!inventoryManagementService.judgingInventory()) { //库存是否足够，因为一定足够
                            printWriter.println("ERR");
                        } else {
                            //加一
                            petManagementService.increment(animal.getName());
                            printWriter.println("OK");
                        }
                    } catch (Exception e) {
                        printWriter.println("ERR");
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socketClient != null)
                    socketClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
