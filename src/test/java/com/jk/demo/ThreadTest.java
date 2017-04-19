package com.jk.demo;

/**
 * Created by zengping on 2017/4/10.
 */
public class ThreadTest {

    public static void main(String args[]){

        class Demo extends Thread{
            @Override
            public void run() {
                // 业务代码
            }
        }

        new Demo().start();//方式一
        new Thread(()->{
            //业务代码
        }).start();//方式二
    }
}
