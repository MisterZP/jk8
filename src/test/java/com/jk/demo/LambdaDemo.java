package com.jk.demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/**
 * Created by zengping on 2017/1/24.
 */
public class LambdaDemo{

    @Test
    public void joinJk8(){
        /*List<String> arrays = Arrays.asList("liy", "zengp", "mamu", null , "fat");
        String res = arrays.stream().filter(Objects::nonNull).collect(Collectors.joining(","));
        Assert.assertEquals("liy,zengp,mamu,fat", res);
        Supplier<Runnable> c = () -> () -> System.out.println("hi");

        Callable<Integer> callable = true ? (() -> 23) : (() -> 42);
        List<String> list = new ArrayList<>();
        Collections.sort(list, (one,two)-> null != one && null != two ? one.compareToIgnoreCase(two) : 0);
        new Thread(()->{
           for(int i = 0; i < 100; i++){
               System.out.println(i);
           }
        }).start();

        Object o = (Runnable) () -> System.out.println("hi");

        List<String> ls = Collections.checkedList(new ArrayList<>(), String.class);

        Set<Integer> si = true ? Collections.singleton(23) : Collections.emptySet();


        List<Integer> aList = new ArrayList<>();
        list.forEach(e -> aList.set(0, aList.get(0) + e.length()));

        int sum = list.stream()
                .mapToInt(e -> e.length())
                .sum();

        int sum_two = list.stream()
                .mapToInt(e -> e.length())
                .reduce(0 , (x, y) -> x + y);

        Comparator<Integer> byName = Comparator.comparing(Integer::intValue);
        Arrays.sort(new Integer[]{1,2,4}, byName);*/

        Function<String, String> result = String::toUpperCase;
        System.out.print(result.apply("xdlfglUVooo"));
        IntFunction<int[]> aNew = int[]::new;
        aNew.apply(10);
        List<Integer> aList = new ArrayList<>();
        List<Selector> seList= new ArrayList<>();
        aList.forEach(p -> p.intValue());
        List<Selector> newList = seList.stream().filter(v -> v.getIndex() >100).collect(Collectors.toList());
        newList.forEach(v -> v.setSelect(true));
    }

    public class Selector{
        private boolean select = false;
        private int index = 0;
        public void setSelect(boolean select){
            this.select = select;
        }
        public int getIndex(){
            return this.index;
        }
        public int setIndex(int index){
            return this.index = index;
        }
    }

}
