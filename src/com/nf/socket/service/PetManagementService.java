package com.nf.socket.service;


import com.nf.socket.beans.Animal;
import com.nf.socket.data.DataBase;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 宠物管理
 */
public class PetManagementService {

    /**
     * 判断宠物是否存在，存在，返回对象
     *
     * @return
     */
    public Animal getAnimal(String name) {
        return DataBase.animalMap.get(name);
    }


    /**
     * 库存加1服务
     *
     * @return
     */
    public void increment(String name) {
        synchronized (PetManagementService.class) {
            Animal animal = DataBase.animalMap.get(name);
            animal.setCount(animal.getCount() + 1);
        }
    }

    /**
     * 重复的受欢迎程度
     *
     * @return
     */
    public static Animal[] popularity() {

        Map<String, Animal> animalMap = DataBase.animalMap;

        Collection<Animal> animals = animalMap.values();

        Animal[] arr = animals.toArray(new Animal[0]);

        //排序
        for (int i = 0; i < arr.length; i++) {
            for (int j = 1; j < arr.length; j++) {
                if (arr[j].getCount() > arr[j - 1].getCount()) {
                    Animal s = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = s;
                }

            }
        }

        //排序，返回

        return arr;
    }


}
