package com.jk.demo.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by zengping on 2017/4/18.
 */
public class RabbitMqTaskTest {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        class Task {

            public void send(int index) throws IOException, TimeoutException {
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
                    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
                    String message = MessageHelper.getMessage(new String[]{"hello world", "I have a dream|{" + ++index + "}"});
                    channel.basicPublish("", TASK_QUEUE_NAME,
                            MessageProperties.PERSISTENT_TEXT_PLAIN,
                            message.getBytes());
                    System.out.println(new StringBuffer().append(" [").append(index).append("] Sent '").append(message).append("'"));

                } finally {
                    if (null != channel)
                        channel.close();
                    if (null != connection)
                        connection.close();
                }
            }

        }

        Task send = new Task();
        for (int i = 0; i < 5; i++) {
            send.send(i);
        }
    }

}
