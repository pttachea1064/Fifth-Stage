package cn.tedu.consumer;

import cn.tedu.config.RabbitMQConfiguration;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
//指定監聽的隊伍列名稱
@RabbitListener(queues = RabbitMQConfiguration.TEST_QUEUE)
public class RabbitConsumer {

    /**
     * 自訂的方法用於處理消費者讀取隊伍列中訊息後的處理邏輯
     * 並且必須要在方法上添加@RabbitHandler,
     * 而且該註解只能在一個方法上添加
     * 自訂的方法還需要聲明形參,該參數用於接收隊伍列中讀取的數據 所以該類形要和讀取的內容一致
     * 框架會自動將隊伍列中的字節數組轉換為對應參數類型之格式
     */
    @RabbitHandler
    public void listen(LocalDateTime localDateTime){
        System.out.println(localDateTime);
    }
}
