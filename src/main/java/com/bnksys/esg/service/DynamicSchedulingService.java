package com.bnksys.esg.service;

import com.bnksys.esg.Enum.ApiList;
import com.bnksys.esg.data.batchListDto;
import org.apache.poi.ss.formula.functions.Now;
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
import java.util.*;
import java.util.concurrent.ScheduledFuture;

@Configuration
@EnableScheduling
public class DynamicSchedulingService implements SchedulingConfigurer {

    @Autowired
    private BatchListService batchListService;

    @Autowired
    private ApiBatchService apiBatchService;

    private Map<Integer, ScheduledFuture<?>> scheduledTasks = new HashMap<>();
    private List<batchListDto> currentBatchList;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(20);
        taskScheduler.initialize();
        taskRegistrar.setTaskScheduler(taskScheduler);

        // 최초 한 번만 모든 스케줄을 가져옴
        currentBatchList = batchListService.getAllBatchList();
        scheduleAllTasks();
    }

    private void scheduleAllTasks() {
        for (batchListDto scheduling : currentBatchList) {
            int batchlistid = scheduling.getBatchlistid();
            int apilistid = scheduling.getApilistid();
            int userid = scheduling.getUserid();
            String cronExpression = scheduling.getBatchtime();

            scheduleTask(batchlistid, apilistid, userid, cronExpression);
        }
    }

    public void scheduleTask(int batchlistid, int apilistid, int userid, String cronExpression) {
        ApiList apilist = ApiList.fromSchedulingId(apilistid);
        String methodName = apilist.getFunctionName();

        TaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        ((ThreadPoolTaskScheduler) taskScheduler).setPoolSize(20);
        ((ThreadPoolTaskScheduler) taskScheduler).initialize();

        ScheduledFuture<?> scheduledTask = ((ThreadPoolTaskScheduler) taskScheduler).schedule(() -> {
            System.out.println("Executing task for SchedulingId: " + apilist);
            apiBatchService.apirequest(batchlistid, apilistid, userid);
        }, new CronTrigger(cronExpression, TimeZone.getTimeZone("Asia/Seoul")));

        scheduledTasks.put(batchlistid, scheduledTask);
    }

    public void updateSchedule(int batchlistid, int apilistid, int userid, String cronExpression) {
        if (scheduledTasks.containsKey(batchlistid)) {
            ScheduledFuture<?> existingTask = scheduledTasks.get(batchlistid);
            existingTask.cancel(true);
            scheduleTask(batchlistid, apilistid, userid, cronExpression);
        } else {
            scheduleTask(batchlistid, apilistid, userid, cronExpression);
        }
    }

    public void removeScheduledTask(int batchlistid) {
        if (scheduledTasks.containsKey(batchlistid)) {
            ScheduledFuture<?> removedTask = scheduledTasks.remove(batchlistid);
            if (removedTask != null) {
                removedTask.cancel(true);
            }
        }
    }
}
