package com.bnksys.esg.config;

import com.bnksys.esg.service.DynamicSchedulingService;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

@Configuration
public class QuartzConfig {

    // 스케줄러 팩토리 빈을 생성하는 메서드
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        // 스케줄러를 돌리는데 사용되는 Bean을 생성
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        return schedulerFactoryBean;
    }

    // 스케줄러 빈을 생성하는 메서드
    @Bean
    public Scheduler scheduler() {
        // 스케줄러 팩토리 빈에서 스케줄러를 가져와 반환
        return schedulerFactoryBean().getScheduler();
    }
}
