package com.nf.socket.command;

import com.nf.socket.beans.Animal;
import com.nf.socket.service.InventoryManagementService;
import com.nf.socket.service.PetManagementService;

import java.io.PrintWriter;

/**
 * 命令行
 */
public class GetCommand implements Command {

    private final PrintWriter printWriter;

    private final PetManagementService petManagementService;

    private final InventoryManagementService inventoryManagementService;


    public GetCommand(PrintWriter printWriter, PetManagementService petManagementService, InventoryManagementService inventoryManagementService) {
        this.printWriter = printWriter;
        this.petManagementService = petManagementService;
        this.inventoryManagementService = inventoryManagementService;
    }

    @Override
    public void execute(String msg) {
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
