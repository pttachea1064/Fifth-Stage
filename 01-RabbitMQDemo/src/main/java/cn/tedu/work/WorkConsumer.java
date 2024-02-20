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
        cc.queueDeclare("work.queue", true, false, false, null);
        // 4.接收消息
        DeliverCallback d = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                byte[] a = message.getBody();
                String s = new String(a);
                System.out.println(consumerTag + "收到：" + s);
                //判斷接收的內容是否包含"."如果包含就sleep 10秒(模擬處理中...)
                if (s.contains(".")) {
                    try {
                        System.out.println(consumerTag + "开始处理消息...");
                        Thread.sleep(10000);
                        System.out.println(consumerTag + "处理消息完毕!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //上述操作結束後 說明消費者已經處理完了任務 此時手動給生產者發送ack消息
                /**
                 * 發送消息回執
                 * 第一個參數:消息確認 消息標籤
                 * 第二個參數:true表示確認多條消息 false表示只確認一條消息
                 */
                cc.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }
        };
        CancelCallback c = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        };
        //告知消費者一次只接收一條消息 參數為1 只接收一條消息 這條消息沒有被處理結束前 不會接受下一條消息
        //這種行為只有在ack手動確認時才生效
        cc.basicQos(1);
        //false表示需要手動確認消息是否已經處理
        cc.basicConsume("work.queue", false, d, c);
        // 5.关闭连接
        //nc.close();
    }
}
