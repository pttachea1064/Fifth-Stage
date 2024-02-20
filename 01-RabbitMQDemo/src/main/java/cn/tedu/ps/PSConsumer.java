package cn.tedu.ps;

import com.rabbitmq.client.*;

import java.io.IOException;

public class PSConsumer {
    public static void main(String[] args) throws Exception {
        // 1.建立连接
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("192.168.144.160");
        cf.setPort(5672);
        cf.setUsername("user");
        cf.setPassword("123456");
        Connection nc = cf.newConnection();
        // 2.创建通道Channel
        final Channel cc = nc.createChannel();
        // 3.定义队列,随机队列,非持久,独占并且自动删除
        String queueName = cc.queueDeclare().getQueue();
        // 4.绑定交换机
        cc.exchangeDeclare("logs","fanout");
        // 5.绑定交换机和队列
        cc.queueBind(queueName,"logs","");
        // 6.接收消息
        DeliverCallback d = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                byte[] a = message.getBody();
                String s = new String(a);
                System.out.println(consumerTag + "收到：" + s);
                //判断接收的内容是否包含".",如果包含,那么睡眠10秒
                if (s.contains(".")) {
                    try {
                        System.out.println(consumerTag + "开始处理消息...");
                        Thread.sleep(10000);
                        System.out.println(consumerTag + "处理消息完毕!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        CancelCallback c = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        };
        cc.basicConsume(queueName,true,d,c);

        // 7.关闭连接
        //nc.close();
    }
}
