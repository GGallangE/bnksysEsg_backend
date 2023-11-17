package com.bnksys.esg.service;

import com.bnksys.esg.Enum.ApiList;
import com.bnksys.esg.data.batchListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TimeZone;

@Service
public class DynamicSchedulingService {

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private BatchListService batchListService;

    @Scheduled(fixedRate = 60000)
    public void refreshSchedules() {
        List<batchListDto> schedulingList = batchListService.getAllBatchList();
        for (batchListDto scheduling : schedulingList) {
            String cronExpression = scheduling.getBatchtime();
            int schedulingId = scheduling.getBatchlistid();

            scheduleTask(schedulingId, cronExpression);
        }
    }

    private void scheduleTask(int schedulingId, String cronExpression) {
        ApiList apilist = ApiList.fromSchedulingId(schedulingId);
        taskScheduler.schedule(() -> {
            System.out.println("Executing task for SchedulingId: " + apilist);
        }, new CronTrigger(cronExpression, TimeZone.getTimeZone("Asia/Seoul")));
    }
}
