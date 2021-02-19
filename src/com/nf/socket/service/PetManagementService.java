package com.nf.socket.service;


import com.nf.socket.beans.Animal;
import com.nf.socket.data.DataBase;
import com.nf.socket.utils.Sorter;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 宠物管理
 */
public class PetManagementService {

    private static Lock lock = new ReentrantLock();

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
        lock.lock();
        Animal animal = DataBase.animalMap.get(name);
        animal.setCount(animal.getCount() + 1);
        lock.unlock();
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

        Sorter<Animal> sorter = new Sorter<>();

        //排序，返回  实现Comparator接口，进行设定排序规则
        sorter.sort(arr, Comparator.comparingInt(Animal::getCount));

//        sorter.sort(arr, (a, b) -> Integer.compare(a.getCount(), b.getCount()));

        return arr;
    }


}
