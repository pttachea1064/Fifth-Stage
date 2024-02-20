package cn.tedu.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SimpleConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1.建立連接
        ConnectionFactory cf = new ConnectionFactory();
        // ①设置连接参数,分别是: 主机名、端口号、vhost、用户名和密码
        cf.setHost("192.168.144.160");
        cf.setPort(5672);
        cf.setUsername("user");
        cf.setPassword("123456");
        // ②建立连接
        Connection nc = cf.newConnection();
        //2.建立通道
        Channel cc = nc.createChannel();
        //3.創建Queue (必須要與生產者創建的隊列同名,屬性也必須要相同)
        cc.queueDeclare("simple.queue", false, false, false, null);

        //4.接收Message

        //4.1處理數據的回調函數
        /**
         * 處理消息的方法
         * @param consumerTag 消費者的唯一標示系統自動生成
         * @param message 我們接收的消息封裝在其中
         * @throws IOException
         */
        DeliverCallback d = new DeliverCallback() {
            public void handle(String consumerTag , Delivery message) throws IOException{
                //獲取Delivery中封裝的讀取Queue中的字節內容
                byte[] a = message.getBody();
                String s = new String (a);
                System.out.println(consumerTag+"get: " + s);
            }
        };

        //4.2取消數據的回調函數
        CancelCallback c = new CancelCallback() {
            public void handle(String s) throws IOException {

            }
        };

        /**
         * 第一個參數:隊伍列名稱
         * 第二個參數:先設為true 表示是否自動確認接收到訊息
         * 第三個參數:處理數據的回調函數
         * 第四個參數:取消接收的回調函數
         */
        cc.basicConsume("simple.queue",true,d,c );
        //關閉連接
//        nc.close();


    }
}
