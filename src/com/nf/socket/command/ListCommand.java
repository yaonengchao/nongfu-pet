package com.nf.socket.command;

import com.nf.socket.beans.Animal;
import com.nf.socket.service.PetManagementService;

import java.io.PrintWriter;

/**
 * 命令行
 */
public class ListCommand implements Command{

    private final PrintWriter printWriter;

    public ListCommand(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    @Override
    public void execute(String msg) {
        //调用受欢迎程度的方法
        Animal[] popularity = PetManagementService.popularity();

        for (Animal animal : popularity) {
            printWriter.println(animal.toString());
        }
        printWriter.println("OK");
    }
}
