package com.jk.demo.mq;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

/**
 * Created by zengping on 2017/4/18.
 */
public class MqSubscribeTest {
    public static void main(String[] args) throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        class Subscribe {
            public void subscribe() throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
                SubscribeBuilder.builder("admin", "admin", "10.118.28.177");
            }
        }
        new Subscribe().subscribe();
    }
}
