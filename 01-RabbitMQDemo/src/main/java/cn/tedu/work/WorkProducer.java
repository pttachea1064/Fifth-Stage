package cn.tedu.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

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
        cc.queueDeclare("work.queue", true, false, false, null);
        // 4.发送消息
        String s = null;
        while (true) {
            System.out.print("输入数据: ");
            s = new Scanner(System.in).nextLine();
            if (s.equals("quit")) {
                break;
            }
            cc.basicPublish("", "work.queue", MessageProperties.PERSISTENT_TEXT_PLAIN, s.getBytes());
        }
        nc.close();
    }
}