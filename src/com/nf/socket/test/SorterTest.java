package com.nf.socket.test;

import com.nf.socket.beans.Animal;
import com.nf.socket.utils.Sorter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class SorterTest {

    /**
     * 排序测试
     */
    @Test
    void sort() {
        Sorter<Animal> sorter = new Sorter<>();

        Animal[] animals = new Animal[4];
        animals[0] = new Animal("dog", 1);
        animals[1] = new Animal("cat", 10);
        animals[2] = new Animal("parrot", 6);
        animals[3] = new Animal("chicken", 7);

        sorter.sort(animals, Comparator.comparingInt(Animal::getCount));

        List<String> collect = Arrays.stream(animals).map(Animal::getName).collect(Collectors.toList());

        Assertions.assertEquals(Arrays.asList("cat", "chicken", "parrot", "dog"), collect, "排序错误！！！");
    }
}