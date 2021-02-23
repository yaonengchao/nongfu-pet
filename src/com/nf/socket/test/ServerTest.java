package com.nf.socket.test;

import com.nf.socket.beans.Animal;
import com.nf.socket.client.ClientService;
import com.nf.socket.client.TcpClient;
import com.nf.socket.utils.Sorter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class ServerTest {
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
        Sorter<Animal> sorter = new Sorter<>();
        //解析结果
        Map<String, Animal> startMap = analysisResult(startListResult);

        CountDownLatch countDownLatch = new CountDownLatch(50);

        for (int j = 0; j < 50; j++) {
            int[] numbers = new int[100];
            Random random = new Random();
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = random.nextInt(4);

                switch (numbers[i]) {
                    case 0:
                        startMap.put("dog", new Animal("dog", startMap.get("dog").getCount() + 1));
                        break;
                    case 1:
                        startMap.put("cat", new Animal("cat", startMap.get("cat").getCount() + 1));
                        break;
                    case 2:
                        startMap.put("parrot", new Animal("parrot", startMap.get("parrot").getCount() + 1));
                        break;
                    case 3:
                        startMap.put("chicken", new Animal("chicken", startMap.get("chicken").getCount() + 1));
                        break;
                    default:
                        break;
                }

            }

            Thread t = new Thread(() -> {

                TcpClient tcpClient = new TcpClient(new ClientService() {

                    @Override
                    public void sendMessagesByDIY(PrintWriter printWriter, BufferedReader bufferedReader) throws IOException {
                        String sendMsg = null;
                        for (int number : numbers) {

                            switch (number) {
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
                        this.sendMessages(printWriter, bufferedReader, "BEY");

                    }
                });
                try {
                    tcpClient.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();


            });
            t.start();
        }
        try {
            countDownLatch.await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //解析结果并排序
        Map<String, Animal> endMap = analysisResult(getListResult());
        Collection<Animal> endList = endMap.values();
        Animal[] endAnimal = endList.toArray(new Animal[0]);
        sorter.sort(endAnimal, Comparator.comparingInt(Animal::getCount));

        //排序
        Collection<Animal> startList = startMap.values();
        Animal[] startAnimal = startList.toArray(new Animal[0]);
        sorter.sort(startAnimal, Comparator.comparingInt(Animal::getCount));

        Assertions.assertEquals(Arrays.asList(startAnimal).toString(), Arrays.asList(endAnimal).toString(), "数据错误！！！");

        System.out.println("期望值：" + Arrays.asList(startAnimal).toString());
        System.out.println("计算值：" + Arrays.asList(endAnimal).toString());

    }

    //解析结果
    public Map<String, Animal> analysisResult(List<String> resultList) {
        //解析结果
        Map<String, Animal> map = new Hashtable<>();
        result.forEach(e -> {
            if (!"OK".equals(e)) {
                String[] result = e.split(":");
                String name = result[0];
                int count = Integer.parseInt(result[1]);
                Animal animal = new Animal(name, count);
                map.put(name, animal);
            }
        });
        return map;
    }

}
