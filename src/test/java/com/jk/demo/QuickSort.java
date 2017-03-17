package com.jk.demo;

import java.util.Arrays;

/**
 * Created by zengping on 2017/2/21.
 */
public class QuickSort {

    public static int partition(int[] array, int lo, int hi) {
        //三取数组中优化
        int mid = lo + (hi - lo) / 2;

        if (array[mid] > array[hi]) {
            swap(array, mid, hi);
        }
        if (array[lo] > array[hi]) {
            swap(array, lo, hi);
        }
        if (array[mid] > array[lo]) {
            swap(array, mid, lo);
        }

        int key = array[lo];
        while (lo < hi) {
            while (array[hi] >= key && hi > lo)
                hi--;
            swap(array, lo, hi);
            while (array[lo] <= key && hi > lo)
                lo++;
            swap(array, lo, hi);
        }

        Arrays.stream(array).forEach(value -> System.out.print(value + ","));
        System.out.println("---------------" + key + "---------------");
        return hi;
    }

    public static void swap(int[] array, int start_index, int end_index) {
        int temp = array[start_index];
        array[start_index] = array[end_index];
        array[end_index] = temp;
    }

    public static void sort(int[] array, int lo, int hi) {
        if (lo >= hi)
            return;
        int index = partition(array, lo, hi);
        sort(array, lo, index - 1);
        sort(array, index + 1, hi);
    }

    public static void main(String[] args) {
        int[] ar = new int[]{4, 3, 7, 5, 9, 13, 23, 11, 8};
        sort(ar, 0, ar.length - 1);
        System.out.println("---------------end---------------");
    }
}
