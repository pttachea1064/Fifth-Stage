package cn.tedu.work;

import com.rabbitmq.client.*;

import java.io.IOException;

public class WorkConsumer {
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
        // 4.接收消息
        DeliverCallback d = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                byte[] a = message.getBody();
                String s = new String(a);
                System.out.println(consumerTag + "收到：" + s);
                System.out.println("-----------");
            }
        };
        CancelCallback c = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        };
        cc.basicConsume("work.queue", true, d, c);
        // 5.关闭连接
        //nc.close();
    }
}
