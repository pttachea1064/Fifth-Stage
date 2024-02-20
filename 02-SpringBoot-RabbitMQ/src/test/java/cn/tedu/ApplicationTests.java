package cn.tedu;

import cn.tedu.config.RabbitMQConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
class ApplicationTests {

    //利用RabbitTemplate工具类,操作RabbitMq
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void RabbitMqProducer() {
        //获取当前时间,发送到RabbitMq的服务器中
        LocalDateTime now = LocalDateTime.now();
        //convertAndSend() 会自动将发送的信息转换为byte[]发送
        rabbitTemplate.convertAndSend(
                RabbitMQConfiguration.TEST_EX, //指定交换机
                RabbitMQConfiguration.TEST_RK, //指定路由key
                now //具体发送的信息
        );
    }


}
