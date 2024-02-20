package cn.tedu.demo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;

/**
 * 任務類:定義定時執行之內容
 */
public class HelloJob implements Job {
    @Override
    public void execute (JobExecutionContext jobExecutionContext) throws JobExecutionException{
    //獲取當前時間
        LocalDateTime now = LocalDateTime.now();
        System.out.println("準被開始計時每五秒一次運作,當前時間為: " +now);
    }
}
