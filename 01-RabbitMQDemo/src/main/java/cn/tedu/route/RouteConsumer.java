package cn.tedu.route;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Scanner;

public class RouteConsumer {
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
        //定义交换机,如果服务器没有指定的交换机,就自己创建一个
        cc.exchangeDeclare("route.logs", "direct");
        // 5.定义路由key
        System.out.println("請輸入當前消費者要綁定的路由Key,如果有多個請用空格隔開: ");
        String keys = new Scanner(System.in).nextLine();
        String[] bindKeys = keys.split("\\s+");//隨意的空格數量 (只要是空格就可以)
        for (String bindKey : bindKeys) {
            // 6.绑定交换机和队列
            cc.queueBind(queueName, "route.logs", bindKey);
        }

        // 7.接收消息
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
        cc.basicConsume(queueName, true, d, c);
        // 5.关闭连接
        //nc.close();
    }
}
