package com.nf.socket.data;

import com.nf.socket.beans.Animal;

import java.util.Hashtable;
import java.util.Map;

public class DataBase {
    /**
     * 宠物类型
     */
    public static Map<String, Animal> animalMap = new Hashtable<>();

    static {
        animalMap.put("dog", new Animal("dog", 0));
        animalMap.put("cat", new Animal("cat", 0));
        animalMap.put("parrot", new Animal("parrot", 0));
        animalMap.put("chicken", new Animal("chicken", 0));
    }

    /**
     * 私有化构造器
     */
    private DataBase() {

    }
}
