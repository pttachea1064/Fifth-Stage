package cn.tedu.demo;

import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 調度器類 內部會定義觸發器 並且將觸發器與任務進行綁定
 */
public class HelloScheduler {
    public static void main(String[] args) throws Exception {
        //1.從工廠中獲取任務調度之實際例子
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        //2.定義任務調度實際例子 並將該實際例子與任務類綁定         //定義當前實際例子的唯一表示
        JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("job1", "group1").build();
        System.out.println("name:" +job.getKey().getName());
        System.out.println("group:"+job.getKey().getGroup());
        System.out.println("jobClass:" +job.getClass().getName());
        //3.定義觸發器 現在開始每五秒執行一次
        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .startNow()//立即執行
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatSecondlyForever(5))//每5秒執行一次
                .build();
        //4.使用觸發器調度任務的執行
        scheduler.scheduleJob(job,trigger);
        //開啟
        scheduler.start();
        //關閉
        //scheduler.shutdown();
    }
}
