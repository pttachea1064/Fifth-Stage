package cn.tedu.demo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;


/**
 * 簡單觸發器 指定時間執行一次
 */
public class HelloCronScheduler {
    public static void main(String[] args) throws Exception {
        //1.從工廠中獲取任務調度之實際例子
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        //2.定義觸發的時間點
        JobDetail job = JobBuilder.newJob(HelloCronJob.class).withIdentity("job1", "group1").build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))//每5秒執行一次
                .build();

        scheduler.scheduleJob(job,trigger);

        scheduler.start();







    }
}
