package cn.tedu.demo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;

public class HelloSimpleJob implements Job {
    @Override
    public void execute (JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //獲取當前時間
        LocalDateTime now = LocalDateTime.now();
//        System.out.println("準被開始計時每五秒一次運作,當前時間為: " +now);
        System.out.println("SimpleJob is taught 當前時間為:" +now   );
    }
}
