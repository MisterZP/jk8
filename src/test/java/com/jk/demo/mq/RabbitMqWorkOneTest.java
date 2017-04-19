package com.jk.demo.mq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by zengping on 2017/4/18.
 */
public class RabbitMqWorkOneTest {

    public static void main(String[] args) throws IOException, TimeoutException {
        class Worker {
            public void listener() throws IOException, TimeoutException {
                MqWorkerBuilder.builder("admin", "admin", "10.118.28.177");
            }
        }
        new Worker().listener();
    }

}
