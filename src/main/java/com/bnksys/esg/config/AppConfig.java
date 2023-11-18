package com.bnksys.esg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    // Http 요청 및 응답 시 동기식 요청으로 블로하여 데이터를 주고 받기 위해 RestTemplete Bean등록
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}