package cn.tedu.demo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;

public class HelloCronJob implements Job {
    @Override
    public void execute (JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //獲取當前時間
        LocalDateTime now = LocalDateTime.now();
        System.out.println("CronJob is taught 當前時間為:" +now   );
    }
}
