package cn.tedu.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 該配置類用於生產RabbitMQ的必要組件
 * 交換機
 * 隊伍列
 * 路由key
 */
@Configuration
public class RabbitMQConfiguration {
    //定義隊列名稱
    public static final String TEST_QUEUE="test_queue";
    //定義交換機名稱
    public static final String TEST_EX="test_ex";
    //定義路由key名稱
    public static final String TEST_RK="test_routing_key";

    @Bean
    public Queue generateQueue(){
        return  new Queue(TEST_QUEUE);
    }

    @Bean
    public DirectExchange generateExchange(){
        return new DirectExchange(TEST_EX);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(generateQueue()).to(generateExchange()).with(TEST_RK);
    }

}
