package com.jk.demo;

/**
 * Created by zengping on 2017/4/10.
 */
public class SingleTown {
    private SingleTown() {
    }

    public static SingleTown instance() {
        return InstanceHelper.singleTown;
    }

    private static class InstanceHelper {
        public static SingleTown singleTown = new SingleTown();
    }

}
