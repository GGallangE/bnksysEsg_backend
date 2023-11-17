package com.bnksys.esg.service;

import com.bnksys.esg.Enum.ApiList;
import com.bnksys.esg.data.batchListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.TimeZone;

@Configuration
@EnableScheduling
public class DynamicSchedulingService implements SchedulingConfigurer {

    @Autowired
    private BatchListService batchListService;

    @Autowired
    private ApiBatchService apiBatchService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(20);
        taskScheduler.initialize();
        taskRegistrar.setTaskScheduler(taskScheduler);
    }

    @Scheduled(fixedRate = 60000)
    public void refreshSchedules() {
        List<batchListDto> batchList = batchListService.getAllBatchList();
        for (batchListDto scheduling : batchList) {
            String cronExpression = scheduling.getBatchtime();
            int batchlistid = scheduling.getBatchlistid();
            int apilistid = scheduling.getApilistid();
            int userid = scheduling.getUserid();

            scheduleTask(batchlistid, apilistid, userid, cronExpression);
        }
    }

    private void scheduleTask(int batchlistid, int apilistid, int userid, String cronExpression) {
        ApiList apilist = ApiList.fromSchedulingId(apilistid);
        String methodName = apilist.getFunctionName();

        TaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        ((ThreadPoolTaskScheduler) taskScheduler).setPoolSize(20);
        ((ThreadPoolTaskScheduler) taskScheduler).initialize();

        taskScheduler.schedule(() -> {
            System.out.println("Executing task for SchedulingId: " + apilist);
            try {
                Method method = ApiBatchService.class.getMethod(methodName, int.class, int.class, int.class);
                method.invoke(apiBatchService, batchlistid, apilistid, userid);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace(); // Handle the exception as needed
            }
        }, new CronTrigger(cronExpression, TimeZone.getTimeZone("Asia/Seoul")));
    }
}
