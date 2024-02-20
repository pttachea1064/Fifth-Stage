package cn.tedu.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

public class WorkProducer {
    public static void main(String[] args) throws Exception {
        // 1.建立连接
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("192.168.144.160");
        cf.setPort(5672);
        cf.setUsername("user");
        cf.setPassword("123456");
        Connection nc = cf.newConnection();
        // 2.创建通道Channel
        Channel cc = nc.createChannel();
        // 3.创建队列
        cc.queueDeclare("work.queue", false, false, false, null);
        // 4.发送消息
        String s="Hello World"+System.currentTimeMillis();
        cc.basicPublish("", "work.queue", null, s.getBytes());
        System.out.println("发送成功!");
        // 5.关闭通道和连接
        nc.close();
    }
}