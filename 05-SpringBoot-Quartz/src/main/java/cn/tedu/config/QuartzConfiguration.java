package cn.tedu.config;

import cn.tedu.quartz.QuartzJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfiguration {
    @Bean
    public JobDetail showTime() {
        System.out.println("showTime方法运行了");
        return JobBuilder.newJob(QuartzJob.class)
                .withIdentity("dateTime")//指定的當前任務給予唯一名稱
                .storeDurably()//即使沒有Trigger綁定 也不會被刪除
                .build();
    }

    //創建觸發器
    @Bean
    public Trigger showTimeTrigger() {
        System.out.println("showTimeTrigger方法运行了");
        //定义Cron表达式
        CronScheduleBuilder cron = CronScheduleBuilder.cronSchedule("0/3 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(showTime())//綁定要運行的任務方法
                .withIdentity("dateTimeTrigger")//該觸發器的唯一名稱
                .withSchedule(cron)//綁定觸發器
                .build();
    }
}
