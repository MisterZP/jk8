package com.jk.demo.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

/**
 * Created by zengping on 2017/4/18.
 */
public class MqPublishTest {
    private static final String EXCHANGE_NAME = "test.publish";
    private static final String AMQP_URI = "amqp://admin:admin@10.118.28.177:5672";
    public static void main(String[] args) throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        class Publish {
            public void publish(int index) throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
                Connection connection = null;
                Channel channel = null;
                try {
                    ConnectionFactory factory = new ConnectionFactory();
                    /*factory.setUsername("admin");
                    factory.setPassword("admin");
                    factory.setHost("10.118.28.177");*/
                    factory.setUri(AMQP_URI);
                    connection = factory.newConnection();
                    //factory.setPort(5672);
                    channel = connection.createChannel();
                    channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
                    String message = MessageHelper.getMessage(new String[]{"hello world", "I have a dream|{" + ++index + "}"});
                    channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
                    System.out.println(" [x] Sent '" + message + "'");
                } finally {
                    if (null != channel)
                        channel.close();
                    if (null != connection)
                        connection.close();
                }

            }
        }
        for (int i = 0; i < 10; i++)
            new Publish().publish(i);
    }
}
