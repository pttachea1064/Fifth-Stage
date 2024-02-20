package cn.tedu.demo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;


/**
 * 簡單觸發器 指定時間執行一次
 */
public class HelloSimpleScheduler {
    public static void main(String[] args) throws Exception {
        //1.從工廠中獲取任務調度之實際例子
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        //2.定義觸發的時間點
        Date startDate = new Date();
        Date endDate = new Date();

        endDate.setTime(endDate.getTime()+20000);

        startDate.setTime(startDate.getTime()+3000);//啟動後的3秒後
        JobDetail job = JobBuilder.newJob(HelloSimpleJob.class).withIdentity("job1", "group1").build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .startAt(startDate)//在指定的時間開始執行
                .endAt(endDate)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatSecondlyForever(5).withRepeatCount(3))
                //每5秒執行一次 執行指定的次數後結束執行(withReapeatCount)
                .build();

        scheduler.scheduleJob(job,trigger);

        scheduler.start();







    }
}
