package cn.tedu.route;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

public class RouteProducer {
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

        // 3.创建交换机
        cc.exchangeDeclare("route.logs", BuiltinExchangeType.DIRECT);

        // 4.发送消息
        String s = null;
        while (true) {
            System.out.print("输入数据: ");
            s = new Scanner(System.in).nextLine();
            if (s.equals("quit")) {
                break;
            }
            //将信息发送给交换机
            cc.basicPublish("route.logs", "", null, s.getBytes());
        }

        // 5.关闭通道和连接
        nc.close();
    }
}