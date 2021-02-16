package com.nf.socket.utils;


import java.util.Comparator;

/**
 * 排序
 *
 * @param <T>
 */
public class Sorter<T> {
    public void sort(T[] arr, Comparator<T> comparator) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {

                if (comparator.compare(arr[j], arr[j + 1]) < 0) {
                    swap(arr, j, j + 1);
                }
            }

        }
    }

    /**
     * 置换
     *
     * @param arr
     * @param i
     * @param j
     */
    public void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
