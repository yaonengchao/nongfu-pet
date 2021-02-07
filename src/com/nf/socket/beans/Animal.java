package com.nf.socket.beans;

public class Animal {

    /**
     * 骑车总数
     */
    private int total;

    /**
     * 宠物名称
     */
    private String name;

    /**
     * 宠物购买人数
     */
    private int count;

    public Animal(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public Animal() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    @Override
    public String toString() {
        return name + ":" + count;
    }
}
