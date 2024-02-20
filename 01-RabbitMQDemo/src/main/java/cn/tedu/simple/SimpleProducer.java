package cn.tedu.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SimpleProducer {
    public static void main(String[] args) throws Exception {
        // 1.建立连接
        ConnectionFactory cf = new ConnectionFactory();
        // ①设置连接的参数: 主机名、端口号、vhost、用户名和密码
        cf.setHost("192.168.144.160");
        cf.setPort(5672);
        cf.setUsername("user");
        cf.setPassword("123456");
        // ②建立连接
        Connection nc = cf.newConnection();
        // 2.创建通道Channel
        Channel cc = nc.createChannel();
        // 3.创建队列
        //服務通信 發送數據 hello world
        /**
         * 第一個參數:隊列的名稱
         * 第二個參數:是否是一個持久隊列
         * 第三個參數:是否是獨占隊列
         * 第四個參數:是否自動刪除
         * 第五個參數:其他參數屬性設置
         */
        cc.queueDeclare("simple.queue", false, false, false, null);
        // 4.发送消息
        String s= " Hello World " + System.currentTimeMillis();
        /**
         * 第一個參數:先忽略
         * 第二個參數:隊列的名稱
         * 第三個參數:其他屬性設置
         * 第四個參數:要發到隊列的消息(發送到服務器的消息),發送內容需要是byte[]數組類型
         */
        cc.basicPublish("", "simple.queue", null, s.getBytes());
        System.out.println("发送成功!");
        // 5.处理消息
        nc.close();
    }

}
