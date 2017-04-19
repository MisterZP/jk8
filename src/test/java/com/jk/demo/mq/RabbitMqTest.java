package com.jk.demo.mq;

import com.rabbitmq.client.*;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by zengping on 2017/4/18.
 */
public class RabbitMqTest {

    private final static String QUEUE_NAME = "test.queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        class Send {
            public void send() throws IOException, TimeoutException {
                Connection connection = null;
                Channel channel = null;
                try {
                    ConnectionFactory factory = new ConnectionFactory();
                    factory.setUsername("admin");
                    factory.setPassword("admin");
                    //factory.setPort(5672);
                    factory.setHost("10.118.28.177");
                    connection = factory.newConnection();
                    channel = connection.createChannel();
                    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                    String message = "Hello World!";
                    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                    System.out.println(" [x] Sent '" + message + "'");
                } finally {
                    if (null != channel)
                        channel.close();
                    if (null != connection)
                        connection.close();
                }
            }
        }

        class Reciew {
            public void receiew() throws IOException, TimeoutException {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setUsername("admin");
                factory.setPassword("admin");
                factory.setHost("10.118.28.177");
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();
                channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
                Consumer consumer = new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope,
                                               AMQP.BasicProperties properties, byte[] body)
                            throws IOException {
                        String message = new String(body, "UTF-8");
                        System.out.println(" [x] Received '" + message + "'");
                    }
                };
                channel.basicConsume(QUEUE_NAME, true, consumer);
            }
        }

        Send send = new Send();
        Reciew reciew = new Reciew();

        for (int i = 0; i < 5; i++) {
            send.send();
        }
        reciew.receiew();
    }

}
